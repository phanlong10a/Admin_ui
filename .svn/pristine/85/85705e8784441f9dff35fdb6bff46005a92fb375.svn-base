package com.globits.covid19.test.service.impl;

import com.globits.core.repository.AdministrativeUnitRepository;
import com.globits.covid19.test.domain.HealthResourceCategory;
import com.globits.covid19.test.domain.SuspectedLevel;
import com.globits.covid19.test.dto.HealthResourceCategoryDto;
import com.globits.covid19.test.dto.SampleSearchDto;
import com.globits.covid19.test.dto.SearchDto;
import com.globits.covid19.test.dto.SuspectedLevelDto;
import com.globits.covid19.test.repository.*;
import com.globits.covid19.test.service.HealthResourceCategoryService;
import com.globits.covid19.test.service.SuspectedLevelService;
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
import java.util.List;
import java.util.UUID;

@Transactional
@Service
public class HealthResourceCategoryImpl extends GenericServiceImpl<HealthResourceCategory, UUID> implements HealthResourceCategoryService {

	@Autowired
	private EntityManager manager;

	@Autowired
	public SampleRepository sampleRepository;

	@Autowired
	public SuspectedLevelRepository suspectedLevelRepo;
	
	@Autowired
	public SuspectedPersonRepository personRepository;
	
	@Autowired
	public HealthOrganizationRepository healthOrganizationRepository;
	
	@Autowired
	public AdministrativeUnitRepository administrativeUnitRepository;

	@Autowired
	public HealthResourceCategoryRepository healthResourceCategoryRepo;

	@Override
	public Page<HealthResourceCategoryDto> searchByDto(SearchDto dto) {
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
		String sqlCount = "select count(entity.id) from HealthResourceCategory as entity where (1=1) ";
		String sql = "select new com.globits.covid19.test.dto.HealthResourceCategoryDto(entity) from HealthResourceCategory as entity where (1=1) ";

		if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
			whereClause += " AND (entity.name LIKE :text " + "OR entity.code LIKE :text ) ";
		}

		sql += whereClause + orderBy;
		sqlCount += whereClause;
		Query q = manager.createQuery(sql, HealthResourceCategoryDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
			q.setParameter("text", '%' + dto.getText().trim() + '%');
			qCount.setParameter("text", '%' + dto.getText().trim() + '%');
		}
		int startPosition = pageIndex * pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<HealthResourceCategoryDto> entities = q.getResultList();
		long count = (long) qCount.getSingleResult();

		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<HealthResourceCategoryDto> result = new PageImpl<HealthResourceCategoryDto>(entities, pageable, count);

		return result;
	}

	@Override
	public HealthResourceCategoryDto saveOrUpdate(HealthResourceCategoryDto dto, UUID id) {
		if (dto != null) {
			HealthResourceCategory entity = null;
			if (id != null) {
				entity = healthResourceCategoryRepo.findById(id).get();
			}
			if (entity == null) {
				entity = new HealthResourceCategory();
			}
			entity.setName(dto.getName());
			entity.setCode(dto.getCode());
			entity = healthResourceCategoryRepo.save(entity);
			if (entity != null) {
				return new HealthResourceCategoryDto(entity);
			}
		}
		return null;
	}

	@Override
	public HealthResourceCategoryDto getById(UUID id) {
		HealthResourceCategory entity = healthResourceCategoryRepo.findById(id).get();
		if (entity != null) {
			return new HealthResourceCategoryDto(entity);
		}
		return null;
	}

	@Override
	public Boolean deleteById(UUID id) {
		Long checkHealthResource = healthResourceCategoryRepo.checkReferences(id);
		if(checkHealthResource != null && checkHealthResource > 0) {
			return false;
		}
		
		if (id != null) {
			healthResourceCategoryRepo.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public Boolean checkCode(UUID id, String code) {
		if (code != null && StringUtils.hasText(code)) {
			Long count = healthResourceCategoryRepo.checkCode(code, id);
			return count != 0l;
		}
		return null;
	}
}
