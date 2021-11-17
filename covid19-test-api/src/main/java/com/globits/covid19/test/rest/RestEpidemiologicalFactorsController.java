package com.globits.covid19.test.rest;

import com.globits.covid19.test.dto.EpidemiologicalFactorsDto;
import com.globits.covid19.test.dto.EpidemiologicalFactorsSearchDto;
import com.globits.covid19.test.service.EpidemiologicalFactorsService;
import com.globits.covid19.test.utilities.NCoVConstant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/epidemiologicalFactors")
public class RestEpidemiologicalFactorsController {

	@Autowired
	private EpidemiologicalFactorsService service;
	@Secured({NCoVConstant.ROLE_ADMIN, NCoVConstant.ROLE_USER})
	@RequestMapping(value = "/searchByDto", method = RequestMethod.POST)
	public ResponseEntity<Page<EpidemiologicalFactorsDto>> searchByDto(@RequestBody EpidemiologicalFactorsSearchDto dto) {
		Page<EpidemiologicalFactorsDto> result = service.searchByDto(dto);
		return new ResponseEntity<Page<EpidemiologicalFactorsDto>>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	@Secured({NCoVConstant.ROLE_ADMIN})
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<EpidemiologicalFactorsDto> getById(@PathVariable UUID id) {
		EpidemiologicalFactorsDto result = service.getById(id);
		return new ResponseEntity<EpidemiologicalFactorsDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	@Secured({NCoVConstant.ROLE_ADMIN})
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<EpidemiologicalFactorsDto> save(@RequestBody EpidemiologicalFactorsDto dto) {
		EpidemiologicalFactorsDto result = service.saveOrUpdate(dto, null);
		return new ResponseEntity<EpidemiologicalFactorsDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	@Secured({NCoVConstant.ROLE_ADMIN})
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<EpidemiologicalFactorsDto> update(@RequestBody EpidemiologicalFactorsDto dto, @PathVariable("id") UUID id) {
		EpidemiologicalFactorsDto result = service.saveOrUpdate(dto, id);
		return new ResponseEntity<EpidemiologicalFactorsDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
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
