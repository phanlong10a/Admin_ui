package com.globits.covid19.test.repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.covid19.test.domain.Sample;
import com.globits.covid19.test.dto.SampleDto;

@Repository
public interface SampleRepository extends JpaRepository<Sample, UUID> {
	@Query("select entity FROM Sample entity where entity.code =?1 ")
	List<Sample> getByCode(String code);
	
	@Query("select count(s.id)  from Sample s where s.testingDate is not null and "
			+ " s.sampleDate is not null and s.sampleDate > ?1 and s.sampleDate <= ?2 and s.sampleStatus != ?3 ")
	Long getTestCases(Date fromDate,Date toDate,String code);
	
	@Query("select count(s.id)  from Sample s where s.testingDate is null and "
			+ "	 s.sampleDate is not null and s.sampleDate > ?1 and s.sampleDate <= ?2  ")
	Long getRemainedCases(Date fromDate,Date toDate,String code);

	@Query("select entity.code FROM Sample entity where entity.code IN (?1) ")
	List<String> findCodeInListCode(List<String> listCode);

	@Query("select new com.globits.covid19.test.dto.SampleDto(entity, false) FROM Sample entity where entity.code LIKE ?1 ")
	List<SampleDto> getListByCode(String code);
}
