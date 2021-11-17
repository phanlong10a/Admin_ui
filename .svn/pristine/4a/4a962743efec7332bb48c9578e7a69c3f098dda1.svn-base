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

import com.globits.covid19.test.dto.HealthOrganizationDto;
import com.globits.covid19.test.dto.HealthOrganizationSearchDto;
import com.globits.covid19.test.service.HealthOrganizationService;
import com.globits.covid19.test.utilities.NCoVConstant;

@RestController
@RequestMapping("/api/healthOrg")
public class RestHealthOrganizationController {

	@Autowired
	private HealthOrganizationService service;
	@Secured({NCoVConstant.ROLE_ADMIN,NCoVConstant.ROLE_USER})
	@RequestMapping(value = "/searchByDto", method = RequestMethod.POST)
	public ResponseEntity<Page<HealthOrganizationDto>> searchByDto(@RequestBody HealthOrganizationSearchDto dto) {
		Page<HealthOrganizationDto> result = service.searchByDto(dto);
		return new ResponseEntity<Page<HealthOrganizationDto>>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	@Secured({NCoVConstant.ROLE_ADMIN})
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<HealthOrganizationDto> getById(@PathVariable UUID id) {
		HealthOrganizationDto result = service.getById(id);
		return new ResponseEntity<HealthOrganizationDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	@Secured({NCoVConstant.ROLE_ADMIN})
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<HealthOrganizationDto> save(@RequestBody HealthOrganizationDto dto) {
		HealthOrganizationDto result = service.saveOrUpdate(dto, null);
		return new ResponseEntity<HealthOrganizationDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	@Secured({NCoVConstant.ROLE_ADMIN})
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<HealthOrganizationDto> update(@RequestBody HealthOrganizationDto dto, @PathVariable("id") UUID id) {
		HealthOrganizationDto result = service.saveOrUpdate(dto, id);
		return new ResponseEntity<HealthOrganizationDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	@Secured({NCoVConstant.ROLE_ADMIN})
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> deleteById(@PathVariable("id") String id) {
		Boolean result = service.deleteById(UUID.fromString(id));
		return new ResponseEntity<Boolean>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@Secured({NCoVConstant.ROLE_ADMIN})
	@RequestMapping(value = "/checkCode", method = RequestMethod.GET)
	public ResponseEntity<Boolean> checkCode(@RequestParam(value = "id", required = false) UUID id,
											 @RequestParam("code") String code) {
		Boolean result = service.checkCode(id, code);
		return new ResponseEntity<Boolean>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@Secured({NCoVConstant.ROLE_ADMIN,NCoVConstant.ROLE_USER})
	@RequestMapping(value = "/findAllChildHealthOrganizationByUserLogin", method = RequestMethod.POST)
	public ResponseEntity<Page<HealthOrganizationDto>> findAllChildHealthOrganizationByUserLogin(@RequestBody HealthOrganizationSearchDto dto) {
		dto.setNotInOrgIdByUserLogin(true);
		Page<HealthOrganizationDto> result = service.searchByDto(dto);
		return new ResponseEntity<Page<HealthOrganizationDto>>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
}
