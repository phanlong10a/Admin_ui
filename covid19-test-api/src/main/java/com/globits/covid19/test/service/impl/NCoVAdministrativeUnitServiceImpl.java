package com.globits.covid19.test.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.globits.covid19.test.domain.NCoVAdministrativeUnit;
import com.globits.covid19.test.dto.NCoVAdministrativeUnitDto;
import com.globits.covid19.test.dto.NCoVAdministrativeUnitSearchDto;
import com.globits.covid19.test.dto.SearchDto;
import com.globits.covid19.test.repository.NCoVAdministrativeUnitRepository;
import com.globits.covid19.test.service.NCoVAdministrativeUnitService;
import com.globits.security.domain.User;

@Transactional
@Service
public class NCoVAdministrativeUnitServiceImpl extends GenericServiceImpl<NCoVAdministrativeUnit, UUID> implements NCoVAdministrativeUnitService {

	@Autowired
	private NCoVAdministrativeUnitRepository repository;

	@Override
	public List<UUID> getAllNCoVAdministrativeUnitIdByParentId(UUID parentId) {
		List <UUID> ret=new ArrayList<UUID>();
		ret.add(parentId);
		List<UUID> list=repository.getAllIdByParentId(parentId);
		if(list!=null && list.size()>0){
			ret.addAll(list);
			for (UUID long1 : list) {
				List<UUID> lst=new ArrayList<UUID>();
				lst=repository.getAllIdByParentId(long1);
				if(lst!=null && lst.size()>0){
					ret.addAll(lst);
				}
			}
		}
		NCoVAdministrativeUnit au=repository.findOne(parentId);
		return ret;
	}

	@Override
	public List<UUID> getAllChildIdByParentId(UUID parentId) {
		List <UUID> ret=new ArrayList<UUID>();
		List<UUID> list=repository.getAllIdByParentId(parentId);
		if(list!=null && list.size()>0){
			ret.addAll(list);
			for (UUID childId : list) {
				List<UUID> listChild = getAllChildIdByParentId(childId);
				if(listChild!=null && listChild.size()>0){
					ret.addAll(listChild);
				}
			}
		}
		return ret;
	}

	@Override
	public List<NCoVAdministrativeUnit> getAllChildByParentId(UUID uuid) {
		List<NCoVAdministrativeUnit> ret=new ArrayList<>();
		List<NCoVAdministrativeUnit> list=repository.getAllEntityByParentId(uuid);
		if(list!=null && list.size()>0){
			ret.addAll(list);
			for (NCoVAdministrativeUnit childId : list) {
				List<NCoVAdministrativeUnit> listChild = getAllChildByParentId(childId.getId());
				if(listChild!=null && listChild.size()>0){
					ret.addAll(listChild);
				}
			}
		}
		return ret;
	}
	@Override
	public List<NCoVAdministrativeUnitDto> getAllChildByParentId(UUID parentId, String prefix) {
		List<NCoVAdministrativeUnitDto> ret=new ArrayList<NCoVAdministrativeUnitDto>();
		if(prefix=="") {
			NCoVAdministrativeUnitDto parent = repository.getById(parentId);
			if(parent!=null) {
				parent.setName(prefix+parent.getName());
				ret.add(parent);
			}
			prefix="..";
		}
		List<NCoVAdministrativeUnitDto> list=repository.getAllByParentId(parentId);
		if(list!=null && list.size()>0){
			ret.addAll(list);
			for (NCoVAdministrativeUnitDto childId : list) {
				if(prefix!="") {
				childId.setName(prefix+childId.getName());
				ret.add(childId);
				}
				List<NCoVAdministrativeUnitDto> listChild = getAllChildByParentId(childId.getId(),prefix+"..");
				if(listChild!=null && listChild.size()>0){
					ret.addAll(listChild);
				}
			}
		}
		return ret;
	}

	@Override
	public Page<NCoVAdministrativeUnitDto> searchByDto(NCoVAdministrativeUnitSearchDto dto) {
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
		String sqlCount = "select count(entity.id) from NCoVAdministrativeUnit as entity where (1=1) ";
		String sql = "select new com.globits.covid19.test.dto.NCoVAdministrativeUnitDto(entity) from NCoVAdministrativeUnit as entity where (1=1) ";

		if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
			whereClause += " AND (entity.name LIKE :text OR entity.code LIKE :text ) ";
		}
        if (dto.getParentId() != null) {
            whereClause += " AND (entity.parent.id=:parentId ) ";
        }
        if (dto.getIsGetAllCity()!= null && dto.getIsGetAllCity()) {
            whereClause += " AND (entity.parent.id IS NULL ) ";
        }
		sql += whereClause + orderBy;
		sqlCount += whereClause;
		Query q = manager.createQuery(sql, NCoVAdministrativeUnitDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
			q.setParameter("text", '%' + dto.getText().trim() + '%');
			qCount.setParameter("text", '%' + dto.getText().trim() + '%');
		}
        if (dto.getParentId() != null) {
            q.setParameter("parentId", dto.getParentId());
            qCount.setParameter("parentId", dto.getParentId());
        }
        int startPosition = pageIndex * pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<NCoVAdministrativeUnitDto> entities = q.getResultList();
		long count = (long) qCount.getSingleResult();

		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<NCoVAdministrativeUnitDto> result = new PageImpl<NCoVAdministrativeUnitDto>(entities, pageable, count);

		return result;
	}

	@Override
	public NCoVAdministrativeUnitDto getById(UUID id) {
		if (id != null) {
			NCoVAdministrativeUnit entity = repository.getOne(id);
			if (entity != null) {
				return new NCoVAdministrativeUnitDto(entity);
			}
		}
		return null;
	}

	@Override
	public NCoVAdministrativeUnitDto saveOrUpdate(NCoVAdministrativeUnitDto dto, UUID id) {
		if (dto != null) {
			NCoVAdministrativeUnit nCoVAdministrativeUnit = null;
			Boolean isEdit = false;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}
			if (id != null) {// trường hợp edit
				isEdit = true;
				nCoVAdministrativeUnit = this.repository.findOne(id);
			} else if (dto.getId() != null) {
				nCoVAdministrativeUnit = this.repository.findOne(dto.getId());
			} 
			/*
			 * else if (dto.getCode() != null) { List<NCoVAdministrativeUnit> aus =
			 * this.repository .findListByCode(dto.getCode()); if (aus != null && aus.size()
			 * == 1) { nCoVAdministrativeUnit = aus.get(0); } else if (aus != null &&
			 * aus.size() > 1) { for (NCoVAdministrativeUnit item : aus) { if
			 * (item.getName().equals(dto.getName())) { nCoVAdministrativeUnit = item;
			 * break; } } } }
			 */

			if (nCoVAdministrativeUnit == null) {// trường hợp thêm mới
				nCoVAdministrativeUnit = new NCoVAdministrativeUnit();
				nCoVAdministrativeUnit.setCreateDate(currentDate);
				nCoVAdministrativeUnit.setCreatedBy(currentUserName);
			}

			if (dto.getName() != null)
				nCoVAdministrativeUnit.setName(dto.getName());

			if (dto.getCode() != null)
				nCoVAdministrativeUnit.setCode(dto.getCode());

			if (dto.getMapCode() != null)
				nCoVAdministrativeUnit.setMapCode(dto.getMapCode());

			if (dto.getLatitude() != null)
				nCoVAdministrativeUnit.setLatitude(dto.getLatitude());

			if (dto.getLongitude() != null)
				nCoVAdministrativeUnit.setLongitude(dto.getLongitude());

			if (dto.getgMapX() != null)
				nCoVAdministrativeUnit.setgMapX(dto.getgMapX());

			if (dto.getgMapY() != null)
				nCoVAdministrativeUnit.setgMapY(dto.getgMapY());

			if (dto.getTotalAcreage() != null)
				nCoVAdministrativeUnit.setTotalAcreage(dto.getTotalAcreage());

			if (dto.getParent() != null) {
				NCoVAdministrativeUnit parent = null;
				if (dto.getParent().getId() != null) {
					parent = this.repository.findOne(dto.getParent().getId());
				} else if (dto.getParent().getCode() != null) {
					List<NCoVAdministrativeUnit> aus = this.repository
							.findListByCode(dto.getParent().getCode());
					if (aus != null && aus.size() == 1) {
						parent = aus.get(0);
					} else if (aus != null && aus.size() > 1) {
						for (NCoVAdministrativeUnit item : aus) {
							if (item.getName().equals(dto.getParent().getName())) {
								parent = item;
								break;
							}
						}
					}
				}
				if (parent != null) {
					nCoVAdministrativeUnit.setParent(parent);
					if (parent.getLevel() != null && parent.getLevel() > 0) {
						nCoVAdministrativeUnit.setLevel(parent.getLevel() + 1);
					}
				}
			} else {
				nCoVAdministrativeUnit.setLevel(1); // level = 1 là cấp thành phố
				nCoVAdministrativeUnit.setParent(null);
			}

			this.repository.save(nCoVAdministrativeUnit);
			dto.setId(nCoVAdministrativeUnit.getId());
			return dto;
		}
		return null;
	}

	@Override
	public Boolean deleteById(UUID id) {
		if (id != null) {
			NCoVAdministrativeUnit entity = repository.getOne(id);
			if (entity != null) {
				repository.deleteById(id);
				return true;
			}
		}
		return false;
	}

	@Override
	public List<NCoVAdministrativeUnitDto> getAll() {
		// TODO Auto-generated method stub
		List<NCoVAdministrativeUnitDto> dtos = repository.getAll();
		return dtos;
	}

	@Override
	public List<NCoVAdministrativeUnitDto> getAllBasic() {
		List<NCoVAdministrativeUnitDto> dtos = repository.getAllBasic();
		return dtos;
	}

	@Override
	public List<NCoVAdministrativeUnitDto> getAllBasicInEdit(UUID id) {
		List<NCoVAdministrativeUnitDto> dtos = new ArrayList<NCoVAdministrativeUnitDto>();
		if (id != null) {
			dtos = repository.getAllBasicInEdit(id);
		}
		else {
			dtos = repository.getAllBasic();
		}
		return dtos;
	}

	@Override
	public void saveOrUpdateList(List<NCoVAdministrativeUnitDto> listNCoVAdministrativeUnit) {
		ArrayList<NCoVAdministrativeUnitDto> ret = new ArrayList<NCoVAdministrativeUnitDto>();
		for (int i = 0; i < listNCoVAdministrativeUnit.size(); i++) {
			NCoVAdministrativeUnitDto dto = listNCoVAdministrativeUnit.get(i);
			saveAdministrativeImport(dto, dto.getId());
		}
	}

	private void saveAdministrativeImport(NCoVAdministrativeUnitDto dto, UUID id) {

		NCoVAdministrativeUnit fmsAdministrativeUnit = null;
		Boolean isEdit = false;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User modifiedUser = null;
		LocalDateTime currentDate = LocalDateTime.now();
		String currentUserName = "Unknown User";
		if (authentication != null) {
			modifiedUser = (User) authentication.getPrincipal();
			currentUserName = modifiedUser.getUsername();
		}
		if (id != null) {// trường hợp edit
			isEdit = true;
			fmsAdministrativeUnit = repository.findOne(id);
		} else if (dto.getId() != null) {
			fmsAdministrativeUnit = repository.findOne(dto.getId());
		} else if (dto.getCode() != null) {
			List<NCoVAdministrativeUnit> aus = repository
					.findListByCode(dto.getCode());
			if (aus != null && aus.size() == 1) {
				fmsAdministrativeUnit = aus.get(0);
			} else if (aus != null && aus.size() > 1) {
				for (NCoVAdministrativeUnit item : aus) {
					if (item.getName().equals(dto.getName())) {
						fmsAdministrativeUnit = item;
						break;
					}
				}
			}
		}

		if (fmsAdministrativeUnit == null) {// trường hợp thêm mới
			fmsAdministrativeUnit = new NCoVAdministrativeUnit();
			fmsAdministrativeUnit.setCreateDate(currentDate);
			fmsAdministrativeUnit.setCreatedBy(currentUserName);
		}

		if (dto.getName() != null)
			fmsAdministrativeUnit.setName(dto.getName());

		if (dto.getCode() != null)
			fmsAdministrativeUnit.setCode(dto.getCode());

		if (dto.getMapCode() != null)
			fmsAdministrativeUnit.setMapCode(dto.getMapCode());

		if (dto.getLatitude() != null)
			fmsAdministrativeUnit.setLatitude(dto.getLatitude());

		if (dto.getLongitude() != null)
			fmsAdministrativeUnit.setLongitude(dto.getLongitude());

		if (dto.getgMapX() != null)
			fmsAdministrativeUnit.setgMapX(dto.getgMapX());

		if (dto.getgMapY() != null)
			fmsAdministrativeUnit.setgMapY(dto.getgMapY());

		if (dto.getTotalAcreage() != null)
			fmsAdministrativeUnit.setTotalAcreage(dto.getTotalAcreage());

		if (dto.getParent() != null) {
			NCoVAdministrativeUnit parent = null;
			if (dto.getParent().getId() != null) {
				parent = repository.findOne(dto.getParent().getId());
			} else if (dto.getParent().getCode() != null) {
				List<NCoVAdministrativeUnit> aus = repository
						.findListByCode(dto.getParent().getCode());
				if (aus != null && aus.size() == 1) {
					parent = aus.get(0);
				} else if (aus != null && aus.size() > 1) {
					for (NCoVAdministrativeUnit item : aus) {
						if (item.getName().equals(dto.getParent().getName())) {
							parent = item;
							break;
						}
					}
				}
			}
			if (parent != null) {
				fmsAdministrativeUnit.setParent(parent);
				if (parent.getLevel() != null && parent.getLevel() > 0) {
					fmsAdministrativeUnit.setLevel(parent.getLevel() + 1);
				}
			}
		} else {
			fmsAdministrativeUnit.setLevel(1); // level = 1 là cấp thành phố
			fmsAdministrativeUnit.setParent(null);
		}

		repository.save(fmsAdministrativeUnit);
		dto.setId(fmsAdministrativeUnit.getId());
	}

}
