package com.globits.covid19.test.report;

import java.util.UUID;

public class ReportSampleByHealthOrgDto {
	private UUID healthOrgId;
	private String healthOrgName;
	private Long totalDraft;
	private Long totalPending;
	private Long totalAccepted;
	private Long totalRejected;
	private Long totalCanceled;
	private Long totalSample;
	private Long totalPresent;
	
	public ReportSampleByHealthOrgDto() {
		super();
	}
	
	
	public ReportSampleByHealthOrgDto(UUID id, String healthOrgName, Long totalSample) {
		this.healthOrgId = id;
		this.healthOrgName = healthOrgName;
		this.totalSample = totalSample;
	}

	public UUID getHealthOrgId() {
		return healthOrgId;
	}

	public void setHealthOrgId(UUID healthOrgId) {
		this.healthOrgId = healthOrgId;
	}

	public String getHealthOrgName() {
		return healthOrgName;
	}

	public void setHealthOrgName(String healthOrgName) {
		this.healthOrgName = healthOrgName;
	}

	public Long getTotalSample() {
		return totalSample;
	}
	public void setTotalSample(Long totalSample) {
		this.totalSample = totalSample;
	}

	public Long getTotalPresent() {
		return totalPresent;
	}

	public void setTotalPresent(Long totalPresent) {
		this.totalPresent = totalPresent;
	}

	public Long getTotalDraft() {
		return totalDraft;
	}

	public void setTotalDraft(Long totalDraft) {
		this.totalDraft = totalDraft;
	}

	public Long getTotalPending() {
		return totalPending;
	}

	public void setTotalPending(Long totalPending) {
		this.totalPending = totalPending;
	}

	public Long getTotalAccepted() {
		return totalAccepted;
	}

	public void setTotalAccepted(Long totalAccepted) {
		this.totalAccepted = totalAccepted;
	}

	public Long getTotalRejected() {
		return totalRejected;
	}

	public void setTotalRejected(Long totalRejected) {
		this.totalRejected = totalRejected;
	}

	public Long getTotalCanceled() {
		return totalCanceled;
	}

	public void setTotalCanceled(Long totalCanceled) {
		this.totalCanceled = totalCanceled;
	}
	
}
