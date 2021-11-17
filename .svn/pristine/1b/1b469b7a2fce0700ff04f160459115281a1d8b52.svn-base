package com.globits.covid19.test.service.impl;

import java.util.List;
import java.util.UUID;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.globits.covid19.test.domain.EpidemiologicalFactors;
import com.globits.covid19.test.domain.IsolationCenter;
import com.globits.covid19.test.domain.NCoVAdministrativeUnit;
import com.globits.covid19.test.dto.EpidemiologicalFactorsDto;
import com.globits.covid19.test.dto.IsolationCenterDto;
import com.globits.covid19.test.dto.IsolationCenterSearchDto;
import com.globits.covid19.test.repository.IsolationCenterRepository;
import com.globits.covid19.test.repository.NCoVAdministrativeUnitRepository;
import com.globits.covid19.test.service.IsolationCenterService;

@Service
public class IsolationCenterServiceImpl extends GenericServiceImpl<IsolationCenter, UUID>
		implements IsolationCenterService {

	@Autowired
	private IsolationCenterRepository repos;

	@Autowired
	public NCoVAdministrativeUnitRepository nCoVAdministrativeUnitRepository;

	@Override
	public Page<IsolationCenterDto> searchByDto(IsolationCenterSearchDto searchDto) {
		if (searchDto == null) {
			return null;
		}

		int pageIndex = searchDto.getPageIndex();
		int pageSize = searchDto.getPageSize();

		if (pageIndex > 0) {
			pageIndex--;
		} else {
			pageIndex = 0;
		}

		String whereClause = "";
		String orderBy = "";
		String sqlCount = "select count(entity.id) from IsolationCenter as entity where (1=1) ";
		String sql = "select new com.globits.covid19.test.dto.IsolationCenterDto(entity) from IsolationCenter as entity where (1=1) ";

		if (searchDto.getText() != null && StringUtils.hasText(searchDto.getText())) {
			whereClause += " AND (entity.name LIKE :text " + "OR entity.code LIKE :text )";
		}

		sql += whereClause + orderBy;
		sqlCount += whereClause;
		Query q = manager.createQuery(sql, IsolationCenterDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (searchDto.getText() != null && StringUtils.hasText(searchDto.getText())) {
			q.setParameter("text", '%' + searchDto.getText().trim() + '%');
			qCount.setParameter("text", '%' + searchDto.getText().trim() + '%');
		}

		int startPosition = pageIndex * pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<IsolationCenterDto> entities = q.getResultList();
		long count = (long) qCount.getSingleResult();

		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<IsolationCenterDto> result = new PageImpl<IsolationCenterDto>(entities, pageable, count);

		return result;
	}

	@Override
	public IsolationCenterDto saveOrUpdate(IsolationCenterDto dto, UUID id) {
		if (dto != null) {
			IsolationCenter entity = null;
			if (id != null) {
				entity = repos.findById(id).get();
			}

			if (entity == null) {
				entity = new IsolationCenter();
			}

			entity.setName(dto.getName());
			entity.setCode(dto.getCode());
			entity.setAddress(dto.getAddress());
			entity.setContact(dto.getContact());
			entity.setNote(dto.getNote());
			
			NCoVAdministrativeUnit adminUnit = null;
			if (dto.getAdminUnit() != null && dto.getAdminUnit().getId() != null) {
				adminUnit = nCoVAdministrativeUnitRepository.getOne(dto.getAdminUnit().getId());
			}
			entity.setAdminUnit(adminUnit);
			
			entity = repos.save(entity);
			if (entity != null) {
				return new IsolationCenterDto(entity);
			}
		}
		return null;
	}

	@Override
	public IsolationCenterDto getById(UUID id) {
		IsolationCenter entity = repos.findById(id).get();
		if (entity != null) {
			return new IsolationCenterDto(entity);
		}
		return null;
	}

	@Override
	public Boolean deleteById(UUID id) {
		if (id != null) {
			repos.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public Boolean checkCode(String code, UUID id) {
		if (code != null && StringUtils.hasText(code)) {
			Long count = repos.checkCode(code, id);
			return count != 0l;
		}
		return null;
	}
}
