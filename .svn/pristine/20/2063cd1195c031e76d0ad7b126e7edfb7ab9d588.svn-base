package com.globits.covid19.test.service.impl;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.globits.core.domain.AdministrativeUnit;
import com.globits.core.repository.AdministrativeUnitRepository;
import com.globits.core.utils.SecurityUtils;
import com.globits.covid19.test.domain.EpidemiologicalFactors;
import com.globits.covid19.test.domain.HealthOrganization;
import com.globits.covid19.test.domain.NCoVAdministrativeUnit;
import com.globits.covid19.test.domain.Sample;
import com.globits.covid19.test.domain.SuspectedLevel;
import com.globits.covid19.test.domain.SuspectedPerson;
import com.globits.covid19.test.domain.SuspectedType;
import com.globits.covid19.test.dto.HealthOrganizationDto;
import com.globits.covid19.test.dto.ImportSampleResultsDto;
import com.globits.covid19.test.dto.SampleDto;
import com.globits.covid19.test.dto.SampleSearchDto;
import com.globits.covid19.test.dto.SuspectedPersonDto;
import com.globits.covid19.test.dto.UserInfoDto;
import com.globits.covid19.test.repository.EpidemiologicalFactorsRepository;
import com.globits.covid19.test.repository.HealthOrganizationRepository;
import com.globits.covid19.test.repository.NCoVAdministrativeUnitRepository;
import com.globits.covid19.test.repository.SampleRepository;
import com.globits.covid19.test.repository.SuspectedLevelRepository;
import com.globits.covid19.test.repository.SuspectedPersonRepository;
import com.globits.covid19.test.repository.SuspectedTypeRepository;
import com.globits.covid19.test.service.SampleService;
import com.globits.covid19.test.service.UserOrganizationService;
import com.globits.covid19.test.utilities.Enums;
import com.globits.covid19.test.utilities.NCoVConstant;
import com.globits.security.domain.User;

@Transactional
@Service
public class SampleServiceImpl extends GenericServiceImpl<Sample, UUID> implements SampleService {

	@Autowired
	private EntityManager manager;

	@Autowired
	public SampleRepository sampleRepository;

	@Autowired
	public SuspectedPersonRepository personRepository;

	@Autowired
	public HealthOrganizationRepository healthOrganizationRepository;

	@Autowired
	public NCoVAdministrativeUnitRepository nCoVAdministrativeUnitRepository;

	@Autowired
	public SuspectedLevelRepository suspectedLevelRepository;

	@Autowired
	public SuspectedTypeRepository suspectedTypeRepository;

	@Autowired
	public EpidemiologicalFactorsRepository epidemiologicalFactorsRepository;

	@Autowired
	public UserOrganizationService userOrganizationService;

	@Override
	public Page<SampleDto> searchByDto(SampleSearchDto dto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User modifiedUser = null;
		LocalDateTime currentDate = LocalDateTime.now();
		String currentUserName = "Unknown User";
		if (authentication != null) {
			modifiedUser = (User) authentication.getPrincipal();
			currentUserName = modifiedUser.getUsername();
		}
		Boolean isAdmin = SecurityUtils.isUserInRole(modifiedUser, com.globits.core.Constants.ROLE_ADMIN);

		HealthOrganization org = healthOrganizationRepository.getOrgByUser(modifiedUser.getId());
		List<UUID> filterUserOrg = null;
		if (!isAdmin) {
			UserInfoDto userInfoDto = userOrganizationService.getAllInfoByUserLogin();
			if (userInfoDto.getUserOrganization() == null) {
				return null;
			}
			filterUserOrg = userInfoDto.getListChildHealthOrganizationId();
		}

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
		String orderBy = " ORDER BY eqap.createDate DESC  ";
		String sqlCount = "select count(eqap.id) from Sample eqap where (1=1) ";
		String sql = "select new com.globits.covid19.test.dto.SampleDto(eqap, false, false) from Sample eqap where (1=1) ";

		if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
			whereClause += " AND (eqap.name LIKE :text " + "OR eqap.code LIKE :text " + "OR eqap.sampleType LIKE :text "
					+ "OR eqap.sampleStatus LIKE :text OR eqap.person.name LIKE :text OR eqap.person.idNumber LIKE :text OR eqap.person.phoneNumber LIKE :text ) ";
		}

		// Lấy danh sách mẫu thường
		if (dto.getIsParent() != null && dto.getIsParent() == true) {
			whereClause += " AND ( eqap.parent IS NULL) ";
		}
		// lấy danh sách mẫu gộp
		if (dto.getIsChidren() != null && dto.getIsChidren() == true) {
			whereClause += " AND eqap.person IS NULL ";
		}
		if (dto.getIsChidren() != null && dto.getIsChidren() == false) {
			whereClause += " AND ( eqap.person IS NOT NULL ) ";
		}
		if (dto.getSampleResult() != null && dto.getSampleResult() != "") {
			whereClause += " AND ( eqap.sampleResult LIKE :sampleResult) ";
		}
		// Filter By StartTime
		if (dto.getFromDate() != null && dto.getToDate() == null) {
			whereClause += " AND  eqap.sampleDate > :fromDate  ";
		}
		if (dto.getFromDate() == null && dto.getToDate() != null) {
			whereClause += " AND  eqap.sampleDate < :toDate  ";
		}
        if (dto.getFromDate() != null && dto.getToDate() != null) {
            whereClause += " AND (eqap.sampleDate BETWEEN :fromDate AND :toDate)";
        }
        if (dto.getEpiFactor() != null && dto.getEpiFactor().getId() != null) {
        	 whereClause += " AND (eqap.person.epidemiologicalFactors.id =: epicFactorId)";
        }
        if (dto.getSusLevel() != null && dto.getSusLevel().getId() != null) {
       	 	whereClause += " AND (eqap.person.suspectedLevel.id =: susLevelId)";
       }
        if (dto.getIsUpdateSampleResult() != null && dto.getIsUpdateSampleResult() == false) {
       	 	whereClause += " AND (eqap.isUpdateSampleResult = null OR eqap.isUpdateSampleResult = false)";
       }
        if (dto.getIsUpdateSampleResult() != null && dto.getIsUpdateSampleResult() == true) {
       	 	whereClause += " AND (eqap.isUpdateSampleResult = true)";
       }
		if (isAdmin) {
			if (dto.getLabTest() != null && dto.getLabTest().getId() != null) {
				whereClause += " AND eqap.labTest.id =: labTestId";
			}

			if (dto.getCollectSampleOrg() != null && dto.getCollectSampleOrg().getId() != null) {
				whereClause += " AND eqap.sampleCollectOrg.id =: collectSampleOrgId";
			}
		} else {
			if (dto.getLabTest() != null && dto.getLabTest().getId() != null
					&& filterUserOrg.contains(dto.getLabTest().getId())) {
				whereClause += " AND eqap.labTest.id =: labTestId";
			} else if (dto.getCollectSampleOrg() != null && dto.getCollectSampleOrg().getId() != null
					&& filterUserOrg.contains(dto.getCollectSampleOrg().getId())) {
				whereClause += " AND eqap.collectSampleOrg.id =: collectSampleOrgId";
			} else {
				if (org != null)
					whereClause += " and (eqap.sampleCollectOrg.id IN :listOrgId or eqap.labTest.id IN :listOrgId)";
			}

		}

		sql += whereClause + orderBy;
		sqlCount += whereClause;
		Query q = manager.createQuery(sql, SampleDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
			q.setParameter("text", '%' + dto.getText().trim() + '%');
			qCount.setParameter("text", '%' + dto.getText().trim() + '%');
		}
		if (dto.getSampleResult() != null && dto.getSampleResult() != "") {
			q.setParameter("sampleResult", '%' + dto.getSampleResult() + '%');
			qCount.setParameter("sampleResult", '%' + dto.getSampleResult() + '%');
		}
		if (dto.getFromDate() != null && dto.getToDate() == null) {
			q.setParameter("fromDate", dto.getFromDate());
			qCount.setParameter("fromDate", dto.getFromDate());
		}
		if (dto.getFromDate() == null && dto.getToDate() != null) {
			q.setParameter("toDate", dto.getToDate());
			qCount.setParameter("toDate", dto.getToDate());
		}
		if (dto.getFromDate() != null && dto.getToDate() != null) {
            q.setParameter("fromDate", dto.getFromDate());
            q.setParameter("toDate", dto.getToDate());

            qCount.setParameter("fromDate", dto.getFromDate());
            qCount.setParameter("toDate", dto.getToDate());
        }
		 if (dto.getSusLevel() != null && dto.getSusLevel().getId() != null) {
			q.setParameter("susLevelId", dto.getSusLevel().getId());
			qCount.setParameter("susLevelId", dto.getSusLevel().getId());
		}
		 if (dto.getEpiFactor() != null && dto.getEpiFactor().getId() != null) {
				q.setParameter("epicFactorId", dto.getEpiFactor().getId());
				qCount.setParameter("epicFactorId", dto.getEpiFactor().getId());
		 }
//		 if (dto.getIsUpdateSampleResult() != null) {
//	       	 q.setParameter("isUpResult", dto.getIsUpdateSampleResult());
//	       	 qCount.setParameter("isUpResult", dto.getIsUpdateSampleResult());
//	      }
		if (isAdmin) {
			if (dto.getLabTest() != null && dto.getLabTest().getId() != null) {
				q.setParameter("labTestId", dto.getLabTest().getId());
				qCount.setParameter("labTestId", dto.getLabTest().getId());
			}

			if (dto.getCollectSampleOrg() != null && dto.getCollectSampleOrg().getId() != null) {
				q.setParameter("collectSampleOrgId", dto.getCollectSampleOrg().getId());
				qCount.setParameter("collectSampleOrgId", dto.getCollectSampleOrg().getId());
			}
		} else {
			if (dto.getLabTest() != null && dto.getLabTest().getId() != null
					&& filterUserOrg.contains(dto.getLabTest().getId())) {
				q.setParameter("labTestId", dto.getLabTest().getId());
				qCount.setParameter("labTestId", dto.getLabTest().getId());
			} else if (dto.getCollectSampleOrg() != null && dto.getCollectSampleOrg().getId() != null
					&& filterUserOrg.contains(dto.getLabTest().getId())) {
				q.setParameter("collectSampleOrgId", dto.getCollectSampleOrg().getId());
				qCount.setParameter("collectSampleOrgId", dto.getCollectSampleOrg().getId());
			} else {

				q.setParameter("listOrgId", filterUserOrg);
				qCount.setParameter("listOrgId", filterUserOrg);
			}

		}

		int startPosition = pageIndex * pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<SampleDto> entities = q.getResultList();
		long count = (long) qCount.getSingleResult();

		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<SampleDto> result = new PageImpl<SampleDto>(entities, pageable, count);

		return result;
	}

	@Override
	public SampleDto saveOrUpdate(SampleDto dto, UUID id) {
		if (dto != null) {
			Sample entity = null;
			if (id != null) {
				entity = sampleRepository.getOne(id);
			}
			if (entity == null) {
				entity = new Sample();
			}
			String code = dto.getCode();
			if(dto.getIsGeneratorCode() != null && dto.getIsGeneratorCode() == true && dto.getSampleCollectOrg()!=null) {
				code = autoGeneratorCode(dto.getSampleCollectOrg().getCode());
			}
			entity.setName(dto.getName());
			entity.setCode(code);
			entity.setSampleDate(dto.getSampleDate());
			entity.setShipDate(dto.getShipDate());
			entity.setTestingDate(dto.getTestingDate());
			entity.setResultDate(dto.getResultDate());
			entity.setSampleTestType(dto.getSampleTestType());
			Sample parent = null;
			if (dto.getParent() != null && dto.getParent().getId() != null) {
				parent = sampleRepository.getOne(dto.getParent().getId());
			}
			if (parent != null) {
				entity.setParent(parent);
			}
			entity.setSampleType(dto.getSampleType());
			SuspectedPerson person = null;
			if (dto.getPerson() != null && dto.getPerson().getId() != null) {
				person = personRepository.getOne(dto.getPerson().getId());
			} else {
				person = new SuspectedPerson();
			}
			if (dto.getPerson() != null) {
				person.setName(dto.getPerson().getName());
				person.setGiveName(dto.getPerson().getGiveName());
				person.setBirthDate(dto.getPerson().getBirthDate());
				person.setGender(dto.getPerson().getGender());
				person.setDescription(dto.getPerson().getDescription());
				person.setPhoneNumber(dto.getPerson().getPhoneNumber());
				person.setFeverStatus(dto.getPerson().getFeverStatus());
				person.setCoughStatus(dto.getPerson().getCoughStatus());
				person.setShortnessOfBreath(dto.getPerson().getShortnessOfBreath());
				person.setSoreThroat(dto.getPerson().getSoreThroat());
				person.setDetailAddress(dto.getPerson().getDetailAddress());
				person.setIdNumber(dto.getPerson().getIdNumber());
				person.setCareer(dto.getPerson().getCareer());
				person.setSamplingLocation(dto.getPerson().getSamplingLocation());
				person.setDetailEpidemiologicalFactors(dto.getPerson().getDetailEpidemiologicalFactors());
				person.setLastContactDate(dto.getPerson().getLastContactDate());
				person.setDateOfLastContact(dto.getPerson().getDateOfLastContact());
				person.setPneumoniaStatus(dto.getPerson().getPneumoniaStatus());
				SuspectedLevel level = null;
				if (dto.getPerson().getSuspectedLevel() != null
						&& dto.getPerson().getSuspectedLevel().getId() != null) {
					level = suspectedLevelRepository.getOne(dto.getPerson().getSuspectedLevel().getId());
				}
				person.setSuspectedLevel(level);

				SuspectedType suspectedType = null;
				if (dto.getPerson().getSuspectedType() != null && dto.getPerson().getSuspectedType().getId() != null) {
					suspectedType = suspectedTypeRepository.getOne(dto.getPerson().getSuspectedType().getId());
				}
				person.setSuspectedType(suspectedType);

				EpidemiologicalFactors epidemiologicalFactors = null;
				if (dto.getPerson().getEpidemiologicalFactors() != null
						&& dto.getPerson().getEpidemiologicalFactors().getId() != null) {
					epidemiologicalFactors = epidemiologicalFactorsRepository
							.getOne(dto.getPerson().getEpidemiologicalFactors().getId());
				}
				person.setEpidemiologicalFactors(epidemiologicalFactors);

				NCoVAdministrativeUnit wardOfResidence = null;
				if (dto.getPerson().getWardOfResidence() != null
						&& dto.getPerson().getWardOfResidence().getId() != null) {
					wardOfResidence = nCoVAdministrativeUnitRepository
							.findOne(dto.getPerson().getWardOfResidence().getId());
				}
				person.setWardOfResidence(wardOfResidence);

				NCoVAdministrativeUnit districtOfResidence = null;
				if (dto.getPerson().getDistrictOfResidence() != null
						&& dto.getPerson().getDistrictOfResidence().getId() != null) {
					districtOfResidence = nCoVAdministrativeUnitRepository
							.findOne(dto.getPerson().getDistrictOfResidence().getId());
				}
				person.setDistrictOfResidence(districtOfResidence);

				NCoVAdministrativeUnit provinceOfResidence = null;
				if (dto.getPerson().getProvinceOfResidence() != null
						&& dto.getPerson().getProvinceOfResidence().getId() != null) {
					provinceOfResidence = nCoVAdministrativeUnitRepository
							.findOne(dto.getPerson().getProvinceOfResidence().getId());
				}
				person.setProvinceOfResidence(provinceOfResidence);

				HealthOrganization isolationPlace = null;
				if (dto.getPerson().getIsolationPlace() != null
						&& dto.getPerson().getIsolationPlace().getId() != null) {
					isolationPlace = healthOrganizationRepository
							.getOneById(dto.getPerson().getIsolationPlace().getId());
				}
				person.setIsolationPlace(isolationPlace);
				person = personRepository.save(person);
				entity.setPerson(person);
			}

			entity.setSampleStatus(dto.getSampleStatus());
			entity.setSampleResult(dto.getSampleResult());
			HealthOrganization healthOrganization = null;
			if (dto.getLabTest() != null && dto.getLabTest().getId() != null) {
				healthOrganization = healthOrganizationRepository.getOne(dto.getLabTest().getId());
			}
			entity.setLabTest(healthOrganization);

			HealthOrganization sampleCollectOrg = null;
			if (dto.getSampleCollectOrg() != null && dto.getSampleCollectOrg().getId() != null) {
				sampleCollectOrg = healthOrganizationRepository.getOne(dto.getSampleCollectOrg().getId());
			}
			entity.setSampleCollectOrg(sampleCollectOrg);

			NCoVAdministrativeUnit administrativeUnit = null;
			if (dto.getSampleAdminUnit() != null && dto.getSampleAdminUnit().getId() != null) {
				administrativeUnit = nCoVAdministrativeUnitRepository.getOne(dto.getSampleAdminUnit().getId());
			}
			entity.setSampleAdminUnit(administrativeUnit);
			entity = sampleRepository.save(entity);
			// Lưu lại list danh sách con
			Set<Sample> sampleBottles = new HashSet<Sample>();
			if (dto.getChidren() != null && dto.getChidren().size() > 0) {
				int i = 0;
//				int index = 1;
				for (SampleDto srbDto : dto.getChidren()) {
					Sample sampleDetail = null;
					if (srbDto.getId() != null) {
						sampleDetail = sampleRepository.getOne(srbDto.getId());
					} else {
						sampleDetail = new Sample();
					}
					sampleDetail.setParent(entity);
					if(dto.getIsUpdateResult() != null && dto.getIsUpdateResult() == true) {
//						String codeSampleChild = dto.getCode() + "-" + Integer.toString(index);
//						sampleDetail.setCode(codeSampleChild);
						sampleDetail.setSampleResult(dto.getSampleResult());
						sampleDetail.setTestingDate(dto.getTestingDate());
						sampleDetail.setResultDate(dto.getResultDate());
						sampleDetail.setLabTest(healthOrganization);
						sampleDetail.setSampleStatus(dto.getSampleStatus());
					}
					sampleDetail = sampleRepository.save(sampleDetail);
					sampleBottles.add(sampleDetail);
//					index++;
				}
			}
			if (entity.getChidren() == null) {
				entity.setChidren(sampleBottles);
			} else {
				entity.getChidren().clear();
				entity.getChidren().addAll(sampleBottles);
			}

			if (entity != null) {
				return new SampleDto(entity);
			}
		}
		return null;

	}

	@Override
	public SampleDto getById(UUID id) {
		if (id != null) {
			Sample entity = sampleRepository.getOne(id);
			if (entity != null) {
				return new SampleDto(entity);
			}
		}
		return null;
	}

	@Override
	public Boolean deleteById(UUID id) {
		if (id != null) {
			Sample entity = sampleRepository.getOne(id);
			if (entity != null) {
				sampleRepository.deleteById(id);
				return true;
			}
		}
		return false;
	}

	@Override
	public SampleDto getByCode(String code) {
		if (StringUtils.hasText(code)) {
			List<Sample> entities = sampleRepository.getByCode(code);
			if (entities != null && !entities.isEmpty()) {
				return new SampleDto(entities.get(0));
			}
		}
		return null;
	}

	/*
	 * Created by DungHQ
	 */
	@Override
	public Page<SampleDto> searchSampleAndUser(SampleSearchDto dto) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User modifiedUser = null;
		LocalDateTime currentDate = LocalDateTime.now();
		String currentUserName = "Unknown User";
		if (authentication != null) {
			modifiedUser = (User) authentication.getPrincipal();
			currentUserName = modifiedUser.getUsername();
		}
		Boolean isAdmin = SecurityUtils.isUserInRole(modifiedUser, com.globits.core.Constants.ROLE_ADMIN);

		HealthOrganization org = healthOrganizationRepository.getOrgByUser(modifiedUser.getId());
		List<UUID> filterUserOrg = null;
		if (!isAdmin) {
			UserInfoDto userInfoDto = userOrganizationService.getAllInfoByUserLogin();
			if (userInfoDto.getUserOrganization() == null) {
				return null;
			}
			filterUserOrg = userInfoDto.getListChildHealthOrganizationId();
		}

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
		String orderBy = " ORDER BY eqap.createDate DESC  ";
		String sqlCount = "select count(eqap.id) from Sample eqap where (1=1) ";
		String sql = "select new com.globits.covid19.test.dto.SampleDto(eqap, false) from Sample eqap where (1=1) ";

		if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
			whereClause += " AND (eqap.name LIKE :text " + "OR eqap.code LIKE :text " + "OR eqap.sampleType LIKE :text "
					+ "OR eqap.sampleStatus LIKE :text OR eqap.person.name LIKE :text ) ";
		}

		if (dto.getIsParent() != null && dto.getIsParent() == true) {
			whereClause += " AND ( eqap.parent IS NULL AND eqap.person IS NOT NULL ) ";
		}
		if (isAdmin) {
			if (dto.getLabTest() != null && dto.getLabTest().getId() != null) {
				whereClause += " AND eqap.labTest.id =: labTestId";
			}

			if (dto.getCollectSampleOrg() != null && dto.getCollectSampleOrg().getId() != null) {
				whereClause += " AND eqap.collectSampleOrg.id =: collectSampleOrgId";
			}
		} else {
			if (dto.getLabTest() != null && dto.getLabTest().getId() != null
					&& filterUserOrg.contains(dto.getLabTest().getId())) {
				whereClause += " AND eqap.labTest.id =: labTestId";
			} else if (dto.getCollectSampleOrg() != null && dto.getCollectSampleOrg().getId() != null
					&& filterUserOrg.contains(dto.getLabTest().getId())) {
				whereClause += " AND eqap.collectSampleOrg.id =: collectSampleOrgId";
			} else {
				if (org != null)
					whereClause += " and (eqap.sampleCollectOrg.id IN :listOrgId or eqap.labTest.id IN :listOrgId)";
			}

		}

		sql += whereClause + orderBy;
		sqlCount += whereClause;
		Query q = manager.createQuery(sql, SampleDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
			q.setParameter("text", '%' + dto.getText().trim() + '%');
			qCount.setParameter("text", '%' + dto.getText().trim() + '%');
		}
		if (isAdmin) {
			if (dto.getLabTest() != null && dto.getLabTest().getId() != null) {
				q.setParameter("labTestId", dto.getLabTest().getId());
				qCount.setParameter("labTestId", dto.getLabTest().getId());
			}

			if (dto.getCollectSampleOrg() != null && dto.getCollectSampleOrg().getId() != null) {
				q.setParameter("collectSampleOrgId", dto.getLabTest().getId());
				qCount.setParameter("collectSampleOrgId", dto.getLabTest().getId());
			}
		} else {
			if (dto.getLabTest() != null && dto.getLabTest().getId() != null
					&& filterUserOrg.contains(dto.getLabTest().getId())) {
				q.setParameter("labTestId", dto.getLabTest().getId());
				qCount.setParameter("labTestId", dto.getLabTest().getId());
			} else if (dto.getCollectSampleOrg() != null && dto.getCollectSampleOrg().getId() != null
					&& filterUserOrg.contains(dto.getLabTest().getId())) {
				q.setParameter("collectSampleOrgId", dto.getLabTest().getId());
				qCount.setParameter("collectSampleOrgId", dto.getLabTest().getId());
			} else {

				q.setParameter("listOrgId", filterUserOrg);
				qCount.setParameter("listOrgId", filterUserOrg);
			}

		}

		int startPosition = pageIndex * pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<SampleDto> entities = q.getResultList();
		long count = (long) qCount.getSingleResult();

		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<SampleDto> result = new PageImpl<SampleDto>(entities, pageable, count);

		return result;
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

	@Override
	public ImportSampleResultsDto importSample(List<SampleDto> listSampleDto) {
		ImportSampleResultsDto result = new ImportSampleResultsDto();
		List<SampleDto> listSaveData = new ArrayList<SampleDto>();
		String text = "";
		if (listSampleDto != null && listSampleDto.size() > 0) {

			// check mã trùng trong hệ thống rồi thì tạm thời không cho import
			result = checkDuplicateCode(listSampleDto);
			if (result.isDuplicate()) {
				return result;
			}

			for (SampleDto sampleDto : listSampleDto) {
				String error = "";
				SampleDto dto = null;
				// nếu có bản ghi sample trong dữ liệu thì cập nhật lại bản ghi đấy theo file
				// excel
				/*
				 * if (sampleDto.getCode() != null) { List<Sample> listDto =
				 * sampleRepository.getByCode(sampleDto.getCode()); if (listDto != null &&
				 * listDto.size() > 0) { dto = new SampleDto(listDto.get(0), true); } else { dto
				 * = new SampleDto(); } } else { dto = new SampleDto(); }
				 */
				dto = new SampleDto();

				if (sampleDto.getCode() != null) {
					dto.setCode(sampleDto.getCode());
				}

				SuspectedPerson suspectedPerson = null;
				if(sampleDto.getSampleResult() != null) {
					dto.setSampleStatus(Enums.SampleStatusEnum.Accepted.getName());
				}else {
					dto.setSampleStatus(Enums.SampleStatusEnum.Pending.getName());
				}
				dto.setSampleResult(sampleDto.getSampleResult());
				dto.setSampleType(Enums.SampleTypeEnum.sampleNormal.getType());
				dto.setSampleDate(sampleDto.getSampleDate());
				dto.setShipDate(sampleDto.getShipDate());
				dto.setSampleTestType(Enums.SampleTestTypeEnum.Internal.getType());// Mặc định mẫu nội bộ
				// Nơi xét nghiệm
				List<HealthOrganizationDto> listHealthOrganizationDto = healthOrganizationRepository
						.getByNameOrCodeLabTest(sampleDto.getImportLabTest());
				if (listHealthOrganizationDto != null && listHealthOrganizationDto.size() > 0) {
					dto.setLabTest(listHealthOrganizationDto.get(0));
				} else {
					/*
					 * if (error == null || !StringUtils.hasLength(error)) {
					 * result.getListData().add(sampleDto); }
					 */
					error = seperateText(error, NCoVConstant.ERROR_IMPORT_MISSING_LAB_TEST,
							sampleDto.getImportLabTest());
				}
				//Nơi lấy mẫu
				listHealthOrganizationDto = healthOrganizationRepository
						.getByNameOrCodeCollectSample(sampleDto.getImportSampleCollectOrg());
				if (listHealthOrganizationDto != null && listHealthOrganizationDto.size() > 0) {
					dto.setSampleCollectOrg(listHealthOrganizationDto.get(0));
				} else {
					/*
					 * if (error == null || !StringUtils.hasLength(error)) {
					 * result.getListData().add(sampleDto); }
					 */
					error = seperateText(error, NCoVConstant.ERROR_IMPORT_MISSING_COLLECT_ORG,
							sampleDto.getImportSampleCollectOrg());
				}

				List<SuspectedPerson> listPerson = personRepository.getByPersonByName(sampleDto.getPersonName(),
						sampleDto.getPersonBirthDate());
				if (listPerson != null && listPerson.size() > 0) {
					suspectedPerson = listPerson.get(0);
				}
				if (suspectedPerson == null) {
					suspectedPerson = new SuspectedPerson();
					suspectedPerson.setId(null);
				}
				suspectedPerson.setGender(sampleDto.getPersonGender());//Giới tính
				suspectedPerson.setBirthDate(sampleDto.getPersonBirthDate());//Năm sinh
				suspectedPerson.setName(sampleDto.getPersonName());//Tên
				suspectedPerson.setPhoneNumber(sampleDto.getPersonPhoneNumber());//Số điện thoại
				suspectedPerson.setLastContactDate(sampleDto.getLastContactDate());//Ngày tiếp xúc gần nhất
				suspectedPerson.setDetailEpidemiologicalFactors(sampleDto.getDetailEpidemiologicalFactors());//Chi tiết dịch tễ
				suspectedPerson.setSamplingLocation(sampleDto.getSamplingLocation());//Địa điểm lẫy mẫu
				// Yếu tố nguy cơ
				List<EpidemiologicalFactors> epidemiologicalFactors = null;
				if (sampleDto.getImportEpidemiologicalFactors() != null
						&& StringUtils.hasLength(sampleDto.getImportEpidemiologicalFactors())) {
					epidemiologicalFactors = epidemiologicalFactorsRepository
							.getByNameOrCode(sampleDto.getImportEpidemiologicalFactors());
				}
				if (epidemiologicalFactors != null && epidemiologicalFactors.size() > 0) {
					suspectedPerson.setEpidemiologicalFactors(epidemiologicalFactors.get(0));
				} else {
					error = seperateText(error, NCoVConstant.ERROR_IMPORT_MISSING_PERSON_EPIDEMIOLOGICAL_FACORS,
							sampleDto.getImportEpidemiologicalFactors());
				}
				// Cấp độ nguy nhiễm
				List<SuspectedLevel> suspectedLevel = null;
				if (sampleDto.getImportSuspectedLevel() != null
						&& StringUtils.hasLength(sampleDto.getImportSuspectedLevel())) {
					suspectedLevel = suspectedLevelRepository.getByNameOrCode(sampleDto.getImportSuspectedLevel());
				}
				if (suspectedLevel != null && suspectedLevel.size() > 0) {
					suspectedPerson.setSuspectedLevel(suspectedLevel.get(0));
				} else {
					error = seperateText(error, NCoVConstant.ERROR_IMPORT_MISSING_PERSON_SUSPECTED_LEVEL,
							sampleDto.getImportSuspectedLevel());
				}
				// Địa chỉ chi tiết
				if (sampleDto.getDetailAddress() != null) {
					suspectedPerson.setDetailAddress(sampleDto.getDetailAddress());
				}
				// Đơn vị hành chính
				if (sampleDto.getProvinceImport() != null && StringUtils.hasText(sampleDto.getProvinceImport())) {

					List<NCoVAdministrativeUnit> provinceOfResidence = null;
					provinceOfResidence = nCoVAdministrativeUnitRepository
							.getProvinceByNameOrCode("%" + sampleDto.getProvinceImport() + "%");
					if (provinceOfResidence != null && provinceOfResidence.size() > 0
							&& provinceOfResidence.get(0).getId() != null) {
						suspectedPerson.setProvinceOfResidence(provinceOfResidence.get(0));

						List<NCoVAdministrativeUnit> districtOfResidence = null;
						districtOfResidence = nCoVAdministrativeUnitRepository.getByNameOrCodeAndParentId(
								"%" + sampleDto.getDistrictImport() + "%", provinceOfResidence.get(0).getId());

						if (districtOfResidence != null && districtOfResidence.size() > 0
								&& districtOfResidence.get(0).getId() != null) {
							suspectedPerson.setDistrictOfResidence(districtOfResidence.get(0));

							List<NCoVAdministrativeUnit> wardOfResidence = null;
							wardOfResidence = nCoVAdministrativeUnitRepository.getByNameOrCodeAndParentId(
									"%" + sampleDto.getWardImport() + "%", districtOfResidence.get(0).getId());
							if (wardOfResidence != null && wardOfResidence.size() > 0
									&& wardOfResidence.get(0).getId() != null) {
								suspectedPerson.setWardOfResidence(wardOfResidence.get(0));
							}
						}
					}
				}
				dto.setPerson(new SuspectedPersonDto(suspectedPerson, false));
				// check có lỗi hay không
				if (error != null && StringUtils.hasLength(error)) {
					text += String.format(NCoVConstant.ERROR_IMPORT_ROW_INDEX, sampleDto.getIndexImport())
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
	public ImportSampleResultsDto importSampleGroup(List<SampleDto> listSampleDto, List<SampleDto> listGroupSample) {//Bắc Ninh
		//Kiểm tra 
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User modifiedUser = null;
		String currentUserName = "Unknown User";
		if (authentication != null) {
			modifiedUser = (User) authentication.getPrincipal();
			currentUserName = modifiedUser.getUsername();
		}
		Boolean isAdmin = SecurityUtils.isUserInRole(modifiedUser, com.globits.core.Constants.ROLE_ADMIN);

		HealthOrganization org = healthOrganizationRepository.getOrgByUser(modifiedUser.getId());
		
		ImportSampleResultsDto result = new ImportSampleResultsDto();
		List<SampleDto> listSaveData = new ArrayList<SampleDto>();
		String text = "";
		if (listSampleDto != null && listSampleDto.size() > 0) {

			// check mã trùng trong hệ thống rồi thì tạm thời không cho import
			result = checkDuplicateCode(listGroupSample);
			if (result.isDuplicate()) {
				return result;
			}
			
			for (SampleDto sampleDto : listSampleDto) {
				String error = "";
				SampleDto dto = null;
				dto = new SampleDto();

				if (sampleDto.getCode() != null) {
					dto.setCode(sampleDto.getCode());
				}

				SuspectedPerson suspectedPerson = null;
				if(sampleDto.getSampleResult() != null) {
					dto.setSampleStatus(Enums.SampleStatusEnum.Accepted.getName());
				}else {
					dto.setSampleStatus(Enums.SampleStatusEnum.Pending.getName());
				}
				dto.setSampleResult(sampleDto.getSampleResult());
				dto.setSampleType(Enums.SampleTypeEnum.sampleNormal.getType());
				dto.setSampleDate(sampleDto.getSampleDate());
				dto.setShipDate(sampleDto.getShipDate());
				dto.setSampleTestType(Enums.SampleTestTypeEnum.Internal.getType());// Mặc định mẫu nội bộ
				
				if( isAdmin) {
					// Nơi xét nghiệm
					List<HealthOrganizationDto> listHealthOrganizationDto = healthOrganizationRepository
							.getByNameOrCodeLabTest(sampleDto.getImportLabTest());
					if (listHealthOrganizationDto != null && listHealthOrganizationDto.size() > 0) {
						dto.setLabTest(listHealthOrganizationDto.get(0));
					}
					
					//Nơi lấy mẫu
					listHealthOrganizationDto = healthOrganizationRepository
							.getByNameOrCodeCollectSample(sampleDto.getImportSampleCollectOrg());
					if (listHealthOrganizationDto != null && listHealthOrganizationDto.size() > 0) {
						dto.setSampleCollectOrg(listHealthOrganizationDto.get(0));
					}
				}
				// Nơi xét nghiệm
				if (org != null && (org.getOrgType() == Enums.OrganizationTypeEnum.Both.getType() || org.getOrgType() == Enums.OrganizationTypeEnum.LabTest.getType() ) ) {
					dto.setLabTest(new HealthOrganizationDto(org,false, 1));
				} 
				//Nơi lấy mẫu
				if (org != null && (org.getOrgType() == Enums.OrganizationTypeEnum.Both.getType() || org.getOrgType() == Enums.OrganizationTypeEnum.CollectSample.getType() ) ) {
					dto.setSampleCollectOrg(new HealthOrganizationDto(org,false, 1));
				} 

				List<SuspectedPerson> listPerson = personRepository.getByPersonByName(sampleDto.getPersonName(),
						sampleDto.getPersonBirthDate());
				if (listPerson != null && listPerson.size() > 0) {
					suspectedPerson = listPerson.get(0);
				}
				if (suspectedPerson == null) {
					suspectedPerson = new SuspectedPerson();
					suspectedPerson.setId(null);
				}
				suspectedPerson.setGender(sampleDto.getPersonGender());//Giới tính
				suspectedPerson.setBirthDate(sampleDto.getPersonBirthDate());//Năm sinh
				suspectedPerson.setName(sampleDto.getPersonName());//Tên
				suspectedPerson.setPhoneNumber(sampleDto.getPersonPhoneNumber());//Số điện thoại
				suspectedPerson.setLastContactDate(sampleDto.getLastContactDate());//Ngày tiếp xúc gần nhất
				suspectedPerson.setDetailEpidemiologicalFactors(sampleDto.getDetailEpidemiologicalFactors());//Chi tiết dịch tễ
				suspectedPerson.setSamplingLocation(sampleDto.getSamplingLocation());//Địa điểm lẫy mẫu
				// Yếu tố nguy cơ
				List<EpidemiologicalFactors> epidemiologicalFactors = null;
				if (sampleDto.getImportEpidemiologicalFactors() != null
						&& StringUtils.hasLength(sampleDto.getImportEpidemiologicalFactors())) {
					epidemiologicalFactors = epidemiologicalFactorsRepository
							.getByNameOrCode(sampleDto.getImportEpidemiologicalFactors());
				}
				if (epidemiologicalFactors != null && epidemiologicalFactors.size() > 0) {
					suspectedPerson.setEpidemiologicalFactors(epidemiologicalFactors.get(0));
				} 
				// Cấp độ nguy nhiễm
				List<SuspectedLevel> suspectedLevel = null;
				if (sampleDto.getImportSuspectedLevel() != null
						&& StringUtils.hasLength(sampleDto.getImportSuspectedLevel())) {
					suspectedLevel = suspectedLevelRepository.getByNameOrCode(sampleDto.getImportSuspectedLevel());
				}
				if (suspectedLevel != null && suspectedLevel.size() > 0) {
					suspectedPerson.setSuspectedLevel(suspectedLevel.get(0));
				} 
				// Địa chỉ chi tiết
				if (sampleDto.getDetailAddress() != null) {
					suspectedPerson.setDetailAddress(sampleDto.getDetailAddress());
				}
				// Đơn vị hành chính
				if (sampleDto.getProvinceImport() != null && StringUtils.hasText(sampleDto.getProvinceImport())) {

					List<NCoVAdministrativeUnit> provinceOfResidence = null;
					provinceOfResidence = nCoVAdministrativeUnitRepository
							.getProvinceByNameOrCode("%" + sampleDto.getProvinceImport() + "%");
					if (provinceOfResidence != null && provinceOfResidence.size() > 0
							&& provinceOfResidence.get(0).getId() != null) {
						suspectedPerson.setProvinceOfResidence(provinceOfResidence.get(0));

						List<NCoVAdministrativeUnit> districtOfResidence = null;
						districtOfResidence = nCoVAdministrativeUnitRepository.getByNameOrCodeAndParentId(
								"%" + sampleDto.getDistrictImport() + "%", provinceOfResidence.get(0).getId());

						if (districtOfResidence != null && districtOfResidence.size() > 0
								&& districtOfResidence.get(0).getId() != null) {
							suspectedPerson.setDistrictOfResidence(districtOfResidence.get(0));

							List<NCoVAdministrativeUnit> wardOfResidence = null;
							wardOfResidence = nCoVAdministrativeUnitRepository.getByNameOrCodeAndParentId(
									"%" + sampleDto.getWardImport() + "%", districtOfResidence.get(0).getId());
							if (wardOfResidence != null && wardOfResidence.size() > 0
									&& wardOfResidence.get(0).getId() != null) {
								suspectedPerson.setWardOfResidence(wardOfResidence.get(0));
							}
						}
					}
				}
				dto.setPerson(new SuspectedPersonDto(suspectedPerson, false));
				// check có lỗi hay không
				if (error != null && StringUtils.hasLength(error)) {
					text += String.format(NCoVConstant.ERROR_IMPORT_ROW_INDEX, sampleDto.getIndexImport())
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
	public ImportSampleResultsDto importSampleGroupData(List<SampleDto> listSampleDto) {//Bắc Ninh
		//Kiểm tra 
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User modifiedUser = null;
		String currentUserName = "Unknown User";
		if (authentication != null) {
			modifiedUser = (User) authentication.getPrincipal();
			currentUserName = modifiedUser.getUsername();
		}
		Boolean isAdmin = SecurityUtils.isUserInRole(modifiedUser, com.globits.core.Constants.ROLE_ADMIN);

		HealthOrganization org = healthOrganizationRepository.getOrgByUser(modifiedUser.getId());
		
		ImportSampleResultsDto result = new ImportSampleResultsDto();
		List<SampleDto> listSaveData = new ArrayList<SampleDto>();
		String text = "";
		if (listSampleDto != null && listSampleDto.size() > 0) {

			// check mã trùng trong hệ thống rồi thì tạm thời không cho import
			result = checkDuplicateCode(listSampleDto);
			if (result.isDuplicate()) {
				return result;
			}

			for (SampleDto sampleDto : listSampleDto) {
				String error = "";
				SampleDto dto = null;
				
				dto = new SampleDto();

				if (sampleDto.getCode() != null) {
					dto.setCode(sampleDto.getCode());
				}

				SuspectedPerson suspectedPerson = null;
				if(sampleDto.getSampleResult() != null) {
					dto.setSampleStatus(Enums.SampleStatusEnum.Accepted.getName());
				}else {
					dto.setSampleStatus(Enums.SampleStatusEnum.Pending.getName());
				}
				dto.setSampleResult(sampleDto.getSampleResult());
				dto.setSampleType(Enums.SampleTypeEnum.sampleGroup.getType());//Là mẫu gộp
				dto.setSampleDate(sampleDto.getSampleDate());
				dto.setShipDate(sampleDto.getShipDate());
				dto.setSampleTestType(Enums.SampleTestTypeEnum.Internal.getType());// Mặc định mẫu nội bộ
				// Nơi xét nghiệm
				
				if (org != null && (org.getOrgType() == Enums.OrganizationTypeEnum.Both.getType() || org.getOrgType() == Enums.OrganizationTypeEnum.LabTest.getType() ) ) {
					dto.setLabTest(new HealthOrganizationDto(org,false, 1));
				} 
				//Nơi lấy mẫu
				if (org != null && (org.getOrgType() == Enums.OrganizationTypeEnum.Both.getType() || org.getOrgType() == Enums.OrganizationTypeEnum.CollectSample.getType() ) ) {
					dto.setSampleCollectOrg(new HealthOrganizationDto(org,false, 1));
				} 
				 List<SampleDto> listChidren = sampleRepository.getListByCode("%" + sampleDto.getCode() + ".%");
				if(listChidren != null && listChidren.size() > 0) {
					dto.setChidren(listChidren);
				}
				// check có lỗi hay không
				if (error != null && StringUtils.hasLength(error)) {
					text += String.format(NCoVConstant.ERROR_IMPORT_ROW_INDEX, sampleDto.getIndexImport())
							+ String.format(NCoVConstant.ERROR_IMPORT_NOT_FOUND, error) + System.lineSeparator()
							+ System.lineSeparator();
				} else {
					listSaveData.add(dto);
				}
				
			}
			if (text != null && StringUtils.hasLength(text)) {
				result.setText(text);
			} else {
				result.setSuccess(true);
				result.setListSampleGroup(listSaveData);
			}
		}
		return result;
	}
	
	private ImportSampleResultsDto checkDuplicateCode(List<SampleDto> listSample) {
		ImportSampleResultsDto result = new ImportSampleResultsDto();
		result.setDuplicate(false);
		List<String> listCode = new ArrayList<String>();
		if (listSample != null && listSample.size() > 0) {
			for (SampleDto sampleDto : listSample) {
				listCode.add(sampleDto.getCode());
			}
			List<String> listCodeDuplicate = sampleRepository.findCodeInListCode(listCode);
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
	public Boolean checkDuplicateCode(UUID id, String code) {
		if (code != null && StringUtils.hasText(code)) {
			List<Sample> list = sampleRepository.getByCode(code);
			if (list != null && list.size() > 0) {
				if (id != null && list.get(0).getId().equals(id)) {
					return false;
				}
				return true;
			}
			return false;
		}
		return null;
	}

	@Override
	public ImportSampleResultsDto saveListData(List<SampleDto> listData) {
		ImportSampleResultsDto result = new ImportSampleResultsDto();
		result.setSuccess(true);
		if (listData != null && listData.size() > 0) {
			for (SampleDto sampleDto : listData) {
				this.saveOrUpdate(sampleDto, null);
			}
		}
		return result;
	}

	@Override
	public ByteArrayResource sampleToExcel(List<SampleDto> list) throws Exception {
		String[] COLUMNs = { "No.", "Mã mẫu", "Ngày lấy mẫu", "Ngày gửi mẫu", "Ngày làm xét nghiệm",
				"Ngày tiếp xúc cuối cùng", "Ngày có kết quả", "Kết quả mẫu", "Đơn vị xét nghiệm", "Đơn vị lấy mẫu",
				"Tên", "Năm sinh", "Giới tính", "Cấp độ nghi nhiễm", "Yếu tố dịch tễ" };

//		try () {
		if(list != null && list.size() > 0) {
			Workbook workbook = new XSSFWorkbook(); 
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			CreationHelper createHelper = workbook.getCreationHelper();
			Sheet sheet = workbook.createSheet("Mẫu xét nghiệm thường");
			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.BLUE.getIndex());
			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);
			headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
			// Row for header
			Row headerRow = sheet.createRow(0);
			// Header
			for (int col = 0; col < COLUMNs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(COLUMNs[col]);
				cell.setCellStyle(headerCellStyle);
			}
 			// CellStyle for Date
			CellStyle dateCellStyle = workbook.createCellStyle();
			dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
			dateCellStyle.setAlignment(HorizontalAlignment.CENTER);
			// CellStyle for row
			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setAlignment(HorizontalAlignment.CENTER);
			
			int rowIdx = 1;
			long stt = 0;
			for (SampleDto sample : list) {
				Row row = sheet.createRow(rowIdx++);
				stt++;
				row.createCell(0).setCellValue(stt);
				// Mã mẫu
				Cell code = row.createCell(1);
				code.setCellValue(sample.getCode()); code.setCellStyle(cellStyle);
				// Ngày lấy mẫu
				if(sample.getSampleDate() != null) {
					Cell sampleDate = row.createCell(2);
					sampleDate.setCellValue(sample.getSampleDate());
					sampleDate.setCellStyle(dateCellStyle);
				}
				
				// Ngày gửi mẫu
				if(sample.getShipDate() != null) {
					Cell shipDate = row.createCell(3);
					shipDate.setCellValue(sample.getShipDate());
					shipDate.setCellStyle(dateCellStyle);
				}
				
				// Ngày làm xét nghiệm
				if(sample.getTestingDate() != null) {
					Cell testingDate = row.createCell(4);
					testingDate.setCellValue(sample.getTestingDate());
					testingDate.setCellStyle(dateCellStyle);
				}
				
			
				
				// Ngày có kết quả
				if(sample.getResultDate() != null) {
					Cell resultDate = row.createCell(6);
					resultDate.setCellValue(sample.getResultDate());
					resultDate.setCellStyle(dateCellStyle);
				}
				
				// Kết quả mẫu
				String resultSample = "";
				if(sample.getSampleResult() != null) {
					if(sample.getSampleResult().equals(Enums.SampleResultEnum.Checking.getName())) {
						resultSample = "Dương tính chờ xác nhận";
					}else if(sample.getSampleResult().equals(Enums.SampleResultEnum.Negative.getName())) {
						resultSample = "Âm tính";
					}else if(sample.getSampleResult().equals(Enums.SampleResultEnum.Positive.getName())) {
						resultSample = "Dương tính";
					}
				}
				Cell sampleResult = row.createCell(7);
				sampleResult.setCellValue(resultSample); 
				sampleResult.setCellStyle(cellStyle);
				// Đơn vị xét nghiệm
				if(sample.getLabTest() != null && sample.getLabTest().getName() != null) {
					Cell labTest = row.createCell(8);
					labTest.setCellValue(sample.getLabTest().getName()); 
					labTest.setCellStyle(cellStyle);
				}
				// Đơn vị lấy mẫu
				if(sample.getSampleCollectOrg() != null && sample.getSampleCollectOrg().getName() != null) {
					Cell sampleCollectOrgName = row.createCell(9);
					sampleCollectOrgName.setCellValue(sample.getSampleCollectOrg().getName()); 
					sampleCollectOrgName.setCellStyle(cellStyle);
					
				}
				if(sample.getPerson() != null) {
					// Ngày tiếp xúc cuối cùng
					if(sample.getPerson().getLastContactDate() != null) {
						Cell lastContactDate = row.createCell(5);
						lastContactDate.setCellValue(sample.getPerson().getLastContactDate());
						lastContactDate.setCellStyle(dateCellStyle);
					}
					// Tên
					Cell namePerson = row.createCell(10);
					namePerson.setCellValue(sample.getPerson().getName()); 
					namePerson.setCellStyle(cellStyle);
					 // Năm sinh
					if(sample.getPerson().getBirthDate() != null) {
						Cell birthDate = row.createCell(11);
						DateFormat dateFormat = new SimpleDateFormat("yyyy");  
						String strDate = dateFormat.format(sample.getPerson().getBirthDate());  
						birthDate.setCellValue(strDate); 
						birthDate.setCellStyle(cellStyle);
					}
					// Giới tính
					String genderPerson = "";
					if(sample.getPerson().getGender() != null) {
						if(sample.getPerson().getGender().equals("M")) {
							genderPerson = "Nam";
						}
						else if(sample.getPerson().getGender().equals("F")) {
							genderPerson = "Nữ";
						}
						else if(sample.getPerson().getGender().equals("U")){
							genderPerson = "Khác";
						}
					}
					
					Cell gender = row.createCell(12);
					gender.setCellValue(genderPerson); 
					gender.setCellStyle(cellStyle);

					String suspect = "";
					String epidemiological = "";
					if (sample != null && sample.getPerson() != null && sample.getPerson().getSuspectedLevel() != null
							&& sample.getPerson().getSuspectedLevel().getName() != null) {
						suspect=sample.getPerson().getSuspectedLevel().getName();
					}
					if (sample != null && sample.getPerson() != null && sample.getPerson().getEpidemiologicalFactors() != null
							&& sample.getPerson().getEpidemiologicalFactors().getName() != null) {
						epidemiological=sample.getPerson().getEpidemiologicalFactors().getName();
					}
					// Cấp độ nghi nhiễm
					Cell suspectCell = row.createCell(13);
					suspectCell.setCellValue(suspect); 
					suspectCell.setCellStyle(cellStyle);
					// Yếu tố dịch tễ
					Cell epidemiologicalCell = row.createCell(14);
					epidemiologicalCell.setCellValue(epidemiological); 
					epidemiologicalCell.setCellStyle(cellStyle);
				}																														
			}
			for (int columnIndex = 0; columnIndex < COLUMNs.length; columnIndex++) {
				sheet.autoSizeColumn(columnIndex);
			}
			ByteArrayOutputStream excelFile = new ByteArrayOutputStream();
			workbook.write(excelFile);
			workbook.close();
			return new ByteArrayResource(excelFile.toByteArray());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		}
		return null;
	}

	@Override
	public SampleDto saveOrUpdateListPerson(SampleDto dto, UUID id) {
		if (dto != null) {
			// Lưu Sample cha
			Sample entity = null;
			if (id != null) {
				entity = sampleRepository.getOne(id);
			}
			if (entity == null) {
				entity = new Sample();
			}
			String codeSampleParent = dto.getCode();
			if(dto.getIsGeneratorCode() != null && dto.getIsGeneratorCode() == true && dto.getSampleCollectOrg()!=null) {
				codeSampleParent = autoGeneratorCode(dto.getSampleCollectOrg().getCode());
			}
			entity.setName(dto.getName());
			entity.setCode(codeSampleParent);
			entity.setSampleDate(dto.getSampleDate());
			entity.setShipDate(dto.getShipDate());
			entity.setTestingDate(dto.getTestingDate());
			entity.setResultDate(dto.getResultDate());
			entity.setSampleTestType(dto.getSampleTestType());
			entity.setSampleType(dto.getSampleType());
			entity.setSampleStatus(dto.getSampleStatus());
			entity.setSampleResult(dto.getSampleResult());
			HealthOrganization healthOrganization = null;
			if (dto.getLabTest() != null && dto.getLabTest().getId() != null) {
				healthOrganization = healthOrganizationRepository.getOne(dto.getLabTest().getId());
			}
			entity.setLabTest(healthOrganization);

			HealthOrganization sampleCollectOrg = null;
			if (dto.getSampleCollectOrg() != null && dto.getSampleCollectOrg().getId() != null) {
				sampleCollectOrg = healthOrganizationRepository.getOne(dto.getSampleCollectOrg().getId());
			}
			entity.setSampleCollectOrg(sampleCollectOrg);

			NCoVAdministrativeUnit administrativeUnit = null;
			if (dto.getSampleAdminUnit() != null && dto.getSampleAdminUnit().getId() != null) {
				administrativeUnit = nCoVAdministrativeUnitRepository.getOne(dto.getSampleAdminUnit().getId());
			}
			entity.setSampleAdminUnit(administrativeUnit);
			entity = sampleRepository.save(entity);
			
			Set<Sample> sampleBottles = new HashSet<Sample>();
			
			// Lưu các children
			if (dto.getChidren() != null && dto.getChidren().size() > 0) {
				Integer index = 0;
				for(SampleDto srbDto : dto.getChidren()) {
					Sample sampleChild = null;
					if (srbDto.getId() != null) {
						sampleChild = sampleRepository.getOne(srbDto.getId());
					} else {
						sampleChild = new Sample();
					}
					
					String code = codeSampleParent + "-" + Integer.toString(index + 1);
					boolean updateOrNot = false;
					if(dto.getIsUpdateResult() != null) {
						updateOrNot = dto.getIsUpdateResult();
					}
					if(sampleChild.getCode() == null || sampleChild.getCode().length() == 0) {
						sampleChild.setCode(code);
						updateOrNot = true; // tạo mới sample con luôn update theo saple gộp
					}else {
						sampleChild.setCode(srbDto.getCode());
					}
					// Cập nhật cho các trường
					if(dto.getIsUpdateResult() !=null && updateOrNot == true) {
						sampleChild.setSampleDate(dto.getSampleDate());
						sampleChild.setShipDate(dto.getShipDate());
						sampleChild.setTestingDate(dto.getTestingDate());
						sampleChild.setResultDate(dto.getResultDate());
						sampleChild.setSampleTestType(dto.getSampleTestType());
						sampleChild.setSampleStatus(dto.getSampleStatus());
						
						HealthOrganization healthOrganizationChild = null;
						if (dto.getLabTest() != null && dto.getLabTest().getId() != null) {
							healthOrganizationChild = healthOrganizationRepository.getOne(dto.getLabTest().getId());
						}
						sampleChild.setLabTest(healthOrganizationChild);

						HealthOrganization sampleCollectOrgChild = null;
						if (dto.getSampleCollectOrg() != null && dto.getSampleCollectOrg().getId() != null) {
							sampleCollectOrgChild = healthOrganizationRepository.getOne(dto.getSampleCollectOrg().getId());
						}
						sampleChild.setSampleCollectOrg(sampleCollectOrgChild);

						NCoVAdministrativeUnit administrativeUnitChild = null;
						if (dto.getSampleAdminUnit() != null && dto.getSampleAdminUnit().getId() != null) {
							administrativeUnitChild = nCoVAdministrativeUnitRepository.getOne(dto.getSampleAdminUnit().getId());
						}
						entity.setSampleAdminUnit(administrativeUnitChild);
					}
					
					sampleChild.setParent(entity);
					sampleChild.setName(dto.getName());
					sampleChild.setSampleResult(dto.getSampleResult());
					sampleChild.setSampleType(1);
					
					SuspectedPerson person = null;
					if(srbDto.getPerson() != null && srbDto.getPerson().getId() != null) {
						person = personRepository.getOne(srbDto.getPerson().getId());
					}else {
						person = new SuspectedPerson();
					}
					
					if (person != null) {
						person.setName(srbDto.getPerson().getName());
						person.setGiveName(srbDto.getPerson().getGiveName());
						person.setBirthDate(srbDto.getPerson().getBirthDate());
						person.setGender(srbDto.getPerson().getGender());
						person.setDescription(srbDto.getPerson().getDescription());
						person.setPhoneNumber(srbDto.getPerson().getPhoneNumber());
						person.setFeverStatus(srbDto.getPerson().getFeverStatus());
						person.setCoughStatus(srbDto.getPerson().getCoughStatus());
						person.setShortnessOfBreath(srbDto.getPerson().getShortnessOfBreath());
						person.setSoreThroat(srbDto.getPerson().getSoreThroat());
						person.setDetailAddress(srbDto.getPerson().getDetailAddress());
						person.setIdNumber(srbDto.getPerson().getIdNumber());
						person.setCareer(srbDto.getPerson().getCareer());
						person.setSamplingLocation(srbDto.getPerson().getSamplingLocation());
						person.setDetailEpidemiologicalFactors(srbDto.getPerson().getDetailEpidemiologicalFactors());
						person.setLastContactDate(srbDto.getPerson().getLastContactDate());
						person.setDateOfLastContact(srbDto.getPerson().getDateOfLastContact());
						person.setPneumoniaStatus(srbDto.getPerson().getPneumoniaStatus());
						SuspectedLevel level = null;
						if (srbDto.getPerson().getSuspectedLevel() != null
								&& srbDto.getPerson().getSuspectedLevel().getId() != null) {
							level = suspectedLevelRepository.getOne(srbDto.getPerson().getSuspectedLevel().getId());
						}
						person.setSuspectedLevel(level);
	
						SuspectedType suspectedType = null;
						if (srbDto.getPerson().getSuspectedType() != null && srbDto.getPerson().getSuspectedType().getId() != null) {
							suspectedType = suspectedTypeRepository.getOne(srbDto.getPerson().getSuspectedType().getId());
						}
						person.setSuspectedType(suspectedType);
	
						EpidemiologicalFactors epidemiologicalFactors = null;
						if (srbDto.getPerson().getEpidemiologicalFactors() != null
								&& srbDto.getPerson().getEpidemiologicalFactors().getId() != null) {
							epidemiologicalFactors = epidemiologicalFactorsRepository
									.getOne(srbDto.getPerson().getEpidemiologicalFactors().getId());
						}
						person.setEpidemiologicalFactors(epidemiologicalFactors);
	
						NCoVAdministrativeUnit wardOfResidence = null;
						if (srbDto.getPerson().getWardOfResidence() != null
								&& srbDto.getPerson().getWardOfResidence().getId() != null) {
							wardOfResidence = nCoVAdministrativeUnitRepository
									.findOne(srbDto.getPerson().getWardOfResidence().getId());
						}
						person.setWardOfResidence(wardOfResidence);
	
						NCoVAdministrativeUnit districtOfResidence = null;
						if (srbDto.getPerson().getDistrictOfResidence() != null
								&& srbDto.getPerson().getDistrictOfResidence().getId() != null) {
							districtOfResidence = nCoVAdministrativeUnitRepository
									.findOne(srbDto.getPerson().getDistrictOfResidence().getId());
						}
						person.setDistrictOfResidence(districtOfResidence);
	
						NCoVAdministrativeUnit provinceOfResidence = null;
						if (srbDto.getPerson().getProvinceOfResidence() != null
								&& srbDto.getPerson().getProvinceOfResidence().getId() != null) {
							provinceOfResidence = nCoVAdministrativeUnitRepository
									.findOne(srbDto.getPerson().getProvinceOfResidence().getId());
						}
						person.setProvinceOfResidence(provinceOfResidence);
	
						HealthOrganization isolationPlace = null;
						if (srbDto.getPerson().getIsolationPlace() != null
								&& srbDto.getPerson().getIsolationPlace().getId() != null) {
							isolationPlace = healthOrganizationRepository
									.getOneById(srbDto.getPerson().getIsolationPlace().getId());
						}
						person.setIsolationPlace(isolationPlace);
						person = personRepository.save(person);
						sampleChild.setPerson(person);
					}
					
					sampleChild = sampleRepository.save(sampleChild);
					sampleBottles.add(sampleChild);
					index ++;
				}
			}
			
			if (entity.getChidren() == null) {
				entity.setChidren(sampleBottles);
			} else {
				entity.getChidren().clear();
				entity.getChidren().addAll(sampleBottles);
			}

			if (entity != null) {
				return new SampleDto(entity);
			}
		}
		return null;
	}

	@Override
	public String autoGeneratorCode(String sampleCollectOrgName) {
		String code = "";
		String sampleCollectOrg = sampleCollectOrgName;
		String numberZero = "";
		// Đếm số mẫu đơn vị
		String countSample = "SELECT COUNT(entity.id) FROM Sample entity WHERE "
				+ "entity.sampleCollectOrg.code =: nameCollectOrg ";
		Query qCount = manager.createQuery(countSample);
		qCount.setParameter("nameCollectOrg", sampleCollectOrgName);
		long numberSampleByCollect = (long) qCount.getSingleResult() + 1;
		// Thêm 0 đằng trước
		StringBuilder sb = new StringBuilder();
		String characters = Long.toString(numberSampleByCollect);
		int subtraction = 6 - characters.length();
		if(subtraction > 0) {
			for (int i = 0; i < subtraction; i++) {
	            char numZero = '0';
	            sb.append(numZero);
	        }
			numberZero = sb.toString();
		}
		// Nối các chuỗi lại
		code = sampleCollectOrg +"-"+ numberZero + numberSampleByCollect;
		return code;
	}

	@Override
	public List<SampleDto> updateResultSample(List<SampleDto> list, String resultSample) {
		List<SampleDto> listSample = new ArrayList<SampleDto>();
		if(list != null && list.size() > 0) {
			for(SampleDto sampleDto : list) {
				Sample sample = null;
				if(sampleDto != null && sampleDto.getId() != null) {
					sample = sampleRepository.getOne(sampleDto.getId());
				}
				if(resultSample != null){
					if(resultSample.equals("Checking")
							|| resultSample.equals("Negative") 
							|| resultSample.equals("Positive")) {
						sample.setSampleResult(resultSample);
						sample.setIsUpdateSampleResult(true);
					}
				}
				sample = sampleRepository.save(sample);
				SampleDto sDto = new SampleDto(sample);
				
				// Cập nhật cho các child
				if(sDto.getChidren()!=null && sDto.getChidren().size() > 0) {
					for(SampleDto sampleChild : sDto.getChidren()) {
						Sample childSample = null;
						if(sampleChild != null && sampleChild.getId() !=null) {
							childSample = sampleRepository.getOne(sampleChild.getId());	
						}
						if(resultSample != null){
							if(resultSample.equals("Checking")
									|| resultSample.equals("Negative") 
									|| resultSample.equals("Positive")) {
								childSample.setSampleResult(resultSample);
								childSample.setIsUpdateSampleResult(true);
							}
						}
						childSample = sampleRepository.save(childSample);
					}
				}
				
				listSample.add(sDto);
			}
			if(listSample != null && listSample.size() > 0) {
				return listSample;
			}else {
				return null;
			}
		}
		return null;
	}
}
