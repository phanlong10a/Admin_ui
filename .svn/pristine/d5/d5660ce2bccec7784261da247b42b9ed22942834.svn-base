package com.globits.covid19.test.rest;

import com.globits.covid19.test.dto.HealthResourceDto;
import com.globits.covid19.test.dto.SearchDto;
import com.globits.covid19.test.service.HealthResourceService;
import com.globits.covid19.test.utilities.NCoVConstant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/healthResource")
public class RestHealthResourceController {

	@Autowired
	private HealthResourceService service;
	
	@Secured({NCoVConstant.ROLE_ADMIN, NCoVConstant.ROLE_USER})
	@RequestMapping(value = "/searchByDto", method = RequestMethod.POST)
	public ResponseEntity<Page<HealthResourceDto>> searchByDto(@RequestBody SearchDto dto) {
		Page<HealthResourceDto> result = service.searchByDto(dto);
		return new ResponseEntity<Page<HealthResourceDto>>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	@Secured({NCoVConstant.ROLE_ADMIN, NCoVConstant.ROLE_USER})
	@RequestMapping(value = "getById/{id}", method = RequestMethod.GET)
	public ResponseEntity<HealthResourceDto> getById(@PathVariable UUID id) {
		HealthResourceDto result = service.getById(id);
		return new ResponseEntity<HealthResourceDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	@Secured({NCoVConstant.ROLE_ADMIN})
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<HealthResourceDto> save(@RequestBody HealthResourceDto dto) {
		HealthResourceDto result = service.saveOrUpdate(dto, null);
		return new ResponseEntity<HealthResourceDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	@Secured({NCoVConstant.ROLE_ADMIN})
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<HealthResourceDto> update(@RequestBody HealthResourceDto dto, @PathVariable("id") UUID id) {
		HealthResourceDto result = service.saveOrUpdate(dto, id);
		return new ResponseEntity<HealthResourceDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
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
		Boolean result = service.checkCode(id, code);
		return new ResponseEntity<Boolean>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

}
