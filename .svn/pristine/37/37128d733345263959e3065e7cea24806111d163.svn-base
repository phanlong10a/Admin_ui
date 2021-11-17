package com.globits.covid19.test.service;

import com.globits.covid19.test.domain.NCoVAdministrativeUnit;
import com.globits.covid19.test.dto.NCoVAdministrativeUnitDto;
import com.globits.covid19.test.dto.NCoVAdministrativeUnitSearchDto;
import com.globits.covid19.test.dto.SearchDto;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

public interface NCoVAdministrativeUnitService extends GenericService<NCoVAdministrativeUnit, UUID> {

	List<UUID> getAllNCoVAdministrativeUnitIdByParentId(UUID administrativeUnitId);

	//hàm này chỉ lấy thằng con, ko lấy thằng cha
	List<UUID> getAllChildIdByParentId(UUID parentId);

	List<NCoVAdministrativeUnit> getAllChildByParentId(UUID parentId);

	List<NCoVAdministrativeUnitDto> getAllChildByParentId(UUID parentId, String prefix);

	NCoVAdministrativeUnitDto getById(UUID id);

	NCoVAdministrativeUnitDto saveOrUpdate(NCoVAdministrativeUnitDto dto, UUID id);

	Boolean deleteById(UUID id);

	List<NCoVAdministrativeUnitDto> getAll();

	List<NCoVAdministrativeUnitDto> getAllBasic();

	List<NCoVAdministrativeUnitDto> getAllBasicInEdit(UUID id);

	void saveOrUpdateList(List<NCoVAdministrativeUnitDto> listFmsAdministrativeUnit);

    Page<NCoVAdministrativeUnitDto> searchByDto(NCoVAdministrativeUnitSearchDto dto);
}
