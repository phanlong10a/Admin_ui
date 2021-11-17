package com.globits.covid19.test.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.globits.core.dto.BaseObjectDto;
import com.globits.covid19.test.domain.Sample;

public class SampleDto extends BaseObjectDto {

	private String name;
	private String code;
	private SampleDto parent;
	private UUID parentId;
	private List<SampleDto> chidren = new ArrayList<>();
	private Integer sampleType;// Loại mẫu
	private SuspectedPersonDto person;// Người được lấy mẫu
	private String sampleStatus;//
	private NCoVAdministrativeUnitDto sampleAdminUnit;// Đơn vị hành chính lấy mẫu - có thể chưa cần sử dụng
	private HealthOrganizationDto labTest;// Đơn vị xét nghiệm
	private HealthOrganizationDto sampleCollectOrg;// Đơn vị lấy mẫu
	private Date sampleDate;// Ngày lấy mẫu
	private Date shipDate;// Ngày gửi mẫu
	private Date testingDate;// Ngày làm xét nghiệm
	private Date resultDate;// Ngày có kết quả
	private String sampleResult; // Kết quả mẫu
	private Integer sampleTestType;// Nguồn gốc mẫu (từ ngoài vào=1, nội bộ tỉnh=0, gửi ra ngoài=2)
	private Boolean isUpdateSampleResult = false; // Đã update kết quả mẫu hay chưa
	private Boolean isUpdateResult = false;
	private List<SuspectedPersonDto> listPerson;// Người được lấy mẫu
	private Boolean isGeneratorCode = false;
	// dữ liệu từ import file
	private String personName;
	private Date personBirthDate;
	private String personGender;
	private String detailAddress;
	private String physicalAdminUnitName;
	private String sampleCollectOrgName;
	private String districtName;
	private String personPhoneNumber;
	private Date lastContactDate;
	private String detailEpidemiologicalFactors;
	private String samplingLocation;
	private String importSuspectedLevel;
	private String importEpidemiologicalFactors;
	private String importSampleCollectOrg;
	private String importLabTest;
	private Integer indexImport;// xã/phường
	private String wardImport;// xã/phường
	private String districtImport;// quận/huyện
	private String provinceImport;// tỉnh/thành phố
	private String detailsError;// chi tiết dòng lỗi

	public SampleDto() {
		super();
	}

	public SampleDto(Sample entity) {
		this(entity, true);
	}

	public SampleDto(Sample entity, boolean isParent) {
		if (entity != null) {
			this.setId(entity.getId());
			this.setCreateDate(entity.getCreateDate());
			this.setCreatedBy(entity.getCreatedBy());
			this.setModifyDate(entity.getModifyDate());
			this.name = entity.getName();
			this.code = entity.getCode();
			this.sampleDate = entity.getSampleDate();
			this.shipDate = entity.getShipDate();
			this.testingDate = entity.getTestingDate();
			this.resultDate = entity.getResultDate();
			this.sampleTestType = entity.getSampleTestType();
			this.isUpdateSampleResult = entity.getIsUpdateSampleResult();
			if (isParent) {
				if (entity.getParent() != null) {
					this.parent = new SampleDto();
					this.parent.setCode(entity.getParent().getCode());
					this.parent.setName(entity.getParent().getName());
					this.parentId = entity.getParent().getId();
					this.parent.setSampleType(entity.getParent().getSampleType());
					this.parent.setSampleDate(entity.getParent().getSampleDate());
					this.parent.setShipDate(entity.getParent().getShipDate());
					this.parent.setTestingDate(entity.getParent().getTestingDate());
					this.parent.setResultDate(entity.getParent().getResultDate());
					this.parent.setSampleTestType(entity.getParent().getSampleTestType());
					this.parent.setIsUpdateSampleResult(entity.getParent().getIsUpdateSampleResult());
				}
			}

			if (entity.getSampleType() != null) {
				this.sampleType = entity.getSampleType();
			}
			if (entity.getPerson() != null) {
				this.person = new SuspectedPersonDto(entity.getPerson(), true);
			}
			if (entity.getSampleType() != null) {
				this.sampleType = entity.getSampleType();
			}
			if (entity.getSampleStatus() != null) {
				this.sampleStatus = entity.getSampleStatus();
			}
			if (entity.getSampleResult() != null) {
				this.sampleResult = entity.getSampleResult();
			}
			if (entity.getSampleAdminUnit() != null) {
				this.sampleAdminUnit = new NCoVAdministrativeUnitDto(entity.getSampleAdminUnit(), true);
			}
			if (entity.getChidren() != null && entity.getChidren().size() > 0) {
				this.chidren = new ArrayList<>();
				for (Sample sample : entity.getChidren()) {
					this.chidren.add(new SampleDto(sample, true));
				}
			}
			if (entity.getLabTest() != null) {
				this.labTest = new HealthOrganizationDto(entity.getLabTest(), true);
			}
			if (entity.getSampleCollectOrg() != null) {
				this.sampleCollectOrg = new HealthOrganizationDto(entity.getSampleCollectOrg(), true);
			}
		}
	}
	
	public SampleDto(Sample entity, boolean isParent, boolean isChidren) {
		if (entity != null) {
			this.setId(entity.getId());
			this.setCreateDate(entity.getCreateDate());
			this.setCreatedBy(entity.getCreatedBy());
			this.setModifyDate(entity.getModifyDate());
			this.name = entity.getName();
			this.code = entity.getCode();
			this.sampleDate = entity.getSampleDate();
			this.shipDate = entity.getShipDate();
			this.testingDate = entity.getTestingDate();
			this.resultDate = entity.getResultDate();
			this.sampleTestType = entity.getSampleTestType();
			if (isParent) {
				if (entity.getParent() != null) {
					this.parent = new SampleDto();
					this.parent.setCode(entity.getParent().getCode());
					this.parent.setName(entity.getParent().getName());
					this.parentId = entity.getParent().getId();
					this.parent.setSampleType(entity.getParent().getSampleType());
					this.parent.setSampleDate(entity.getParent().getSampleDate());
					this.parent.setShipDate(entity.getParent().getShipDate());
					this.parent.setTestingDate(entity.getParent().getTestingDate());
					this.parent.setResultDate(entity.getParent().getResultDate());
					this.parent.setSampleTestType(entity.getParent().getSampleTestType());
				}
			}

			if (entity.getSampleType() != null) {
				this.sampleType = entity.getSampleType();
			}
			if (entity.getPerson() != null) {
				this.person = new SuspectedPersonDto(entity.getPerson(), true);
			}
			if (entity.getSampleType() != null) {
				this.sampleType = entity.getSampleType();
			}
			if (entity.getSampleStatus() != null) {
				this.sampleStatus = entity.getSampleStatus();
			}
			if (entity.getSampleResult() != null) {
				this.sampleResult = entity.getSampleResult();
			}
			if (entity.getSampleAdminUnit() != null) {
				this.sampleAdminUnit = new NCoVAdministrativeUnitDto(entity.getSampleAdminUnit(), true);
			}
			if(isChidren) {
				if (entity.getChidren() != null && entity.getChidren().size() > 0) {
					this.chidren = new ArrayList<>();
					for (Sample sample : entity.getChidren()) {
						this.chidren.add(new SampleDto(sample, true));
					}
				}
			}
			if (entity.getLabTest() != null) {
				this.labTest = new HealthOrganizationDto(entity.getLabTest(), true);
			}
			if (entity.getSampleCollectOrg() != null) {
				this.sampleCollectOrg = new HealthOrganizationDto(entity.getSampleCollectOrg(), true);
			}
		}
	}

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

	public SampleDto getParent() {
		return parent;
	}

	public void setParent(SampleDto parent) {
		this.parent = parent;
	}

	public Integer getSampleType() {
		return sampleType;
	}

	public void setSampleType(Integer sampleType) {
		this.sampleType = sampleType;
	}

	public SuspectedPersonDto getPerson() {
		return person;
	}

	public void setPerson(SuspectedPersonDto person) {
		this.person = person;
	}

	public UUID getParentId() {
		return parentId;
	}

	public void setParentId(UUID parentId) {
		this.parentId = parentId;
	}

	public List<SampleDto> getChidren() {
		return chidren;
	}

	public void setChidren(List<SampleDto> chidren) {
		this.chidren = chidren;
	}

	public String getSampleStatus() {
		return sampleStatus;
	}

	public void setSampleStatus(String sampleStatus) {
		this.sampleStatus = sampleStatus;
	}

	public NCoVAdministrativeUnitDto getSampleAdminUnit() {
		return sampleAdminUnit;
	}

	public void setSampleAdminUnit(NCoVAdministrativeUnitDto sampleAdminUnit) {
		this.sampleAdminUnit = sampleAdminUnit;
	}

	public HealthOrganizationDto getLabTest() {
		return labTest;
	}

	public void setLabTest(HealthOrganizationDto labTest) {
		this.labTest = labTest;
	}

	public HealthOrganizationDto getSampleCollectOrg() {
		return sampleCollectOrg;
	}

	public void setSampleCollectOrg(HealthOrganizationDto sampleCollectOrg) {
		this.sampleCollectOrg = sampleCollectOrg;
	}

	public Date getSampleDate() {
		return sampleDate;
	}

	public void setSampleDate(Date sampleDate) {
		this.sampleDate = sampleDate;
	}

	public Date getShipDate() {
		return shipDate;
	}

	public void setShipDate(Date shipDate) {
		this.shipDate = shipDate;
	}

	public Date getTestingDate() {
		return testingDate;
	}

	public void setTestingDate(Date testingDate) {
		this.testingDate = testingDate;
	}

	public Date getResultDate() {
		return resultDate;
	}

	public void setResultDate(Date resultDate) {
		this.resultDate = resultDate;
	}
	
	public Boolean getIsUpdateSampleResult() {
		return isUpdateSampleResult;
	}

	public void setIsUpdateSampleResult(Boolean isUpdateSampleResult) {
		this.isUpdateSampleResult = isUpdateSampleResult;
	}

	////////// import excle

	public Boolean getIsGeneratorCode() {
		return isGeneratorCode;
	}

	public void setIsGeneratorCode(Boolean isGeneratorCode) {
		this.isGeneratorCode = isGeneratorCode;
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

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
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

	public String getSampleResult() {
		return sampleResult;
	}

	public void setSampleResult(String sampleResult) {
		this.sampleResult = sampleResult;
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

	public Integer getIndexImport() {
		return indexImport;
	}

	public void setIndexImport(Integer indexImport) {
		this.indexImport = indexImport;
	}

	public String getDetailsError() {
		return detailsError;
	}

	public void setDetailsError(String detailsError) {
		this.detailsError = detailsError;
	}

	public Integer getSampleTestType() {
		return sampleTestType;
	}

	public void setSampleTestType(Integer sampleTestType) {
		this.sampleTestType = sampleTestType;
	}

	public String getPersonPhoneNumber() {
		return personPhoneNumber;
	}

	public void setPersonPhoneNumber(String personPhoneNumber) {
		this.personPhoneNumber = personPhoneNumber;
	}

	public Date getLastContactDate() {
		return lastContactDate;
	}

	public void setLastContactDate(Date lastContactDate) {
		this.lastContactDate = lastContactDate;
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

	public Boolean getIsUpdateResult() {
		return isUpdateResult;
	}

	public void setIsUpdateResult(Boolean isUpdateResult) {
		this.isUpdateResult = isUpdateResult;
	}

	public List<SuspectedPersonDto> getListPerson() {
		return listPerson;
	}

	public void setListPerson(List<SuspectedPersonDto> listPerson) {
		this.listPerson = listPerson;
	}

}
