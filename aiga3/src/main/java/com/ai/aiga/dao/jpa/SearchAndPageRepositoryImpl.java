package com.ai.aiga.dao.jpa;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.*;

public class SearchAndPageRepositoryImpl<T, ID extends Serializable> extends
        SimpleJpaRepository<T, ID> implements SearchAndPageRepository<T, ID> {

    private Class<T> domainClass;

    protected final EntityManager entityManager;

    //public SearchAndPageRepositoryImpl(){
    //	super((Class<T>)null, null);
    //}

    public SearchAndPageRepositoryImpl(JpaEntityInformation entityInformation,
                                       EntityManager entityManager) {
        super(entityInformation, entityManager);
        domainClass = entityInformation.getJavaType();
        // Keep the EntityManager around to used from the newly introduced methods.
        this.entityManager = entityManager;
    }

    // There are two constructors to choose from, either can be used.
    public SearchAndPageRepositoryImpl(Class<T> domainClass,
                                       EntityManager entityManager) {
        super(domainClass, entityManager);

        // This is the recommended method for accessing inherited class
        // dependencies.
        this.domainClass = domainClass;
        this.entityManager = entityManager;
    }

    @Override
    public List<T> search() {
        return null;
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

    @Override
    public Page<T> search(Pageable pageable) {
        System.out.println("search(Pageable pageable)");
        return null;
    }

    @Override
    public Page<T> search(List<String> sql, List<Condition> cons,
                          Pageable pageable) {

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

        Long total = getSearchCountQuery(list).getSingleResult();
        List<T> content = total > pageable.getOffset() ? tq.getResultList() : Collections.<T>emptyList();

        return new PageImpl<T>(content, pageable, total);

    }

    @Override
    public Page<T> search(String sql, Pageable pageable) {

        if (StringUtils.isBlank(sql)) {
            return new PageImpl<T>(new ArrayList<T>());
        } else {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(domainClass);


            Root<T> root = query.from(domainClass);


            List<Predicate> list = new ArrayList<Predicate>();

            query.where(list.toArray(new Predicate[list.size()]));
            query.select(root);

            TypedQuery<T> tq = entityManager.createQuery(query);

            tq.setFirstResult(pageable.getOffset());
            tq.setMaxResults(pageable.getPageSize());

            Long total = getSearchCountQuery(list).getSingleResult();
            List<T> content = total > pageable.getOffset() ? tq.getResultList() : Collections.<T>emptyList();

            return new PageImpl<T>(content, pageable, total);

        }

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


    public Page<T> searchBySql(String sql, Class<T> domainClass, Pageable pageable) {
        // TODO Auto-generated method stub
        // TODO Auto-generated method stub
        Query query = entityManager.createNativeQuery(sql, domainClass);
        List<T> list = query.getResultList();

        entityManager.close();
        List<T> list2 = null;
        //根据分页条数截取出数据
        int startIndex = pageable.getPageNumber() * pageable.getPageSize();
        int endIndex = (pageable.getPageNumber() + 1) * pageable.getPageSize();

        if (list.size() >= endIndex) {
            list2 = list.subList(startIndex, startIndex + pageable.getPageSize());
        } else {
            list2 = list.subList(startIndex, list.size());
        }
        return new PageImpl<T>(list2, pageable, list.size());
    }

    public Session getHibernateSession() {
        return (Session) entityManager.getDelegate();
    }

    @Override
    public List searchformSQL(String sql) {
        Session session = getHibernateSession();
        SQLQuery sqlQuery = session.createSQLQuery(sql);
        return sqlQuery.list();
    }

    /**
     * 根据原生SQL分页查询数据，并根据keyList的属性封装成键值对数据
     *
     * @param nativeSQL
     * @param pageable
     * @param keyList
     * @return
     */
    @Override
    public Page<T> searchByNativeSQL(String nativeSQL, Pageable pageable, List<String> keyList) {
        if (!StringUtils.isBlank(nativeSQL)) {
            Query query = entityManager.createNativeQuery(nativeSQL);
            Long total = (long) query.getResultList().size();//获取总数据行数
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
            return new PageImpl<T>(reList, pageable, total);
        } else {
            return new PageImpl<T>(new ArrayList<T>());
        }
    }

    /**
     * 批量保存
     * @param list
     */
    @Override
    public void saveList(List<Object> list){
        if (list != null&&list.size()>0) {
            for (Object obj:list){
                this.entityManager.persist(obj);
            }
            this.entityManager.flush();
            this.entityManager.clear();
        }
    }
}