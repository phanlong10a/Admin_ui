package com.globits.covid19.test.rest;

import com.globits.covid19.test.dto.IsolationCenterDto;
import com.globits.covid19.test.dto.IsolationCenterSearchDto;
import com.globits.covid19.test.service.IsolationCenterService;
import com.globits.covid19.test.utilities.NCoVConstant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/isolationCenter")
public class RestIsolationCenterController {

	@Autowired
	private IsolationCenterService service;
	@Secured({NCoVConstant.ROLE_ADMIN, NCoVConstant.ROLE_USER})
	@RequestMapping(value = "/searchByDto", method = RequestMethod.POST)
	public ResponseEntity<Page<IsolationCenterDto>> searchByDto(@RequestBody IsolationCenterSearchDto dto) {
		Page<IsolationCenterDto> result = service.searchByDto(dto);
		return new ResponseEntity<Page<IsolationCenterDto>>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	@Secured({NCoVConstant.ROLE_ADMIN})
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<IsolationCenterDto> getById(@PathVariable UUID id) {
		IsolationCenterDto result = service.getById(id);
		return new ResponseEntity<IsolationCenterDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	@Secured({NCoVConstant.ROLE_ADMIN})
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<IsolationCenterDto> save(@RequestBody IsolationCenterDto dto) {
		IsolationCenterDto result = service.saveOrUpdate(dto, null);
		return new ResponseEntity<IsolationCenterDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	@Secured({NCoVConstant.ROLE_ADMIN})
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<IsolationCenterDto> update(@RequestBody IsolationCenterDto dto, @PathVariable("id") UUID id) {
		IsolationCenterDto result = service.saveOrUpdate(dto, id);
		return new ResponseEntity<IsolationCenterDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
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
		Boolean result = service.checkCode(code, id);
		return new ResponseEntity<Boolean>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

}
