package com.cg.core.dao;


import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;

import com.cg.common.utils.Page;
import com.cg.common.utils.PageUtil;

/**
 * 基础数据库操作类
 * @author Hua
 *
 */
public interface IBaseDao<T> {
	public SessionFactory getSessionFactory()throws Exception;
	/**
	 * 增
	 * @throws Exception 
	 */
	public T save(T entity) throws Exception;
	
	
	/**
	 * 改
	 * @param entity
	 * @throw Exception
	 */
	public T update(T entity) throws Exception;
	
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
	/**
	 * 条件查询集合
	 * @param hql
	 * @param param
	 * @return
	 */
	public List<Object> findReturnObject(String hql,Object[] param);
	
	/**
	 * 条件查询集合
	 * @param hql
	 * @param param
	 * @return
	 */
	public List<Object> findReturnObject(String hql,List<Object> params);
	
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
	 * 分页查询
	 * @param hql
	 * @param params
	 * @param page
	 * @param rows
	 * 
	 * @return
	 */
	public PageUtil<Object> findReturnObject(String hql , List<Object> params,PageUtil<Object> pageUtil);
	
	/**
	 * 总记录数类
	 * @param hql
	 * @param obj
	 * 
	 * @return
	 */
	public Long count(String hql,Object[] obj) throws Exception;
	
	/**
	 * 总记录数类
	 * @param hql
	 * @param obj
	 * 
	 * @return
	 */
	public Long count(String hql,List<Object> params) throws Exception;
	
	/**
	 * 保存或修改
	 */
	public void saveOrUpdate(T entity);
	
	public int executeHQL(String hql,  Object[] params) throws Exception;
	
	public int executeHQL(String hql,  List<Object> params) throws Exception;
	
	
	/**
	 * 根据查询语句查询数据库并返回查询结果所包含的业务对象集合。
	 * 
	 * @param queryString
	 *            指定查询语句
	 * @return 返回查询结果包含的业务对象集合
	 * 
	 * @throws Exception
	 *             查询失败时抛出DAO异常
	 */
	public List<T> find(String queryString,Page page) throws Exception;
	
	/**
	 * 根据带一个参数的查询语句查询数据库并返回查询结果所包含的业务对象集合。
	 * 
	 * @param queryString
	 *            指定查询语句
	 * @param param
	 *            指定所带参数
	 * @return 返回查询结果包含的业务对象集合
	 * 
	 * @throws Exception
	 *             查询失败时抛出DAO异常
	 */
	public List<T> find(String queryString, Object param,Page page) throws Exception;

	/**
	 * 根据带多个参数的查询语句查询数据库并返回查询结果所包含的业务对象集合。
	 * 
	 * @param queryString
	 *            指定查询语句
	 * @param params
	 *            指定参数数组
	 * @return 返回查询结果包含的业务对象集合
	 * 
	 * @throws Exception
	 *             查询失败时抛出DAO异常
	 */
	public List<T> find(String queryString, List<Object> params,Page page) throws Exception;
	public List<T> find(String queryString, Object[] params,Page page) throws Exception;
	public List<T> findSQL(String sql, Object[] param);
	
	/**
	 * 按照sql分页查询
	 * @param sql
	 * @param params
	 * @param pageUtil
	 * @return
	 * @throws Exception
	 */
	public PageUtil<T> findSQL(String find_data_sql,String find_count_sql,List<Object> params,PageUtil<T> pageUtil) throws Exception;
	
	/**
	 * 计算总数
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Long findSQLCount(String sql,List<Object> params) throws Exception;
	
	public List<T> find(String queryString, List<Object> params,Integer firstResult,Integer maxResults) throws Exception;
	
}
