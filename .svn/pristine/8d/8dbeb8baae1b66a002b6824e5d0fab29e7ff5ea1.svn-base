package com.globits.covid19.test.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.covid19.test.domain.IsolationCenter;

@Repository
public interface IsolationCenterRepository extends JpaRepository<IsolationCenter, UUID> {
	@Query("select count(entity.id) FROM IsolationCenter entity where entity.code =?1 and (entity.id <> ?2 or ?2 is null) ")
    Long checkCode(String code, UUID id);
}
