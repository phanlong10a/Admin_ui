package com.globits.covid19.test.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.covid19.test.domain.SuspectedLevel;
@Repository
public interface SuspectedLevelRepository extends JpaRepository<SuspectedLevel, UUID>{
    @Query("select count(entity.id) FROM SuspectedLevel entity where entity.code =?1 and (entity.id <> ?2 or ?2 is null) ")
    Long checkCode(String code, UUID id);

    @Query("select count(entity.id) FROM SuspectedLevel entity where entity.code =?1 ")
    Long checkCode(String code);
    
    @Query("Select count(entity.id) from SuspectedLevel entity where entity.parent.id =?1")
    Integer countChidren(UUID id);

    @Query("select entity FROM SuspectedLevel entity where entity.code = ?1 OR entity.name = ?1 ")
	List<SuspectedLevel> getByNameOrCode(String importSuspectedLevel);
}
