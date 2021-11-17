package com.globits.covid19.test.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.AdministrativeUnit;
import com.globits.core.domain.BaseObject;
import com.globits.core.domain.Person;


/**
 * IsolationCenter entity
 * @author linhtt
 * @since 2021/02/04
 */
@Entity
@Table(name = "tbl_isolation_center ")
@XmlRootElement
public class IsolationCenter extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="name")
	private String name;
	
	@Column(name="code",nullable = false,unique = true)
	private String code;//Mã  - tự sinh nhưng tạm thời cho gõ vào
	
    @Column(name="address") // dia chi
    private String address;
    
    @Column(name="contact") // thong tin lien he
    private String contact;
    
	@Column(name="note")
	private Date note;//thong tin them, ghi chu
	
	@ManyToOne
	@JoinColumn(name="admin_unit_id")
	private NCoVAdministrativeUnit adminUnit;//Đơn vị hành chính - có thể chưa cần sử dụng

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

    public NCoVAdministrativeUnit getAdminUnit() {
        return adminUnit;
    }

    public void setAdminUnit(NCoVAdministrativeUnit adminUnit) {
        this.adminUnit = adminUnit;
    }
	
	
}
