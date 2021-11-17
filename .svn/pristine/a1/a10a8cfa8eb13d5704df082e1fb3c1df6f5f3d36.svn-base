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

import com.globits.core.domain.AdministrativeUnit;
import com.globits.core.domain.BaseObject;

@Entity
@Table(name = "tbl_suspected_person")
@XmlRootElement
public class SuspectedPerson extends BaseObject {
	private static final long serialVersionUID = 1L;
	@Column(name="name")
	private String name;
	@Column(name="given_name")
	private String giveName;
	@Column(name="birth_date")
	private Date birthDate;
	
	@ManyToOne
	@JoinColumn(name="ward_of_residence")
	@NotFound(action = NotFoundAction.IGNORE)
	private NCoVAdministrativeUnit wardOfResidence;//xã/phường cư trú
	
	@ManyToOne
	@JoinColumn(name="districtOfResidence")
	@NotFound(action = NotFoundAction.IGNORE)
	private NCoVAdministrativeUnit districtOfResidence;//quận/huyện cư trú
	
	@ManyToOne
	@JoinColumn(name="province_of_residence")
	@NotFound(action = NotFoundAction.IGNORE)
	private NCoVAdministrativeUnit provinceOfResidence;//tỉnh/thành phố cư trú

	@ManyToOne
	@JoinColumn(name="resident_admin_unit_id", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	private NCoVAdministrativeUnit residentAdminUnit;//Địa chỉ thường trú
	
	@Column(name="gender")
	private String gender;
	
	@ManyToOne
	@JoinColumn(name="suspected_level_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private SuspectedLevel suspectedLevel;//F0, F1, F2, F3, F4
	
	@ManyToOne
	@JoinColumn(name="suspected_type_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private SuspectedType suspectedType;//Từ nước ngoài về, tiếp xúc người nhiễm, đi từ vùng dịch ra, ...
	@ManyToOne
	@JoinColumn(name="epidemiological_factors_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private EpidemiologicalFactors epidemiologicalFactors;
	
	@Column(name="detail_epidemiological_factors")
	private String detailEpidemiologicalFactors;//Yếu tố dịch tễ chi tiết (mô tả)
	
	@Column(name="description")
	private String description;
	
	@Column(name="phone_number")
	private String phoneNumber;//Số điện thoại
	
	@Column(name="fever_status")
	private Integer feverStatus;//Sốt (Có hoặc không 1 = Có, 2=không)
	
	@Column(name="cough_status")
	private Integer coughStatus;//Ho (Có hoặc không 1 = Có, 2=không)
	
	@Column(name="shortness_breath")
	private Integer shortnessOfBreath;//Khó thở (Có hoặc không 1 = Có, 2=không)
	
	@Column(name="sore_throat")
	private Integer soreThroat;//Đau họng(Có hoặc không 1 = Có, 2=không)
	
	@Column(name="detail_address")
	private String detailAddress;//Địa chỉ chi tiết hiện nay
	
	@Column(name="id_number")
	private String idNumber; //Số căn cước 
	
	@Column(name="sampling_location")
	private String samplingLocation; //Địa điểm lẫy mẫu
	
	@Column(name="career")
	private String career; //Nghề nghiệp
	
	@ManyToOne
	@JoinColumn(name="isolation_place")
	private HealthOrganization isolationPlace;//Nơi cách ly
	
	@Column(name = "date_of_last_contact")
	private Date dateOfLastContact;
	
	@Column(name="pneumonia_status")
	private Integer pneumoniaStatus;//Viêm phổi(Có hoặc không 1 = Có, 2=không)
	
	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@NotFound(action = NotFoundAction.IGNORE)
	private Set<Sample> samples;
	
	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@NotFound(action = NotFoundAction.IGNORE)
	private Set<RelatedPerson> relatedPerson;

	@Column(name="last_contact_date")
	private Date lastContactDate;// Ngày tiếp xúc cuối cùng
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGiveName() {
		return giveName;
	}
	public void setGiveName(String giveName) {
		this.giveName = giveName;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public NCoVAdministrativeUnit getResidentAdminUnit() {
		return residentAdminUnit;
	}
	public void setResidentAdminUnit(NCoVAdministrativeUnit residentAdminUnit) {
		this.residentAdminUnit = residentAdminUnit;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public SuspectedLevel getSuspectedLevel() {
		return suspectedLevel;
	}
	public void setSuspectedLevel(SuspectedLevel suspectedLevel) {
		this.suspectedLevel = suspectedLevel;
	}
	public SuspectedType getSuspectedType() {
		return suspectedType;
	}
	public void setSuspectedType(SuspectedType suspectedType) {
		this.suspectedType = suspectedType;
	}
	public EpidemiologicalFactors getEpidemiologicalFactors() {
		return epidemiologicalFactors;
	}
	public void setEpidemiologicalFactors(EpidemiologicalFactors epidemiologicalFactors) {
		this.epidemiologicalFactors = epidemiologicalFactors;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Integer getFeverStatus() {
		return feverStatus;
	}
	public void setFeverStatus(Integer feverStatus) {
		this.feverStatus = feverStatus;
	}
	public Integer getCoughStatus() {
		return coughStatus;
	}
	public void setCoughStatus(Integer coughStatus) {
		this.coughStatus = coughStatus;
	}
	public Integer getShortnessOfBreath() {
		return shortnessOfBreath;
	}
	public void setShortnessOfBreath(Integer shortnessOfBreath) {
		this.shortnessOfBreath = shortnessOfBreath;
	}
	public Integer getSoreThroat() {
		return soreThroat;
	}
	public void setSoreThroat(Integer soreThroat) {
		this.soreThroat = soreThroat;
	}
	public String getDetailAddress() {
		return detailAddress;
	}
	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public Set<Sample> getSamples() {
		return samples;
	}
	public void setSamples(Set<Sample> samples) {
		this.samples = samples;
	}
	public Set<RelatedPerson> getRelatedPerson() {
		return relatedPerson;
	}
	public void setRelatedPerson(Set<RelatedPerson> relatedPerson) {
		this.relatedPerson = relatedPerson;
	}
	public String getDetailEpidemiologicalFactors() {
		return detailEpidemiologicalFactors;
	}
	public void setDetailEpidemiologicalFactors(String detailEpidemiologicalFactors) {
		this.detailEpidemiologicalFactors = detailEpidemiologicalFactors;
	}
	public String getSamplingLocation() {
		return samplingLocation;
	}
	public void setSamplingLocation(String samplingLocation) {
		this.samplingLocation = samplingLocation;
	}
	public HealthOrganization getIsolationPlace() {
		return isolationPlace;
	}
	public void setIsolationPlace(HealthOrganization isolationPlace) {
		this.isolationPlace = isolationPlace;
	}
	public NCoVAdministrativeUnit getWardOfResidence() {
		return wardOfResidence;
	}
	public void setWardOfResidence(NCoVAdministrativeUnit wardOfResidence) {
		this.wardOfResidence = wardOfResidence;
	}
	public NCoVAdministrativeUnit getDistrictOfResidence() {
		return districtOfResidence;
	}
	public void setDistrictOfResidence(NCoVAdministrativeUnit districtOfResidence) {
		this.districtOfResidence = districtOfResidence;
	}
	public NCoVAdministrativeUnit getProvinceOfResidence() {
		return provinceOfResidence;
	}
	public void setProvinceOfResidence(NCoVAdministrativeUnit provinceOfResidence) {
		this.provinceOfResidence = provinceOfResidence;
	}
	public String getCareer() {
		return career;
	}
	public void setCareer(String career) {
		this.career = career;
	}
	public Date getLastContactDate() {
		return lastContactDate;
	}
	public void setLastContactDate(Date lastContactDate) {
		this.lastContactDate = lastContactDate;
	}
	public Date getDateOfLastContact() {
		return dateOfLastContact;
	}
	public void setDateOfLastContact(Date dateOfLastContact) {
		this.dateOfLastContact = dateOfLastContact;
	}
	public Integer getPneumoniaStatus() {
		return pneumoniaStatus;
	}
	public void setPneumoniaStatus(Integer pneumoniaStatus) {
		this.pneumoniaStatus = pneumoniaStatus;
	}
	
}
