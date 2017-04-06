package com.cg.core.service;


import java.io.Serializable;
import java.util.List;

import com.cg.common.utils.Page;
import com.cg.common.utils.PageUtil;

public interface IBaseService<T> {

	/**
	 * 增
	 * @throws Exception 
	 */
	public T save(T entity) throws Exception;
	public T saveByAdmin(T entity,T adminLog) throws Exception;
	/**
	 * 改
	 * @param entity
	 * @throw Exception
	 */
	public T update(T entity) throws Exception;
 
	public T updateByAdmin(T entity,T adminLog) throws Exception;
	
	/**
	 * 通过HQL自定义更新数据库
	 * @param hql
	 * @param params
	 * @throw Exception
	 */
	public void updateForHql(String hql , Object[] params) throws Exception;
	
	/**
	 * 删
	 * @param entity
	 * @throw Exception
	 */
	public void delete(T entity) throws Exception;
	public void deleteByAdmin(T entity,T adminLog) throws Exception;
	/**
	 * 根据hql查询集合
	 * @param hql
	 * @return
	 */
	public List<T> find(String hql) throws Exception;
	
	/**
	 * 根据ID获取一个对象
	 * @param Class
	 * @param id
	 * 
	 * return
	 */
	public T findEntity(Class<T> c, Serializable id) throws Exception;
	
	/**
	 * 条件获取一个对象
	 * @param hql
	 * @param obj
	 */
	public T findEntity(String hql,Object[] obj) throws Exception;
	
	/**
	 * 获取一个对象
	 * @param hql
	 * @param params
	 * 
	 * @return
	 */
	public T findEntity(String hql,List<Object> params) throws Exception;
	
	/**
	 * 条件查询集合
	 * @param hql
	 * @param param
	 * 
	 * @return
	 */
	public List<T> find(String hql,List<Object> params);
	
	/**
	 * 条件查询集合
	 * @param hql
	 * @param param
	 * @return
	 */
	public List<T> find(String hql,Object[] param);
	public List<T> find(String hql,Object param);
	
	public List<T> findSQL(String sql,Object[] param);
	
	/**
	 * 分页查询
	 * @param hql
	 * @param params
	 * @param page
	 * @param rows
	 * 
	 * @return
	 */
	public PageUtil<T> find(String hql , List<Object> params,PageUtil<T> pageUtil);
	public PageUtil<T> find(String hql , List<Object> params,PageUtil<T> pageUtil,String countHQL);
	/**
	 * 保存或修改
	 */
	public void saveOrUpdate(T entity);
	
	public int executeHQL( String hql,  Object[] params);
	
	
	public List<T> find(String queryString,  List<Object> params ,Page page);
	public List<T> find(String queryString, Object param,Page page);
	public List<T> find(String queryString, Object[] params,Page page) ;
	public List<T> find(String queryString,Page page);
	
	public Long count(String hql, List<Object> params) throws Exception;
	
	
	
	//-----------------事务方法-----------------
	/**
	 * 批量保存对象
	 * @param entitys
	 * @throws Exception
	 */
	public void saveTransactional(T...entitys) throws Exception;
	
	/**
	 * 批量更新对象
	 * @param entitys
	 * @throws Exception
	 */
	public void updateTransactional(T...entitys) throws Exception;
	/**
	 * 批量删除对象
	 * @param entitys
	 * @throws Exception
	 */
	public void deleteTransactional(T...entitys) throws Exception;
	
	
	/**
	 * 批量执行事务操作
	 * @param entitys
	 * @throws Exception
	 */
	public void newTransactional(T[] saveEntitys,T[] updateEntitys,T[] deleteEntitys) throws Exception;
	
	
	/**
	 * 查找数量限制 
	 * @param firstResult 
	 * @param maxResults 返回最大结果集数量
	 * 
	 * */
	public List<T> find(String queryString,  List<Object> params ,Integer firstResult,Integer maxResults) throws Exception;
}
