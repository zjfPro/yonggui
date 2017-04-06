package com.cg.core.dao.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import org.hibernate.NonUniqueObjectException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.cg.common.utils.Page;
import com.cg.common.utils.PageUtil;
import com.cg.core.dao.IBaseDao;

 
 
@Repository("baseDao")
@SuppressWarnings("all")
public class BaseDaoImpl<T> implements IBaseDao<T> {

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	// 获取session
	protected Session getcurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public T save(T entity) throws Exception {
		// TODO Auto-generated method stub
		try {
			this.getcurrentSession().save(entity);
			return entity;
		} catch (DataAccessException e) {
			throw new Exception("保存" + entity.getClass().getName() + "实例失败", e);
		}

	}

	@Override
	public T update(T entity) throws Exception {
		// TODO Auto-generated method stub
		try {
			this.getcurrentSession().update(entity);
			return entity;
		}catch (NonUniqueObjectException e) {
			this.getcurrentSession().merge(entity);
			return entity;
		} catch (DataAccessException e) {
			// TODO: handle exception
			throw new Exception("更新" + entity.getClass().getName() + "实例失败", e);
		}
	}

	@Override
	public void updateForHql(String hql, Object[] params) throws Exception {
		// TODO Auto-generated method stub

		try {
			Query q = this.getcurrentSession().createQuery(hql);
			if (params != null && params.length > 0) {
				for (int i = 0; i < params.length; i++) {
					q.setParameter(i, params[i]);
				}
			}
			q.executeUpdate();
		} catch (DataAccessException e) {
			// TODO: handle exception
			throw new Exception("update error for updateForHql", e);
		}
	}

	@Override
	public void delete(T entity) throws Exception {
		// TODO Auto-generated method stub
		try {
			this.getcurrentSession().delete(entity);
		} catch (DataAccessException e) {
			// TODO: handle exception
			throw new Exception("删除" + entity.getClass().getName() + "实例失败", e);
		}
	}

	@Override
	public List<T> find(String hql) throws Exception {
		// TODO Auto-generated method stub
		return this.getcurrentSession().createQuery(hql).list();
	}

	@Override
	public T findEntity(Class<T> c, Serializable id) throws Exception {
		// TODO Auto-generated method stub
		return (T) this.getcurrentSession().get(c, id);
	}

	@Override
	public T findEntity(String hql, Object[] obj) throws Exception {
		// TODO Auto-generated method stub
		List<T> list = this.find(hql, obj);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public T findEntity(String hql, List<Object> params) throws Exception {
		// TODO Auto-generated method stub
		List<T> list = this.find(hql, params);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<T> find(String hql, List<Object> params) {
		// TODO Auto-generated method stub
		Query q = this.getcurrentSession().createQuery(hql);
		if (params != null && params.size() > 0) {
			for (int i = 0; i < params.size(); i++) {
				q.setParameter(i, params.get(i));
			}
		}
		return q.list();
	}
	
	@Override
	public PageUtil<T> find(String hql, List<Object> params,
			PageUtil<T> pageUtil) {
		// TODO Auto-generated method stub
		try {
			if (pageUtil==null || pageUtil.getPage()==null) {
				pageUtil = new PageUtil();
			}
			Query q = this.getcurrentSession().createQuery(hql);
			if (params != null && params.size() > 0) {
				for (int i = 0; i < params.size(); i++) {
					q.setParameter(i, params.get(i));
				}
			}
			Long count = this.count(hql, params);
			if (count==null) {
				System.err.println(hql+" 翻页查询错误");
			}
			pageUtil.setTotal(count);
			int startpoint = pageUtil.getStartPoint();
			pageUtil.setDatas(q.setFirstResult(startpoint)
					.setMaxResults(pageUtil.getRows()).list());
			return pageUtil;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public PageUtil<T> find(String hql, List<Object> params,
			PageUtil<T> pageUtil,String countHQL) {
		// TODO Auto-generated method stub
		try {
			if (pageUtil==null || pageUtil.getPage()==null) {
				pageUtil = new PageUtil();
			}
			Query q = this.getcurrentSession().createQuery(hql);
			if (params != null && params.size() > 0) {
				for (int i = 0; i < params.size(); i++) {
					q.setParameter(i, params.get(i));
				}
			}
			 
			pageUtil.setTotal(this.count(countHQL, params));
 
			pageUtil.setDatas(q.setFirstResult(pageUtil.getStartPoint())
					.setMaxResults(pageUtil.getRows()).list());
			return pageUtil;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	
	
	@Override
	public Long count(String hql, Object[] obj) throws Exception {
		// TODO Auto-generated method stub
		String countHQL = "select count(*) ";
		if (hql.indexOf("count") >1 ) {
			countHQL="";
		}
		Query q = this.getcurrentSession()
				.createQuery(countHQL + hql);
		if (obj != null && obj.length > 0) {
			for (int i = 0; i < obj.length; i++) {
				q.setParameter(i, obj[i]);
			}
		}
		return (Long) q.uniqueResult();
	}

	@Override
	public Long count(String hql, List<Object> params) throws Exception {
		
		String countHQL = "select count(*) ";
		if (hql.indexOf("count") >1 ) {
			countHQL="";
		}
	 
		Query q = this.getcurrentSession().createQuery(
				countHQL+ hql);
		if (params != null && params.size() > 0) {
			for (int i = 0; i < params.size(); i++) {
				q.setParameter(i, params.get(i));
			}
		}
		return (Long) q.uniqueResult();
	}

	@Override
	public List<T> find(String hql, Object[] param) {
		// TODO Auto-generated method stub
		Query q = this.getcurrentSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		return q.list();
	}

	public void saveOrUpdate(T entity) {
		this.getcurrentSession().saveOrUpdate(entity);
	}

	@Override
	public List<Object> findReturnObject(String hql, Object[] param) {
		// TODO Auto-generated method stub
		Query q = this.getcurrentSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		return q.list();
	}

	@Override
	public List<Object> findReturnObject(String hql, List<Object> params) {
		// TODO Auto-generated method stub
		Query q = this.getcurrentSession().createQuery(hql);
		if (params != null && params.size() > 0) {
			for (int i = 0; i < params.size(); i++) {
				q.setParameter(i, params.get(i));
			}
		}
		return q.list();
	}

	@Override
	public PageUtil<Object> findReturnObject(String hql, List<Object> params,
			PageUtil<Object> pageUtil) {
		// TODO Auto-generated method stub
		try {
			Query q = this.getcurrentSession().createQuery(hql);
			if (params != null && params.size() > 0) {
				for (int i = 0; i < params.size(); i++) {
					q.setParameter(i, params.get(i));
				}
			}
			pageUtil.setTotal(this.count(hql, params));
			pageUtil.setDatas(q.setFirstResult(pageUtil.getStartPoint())
					.setMaxResults(pageUtil.getRows()).list());
			return pageUtil;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public int executeHQL(String hql, Object[] params) throws Exception {
		Query query = this.getcurrentSession().createQuery(hql);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		return query.executeUpdate();
	}
	
	public int executeHQL(String hql, List<Object> params) throws Exception {
		Query query = this.getcurrentSession().createQuery(hql);
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i, params.get(i));
		}
		return query.executeUpdate();
	}

	@Override
	public List<T> find(String hql, Object param) {
		Query q = this.getcurrentSession().createQuery(hql);
		q.setParameter(0, param);
		return q.list();
	}
	


	
	
	
	
	
	
	
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.squall.dao.IGenericDao#find(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<T> find(final String queryString, final Page page)
			throws Exception {
		try {
			Query query = this.getcurrentSession().createQuery(queryString);
			query.setMaxResults(page.getPageSize());
			query.setFirstResult(page.getPage());
			List<T> list = query.list();
			return list;
		} catch (DataAccessException e) {
			throw new Exception("执行查询 " + queryString + " 失败", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.squall.dao.IGenericDao#find(java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public List<T> find(final String queryString, final Object param,
			final Page page) throws Exception {
		try {
			Query query = this.getcurrentSession().createQuery(queryString);
			query.setParameter(0, param);
			query.setMaxResults(page.getPageSize());
			query.setFirstResult(page.getPage());
			List<T> list = query.list();
			return list;
		} catch (DataAccessException e) {
			throw new Exception("执行参数�?" + param + " 的查�?" + queryString
					+ " 失败", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.squall.dao.IGenericDao#find(java.lang.String,
	 * java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	public List<T> find(final String queryString, final Object[] params,
			final Page page) throws Exception {
		try {
			Query query = this.getcurrentSession().createQuery(queryString);
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
			query.setMaxResults(page.getPageSize());
			query.setFirstResult(page.getPage());
			return query.list();
		} catch (DataAccessException e) {
			StringBuffer paramString = new StringBuffer("");
			for (int i = 0; i < params.length; i++) {
				paramString.append(params[i]);
				paramString.append(" ");
			}
			throw new Exception("执行参数�?" + paramString + "的查�?" + queryString
					+ " 失败", e);
		}
	}

	
	@SuppressWarnings("unchecked")
	public List<T> find(final String queryString, final List<Object> params,
			final Page page) throws Exception {
		try {
			Query query = this.getcurrentSession().createQuery(queryString);
			for (int i = 0; i < params.size(); i++) {
				query.setParameter(i,params.get(i));
			}
			
			query.setMaxResults(page.getPageSize());
			query.setFirstResult(page.getPage());
			return query.list();
		} catch (DataAccessException e) {
			StringBuffer paramString = new StringBuffer("");
			for (int i = 0; i < params.size(); i++) {
				paramString.append(params.get(i));
				paramString.append(" ");
			}
			throw new Exception("执行参数�?" + paramString + "的查�?" + queryString
					+ " 失败", e);
		}
	}
	
	
	@Override
	public List<T> findSQL(String sql, Object[] param) {
		Query q = this.getcurrentSession().createSQLQuery(sql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		return q.list();
	}

	@Override
	public PageUtil<T> findSQL(String sql,String find_count_sql, List<Object> params,
			PageUtil<T> pageUtil) throws Exception {
		SQLQuery q = this.getcurrentSession().createSQLQuery(sql);
		if(params!=null){
			for (int i = 0; i < params.size(); i++) {
				q.setParameter(i, params.get(i));
			}
		}
		pageUtil.setTotal(this.findSQLCount(find_count_sql, params));
		pageUtil.setDatas(q.setFirstResult(pageUtil.getStartPoint())
				.setMaxResults(pageUtil.getRows()).list());
		return pageUtil;
	}

	@Override
	public Long findSQLCount(String sql, List<Object> params)
			throws Exception {
		
		try {
			SQLQuery q = this.getcurrentSession().createSQLQuery(sql);
			if(params!=null){
				for (int i = 0; i < params.size(); i++) {
					q.setParameter(i, params.get(i));
				}
			}
			for (Object object : q.list()) {
				System.out.println(object);
			}
			return ((BigInteger) q.uniqueResult()).longValue();
		} catch (Exception e) {
			e.printStackTrace();
			return 0l;
		}
		
		
		
	}

	@Override
	public List<T> find(String hql,List<Object> params,Integer firstResult,Integer maxResults) throws Exception {
		// TODO Auto-generated method stub
		Query q = this.getcurrentSession().createQuery(hql);
		
		if (params != null && params.size() > 0) {
			for (int i = 0; i < params.size(); i++) {
				q.setParameter(i, params.get(i));
			}
		}
		q.setFirstResult(firstResult);
		q.setMaxResults(maxResults);
		return q.list();
		
	} 
	
	
	
}
