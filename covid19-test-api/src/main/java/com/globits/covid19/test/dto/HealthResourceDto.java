package com.globits.covid19.test.dto;

import com.globits.core.dto.BaseObjectDto;
import com.globits.covid19.test.domain.HealthResource;

public class HealthResourceDto extends BaseObjectDto {

	private String name;
	
	private String code;
	
	private HealthResourceCategoryDto category;
	

	public HealthResourceDto() {
		super();
	}

	public HealthResourceDto(HealthResource entity) {
		if(entity != null) {
			this.id = entity.getId();
			this.name = entity.getName();
			this.code = entity.getCode();
			if(entity.getCategory() != null) {
				HealthResourceCategoryDto cDto = new HealthResourceCategoryDto();
				cDto.setId(entity.getCategory().getId());
				cDto.setName(entity.getCategory().getName());
				cDto.setCode(entity.getCategory().getCode());
				this.category = cDto;
			}
		}
	}

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

	public HealthResourceCategoryDto getCategory() {
		return category;
	}

	public void setCategory(HealthResourceCategoryDto category) {
		this.category = category;
	}

}
