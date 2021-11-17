package com.globits.covid19.test.repository;

import com.globits.covid19.test.domain.SuspectedType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface SuspectedTypeRepository extends JpaRepository<SuspectedType , UUID>{
    @Query("select count(entity.id) FROM SuspectedType entity where entity.code =?1 and (entity.id <> ?2 or ?2 is null) ")
    Long checkCode(String code, UUID id);

    @Query("select count(entity.id) FROM SuspectedType entity where entity.code =?1 ")
    Long checkCode(String code);
}
