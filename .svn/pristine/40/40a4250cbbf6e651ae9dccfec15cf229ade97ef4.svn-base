package com.globits.covid19.test.service;

import com.globits.covid19.test.domain.HealthResource;
import com.globits.covid19.test.dto.HealthResourceDto;
import com.globits.covid19.test.dto.SearchDto;

import org.springframework.data.domain.Page;

import java.util.UUID;

public interface HealthResourceService extends GenericService<HealthResource, UUID> {

	Page<HealthResourceDto> searchByDto(SearchDto dto);

	HealthResourceDto saveOrUpdate(HealthResourceDto dto, UUID id);

	HealthResourceDto getById(UUID id);

	Boolean deleteById(UUID id);

	Boolean checkCode (UUID id,String code);
}
