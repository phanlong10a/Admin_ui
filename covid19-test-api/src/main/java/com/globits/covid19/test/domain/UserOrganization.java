package com.globits.covid19.test.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.globits.core.domain.BaseObject;
import com.globits.security.domain.Role;
import com.globits.security.domain.User;
@Entity
@Table(name = "tbl_user_organization")
@XmlRootElement
public class UserOrganization extends BaseObject {
	/**
	 * 
	 */
	
	
	
	private static final long serialVersionUID = 1L;
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	@ManyToOne
	@JoinColumn(name="org_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private HealthOrganization org;

	@OneToOne
	@JoinColumn(name = "administrative_unit_id")
	private NCoVAdministrativeUnit administrativeUnit;

	@ManyToOne
	@JoinColumn(name="role_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private Role role;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public HealthOrganization getOrg() {
		return org;
	}
	public void setOrg(HealthOrganization org) {
		this.org = org;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}

	public NCoVAdministrativeUnit getAdministrativeUnit() {
		return administrativeUnit;
	}

	public void setAdministrativeUnit(NCoVAdministrativeUnit administrativeUnit) {
		this.administrativeUnit = administrativeUnit;
	}
	
	
}
