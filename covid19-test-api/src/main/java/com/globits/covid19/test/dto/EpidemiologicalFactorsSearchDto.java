package com.globits.covid19.test.dto;

import java.util.UUID;

public class EpidemiologicalFactorsSearchDto extends SearchDto {
    private UUID parentId;
    private String name;
    private String code;

    public UUID getParentId() {
        return parentId;
    }

    public void setParentId(UUID parentId) {
        this.parentId = parentId;
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
