package com.globits.covid19.test.service.impl;

import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.globits.covid19.test.dto.*;
import com.globits.covid19.test.utilities.Enums;
import com.globits.covid19.test.utilities.NCoVConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.globits.covid19.test.domain.EpidemiologicalFactors;
import com.globits.covid19.test.domain.HealthOrganization;
import com.globits.covid19.test.domain.NCoVAdministrativeUnit;
import com.globits.covid19.test.domain.Sample;
import com.globits.covid19.test.domain.SuspectedLevel;
import com.globits.covid19.test.domain.SuspectedPerson;
import com.globits.covid19.test.domain.SuspectedType;
import com.globits.covid19.test.repository.EpidemiologicalFactorsRepository;
import com.globits.covid19.test.repository.HealthOrganizationRepository;
import com.globits.covid19.test.repository.NCoVAdministrativeUnitRepository;
import com.globits.covid19.test.repository.SampleRepository;
import com.globits.covid19.test.repository.SuspectedLevelRepository;
import com.globits.covid19.test.repository.SuspectedPersonRepository;
import com.globits.covid19.test.repository.SuspectedTypeRepository;
import com.globits.covid19.test.service.SampleService;
import com.globits.covid19.test.service.SuspectedPersonService;

@Transactional
@Service
public class SuspectedPersonServiceImpl extends GenericServiceImpl<SuspectedPerson, UUID>
        implements SuspectedPersonService {

    @Autowired
    private EntityManager manager;

    @Autowired
    public SuspectedPersonRepository personRepository;

    @Autowired
    public SuspectedLevelRepository suspectedLevelRepo;

    @Autowired
    public SuspectedTypeRepository suspectedTypeRepo;

    @Autowired
    public NCoVAdministrativeUnitRepository administrativeUnitRepository;

    @Autowired
    public SuspectedLevelRepository suspectedLevelRepository;

    @Autowired
    public SampleRepository sampleRepository;

    @Autowired
    private HealthOrganizationRepository healthOrganizationRepository;

    @Autowired
    private EpidemiologicalFactorsRepository epidemiologicalFactorsRepository;
    
    @Autowired
    private SampleService sampleService;

    @Autowired
    private NCoVAdministrativeUnitRepository nCoVAdministrativeUnitRepository;
    @Override
    public Page<SuspectedPersonDto> searchByDto(SuspectedPersonSearchDto dto) {
        if (dto == null) {
            return null;
        }
        int pageIndex = dto.getPageIndex();
        int pageSize = dto.getPageSize();

        if (pageIndex > 0) {
            pageIndex--;
        } else {
            pageIndex = 0;
        }

        String whereClause = "";
        String orderBy = " ORDER BY entity.createDate DESC";
        String sqlCount = "select count(entity.id) from SuspectedPerson as entity where (1=1) ";
        String sql = "select new com.globits.covid19.test.dto.SuspectedPersonDto(entity, false) from SuspectedPerson as entity where (1=1) ";

        if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
            whereClause += " AND (entity.name LIKE :text OR entity.idNumber LIKE :text OR entity.phoneNumber LIKE :text ) ";
//            		+ " OR entity.wardOfResidence.name LIKE :text OR entity.districtOfResidence.name LIKE :text OR entity.provinceOfResidence.name LIKE :text )";
        }
        if (dto.getIdNumber() != null && StringUtils.hasText(dto.getIdNumber())) {
            whereClause += " AND (entity.idNumber LIKE :idNumber)";
        }
        if (dto.getPhoneNumber() != null && StringUtils.hasText(dto.getPhoneNumber())) {
            whereClause += " AND (entity.phoneNumber LIKE :phoneNumber)";
        }
        if(dto.getSuspectedLevel() != null && StringUtils.hasText(dto.getSuspectedLevel().getName())) {
        	whereClause += " AND (entity.suspectedLevel.name LIKE :suspectedLevel)";
        }
        
        if(dto.getEpidemiologicalFactors() != null && StringUtils.hasText(dto.getEpidemiologicalFactors().getName())) {
        	whereClause += " AND (entity.epidemiologicalFactors.name LIKE :epidemiologicalFactors)";
        }
        
        sql += whereClause + orderBy;
        sqlCount += whereClause;
        Query q = manager.createQuery(sql, SuspectedPersonDto.class);
        Query qCount = manager.createQuery(sqlCount);

        if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
            q.setParameter("text", '%' + dto.getText().trim() + '%');
            qCount.setParameter("text", '%' + dto.getText().trim() + '%');
        }
        if (dto.getIdNumber() != null && StringUtils.hasText(dto.getIdNumber())) {
            q.setParameter("idNumber", '%' + dto.getIdNumber().trim() + '%');
            qCount.setParameter("idNumber", '%' + dto.getIdNumber().trim() + '%');
        }
        if (dto.getPhoneNumber() != null && StringUtils.hasText(dto.getPhoneNumber())) {
            q.setParameter("phoneNumber", '%' + dto.getPhoneNumber().trim() + '%');
            qCount.setParameter("phoneNumber", '%' + dto.getPhoneNumber().trim() + '%');
        }
        if (dto.getSuspectedLevel() != null && StringUtils.hasText(dto.getSuspectedLevel().getName())) {
            q.setParameter("suspectedLevel", '%' + dto.getSuspectedLevel().getName().trim() + '%');
            qCount.setParameter("suspectedLevel", '%' + dto.getSuspectedLevel().getName().trim() + '%');
        }
        if (dto.getEpidemiologicalFactors() != null && StringUtils.hasText(dto.getEpidemiologicalFactors().getName())) {
            q.setParameter("epidemiologicalFactors", '%' + dto.getEpidemiologicalFactors().getName().trim() + '%');
            qCount.setParameter("epidemiologicalFactors", '%' + dto.getEpidemiologicalFactors().getName().trim() + '%');
        }
        int startPosition = pageIndex * pageSize;
        q.setFirstResult(startPosition);
        q.setMaxResults(pageSize);
        List<SuspectedPersonDto> entities = q.getResultList();
        long count = (long) qCount.getSingleResult();

        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<SuspectedPersonDto> result = new PageImpl<SuspectedPersonDto>(entities, pageable, count);

        return result;
    }

    @Override
    public ImportSuspectedPersonResultDto importSusPerson(List<SuspectedPersonDto> listSusPersonDto) {
        ImportSuspectedPersonResultDto result = new ImportSuspectedPersonResultDto();
        List<SuspectedPersonDto> listSaveData = new ArrayList<SuspectedPersonDto>();
        String text = "";
        if (listSusPersonDto != null && listSusPersonDto.size() > 0) {

//             check mã trùng trong hệ thống rồi thì tạm thời không cho import
//            result = checkDuplicateCode(listSusPersonDto);
//            if (result.isDuplicate()) {
//                return result;
//            }

            for (SuspectedPersonDto susPersonDto : listSusPersonDto) {
                String error = "";
                SuspectedPersonDto dto = null;
                dto = new SuspectedPersonDto();
                SuspectedPerson suspectedPerson = null;

                List<SuspectedPerson> listPerson = personRepository.getByPersonByName(susPersonDto.getPersonName(),
                        susPersonDto.getPersonBirthDate());
                if (listPerson != null && listPerson.size() > 0) {
                    suspectedPerson = listPerson.get(0);
                }
                if (suspectedPerson == null) {
                    suspectedPerson = new SuspectedPerson();
                    suspectedPerson.setId(null);
                }
                suspectedPerson.setName(susPersonDto.getPersonName());//Tên
                suspectedPerson.setBirthDate(susPersonDto.getPersonBirthDate());//Năm sinh
                suspectedPerson.setGender(susPersonDto.getPersonGender());//Giới tính
                suspectedPerson.setIdNumber(susPersonDto.getPersonIdNumber()); // CMTND
                suspectedPerson.setPhoneNumber(susPersonDto.getPersonPhoneNumber());//Số điện thoại
                suspectedPerson.setLastContactDate(susPersonDto.getPersonLastContactDate());//Ngày tiếp xúc gần nhất
                suspectedPerson.setDetailEpidemiologicalFactors(susPersonDto.getDetailEpidemiologicalFactors());//Chi tiết dịch tễ
                suspectedPerson.setCareer(susPersonDto.getPersonCareer()); // Nghề nghiệp
                suspectedPerson.setDateOfLastContact(susPersonDto.getPersonLastContactDate()); // Ngày tiếp xúc cuối cùng
                
                //Đơn vị cách ly
                List<HealthOrganization> isolationPlace = null;
                if(susPersonDto.getImportIsolationPlace() != null && StringUtils.hasLength(susPersonDto.getImportIsolationPlace())) {
                	isolationPlace = healthOrganizationRepository.getByNameOrCodeIsolation(susPersonDto.getImportIsolationPlace());
                }
                if(isolationPlace != null && isolationPlace.size() > 0) {
                	suspectedPerson.setIsolationPlace(isolationPlace.get(0));
                }
//                else {
//                	error = seperateText(error, NCoVConstant.ERROR_IMPORT_MISSING_ISOLATION_PLACE, susPersonDto.getImportIsolationPlace());
//                }
                // Yếu tố nguy cơ
                List<EpidemiologicalFactors> epidemiologicalFactors = null;
                if (susPersonDto.getImportEpidemiologicalFactors() != null
                        && StringUtils.hasLength(susPersonDto.getImportEpidemiologicalFactors())) {
                    epidemiologicalFactors = epidemiologicalFactorsRepository
                            .getByNameOrCode(susPersonDto.getImportEpidemiologicalFactors());
                }
                if (epidemiologicalFactors != null && epidemiologicalFactors.size() > 0) {
                    suspectedPerson.setEpidemiologicalFactors(epidemiologicalFactors.get(0));
                } else {
                    error = seperateText(error, NCoVConstant.ERROR_IMPORT_MISSING_PERSON_EPIDEMIOLOGICAL_FACORS,
                            susPersonDto.getImportEpidemiologicalFactors());
                }

                if (epidemiologicalFactors != null && epidemiologicalFactors.size() > 0) {
                    suspectedPerson.setEpidemiologicalFactors(epidemiologicalFactors.get(0));
                } else {
                    error = seperateText(error, NCoVConstant.ERROR_IMPORT_MISSING_PERSON_EPIDEMIOLOGICAL_FACORS,
                            susPersonDto.getImportEpidemiologicalFactors());
                }

                // Cấp độ nghi nhiễm
                List<SuspectedLevel> suspectedLevel = null;
                if (susPersonDto.getImportSuspectedLevel() != null
                        && StringUtils.hasLength(susPersonDto.getImportSuspectedLevel())) {
                    suspectedLevel = suspectedLevelRepository.getByNameOrCode(susPersonDto.getImportSuspectedLevel());
                }
                if (suspectedLevel != null && suspectedLevel.size() > 0) {
                    suspectedPerson.setSuspectedLevel(suspectedLevel.get(0));
                } 
//                else {
//                    error = seperateText(error, NCoVConstant.ERROR_IMPORT_MISSING_PERSON_SUSPECTED_LEVEL,
//                            susPersonDto.getImportSuspectedLevel());
//                }
                // Địa chỉ chi tiết
                if (susPersonDto.getDetailAddress() != null) {
                    suspectedPerson.setDetailAddress(susPersonDto.getDetailAddress());
                }
                if (susPersonDto.getProvinceImport() != null && StringUtils.hasText(susPersonDto.getProvinceImport())) {

					List<NCoVAdministrativeUnit> provinceOfResidence = null;
					provinceOfResidence = nCoVAdministrativeUnitRepository
							.getProvinceByNameOrCode("%" + susPersonDto.getProvinceImport() + "%");
					if (provinceOfResidence != null && provinceOfResidence.size() > 0
							&& provinceOfResidence.get(0).getId() != null) {
						suspectedPerson.setProvinceOfResidence(provinceOfResidence.get(0));

						List<NCoVAdministrativeUnit> districtOfResidence = null;
						districtOfResidence = nCoVAdministrativeUnitRepository.getByNameOrCodeAndParentId(
								"%" + susPersonDto.getDistrictImport() + "%", provinceOfResidence.get(0).getId());

						if (districtOfResidence != null && districtOfResidence.size() > 0
								&& districtOfResidence.get(0).getId() != null) {
							suspectedPerson.setDistrictOfResidence(districtOfResidence.get(0));

							List<NCoVAdministrativeUnit> wardOfResidence = null;
							wardOfResidence = nCoVAdministrativeUnitRepository.getByNameOrCodeAndParentId(
									"%" + susPersonDto.getWardImport() + "%", districtOfResidence.get(0).getId());
							if (wardOfResidence != null && wardOfResidence.size() > 0
									&& wardOfResidence.get(0).getId() != null) {
								suspectedPerson.setWardOfResidence(wardOfResidence.get(0));
							}
						}
					}
				}
                //Sốt
                if(susPersonDto.getPersonFeverStatus() != null) {
                	Integer value =  null;
                	if(susPersonDto.getPersonFeverStatus().equals("Không")) {
                		value = 2;
                	}else if(susPersonDto.getPersonFeverStatus().equals("Có")) {
                		value = 1;
                	}
                	suspectedPerson.setFeverStatus(value); // Sốt
                }
                //Ho
                if(susPersonDto.getPersonCoughStatus() != null) {
                	Integer value =  null;
                	if(susPersonDto.getPersonCoughStatus().equals("Không")) {
                		value = 2;
                	}else if(susPersonDto.getPersonCoughStatus().equals("Có")) {
                		value = 1;
                	}
                	suspectedPerson.setCoughStatus(value);
                }
                //Khó thở
                if(susPersonDto.getPersonShortnessOfBreath() != null) {
                	Integer value =  null;
                	if(susPersonDto.getPersonShortnessOfBreath().equals("Không")) {
                		value = 2;
                	}else if(susPersonDto.getPersonShortnessOfBreath().equals("Có")) {
                		value = 1;
                	}
                	suspectedPerson.setShortnessOfBreath(value);
                }
                //Đau họng
                if(susPersonDto.getPersonSoreThroat() != null) {
                	Integer value =  null;
                	if(susPersonDto.getPersonSoreThroat().equals("Không")) {
                		value = 2;
                	}else if(susPersonDto.getPersonSoreThroat().equals("Có")) {
                		value = 1;
                	}
                	suspectedPerson.setSoreThroat(value);
                }
                //Viên phổi
                if(susPersonDto.getPersonPneumoniaStatus() != null) {
                	Integer value =  null;
                	if(susPersonDto.getPersonPneumoniaStatus().equals("Không")) {
                		value = 2;
                	}else if(susPersonDto.getPersonPneumoniaStatus().equals("Có")) {
                		value = 1;
                	}
                	suspectedPerson.setPneumoniaStatus(value);
                }

                dto = new SuspectedPersonDto(suspectedPerson, false);
                // check có lỗi hay không
                if (error != null && StringUtils.hasLength(error)) {
                    text += String.format(NCoVConstant.ERROR_IMPORT_ROW_INDEX, susPersonDto.getIndexImport())
                            + String.format(NCoVConstant.ERROR_IMPORT_NOT_FOUND, error) + System.lineSeparator()
                            + System.lineSeparator();
                } else {
                    listSaveData.add(dto);
                }
                // dto = this.saveOrUpdate(dto, dto.getId());
            }
            if (text != null && StringUtils.hasLength(text)) {
                result.setText(text);
            } else {
                result.setSuccess(true);
                result.setListData(listSaveData);
            }
        }
        return result;
    }

    @Override
    public Boolean checkDuplicateCode(UUID id, String idNumber) {
        return null;
    }

    @Override
    public ImportSuspectedPersonResultDto checkDuplicateCode(List<SuspectedPersonDto> listSusPerson) {
        ImportSuspectedPersonResultDto result = new ImportSuspectedPersonResultDto();
        result.setDuplicate(false);
        List<String> listCode = new ArrayList<String>();
        if (listSusPerson != null && listSusPerson.size() > 0) {
            for (SuspectedPersonDto susPersonDto : listSusPerson) {
                listCode.add(susPersonDto.getIdNumber());
            }
            List<String> listCodeDuplicate = personRepository.findCodeInListCode(listCode);
            if (listCodeDuplicate != null && listCodeDuplicate.size() > 0) {
                result.setDuplicate(true);
                String text = NCoVConstant.ERROR_IMPORT_DUPLICATE_CODE + System.lineSeparator();
                for (String string : listCodeDuplicate) {
                    text += string + "; ";
                }
                result.setText(text);
                result.setListCodeDuplicate(listCodeDuplicate);
            }
        }
        return result;
    }

    @Override
    public ImportSuspectedPersonResultDto saveListData(List<SuspectedPersonDto> listData) {
        ImportSuspectedPersonResultDto result = new ImportSuspectedPersonResultDto();
        result.setSuccess(true);
        if (listData != null && listData.size() > 0) {
            for (SuspectedPersonDto susPersonDto : listData) {
                this.saveOrUpdate(susPersonDto, null);
            }
        }
        return result;
    }

    @Override
    public SuspectedPersonDto saveOrUpdate(SuspectedPersonDto dto, UUID id) {
        if (dto != null) {
            SuspectedPerson entity = null;
            if (id != null) {
                entity = personRepository.findById(id).get();
            }
            if (entity == null) {
                entity = new SuspectedPerson();
            }
            entity.setDetailAddress(dto.getDetailAddress());
            entity.setName(dto.getName());
            entity.setGiveName(dto.getGiveName());
            entity.setBirthDate(dto.getBirthDate());
            entity.setGender(dto.getGender());
            entity.setSamplingLocation(dto.getSamplingLocation());
            entity.setDetailEpidemiologicalFactors(dto.getDetailEpidemiologicalFactors());
            entity.setDescription(dto.getDescription());
            entity.setPhoneNumber(dto.getPhoneNumber());
            entity.setIdNumber(dto.getIdNumber());
            entity.setCareer(dto.getCareer());
            entity.setFeverStatus(dto.getFeverStatus());
            entity.setCoughStatus(dto.getCoughStatus());
            entity.setShortnessOfBreath(dto.getShortnessOfBreath());
            entity.setSoreThroat(dto.getSoreThroat());
            entity.setDateOfLastContact(dto.getDateOfLastContact());
            entity.setLastContactDate(dto.getLastContactDate());
            entity.setPneumoniaStatus(dto.getPneumoniaStatus());
            HealthOrganization isolationPlace = null;
            if (dto.getIsolationPlace() != null && dto.getIsolationPlace().getId() != null) {
                isolationPlace = healthOrganizationRepository.getOneById(dto.getIsolationPlace().getId());
            }
            entity.setIsolationPlace(isolationPlace);

            NCoVAdministrativeUnit wardOfResidence = null;
            if (dto.getWardOfResidence() != null && dto.getWardOfResidence().getId() != null) {
                wardOfResidence = administrativeUnitRepository.findOne(dto.getWardOfResidence().getId());
            }
            entity.setWardOfResidence(wardOfResidence);

            NCoVAdministrativeUnit districtOfResidence = null;
            if (dto.getDistrictOfResidence() != null && dto.getDistrictOfResidence().getId() != null) {
                districtOfResidence = administrativeUnitRepository.findOne(dto.getDistrictOfResidence().getId());
            }
            entity.setDistrictOfResidence(districtOfResidence);

            NCoVAdministrativeUnit provinceOfResidence = null;
            if (dto.getProvinceOfResidence() != null && dto.getProvinceOfResidence().getId() != null) {
                provinceOfResidence = administrativeUnitRepository.findOne(dto.getProvinceOfResidence().getId());
            }
            entity.setProvinceOfResidence(provinceOfResidence);

            NCoVAdministrativeUnit residentAdminUnit = null;
            if (dto.getResidentAdminUnit() != null && dto.getResidentAdminUnit().getId() != null) {
                residentAdminUnit = administrativeUnitRepository.findOne(dto.getResidentAdminUnit().getId());
            }
            entity.setResidentAdminUnit(residentAdminUnit);
            SuspectedLevel suspectedLevel = null;
            if (dto.getSuspectedLevel() != null && dto.getSuspectedLevel().getId() != null) {
                suspectedLevel = suspectedLevelRepo.getOne(dto.getSuspectedLevel().getId());
            }

            entity.setSuspectedLevel(suspectedLevel);
            SuspectedType suspectedType = null;
            if (dto.getSuspectedType() != null && dto.getSuspectedType().getId() != null) {
                suspectedType = suspectedTypeRepo.getOne(dto.getSuspectedType().getId());
            }
            entity.setSuspectedType(suspectedType);

            EpidemiologicalFactors factors = null;
            if (dto.getEpidemiologicalFactors() != null && dto.getEpidemiologicalFactors().getId() != null) {
                factors = epidemiologicalFactorsRepository.getOne(dto.getEpidemiologicalFactors().getId());
            }
            entity.setEpidemiologicalFactors(factors);

            Set<Sample> samples = new HashSet<Sample>();
            if (dto.getSamples() != null && dto.getSamples().size() > 0) {
                for (SampleDto sDto : dto.getSamples()) {
                    Sample s = null;
                    if (sDto.getId() != null) {
                        s = sampleRepository.getOne(sDto.getId());
                        if (s == null) {
                            return null;
                        }
                    } else {
                        s = new Sample();
                    }
                    String code = sDto.getCode();
        			if(sDto.getIsGeneratorCode() != null && sDto.getIsGeneratorCode() == true && sDto.getSampleCollectOrg()!=null) {
        				code = sampleService.autoGeneratorCode(sDto.getSampleCollectOrg().getCode());
        			}
                    s.setName(sDto.getName());
                    s.setCode(code);
                    s.setSampleType(sDto.getSampleType());
                    s.setSampleDate(sDto.getSampleDate());
                    s.setShipDate(sDto.getShipDate());
                    s.setTestingDate(sDto.getTestingDate());
                    s.setResultDate(sDto.getResultDate());
                    s.setSampleTestType(sDto.getSampleTestType());
                    Sample parent = null;
                    if (sDto.getParent() != null) {
                        parent = sampleRepository.getOne(sDto.getParent().getId());
                        if (parent == null) {
                            return null;
                        }
                    }
                    s.setParent(parent);
                    s.setSampleStatus(sDto.getSampleStatus());

                    NCoVAdministrativeUnit au = null;
                    if (sDto.getSampleAdminUnit() != null) {
                        au = administrativeUnitRepository.getOne(sDto.getSampleAdminUnit().getId());
                        if (au == null) {
                            return null;
                        }
                    }
                    s.setSampleAdminUnit(au);

                    HealthOrganization hOrg = null;
                    if (sDto.getLabTest() != null) {
                        hOrg = healthOrganizationRepository.getOne(sDto.getLabTest().getId());
                        if (hOrg == null) {
                            return null;
                        }
                    }
                    s.setLabTest(hOrg);

                    HealthOrganization sampleCollectOrg = null;
                    if (sDto.getSampleCollectOrg() != null) {
                        sampleCollectOrg = healthOrganizationRepository.getOne(sDto.getSampleCollectOrg().getId());
                        if (sampleCollectOrg == null) {
                            return null;
                        }
                    }
                    s.setSampleCollectOrg(sampleCollectOrg);

                    // hàm lưu này chưa lưu chidren

                    s.setPerson(entity);

                    samples.add(s);
                }
            }

            if (entity.getSamples() == null) {
                entity.setSamples(samples);
            } else {
                entity.getSamples().clear();
                entity.getSamples().addAll(samples);
            }

            entity = personRepository.save(entity);
            if (entity != null) {
                return new SuspectedPersonDto(entity, false);
            }
        }
        return null;
    }

    @Override
    public SuspectedPersonDto getById(UUID id) {
        if (id != null) {
            SuspectedPerson entity = personRepository.getOne(id);
            if (entity != null) {
                return new SuspectedPersonDto(entity, false);
            }
        }
        return null;
    }

    @Override
    public Boolean deleteById(UUID id) {
        if (id != null) {
            SuspectedPerson entity = personRepository.getOne(id);
            if (entity != null) {
                personRepository.deleteById(id);
                return true;
            }
        }
        return false;
    }

    private static String seperateText(String errorText, String title, String details) {
        if (errorText != null && StringUtils.hasText(errorText)) {
            errorText += "--";
        }
        if (title != null && StringUtils.hasText(title)) {
            errorText += title;
        }
        if (details != null && StringUtils.hasText(details)) {
            errorText += " (" + details + ")";
        }
        return errorText;
    }
}
