package com.globits.covid19.test.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.covid19.test.domain.NCoVAdministrativeUnit;
import com.globits.covid19.test.dto.NCoVAdministrativeUnitDto;

@Repository
public interface NCoVAdministrativeUnitRepository extends JpaRepository<NCoVAdministrativeUnit, UUID> {

	List<UUID> getAllIdByParentId(UUID parentId);

	List<NCoVAdministrativeUnit> getAllEntityByParentId(UUID uuid);

	List<NCoVAdministrativeUnitDto> getAllByParentId(UUID parentId);

	NCoVAdministrativeUnitDto getById(UUID parentId);
	
	@Query("select u from NCoVAdministrativeUnit u  where u.id = ?1")
	NCoVAdministrativeUnit findById(Long id);

	@Query("select u from NCoVAdministrativeUnit u  where u.id = ?1")
	NCoVAdministrativeUnit findOne(UUID parentId);

	List<NCoVAdministrativeUnit> findListByCode(String code);

	@Query("select new com.globits.covid19.test.dto.NCoVAdministrativeUnitDto(administrative,true) from NCoVAdministrativeUnit administrative ")
	List<NCoVAdministrativeUnitDto> getAll();

	@Query("select new com.globits.covid19.test.dto.NCoVAdministrativeUnitDto(administrative,true, 1) from NCoVAdministrativeUnit administrative ")
	List<NCoVAdministrativeUnitDto> getAllBasic();

	@Query("select new com.globits.covid19.test.dto.NCoVAdministrativeUnitDto(administrative,true, 1) from NCoVAdministrativeUnit administrative WHERE administrative.id NOT IN(?1) ")
	List<NCoVAdministrativeUnitDto> getAllBasicInEdit(UUID id);
	
	@Query("select administrative from NCoVAdministrativeUnit administrative WHERE administrative.name =?1 and administrative.parent.name =?2 ")
	List<NCoVAdministrativeUnit> getAdministrativeUnitByName(String  name ,String parentName );

	@Query("select administrative from NCoVAdministrativeUnit administrative WHERE administrative.parent.id =?2 and (administrative.code LIKE ?1 OR administrative.name LIKE ?1) ")
	List<NCoVAdministrativeUnit> getByNameOrCodeAndParentId(String nameCode, UUID id);

	@Query("select administrative from NCoVAdministrativeUnit administrative WHERE administrative.parent IS NULL AND (administrative.code LIKE ?1 OR administrative.name LIKE ?1) ")
	List<NCoVAdministrativeUnit> getProvinceByNameOrCode(String provinceImport);

}
