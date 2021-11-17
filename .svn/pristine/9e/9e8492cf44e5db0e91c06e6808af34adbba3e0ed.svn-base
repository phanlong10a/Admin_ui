package com.globits.covid19.test.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.covid19.test.domain.EpidemiologicalFactors;

@Repository
public interface EpidemiologicalFactorsRepository extends JpaRepository<EpidemiologicalFactors, UUID> {
	@Query("select count(entity.id) FROM EpidemiologicalFactors entity where entity.code =?1 and (entity.id <> ?2 or ?2 is null) ")
    Long checkCode(String code, UUID id);

    @Query("select entity FROM EpidemiologicalFactors entity where entity.code = ?1 OR entity.name = ?1 ")
	List<EpidemiologicalFactors> getByNameOrCode(String format);
}
