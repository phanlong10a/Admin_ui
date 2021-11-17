package com.globits.covid19.test.repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.covid19.test.domain.Sample;
import com.globits.covid19.test.domain.SuspectedPerson;

@Repository
public interface SuspectedPersonRepository extends JpaRepository<SuspectedPerson, UUID>{
//    @Query("select count(entity.id) FROM SuspectedPerson entity where entity.code =?1 and (entity.id <> ?2 or ?2 is null) ")
//    Long checkCode(String code, UUID id);
	
	@Query("select entity FROM SuspectedPerson entity where entity.name =?1 and entity.birthDate =?2 ")
	List<SuspectedPerson> getByPersonByName(String name,Date birthDate);

	@Query("select entity FROM SuspectedPerson entity where entity.id =?1 ")
	SuspectedPerson findOne(UUID id);

	@Query("select entity.idNumber FROM SuspectedPerson entity where entity.idNumber IN (?1) ")
	List<String> findCodeInListCode(List<String> listIdNumber);
}
