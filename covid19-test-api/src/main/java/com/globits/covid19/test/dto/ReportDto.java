package com.globits.covid19.test.dto;

public class ReportDto {
	private String labTestName;
	private Long waitingForTest;
	private Long tested;
	private Long totalSample;
	private String statusName;
	public String getLabTestName() {
		return labTestName;
	}
	public void setLabTestName(String labTestName) {
		this.labTestName = labTestName;
	}
	public Long getWaitingForTest() {
		return waitingForTest;
	}
	public void setWaitingForTest(Long waitingForTest) {
		this.waitingForTest = waitingForTest;
	}
	public Long getTested() {
		return tested;
	}
	public void setTested(Long tested) {
		this.tested = tested;
	}
	public Long getTotalSample() {
		return totalSample;
	}
	public void setTotalSample(Long totalSample) {
		this.totalSample = totalSample;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public ReportDto() {
		
	}
	public ReportDto(String labTestName, Long waitingForTest, Long tested) {
		super();
		this.labTestName = labTestName;
		this.waitingForTest = waitingForTest;
		this.tested = tested;
	}
	
	public ReportDto(String labTestName, String statusName, Long totalSample) {
		super();
		this.labTestName = labTestName;
		this.statusName = statusName;
		this.totalSample = totalSample;
	}
	
	public ReportDto(String statusName, Long totalSample) {
		super();
		this.statusName = statusName;
		this.totalSample = totalSample;
	}
	
}
