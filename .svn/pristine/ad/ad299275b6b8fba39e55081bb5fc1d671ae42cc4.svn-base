package com.globits.covid19.test.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.globits.covid19.test.dto.AnalyticsDto;
import com.globits.covid19.test.dto.AnalyticsSearchDto;
import com.globits.covid19.test.dto.TestingStatusDto;
import com.globits.covid19.test.service.AnalyticsService;
import com.globits.covid19.test.utilities.NCoVConstant;

@RestController
@RequestMapping("/api/analytics")
public class RestAnalyticsController {
    
    @Autowired
    private AnalyticsService service;
    
    @Secured({NCoVConstant.ROLE_ADMIN,NCoVConstant.ROLE_USER})
    @RequestMapping(value = "/getTotalGroupByHealthOrg", method = RequestMethod.POST)

    public ResponseEntity<List<AnalyticsDto>> getTotalGroupByHealthOrg(@RequestBody AnalyticsSearchDto searchDto) {
        List<AnalyticsDto> result = service.getTotalGroupByHealthOrg(searchDto);
        return new ResponseEntity<>(result, result != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
  
    @Secured({NCoVConstant.ROLE_ADMIN,NCoVConstant.ROLE_USER})
    @RequestMapping(value = "/getListStatus", method = RequestMethod.POST)
    public ResponseEntity<List<TestingStatusDto>> getListStatus(@RequestBody AnalyticsSearchDto searchDto) {
        List<TestingStatusDto> result = service.SummarySampleTestingStatus(searchDto);
        return new ResponseEntity<>(result, result != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
    @Secured({NCoVConstant.ROLE_ADMIN,NCoVConstant.ROLE_USER})
  @RequestMapping(value = "/getTotalSampleByHealthOrg", method = RequestMethod.POST)
    public ResponseEntity<List<AnalyticsDto>> getTotalSampleByHealthOrg(@RequestBody AnalyticsSearchDto searchDto) {
        List<AnalyticsDto> result = service.getTotalSampleByHealthOrg(searchDto);
        return new ResponseEntity<>(result, result != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
    @Secured({NCoVConstant.ROLE_ADMIN,NCoVConstant.ROLE_USER})
@RequestMapping(value = "/getTotalCases", method = RequestMethod.POST)
    public ResponseEntity<List<AnalyticsDto>> getTotalCases(@RequestBody AnalyticsSearchDto searchDto) {
        List<AnalyticsDto> result = service.getTotalCases(searchDto);
        return new ResponseEntity<>(result, result != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@Secured({
			NCoVConstant.ROLE_ADMIN, NCoVConstant.ROLE_USER
	})
	@RequestMapping(value = "/getTotalSuspectedPersonByLevel", method = RequestMethod.POST)
	public ResponseEntity<List<AnalyticsDto>>
			getTotalSuspectedPersonByLevel(@RequestBody AnalyticsSearchDto searchDto) {
		List<AnalyticsDto> result = service.getTotalSuspectedPersonByLevel(searchDto);

		return new ResponseEntity<>(result,
				result != null? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@Secured({
			NCoVConstant.ROLE_ADMIN, NCoVConstant.ROLE_USER
	})
	@RequestMapping(value = "/getTotalEpidemiologicalFactors", method = RequestMethod.POST)
	public ResponseEntity<List<AnalyticsDto>>
			getTotalEpidemiologicalFactors(@RequestBody AnalyticsSearchDto searchDto) {
		List<AnalyticsDto> result = service.getTotalEpidemiologicalFactors(searchDto);

		return new ResponseEntity<>(result,
				result != null? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
}