package com.globits.covid19.test.dto;

import com.globits.core.dto.BaseObjectDto;
import com.globits.covid19.test.domain.HealthResourceCategory;

public class HealthResourceCategoryDto extends BaseObjectDto {
    private String name;
    private String code;
    
    public HealthResourceCategoryDto() {
		super();
	}

	public HealthResourceCategoryDto(HealthResourceCategory entity) {
		if(entity != null) {
			this.id = entity.getId();
			this.name = entity.getName();
			this.code = entity.getCode();
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

}
