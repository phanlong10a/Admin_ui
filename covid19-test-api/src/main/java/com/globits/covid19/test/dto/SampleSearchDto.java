package com.globits.covid19.test.dto;

public class SampleSearchDto extends SearchDto {
    private Boolean isParent;
    private Boolean isChidren;
    private HealthOrganizationDto labTest;
    private HealthOrganizationDto collectSampleOrg;
    private String sampleStatus;
    private String sampleResult;
    private EpidemiologicalFactorsDto epiFactor;
    private SuspectedLevelDto susLevel;
    private Boolean isUpdateSampleResult;
    public Boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(Boolean isParent) {
        this.isParent = isParent;
    }

    public Boolean getIsChidren() {
		return isChidren;
	}

	public void setIsChidren(Boolean isChidren) {
		this.isChidren = isChidren;
	}

	public HealthOrganizationDto getLabTest() {
        return labTest;
    }

    public void setLabTest(HealthOrganizationDto labTest) {
        this.labTest = labTest;
    }

	public HealthOrganizationDto getCollectSampleOrg() {
		return collectSampleOrg;
	}

	public void setCollectSampleOrg(HealthOrganizationDto collectSampleOrg) {
		this.collectSampleOrg = collectSampleOrg;
	}
	public String getSampleStatus() {
		return sampleStatus;
	}

	public void setSampleStatus(String sampleStatus) {
		this.sampleStatus = sampleStatus;
	}

	public String getSampleResult() {
		return sampleResult;
	}

	public void setSampleResult(String sampleResult) {
		this.sampleResult = sampleResult;
	}

	public EpidemiologicalFactorsDto getEpiFactor() {
		return epiFactor;
	}

	public void setEpiFactor(EpidemiologicalFactorsDto epiFactor) {
		this.epiFactor = epiFactor;
	}

	public SuspectedLevelDto getSusLevel() {
		return susLevel;
	}

	public void setSusLevel(SuspectedLevelDto susLevel) {
		this.susLevel = susLevel;
	}

	public Boolean getIsUpdateSampleResult() {
		return isUpdateSampleResult;
	}

	public void setIsUpdateSampleResult(Boolean isUpdateSampleResult) {
		this.isUpdateSampleResult = isUpdateSampleResult;
	}
	
	
}
