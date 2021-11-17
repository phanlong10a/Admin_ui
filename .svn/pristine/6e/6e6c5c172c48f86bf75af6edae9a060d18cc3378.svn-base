package com.globits.covid19.test.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.covid19.test.domain.HealthOrganization;
import com.globits.covid19.test.dto.HealthOrganizationDto;

@Repository
public interface HealthOrganizationRepository extends JpaRepository<HealthOrganization, UUID>{
    @Query("select count(entity.id) FROM HealthOrganization entity where entity.code =?1 and (entity.id <> ?2 or ?2 is null) ")
    Long checkCode(String code, UUID id);

    @Query("select count(entity.id) FROM HealthOrganization entity where entity.code =?1 ")
    Long checkCode(String code);

	@Query("select entity.id from HealthOrganization entity "
			+ " where entity.parent.id = ?1 AND (entity.voided IS NULL OR entity.voided = false) ")
	List<UUID> getAllIdByParentId(UUID parentId);
	
	@Query("select uo.org FROM UserOrganization uo where uo.user.id =?1 ")
	HealthOrganization getOrgByUser(Long userId);

    @Query("select new com.globits.covid19.test.dto.HealthOrganizationDto(entity) FROM HealthOrganization entity where entity.id =?1 ")
	HealthOrganizationDto findOne(UUID id);

    @Query("select entity FROM HealthOrganization entity where entity.id =?1 ")
	HealthOrganization getOneById(UUID id);
    
    @Query("select new com.globits.covid19.test.dto.HealthOrganizationDto(entity) FROM HealthOrganization entity where entity.name =?1 ")
   	List<HealthOrganizationDto> getByName(String name);

    @Query("select new com.globits.covid19.test.dto.HealthOrganizationDto(entity) FROM HealthOrganization entity where entity.code = ?1 OR entity.name = ?1 ")
	List<HealthOrganizationDto> getByNameOrCode(String format);
    
    @Query("select new com.globits.covid19.test.dto.HealthOrganizationDto(entity) FROM HealthOrganization entity where (1 = 1) AND (entity.code = ?1 OR entity.name = ?1) AND (entity.orgType = 1 OR entity.orgType = 0) ")
   	List<HealthOrganizationDto> getByNameOrCodeLabTest(String format);

    @Query("select new com.globits.covid19.test.dto.HealthOrganizationDto(entity) FROM HealthOrganization entity where (1 = 1) AND (entity.code = ?1 OR entity.name = ?1) AND (entity.orgType = 2 OR entity.orgType = 0) ")
   	List<HealthOrganizationDto> getByNameOrCodeCollectSample(String format);
    
    @Query("select entity FROM HealthOrganization entity where (1 = 1) AND (entity.code = ?1 OR entity.name = ?1) AND (entity.orgType = 3 OR entity.orgType = 0) ")
   	List<HealthOrganization> getByNameOrCodeIsolation(String format);
}
