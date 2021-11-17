package com.globits.covid19.test.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;

import com.globits.core.dto.BaseObjectDto;
import com.globits.covid19.test.domain.Sample;
import com.globits.covid19.test.domain.SuspectedPerson;

public class SuspectedPersonDto extends BaseObjectDto{
	private String name;
	private String giveName;
	private Date birthDate;
	private String gender;
	private NCoVAdministrativeUnitDto wardOfResidence;//xã/phường cư trú
	private NCoVAdministrativeUnitDto districtOfResidence;//quận/huyện cư trú
	private NCoVAdministrativeUnitDto provinceOfResidence;//tỉnh/thành phố cư trú
	private NCoVAdministrativeUnitDto residentAdminUnit;//Địa chỉ thường trú
	private SuspectedLevelDto suspectedLevel;//F0, F1, F2, F3, F4
	private SuspectedTypeDto suspectedType;
	private EpidemiologicalFactorsDto epidemiologicalFactors;
	private String description;
	private String phoneNumber;
	private Integer feverStatus;
	private Integer coughStatus;
	private Integer shortnessOfBreath;
	private Integer soreThroat;
	private String detailAddress;
	private String idNumber; //Số căn cước 
	private Set<SampleDto> samples;
	private String samplingLocation; //Địa điểm lẫy mẫu
	private HealthOrganizationDto isolationPlace;//Nơi cách ly
	private String detailEpidemiologicalFactors;//Yếu tố dịch tễ chi tiết (mô tả)
	private String career; //Nghề nghiệp
	private Date lastContactDate;
	private Date dateOfLastContact;// Ngày tiếp xúc cuối cùng
	private Integer pneumoniaStatus;//Viêm phổi(Có hoặc không 1 = Có, 2=không)
	// dữ liệu từ import file
	private String personName;
	private String personIdNumber; //Số căn cước
	private Date personBirthDate;
	private String personGender;
	private String personDetailAddress;
	private String physicalAdminUnitName;
	private String sampleCollectOrgName;
	private String districtName;
	private String personPhoneNumber;
	private Date personLastContactDate;
	private String personDetailEpidemiologicalFactors;
	private String personSamplingLocation;
	private String importSuspectedLevel;
	private String importEpidemiologicalFactors;
	private String importSampleCollectOrg;
	private String importLabTest;
	private String importIsolationPlace;//Nơi cách ly
	private Integer indexImport;// xã/phường
	private String wardImport;// xã/phường
	private String districtImport;// quận/huyện
	private String provinceImport;// tỉnh/thành phố
	private String detailsError;// chi tiết dòng lỗi
	private String personCareer; //Nghề nghiệp
	private String personFeverStatus;
	private String personCoughStatus;
	private String personShortnessOfBreath;
	private String personSoreThroat;
	private String personPneumoniaStatus;//Viêm phổi(Có hoặc không 1 = Có, 2=không)

	public String getImportIsolationPlace() {
		return importIsolationPlace;
	}
	public void setImportIsolationPlace(String importIsolationPlace) {
		this.importIsolationPlace = importIsolationPlace;
	}
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public NCoVAdministrativeUnitDto getResidentAdminUnit() {
		return residentAdminUnit;
	}
	public void setResidentAdminUnit(NCoVAdministrativeUnitDto residentAdminUnit) {
		this.residentAdminUnit = residentAdminUnit;
	}
	public SuspectedLevelDto getSuspectedLevel() {
		return suspectedLevel;
	}
	public void setSuspectedLevel(SuspectedLevelDto suspectedLevel) {
		this.suspectedLevel = suspectedLevel;
	}
	public SuspectedTypeDto getSuspectedType() {
		return suspectedType;
	}
	public void setSuspectedType(SuspectedTypeDto suspectedType) {
		this.suspectedType = suspectedType;
	}

	public EpidemiologicalFactorsDto getEpidemiologicalFactors() {
		return epidemiologicalFactors;
	}
	public void setEpidemiologicalFactors(EpidemiologicalFactorsDto epidemiologicalFactors) {
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
	public Set<SampleDto> getSamples() {
		return samples;
	}
	public void setSamples(Set<SampleDto> samples) {
		this.samples = samples;
	}
	public String getSamplingLocation() {
		return samplingLocation;
	}
	public void setSamplingLocation(String samplingLocation) {
		this.samplingLocation = samplingLocation;
	}
	public HealthOrganizationDto getIsolationPlace() {
		return isolationPlace;
	}
	public void setIsolationPlace(HealthOrganizationDto isolationPlace) {
		this.isolationPlace = isolationPlace;
	}
	public String getDetailEpidemiologicalFactors() {
		return detailEpidemiologicalFactors;
	}
	public void setDetailEpidemiologicalFactors(String detailEpidemiologicalFactors) {
		this.detailEpidemiologicalFactors = detailEpidemiologicalFactors;
	}
	
	public NCoVAdministrativeUnitDto getWardOfResidence() {
		return wardOfResidence;
	}
	public void setWardOfResidence(NCoVAdministrativeUnitDto wardOfResidence) {
		this.wardOfResidence = wardOfResidence;
	}
	public NCoVAdministrativeUnitDto getDistrictOfResidence() {
		return districtOfResidence;
	}
	public void setDistrictOfResidence(NCoVAdministrativeUnitDto districtOfResidence) {
		this.districtOfResidence = districtOfResidence;
	}
	public NCoVAdministrativeUnitDto getProvinceOfResidence() {
		return provinceOfResidence;
	}
	public void setProvinceOfResidence(NCoVAdministrativeUnitDto provinceOfResidence) {
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

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public Date getPersonBirthDate() {
		return personBirthDate;
	}

	public void setPersonBirthDate(Date personBirthDate) {
		this.personBirthDate = personBirthDate;
	}

	public String getPersonGender() {
		return personGender;
	}

	public void setPersonGender(String personGender) {
		this.personGender = personGender;
	}

	public String getPersonDetailAddress() {
		return personDetailAddress;
	}

	public void setPersonDetailAddress(String personDetailAddress) {
		this.personDetailAddress = personDetailAddress;
	}

	public String getPhysicalAdminUnitName() {
		return physicalAdminUnitName;
	}

	public void setPhysicalAdminUnitName(String physicalAdminUnitName) {
		this.physicalAdminUnitName = physicalAdminUnitName;
	}

	public String getSampleCollectOrgName() {
		return sampleCollectOrgName;
	}

	public void setSampleCollectOrgName(String sampleCollectOrgName) {
		this.sampleCollectOrgName = sampleCollectOrgName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getPersonPhoneNumber() {
		return personPhoneNumber;
	}

	public void setPersonPhoneNumber(String personPhoneNumber) {
		this.personPhoneNumber = personPhoneNumber;
	}

	public Date getPersonLastContactDate() {
		return personLastContactDate;
	}

	public void setPersonLastContactDate(Date personLastContactDate) {
		this.personLastContactDate = personLastContactDate;
	}

	public String getPersonDetailEpidemiologicalFactors() {
		return personDetailEpidemiologicalFactors;
	}

	public void setPersonDetailEpidemiologicalFactors(String personDetailEpidemiologicalFactors) {
		this.personDetailEpidemiologicalFactors = personDetailEpidemiologicalFactors;
	}

	public String getPersonSamplingLocation() {
		return personSamplingLocation;
	}

	public void setPersonSamplingLocation(String personSamplingLocation) {
		this.personSamplingLocation = personSamplingLocation;
	}

	public String getImportSuspectedLevel() {
		return importSuspectedLevel;
	}

	public void setImportSuspectedLevel(String importSuspectedLevel) {
		this.importSuspectedLevel = importSuspectedLevel;
	}

	public String getImportEpidemiologicalFactors() {
		return importEpidemiologicalFactors;
	}

	public void setImportEpidemiologicalFactors(String importEpidemiologicalFactors) {
		this.importEpidemiologicalFactors = importEpidemiologicalFactors;
	}

	public String getImportSampleCollectOrg() {
		return importSampleCollectOrg;
	}

	public void setImportSampleCollectOrg(String importSampleCollectOrg) {
		this.importSampleCollectOrg = importSampleCollectOrg;
	}

	public String getImportLabTest() {
		return importLabTest;
	}

	public void setImportLabTest(String importLabTest) {
		this.importLabTest = importLabTest;
	}

	public Integer getIndexImport() {
		return indexImport;
	}

	public void setIndexImport(Integer indexImport) {
		this.indexImport = indexImport;
	}

	public String getWardImport() {
		return wardImport;
	}

	public void setWardImport(String wardImport) {
		this.wardImport = wardImport;
	}

	public String getDistrictImport() {
		return districtImport;
	}

	public void setDistrictImport(String districtImport) {
		this.districtImport = districtImport;
	}

	public String getProvinceImport() {
		return provinceImport;
	}

	public void setProvinceImport(String provinceImport) {
		this.provinceImport = provinceImport;
	}

	public String getDetailsError() {
		return detailsError;
	}

	public void setDetailsError(String detailsError) {
		this.detailsError = detailsError;
	}

	public String getPersonIdNumber() {
		return personIdNumber;
	}

	public void setPersonIdNumber(String personIdNumber) {
		this.personIdNumber = personIdNumber;
	}

	public String getPersonCareer() {
		return personCareer;
	}

	public void setPersonCareer(String personCareer) {
		this.personCareer = personCareer;
	}

	public String getPersonFeverStatus() {
		return personFeverStatus;
	}
	public void setPersonFeverStatus(String personFeverStatus) {
		this.personFeverStatus = personFeverStatus;
	}
	public String getPersonCoughStatus() {
		return personCoughStatus;
	}
	public void setPersonCoughStatus(String personCoughStatus) {
		this.personCoughStatus = personCoughStatus;
	}
	public String getPersonShortnessOfBreath() {
		return personShortnessOfBreath;
	}
	public void setPersonShortnessOfBreath(String personShortnessOfBreath) {
		this.personShortnessOfBreath = personShortnessOfBreath;
	}
	public String getPersonSoreThroat() {
		return personSoreThroat;
	}
	public void setPersonSoreThroat(String personSoreThroat) {
		this.personSoreThroat = personSoreThroat;
	}
	public String getPersonPneumoniaStatus() {
		return personPneumoniaStatus;
	}
	public void setPersonPneumoniaStatus(String personPneumoniaStatus) {
		this.personPneumoniaStatus = personPneumoniaStatus;
	}
	public SuspectedPersonDto() {
		super();
	}
	public SuspectedPersonDto(SuspectedPerson entity, boolean sample) {
		if(entity !=null) {
			this.id =entity.getId();
			this.name = entity.getName();
			this.giveName = entity.getGiveName();
			this.gender = entity.getGender();
			this.birthDate = entity.getBirthDate();
			this.description = entity.getDescription();
			this.phoneNumber = entity.getPhoneNumber();
			this.feverStatus = entity.getFeverStatus();
			this.coughStatus = entity.getCoughStatus();
			this.shortnessOfBreath = entity.getShortnessOfBreath();
			this.soreThroat = entity.getSoreThroat();
			this.detailAddress = entity.getDetailAddress();
			this.idNumber = entity.getIdNumber();
			this.samplingLocation = entity.getSamplingLocation();
			this.detailEpidemiologicalFactors =  entity.getDetailEpidemiologicalFactors();
			this.lastContactDate = entity.getLastContactDate();
			this.dateOfLastContact = entity.getDateOfLastContact();
			this.pneumoniaStatus = entity.getPneumoniaStatus();
			this.career = entity.getCareer();
			if (entity.getIsolationPlace() != null) {
				this.isolationPlace = new HealthOrganizationDto(entity.getIsolationPlace(), true, 1);
			}
			if(entity.getSuspectedLevel()!=null) {
				suspectedLevel = new SuspectedLevelDto();
				suspectedLevel.setCode(entity.getSuspectedLevel().getCode());
				suspectedLevel.setName(entity.getSuspectedLevel().getName());
				suspectedLevel.setId(entity.getSuspectedLevel().getId());
				suspectedLevel.setCreatedBy(entity.getSuspectedLevel().getCreatedBy());
			}
			if(entity.getEpidemiologicalFactors()!=null) {
				epidemiologicalFactors = new EpidemiologicalFactorsDto();
				epidemiologicalFactors.setCode(entity.getEpidemiologicalFactors().getCode());
				epidemiologicalFactors.setName(entity.getEpidemiologicalFactors().getName());
				epidemiologicalFactors.setId(entity.getEpidemiologicalFactors().getId());
				epidemiologicalFactors.setCreatedBy(entity.getEpidemiologicalFactors().getCreatedBy());	
			}

			if(entity.getWardOfResidence() != null){
				this.wardOfResidence = new NCoVAdministrativeUnitDto(entity.getWardOfResidence(), true, 1);
			}
			if(entity.getDistrictOfResidence() != null){
				this.districtOfResidence = new NCoVAdministrativeUnitDto(entity.getDistrictOfResidence(), true, 1);
			}
			if(entity.getProvinceOfResidence() != null){
				this.provinceOfResidence = new NCoVAdministrativeUnitDto(entity.getProvinceOfResidence(), true, 1);
			}
			if(entity.getResidentAdminUnit() != null){
				this.residentAdminUnit = new NCoVAdministrativeUnitDto(entity.getResidentAdminUnit());
			}
			if(entity.getSuspectedLevel() != null){
				this.suspectedLevel = new SuspectedLevelDto(entity.getSuspectedLevel());
			}
			if(entity.getSuspectedType() != null){
				this.suspectedType = new SuspectedTypeDto(entity.getSuspectedType());
			}
			if(!sample) {
				if (entity.getSamples() != null && entity.getSamples().size() > 0) {
					this.samples = new HashSet<SampleDto>();
					for (Sample s : entity.getSamples()) {
						this.samples.add(new SampleDto(s, false));
					}
				}
			}
		}
	}
}
