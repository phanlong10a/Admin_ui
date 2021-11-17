package com.globits.covid19.test.domain;

import java.util.Date;
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


/**
 * Sample entity
 * @author linhtt
 * @since 2021/02/04
 */
@Entity
@Table(name = "tbl_sample")
@XmlRootElement
public class Sample extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="name")
	private String name;
	
	@Column(name="code",nullable = false,unique = true)
	private String code;//Mã mẫu - tự sinh nhưng tạm thời cho gõ vào
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="parent_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private Sample parent;
	
	@Column(name="sample_type")
	private Integer sampleType;//Loại mẫu
	@Column(name="sample_date")
	private Date sampleDate;//Ngày lấy mẫu
	
	@Column(name="ship_date")
	private Date shipDate;//Ngày gửi mẫu
	
	@Column(name="testing_date")
	private Date testingDate;//Ngày làm xét nghiệm
	
	@Column(name="result_date")
	private Date resultDate;//Ngày có kết quả
	
	@Column(name = "sample_result")
	private String sampleResult; //Kết quả mẫu
	
	@OneToMany(mappedBy = "parent")
	private Set<Sample> chidren;
	
	@Column(name = "is_update")
	private Boolean isUpdateSampleResult; // đã update kết quả mẫu hay chưa
	
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name="person_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private SuspectedPerson person;//Người được lấy mẫu
	
	@Column(name="sample_status")
	private String sampleStatus;//
	
	@ManyToOne
	@JoinColumn(name="sample_admin_unit_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private NCoVAdministrativeUnit sampleAdminUnit;//Đơn vị hành chính lấy mẫu - có thể chưa cần sử dụng
	
	
	@ManyToOne
	@JoinColumn(name="tab_test_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private HealthOrganization labTest;//Đơn vị xét nghiệm
	
	@ManyToOne
	@JoinColumn(name="sample_collect_org_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private HealthOrganization sampleCollectOrg;//Đơn vị lấy mẫu
	
	@Column(name="sample_test_type")
	private Integer sampleTestType;//Nguồn gốc mẫu (từ ngoài vào=1, nội bộ tỉnh=0, gửi ra ngoài=2)
	
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

	public Sample getParent() {
		return parent;
	}

	public void setParent(Sample parent) {
		this.parent = parent;
	}

	public Integer getSampleType() {
		return sampleType;
	}

	public void setSampleType(Integer sampleType) {
		this.sampleType = sampleType;
	}

	public Set<Sample> getChidren() {
		return chidren;
	}

	public void setChidren(Set<Sample> chidren) {
		this.chidren = chidren;
	}


	public SuspectedPerson getPerson() {
		return person;
	}

	public void setPerson(SuspectedPerson person) {
		this.person = person;
	}

	public String getSampleStatus() {
		return sampleStatus;
	}

	public void setSampleStatus(String sampleStatus) {
		this.sampleStatus = sampleStatus;
	}

	public NCoVAdministrativeUnit getSampleAdminUnit() {
		return sampleAdminUnit;
	}

	public void setSampleAdminUnit(NCoVAdministrativeUnit sampleAdminUnit) {
		this.sampleAdminUnit = sampleAdminUnit;
	}

	public Date getSampleDate() {
		return sampleDate;
	}

	public void setSampleDate(Date sampleDate) {
		this.sampleDate = sampleDate;
	}

	public Date getResultDate() {
		return resultDate;
	}

	public void setResultDate(Date resultDate) {
		this.resultDate = resultDate;
	}

	public HealthOrganization getLabTest() {
		return labTest;
	}

	public void setLabTest(HealthOrganization labTest) {
		this.labTest = labTest;
	}

	public Date getTestingDate() {
		return testingDate;
	}

	public void setTestingDate(Date testingDate) {
		this.testingDate = testingDate;
	}

	public HealthOrganization getSampleCollectOrg() {
		return sampleCollectOrg;
	}

	public void setSampleCollectOrg(HealthOrganization sampleCollectOrg) {
		this.sampleCollectOrg = sampleCollectOrg;
	}

	public Date getShipDate() {
		return shipDate;
	}

	public void setShipDate(Date shipDate) {
		this.shipDate = shipDate;
	}

	public String getSampleResult() {
		return sampleResult;
	}

	public void setSampleResult(String sampleResult) {
		this.sampleResult = sampleResult;
	}

	public Integer getSampleTestType() {
		return sampleTestType;
	}

	public void setSampleTestType(Integer sampleTestType) {
		this.sampleTestType = sampleTestType;
	}

	public Boolean getIsUpdateSampleResult() {
		return isUpdateSampleResult;
	}

	public void setIsUpdateSampleResult(Boolean isUpdateSampleResult) {
		this.isUpdateSampleResult = isUpdateSampleResult;
	}
	
	
}
