package com.cg.common.utils;

import java.util.List;

/**
 * 
 * @author PanHongBin     2011-12-01
 *
 */
public class PageView {

	/**分页数据**/
	private List records;
	/**页码开始索引和结束索引**/
	private PageIndex pageIndex; 
	/**在分页条上每次显示的页面数**/
	private int pageBarSize = 9;      //在分页条上每次显示的页面数;默认设为5
	/**当前页**/
	private int currentPage;
	/**每页显示记录数**/
	private int maxResult; 
	/**总记录数**/
	private long totalRecord;
	/**总页数**/ 
	private long totalPage;
	
	/** 上一页 **/
	private long previousPageNo;
	/** 下一页 **/
	private long nextPageNo;
	
	public PageView(){}
	
	public PageView(int currentpage,int maxResult,PagerModel pagerModel) {
		this.currentPage=currentpage;
		this.maxResult=maxResult;
		setTotalRecord(pagerModel.getTotal());
		setRecords(pagerModel.getData());
	}
	public PageView(int currentpage,int maxResult,List result,long totalRecord) {
		this.currentPage=currentpage;
		this.maxResult=maxResult;
		setTotalRecord(totalRecord);
		setRecords(result);
	}
	public PageView(int pageBarSize,int currentpage,int maxResult,PagerModel pagerModel) {
		this.pageBarSize=pageBarSize;
		this.currentPage=currentpage;
		this.maxResult=maxResult;
		setTotalRecord(pagerModel.getTotal());
		setRecords(pagerModel.getData());
	}
	public PageView(int currentpage,int maxResult,long count) {
		this.currentPage=currentpage;
		this.maxResult=maxResult;
		setTotalRecord(count);
	}
	public List getRecords() {
		return records;
	}
	public void setRecords(List records) {
		this.records = records;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentpage) {
		this.currentPage = currentpage;
	}
	public int getMaxResult() {
		return maxResult;
	}
	public void setMaxResult(int maxResult) {
		this.maxResult = maxResult;
	}
	public long getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(long totalrecord) {
		this.totalRecord = totalrecord;
		setTotalPage((this.totalRecord%this.maxResult==0)?this.totalRecord/this.maxResult:this.totalRecord/this.maxResult+1);
	}
	public long getTotalPage() {
		return totalRecord%maxResult == 0 ? totalRecord/maxResult : totalRecord/maxResult+1;
	}
	public void setTotalPage(long totalpage) {
		this.totalPage = totalpage;
	}
	public PageIndex getPageIndex() {
		if(this.pageIndex==null){
			setPageIndex(PageIndex.getPageIndex(this.pageBarSize,this.currentPage, this.totalPage));
		}
		return this.pageIndex;
	}
	public void setPageIndex(PageIndex pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getPageBarSize() {
		return pageBarSize;
	}
	public void setPageBarSize(int pageBarSize) {
		this.pageBarSize = pageBarSize;
	}
	public long getPreviousPageNo() {
		return (this.currentPage-1)<1?1:this.currentPage-1;
	}
	public void setPreviousPageNo(long previousPageNo) {
		this.previousPageNo = previousPageNo;
	}
	public long getNextPageNo() {
		return (this.currentPage+1) > this.totalPage ? this.totalPage : this.currentPage+1;
	}
	public void setNextPageNo(long nextPageNo) {
		this.nextPageNo = nextPageNo;
	}

	@Override
	public String toString() {
		return "PageView [records=" + records + ", pageIndex=" + pageIndex
				+ ", pageBarSize=" + pageBarSize + ", currentPage="
				+ currentPage + ", maxResult=" + maxResult + ", totalRecord="
				+ totalRecord + ", totalPage=" + totalPage
				+ ", previousPageNo=" + previousPageNo + ", nextPageNo="
				+ nextPageNo + "]";
	}
	
}
