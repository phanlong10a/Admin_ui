package com.globits.covid19.test.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.globits.core.domain.BaseObject;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.util.Set;

@Entity
@Table(name = "tbl_epidemiological_factors")
@XmlRootElement
public class EpidemiologicalFactors extends BaseObject{
	@Column(name="name")
	private String name;
	
	@Column(name="code",nullable = false,unique = true)
	private String code;//Mã mẫu - tự sinh nhưng tạm thời cho gõ vào
	
	@Column(name="description")
	private String description;//Mổ tả

	@ManyToOne
	@JoinColumn(name = "parent_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private EpidemiologicalFactors parent;

	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
	private Set<EpidemiologicalFactors> children;

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

	public EpidemiologicalFactors getParent() {
		return parent;
	}

	public void setParent(EpidemiologicalFactors parent) {
		this.parent = parent;
	}

	public Set<EpidemiologicalFactors> getChildren() {
		return children;
	}

	public void setChildren(Set<EpidemiologicalFactors> children) {
		this.children = children;
	}
}
