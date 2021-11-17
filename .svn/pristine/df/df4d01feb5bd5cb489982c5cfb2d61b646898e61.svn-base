package com.globits.covid19.test.rest;

import com.globits.covid19.test.dto.HealthOrganizationDto;
import com.globits.covid19.test.dto.SampleSearchDto;
import com.globits.covid19.test.dto.SuspectedLevelDto;
import com.globits.covid19.test.service.HealthOrganizationService;
import com.globits.covid19.test.service.SuspectedLevelService;
import com.globits.covid19.test.utilities.NCoVConstant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/suspectedLevel")
public class RestSuspectedLevelController {

	@Autowired
	private SuspectedLevelService service;
	@Secured({NCoVConstant.ROLE_ADMIN, NCoVConstant.ROLE_USER})
	@RequestMapping(value = "/searchByDto", method = RequestMethod.POST)
	public ResponseEntity<Page<SuspectedLevelDto>> searchByDto(@RequestBody SampleSearchDto dto) {
		Page<SuspectedLevelDto> result = service.searchByDto(dto);
		return new ResponseEntity<Page<SuspectedLevelDto>>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	@Secured({NCoVConstant.ROLE_ADMIN, NCoVConstant.ROLE_USER})
	@RequestMapping(value = "getById/{id}", method = RequestMethod.GET)
	public ResponseEntity<SuspectedLevelDto> getById(@PathVariable UUID id) {
		SuspectedLevelDto result = service.getById(id);
		return new ResponseEntity<SuspectedLevelDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	@Secured({NCoVConstant.ROLE_ADMIN})
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<SuspectedLevelDto> save(@RequestBody SuspectedLevelDto dto) {
		SuspectedLevelDto result = service.saveOrUpdate(dto, null);
		return new ResponseEntity<SuspectedLevelDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	@Secured({NCoVConstant.ROLE_ADMIN})
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<SuspectedLevelDto> update(@RequestBody SuspectedLevelDto dto, @PathVariable("id") UUID id) {
		SuspectedLevelDto result = service.saveOrUpdate(dto, id);
		return new ResponseEntity<SuspectedLevelDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	@Secured({NCoVConstant.ROLE_ADMIN})
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> deleteById(@PathVariable("id") String id) {
		Boolean result = service.deleteById(UUID.fromString(id));
		return new ResponseEntity<Boolean>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	@Secured({NCoVConstant.ROLE_ADMIN, NCoVConstant.ROLE_USER})
	@RequestMapping(value = "/checkCode", method = RequestMethod.GET)
	public ResponseEntity<Boolean> checkCode(@RequestParam(value = "id", required = false) UUID id,
											 @RequestParam("code") String code) {
		Boolean result = service.checkCode(code, id);
		return new ResponseEntity<Boolean>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

}
