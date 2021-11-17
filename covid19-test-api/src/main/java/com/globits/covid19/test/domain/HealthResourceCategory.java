package com.globits.covid19.test.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;
@Entity
@Table(name = "tbl_health_resource_category")
@XmlRootElement
public class HealthResourceCategory extends BaseObject{
	private static final long serialVersionUID = 6185331698779919314L;
	@Column(name="name")
	private String name;
	
	@Column(name="code")
	private String code;

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
