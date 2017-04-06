package com.cg.common.utils;

import java.util.ArrayList;
import java.util.List;


public class Page {
private int page = 0; //开始位置
private int pageSize = 4; //页数大小
private String data;

 
public int getPage() {
	return page;
}
public void setPage(int page) {
	this.page = page;
}
public int getPageSize() {
	return pageSize;
}
public void setPageSize(int pageSize) {
	this.pageSize = pageSize;
}
public String getData() {
	return data;
}
public void setData(String d) {
	this.data = d;
}




}
