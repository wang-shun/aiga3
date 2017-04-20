package com.ai.aiga.dao.jpa;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.*;

public class SearchAndPageRepositoryImpl<T, ID extends Serializable> extends
        SimpleJpaRepository<T, ID> implements SearchAndPageRepository<T, ID> {

    private Class<T> domainClass;

    protected final EntityManager entityManager;

    public SearchAndPageRepositoryImpl(JpaEntityInformation entityInformation,
                                       EntityManager entityManager) {
        super(entityInformation, entityManager);
        domainClass = entityInformation.getJavaType();
        this.entityManager = entityManager;
    }

    public SearchAndPageRepositoryImpl(Class<T> domainClass,
                                       EntityManager entityManager) {
        super(domainClass, entityManager);

        this.domainClass = domainClass;
        this.entityManager = entityManager;
    }
    
    @Override
    public List<T> search(List<Condition> cons) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(domainClass);

        Root<T> root = query.from(domainClass);

        List<Predicate> list = new ArrayList<Predicate>();
        for (Condition con : cons) {
            if (con != null) {
                Predicate predicate = null;
                Path path = root.get(con.getName());
                if (con.getType().equals(Condition.Type.EQ)) {
                    predicate = builder.equal(path, con.getVal());
                } else if (con.getType().equals(Condition.Type.LIKE)) {
                    predicate = builder.like(path, con.getVal().toString());
                } else if (con.getType().equals(Condition.Type.LT)) {
                    Object val = con.getVal();
                    if (val instanceof Number) {
                        predicate = builder.lt(path, (Number) val);
                    } else if (val instanceof Date) {
                        predicate = builder.lessThan(path, builder.literal((Date) val));
                    }
                } else if (con.getType().equals(Condition.Type.GT)) {
                    Object val = con.getVal();
                    if (val instanceof Number) {
                        predicate = builder.gt(path, (Number) val);
                    } else if (val instanceof Date) {
                        predicate = builder.greaterThan(path, builder.literal((Date) val));
                    }
                }

                if (predicate != null) {
                    list.add(predicate);
                }
            }
        }
        query.where(list.toArray(new Predicate[list.size()]));
        query.select(root);

        TypedQuery<T> tq = entityManager.createQuery(query);

        List<T> content = tq.getResultList();

        return content;

    }

    @Override
    public Page<T> search(List<Condition> cons, Pageable pageable) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(domainClass);

        Root<T> root = query.from(domainClass);

        List<Predicate> list = new ArrayList<Predicate>();
        for (Condition con : cons) {
            if (con != null) {

                Predicate predicate = null;
                Path path = root.get(con.getName());
                if (con.getType().equals(Condition.Type.EQ)) {
                    predicate = builder.equal(path, con.getVal());
                } else if (con.getType().equals(Condition.Type.LIKE)) {
                    predicate = builder.like(path, con.getVal().toString());
                } else if (con.getType().equals(Condition.Type.LT)) {
                    Object val = con.getVal();
                    if (val instanceof Number) {
                        predicate = builder.lt(path, (Number) val);
                    } else if (val instanceof Date) {
                        predicate = builder.lessThan(path, builder.literal((Date) val));
                    }
                } else if (con.getType().equals(Condition.Type.GT)) {
                    Object val = con.getVal();
                    if (val instanceof Number) {
                        predicate = builder.gt(path, (Number) val);
                    } else if (val instanceof Date) {
                        predicate = builder.greaterThan(path, builder.literal((Date) val));
                    }
                }

                if (predicate != null) {
                    list.add(predicate);
                }
            }
        }

        query.where(list.toArray(new Predicate[list.size()]));
        query.select(root);
        TypedQuery<T> tq = entityManager.createQuery(query);

        tq.setFirstResult(pageable.getOffset());
        tq.setMaxResults(pageable.getPageSize());

        //Long total = em.createQuery(getCountQueryString(), Long.class).getSingleResult();
        Long total = getSearchCountQuery(list).getSingleResult();
        List<T> content = total > pageable.getOffset() ? tq.getResultList() : Collections.<T>emptyList();

        return new PageImpl<T>(content, pageable, total);
    }

    private TypedQuery<Long> getSearchCountQuery(List<Predicate> list) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);

        Root<T> root = query.from(domainClass);
        query.where(list.toArray(new Predicate[list.size()]));

        if (query.isDistinct()) {
            query.select(builder.countDistinct(root));
        } else {
            query.select(builder.count(root));
        }

        return entityManager.createQuery(query);
    }

//    @Override
//    public <R> List<R> search(String jpaSql, Class<R> domainClass) {
//    	
//    	Query query = entityManager.createQuery(jpaSql);
//    	return query.getResultList();
//    	
//    }
//
//    @Override
//    public <R> Page<R> search(String jpaSql, Class<R> domainClass, Pageable pageable) {
//    	return null;
//    }

    public <R> List<R> searchByNativeSQL(String nativeSql, Class<R> domainClass){
    	return searchByNativeSQL(nativeSql, (List<ParameterCondition>)null, domainClass);
    }

    public <R> Page<R> searchByNativeSQL(String nativeSQL, Class<R> domainClass, Pageable pageable) {
    	return searchByNativeSQL(nativeSQL, null, domainClass, pageable);
    }

    /**
	 * @ClassName: SearchAndPageRepositoryImpl :: getNativeCount
	 * @author: taoyf
	 * @date: 2017年4月18日 下午7:11:16
	 *
	 * @Description:
	 * @param sql
	 * @return          
	 */
	private Long getNativeCount(String sql, List<ParameterCondition> parameters) {
		
		String countSql = SqlHelp.getCountSql(sql);
		Query contentQuery = entityManager.createNativeQuery(countSql);
		buildParameters(contentQuery, parameters);
		
		Number number = (Number) contentQuery.getSingleResult();
		
		if(number == null){
			return 0l;
		}else{
			return number.longValue();
		}
		
	}
	
	private Long getNativeCount(String sql) {
		return getNativeCount(sql, null);
	}

    @Override
    public Page<Map> searchByNativeSQL(String nativeSQL, Pageable pageable, List<String> keyList) {
        if (!StringUtils.isBlank(nativeSQL)) {
            Query query = entityManager.createNativeQuery(nativeSQL);
            Query count=entityManager.createNativeQuery("select count(*) from ("+nativeSQL+")");
            Long total = Long.parseLong(count.getSingleResult().toString());//获取总数据行数
            query.setFirstResult(pageable.getOffset());//设置起始行
            query.setMaxResults(pageable.getPageSize());//设置最大查询结果数
            List reList = new ArrayList();//存放封装后的数据
            List<Object> content = total > pageable.getOffset() ? query.getResultList() : reList;
            //根据keyList键值封装数据，keyList键值必须与SQL里数量和顺序一致
            if (content != null && content.size() > 0) {
                for (Object obj : content) {
                    Object[] ary = (Object[]) obj;
                    Map<String, String> map = new HashMap<String, String>();
                    for (int i = 0; i < keyList.size(); i++) {
                        map.put(keyList.get(i), ary[i] != null ? ary[i].toString() : "");
                    }
                    reList.add(map);
                }
            }
            return new PageImpl<Map>(reList, pageable, total);
        } else {
            return new PageImpl<Map>(new ArrayList<Map>());
        }
    }

    @Override
    public Page<Map> searchByNativeSQL(String nativeSQL, Pageable pageable) {
    	 return searchByNativeSQL(nativeSQL, (List<ParameterCondition>) null, pageable);
    }
    
	@Override
	public List<Map> searchByNativeSQL(String nativeSQL) {
		return searchByNativeSQL(nativeSQL, (List<ParameterCondition>) null);
	}
    
	private Session getHibernateSession() {
        return (Session) entityManager.getDelegate();
    }
	
    @Override
    public List searchformSQL(String sql) {
        Session session = getHibernateSession();
        SQLQuery sqlQuery = session.createSQLQuery(sql);
        return sqlQuery.list();
    }
    
    public void buildParameters(Query query, List<ParameterCondition> parameters){
    	
    	if(parameters != null){
    		for(ParameterCondition param : parameters){
    			Object val = param.getVal();
    				
				if(val != null && val instanceof java.util.Date){
					query.setParameter(param.getName(), (java.util.Date)val, TemporalType.TIMESTAMP);
				}else{
					query.setParameter(param.getName(), val);
				}
    				
    		}
    	}
    }

	@Override
	public Page<Map> searchByNativeSQL(String nativeSQL, List<ParameterCondition> parameters, Pageable pageable) {
		Query query = entityManager.createNativeQuery(nativeSQL);
        query.unwrap(SQLQuery.class).setResultTransformer(ColToMapResultTransformer.INSTANCE);
        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        
        buildParameters(query, parameters);
        
        Long total = getNativeCount(nativeSQL, parameters);
        
        List<Map> content = total > pageable.getOffset() ? query.getResultList() : new ArrayList<Map>();
        return new PageImpl<Map>(content, pageable, total);
	}

	@Override
	public List<Map> searchByNativeSQL(String nativeSQL, List<ParameterCondition> parameters) {
		Query query = entityManager.createNativeQuery(nativeSQL);System.out.println("调用数据库");
        query.unwrap(SQLQuery.class).setResultTransformer(ColToMapResultTransformer.INSTANCE);
        
        buildParameters(query, parameters);
        
        return query.getResultList();
	}

	@Override
	public <R> Page<R> searchByNativeSQL(String nativeSQL, List<ParameterCondition> parameters, Class<R> domainClass,
			Pageable pageable) {
    	Query contentQuery = entityManager.createNativeQuery(nativeSQL);
    	contentQuery.setFirstResult(pageable.getOffset());
    	contentQuery.setMaxResults(pageable.getPageSize());
    	buildParameters(contentQuery, parameters);
    	
    	contentQuery.unwrap(SQLQuery.class).setResultTransformer(ColToMapResultTransformer.INSTANCE);

    	Long total = getNativeCount(nativeSQL, parameters);
    	
    	List<R> content = total > pageable.getOffset() ? contentQuery.getResultList() : new ArrayList<R>();
   
    	return new PageImpl<R>(content, pageable, total);
	}

	@Override
	public <R> List<R> searchByNativeSQL(String nativeSQL, List<ParameterCondition> parameters, Class<R> domainClass) {
	   	Query contentQuery = entityManager.createNativeQuery(nativeSQL);
    	contentQuery.unwrap(SQLQuery.class).setResultTransformer(ColToBeanResultTransformer.instance(domainClass));
    	buildParameters(contentQuery, parameters);
    	
    	return contentQuery.getResultList();
	}

    
}