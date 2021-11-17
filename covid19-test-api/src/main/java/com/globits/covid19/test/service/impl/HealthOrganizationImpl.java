package com.globits.covid19.test.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.globits.core.repository.AdministrativeUnitRepository;
import com.globits.covid19.test.domain.EpidemiologicalFactors;
import com.globits.covid19.test.domain.HealthOrganization;
import com.globits.covid19.test.domain.SuspectedLevel;
import com.globits.covid19.test.dto.HealthOrganizationDto;
import com.globits.covid19.test.dto.HealthOrganizationSearchDto;
import com.globits.covid19.test.dto.UserInfoDto;
import com.globits.covid19.test.repository.HealthOrganizationRepository;
import com.globits.covid19.test.repository.SampleRepository;
import com.globits.covid19.test.repository.SuspectedPersonRepository;
import com.globits.covid19.test.service.HealthOrganizationService;
import com.globits.covid19.test.service.UserOrganizationService;
import com.globits.covid19.test.utilities.Enums;

@Transactional
@Service
public class HealthOrganizationImpl extends GenericServiceImpl<HealthOrganization, UUID>
		implements HealthOrganizationService {

	@Autowired
	private EntityManager manager;

	@Autowired
	public SampleRepository sampleRepository;

	@Autowired
	public SuspectedPersonRepository personRepository;

	@Autowired
	public HealthOrganizationRepository healthOrganizationRepository;

	@Autowired
	public AdministrativeUnitRepository administrativeUnitRepository;

	@Autowired
	public UserOrganizationService userOrganizationService;

	@Override
	public Page<HealthOrganizationDto> searchByDto(HealthOrganizationSearchDto dto) {
		if (dto == null)
			return null;
		List<Integer> orgTypes = new ArrayList<>();
		UserInfoDto userOrganization = userOrganizationService.getAllInfoByUserLogin();
		if (userOrganization == null || !userOrganization.isAdmin()
				&& userOrganization.isUser() && (userOrganization.getListChildHealthOrganizationId() == null
						|| userOrganization.getListChildHealthOrganizationId().size() <= 0))
			return null;
		int pageIndex = dto.getPageIndex();
		int pageSize = dto.getPageSize();

		if (pageIndex > 0)
			pageIndex--;
		else
			pageIndex = 0;

		String whereClause = "";
		String orderBy = " ";
		String sqlCount = " select count(entity.id) from HealthOrganization as entity where (1=1)  ";
		String sql = " select new com.globits.covid19.test.dto.HealthOrganizationDto(entity) from HealthOrganization as entity where (1=1) ";

		if (dto.getText() != null && StringUtils.hasText(dto.getText()))
			whereClause += " AND (entity.name LIKE :text OR entity.code LIKE :text) ";

		if (dto.getOrgType() != null) {
			orgTypes.add(dto.getOrgType());
			if (!dto.getOrgType().equals(Enums.OrganizationTypeEnum.Both.getType())) {
				orgTypes.add(Enums.OrganizationTypeEnum.Both.getType());
				whereClause += " AND (entity.orgType IN (:orgTypes) ) ";
			}
		}
		if (!userOrganization.isAdmin()
				&& userOrganization.isUser() && userOrganization.getListChildHealthOrganizationId() != null
				&& !dto.getIsGetAll())
			whereClause += " AND (entity.id IN (:listHealthOrganizationId)) ";
		if (!userOrganization.isAdmin())
			if (dto.isNotInOrgIdByUserLogin())
				if (userOrganization.getUserOrganization() != null
						&& userOrganization.getUserOrganization().getOrg() != null
						&& userOrganization.getUserOrganization().getOrg().getId() != null)
					whereClause += " AND (entity.id != :orgIdByUserLogin) ";
				else
					return null;
		
		if(dto.getIsExternalOrg() != null)
			if(dto.getIsExternalOrg())
				whereClause += " AND (entity.isExternalOrg = :isExternalOrg) ";
			else
				whereClause += " AND (entity.isExternalOrg = :isExternalOrg OR entity.isExternalOrg IS NULL) ";

		sql += whereClause + orderBy;
		sqlCount += whereClause;
		Query q = manager.createQuery(sql, HealthOrganizationDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
			q.setParameter("text", '%' + dto.getText().trim() + '%');
			qCount.setParameter("text", '%' + dto.getText().trim() + '%');
		}

		if (dto.getOrgType() != null)
			if (!dto.getOrgType().equals(Enums.OrganizationTypeEnum.Both.getType())) {
				q.setParameter("orgTypes", orgTypes);
				qCount.setParameter("orgTypes", orgTypes);
			}
		if (!userOrganization.isAdmin()
				&& userOrganization.isUser() && userOrganization.getListChildHealthOrganizationId() != null
				&& !dto.getIsGetAll()) {
			q.setParameter("listHealthOrganizationId", userOrganization.getListChildHealthOrganizationId());
			qCount.setParameter("listHealthOrganizationId", userOrganization.getListChildHealthOrganizationId());
		}
		if (!userOrganization.isAdmin())
			if (dto.isNotInOrgIdByUserLogin())
				if (userOrganization.getUserOrganization() != null
						&& userOrganization.getUserOrganization().getOrg() != null
						&& userOrganization.getUserOrganization().getOrg().getId() != null) {
					q.setParameter("orgIdByUserLogin", userOrganization.getUserOrganization().getOrg().getId());
					qCount.setParameter("orgIdByUserLogin", userOrganization.getUserOrganization().getOrg().getId());
				}
		if(dto.getIsExternalOrg() != null) {
			q.setParameter("isExternalOrg", dto.getIsExternalOrg());
			qCount.setParameter("isExternalOrg", dto.getIsExternalOrg());
		}
		int startPosition = pageIndex * pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<HealthOrganizationDto> entities = q.getResultList();
		long count = (long) qCount.getSingleResult();

		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<HealthOrganizationDto> result = new PageImpl<>(entities, pageable, count);

		return result;
	}

	@Override
	public HealthOrganizationDto saveOrUpdate(HealthOrganizationDto dto, UUID id) {
		if (dto != null) {
			HealthOrganization entity = null;
			if (id != null)
				entity = healthOrganizationRepository.getOne(id);
			if (entity == null)
				entity = new HealthOrganization();
			entity.setName(dto.getName());
			entity.setCode(dto.getCode());
			entity.setOrgType(dto.getOrgType());
			entity.setIsExternalOrg(dto.getIsExternalOrg());
			if (dto.getParent() != null && dto.getParent().getId() != null) {
				HealthOrganization parent = healthOrganizationRepository.findById(dto.getParent().getId()).get();
				entity.setParent(parent);
			}

			entity = healthOrganizationRepository.save(entity);
			if (entity != null)
				return new HealthOrganizationDto(entity);
		}
		return null;
	}

	@Override
	public HealthOrganizationDto getById(UUID id) {
		HealthOrganization entity = healthOrganizationRepository.getOne(id);
		if (entity != null)
			return new HealthOrganizationDto(entity);
		return null;
	}

	@Override
	public Boolean deleteById(UUID id) {
		if (id != null) {
			healthOrganizationRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public Boolean checkCode(UUID id, String code) {
		if (code != null && StringUtils.hasText(code)) {
			Long count = healthOrganizationRepository.checkCode(code, id);
			return count != 0l;
		}
		return null;
	}

	@Override
	public List<HealthOrganizationDto> getHealthOrganizationsByUserInfoDto(UserInfoDto userOrganization) {
		List<HealthOrganizationDto> healthOrganizations = null;

		if(userOrganization != null){
			healthOrganizations = new ArrayList<>();

			// add organization's child name
			if(userOrganization.getListChildHealthOrganizationId() != null)
				for(UUID uuid: userOrganization.getListChildHealthOrganizationId()){
					HealthOrganizationDto healthOrganizationDto = getById(uuid);

					healthOrganizations.add(healthOrganizationDto);
				}
		}

		return healthOrganizations;
	}

	@Override
	public List<HealthOrganizationDto> getChildHealthOrganizations(UUID healthOrganizationId) {
		List<HealthOrganizationDto> healthOrganizations = null;

		HealthOrganization parentHealthOrganization = healthOrganizationRepository.getOne(healthOrganizationId);

		HealthOrganizationDto healthOrganizationDto = null;
		if(parentHealthOrganization != null)
			healthOrganizationDto = new HealthOrganizationDto(parentHealthOrganization, false);

		if(healthOrganizationDto.getChidren() != null)
			healthOrganizations = healthOrganizationDto.getChidren();
		else
			healthOrganizations = new ArrayList<>();
		// add itself
		healthOrganizations.add(healthOrganizationDto);

		return healthOrganizations;
	}

	@Override
	public List<HealthOrganizationDto> getHealthOrganizationsByTypeAnalytics(String typeAnalytics) {

		String joinColumn = null;
		if(typeAnalytics != null){
			if(typeAnalytics.equals(EpidemiologicalFactors.class.getSimpleName()))
				joinColumn =
						" JOIN EpidemiologicalFactors epidemiological ON epidemiological.id = person.epidemiologicalFactors.id";
			else if(typeAnalytics.equals(SuspectedLevel.class.getSimpleName()))
				joinColumn = " JOIN SuspectedLevel level ON level.id = person.suspectedLevel.id";
		} else
			return null;

		String sql =
				"SELECT DISTINCT new com.globits.covid19.test.dto.HealthOrganizationDto(health) FROM HealthOrganization health"
						+ " JOIN Sample sample ON sample.labTest.id = health.id"
						+ " JOIN SuspectedPerson person ON person.id = sample.person.id"
						+ joinColumn;
		String orderBy = " ORDER BY health.name";

		sql += orderBy;

		Query query = manager.createQuery(sql, HealthOrganizationDto.class);
		List<HealthOrganizationDto> healthOrganizations = query.getResultList();

		return healthOrganizations;
	}
}
