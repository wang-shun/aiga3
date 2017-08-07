package com.ai.am.core.transaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.JdbcTransactionObjectSupport;
import org.springframework.orm.jpa.DefaultJpaDialect;
import org.springframework.orm.jpa.EntityManagerFactoryInfo;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;
import org.springframework.transaction.support.DelegatingTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.CollectionUtils;

import com.ai.am.core.datasource.DatabaseContextHolder;
import com.ai.am.core.datasource.DynamicDataSource;

/**
 * @ClassName: MultipleTransactionManager
 * @author: taoyf
 * @date: 2017年5月2日 下午5:32:58
 * @Description:
 * 
 */
public class MultipleTransactionManager extends AbstractPlatformTransactionManager implements InitializingBean {

	public static final String RESOURCE_HANDLER_MARK = "MUL_TRAN_RESOURCE_HANDLER_MARK";

	/**
	 * 基础数据库, 暂用 entityManager
	 */
	private EntityManagerFactory entityManagerFactory;

	private DynamicDataSource dynamicDataSource;

	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public DynamicDataSource getDynamicDataSource() {
		return dynamicDataSource;
	}

	public void setDynamicDataSource(DynamicDataSource dynamicDataSource) {
		this.dynamicDataSource = dynamicDataSource;
	}

	@Override
	public void afterPropertiesSet() {
		if (getDynamicDataSource() == null) {
			throw new IllegalArgumentException("Property 'dataSource' is required");
		}
		if (getEntityManagerFactory() == null) {
			throw new IllegalArgumentException("'entityManagerFactory' or 'persistenceUnitName' is required");
		}
		if (getEntityManagerFactory() instanceof EntityManagerFactoryInfo) {
			EntityManagerFactoryInfo emfInfo = (EntityManagerFactoryInfo) getEntityManagerFactory();
			// DataSource dataSource = emfInfo.getDataSource();
			// if (dataSource != null) {
			// setDataSource(dataSource);
			// }
			JpaDialect jpaDialect = emfInfo.getJpaDialect();
			if (jpaDialect != null) {
				setJpaDialect(jpaDialect);
			}
		}
	}

	/******************************************/
	private final Map<String, Object> jpaPropertyMap = new HashMap<String, Object>();

	private JpaDialect jpaDialect = new DefaultJpaDialect();

	public void setJpaProperties(Properties jpaProperties) {
		CollectionUtils.mergePropertiesIntoMap(jpaProperties, this.jpaPropertyMap);
	}

	public void setJpaPropertyMap(Map<String, ?> jpaProperties) {
		if (jpaProperties != null) {
			this.jpaPropertyMap.putAll(jpaProperties);
		}
	}

	public Map<String, Object> getJpaPropertyMap() {
		return this.jpaPropertyMap;
	}

	public void setJpaDialect(JpaDialect jpaDialect) {
		this.jpaDialect = (jpaDialect != null ? jpaDialect : new DefaultJpaDialect());
	}

	public JpaDialect getJpaDialect() {
		return this.jpaDialect;
	}

	/******************************************/

	/**
	 * 得到一个事务对象, 就认为
	 */
	@Override
	protected Object doGetTransaction() throws TransactionException {
		MultipleTransaction transaction = new MultipleTransaction();
		transaction.setSavepointAllowed(isNestedTransactionAllowed());
		MultipleTransactionHolder mulHolder = (MultipleTransactionHolder) TransactionSynchronizationManager
				.getResource(RESOURCE_HANDLER_MARK);
		transaction.setMultipleTransactionHolder(mulHolder, false);
		return transaction;
	}

	@Override
	protected boolean isExistingTransaction(Object transaction) {
		MultipleTransaction txObject = (MultipleTransaction) transaction;

		String currentDs = getDynamicDataSource().determineCurrentLookupKey();

		if (txObject.getMultipleTransactionHolder() == null) {
			return false;
		} else {
			ResourceHolder holder = txObject.getMultipleTransactionHolder().getHolder(currentDs);
			return holder != null;
		}

	}

	/**
	 * 开始一个事务, 具体到连接怎么处理
	 */
	@Override
	protected void doBegin(Object transaction, TransactionDefinition definition) throws TransactionException {
		MultipleTransaction txObject = (MultipleTransaction) transaction;
		MultipleTransactionHolder mtholder = txObject.getMultipleTransactionHolder();
		// if(mtholder == null || mtholder.isSynchronizedWithTransaction()){
		if (mtholder == null) {
			mtholder = new MultipleTransactionHolder();
			txObject.setMultipleTransactionHolder(mtholder, true);
		}

		String currentDs = getDynamicDataSource().determineCurrentLookupKey();

		try {

			if (currentDs == null || DatabaseContextHolder.DEFAULT_BASE.equals(currentDs)) {
				// base库, 走JPA
				ResourceHolder rh = mtholder.getHolder(DatabaseContextHolder.DEFAULT_BASE);
				if (rh == null || rh.getResourceHolder() == null
						|| rh.getResourceHolder().isSynchronizedWithTransaction()) {
					EntityManager newEm = createEntityManagerForTransaction();
					if (logger.isDebugEnabled()) {
						logger.debug("Opened new EntityManager [" + newEm + "] for JPA transaction");
					}
					EntityManagerHolder emh = new EntityManagerHolder(newEm);
					rh = new ResourceHolder();
					rh.setName(DatabaseContextHolder.DEFAULT_BASE);
					rh.setNewResource(true);
					rh.setResourceHolder(emh);

					mtholder.putHolder(DatabaseContextHolder.DEFAULT_BASE, rh);
				}
				EntityManagerHolder emh = (EntityManagerHolder) rh.getResourceHolder();
				EntityManager em = emh.getEntityManager();
				// Delegate to JpaDialect for actual transaction begin.
				final int timeoutToUse = determineTimeout(definition);
				Object transactionData = getJpaDialect().beginTransaction(em,
						new DelegatingTransactionDefinition(definition) {
							@Override
							public int getTimeout() {
								return timeoutToUse;
							}
						});
				rh.setData(transactionData);

				// Bind the entity manager holder to the thread.
				if (rh.isNewResource()) {
					TransactionSynchronizationManager.bindResource(getEntityManagerFactory(), emh);
				}
				emh.setSynchronizedWithTransaction(true);

			} else {
				// 动态库, 走jdbc
				ResourceHolder rh = mtholder.getHolder(currentDs);
				if (rh == null || rh.getResourceHolder() == null
						|| rh.getResourceHolder().isSynchronizedWithTransaction()) {
					DataSource dataSource = getDynamicDataSource().determineTargetDataSource();
					Connection newCon = dataSource.getConnection();
					if (logger.isDebugEnabled()) {
						logger.debug("Acquired Connection [" + newCon + "] for JDBC transaction");
					}
					ConnectionHolder ch = new ConnectionHolder(newCon);
					rh = new ResourceHolder();
					rh.setName(currentDs);
					rh.setNewResource(true);
					rh.setResourceHolder(ch);
					rh.setDataSource(dataSource);

					mtholder.putHolder(currentDs, rh);
				}

				ConnectionHolder ch = (ConnectionHolder) rh.getResourceHolder();

				ch.setSynchronizedWithTransaction(true);
				Connection con = ch.getConnection();

				Integer previousIsolationLevel = DataSourceUtils.prepareConnectionForTransaction(con, definition);
				txObject.setPreviousIsolationLevel(previousIsolationLevel);

				// Switch to manual commit if necessary. This is very expensive
				// in some JDBC drivers,
				// so we don't want to do it unnecessarily (for example if we've
				// explicitly
				// configured the connection pool to set it already).
				if (con.getAutoCommit()) {
					// txObject.setMustRestoreAutoCommit(true);
					rh.setData(true);
					if (logger.isDebugEnabled()) {
						logger.debug("Switching JDBC Connection [" + con + "] to manual commit");
					}
					con.setAutoCommit(false);
				}
				// txObject.getConnectionHolder().setTransactionActive(true);

				int timeout = determineTimeout(definition);
				if (timeout != TransactionDefinition.TIMEOUT_DEFAULT) {
					txObject.getConnectionHolder().setTimeoutInSeconds(timeout);
				}

				// Bind the session holder to the thread.
				if (rh.isNewResource()) {
					TransactionSynchronizationManager.bindResource(getDynamicDataSource().determineTargetDataSource(),
							txObject.getConnectionHolder());
				}

			}

			if (txObject.isNewMultipleTransaction()) {
				TransactionSynchronizationManager.bindResource(RESOURCE_HANDLER_MARK, mtholder);
			}

		} catch (TransactionException ex) {
			closeEntityManagerAfterFailedBegin(txObject, currentDs);
			throw ex;
		} catch (Throwable ex) {
			closeEntityManagerAfterFailedBegin(txObject, currentDs);
			throw new CannotCreateTransactionException("Could not open JPA EntityManager for transaction", ex);
		}
	}

	/**
	 * 提交事务
	 */
	@Override
	protected void doCommit(DefaultTransactionStatus status) throws TransactionException {

		MultipleTransaction txObject = (MultipleTransaction) status.getTransaction();
		MultipleTransactionHolder mtholder = txObject.getMultipleTransactionHolder();

		if (mtholder.stackSize() == 0) {
			if (status.isDebug()) {
				logger.debug("transaction already commited!");
			}
		} else if (mtholder.stackSize() == 1) {
			// 开始提交
			Map<String, ResourceHolder> map = mtholder.getAllHolder();

			for (String key : map.keySet()) {
				ResourceHolder detailHolder = map.get(key);

				if (detailHolder.getResourceHolder() instanceof ConnectionHolder) {
					ConnectionHolder connectionHolder = (ConnectionHolder) detailHolder.getResourceHolder();

					Connection con = connectionHolder.getConnection();
					if (status.isDebug()) {
						logger.debug("Committing JDBC transaction on Connection [" + con + "]");
					}
					try {
						con.commit();
					} catch (SQLException ex) {
						detailHolder.setError(true);
						throw new TransactionSystemException("Could not commit JDBC transaction", ex);
					} finally {

					}
				} else if (detailHolder.getResourceHolder() instanceof EntityManagerHolder) {
					EntityManagerHolder entityManagerHolder = (EntityManagerHolder) detailHolder.getResourceHolder();

					try {
						EntityTransaction tx = entityManagerHolder.getEntityManager().getTransaction();
						tx.commit();
					} catch (RollbackException ex) {
						detailHolder.setError(true);
						if (ex.getCause() instanceof RuntimeException) {
							DataAccessException dex = getJpaDialect()
									.translateExceptionIfPossible((RuntimeException) ex.getCause());
							if (dex != null) {
								throw dex;
							}
						}
						throw new TransactionSystemException("Could not commit JPA transaction", ex);
					} catch (RuntimeException ex) {
						// Assumably failed to flush changes to database.
						throw DataAccessUtils.translateIfNecessary(ex, getJpaDialect());
					}
				}
			}

		} else {
			if (status.isDebug()) {
				logger.debug("not the open transaction");
			}
			mtholder.popHolder();
		}

	}

	/**
	 * 回滚事务
	 */
	@Override
	protected void doRollback(DefaultTransactionStatus status) throws TransactionException {

		MultipleTransaction txObject = (MultipleTransaction) status.getTransaction();
		MultipleTransactionHolder mtholder = txObject.getMultipleTransactionHolder();

		if (mtholder.stackSize() == 0) {
			if (status.isDebug()) {
				logger.debug("transaction already rollback!");
			}
		} else if (mtholder.stackSize() == 1) {
			// 开始提交
			Map<String, ResourceHolder> map = mtholder.getAllHolder();

			for (String key : map.keySet()) {
				ResourceHolder detailHolder = map.get(key);

				if (!detailHolder.isError()) {

					if (detailHolder.getResourceHolder() instanceof ConnectionHolder) {
						ConnectionHolder connectionHolder = (ConnectionHolder) detailHolder.getResourceHolder();

						Connection con = connectionHolder.getConnection();

						if (status.isDebug()) {
							logger.debug("Rolling back JDBC transaction on Connection [" + con + "]");
						}
						try {
							con.rollback();
						} catch (SQLException ex) {
							detailHolder.setError(true);
							throw new TransactionSystemException("Could not roll back JDBC transaction", ex);
						}

					} else if (detailHolder.getResourceHolder() instanceof EntityManagerHolder) {
						EntityManagerHolder entityManagerHolder = (EntityManagerHolder) detailHolder
								.getResourceHolder();

						if (status.isDebug()) {
							logger.debug("Rolling back JPA transaction on EntityManager ["
									+ entityManagerHolder.getEntityManager() + "]");
						}
						try {
							EntityTransaction tx = entityManagerHolder.getEntityManager().getTransaction();
							if (tx.isActive()) {
								tx.rollback();
							}
						} catch (PersistenceException ex) {
							throw new TransactionSystemException("Could not roll back JPA transaction", ex);
						} finally {
							if (!txObject.isNewMultipleTransaction()) {
								// Clear all pending inserts/updates/deletes in
								// the EntityManager.
								// Necessary for pre-bound EntityManagers, to
								// avoid inconsistent state.
								entityManagerHolder.getEntityManager().clear();
							}
						}
					}
				}
			}

		} else {
			if (status.isDebug()) {
				logger.debug("not the rollback transaction");
			}
			mtholder.popHolder();
		}

	}

	protected EntityManager createEntityManagerForTransaction() {
		EntityManagerFactory emf = getEntityManagerFactory();
		if (emf instanceof EntityManagerFactoryInfo) {
			emf = ((EntityManagerFactoryInfo) emf).getNativeEntityManagerFactory();
		}
		Map<String, Object> properties = getJpaPropertyMap();
		return (!CollectionUtils.isEmpty(properties) ? emf.createEntityManager(properties) : emf.createEntityManager());
	}

	protected void closeEntityManagerAfterFailedBegin(MultipleTransaction txObject, String key) {
		ResourceHolder resourceHolder = txObject.getMultipleTransactionHolder().getHolder(key);

		if (resourceHolder.isNewResource()) {

			if (resourceHolder.getResourceHolder() instanceof EntityManagerHolder) {
				EntityManagerHolder holder = (EntityManagerHolder) resourceHolder.getResourceHolder();
				if (holder != null) {
					EntityManager em = holder.getEntityManager();
					try {
						if (em.getTransaction().isActive()) {
							em.getTransaction().rollback();
						}
					} catch (Throwable ex) {
						logger.debug("Could not rollback EntityManager after failed transaction begin", ex);
					} finally {
						EntityManagerFactoryUtils.closeEntityManager(em);
					}
					txObject.getMultipleTransactionHolder().popHolder();
					txObject.getMultipleTransactionHolder().removeHolder(key);
				}
			} else if (resourceHolder.getResourceHolder() instanceof ConnectionHolder) {
				ConnectionHolder holder = (ConnectionHolder) resourceHolder.getResourceHolder();
				DataSourceUtils.releaseConnection(holder.getConnection(),
						getDynamicDataSource().determineTargetDataSource());

				txObject.getMultipleTransactionHolder().popHolder();
				txObject.getMultipleTransactionHolder().removeHolder(key);
			}
		}

	}

	@Override
	protected void doSetRollbackOnly(DefaultTransactionStatus status) {
		MultipleTransaction txObject = (MultipleTransaction) status.getTransaction();
		if (status.isDebug()) {
			logger.debug(
					"Setting MUL transaction [" + txObject.getConnectionHolder().getConnection() + "] rollback-only");
		}
		txObject.setRollbackOnly();
	}

	@Override
	protected void doCleanupAfterCompletion(Object transaction) {

		MultipleTransaction txObject = (MultipleTransaction) transaction;

		// Remove the connection holder from the thread, if exposed.
		if (txObject.isNewMultipleTransaction()) {
			TransactionSynchronizationManager.unbindResource(RESOURCE_HANDLER_MARK);
		}

		Map<String, ResourceHolder> map = txObject.getMultipleTransactionHolder().getAllHolder();

		for (String key : map.keySet()) {
			ResourceHolder detailHolder = map.get(key);

			if (detailHolder.getResourceHolder() instanceof ConnectionHolder) {
				ConnectionHolder connectionHolder = (ConnectionHolder) detailHolder.getResourceHolder();

				// Remove the connection holder from the thread, if exposed.
				if (txObject.isNewMultipleTransaction()) {
					TransactionSynchronizationManager.unbindResource(detailHolder.getDataSource());
				}

				// Reset connection.
				Connection con = connectionHolder.getConnection();
				try {
					if(Boolean.TRUE.equals(detailHolder.getData())){
						con.setAutoCommit(true);
					}
					DataSourceUtils.resetConnectionAfterTransaction(con, txObject.getPreviousIsolationLevel());
				}
				catch (Throwable ex) {
					logger.debug("Could not reset JDBC Connection after transaction", ex);
				}

				if (txObject.isNewMultipleTransaction()) { 
					if (logger.isDebugEnabled()) {
						logger.debug("Releasing JDBC Connection [" + con + "] after transaction");
					}
					DataSourceUtils.releaseConnection(con, (DataSource)detailHolder.getDataSource());
				}

				connectionHolder.clear();

			} else if (detailHolder.getResourceHolder() instanceof EntityManagerHolder) {
				EntityManagerHolder entityManagerHolder = (EntityManagerHolder) detailHolder.getResourceHolder();

				// Remove the entity manager holder from the thread, if still there.
				// (Could have been removed by EntityManagerFactoryUtils in order
				// to replace it with an unsynchronized EntityManager).
				if (txObject.isNewMultipleTransaction()) {
					TransactionSynchronizationManager.unbindResourceIfPossible(getEntityManagerFactory());
				}
				entityManagerHolder.clear();

				// Remove the JDBC connection holder from the thread, if exposed.
//				if (txObject.hasConnectionHolder()) {
//					TransactionSynchronizationManager.unbindResource(getDataSource());
//					try {
//						getJpaDialect().releaseJdbcConnection(txObject.getConnectionHolder().getConnectionHandle(),
//								txObject.getEntityManagerHolder().getEntityManager());
//					}
//					catch (Exception ex) {
//						// Just log it, to keep a transaction-related exception.
//						logger.error("Could not close JDBC connection after transaction", ex);
//					}
//				}

				getJpaDialect().cleanupTransaction(detailHolder.getData());

				// Remove the entity manager holder from the thread.
				if (txObject.isNewMultipleTransaction()) {
					EntityManager em = entityManagerHolder.getEntityManager();
					if (logger.isDebugEnabled()) {
						logger.debug("Closing JPA EntityManager [" + em + "] after transaction");
					}
					EntityManagerFactoryUtils.closeEntityManager(em);
				}
				else {
					logger.debug("Not closing pre-bound JPA EntityManager after transaction");
				}
			}
		}

	}

	private class MultipleTransaction extends JdbcTransactionObjectSupport {

		private boolean newMultipleTransaction = false;

		private MultipleTransactionHolder multipleTransactionHolder;

		/**
		 * @param newMultipleTransaction
		 * @param multipleTransactionHolder
		 */

		@Override
		public boolean isRollbackOnly() {
			return false;
		}

		public void setRollbackOnly() {

			Map<String, ResourceHolder> map = multipleTransactionHolder.getAllHolder();

			for (String key : map.keySet()) {
				ResourceHolder detailHolder = map.get(key);

				if (detailHolder.getResourceHolder() instanceof ConnectionHolder) {
					ConnectionHolder connectionHolder = (ConnectionHolder) detailHolder.getResourceHolder();
					connectionHolder.setRollbackOnly();

				} else if (detailHolder.getResourceHolder() instanceof EntityManagerHolder) {
					EntityManagerHolder entityManagerHolder = (EntityManagerHolder) detailHolder.getResourceHolder();

					EntityTransaction tx = entityManagerHolder.getEntityManager().getTransaction();
					if (tx.isActive()) {
						tx.setRollbackOnly();
					}
					if (hasConnectionHolder()) {
						getConnectionHolder().setRollbackOnly();
					}

				}
			}
		}

		public MultipleTransactionHolder getMultipleTransactionHolder() {
			return multipleTransactionHolder;
		}

		public void setMultipleTransactionHolder(MultipleTransactionHolder multipleTransactionHolder,
				boolean newMultipleTransaction) {
			this.multipleTransactionHolder = multipleTransactionHolder;
			this.newMultipleTransaction = newMultipleTransaction;
		}

		public boolean isNewMultipleTransaction() {
			return newMultipleTransaction;
		}

	}

}
