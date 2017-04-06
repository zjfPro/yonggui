package com.cg.common.utils;

import java.util.List;

public class PageUtil<T> {

	private Integer rows=20;//每页的记录数
	private Long total;//总记录数
	private Integer page=0;//当前页数
	private List<T> datas;//数据
	private Integer totalPages;
 

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	/**
	 * 指针开始的位置
	 * @return
	 */
	public int getStartPoint(){
		if(page <= 0){
			page = 1;
		}else if(page > this.getTotalPages()){
			page = -1;
		}
		return (page - 1)*rows;
	}
	
	/**
	 * 求总页数
	 * @return
	 */
	public Integer getTotalPages() {
		int mod = 0;
		
		try {
			mod=(int) (total%rows);
			mod=(int) (mod == 0 ? total/rows : total/rows+1);
		} catch (Exception e) {
		}
		return mod;
	}
	
	/**
	 * 是否有下一页
	 * @return
	 */
	public boolean isHaveNext(){
		int next = this.getPage()+1;
		if(next <= this.getTotalPages()){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 是否有上一页
	 * @return
	 */
	public boolean isHavePre(){
		int next = this.getPage()-1;
		if(next >= 1){
			return true;
		}else{
			return false;
		}
	}
	
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	public Long getTotal() {
		return total;
	}
	public List<T> getDatas() {
		return datas;
	}
	public void setDatas(List<T> datas) {
		this.datas = datas;
	}
	
	public void setTotal(Long total) {
		this.total = total;
	}
	
	public Integer getPage(){
		return this.page < 1 ? 1 : this.page;
	}
	
	public void setPage(Integer page) {
		if(page < 1)this.page = 1;
		else this.page = page;
		
	}
	
}
