package com.globits.covid19.test.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.covid19.test.domain.HealthOrganization;
import com.globits.covid19.test.domain.HealthResource;
import com.globits.covid19.test.dto.HealthOrganizationDto;

@Repository
public interface HealthResourceRepository extends JpaRepository<HealthResource, UUID>{
    @Query("select count(entity.id) FROM HealthResource entity where entity.code =?1 and (entity.id <> ?2 or ?2 is null) ")
    Long checkCode(String code, UUID id);

    @Query("select count(entity.id) FROM HealthResource entity where entity.code =?1 ")
    Long checkCode(String code);

}
