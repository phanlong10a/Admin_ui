package com.globits.covid19.test.service;

import com.globits.covid19.test.domain.IsolationCenter;
import com.globits.covid19.test.dto.IsolationCenterDto;
import com.globits.covid19.test.dto.IsolationCenterSearchDto;

import org.springframework.data.domain.Page;

import java.util.UUID;

public interface IsolationCenterService extends GenericService<IsolationCenter, UUID> {

	Page<IsolationCenterDto> searchByDto(IsolationCenterSearchDto searchDto);

	IsolationCenterDto saveOrUpdate(IsolationCenterDto dto, UUID id);

	IsolationCenterDto getById(UUID id);

	Boolean deleteById(UUID id);

	Boolean checkCode(String code, UUID id);

}
