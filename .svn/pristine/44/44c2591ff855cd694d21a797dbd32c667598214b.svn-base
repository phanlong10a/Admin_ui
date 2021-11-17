package com.globits.covid19.test.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.globits.covid19.test.domain.HealthOrganization;
import com.globits.covid19.test.dto.HealthOrganizationDto;
import com.globits.covid19.test.dto.HealthOrganizationSearchDto;
import com.globits.covid19.test.dto.UserInfoDto;

public interface HealthOrganizationService extends GenericService<HealthOrganization, UUID> {

	Page<HealthOrganizationDto> searchByDto(HealthOrganizationSearchDto dto);

	HealthOrganizationDto saveOrUpdate(HealthOrganizationDto dto, UUID id);

	HealthOrganizationDto getById(UUID id);

	Boolean deleteById(UUID id);

	Boolean checkCode (UUID id,String code);
//	HealthOrganizationDto getByCode(String code);

	List<HealthOrganizationDto> getHealthOrganizationsByUserInfoDto(UserInfoDto userOrganization);

	List<HealthOrganizationDto> getChildHealthOrganizations(UUID healthOrganizationId);

	List<HealthOrganizationDto> getHealthOrganizationsByTypeAnalytics(String typeAnalytics);
}
