package com.globits.covid19.test.dto;

import java.util.List;

public class ImportSampleResultsDto {
	private boolean success = false;
	private boolean duplicate = true; //check duplicate data (theo code)
	private List<SampleDto> listData;
	private List<SampleDto> listSampleGroup;//List mau gop
	List<String> listCodeDuplicate;
	private String text;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public List<SampleDto> getListData() {
		return listData;
	}
	public void setListData(List<SampleDto> listData) {
		this.listData = listData;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<String> getListCodeDuplicate() {
		return listCodeDuplicate;
	}
	public void setListCodeDuplicate(List<String> listCodeDuplicate) {
		this.listCodeDuplicate = listCodeDuplicate;
	}
	public boolean isDuplicate() {
		return duplicate;
	}
	public void setDuplicate(boolean duplicate) {
		this.duplicate = duplicate;
	}
	
	public List<SampleDto> getListSampleGroup() {
		return listSampleGroup;
	}
	public void setListSampleGroup(List<SampleDto> listSampleGroup) {
		this.listSampleGroup = listSampleGroup;
	}
	public ImportSampleResultsDto() {
		super();
	}
	
}
