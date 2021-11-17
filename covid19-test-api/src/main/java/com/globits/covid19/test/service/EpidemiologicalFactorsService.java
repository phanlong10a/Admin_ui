package com.globits.covid19.test.service;

import com.globits.covid19.test.domain.EpidemiologicalFactors;
import com.globits.covid19.test.dto.EpidemiologicalFactorsDto;
import com.globits.covid19.test.dto.EpidemiologicalFactorsSearchDto;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface EpidemiologicalFactorsService extends GenericService<EpidemiologicalFactors, UUID> {

	Page<EpidemiologicalFactorsDto> searchByDto(EpidemiologicalFactorsSearchDto dto);

	EpidemiologicalFactorsDto saveOrUpdate(EpidemiologicalFactorsDto dto, UUID id);

	EpidemiologicalFactorsDto getById(UUID id);

	Boolean deleteById(UUID id);

	Boolean checkCode(String code, UUID id);

}
