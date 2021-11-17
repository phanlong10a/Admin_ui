package com.globits.covid19.test.service;

import java.util.List;
import java.util.UUID;

import com.globits.covid19.test.dto.*;
import org.springframework.data.domain.Page;

import com.globits.covid19.test.domain.SuspectedPerson;

public interface SuspectedPersonService extends GenericService<SuspectedPerson, UUID> {

	SuspectedPersonDto saveOrUpdate(SuspectedPersonDto dto, UUID id);

	SuspectedPersonDto getById(UUID id);

	Boolean deleteById(UUID id);

    Page<SuspectedPersonDto> searchByDto(SuspectedPersonSearchDto dto);

	ImportSuspectedPersonResultDto importSusPerson(List<SuspectedPersonDto> dto);

	Boolean checkDuplicateCode(UUID id, String idNumber);

	ImportSuspectedPersonResultDto checkDuplicateCode(List<SuspectedPersonDto> listSusPerson);

	ImportSuspectedPersonResultDto saveListData(List<SuspectedPersonDto> listData);

}
