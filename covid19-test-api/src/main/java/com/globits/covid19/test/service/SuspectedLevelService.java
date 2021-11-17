package com.globits.covid19.test.service;

import com.globits.covid19.test.domain.SuspectedLevel;
import com.globits.covid19.test.dto.SampleSearchDto;
import com.globits.covid19.test.dto.SuspectedLevelDto;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface SuspectedLevelService extends GenericService<SuspectedLevel, UUID> {

	Page<SuspectedLevelDto> searchByDto(SampleSearchDto dto);

	SuspectedLevelDto saveOrUpdate(SuspectedLevelDto dto, UUID id);

	SuspectedLevelDto getById(UUID id);

	Boolean deleteById(UUID id);

	Boolean checkCode(String code, UUID id);

}
