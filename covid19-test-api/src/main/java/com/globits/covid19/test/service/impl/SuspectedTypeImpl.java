package com.globits.covid19.test.service.impl;

import com.globits.core.repository.AdministrativeUnitRepository;
import com.globits.covid19.test.domain.SuspectedLevel;
import com.globits.covid19.test.domain.SuspectedType;
import com.globits.covid19.test.dto.SampleSearchDto;
import com.globits.covid19.test.dto.SuspectedLevelDto;
import com.globits.covid19.test.dto.SuspectedTypeDto;
import com.globits.covid19.test.repository.*;
import com.globits.covid19.test.service.SuspectedLevelService;
import com.globits.covid19.test.service.SuspectedTypeService;
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
public class SuspectedTypeImpl extends GenericServiceImpl<SuspectedType, UUID> implements SuspectedTypeService {

	@Autowired
	private EntityManager manager;

	@Autowired
	public SampleRepository sampleRepository;

	@Autowired
	public SuspectedTypeRepository suspectedTypeRepo;
	
	@Autowired
	public SuspectedPersonRepository personRepository;
	
	@Autowired
	public HealthOrganizationRepository healthOrganizationRepository;
	
	@Autowired
	public AdministrativeUnitRepository administrativeUnitRepository;


	@Override
	public Page<SuspectedTypeDto> searchByDto(SampleSearchDto dto) {
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
		String sqlCount = "select count(entity.id) from SuspectedType as entity where (1=1) ";
		String sql = "select new com.globits.covid19.test.dto.SuspectedTypeDto(entity) from SuspectedType as entity where (1=1) ";

		if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
			whereClause += " AND (entity.name LIKE :text " + "OR entity.code LIKE :text  OR entity.description LIKE :text ) ";
		}

		sql += whereClause + orderBy;
		sqlCount += whereClause;
		Query q = manager.createQuery(sql, SuspectedTypeDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
			q.setParameter("text", '%' + dto.getText().trim() + '%');
			qCount.setParameter("text", '%' + dto.getText().trim() + '%');
		}
		int startPosition = pageIndex * pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<SuspectedTypeDto> entities = q.getResultList();
		long count = (long) qCount.getSingleResult();

		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<SuspectedTypeDto> result = new PageImpl<SuspectedTypeDto>(entities, pageable, count);

		return result;
	}

	@Override
	public SuspectedTypeDto saveOrUpdate(SuspectedTypeDto dto, UUID id) {
		if (dto != null) {
			SuspectedType entity = null;
			if (id != null) {
				entity = suspectedTypeRepo.findById(id).get();
			}
			if (entity == null) {
				entity = new SuspectedType();
			}
			entity.setName(dto.getName());
			entity.setCode(dto.getCode());
			entity.setDescription(dto.getDescription());
			entity = suspectedTypeRepo.save(entity);
			if (entity != null) {
				return new SuspectedTypeDto(entity);
			}
		}
		return null;
	}

	@Override
	public SuspectedTypeDto getById(UUID id) {
		SuspectedType entity = suspectedTypeRepo.findById(id).get();
		if (entity != null) {
			return new SuspectedTypeDto(entity);
		}
		return null;
	}

	@Override
	public Boolean deleteById(UUID id) {
		if (id != null) {
			suspectedTypeRepo.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public Boolean checkCode(String code, UUID id) {
		if (code != null && StringUtils.hasText(code)) {
			Long count = suspectedTypeRepo.checkCode(code, id);
			return count != 0l;
		}
		return null;
	}
}
