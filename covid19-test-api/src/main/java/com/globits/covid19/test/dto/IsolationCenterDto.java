package com.globits.covid19.test.dto;

import java.util.Date;

import com.globits.core.dto.BaseObjectDto;
import com.globits.covid19.test.domain.IsolationCenter;

public class IsolationCenterDto extends BaseObjectDto {
	private String name;
	private String code;// Mã - tự sinh nhưng tạm thời cho gõ vào
	private String address; // dia chi
	private String contact; // thong tin lien he
	private Date note;// thong tin them, ghi chu
	private NCoVAdministrativeUnitDto adminUnit; // Đơn vị hành chính - có thể chưa cần sử dụng

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public Date getNote() {
		return note;
	}

	public void setNote(Date note) {
		this.note = note;
	}

	public NCoVAdministrativeUnitDto getAdminUnit() {
		return adminUnit;
	}

	public void setAdminUnit(NCoVAdministrativeUnitDto adminUnit) {
		this.adminUnit = adminUnit;
	}

	public IsolationCenterDto() {
		super();
	}

	public IsolationCenterDto(IsolationCenter entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.name = entity.getName();
			this.code = entity.getCode();
			this.address = entity.getAddress();
			this.contact = entity.getContact();
			this.note = entity.getNote();
			if(entity.getAdminUnit() != null) {
				this.adminUnit = new NCoVAdministrativeUnitDto(entity.getAdminUnit(), true);
			}
		}
	}
}
