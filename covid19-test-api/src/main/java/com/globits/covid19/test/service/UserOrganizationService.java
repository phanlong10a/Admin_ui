package com.globits.covid19.test.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.globits.covid19.test.dto.UserInfoDto;
import com.globits.covid19.test.dto.UserOrgSearch;
import com.globits.covid19.test.dto.UserOrganizationDto;
import com.globits.security.domain.User;
import com.globits.security.dto.RoleDto;
import com.globits.security.dto.UserDto;

@Service
public interface UserOrganizationService {
//	UserInfoDto getUserInfoByUserLogin();
	Page<UserOrganizationDto> searchByDto(UserOrgSearch dto);
	UserOrganizationDto saveOrUpdate(UserOrganizationDto dto, UUID id);
	UserOrganizationDto getById(UUID id);
	List<UserOrganizationDto> getAllOrgByUserId(Long id);
	boolean checkEmail(String email, Long id);
	boolean checkUsername(String username, Long id);
	UserInfoDto getAllInfoByUserLogin();
	UserDto getCurrentUserDto();
	User getCurrentUser();
    UserOrganizationDto getUserOrganizationDtoByUserId(Long id);
	List<UUID> findAllChildHealthOrganizationById(UUID id, UserInfoDto info);
	List<RoleDto> getRoleUser();
}
