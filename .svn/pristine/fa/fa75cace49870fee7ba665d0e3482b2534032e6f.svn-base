package com.globits.covid19.test.service.impl;

import com.globits.core.repository.AdministrativeUnitRepository;
import com.globits.covid19.test.domain.HealthOrganization;
import com.globits.covid19.test.domain.SuspectedLevel;
import com.globits.covid19.test.dto.HealthOrganizationDto;
import com.globits.covid19.test.dto.SampleSearchDto;
import com.globits.covid19.test.dto.SuspectedLevelDto;
import com.globits.covid19.test.repository.HealthOrganizationRepository;
import com.globits.covid19.test.repository.SampleRepository;
import com.globits.covid19.test.repository.SuspectedLevelRepository;
import com.globits.covid19.test.repository.SuspectedPersonRepository;
import com.globits.covid19.test.service.HealthOrganizationService;
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
public class SuspectedLevelImpl extends GenericServiceImpl<SuspectedLevel, UUID> implements SuspectedLevelService {

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


	@Override
	public Page<SuspectedLevelDto> searchByDto(SampleSearchDto dto) {
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
		String orderBy = " ORDER BY entity.code ASC ";
		String sqlCount = "select count(entity.id) from SuspectedLevel as entity where (1=1) ";
		String sql = "select new com.globits.covid19.test.dto.SuspectedLevelDto(entity) from SuspectedLevel as entity where (1=1) ";

		if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
			whereClause += " AND (entity.name LIKE :text " + "OR entity.code LIKE :text OR entity.description LIKE :text ) ";
		}

		sql += whereClause + orderBy;
		sqlCount += whereClause;
		Query q = manager.createQuery(sql, SuspectedLevelDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
			q.setParameter("text", '%' + dto.getText().trim() + '%');
			qCount.setParameter("text", '%' + dto.getText().trim() + '%');
		}
		int startPosition = pageIndex * pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<SuspectedLevelDto> entities = q.getResultList();
		long count = (long) qCount.getSingleResult();

		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<SuspectedLevelDto> result = new PageImpl<SuspectedLevelDto>(entities, pageable, count);

		return result;
	}

	@Override
	public SuspectedLevelDto saveOrUpdate(SuspectedLevelDto dto, UUID id) {
		if (dto != null) {
			SuspectedLevel entity = null;
			Boolean isCheckCode = false;
			if (id != null) {
				entity = suspectedLevelRepo.findById(id).get();
				if(!entity.getCode().equals(dto.getCode())) {
					isCheckCode = true;
				}
			}
			if (entity == null) {
				entity = new SuspectedLevel();
			}
			
			entity.setName(dto.getName());
//			entity.setCode(dto.getCode());
			entity.setDescription(dto.getDescription());
			
			SuspectedLevel parent = null;
			if (dto.getParent() != null && dto.getParent().getId() != null) {
				parent = suspectedLevelRepo.getOne(dto.getParent().getId());
				Integer count = suspectedLevelRepo.countChidren(parent.getId());
				String code = this.genCode(parent.getCode() , count + 1);
				entity.setCode(code);
			}else {
				entity.setCode(dto.getCode());
			}
			if (parent != null) {
				entity.setParent(parent);
			}
			//Thay doi code cha -> thay doi code con
			if(isCheckCode) {
				if(dto.getChidren() != null && dto.getChidren().size() > 0) {
					SuspectedLevel chidren = null;
					int i = 1;
					for(SuspectedLevelDto itemChidren : dto.getChidren()) {
						chidren = suspectedLevelRepo.getOne(itemChidren.getId());
						String code = this.genCode(entity.getCode(), i);
						chidren.setCode(code);
						suspectedLevelRepo.save(chidren);
						i++;
					}
				}
			}
			entity = suspectedLevelRepo.save(entity);
			if (entity != null) {
				return new SuspectedLevelDto(entity);
			}
		}
		return null;
	}

	@Override
	public SuspectedLevelDto getById(UUID id) {
		SuspectedLevel entity = suspectedLevelRepo.findById(id).get();
		if (entity != null) {
			return new SuspectedLevelDto(entity);
		}
		return null;
	}

	@Override
	public Boolean deleteById(UUID id) {
		if (id != null) {
			suspectedLevelRepo.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public Boolean checkCode(String code, UUID id) {
		if (code != null && StringUtils.hasText(code)) {
			Long count = suspectedLevelRepo.checkCode(code, id);
			return count != 0l;
		}
		return null;
	}
	
	
	public String genCode(String code, int index) {
		String genCode = code + ".";
		if(index < 10) {
			genCode += "00" + index;
		}
		if(index >= 10 && index < 100) {
			genCode += "0" + index;
		}
		if(index >= 100) {
			genCode +=  index;
		}
		return genCode;
	}
}
