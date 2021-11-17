package com.globits.covid19.test.dto;

import com.globits.core.dto.BaseObjectDto;
import com.globits.covid19.test.domain.UserOrganization;
import com.globits.security.domain.User;
import com.globits.security.dto.RoleDto;
import com.globits.security.dto.UserDto;

public class UserOrganizationDto extends BaseObjectDto{
	private UserDto user;
	private HealthOrganizationDto org;
	private RoleDto role;
	private NCoVAdministrativeUnitDto administrativeUnit;

	public UserOrganizationDto() {
		
	}
	public UserOrganizationDto(UserOrganization entity) {
		this(entity,true);
	}
	public UserOrganizationDto(UserOrganization entity,Boolean simple) {
		if(entity!=null) {
			id = entity.getId();
			if(simple) {
				if(entity.getUser()!=null)
					user = new UserDto(entity.getUser());
				if(entity.getOrg()!=null)
					org = new HealthOrganizationDto(entity.getOrg());

				if(entity.getAdministrativeUnit() != null)
					administrativeUnit = new NCoVAdministrativeUnitDto(entity.getAdministrativeUnit());
				if(entity.getRole()!=null)
					role = new RoleDto(entity.getRole());
			}
		}
	}
	public UserOrganizationDto(UserOrganization entity,User user) {
		if(entity != null) {
			id = entity.getId();
			if(entity.getUser() != null)
				this.user = new UserDto(entity.getUser());
			if(entity.getUser()!=null)
				this.user = new UserDto(entity.getUser());
			if(entity.getOrg()!=null)
				org = new HealthOrganizationDto(entity.getOrg());

			if(entity.getAdministrativeUnit() != null)
				administrativeUnit = new NCoVAdministrativeUnitDto(entity.getAdministrativeUnit());
			if(entity.getRole()!=null)
				role = new RoleDto(entity.getRole());
			
		}
		this.user = new UserDto(user);
	}
	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}
	public HealthOrganizationDto getOrg() {
		return org;
	}
	public void setOrg(HealthOrganizationDto org) {
		this.org = org;
	}
	public RoleDto getRole() {
		return role;
	}
	public void setRole(RoleDto role) {
		this.role = role;
	}

	public NCoVAdministrativeUnitDto getAdministrativeUnit() {
		return administrativeUnit;
	}

	public void setAdministrativeUnit(NCoVAdministrativeUnitDto administrativeUnit) {
		this.administrativeUnit = administrativeUnit;
	}
	
}
