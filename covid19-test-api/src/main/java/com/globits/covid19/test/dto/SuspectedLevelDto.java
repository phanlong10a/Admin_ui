package com.globits.covid19.test.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;

import com.globits.core.dto.BaseObjectDto;
import com.globits.covid19.test.domain.HealthOrganization;
import com.globits.covid19.test.domain.Sample;
import com.globits.covid19.test.domain.SuspectedLevel;

public class SuspectedLevelDto extends BaseObjectDto {
    private String name;
    private String code;
	private String description;//Mổ tả
	private UUID parentId;
	private SuspectedLevelDto parent;
	private List<SuspectedLevelDto> chidren = new ArrayList<>(); 
	
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

	public SuspectedLevelDto getParent() {
		return parent;
	}

	public void setParent(SuspectedLevelDto parent) {
		this.parent = parent;
	}

	public List<SuspectedLevelDto> getChidren() {
		return chidren;
	}

	public void setChidren(List<SuspectedLevelDto> chidren) {
		this.chidren = chidren;
	}

	public UUID getParentId() {
		return parentId;
	}

	public void setParentId(UUID parentId) {
		this.parentId = parentId;
	}

	public SuspectedLevelDto() {
        super();
    }

    public SuspectedLevelDto(SuspectedLevel entity ) {
//        if (entity != null) {
//            this.id = entity.getId();
//            this.name = entity.getName();
//            this.code = entity.getCode();
//            this.description = entity.getDescription();
//        }
    	this(entity, true);
    }

    public SuspectedLevelDto(SuspectedLevel entity, Boolean isParent ) {
    	   if (entity != null) {
               this.id = entity.getId();
               this.name = entity.getName();
               this.code = entity.getCode();
               this.description = entity.getDescription();
   
               if(isParent) {
   				if(entity.getParent() != null) {
   					this.parent = new SuspectedLevelDto();
   					this.parent.setCode(entity.getParent().getCode());
   					this.parent.setName(entity.getParent().getName());
   					this.parent.setDescription(entity.getParent().getDescription());
   					this.parent.setId(entity.getParent().getId());
   					this.parentId = entity.getParent().getId();
   				}
   			   }
               if(entity.getChidren() != null && entity.getChidren().size() > 0) {
   				this.chidren = new ArrayList<>();
   				for (SuspectedLevel suspectedLevel : entity.getChidren()) {
   					this.chidren.add(new SuspectedLevelDto(suspectedLevel, true));
   					}
   				}
           }
    }
}
