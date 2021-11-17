package com.globits.covid19.test.service;

import com.globits.covid19.test.domain.SuspectedType;
import com.globits.covid19.test.dto.SampleSearchDto;
import com.globits.covid19.test.dto.SuspectedTypeDto;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface SuspectedTypeService extends GenericService<SuspectedType, UUID> {

	Page<SuspectedTypeDto> searchByDto(SampleSearchDto dto);

	SuspectedTypeDto saveOrUpdate(SuspectedTypeDto dto, UUID id);

	SuspectedTypeDto getById(UUID id);

	Boolean deleteById(UUID id);

	Boolean checkCode(String code, UUID id);

}
