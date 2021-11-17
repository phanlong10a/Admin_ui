package com.globits.covid19.test.rest;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import com.globits.covid19.test.domain.Sample;
import com.globits.covid19.test.dto.SampleDto;
import com.globits.covid19.test.dto.SampleSearchDto;
import com.globits.covid19.test.service.SampleService;
import com.globits.covid19.test.utilities.NCoVConstant;

@RestController
@RequestMapping("/api/sample")
public class RestSampleController {

	@Autowired
	private SampleService service;
	@Secured({NCoVConstant.ROLE_ADMIN,NCoVConstant.ROLE_USER})
	@RequestMapping(value = "searchByDto", method = RequestMethod.POST)
	public ResponseEntity<Page<SampleDto>> searchByDto(@RequestBody SampleSearchDto dto) {
		//Page<SampleDto> result = service.searchByDto(dto);//DungHQ commented
		Page<SampleDto> result = service.searchSampleAndUser(dto);
		return new ResponseEntity<Page<SampleDto>>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	@Secured({NCoVConstant.ROLE_ADMIN,NCoVConstant.ROLE_USER})
	@RequestMapping(value = "searchByPage", method = RequestMethod.POST)
	public ResponseEntity<Page<SampleDto>> searchByPage(@RequestBody SampleSearchDto dto) {
		Page<SampleDto> result = service.searchByDto(dto);
		return new ResponseEntity<Page<SampleDto>>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	@Secured({NCoVConstant.ROLE_ADMIN,NCoVConstant.ROLE_USER})
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<SampleDto> getById(@PathVariable UUID id) {
		SampleDto result = service.getById(id);
		return new ResponseEntity<SampleDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	@Secured({NCoVConstant.ROLE_ADMIN,NCoVConstant.ROLE_USER})
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<SampleDto> save(@RequestBody SampleDto dto) {
		SampleDto result = service.saveOrUpdate(dto, null);
		return new ResponseEntity<SampleDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	@Secured({NCoVConstant.ROLE_ADMIN,NCoVConstant.ROLE_USER})
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<SampleDto> update(@RequestBody SampleDto dto, @PathVariable("id") UUID id) {
		SampleDto result = service.saveOrUpdate(dto, id);
		return new ResponseEntity<SampleDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	@Secured({NCoVConstant.ROLE_ADMIN,NCoVConstant.ROLE_USER})
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> deleteById(@PathVariable("id") String id) {
		Boolean result = service.deleteById(UUID.fromString(id));
		return new ResponseEntity<Boolean>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	@Secured({NCoVConstant.ROLE_ADMIN,NCoVConstant.ROLE_USER})
	@RequestMapping(value = "searchbyuser", method = RequestMethod.POST)
	public ResponseEntity<Page<SampleDto>> searchSampleAndUser(@RequestBody SampleSearchDto dto) {
		Page<SampleDto> result = service.searchSampleAndUser(dto);
		return new ResponseEntity<Page<SampleDto>>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@Secured({NCoVConstant.ROLE_ADMIN,NCoVConstant.ROLE_USER})
	@RequestMapping(value = "/checkCode",method = RequestMethod.GET)
	public ResponseEntity<Boolean> checkDuplicateCode(@RequestParam(value = "id", required=false) UUID id, @RequestParam("code") String code) {
		Boolean result = service.checkDuplicateCode(id, code);
		return new ResponseEntity<Boolean>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@Secured({NCoVConstant.ROLE_ADMIN,NCoVConstant.ROLE_USER})
	@RequestMapping(value="/saveSampleGroup", method = RequestMethod.POST)
	public ResponseEntity<SampleDto> saveSampleGroup(@RequestBody SampleDto dto) {
		SampleDto result = service.saveOrUpdateListPerson(dto, null);
		return new ResponseEntity<SampleDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@Secured({NCoVConstant.ROLE_ADMIN,NCoVConstant.ROLE_USER})
	@RequestMapping(value = "/updateSampleGroup/{id}", method = RequestMethod.PUT)
	public ResponseEntity<SampleDto> updateSampleGroup(@RequestBody SampleDto dto, @PathVariable("id") UUID id) {
		SampleDto result = service.saveOrUpdateListPerson(dto, id);
		return new ResponseEntity<SampleDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@Secured({NCoVConstant.ROLE_ADMIN,NCoVConstant.ROLE_USER})
	@RequestMapping(value = "/updateResultSample/{result}", method = RequestMethod.PUT)
	public ResponseEntity<List<SampleDto>> updateResultSample(@RequestBody List<SampleDto> list, @PathVariable("result") String resultSample) {
		List<SampleDto> result = service.updateResultSample(list, resultSample);
		return new ResponseEntity<List<SampleDto>>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
}
