package com.globits.covid19.test.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.globits.covid19.test.domain.HealthOrganization;
import com.globits.covid19.test.domain.NCoVAdministrativeUnit;
import com.globits.covid19.test.domain.UserOrganization;
import com.globits.covid19.test.dto.HealthOrganizationDto;
import com.globits.covid19.test.dto.UserInfoDto;
import com.globits.covid19.test.dto.UserOrgSearch;
import com.globits.covid19.test.dto.UserOrganizationDto;
import com.globits.covid19.test.repository.HealthOrganizationRepository;
import com.globits.covid19.test.repository.NCoVAdministrativeUnitRepository;
import com.globits.covid19.test.repository.UserOrganizationRepository;
import com.globits.covid19.test.service.UserOrganizationService;
import com.globits.covid19.test.utilities.NCoVConstant;
import com.globits.security.domain.Role;
import com.globits.security.domain.User;
import com.globits.security.dto.RoleDto;
import com.globits.security.dto.UserDto;
import com.globits.security.repository.RoleRepository;
import com.globits.security.repository.UserRepository;
import com.globits.security.service.UserService;

@Service
public class UserOrganizationServiceImpl extends GenericServiceImpl<UserOrganization, UUID>
        implements UserOrganizationService {
    @Autowired
    UserOrganizationRepository userOrgRepository;
    @Autowired
    UserService userService;
    @Autowired
    HealthOrganizationRepository orgRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;

	@Autowired
	NCoVAdministrativeUnitRepository administrativeUnitRepository;

	@Autowired
	public UserOrganizationService userOrganizationService;

    @Override
    @Transactional(readOnly = true)
    public UserDto getCurrentUserDto() {
        User entity = getCurrentUser();
        if (entity != null)
			return new UserDto(entity);

        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if (user != null && user.getUsername() != null) {
            User entity = userRepository.findByUsernameAndPerson(user.getUsername());

            if (entity != null)
				return entity;
        }

        return null;
    }

    @Override
    public Page<UserOrganizationDto> searchByDto(UserOrgSearch dto) {
        if (dto == null)
			return null;
		List<UUID> listHealthOrganizationId = new ArrayList<>();
		UserInfoDto userOrganization = userOrganizationService.getAllInfoByUserLogin();
		if (userOrganization == null || !userOrganization.isAdmin()
				&& userOrganization.isUser() && (userOrganization.getListChildHealthOrganizationId() == null
						|| userOrganization.getListChildHealthOrganizationId().size() <= 0))
			return null;
		
		if (dto.getIdHealthOrg() != null)
			if (!userOrganization.isAdmin() && userOrganization.isUser()
					&& !userOrganization.getListChildHealthOrganizationId().contains(dto.getIdHealthOrg()))
				return null;
		
        int pageIndex = dto.getPageIndex();
        int pageSize = dto.getPageSize();

        if (pageIndex > 0)
			pageIndex--;
		else
			pageIndex = 0;

        String whereClause = " where (1=1) ";
        String orderBy = " ";
        String sqlCount = "select count(user.id) from UserOrganization as user "
        		+ " right join User as u on user.user.id = u.id ";
        String sql = "select new com.globits.covid19.test.dto.UserOrganizationDto(user,u) from UserOrganization as user "
        		+ " right join User as u on user.user.id = u.id ";
        if (dto.getText() != null && StringUtils.hasText(dto.getText()))
			whereClause += " AND (u.person.displayName LIKE :text ) ";
		/*
		 * if(dto.getIdHealthOrg() != null) { whereClause +=
		 * " AND ((user.org.id  = :idHealthOrg) OR (user.org.parent.id = :idHealthOrg) ) "
		 * ; }
		 */
        if (!userOrganization.isAdmin())
			if (userOrganization.isUser()
            		&& userOrganization.getListChildHealthOrganizationId() != null
            		&& userOrganization.getUserOrganization() != null && userOrganization.getUserOrganization().getOrg() != null
            		&& userOrganization.getUserOrganization().getUser() != null
            		&& userOrganization.getListChildHealthOrganizationId().size() > 0) {
            	listHealthOrganizationId = userOrganizationService.findAllChildHealthOrganizationById(userOrganization.getUserOrganization().getOrg().getId(), userOrganization);
            	listHealthOrganizationId.remove(userOrganization.getUserOrganization().getOrg().getId());
            	sql += "  join HealthOrganization as org on user.org.id = org.id ";
            	sqlCount += "  join HealthOrganization as org on user.org.id = org.id ";
            	whereClause += " AND ( ";
            	if (listHealthOrganizationId != null && listHealthOrganizationId.size()>0)
					whereClause += " (org.id IN (:listHealthOrganizationId)) OR (u.id = :userId) ";
				else
					whereClause += " (u.id = :userId) ";
            	
            	whereClause += " )";
			} else
				return null;
        sql += whereClause + orderBy;
        sqlCount += whereClause;
        Query q = manager.createQuery(sql, UserOrganizationDto.class);
        Query qCount = manager.createQuery(sqlCount);

        if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
            q.setParameter("text", '%' + dto.getText().trim() + '%');
            qCount.setParameter("text", '%' + dto.getText().trim() + '%');
        }
		/*
		 * if(dto.getIdHealthOrg() != null) { q.setParameter("idHealthOrg",
		 * dto.getIdHealthOrg()); qCount.setParameter("idHealthOrg",
		 * dto.getIdHealthOrg()); }
		 */

        if (!userOrganization.isAdmin())
			if (userOrganization.isUser()
            		&& userOrganization.getListChildHealthOrganizationId() != null
            		&& userOrganization.getUserOrganization() != null && userOrganization.getUserOrganization().getOrg() != null
            		&& userOrganization.getUserOrganization().getUser() != null
            		&& userOrganization.getListChildHealthOrganizationId().size() > 0) {
        		if (listHealthOrganizationId != null && listHealthOrganizationId.size()>0) {
                	q.setParameter("listHealthOrganizationId", listHealthOrganizationId);
                    qCount.setParameter("listHealthOrganizationId", listHealthOrganizationId);

                	q.setParameter("userId", userOrganization.getUserOrganization().getUser().getId());
                    qCount.setParameter("userId", userOrganization.getUserOrganization().getUser().getId());
				}
            	else {
                	q.setParameter("userId", userOrganization.getUserOrganization().getUser().getId());
                    qCount.setParameter("userId", userOrganization.getUserOrganization().getUser().getId());
            	}
			} else
				return null;
        int startPosition = pageIndex * pageSize;
        q.setFirstResult(startPosition);
        q.setMaxResults(pageSize);
        List<UserOrganizationDto> entities = q.getResultList();
        long count = (long) qCount.getSingleResult();

        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<UserOrganizationDto> result = new PageImpl<>(entities, pageable, count);

        return result;
    }

    @Override
    public UserOrganizationDto saveOrUpdate(UserOrganizationDto dto, UUID id) {
        if (dto != null && dto.getUser() != null && dto.getUser() != null) {
            UserOrganization userOrg = null;
            User user = null;

            if (dto.getUser() != null) {

                UserDto userDto = userService.save(dto.getUser());
                if (userDto != null)
					user = userDto.toEntity();
                if (user == null || userDto.getId() == null)
					return null;
				else if (id != null && dto.getId().equals(id))
					userOrg = userOrgRepository.getOne(id);
                if (userOrg == null)
					userOrg = new UserOrganization();
                if (dto.getOrg() != null && dto.getOrg().getId() != null) {
                    HealthOrganization org = orgRepository.getOne(dto.getOrg().getId());
                    if (org != null)
						userOrg.setOrg(org);
                }
                if (dto.getRole() != null && dto.getRole().getId() != null) {
                    Role role = roleRepository.getOne(dto.getRole().getId());
                    if (role != null)
						userOrg.setRole(role);
                }

				if(dto.getAdministrativeUnit() != null && dto.getAdministrativeUnit().getId() != null){
					NCoVAdministrativeUnit administrativeUnit =
							administrativeUnitRepository.getOne(dto.getAdministrativeUnit().getId());

					if(administrativeUnit != null)
						userOrg.setAdministrativeUnit(administrativeUnit);
				}

                userOrg.setUser(user);

                userOrg = userOrgRepository.save(userOrg);
                return new UserOrganizationDto(userOrg);
            }
            return null;
        }
        return null;
    }

    @Override
    public UserOrganizationDto getById(UUID id) {
        if (id != null) {
            UserOrganization entity = userOrgRepository.getOne(id);
            if (entity != null)
				return new UserOrganizationDto(entity);
        }
        return null;
    }

    @Override
    public List<UserOrganizationDto> getAllOrgByUserId(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean checkEmail(String email, Long id) {
        if (email != null && StringUtils.hasText(email)) {
            List<UserOrganization> entities = userOrgRepository.findByEmail(email);
            if (entities != null && entities.size() > 0) {
                User entity = entities.get(0).getUser();
                if (id != null && entity.getId().equals(id))
                    return false;
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean checkUsername(String username, Long id) {
        if (username != null && StringUtils.hasText(username)) {
            List<UserOrganization> entities = userOrgRepository.findByusername(username);
            if (entities != null && entities.size() > 0) {
                User entity = entities.get(0).getUser();
                if (id != null && entity.getId().equals(id))
                    return false;
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public UserOrganizationDto getUserOrganizationDtoByUserId(Long id) {
        // TODO Auto-generated method stub
        List<UserOrganizationDto> list = userOrgRepository.getByUserId(id);
        if (list != null && list.size() > 0 && list.get(0).getId() != null)
			return list.get(0);
        return null;
    }

    @Override
    public UserInfoDto getAllInfoByUserLogin() {
        UserInfoDto userInfoDto = null;
        UserDto user = getCurrentUserDto();
        if (user != null && user.getId() != null) {
            userInfoDto = new UserInfoDto();
            userInfoDto = getRolesByUser(userInfoDto, user);
            UserOrganizationDto dto = getUserOrganizationDtoByUserId(user.getId());
            if (dto != null && dto.getId() != null && dto.getOrg() != null) {
				if (dto.getOrg() != null && dto.getOrg().getOrgType() != null)
					userInfoDto.setOrgType(dto.getOrg().getOrgType());
                userInfoDto.setUserOrganization(dto);
                List<UUID> listChildHealthOrganizationId = findAllChildHealthOrganizationById(dto.getOrg().getId(),
                        userInfoDto);
                if (listChildHealthOrganizationId != null && listChildHealthOrganizationId.size() > 0)
					userInfoDto.setListChildHealthOrganizationId(listChildHealthOrganizationId);
            }
        }
        return userInfoDto;
    }

    public List<HealthOrganizationDto> getHealthOrganizationDtoByLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            User modifiedUser = (User) authentication.getPrincipal();
            if (modifiedUser != null) {
                String currentUserName = modifiedUser.getUsername();
                String sql = " SELECT new com.globits.covid19.test.dto.HealthOrganizationDto(entity.org) FROM UserOrganization entity WHERE entity.user.id=:userId AND (entity.org.voided IS NULL OR entity.org.voided = false) ";
                Query q = manager.createQuery(sql, HealthOrganizationDto.class);
                q.setParameter("userId", modifiedUser.getId());
                return q.getResultList();
            }
        }
        return null;
    }

	@Override
	public List<UUID> findAllChildHealthOrganizationById(UUID id, UserInfoDto info) {
		//List<HealthOrganizationDto> listHealthOrganizationDto = this.getHealthOrganizationDtoByLoginUser();
		List<HealthOrganizationDto> listHealthOrganizationDto = null;
		if (id != null) {
			HealthOrganizationDto dto = orgRepository.findOne(id);
			if (dto != null && dto.getId() != null) {
				listHealthOrganizationDto = new ArrayList<>();
				listHealthOrganizationDto.add(dto);
			}
		}
		if (listHealthOrganizationDto != null && listHealthOrganizationDto.size() > 0) {
			List<UUID> listCrmHealthOrganizationId = new ArrayList<>();
			for (HealthOrganizationDto healthOrganizationDto : listHealthOrganizationDto) {
				List<UUID> list = new ArrayList<>();
				if (healthOrganizationDto.getId() != null) {
					list = getAllHealthOrganizationIdByParentId(healthOrganizationDto.getId());
					if (info != null && info.isUser()) {
						if (healthOrganizationDto.getParent() != null
								&& healthOrganizationDto.getParent().getId() != null) {
							if (list == null)
								list = new ArrayList<>();
							list.add(healthOrganizationDto.getParent().getId());
						}
						if (healthOrganizationDto.getParent() != null
								&& healthOrganizationDto.getParent().getParent() != null
								&& healthOrganizationDto.getParent().getParent().getId() != null) {
							if (list == null)
								list = new ArrayList<>();
							list.add(healthOrganizationDto.getParent().getParent().getId());
						}
					}
					if (list != null && list.size() > 0)
						listCrmHealthOrganizationId.addAll(list);
				}
			}
			return listCrmHealthOrganizationId;
		}
		return null;
	}

    private List<UUID> getAllHealthOrganizationIdByParentId(UUID parentId) {
        List<UUID> ret = new ArrayList<>();
        ret.add(parentId);
        List<UUID> list = orgRepository.getAllIdByParentId(parentId);
        if (list != null && list.size() > 0) {
            ret.addAll(list);
            for (UUID id : list) {
                List<UUID> lst = new ArrayList<>();
                lst = orgRepository.getAllIdByParentId(id);
                if (lst != null && lst.size() > 0)
					ret.addAll(lst);
            }
        }
        // FmsHealthOrganization au=administrativeUnitRepository.findById(parentId);
        return ret;
    }

    private List<UUID> getAllIdByParentId(UUID parentId) {
        // TODO Auto-generated method stub
        return null;
    }

    private UserInfoDto getRolesByUser(UserInfoDto info, UserDto currentUser) {
        if (currentUser != null && info != null) {
            for (RoleDto role : currentUser.getRoles()) {
                if (role.getAuthority().equals(NCoVConstant.ROLE_ADMIN)
                        || role.getName().equals(NCoVConstant.ROLE_ADMIN))
					info.setAdmin(true);
                if (role.getAuthority().equals(NCoVConstant.ROLE_USER)
                        || role.getName().equals(NCoVConstant.ROLE_USER))
					info.setUser(true);
            }
            return info;
        }
        return null;
    }
    @Override
    public List<RoleDto> getRoleUser() {
    	List<Role> roles = roleRepository.findAll();
    	List<RoleDto> roleDto = new ArrayList<>();
    	if(roles != null && roles.size() > 0)
			for(Role item: roles)
				if(item.getAuthority().equals(NCoVConstant.ROLE_USER) || item.getName().equals(NCoVConstant.ROLE_USER))
					roleDto.add(new RoleDto(item));
    	
    	return roleDto;
    }
}
