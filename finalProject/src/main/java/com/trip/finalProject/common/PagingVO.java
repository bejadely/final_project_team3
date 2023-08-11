package com.trip.finalProject.common;

import lombok.Getter;

@Getter
public class PagingVO {
	private final static int defaultVal = 10;
	
	private int totalData;
	
	private int nowPage;
	private int cntPage = 5;
	private int startPage;
	private int endPage;
	
	private int cntPerPage;
	private int lastPage;
	private int start;
	private int end;
	
	public PagingVO(int totalData, int nowPage, int cntPerPage) {
		this.totalData = totalData;
		this.nowPage = nowPage;
		this.cntPerPage = cntPerPage;
		calcLastPage();
		calcStartEndPage();
		calcStartEnd();
		this.startPage = ((nowPage - 1) / cntPage) * cntPage + 1 ;
		this.endPage = startPage + (cntPage-1);
		if(this.endPage > this.lastPage) {
			this.endPage = this.lastPage;
		}
	}
	
	public PagingVO(int totalData, int nowPage) {
		this(totalData, nowPage, defaultVal);
	}
	
	private void calcLastPage() {
		this.lastPage = (int)Math.ceil((double)this.totalData / (double)this.cntPerPage);
	}
	
	private void calcStartEndPage() {
		this.endPage = (int)Math.ceil((double)this.nowPage / (double)this.cntPage) * this.cntPage;
		if(this.endPage > this.lastPage) {
			this.endPage = this.lastPage;
		}
		
		this.startPage = this.endPage - this.cntPage + 1;
		if(this.startPage < 1) {
			this.startPage = 1;
		}
	}
	
	private void calcStartEnd() {
		this.start = ((this.nowPage - 1) * this.cntPerPage) + 1;
		this.end = this.nowPage * this.cntPerPage;
		if(this.end > this.totalData) {
			this.end = this.totalData;
		}
	}
}
