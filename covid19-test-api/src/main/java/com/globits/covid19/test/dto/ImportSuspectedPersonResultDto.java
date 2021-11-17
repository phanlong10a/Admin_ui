package com.globits.covid19.test.dto;

import java.util.List;

public class ImportSuspectedPersonResultDto {
    private boolean success = false;
    private boolean duplicate = false; //check duplicate data (theo code)
    private List<SuspectedPersonDto> listData;
//    private List<SuspectedPersonDto> listSampleGroup;//List mau gop
    List<String> listCodeDuplicate;
    private String text;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isDuplicate() {
        return duplicate;
    }

    public void setDuplicate(boolean duplicate) {
        this.duplicate = duplicate;
    }

    public List<SuspectedPersonDto> getListData() {
        return listData;
    }

    public void setListData(List<SuspectedPersonDto> listData) {
        this.listData = listData;
    }

    public List<String> getListCodeDuplicate() {
        return listCodeDuplicate;
    }

    public void setListCodeDuplicate(List<String> listCodeDuplicate) {
        this.listCodeDuplicate = listCodeDuplicate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
