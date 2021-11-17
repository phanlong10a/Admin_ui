package com.globits.covid19.test.repository;

import com.globits.covid19.test.domain.HealthResourceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HealthResourceCategoryRepository extends JpaRepository<HealthResourceCategory, UUID>{
    @Query("select count(entity.id) FROM HealthResourceCategory entity where entity.code =?1 and (entity.id <> ?2 or ?2 is null) ")
    Long checkCode(String code, UUID id);

    @Query("select count(entity.id) FROM HealthResourceCategory entity where entity.code =?1 ")
    Long checkCode(String code);
    
    @Query("select count(entity.id) FROM HealthResource entity where entity.category.id =?1 ")
    Long checkReferences(UUID cId);
}
