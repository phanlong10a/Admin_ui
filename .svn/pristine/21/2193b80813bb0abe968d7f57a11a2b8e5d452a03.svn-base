package com.globits.covid19.test.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.globits.core.domain.BaseObject;
@Entity
@Table(name = "tbl_suspected_level")
@XmlRootElement
public class SuspectedLevel extends BaseObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name="name")
	private String name;
	@Column(name="code")
	private String code;
	@Column(name="description")
	private String description;//Mổ tả
	
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name="parent_id")
//	@NotFound(action = NotFoundAction.IGNORE)
	private SuspectedLevel parent;
	
	@OneToMany(mappedBy = "parent")
	private Set<SuspectedLevel> chidren;
	
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
	public SuspectedLevel getParent() {
		return parent;
	}
	public void setParent(SuspectedLevel parent) {
		this.parent = parent;
	}
	public Set<SuspectedLevel> getChidren() {
		return chidren;
	}
	public void setChidren(Set<SuspectedLevel> chidren) {
		this.chidren = chidren;
	}
	
}
