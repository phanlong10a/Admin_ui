package com.globits.covid19.test.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;
@Entity
@Table(name = "tbl_health_resource")
@XmlRootElement
public class HealthResource extends BaseObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6185331698779919314L;
	@Column(name="name")
	private String name;
	
	@Column(name="code")
	private String code;

	
	@ManyToOne
	@JoinColumn(name="category_id")
	private HealthResourceCategory category;
	
	
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
	public HealthResourceCategory getCategory() {
		return category;
	}
	public void setCategory(HealthResourceCategory category) {
		this.category = category;
	}
}
