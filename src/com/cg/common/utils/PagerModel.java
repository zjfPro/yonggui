package com.cg.common.utils;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@SuppressWarnings( { "unchecked", "serial" })
public class PagerModel implements Serializable {

	private int total;
	private List data;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List getData() {
		return data == null ? Collections.emptyList() : data;
	}

	public void setData(List data) {
		this.data = data;
	}
}
