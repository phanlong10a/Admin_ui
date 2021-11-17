package com.globits.covid19.test.service;

import java.util.List;

import com.globits.covid19.test.dto.AnalyticsDto;
import com.globits.covid19.test.dto.AnalyticsSearchDto;
import com.globits.covid19.test.dto.TestingStatusDto;

public interface AnalyticsService {

    List<AnalyticsDto> getTotalOverview(AnalyticsSearchDto searchDto);

    List<AnalyticsDto> getTotalGroupByEpidemiologicalFactors(AnalyticsSearchDto searchDto);

    List<AnalyticsDto> getTotalGroupByAdminUnit(AnalyticsSearchDto searchDto);

    List<AnalyticsDto> getTotalGroupByHealthOrg(AnalyticsSearchDto searchDto);
    
    Long getTotalTestedCases(AnalyticsSearchDto searchDto);
    
    Long getTotalRemainedCases(AnalyticsSearchDto searchDto);

	List<TestingStatusDto> SummarySampleTestingStatus(AnalyticsSearchDto searchDto);
	
	List<AnalyticsDto> getTotalCases(AnalyticsSearchDto searchDto);

	List<AnalyticsDto> getTotalSampleByHealthOrg(AnalyticsSearchDto searchDto);
	
	List<AnalyticsDto> getTotalCasesTest(AnalyticsSearchDto searchDto);

	List<AnalyticsDto> getTotalSuspectedPersonByLevel(AnalyticsSearchDto searchDto);

	List<AnalyticsDto> getTotalEpidemiologicalFactors(AnalyticsSearchDto searchDto);

}
