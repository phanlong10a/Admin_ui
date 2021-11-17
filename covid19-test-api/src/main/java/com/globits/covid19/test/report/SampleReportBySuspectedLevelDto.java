package com.globits.covid19.test.report;

import java.util.UUID;

public class SampleReportBySuspectedLevelDto {
	private UUID parentId;
	private UUID suspectedLevelId;
	private String suspectedLeveCode;
	private String suspectedLevelNameParent;
	private String suspectedLeveName;
	private Long totalDraft = 0L;
	private Long totalPending = 0L;
	private Long totalAccepted = 0L;
	private Long unfinishedQuantity = 0L ;
	private Long completedQuantity = 0L;
	
	public SampleReportBySuspectedLevelDto() {
		super();
	}
	
	public SampleReportBySuspectedLevelDto(UUID suspectedLevelId, String suspectedLeveCode, String suspectedLeveName, Long completedQuantity) {
		this.suspectedLevelId = suspectedLevelId;
		this.suspectedLeveCode = suspectedLeveCode;
		this.suspectedLeveName = suspectedLeveName;
		this.completedQuantity = completedQuantity;
	}

	public SampleReportBySuspectedLevelDto(UUID parentId, UUID suspectedLevelId, String suspectedLevelNameParent,
			String suspectedLeveName, Long unfinishedQuantity) {
		this.parentId = parentId;
		this.suspectedLevelId = suspectedLevelId;
		this.suspectedLevelNameParent = suspectedLevelNameParent;
		this.suspectedLeveName = suspectedLeveName;
		this.unfinishedQuantity = unfinishedQuantity;
	}

	public String getSuspectedLevelNameParent() {
		return suspectedLevelNameParent;
	}

	public void setSuspectedLevelNameParent(String suspectedLevelNameParent) {
		this.suspectedLevelNameParent = suspectedLevelNameParent;
	}

	public String getSuspectedLeveName() {
		return suspectedLeveName;
	}

	public void setSuspectedLeveName(String suspectedLeveName) {
		this.suspectedLeveName = suspectedLeveName;
	}

	public Long getUnfinishedQuantity() {
		return unfinishedQuantity;
	}
	public void setUnfinishedQuantity(Long unfinishedQuantity) {
		this.unfinishedQuantity = unfinishedQuantity;
	}
	public Long getCompletedQuantity() {
		return completedQuantity;
	}
	public void setCompletedQuantity(Long completedQuantity) {
		this.completedQuantity = completedQuantity;
	}

	public UUID getParentId() {
		return parentId;
	}

	public void setParentId(UUID parentId) {
		this.parentId = parentId;
	}

	public UUID getSuspectedLevelId() {
		return suspectedLevelId;
	}

	public void setSuspectedLevelId(UUID suspectedLevelId) {
		this.suspectedLevelId = suspectedLevelId;
	}

	public String getSuspectedLeveCode() {
		return suspectedLeveCode;
	}

	public void setSuspectedLeveCode(String suspectedLeveCode) {
		this.suspectedLeveCode = suspectedLeveCode;
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
	
}
