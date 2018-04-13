package com.agonzales.SistemaEscolar.util;

import org.springframework.data.domain.Sort;

public class PaginacionDTO {

	private Integer sEcho;
	private Integer iColumns;
	private Integer iDisplayStart;
	private Integer iDisplayLength;
	private Integer iSortCol_0;
	private String sSortDir_0;
	private int countResult;

	public Integer getsEcho() {
		return sEcho;
	}

	public void setsEcho(Integer sEcho) {
		this.sEcho = sEcho;
	}

	public Integer getiColumns() {
		return iColumns;
	}

	public void setiColumns(Integer iColumns) {
		this.iColumns = iColumns;
	}

	public Integer getiDisplayStart() {
		return iDisplayStart;
	}

	public void setiDisplayStart(Integer iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}

	public Integer getiDisplayLength() {
		return iDisplayLength;
	}

	public void setiDisplayLength(Integer iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}

	public Integer getiSortCol_0() {
		return iSortCol_0;
	}

	public void setiSortCol_0(Integer iSortCol_0) {
		this.iSortCol_0 = iSortCol_0;
	}

	public String getsSortDir_0() {
		return sSortDir_0;
	}

	public void setsSortDir_0(String sSortDir_0) {
		this.sSortDir_0 = sSortDir_0;
	}

	public int getCountResult() {
		return countResult;
	}

	public void setCountResult(int countResult) {
		this.countResult = countResult;
	}

	public int getPageNumber() {
		if(getCountResult() == 0) {
			return 0;
		}
		return getiDisplayStart() / getCountResult();
	}

	public int getRegistrosPorPagina() {
		if(getiDisplayLength() == -1) {
			return getCountResult();
		}
		return getiDisplayLength();
	}

	public Sort.Direction getSortDirection(){
		if(getsSortDir_0().equals("desc")) {
			return Sort.Direction.DESC;
		}
		return Sort.Direction.ASC;
	}
	
}
