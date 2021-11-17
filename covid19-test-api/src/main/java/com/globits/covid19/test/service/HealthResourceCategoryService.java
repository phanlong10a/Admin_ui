package com.globits.covid19.test.service;

import com.globits.covid19.test.domain.HealthResourceCategory;
import com.globits.covid19.test.dto.HealthResourceCategoryDto;
import com.globits.covid19.test.dto.SearchDto;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface HealthResourceCategoryService extends GenericService<HealthResourceCategory, UUID> {

	Page<HealthResourceCategoryDto> searchByDto(SearchDto dto);

	HealthResourceCategoryDto saveOrUpdate(HealthResourceCategoryDto dto, UUID id);

	HealthResourceCategoryDto getById(UUID id);

	Boolean deleteById(UUID id);

	Boolean checkCode(UUID id, String code);

}
