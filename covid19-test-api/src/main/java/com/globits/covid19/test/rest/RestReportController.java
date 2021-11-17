package com.globits.covid19.test.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.globits.covid19.test.dto.ReportDto;
import com.globits.covid19.test.dto.SampleSearchDto;
import com.globits.covid19.test.dto.SearchDto;
import com.globits.covid19.test.report.ReportSampleByHealthOrgDto;
import com.globits.covid19.test.report.ReportSearchDto;
import com.globits.covid19.test.report.SampleReportBySuspectedLevelDto;
import com.globits.covid19.test.service.ReportService;
import com.globits.covid19.test.utilities.NCoVConstant;

@RestController
@RequestMapping("/api/report")
public class RestReportController {
	@Autowired
	ReportService service;
	@Secured({NCoVConstant.ROLE_ADMIN,NCoVConstant.ROLE_USER})
	@RequestMapping(value = "/reportNumberOfTestsByStatus", method = RequestMethod.POST)
	public ResponseEntity<List<ReportDto>> reportNumberOfTestsByStatus(@RequestBody SampleSearchDto dto) {
		List<ReportDto> result = service.reportNumberOfTestsByStatus(dto);
		return new ResponseEntity<List<ReportDto>>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@Secured({NCoVConstant.ROLE_ADMIN,NCoVConstant.ROLE_USER})
	@RequestMapping(value = "/reportNumberOfTestsByResult", method = RequestMethod.POST)
	public ResponseEntity<List<ReportDto>> reportNumberOfTestsByResult(@RequestBody SampleSearchDto dto) {
		List<ReportDto> result = service.reportNumberOfTestsByResult(dto);
		return new ResponseEntity<List<ReportDto>>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@Secured({NCoVConstant.ROLE_ADMIN,NCoVConstant.ROLE_USER})
	@RequestMapping(value = "/reportNumberOrgOfTestsByStatus", method = RequestMethod.POST)
	public ResponseEntity<List<ReportDto>> reportNumberOrgOfTestsByStatus(@RequestBody SampleSearchDto dto) {
		List<ReportDto> result = service.reportNumberOrgOfTestsByStatus(dto);
		return new ResponseEntity<List<ReportDto>>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@Secured({NCoVConstant.ROLE_ADMIN,NCoVConstant.ROLE_USER})
	@RequestMapping(value = "/reportNumberOrgOfTestsByResult", method = RequestMethod.POST)
	public ResponseEntity<List<ReportDto>> reportNumberOrgOfTestsByResult(@RequestBody SampleSearchDto dto) {
		List<ReportDto> result = service.reportNumberOrgOfTestsByResult(dto);
		return new ResponseEntity<List<ReportDto>>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@Secured({NCoVConstant.ROLE_ADMIN,NCoVConstant.ROLE_USER})
	@RequestMapping(value = "/reportSampleByHealthOrg", method = RequestMethod.POST)
	public ResponseEntity<List<ReportSampleByHealthOrgDto>> reportSampleByHealthOrg(@RequestBody ReportSearchDto dto) {
		List<ReportSampleByHealthOrgDto> result = service.reportSampleByHealthOrgDto(dto);
		return new ResponseEntity<List<ReportSampleByHealthOrgDto>>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@Secured({NCoVConstant.ROLE_ADMIN,NCoVConstant.ROLE_USER})
	@RequestMapping(value = "/reportSampleByCollectOrg", method = RequestMethod.POST)
	public ResponseEntity<List<ReportSampleByHealthOrgDto>> reportSampleByCollectOrg(@RequestBody ReportSearchDto dto) {
		List<ReportSampleByHealthOrgDto> result = service.reportSampleByCollectOrg(dto);
		return new ResponseEntity<List<ReportSampleByHealthOrgDto>>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@Secured({NCoVConstant.ROLE_ADMIN,NCoVConstant.ROLE_USER})
	@RequestMapping(value = "/sampleReportBySuspectedLevel", method = RequestMethod.POST)
	public ResponseEntity<List<SampleReportBySuspectedLevelDto>> sampleReportBySuspectedLevel(@RequestBody ReportSearchDto dto) {
		List<SampleReportBySuspectedLevelDto> result = service.SampleReportBySuspectedLevel(dto);
		return new ResponseEntity<List<SampleReportBySuspectedLevelDto>>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	
}
