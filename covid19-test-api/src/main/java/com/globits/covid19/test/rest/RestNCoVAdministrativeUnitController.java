package com.globits.covid19.test.rest;

import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;

import com.globits.covid19.test.dto.NCoVAdministrativeUnitDto;
import com.globits.covid19.test.dto.NCoVAdministrativeUnitSearchDto;
import com.globits.covid19.test.dto.SearchDto;
import com.globits.covid19.test.service.NCoVAdministrativeUnitService;
import com.globits.covid19.test.utilities.NCoVConstant;

@RestController
@RequestMapping("/api/administrativeUnit")
public class RestNCoVAdministrativeUnitController {

	@Autowired
	private NCoVAdministrativeUnitService service;
	@Secured({NCoVConstant.ROLE_ADMIN,NCoVConstant.ROLE_USER})
	@RequestMapping(value = "searchByDto", method = RequestMethod.POST)
	public ResponseEntity<Page<NCoVAdministrativeUnitDto>> searchByDto(@RequestBody NCoVAdministrativeUnitSearchDto dto) {
		Page<NCoVAdministrativeUnitDto> result = service.searchByDto(dto);
		return new ResponseEntity<Page<NCoVAdministrativeUnitDto>>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	@Secured({NCoVConstant.ROLE_ADMIN,NCoVConstant.ROLE_USER})
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<NCoVAdministrativeUnitDto> getById(@PathVariable UUID id) {
		NCoVAdministrativeUnitDto result = service.getById(id);
		return new ResponseEntity<NCoVAdministrativeUnitDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	@Secured({NCoVConstant.ROLE_ADMIN,NCoVConstant.ROLE_USER})
	@RequestMapping(value = {"/getAllBasicInEdit", "getAllBasicInEdit/{id}"}, method = RequestMethod.GET)
	public ResponseEntity<List<NCoVAdministrativeUnitDto>> getAllBasicInEdit(@PathVariable (name = "id", required = false) final UUID id) {
		List<NCoVAdministrativeUnitDto> result = service.getAllBasicInEdit(id);
		return new ResponseEntity<List<NCoVAdministrativeUnitDto>>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	@Secured({NCoVConstant.ROLE_ADMIN,NCoVConstant.ROLE_USER})
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<NCoVAdministrativeUnitDto> save(@RequestBody NCoVAdministrativeUnitDto dto) {
		NCoVAdministrativeUnitDto result = service.saveOrUpdate(dto, null);
		return new ResponseEntity<NCoVAdministrativeUnitDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	@Secured({NCoVConstant.ROLE_ADMIN,NCoVConstant.ROLE_USER})
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<NCoVAdministrativeUnitDto> update(@RequestBody NCoVAdministrativeUnitDto dto, @PathVariable("id") UUID id) {
		NCoVAdministrativeUnitDto result = service.saveOrUpdate(dto, id);
		return new ResponseEntity<NCoVAdministrativeUnitDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	@Secured({NCoVConstant.ROLE_ADMIN,NCoVConstant.ROLE_USER})
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> deleteById(@PathVariable("id") String id) {
		Boolean result = service.deleteById(UUID.fromString(id));
		return new ResponseEntity<Boolean>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}


}
