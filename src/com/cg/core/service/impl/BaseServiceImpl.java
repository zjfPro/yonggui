package com.cg.core.service.impl;


import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cg.common.utils.Page;
import com.cg.common.utils.PageUtil;
import com.cg.core.dao.IBaseDao;
import com.cg.core.service.IBaseService;
 




@Service("baseService")
public class BaseServiceImpl<T> implements IBaseService<T>{

	@Resource(name = "baseDao")
	private IBaseDao<T> baseDao;
	
	
	public void removeSessionCache(Object obj) throws Exception {
		baseDao.getSessionFactory().getCurrentSession().evict(obj);
	}
	
	
	@Override
	public T save(T entity) throws Exception {
		// TODO Auto-generated method stub
		this.baseDao.save(entity);
		return entity;
	}

	@Override
	public T update(T entity) throws Exception {
		// TODO Auto-generated method stub
		this.baseDao.update(entity);
		return entity;
	}

	@Override
	public void updateForHql(String hql, Object[] params) throws Exception {
		// TODO Auto-generated method stub
		this.baseDao.updateForHql(hql, params);
	}

	@Override
	public void delete(T entity) throws Exception {
		// TODO Auto-generated method stub
		this.baseDao.delete(entity);
	}

	@Override
	public List<T> find(String hql) throws Exception {
		// TODO Auto-generated method stub
		return this.baseDao.find(hql);
	}

	@Override
	public T findEntity(Class<T> c, Serializable id) throws Exception {
		// TODO Auto-generated method stub
		return baseDao.findEntity(c, id);
	}

	@Override
	public T findEntity(String hql, Object[] obj) throws Exception {
		// TODO Auto-generated method stub
		return this.baseDao.findEntity(hql, obj);
	}

	@Override
	public T findEntity(String hql, List<Object> params) throws Exception {
		// TODO Auto-generated method stub
		return this.baseDao.findEntity(hql, params);
	}

	@Override
	public List<T> find(String hql, List<Object> params) {
		// TODO Auto-generated method stub
		return this.baseDao.find(hql, params);
	}

	@Override
	public List<T> find(String hql, Object[] param) {
		// TODO Auto-generated method stub
		return this.baseDao.find(hql, param);
	}
	@Override
	public List<T> find(String hql, Object param) {
		// TODO Auto-generated method stub
		return this.baseDao.find(hql, param);
	}


	@Override
	public PageUtil<T> find(String hql, List<Object> params,
			PageUtil<T> pageUtil) {
		// TODO Auto-generated method stub
		return this.baseDao.find(hql, params, pageUtil);
	}
	
 
	@Override
	public PageUtil<T> find(String hql, List<Object> params,
			PageUtil<T> pageUtil,String countHQL) {
		// TODO Auto-generated method stub
		return this.baseDao.find(hql, params, pageUtil,countHQL);
	}
	

	public void saveOrUpdate(T entity){
		this.baseDao.saveOrUpdate(entity);
	};
	
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public int executeHQL(String hql, Object[] params) {
		int i = -1;
		try {
			i=	this.baseDao.executeHQL(hql, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}
	
	
	
	
	
	

	public List<T> find(String queryString,Page page) {
		try {
			return baseDao.find(queryString,page);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public List<T> find(String queryString, Object param,Page page) {
		try {
			return baseDao.find(queryString, param, page);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<T> find(String queryString, Object[] params,Page page) {
		try {
			return baseDao.find(queryString, params, page);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<T> find(String queryString, List<Object> params,Page page) {
		try {
			return baseDao.find(queryString, params, page);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public List<T> findSQL(String hql, Object[] param) {
		try {
			return baseDao.findSQL(hql, param);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public Long count(String hql, List<Object> params) throws Exception {
		return baseDao.count(hql, params);
	}


	@Override
	public T updateByAdmin(T entity, T adminLog) throws Exception {
		this.baseDao.update(entity);
		this.baseDao.save(adminLog);
		return entity;
	}


	@Override
	public T saveByAdmin(T entity, T adminLog) throws Exception {
		this.baseDao.save(entity);
		this.baseDao.save(adminLog);
		return entity;
	}


	@Override
	public void deleteByAdmin(T entity, T adminLog) throws Exception {
		this.baseDao.delete(entity);
		this.baseDao.save(adminLog);
	}


	/**
	 * 批量保存对象
	 */
	@Override
	public void saveTransactional(T... entitys) throws Exception {
		for (T t : entitys) {
			this.baseDao.save(t);
		}
	}

	/**
	 * 批量更新对象
	 */
	@Override
	public void updateTransactional(T... entitys) throws Exception {
		for (T t : entitys) {
			this.baseDao.update(t);
		}
	}

	/**
	 * 批量删除对象
	 */
	@Override
	public void deleteTransactional(T... entitys) throws Exception {
		for (T t : entitys) {
			this.baseDao.delete(t);
		}
	}

	/**
	 * 批量执行事务操作
	 */
	@Override
	public void newTransactional(T[] saveEntitys, T[] updateEntitys,
			T[] deleteEntitys) throws Exception {
		for (T t : saveEntitys) {
			this.baseDao.save(t);
		}
		for (T t : updateEntitys) {
			this.baseDao.update(t);
		}
		for (T t : deleteEntitys) {
			this.baseDao.delete(t);
		}
	}

	/**
	 * 查找数量限制 
	 * @param firstResult
	 * @param maxResults
	 * 
	 * */
	@Override
	public List<T> find(String queryString, List<Object> params,
			Integer firstResult, Integer maxResults) throws Exception {
		return this.baseDao.find(queryString, params, firstResult, maxResults);
	}

	
}
 
