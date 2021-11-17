package com.globits.covid19.test.report;

import java.util.Date;

import com.globits.covid19.test.domain.HealthOrganization;
import com.globits.covid19.test.dto.SearchDto;

public class ReportSearchDto extends SearchDto {
	private HealthOrganization labTest;
	private HealthOrganization collectOrg;
	private Boolean isDraft;
	private Boolean isPending;
	private Boolean isAccepted;
	private Boolean isPresent; 
	private Boolean isLabTest; 
	private Boolean isCollectOrg;
	private Boolean isParent;
	
	public HealthOrganization getLabTest() {
		return labTest;
	}

	public void setLabTest(HealthOrganization labTest) {
		this.labTest = labTest;
	}

	public Boolean getIsPresent() {
		return isPresent;
	}

	public void setIsPresent(Boolean isPresent) {
		this.isPresent = isPresent;
	}

	public Boolean getIsLabTest() {
		return isLabTest;
	}

	public void setIsLabTest(Boolean isLabTest) {
		this.isLabTest = isLabTest;
	}

	public Boolean getIsCollectOrg() {
		return isCollectOrg;
	}

	public void setIsCollectOrg(Boolean isCollectOrg) {
		this.isCollectOrg = isCollectOrg;
	}

	public HealthOrganization getCollectOrg() {
		return collectOrg;
	}

	public void setCollectOrg(HealthOrganization collectOrg) {
		this.collectOrg = collectOrg;
	}

	public Boolean getIsDraft() {
		return isDraft;
	}

	public void setIsDraft(Boolean isDraft) {
		this.isDraft = isDraft;
	}

	public Boolean getIsPending() {
		return isPending;
	}

	public void setIsPending(Boolean isPending) {
		this.isPending = isPending;
	}

	public Boolean getIsAccepted() {
		return isAccepted;
	}

	public void setIsAccepted(Boolean isAccepted) {
		this.isAccepted = isAccepted;
	}

	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}
	
}
