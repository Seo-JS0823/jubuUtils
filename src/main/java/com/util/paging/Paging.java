package com.util.paging;

import java.util.List;

public class Paging<T> {

	private List<T> response;
	private int totalRecord;
	
	public Paging(int totalRecord) {
		this.totalRecord = totalRecord;
	}
	
	public int getLimit(int nowPage) {
		return (nowPage - 1) * 10;
	}
	
	public int getTotalPage() {
		return (int) Math.ceil((double) this.totalRecord / 10);
	}
	
	public void setResponse(List<T> response) {
		this.response = response;
	}
	
	public List<T> getResponse() {
		return this.response;
	}
}
