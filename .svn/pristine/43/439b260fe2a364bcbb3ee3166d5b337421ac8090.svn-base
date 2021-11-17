package com.globits.covid19.test.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;
/**
 * Sample entity
 * @author dunghq
 * @since 2021/05/20
 */
@Entity
@Table(name = "tbl_related_person")
@XmlRootElement
public class RelatedPerson extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9149804868071450652L;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="person_id")
	private SuspectedPerson person;//Đối tượng chính
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="contact_person_id")
	private SuspectedPerson contactPerson;//Người tiếp xúc 
	@Column(name="contact_type")
	private Integer contactType;//Loại tiếp xúc (sẽ xử lý sau)
	
	public SuspectedPerson getPerson() {
		return person;
	}
	public void setPerson(SuspectedPerson person) {
		this.person = person;
	}
	public SuspectedPerson getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(SuspectedPerson contactPerson) {
		this.contactPerson = contactPerson;
	}
	public Integer getContactType() {
		return contactType;
	}
	public void setContactType(Integer contactType) {
		this.contactType = contactType;
	}


}
