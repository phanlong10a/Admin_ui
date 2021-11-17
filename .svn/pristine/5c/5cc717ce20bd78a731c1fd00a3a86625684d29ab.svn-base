package com.globits.covid19.test.service.impl;

import java.util.List;
import java.util.UUID;

import javax.persistence.Query;

import com.globits.covid19.test.domain.HealthOrganization;
import com.globits.covid19.test.domain.Sample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.globits.covid19.test.domain.EpidemiologicalFactors;
import com.globits.covid19.test.dto.EpidemiologicalFactorsDto;
import com.globits.covid19.test.dto.EpidemiologicalFactorsSearchDto;
import com.globits.covid19.test.repository.EpidemiologicalFactorsRepository;
import com.globits.covid19.test.service.EpidemiologicalFactorsService;

@Service
public class EpidemiologicalFactorsServiceImpl extends GenericServiceImpl<EpidemiologicalFactors, UUID>
		implements EpidemiologicalFactorsService {

	@Autowired
	private EpidemiologicalFactorsRepository eFRepos;

	@Override
	public Page<EpidemiologicalFactorsDto> searchByDto(EpidemiologicalFactorsSearchDto searchDto) {
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
		String sqlCount = "select count(entity.id) from EpidemiologicalFactors as entity where (1=1) ";
		String sql = "select new com.globits.covid19.test.dto.EpidemiologicalFactorsDto(entity) from EpidemiologicalFactors as entity where (1=1) ";

		if (searchDto.getText() != null && StringUtils.hasText(searchDto.getText())) {
			whereClause += " AND (entity.name LIKE :text " + "OR entity.code LIKE :text OR entity.description LIKE :text )";
		}

		sql += whereClause + orderBy;
		sqlCount += whereClause;
		Query q = manager.createQuery(sql, EpidemiologicalFactorsDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (searchDto.getText() != null && StringUtils.hasText(searchDto.getText())) {
			q.setParameter("text", '%' + searchDto.getText().trim() + '%');
			qCount.setParameter("text", '%' + searchDto.getText().trim() + '%');
		}

		int startPosition = pageIndex * pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<EpidemiologicalFactorsDto> entities = q.getResultList();
		long count = (long) qCount.getSingleResult();

		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<EpidemiologicalFactorsDto> result = new PageImpl<EpidemiologicalFactorsDto>(entities, pageable, count);

		return result;
	}

	@Override
	public EpidemiologicalFactorsDto saveOrUpdate(EpidemiologicalFactorsDto dto, UUID id) {
		if (dto != null) {
			EpidemiologicalFactors entity = null;
			if (id != null) {
				entity = eFRepos.findById(id).get();
			}
			if (entity == null) {
				entity = new EpidemiologicalFactors();
			}
			entity.setName(dto.getName());
			entity.setCode(dto.getCode());
			entity.setDescription(dto.getDescription());

			if (dto.getParent() != null && dto.getParent().getId() != null) {
				EpidemiologicalFactors parent = eFRepos.findById(dto.getParent().getId()).get();
				entity.setParent(parent);
			}

			entity = eFRepos.save(entity);
			if (entity != null) {
				return new EpidemiologicalFactorsDto(entity);
			}
		}
		return null;
	}

	@Override
	public EpidemiologicalFactorsDto getById(UUID id) {
		EpidemiologicalFactors entity = eFRepos.findById(id).get();
		if (entity != null) {
			return new EpidemiologicalFactorsDto(entity);
		}
		return null;
	}

	@Override
	public Boolean deleteById(UUID id) {
		if (id != null) {
			eFRepos.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public Boolean checkCode(String code, UUID id) {
		if (code != null && StringUtils.hasText(code)) {
			Long count = eFRepos.checkCode(code, id);
			return count != 0l;
		}
		return null;
	}
}
