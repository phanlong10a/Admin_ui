package com.globits.covid19.test.dto;

import com.globits.core.dto.BaseObjectDto;
import com.globits.covid19.test.domain.EpidemiologicalFactors;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EpidemiologicalFactorsDto extends BaseObjectDto {
    private String name;
    private String code;
    private String description;//Mổ tả
    private EpidemiologicalFactorsDto parent;
    private UUID parentId;
    private List<EpidemiologicalFactorsDto> children = new ArrayList<>();

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

    public EpidemiologicalFactorsDto getParent() {
        return parent;
    }

    public void setParent(EpidemiologicalFactorsDto parent) {
        this.parent = parent;
    }

    public UUID getParentId() {
        return parentId;
    }

    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }

    public List<EpidemiologicalFactorsDto> getChildren() {
        return children;
    }

    public void setChildren(List<EpidemiologicalFactorsDto> children) {
        this.children = children;
    }

    public EpidemiologicalFactorsDto() {
        super();
    }

    public EpidemiologicalFactorsDto(EpidemiologicalFactors entity) {
        this(entity, true);
    }

    public EpidemiologicalFactorsDto(EpidemiologicalFactors entity, Boolean isParent) {
        if (entity != null) {
            this.id = entity.getId();
            this.name = entity.getName();
            this.code = entity.getCode();
            this.description = entity.getDescription();
            if (entity.getParent() != null) {
                if (isParent) {
                    this.parent = new EpidemiologicalFactorsDto();
                    this.parent.setCode(entity.getParent().getCode());
                    this.parent.setName(entity.getParent().getName());
                    this.parent.setDescription(entity.getParent().getDescription());
                }
                this.parentId = entity.getParent().getId();
            }
            if (entity.getChildren() != null && entity.getChildren().size() > 0) {
                this.children = new ArrayList<>();
                for (EpidemiologicalFactors epFac : entity.getChildren()) {
                    this.children.add(new EpidemiologicalFactorsDto(epFac, true));
                }
            }
        }
    }

//    public EpidemiologicalFactorsDto(EpidemiologicalFactors entity, Boolean isParent, int child) {
//        if (entity != null) {
//            this.id = entity.getId();
//            this.name = entity.getName();
//            this.code = entity.getCode();
//            this.description = entity.getDescription();
//        }
//    }
}
