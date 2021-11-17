package com.globits.covid19.test.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.globits.core.domain.BaseObject;
@Entity
@Table(name = "tbl_health_organization")
@XmlRootElement
public class HealthOrganization extends BaseObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name="name")
	private String name;
	@Column(name="code",nullable = false,unique = true)
	private String code;
	@Column(name="org_type")
	private Integer orgType;
	@ManyToOne
	@JoinColumn(name="parent_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private HealthOrganization parent;
	
	@OneToMany(mappedBy = "parent")
	private Set<HealthOrganization> chidren;
	
	@Column(name="is_external_org")
	private Boolean isExternalOrg;//Nếu là đơn vị ngoại tỉnh thì set = true
	
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

	public HealthOrganization getParent() {
		return parent;
	}

	public void setParent(HealthOrganization parent) {
		this.parent = parent;
	}

	public Set<HealthOrganization> getChidren() {
		return chidren;
	}

	public void setChidren(Set<HealthOrganization> chidren) {
		this.chidren = chidren;
	}

	public Integer getOrgType() {
		return orgType;
	}

	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
	}

	public Boolean getIsExternalOrg() {
		return isExternalOrg;
	}

	public void setIsExternalOrg(Boolean isExternalOrg) {
		this.isExternalOrg = isExternalOrg;
	}
	
	
}
