package com.globits.covid19.test.dto;

import java.util.List;
import java.util.UUID;

public class AnalyticsDto {
    private UUID healthOrgId;
    private String name;
	private Integer totalSamplePositive;
	private Integer totalSampleNegative;
    private Long totalCases;
    private String sampleTestDate;
    private Long totalTestedCases;
    private Long totalRemainedCases;
    private Long totalAllTimeTestedCases;
    private Long totalAllTimeRemainedCases;
    private Long totalSample;
    private List<TestingStatusDto> listStatus;
	private TestingStatusDto status;
    
    public AnalyticsDto() {
		
	}
    public AnalyticsDto(Long totalCases,String sampleTestDate) {
        this.totalCases = totalCases;
        this.sampleTestDate = sampleTestDate;
    }
	public AnalyticsDto(UUID healthOrgId, String name, Long totalCases, Long totalRemainedCases, Long totalTestedCases, Integer totalSamplePositive, Integer totalSampleNegative) {
        this.name = name;
        this.healthOrgId = healthOrgId;
        this.totalCases = totalCases;
        this.totalSamplePositive = totalSamplePositive;
        this.totalSampleNegative = totalSampleNegative;
        this.totalTestedCases = totalTestedCases;
        this.totalRemainedCases = totalRemainedCases;
    }

    public AnalyticsDto(UUID healthOrgId, String name, Long totalSample) {
        this.healthOrgId = healthOrgId;
        this.name = name;
        this.totalSample = totalSample;
    }

	public AnalyticsDto(String healthOrgName, Long total, String suspectedLevelName) {
		name = healthOrgName;
		status = new TestingStatusDto(suspectedLevelName, total);
	}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getTotalSamplePositive() {
        return totalSamplePositive;
    }
    public void setTotalSamplePositive(Integer totalSamplePositive) {
        this.totalSamplePositive = totalSamplePositive;
    }
    public Integer getTotalSampleNegative() {
        return totalSampleNegative;
    }
    public void setTotalSampleNegative(Integer totalSampleNegative) {
        this.totalSampleNegative = totalSampleNegative;
    }

	public Long getTotalCases() {
		return totalCases;
	}
	public void setTotalCases(Long totalTCases) {
		totalCases = totalTCases;
	}
//	public Date getSampleTestDate() {
//		return sampleTestDate;
//	}
//	public void setSampleTestDate(Date sampleTestDate) {
//		this.sampleTestDate = sampleTestDate;
//	}


    public String getSampleTestDate() {
        return sampleTestDate;
    }

    public void setSampleTestDate(String sampleTestDate) {
        this.sampleTestDate = sampleTestDate;
    }

    public UUID getHealthOrgId() {
        return healthOrgId;
    }
    public void setHealthOrgId(UUID healthOrgId) {
        this.healthOrgId = healthOrgId;
    }
    public Long getTotalTestedCases() {
        return totalTestedCases;
    }
    public void setTotalTestedCases(Long totalTestedCases) {
        this.totalTestedCases = totalTestedCases;
    }
    public Long getTotalRemainedCases() {
        return totalRemainedCases;
    }
    public void setTotalRemainedCases(Long totalRemainedCases) {
        this.totalRemainedCases = totalRemainedCases;
    }
	public List<TestingStatusDto> getListStatus() {
		return listStatus;
	}
	public void setListStatus(List<TestingStatusDto> listStatus) {
		this.listStatus = listStatus;
	}
    public Long getTotalSample() {
        return totalSample;
    }
    public void setTotalSample(Long totalSample) {
        this.totalSample = totalSample;
    }
	public Long getTotalAllTimeTestedCases() {
		return totalAllTimeTestedCases;
	}
	public void setTotalAllTimeTestedCases(Long totalAllTimeTestedCases) {
		this.totalAllTimeTestedCases = totalAllTimeTestedCases;
	}
	public Long getTotalAllTimeRemainedCases() {
		return totalAllTimeRemainedCases;
	}
	public void setTotalAllTimeRemainedCases(Long totalAllTimeRemainedCases) {
		this.totalAllTimeRemainedCases = totalAllTimeRemainedCases;
	}

	public TestingStatusDto getStatus() {
		return status;
	}

	public void setStatus(TestingStatusDto status) {
		this.status = status;
	}
}
