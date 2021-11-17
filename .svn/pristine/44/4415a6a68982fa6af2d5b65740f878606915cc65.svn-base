package com.globits.covid19.test.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.covid19.test.domain.UserOrganization;
import com.globits.covid19.test.dto.UserOrganizationDto;

@Repository
public interface UserOrganizationRepository extends JpaRepository<UserOrganization, UUID>{
	@Query("select entity FROM UserOrganization entity where entity.user.id =?1 ")
	List<UserOrganization> getAllByUserId(Long id);
	
	@Query("select new com.globits.covid19.test.dto.UserOrganizationDto(entity) FROM UserOrganization entity where entity.user.id =?1 ")
	List<UserOrganization> getAllOrgByUserId(Long id);
	
	@Query("select entity FROM UserOrganization entity where entity.user.email =?1 ")
	List<UserOrganization> findByEmail(String email);
	
	@Query("select entity FROM UserOrganization entity where entity.user.username =?1 ")
	List<UserOrganization> findByusername(String username);

	@Query("select entity FROM UserOrganization entity where entity.user.id =?1 ")
	List<UserOrganizationDto> getByUserId(Long id);
}
