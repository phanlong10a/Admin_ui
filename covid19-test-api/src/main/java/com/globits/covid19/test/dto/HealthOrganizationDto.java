package com.globits.covid19.test.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.globits.core.dto.BaseObjectDto;
import com.globits.covid19.test.domain.HealthOrganization;

public class HealthOrganizationDto extends BaseObjectDto{
	private String name;
	private String code;
	private HealthOrganizationDto parent;
	private List<HealthOrganizationDto> chidren;
	private UUID parentId;
	private Integer orgType;
	private Boolean isExternalOrg;//Nếu là đơn vị ngoại tỉnh thì set = true
	
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
	public HealthOrganizationDto getParent() {
		return parent;
	}
	public void setParent(HealthOrganizationDto parent) {
		this.parent = parent;
	}
	public Integer getOrgType() {
		return orgType;
	}
	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
	}

	public List<HealthOrganizationDto> getChidren() {
		return chidren;
	}
	public void setChidren(List<HealthOrganizationDto> chidren) {
		this.chidren = chidren;
	}
	public UUID getParentId() {
		return parentId;
	}
	public void setParentId(UUID parentId) {
		this.parentId = parentId;
	}
	public Boolean getIsExternalOrg() {
		return isExternalOrg;
	}
	public void setIsExternalOrg(Boolean isExternalOrg) {
		this.isExternalOrg = isExternalOrg;
	}
	public HealthOrganizationDto() {
		super();
	}

	public HealthOrganizationDto(HealthOrganization entity, boolean isParent, int type) {
		if(entity != null) {
			this.id = entity.getId();
			this.name = entity.getName();
			this.code = entity.getCode();
			this.orgType = entity.getOrgType();
			this.isExternalOrg = entity.getIsExternalOrg();
		}
	}

	public HealthOrganizationDto(HealthOrganization entity, boolean isParent) {
		if(entity != null) {
			this.id = entity.getId();
			this.name = entity.getName();
			this.code = entity.getCode();
			this.orgType = entity.getOrgType();
			this.isExternalOrg = entity.getIsExternalOrg();
				if(entity.getParent() != null) {
					if(isParent) {
					this.parent = new HealthOrganizationDto();
					this.parent.setCode(entity.getParent().getCode());
					this.parent.setName(entity.getParent().getName());
					this.parent.setIsExternalOrg(entity.getParent().getIsExternalOrg());
				}
				this.parentId = entity.getParent().getId();

			}
			if(entity.getChidren() != null && entity.getChidren().size() > 0) {
				this.chidren = new ArrayList<>();
				for (HealthOrganization healthOrganization : entity.getChidren()) {
					this.chidren.add(new HealthOrganizationDto(healthOrganization, true));
				}
			}
		}
	}

	public HealthOrganizationDto(HealthOrganization entity) {
		this(entity,true);
	}
}
