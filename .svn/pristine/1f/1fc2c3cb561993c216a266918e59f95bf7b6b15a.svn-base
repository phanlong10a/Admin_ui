package com.globits.covid19.test.service.impl;

import com.globits.core.domain.AdministrativeUnit;
import com.globits.core.repository.AdministrativeUnitRepository;
import com.globits.covid19.test.domain.HealthResource;
import com.globits.covid19.test.domain.HealthResourceCategory;
import com.globits.covid19.test.domain.Sample;
import com.globits.covid19.test.domain.SuspectedPerson;
import com.globits.covid19.test.domain.UserOrganization;
import com.globits.covid19.test.dto.HealthResourceDto;
import com.globits.covid19.test.dto.SampleDto;
import com.globits.covid19.test.dto.SampleSearchDto;
import com.globits.covid19.test.dto.SearchDto;
import com.globits.covid19.test.dto.UserInfoDto;
import com.globits.covid19.test.repository.HealthResourceCategoryRepository;
import com.globits.covid19.test.repository.HealthResourceRepository;
import com.globits.covid19.test.repository.SampleRepository;
import com.globits.covid19.test.repository.SuspectedPersonRepository;
import com.globits.covid19.test.service.HealthResourceService;
import com.globits.covid19.test.service.HealthResourceService;
import com.globits.covid19.test.service.UserOrganizationService;
import com.globits.covid19.test.utilities.Enums;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Transactional
@Service
public class HealthResourceImpl extends GenericServiceImpl<HealthResource, UUID>
		implements HealthResourceService {

	@Autowired
	private EntityManager manager;

	@Autowired
	public SampleRepository sampleRepository;

	@Autowired
	public SuspectedPersonRepository personRepository;

	@Autowired
	public HealthResourceRepository healthResourceRepository;
	
	@Autowired
	public HealthResourceCategoryRepository healthResourceCategoryRepository;

	@Override
	public Page<HealthResourceDto> searchByDto(SearchDto dto) {
		if (dto == null) {
			return null;
		}
		
		int pageIndex = dto.getPageIndex();
		int pageSize = dto.getPageSize();

		if (pageIndex > 0) {
			pageIndex--;
		} else {
			pageIndex = 0;
		}

		String whereClause = "";
		String orderBy = " ";
		String sqlCount = " select count(entity.id) from HealthResource as entity where (1=1)  ";
		String sql = " select new com.globits.covid19.test.dto.HealthResourceDto(entity) from HealthResource as entity where (1=1) ";

		if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
			whereClause += " AND (entity.name LIKE :text OR entity.code LIKE :text) ";
		}

		sql += whereClause + orderBy;
		sqlCount += whereClause;
		Query q = manager.createQuery(sql, HealthResourceDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
			q.setParameter("text", '%' + dto.getText().trim() + '%');
			qCount.setParameter("text", '%' + dto.getText().trim() + '%');
		}

		int startPosition = pageIndex * pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<HealthResourceDto> entities = q.getResultList();
		long count = (long) qCount.getSingleResult();

		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<HealthResourceDto> result = new PageImpl<HealthResourceDto>(entities, pageable, count);

		return result;
	}

	@Override
	public HealthResourceDto saveOrUpdate(HealthResourceDto dto, UUID id) {
		if (dto != null) {
			HealthResource entity = null;
			if (id != null) {
				entity = healthResourceRepository.getOne(id);
			}
			if (entity == null) {
				entity = new HealthResource();
			}
			entity.setName(dto.getName());
			entity.setCode(dto.getCode());
			
			HealthResourceCategory c = new HealthResourceCategory();
			if(dto.getCategory() != null) {
				c = healthResourceCategoryRepository.getOne(dto.getCategory().getId());
				if(c == null) {
					return null;
				}
			}
			entity.setCategory(c);

			entity = healthResourceRepository.save(entity);
			if (entity != null) {
				return new HealthResourceDto(entity);
			}
		}
		return null;
	}

	@Override
	public HealthResourceDto getById(UUID id) {
		HealthResource entity = healthResourceRepository.getOne(id);
		if (entity != null) {
			return new HealthResourceDto(entity);
		}
		return null;
	}

	@Override
	public Boolean deleteById(UUID id) {
		if (id != null) {
			healthResourceRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public Boolean checkCode(UUID id, String code) {
		if (code != null && StringUtils.hasText(code)) {
			Long count = healthResourceRepository.checkCode(code, id);
			return count != 0l;
		}
		return null;
	}
}
