package com.globits.covid19.test.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;

import com.globits.covid19.test.domain.Sample;
import com.globits.covid19.test.dto.ImportSampleResultsDto;
import com.globits.covid19.test.dto.SampleDto;
import com.globits.covid19.test.dto.SampleSearchDto;

public interface SampleService extends GenericService<Sample, UUID> {

	Page<SampleDto> searchByDto(SampleSearchDto dto);

	SampleDto saveOrUpdate(SampleDto dto, UUID id);

	SampleDto getById(UUID id);

	Boolean deleteById(UUID id);

	SampleDto getByCode(String code);

	Page<SampleDto> searchSampleAndUser(SampleSearchDto dto);
	
	ImportSampleResultsDto importSample(List<SampleDto> dto);

	Boolean checkDuplicateCode(UUID id, String code);

	ImportSampleResultsDto saveListData(List<SampleDto> listData);
	
	ByteArrayResource sampleToExcel(List<SampleDto> list) throws Exception;
	
	ImportSampleResultsDto importSampleGroup(List<SampleDto> dto,List<SampleDto> listGroupSample);
	
	ImportSampleResultsDto importSampleGroupData(List<SampleDto> dto);
	
	SampleDto saveOrUpdateListPerson(SampleDto dto, UUID id);
	
	List<SampleDto> updateResultSample(List<SampleDto> list, String resultSample);
	
	String autoGeneratorCode(String sampleCollectOrgName);
	
}
