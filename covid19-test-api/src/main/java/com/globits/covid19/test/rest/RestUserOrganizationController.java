package com.globits.covid19.test.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.globits.covid19.test.dto.UserInfoDto;
import com.globits.covid19.test.dto.UserOrgSearch;
import com.globits.covid19.test.dto.UserOrganizationDto;
import com.globits.covid19.test.service.UserOrganizationService;
import com.globits.security.dto.RoleDto;
import com.globits.security.dto.UserDto;
import com.globits.security.service.UserService;

@RestController
@RequestMapping("/api/userOrganization")
public class RestUserOrganizationController {
	@Autowired
	private UserOrganizationService service;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/searchByDto", method = RequestMethod.POST)
    public ResponseEntity<Page<UserOrganizationDto>> searchByDto(@RequestBody UserOrgSearch dto) {
        Page<UserOrganizationDto> result = service.searchByDto(dto);
        return new ResponseEntity<Page<UserOrganizationDto>>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UserOrganizationDto> save(@RequestBody UserOrganizationDto dto) {
    	UserOrganizationDto result = service.saveOrUpdate(dto, null);
        return new ResponseEntity<UserOrganizationDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<UserOrganizationDto> update(@RequestBody UserOrganizationDto dto, @PathVariable("id") UUID id) {
    	UserOrganizationDto result = service.saveOrUpdate(dto, id);
        return new ResponseEntity<UserOrganizationDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserOrganizationDto> getById(@PathVariable UUID id) {
    	UserOrganizationDto result = service.getById(id);
        return new ResponseEntity<UserOrganizationDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/getAllOrgByUserId/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserOrganizationDto> getAllOrgByUserId(@PathVariable Long id) {
        UserOrganizationDto result = service.getUserOrganizationDtoByUserId(id);
        return new ResponseEntity<UserOrganizationDto>(result,
                (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/check-email", method = RequestMethod.POST)
    public boolean checkEmail(@RequestBody UserOrganizationDto dto) {
    	boolean result = true;
    	result = service.checkEmail(dto.getUser().getEmail(), dto.getUser().getId());
    	return result;
    }
    
    @RequestMapping(value = "/check-username", method = RequestMethod.POST)
    public boolean checkUsername(@RequestBody UserOrganizationDto dto) {
    	boolean result = true;
    	result = service.checkUsername(dto.getUser().getUsername(), dto.getUser().getId());
    	return result;
    }
    
    @RequestMapping(value = "/users", method = RequestMethod.PUT)
	public UserDto updateUser(@RequestBody UserDto user) {
		return userService.save(user);
	}

    @RequestMapping(value = "/users", method = RequestMethod.POST)
	public UserDto saveUser(@RequestBody UserDto user) {
		return userService.save(user);
	}
    
    @RequestMapping(value = "/getAllInfoByUserLogin", method = RequestMethod.GET)
    public ResponseEntity<UserInfoDto> getAllInfoByUserLogin() {
    	UserInfoDto result = service.getAllInfoByUserLogin();
        return new ResponseEntity<UserInfoDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
    
    @RequestMapping(value = "/getRoleUser", method = RequestMethod.GET)
    public ResponseEntity<List<RoleDto>> getRoleUser() {
    	List<RoleDto> result = service.getRoleUser();
        return new ResponseEntity<List<RoleDto>>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
    
    @RequestMapping(value = "/getUserByUsername", method = RequestMethod.GET)
    public UserDto findByUsername(@RequestParam(value = "username", required = true) String username) {
		if (username == null) {
			return null;
		}
		return userService.findByUsername(username);
	}
    
    @RequestMapping(value = "/getUserByEmail", method = RequestMethod.GET)
    public UserDto findByEmail(@RequestParam(value = "email", required = true) String email) {
		if (email == null) {
			return null;
		}
		return userService.findByEmail(email);
	}
}
