package com.globits.covid19.test.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;

/*
 * Bảng này dùng để quản lý các báo cáo tài nguyên từ các đơn vị
 * Mỗi lần đơn vị báo cáo sẽ tạo ra 1 bản ghi này cho 1 loại tài nguyên báo cáo (ví dụ : Số lượng máy thở, số lượng test-kit Covid 19, ...)
 * Số lượng sẽ là số lượng hiện thời tại thời điểm báo cáo
 * Ngày báo cáo sẽ được lưu dư thừa để dễ query dữ liệu sau cùng (sẽ sử dụng để làm báo cáo tổng hợp)
 */
@Entity
@Table(name = "tbl_health_resource_report")
@XmlRootElement
public class HealthResourceReport extends BaseObject{
	private static final long serialVersionUID = -8999417281822675962L;
	@ManyToOne
	@JoinColumn(name="org_id")
	private HealthOrganization org;
	@ManyToOne
	@JoinColumn(name="resource_id")
	private HealthResource resource;
	@ManyToOne
	@JoinColumn(name="report_id")
	private OrganizationHealthReport report;
	@Column(name="report_date")
	private Date reportDate;
	
	@Column(name="quantity")
	private Integer quantity;
	
	public HealthOrganization getOrg() {
		return org;
	}
	public void setOrg(HealthOrganization org) {
		this.org = org;
	}
	public HealthResource getResource() {
		return resource;
	}
	public void setResource(HealthResource resource) {
		this.resource = resource;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public OrganizationHealthReport getReport() {
		return report;
	}
	public void setReport(OrganizationHealthReport report) {
		this.report = report;
	}
	public Date getReportDate() {
		return reportDate;
	}
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	
	
}
