package com.globits.covid19.test.service;

import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.globits.covid19.test.dto.ReportDto;
import com.globits.covid19.test.dto.SampleSearchDto;
import com.globits.covid19.test.report.ReportSampleByHealthOrgDto;
import com.globits.covid19.test.report.ReportSearchDto;
import com.globits.covid19.test.report.SampleReportBySuspectedLevelDto;

@Service
public interface ReportService {
	public List<ReportDto> reportNumberOfTestsByStatus(SampleSearchDto dto);
	
	public List<ReportDto> reportNumberOrgOfTestsByStatus(SampleSearchDto dto);
	
	public List<ReportSampleByHealthOrgDto> reportSampleByHealthOrgDto(ReportSearchDto dto);

	public List<SampleReportBySuspectedLevelDto> SampleReportBySuspectedLevel(ReportSearchDto dto);

	public List<ReportSampleByHealthOrgDto> reportSampleByCollectOrg(ReportSearchDto dto);
	
	ByteArrayResource reportNumberOfTestsByStatusToExcel(List<ReportDto> list) throws Exception;
	
	ByteArrayResource reportSampleByHealthOrgToExcel(List<ReportSampleByHealthOrgDto> list) throws Exception;
	
	ByteArrayResource reportSampleByCollectOrgToExcel(List<ReportSampleByHealthOrgDto> list) throws Exception;
	
	ByteArrayResource SampleReportBySuspectedLevelToExcel(List<SampleReportBySuspectedLevelDto> list) throws Exception;
	
	List<ReportDto> reportNumberOfTestsByResult(SampleSearchDto dto);
	
	List<ReportDto> reportNumberOrgOfTestsByResult(SampleSearchDto dto);

	ByteArrayResource reportNumberOfTestsByResultToExcel(List<ReportDto> list) throws Exception;

}
