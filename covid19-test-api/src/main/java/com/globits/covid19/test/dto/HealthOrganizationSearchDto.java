package com.globits.covid19.test.dto;

import java.util.List;
import java.util.UUID;

public class HealthOrganizationSearchDto extends SearchDto{
	private String name;
	private String code;
	private UUID parentId;
	private Integer orgType;
	private Boolean isGetAll = false;
	private boolean notInOrgIdByUserLogin = false;
	private Boolean isExternalOrg;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public UUID getParentId() {
		return parentId;
	}
	public void setParentId(UUID parentId) {
		this.parentId = parentId;
	}
	public Integer getOrgType() {
		return orgType;
	}
	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
	}
	public Boolean getIsGetAll() {
		return isGetAll;
	}
	public void setIsGetAll(Boolean isGetAll) {
		this.isGetAll = isGetAll;
	}
	public boolean isNotInOrgIdByUserLogin() {
		return notInOrgIdByUserLogin;
	}
	public void setNotInOrgIdByUserLogin(boolean notInOrgIdByUserLogin) {
		this.notInOrgIdByUserLogin = notInOrgIdByUserLogin;
	}
	public Boolean getIsExternalOrg() {
		return isExternalOrg;
	}
	public void setIsExternalOrg(Boolean isExternalOrg) {
		this.isExternalOrg = isExternalOrg;
	}
	
}
