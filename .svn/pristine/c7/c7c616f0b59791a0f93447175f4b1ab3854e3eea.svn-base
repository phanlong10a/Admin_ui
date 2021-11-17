package com.globits.covid19.test.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globits.covid19.test.domain.EpidemiologicalFactors;
import com.globits.covid19.test.domain.SuspectedLevel;
import com.globits.covid19.test.dto.AnalyticsDto;
import com.globits.covid19.test.dto.AnalyticsSearchDto;
import com.globits.covid19.test.dto.HealthOrganizationDto;
import com.globits.covid19.test.dto.TestingStatusDto;
import com.globits.covid19.test.dto.UserInfoDto;
import com.globits.covid19.test.repository.SampleRepository;
import com.globits.covid19.test.service.AnalyticsService;
import com.globits.covid19.test.service.HealthOrganizationService;
import com.globits.covid19.test.service.UserOrganizationService;
import com.globits.covid19.test.utilities.Enums;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {
	@Autowired
	private EntityManager manager;

	@Autowired
	public UserOrganizationService userOrganizationService;

	@Autowired
	public SampleRepository sampleRepository;

	@Autowired
	HealthOrganizationService healthOrganizationService;

	@Override
	public List<AnalyticsDto> getTotalOverview(AnalyticsSearchDto searchDto) {
		// TODO can lay them thong tin duong tinh, am tinh
		List<AnalyticsDto> analyticsDto = new ArrayList<>();
		String whereClause = "";
		return analyticsDto;
	}

	@Override
	public List<AnalyticsDto> getTotalGroupByAdminUnit(AnalyticsSearchDto searchDto) {
		// TODO can lay them thong tin duong tinh, am tinh
		List<AnalyticsDto> analyticsDto = new ArrayList<>();

		return analyticsDto;
	}

	public boolean checkPermissionUser(UserInfoDto userOrganization) {
		if (userOrganization.isAdmin())
			return true;
		else if (userOrganization.isUser())
			if (userOrganization.getOrgType() != null && userOrganization.getUserOrganization() != null
					&& userOrganization.getUserOrganization().getUser() != null && userOrganization.getUserOrganization().getUser().getId() != null
					&& userOrganization.getUserOrganization().getOrg() != null && userOrganization.getUserOrganization().getOrg().getId() != null)
				return true;
		return false;
	}

	public String getWhereClause(AnalyticsSearchDto searchDto, UserInfoDto userOrganization) {
		String whereClause = "";

		if (!userOrganization.isAdmin() && userOrganization.isUser()) {
			if (userOrganization.getOrgType() == null)
				return "";
			if (userOrganization.getOrgType() != null) {
				if (userOrganization.getOrgType() == null
						&& (userOrganization.getListChildHealthOrganizationId() == null
								|| userOrganization.getListChildHealthOrganizationId().size() <= 0))
					return "";
				if (userOrganization.getOrgType().equals(Enums.OrganizationTypeEnum.LabTest.getType()))
					whereClause += " AND s.labTest.id IN (:listHealthOrganizationId) ";
				else if (userOrganization.getOrgType().equals(Enums.OrganizationTypeEnum.CollectSample.getType()))
					whereClause += " AND s.sampleCollectOrg.id IN (:listHealthOrganizationId) ";
				else if (userOrganization.getOrgType().equals(Enums.OrganizationTypeEnum.Isolation.getType()))
					//tạm thời chưa có logic về đơn vị cách ly nên sẽ lấy tất của đơn vị
					whereClause += " AND (s.person.isolationPlace.id IN (:listHealthOrganizationId)) ";
				else if (userOrganization.getOrgType().equals(Enums.OrganizationTypeEnum.Both.getType()))
					whereClause += " AND (s.labTest.id IN (:listHealthOrganizationId) OR s.sampleCollectOrg.id IN (:listHealthOrganizationId) OR s.person.isolationPlace.id IN (:listHealthOrganizationId)) ";
				else
					whereClause += " AND (s.labTest.id IN (:listHealthOrganizationId) OR s.sampleCollectOrg.id IN (:listHealthOrganizationId) OR s.person.isolationPlace.id IN (:listHealthOrganizationId)) ";
			}
		}
		return whereClause;
	}

	@Override
	public List<AnalyticsDto> getTotalGroupByHealthOrg(AnalyticsSearchDto searchDto) {
		List<UUID> listHealthOrganizationId = new ArrayList<>();
		UserInfoDto userOrganization = userOrganizationService.getAllInfoByUserLogin();
		if (userOrganization == null || !userOrganization.isAdmin()
				&& userOrganization.isUser() && (userOrganization.getListChildHealthOrganizationId() == null
						|| userOrganization.getListChildHealthOrganizationId().size() <= 0))
			return null;
		if (userOrganization.getListChildHealthOrganizationId() != null
				&& userOrganization.getListChildHealthOrganizationId().size() > 0)
			listHealthOrganizationId = userOrganization.getListChildHealthOrganizationId();
		if (!checkPermissionUser(userOrganization))
			return null;
		// TODO chuyen sang native SQL de co the count nhieu sử dụng SUM and CASE WHEN,
		// gio tạm thoi can lam nhanh nen count nhieu lan và dung foreach,
		List<AnalyticsDto> analyticsDto = new ArrayList<>();
		String whereClause = "";
		String orderBy = "  ORDER BY s.labTest.name";
		String groupBy = " GROUP BY s.labTest.id";
		String sqlTotal = "select new com.globits.covid19.test.dto.AnalyticsDto(s.labTest.id, s.labTest.name, count(s.id), 0L, 0L, 0, 0) from Sample s where s.parent.id IS NULL";

		whereClause += getWhereClause(searchDto, userOrganization);

		if (searchDto.getHealthOrganizationId() != null) {
			listHealthOrganizationId = userOrganizationService
					.findAllChildHealthOrganizationById(searchDto.getHealthOrganizationId(), userOrganization);
			whereClause += " AND (s.labTest.id IN (:listHealthOrganizationId) OR s.sampleCollectOrg.id IN (:listHealthOrganizationId) OR s.person.isolationPlace.id IN (:listHealthOrganizationId)) ";
		}
		sqlTotal += whereClause + groupBy + orderBy;
		Query qTotal = manager.createQuery(sqlTotal, AnalyticsDto.class);

		if (!userOrganization.isAdmin() && userOrganization.isUser())
			if (userOrganization.getOrgType() != null)
				qTotal.setParameter("listHealthOrganizationId", listHealthOrganizationId);
		if (searchDto.getHealthOrganizationId() != null)
			qTotal.setParameter("listHealthOrganizationId", listHealthOrganizationId);
		List<AnalyticsDto> analyticsTotalDto = qTotal.getResultList();

		whereClause = " where (1=1) AND s.parent.id IS NULL ";
		orderBy = "  ORDER BY s.labTest.name";
		groupBy = " GROUP BY s.labTest.id";
		List<String> listStatus = new ArrayList<>();
		listStatus.add(Enums.SampleStatusEnum.Pending.getName().toString());
		listStatus.add(Enums.SampleStatusEnum.Draft.getName().toString());
		String sqlTotalRemained = "select new com.globits.covid19.test.dto.AnalyticsDto(s.labTest.id, s.labTest.name,0L,count(s.id), 0L, 0, 0) from Sample s ";
				whereClause += " AND s.sampleStatus IN (:listStatus) ";

		whereClause += getWhereClause(searchDto, userOrganization);

		if (searchDto.getHealthOrganizationId() != null) {
			listHealthOrganizationId = userOrganizationService
					.findAllChildHealthOrganizationById(searchDto.getHealthOrganizationId(), userOrganization);
			whereClause += " AND (s.labTest.id IN (:listHealthOrganizationId) OR s.sampleCollectOrg.id IN (:listHealthOrganizationId) OR s.person.isolationPlace.id IN (:listHealthOrganizationId)) ";
		}

		sqlTotalRemained += whereClause + groupBy + orderBy;
		Query qTotalRemained = manager.createQuery(sqlTotalRemained, AnalyticsDto.class);

		qTotalRemained.setParameter("listStatus", listStatus);

		if (!userOrganization.isAdmin() && userOrganization.isUser())
			if (userOrganization.getOrgType() != null)
				qTotalRemained.setParameter("listHealthOrganizationId", listHealthOrganizationId);
		if (searchDto.getHealthOrganizationId() != null)
			qTotalRemained.setParameter("listHealthOrganizationId", listHealthOrganizationId);
		List<AnalyticsDto> analyticsTotalRemainedDto = qTotalRemained.getResultList();
		if (analyticsTotalRemainedDto != null && analyticsTotalDto.size() > 0 && analyticsTotalDto != null
				&& analyticsTotalRemainedDto.size() > 0)
			for (AnalyticsDto analyticsTotal : analyticsTotalDto)
				for (AnalyticsDto analyticsTotalRemained : analyticsTotalRemainedDto) {
					if (analyticsTotal.getHealthOrgId() == null)
						continue;
					if (analyticsTotal.getHealthOrgId().toString()
							.equals(analyticsTotalRemained.getHealthOrgId().toString()))
						analyticsTotal.setTotalRemainedCases(analyticsTotalRemained.getTotalRemainedCases());
				}

		return analyticsTotalDto;
	}

	@Override
	public List<AnalyticsDto> getTotalGroupByEpidemiologicalFactors(AnalyticsSearchDto searchDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getTotalTestedCases(AnalyticsSearchDto searchDto) {
		List<UUID> listHealthOrganizationId = new ArrayList<>();
		UserInfoDto userOrganization = userOrganizationService.getAllInfoByUserLogin();
		if (userOrganization == null || !userOrganization.isAdmin()
				&& userOrganization.isUser() && (userOrganization.getListChildHealthOrganizationId() == null
						|| userOrganization.getListChildHealthOrganizationId().size() <= 0))
			return null;
		if (userOrganization.getListChildHealthOrganizationId() != null
				&& userOrganization.getListChildHealthOrganizationId().size() > 0)
			listHealthOrganizationId = userOrganization.getListChildHealthOrganizationId();
		if (!checkPermissionUser(userOrganization))
			return null;

		String whereClause = " ";
		String orderBy = " ";
		String groupBy = "  ";
		String sqlTotal = "select count(s.id) from Sample s where s.testingDate is not null and s.sampleDate is not null ";

		whereClause += getWhereClause(searchDto, userOrganization);

		if (searchDto.getHealthOrganizationId() != null) {
			listHealthOrganizationId = userOrganizationService
					.findAllChildHealthOrganizationById(searchDto.getHealthOrganizationId(), userOrganization);
			whereClause += " AND (s.labTest.id IN (:listHealthOrganizationId) OR s.sampleCollectOrg.id IN (:listHealthOrganizationId) OR s.person.isolationPlace.id IN (:listHealthOrganizationId)) ";
		}

		if (Enums.SampleStatusEnum.Pending.getName() != null)
			whereClause += " AND ( s.sampleStatus !=:status  ) ";
		if (searchDto.getFromDate() != null && searchDto.getToDate() != null)
			whereClause += " AND ( s.testingDate >:fromDate AND s.testingDate <=:endDate ) ";
		sqlTotal += whereClause + groupBy + orderBy;
		Query qTotal = manager.createQuery(sqlTotal);
		if (Enums.SampleStatusEnum.Pending.getName() != null)
			qTotal.setParameter("status", Enums.SampleStatusEnum.Pending.getName());
//		if (Enums.SampleStatusEnum.Draft.getName() != null) {
//			qTotal.setParameter("draft", Enums.SampleStatusEnum.Draft.getName());
//		}
		if (searchDto.getFromDate() != null && searchDto.getToDate() != null) {
			qTotal.setParameter("fromDate", searchDto.getFromDate());
			qTotal.setParameter("endDate", searchDto.getToDate());
		}

		if (!userOrganization.isAdmin() && userOrganization.isUser())
			if (userOrganization.getOrgType() != null)
				qTotal.setParameter("listHealthOrganizationId", listHealthOrganizationId);
		if (searchDto.getHealthOrganizationId() != null)
			qTotal.setParameter("listHealthOrganizationId", listHealthOrganizationId);
		Long analyticsTotalDto = (Long) qTotal.getSingleResult();
		return analyticsTotalDto;
	}

	@Override
	public Long getTotalRemainedCases(AnalyticsSearchDto searchDto) {
		List<UUID> listHealthOrganizationId = new ArrayList<>();
		UserInfoDto userOrganization = userOrganizationService.getAllInfoByUserLogin();
		if (userOrganization == null || !userOrganization.isAdmin()
				&& userOrganization.isUser() && (userOrganization.getListChildHealthOrganizationId() == null
						|| userOrganization.getListChildHealthOrganizationId().size() <= 0))
			return null;
		if (userOrganization.getListChildHealthOrganizationId() != null
				&& userOrganization.getListChildHealthOrganizationId().size() > 0)
			listHealthOrganizationId = userOrganization.getListChildHealthOrganizationId();
		if (!checkPermissionUser(userOrganization))
			return null;

		String whereClause = "";
		String orderBy = "  ";
		String groupBy = "  ";
		String sqlTotal = "select count(s.id) from Sample s where  " + " s.sampleDate is not null  ";
		whereClause += getWhereClause(searchDto, userOrganization);

		if (searchDto.getHealthOrganizationId() != null) {
			listHealthOrganizationId = userOrganizationService
					.findAllChildHealthOrganizationById(searchDto.getHealthOrganizationId(), userOrganization);
			whereClause += " AND (s.labTest.id IN (:listHealthOrganizationId) OR s.sampleCollectOrg.id IN (:listHealthOrganizationId) OR s.person.isolationPlace.id IN (:listHealthOrganizationId)) ";
		}

		if (searchDto.getFromDate() != null && searchDto.getToDate() != null)
			whereClause += " AND ( s.sampleDate >:fromDate AND s.sampleDate <=:endDate ) ";
		sqlTotal += whereClause + groupBy + orderBy;
		Query qTotal = manager.createQuery(sqlTotal);

		if (searchDto.getFromDate() != null && searchDto.getToDate() != null) {
			qTotal.setParameter("fromDate", searchDto.getFromDate());
			qTotal.setParameter("endDate", searchDto.getToDate());
		}

		if (!userOrganization.isAdmin() && userOrganization.isUser())
			if (userOrganization.getOrgType() != null)
				qTotal.setParameter("listHealthOrganizationId", listHealthOrganizationId);
		if (searchDto.getHealthOrganizationId() != null)
			qTotal.setParameter("listHealthOrganizationId", listHealthOrganizationId);

		Long analyticsTotalDto = (Long) qTotal.getSingleResult();
		return analyticsTotalDto;
	}

	@Override
	public List<TestingStatusDto> SummarySampleTestingStatus(AnalyticsSearchDto searchDto) {
		List<UUID> listHealthOrganizationId = new ArrayList<>();
		UserInfoDto userOrganization = userOrganizationService.getAllInfoByUserLogin();
		if (userOrganization == null || !userOrganization.isAdmin()
				&& userOrganization.isUser() && (userOrganization.getListChildHealthOrganizationId() == null
						|| userOrganization.getListChildHealthOrganizationId().size() <= 0))
			return null;
		if (userOrganization.getListChildHealthOrganizationId() != null
				&& userOrganization.getListChildHealthOrganizationId().size() > 0)
			listHealthOrganizationId = userOrganization.getListChildHealthOrganizationId();
		if (!checkPermissionUser(userOrganization))
			return null;

		String whereClause = "";
		String groupBy = " GROUP BY s.sampleStatus";

		String sqlTotal = "SELECT new com.globits.covid19.test.dto.TestingStatusDto( s.sampleStatus, count(s.id)) FROM Sample s ";
		whereClause = " WHERE ( s.person IS NOT NULL ) ";
		whereClause += getWhereClause(searchDto, userOrganization);
		if (searchDto.getHealthOrganizationId() != null) {
			listHealthOrganizationId = userOrganizationService
					.findAllChildHealthOrganizationById(searchDto.getHealthOrganizationId(), userOrganization);
			whereClause += " AND (s.labTest.id IN (:listHealthOrganizationId) OR s.sampleCollectOrg.id IN (:listHealthOrganizationId) OR s.person.isolationPlace.id IN (:listHealthOrganizationId)) ";
		}
		sqlTotal += whereClause + groupBy;
		Query qTotal = manager.createQuery(sqlTotal, TestingStatusDto.class);
		if (!userOrganization.isAdmin() && userOrganization.isUser())
			if (userOrganization.getOrgType() != null)
				qTotal.setParameter("listHealthOrganizationId", listHealthOrganizationId);
				/*
				 * if (userOrganization.getOrgType().equals(Enums.OrganizationTypeEnum.
				 * CollectSample.getType())) { qTotal.setParameter("healthOrganizationId",
				 * listHealthOrganizationId); } else if
				 * (userOrganization.getOrgType().equals(Enums.OrganizationTypeEnum.LabTest.
				 * getType())) { qTotal.setParameter("healthOrganizationId",
				 * listHealthOrganizationId); } else
				 * if(userOrganization.getOrgType().equals(Enums.OrganizationTypeEnum.Both.
				 * getType())) { qTotal.setParameter("healthOrganizationId",
				 * listHealthOrganizationId); }
				 */
		if (searchDto.getHealthOrganizationId() != null)
			qTotal.setParameter("listHealthOrganizationId", listHealthOrganizationId);

		List<TestingStatusDto> list = qTotal.getResultList();
		return list;
	}

	@Override
	public List<AnalyticsDto> getTotalSampleByHealthOrg(AnalyticsSearchDto searchDto) {
		List<UUID> listHealthOrganizationId = new ArrayList<>();
		UserInfoDto userOrganization = userOrganizationService.getAllInfoByUserLogin();
		if (userOrganization == null || !userOrganization.isAdmin()
				&& userOrganization.isUser() && (userOrganization.getListChildHealthOrganizationId() == null
						|| userOrganization.getListChildHealthOrganizationId().size() <= 0))
			return null;
		if (userOrganization.getListChildHealthOrganizationId() != null
				&& userOrganization.getListChildHealthOrganizationId().size() > 0)
			listHealthOrganizationId = userOrganization.getListChildHealthOrganizationId();
		if (!checkPermissionUser(userOrganization))
			return null;

		String whereClause = "";
		String groupBy = " GROUP BY s.labTest.name";

		String sqlTotal = "SELECT new com.globits.covid19.test.dto.AnalyticsDto(s.labTest.id, s.labTest.name, count(s.id)) FROM Sample s WHERE (1=1) "
				+ " AND ( s.person IS NOT NULL )";

		whereClause += getWhereClause(searchDto, userOrganization);

		if (searchDto.getHealthOrganizationId() != null) {
			listHealthOrganizationId = userOrganizationService
					.findAllChildHealthOrganizationById(searchDto.getHealthOrganizationId(), userOrganization);
			whereClause += " AND (s.labTest.id IN (:listHealthOrganizationId) OR s.sampleCollectOrg.id IN (:listHealthOrganizationId) OR s.person.isolationPlace.id IN (:listHealthOrganizationId)) ";
		}

		sqlTotal += whereClause + groupBy;
		Query qTotal = manager.createQuery(sqlTotal, AnalyticsDto.class);

		if (!userOrganization.isAdmin() && userOrganization.isUser())
			if (userOrganization.getOrgType() != null)
				qTotal.setParameter("listHealthOrganizationId", listHealthOrganizationId);
		if (searchDto.getHealthOrganizationId() != null)
			qTotal.setParameter("listHealthOrganizationId", listHealthOrganizationId);
		List<AnalyticsDto> list = qTotal.getResultList();
		return list;
	}

	@Override
	public List<AnalyticsDto> getTotalCases(AnalyticsSearchDto searchDto) {
		List<AnalyticsDto> listData = new ArrayList<>();
		String strTimeline = "01/01/2000";
		Date timeline = null;
		try {
			timeline = new SimpleDateFormat("dd/MM/yyyy").parse(strTimeline);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = dateFormat.format(DateUtils.addDays(searchDto.getToDate(), 1));
//		String strDate = dateFormat.format(searchDto.getToDate() );
		Date date = searchDto.getFromDate();
		String date1 = dateFormat.format(searchDto.getFromDate());
		while (!date1.equals(strDate)) {
			AnalyticsDto AnalyticsDto = new AnalyticsDto();
			Date dateNew = DateUtils.addDays(date, 1);
			searchDto.setFromDate(date);
			searchDto.setToDate(dateNew);
			Long totalTest = getTotalTestedCases(searchDto);
			Long total = getTotalRemainedCases(searchDto);
			if (timeline != null) {
				searchDto.setFromDate(timeline);
				searchDto.setToDate(dateNew);
				Long totalAllRemainedCases = getTotalRemainedCases(searchDto);
				Long totalAllTest = getTotalTestedCases(searchDto);
				AnalyticsDto.setTotalAllTimeRemainedCases(totalAllRemainedCases);
				AnalyticsDto.setTotalAllTimeTestedCases(totalAllTest);

			}
			AnalyticsDto.setTotalRemainedCases(total);
			AnalyticsDto.setTotalTestedCases(totalTest);
			AnalyticsDto.setSampleTestDate(dateFormat.format(date));
			listData.add(AnalyticsDto);
			date = dateNew;
			date1 = dateFormat.format(dateNew);
		}

		return listData;
	}

	@Override
	public List<AnalyticsDto> getTotalCasesTest(AnalyticsSearchDto searchDto) {
		List<AnalyticsDto> listData = new ArrayList<>();
		String strTimeline = "01/01/2000";
		Date timeline = null;
		try {
			timeline = new SimpleDateFormat("dd/MM/yyyy").parse(strTimeline);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = dateFormat.format(searchDto.getToDate());
		Date date = searchDto.getFromDate();
		String date1 = dateFormat.format(searchDto.getFromDate());
		while (!date1.equals(strDate)) {
			AnalyticsDto AnalyticsDto = new AnalyticsDto();
			Date dateNew = DateUtils.addDays(date, 1);
			Long totalTest = sampleRepository.getTestCases(date, dateNew, Enums.SampleStatusEnum.Pending.getName());
			Long total = sampleRepository.getRemainedCases(date, dateNew, Enums.SampleStatusEnum.Pending.getName());
			if (timeline != null) {
				Long totalAllRemainedCases = sampleRepository.getRemainedCases(timeline, dateNew,
						Enums.SampleStatusEnum.Pending.getName());
				Long totalAllTest = sampleRepository.getTestCases(timeline, dateNew,
						Enums.SampleStatusEnum.Pending.getName());
				AnalyticsDto.setTotalAllTimeRemainedCases(totalAllRemainedCases);
				AnalyticsDto.setTotalAllTimeTestedCases(totalAllTest);

			}
			AnalyticsDto.setTotalRemainedCases(total);
			AnalyticsDto.setTotalTestedCases(totalTest);
			AnalyticsDto.setSampleTestDate(dateFormat.format(dateNew));
			listData.add(AnalyticsDto);
			date = dateNew;
			date1 = dateFormat.format(dateNew);
		}
		return listData;
	}

	@Override
	public List<AnalyticsDto> getTotalSuspectedPersonByLevel(AnalyticsSearchDto searchDto) {
		List<AnalyticsDto> listData = getListAnalyticsDtoByTypeAnalytics(SuspectedLevel.class.getSimpleName());

		UserInfoDto userOrganization = userOrganizationService.getAllInfoByUserLogin();
		// Compare Health Organization, add to same listStatus
		List<HealthOrganizationDto> healthOrganizationDtos = null;

		if(searchDto.getHealthOrganizationId() != null)
			healthOrganizationDtos =
					healthOrganizationService.getChildHealthOrganizations(searchDto.getHealthOrganizationId());
		else{
			if(userOrganization.isAdmin())
				healthOrganizationDtos = healthOrganizationService
						.getHealthOrganizationsByTypeAnalytics(SuspectedLevel.class.getSimpleName());
			if(userOrganization.isUser())
				healthOrganizationDtos =
						healthOrganizationService.getHealthOrganizationsByUserInfoDto(userOrganization);
		}

		List<AnalyticsDto> results = filterListAnalysticsByHealthOrganization(listData, healthOrganizationDtos);

		return results;
	}

	@Override
	public List<AnalyticsDto> getTotalEpidemiologicalFactors(AnalyticsSearchDto searchDto) {
		List<AnalyticsDto> listData = getListAnalyticsDtoByTypeAnalytics(EpidemiologicalFactors.class.getSimpleName());

		UserInfoDto userOrganization = userOrganizationService.getAllInfoByUserLogin();
		// Compare Health Organization, add to same listStatus
		List<HealthOrganizationDto> healthOrganizationDtos = null;

		if(searchDto.getHealthOrganizationId() != null)
			healthOrganizationDtos =
					healthOrganizationService.getChildHealthOrganizations(searchDto.getHealthOrganizationId());
		else{
			if(userOrganization.isAdmin())
				healthOrganizationDtos = healthOrganizationService
						.getHealthOrganizationsByTypeAnalytics(EpidemiologicalFactors.class.getSimpleName());
			if(userOrganization.isUser())
				healthOrganizationDtos =
						healthOrganizationService.getHealthOrganizationsByUserInfoDto(userOrganization);
		}

		List<AnalyticsDto> results = filterListAnalysticsByHealthOrganization(listData, healthOrganizationDtos);

		return results;
	}

	private List<AnalyticsDto> filterListAnalysticsByHealthOrganization(List<AnalyticsDto> listData,
			List<HealthOrganizationDto> healthOrganizationDtos) {
		List<AnalyticsDto> results = new ArrayList<>();

		for(HealthOrganizationDto healthOrganization: healthOrganizationDtos){
			AnalyticsDto analyticsDto = new AnalyticsDto();
			analyticsDto.setName(healthOrganization.getName());

			// Create new listStatus
			List<TestingStatusDto> listStatus = new ArrayList<>();

			List<AnalyticsDto> filterDatas = listData.stream()
					.parallel()
					.filter(e -> e.getName().equals(healthOrganization.getName()))
					.collect(Collectors.toList());

			for(AnalyticsDto dto: filterDatas)
				listStatus.add(dto.getStatus());

			if(filterDatas.size() > 0){
				analyticsDto.setListStatus(listStatus);
				results.add(analyticsDto);
			} else
				continue;
		}

		return results;
	}

	private List<AnalyticsDto> getListAnalyticsDtoByTypeAnalytics(String typeAnalytics) {
		String joinColumn = null;
		if(typeAnalytics != null){
			if(typeAnalytics.equals(EpidemiologicalFactors.class.getSimpleName()))
				joinColumn =
						" JOIN EpidemiologicalFactors " + typeAnalytics
								+ " ON "
								+ typeAnalytics
								+ ".id = person.epidemiologicalFactors.id";
			else if(typeAnalytics.equals(SuspectedLevel.class.getSimpleName()))
				joinColumn = " JOIN SuspectedLevel " + typeAnalytics
						+ " ON "
						+ typeAnalytics
						+ ".id = person.suspectedLevel.id";
		} else
			return null;

		String sql =
				"SELECT new com.globits.covid19.test.dto.AnalyticsDto(health.name, count(person.id) as total, "
						+ typeAnalytics
						+ ".name) FROM HealthOrganization health"
						+ " JOIN Sample sample ON sample.labTest.id = health.id"
						+ " JOIN SuspectedPerson person ON person.id = sample.person.id"
						+ joinColumn;

		String groupBy = " GROUP BY health.id, " + typeAnalytics + ".id";
		String orderBy = " ORDER BY health.name";

		sql += groupBy + orderBy;

		Query query = manager.createQuery(sql, AnalyticsDto.class);
		List<AnalyticsDto> listData = query.getResultList();
		return listData;
	}
}
