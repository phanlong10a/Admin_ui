package com.globits.covid19.test.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;

@Entity
@Table(name = "tbl_health_org_report")
@XmlRootElement
public class OrganizationHealthReport extends BaseObject{
	@Column(name="name")
	private String name;
	@Column(name="report_date")
	private Date reportDate;
	@OneToMany(mappedBy = "report")
	private Set<HealthResourceReport> resources;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getReportDate() {
		return reportDate;
	}
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	public Set<HealthResourceReport> getResources() {
		return resources;
	}
	public void setResources(Set<HealthResourceReport> resources) {
		this.resources = resources;
	}
}
