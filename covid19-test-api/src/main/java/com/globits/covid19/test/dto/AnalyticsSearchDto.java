package com.globits.covid19.test.dto;

import java.util.UUID;

import org.joda.time.LocalDateTime;

public class AnalyticsSearchDto extends SearchDto{
    private LocalDateTime sampleDateFrom;
    private LocalDateTime sampleDateTo;
    private UUID administrativeId;
    private UUID healthOrganizationId;
    public LocalDateTime getSampleDateFrom() {
        return sampleDateFrom;
    }
    public void setSampleDateFrom(LocalDateTime sampleDateFrom) {
        this.sampleDateFrom = sampleDateFrom;
    }
    public LocalDateTime getSampleDateTo() {
        return sampleDateTo;
    }
    public void setSampleDateTo(LocalDateTime sampleDateTo) {
        this.sampleDateTo = sampleDateTo;
    }
    public UUID getAdministrativeId() {
        return administrativeId;
    }
    public void setAdministrativeId(UUID administrativeId) {
        this.administrativeId = administrativeId;
    }
	public UUID getHealthOrganizationId() {
		return healthOrganizationId;
	}
	public void setHealthOrganizationId(UUID healthOrganizationId) {
		this.healthOrganizationId = healthOrganizationId;
	}
	public AnalyticsSearchDto() {
		super();
	} 
    
}
