package com.globits.covid19.test.rest;

import com.globits.covid19.test.dto.SampleSearchDto;
import com.globits.covid19.test.dto.SuspectedPersonDto;
import com.globits.covid19.test.dto.SuspectedPersonSearchDto;
import com.globits.covid19.test.service.SuspectedPersonService;
import com.globits.covid19.test.utilities.NCoVConstant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/suspectedPerson")
public class RestSuspectedPersonController {

    @Autowired
    private SuspectedPersonService service;
    @Secured({NCoVConstant.ROLE_ADMIN, NCoVConstant.ROLE_USER})
    @RequestMapping(value = "/searchByDto", method = RequestMethod.POST)
    public ResponseEntity<Page<SuspectedPersonDto>> searchByDto(@RequestBody SuspectedPersonSearchDto dto) {
        Page<SuspectedPersonDto> result = service.searchByDto(dto);
        return new ResponseEntity<Page<SuspectedPersonDto>>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
    @Secured({NCoVConstant.ROLE_ADMIN, NCoVConstant.ROLE_USER})
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<SuspectedPersonDto> getById(@PathVariable UUID id) {
        SuspectedPersonDto result = service.getById(id);
        return new ResponseEntity<SuspectedPersonDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
    @Secured({NCoVConstant.ROLE_ADMIN, NCoVConstant.ROLE_USER})
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<SuspectedPersonDto> save(@RequestBody SuspectedPersonDto dto) {
        SuspectedPersonDto result = service.saveOrUpdate(dto, null);
        return new ResponseEntity<SuspectedPersonDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
    @Secured({NCoVConstant.ROLE_ADMIN, NCoVConstant.ROLE_USER})
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<SuspectedPersonDto> update(@RequestBody SuspectedPersonDto dto, @PathVariable("id") UUID id) {
        SuspectedPersonDto result = service.saveOrUpdate(dto, id);
        return new ResponseEntity<SuspectedPersonDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
    @Secured({NCoVConstant.ROLE_ADMIN})
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") String id) {
        Boolean result = service.deleteById(UUID.fromString(id));
        return new ResponseEntity<Boolean>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

}
