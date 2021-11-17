package com.globits.covid19.test.rest;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.globits.covid19.test.dto.SampleSearchDto;
import com.globits.covid19.test.dto.SuspectedTypeDto;
import com.globits.covid19.test.service.SuspectedTypeService;
import com.globits.covid19.test.utilities.NCoVConstant;

@RestController
@RequestMapping("/api/suspectedType")
public class RestSuspectedTypeController {

	@Autowired
	private SuspectedTypeService service;
	@Secured({NCoVConstant.ROLE_ADMIN, NCoVConstant.ROLE_USER})
	@RequestMapping(value = "/searchByDto", method = RequestMethod.POST)
	public ResponseEntity<Page<SuspectedTypeDto>> searchByDto(@RequestBody SampleSearchDto dto) {
		Page<SuspectedTypeDto> result = service.searchByDto(dto);
		return new ResponseEntity<Page<SuspectedTypeDto>>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	@Secured({NCoVConstant.ROLE_ADMIN, NCoVConstant.ROLE_USER})
	@RequestMapping(value = "getById/{id}", method = RequestMethod.GET)
	public ResponseEntity<SuspectedTypeDto> getById(@PathVariable UUID id) {
		SuspectedTypeDto result = service.getById(id);
		return new ResponseEntity<SuspectedTypeDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	@Secured({NCoVConstant.ROLE_ADMIN})
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<SuspectedTypeDto> save(@RequestBody SuspectedTypeDto dto) {
		SuspectedTypeDto result = service.saveOrUpdate(dto, null);
		return new ResponseEntity<SuspectedTypeDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	@Secured({NCoVConstant.ROLE_ADMIN})
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<SuspectedTypeDto> update(@RequestBody SuspectedTypeDto dto, @PathVariable("id") UUID id) {
		SuspectedTypeDto result = service.saveOrUpdate(dto, id);
		return new ResponseEntity<SuspectedTypeDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
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
