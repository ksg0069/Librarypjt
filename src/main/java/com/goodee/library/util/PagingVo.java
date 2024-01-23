package com.goodee.library.util;

public class PagingVo {
	
	private int totalCount;
	private int startPage; //페이지바
	private int endPage;   //페이지바
	private boolean prev;
	private boolean next;
	private int pageSize = 10;
	private int page_no = 1;
	private int limit_pageNo; //화면에 몇개의 row 가져올까
	
	
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		// 페이징 계산
		calcData();
	}
	
	private void calcData() {
	   
		limit_pageNo = (page_no-1)*pageSize; //한 화면에 정보 몇개 10
		
		// 페이지바에 페이지 10개
		endPage = (int)(Math.ceil(page_no/(double)pageSize) * pageSize);
		// 페이지바에 페이지 10개
		startPage = (endPage-pageSize)+ 1;
		//한 화면에 정보 몇개 10
		int tempEndPage = (int)(Math.ceil(totalCount/(double)pageSize)); 
		
		if(endPage > tempEndPage) {
			endPage = tempEndPage;
		}
		prev = startPage == 1? false : true;
		next = endPage * pageSize >= totalCount ? false: true; //한화면에 정보 몇개 10
		

	}
	
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public boolean isPrev() {
		return prev;
	}
	public void setPrev(boolean prev) {
		this.prev = prev;
	}
	public boolean isNext() {
		return next;
	}
	public void setNext(boolean next) {
		this.next = next;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPage_no() {
		return page_no;
	}
	public void setPage_no(int page_no) {
		this.page_no = page_no;
	}
	public int getLimit_pageNo() {
		return limit_pageNo;
	}
	public void setLimit_pageNo(int limit_pageNo) {
		this.limit_pageNo = limit_pageNo;
	}
	
	

}
