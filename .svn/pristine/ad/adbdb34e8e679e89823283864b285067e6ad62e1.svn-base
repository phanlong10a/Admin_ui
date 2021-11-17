package com.globits.covid19.test.rest;

import com.globits.covid19.test.dto.HealthResourceCategoryDto;
import com.globits.covid19.test.dto.SearchDto;
import com.globits.covid19.test.service.HealthResourceCategoryService;
import com.globits.covid19.test.utilities.NCoVConstant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/healthResourceCategory")
public class RestHealthResourceCategoryController {

	@Autowired
	private HealthResourceCategoryService service;
	
	@Secured({NCoVConstant.ROLE_ADMIN, NCoVConstant.ROLE_USER})
	@RequestMapping(value = "/searchByDto", method = RequestMethod.POST)
	public ResponseEntity<Page<HealthResourceCategoryDto>> searchByDto(@RequestBody SearchDto dto) {
		Page<HealthResourceCategoryDto> result = service.searchByDto(dto);
		return new ResponseEntity<Page<HealthResourceCategoryDto>>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	@Secured({NCoVConstant.ROLE_ADMIN, NCoVConstant.ROLE_USER})
	@RequestMapping(value = "getById/{id}", method = RequestMethod.GET)
	public ResponseEntity<HealthResourceCategoryDto> getById(@PathVariable UUID id) {
		HealthResourceCategoryDto result = service.getById(id);
		return new ResponseEntity<HealthResourceCategoryDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	@Secured({NCoVConstant.ROLE_ADMIN})
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<HealthResourceCategoryDto> save(@RequestBody HealthResourceCategoryDto dto) {
		HealthResourceCategoryDto result = service.saveOrUpdate(dto, null);
		return new ResponseEntity<HealthResourceCategoryDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	@Secured({NCoVConstant.ROLE_ADMIN})
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<HealthResourceCategoryDto> update(@RequestBody HealthResourceCategoryDto dto, @PathVariable("id") UUID id) {
		HealthResourceCategoryDto result = service.saveOrUpdate(dto, id);
		return new ResponseEntity<HealthResourceCategoryDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
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
