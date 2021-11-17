package com.globits.covid19.test.dto;

import javax.persistence.Column;

import com.globits.core.dto.BaseObjectDto;
import com.globits.covid19.test.domain.SuspectedType;

public class SuspectedTypeDto extends BaseObjectDto {
    private String name;
    private String code;
	private String description;//Mổ tả
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

    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public SuspectedTypeDto() {
        super();
    }

    public SuspectedTypeDto(SuspectedType entity ) {
        if (entity != null) {
            this.id = entity.getId();
            this.name = entity.getName();
            this.code = entity.getCode();
            this.description = entity.getDescription();
        }
    }


}
