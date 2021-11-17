package com.globits.covid19.test.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.Query;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.globits.covid19.test.dto.ReportDto;
import com.globits.covid19.test.dto.SampleSearchDto;
import com.globits.covid19.test.dto.UserInfoDto;
import com.globits.covid19.test.report.ReportSampleByHealthOrgDto;
import com.globits.covid19.test.report.ReportSearchDto;
import com.globits.covid19.test.report.SampleReportBySuspectedLevelDto;
import com.globits.covid19.test.service.ReportService;
import com.globits.covid19.test.service.UserOrganizationService;
import com.globits.covid19.test.utilities.Enums;

@Service
public class ReportServiceImpl implements ReportService {
	@Autowired
	private EntityManager manager;
	@Autowired
	public UserOrganizationService userOrganizationService;

	@Override
	public List<ReportDto> reportNumberOfTestsByStatus(SampleSearchDto dto) {
		if (dto == null) {
			return null;
		}
		UserInfoDto userInfoDto = userOrganizationService.getAllInfoByUserLogin();
		List<UUID> filterUserOrg = null;

		if (userInfoDto.isUser()) {
			if (userInfoDto.getUserOrganization() == null) {
				return null;
			}
			filterUserOrg = userInfoDto.getListChildHealthOrganizationId();
		}
		int pageIndex = dto.getPageIndex();
		int pageSize = dto.getPageSize();

		if (pageIndex > 0) {
			pageIndex--;
		} else {
			pageIndex = 0;
		}

		String whereClause = "";
		String orderBy = " ORDER BY s.createDate DESC ";
		String groupBy = " GROUP BY s.sampleStatus ";
		String sqlCount = "select count(s.id) from Sample as s where (1=1) ";
		String sql = "select new com.globits.covid19.test.dto.ReportDto(s.sampleStatus,count(s.id)) from Sample as s where s.sampleStatus is not null ";

		if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
			whereClause += " AND (s.name LIKE :text " + "OR s.code LIKE :text " + "OR s.sampleType LIKE :text "
					+ "OR s.sampleStatus LIKE :text) ";
		}
		if (dto.getFromDate() != null && dto.getToDate() == null) {
			whereClause += " AND  s.sampleDate > :fromDate  ";
		}
		if (dto.getFromDate() == null && dto.getToDate() != null) {
			whereClause += " AND  s.sampleDate < :toDate  ";
		}
		if (dto.getFromDate() != null && dto.getToDate() != null) {
			whereClause += " AND ( s.sampleDate BETWEEN :fromDate AND :endDate ) ";
		}
		if (dto.getIsParent() != null && dto.getIsParent() == true) {
			whereClause += " AND s.parent IS NULL AND s.person IS NOT NULL ";
		}
		if (dto.getIsChidren() != null && dto.getIsChidren() == false) {
			whereClause += " AND ( s.person IS NOT NULL ) ";
		}
		if (dto.getLabTest() != null && dto.getLabTest().getId() != null) {
			whereClause += " AND s.labTest.id =: labTestId";
		}
		if (dto.getCollectSampleOrg() != null && dto.getCollectSampleOrg().getId() != null) {
			whereClause += " AND s.collectSampleOrg.id =: collectSampleOrgId";
		}
		if (filterUserOrg != null && !filterUserOrg.isEmpty()) {
			whereClause += " AND ( s.labTest.id IN :filterUserOrg OR s.sampleCollectOrg IN :filterUserOrg)";
		}
		sql += whereClause + groupBy;
		sqlCount += whereClause;
		Query q = manager.createQuery(sql, ReportDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
			q.setParameter("text", '%' + dto.getText().trim() + '%');
			qCount.setParameter("text", '%' + dto.getText().trim() + '%');
		}
		if (dto.getFromDate() != null && dto.getToDate() == null) {
			q.setParameter("fromDate", dto.getFromDate());
			qCount.setParameter("fromDate", dto.getFromDate());
		}
		if (dto.getFromDate() == null && dto.getToDate() != null) {
			q.setParameter("endDate", dto.getToDate());
			qCount.setParameter("endDate", dto.getToDate());
		}
		if (dto.getFromDate() != null && dto.getToDate() != null) {
			q.setParameter("fromDate", dto.getFromDate());
			q.setParameter("endDate", dto.getToDate());
			qCount.setParameter("fromDate", dto.getFromDate());
			qCount.setParameter("endDate", dto.getToDate());
		}
		if (dto.getLabTest() != null && dto.getLabTest().getId() != null) {
			q.setParameter("labTestId", dto.getLabTest().getId());
			qCount.setParameter("labTestId", dto.getLabTest().getId());
		}
		if (dto.getCollectSampleOrg() != null && dto.getCollectSampleOrg().getId() != null) {
			q.setParameter("collectSampleOrgId", dto.getCollectSampleOrg().getId());
			qCount.setParameter("collectSampleOrgId", dto.getCollectSampleOrg().getId());
		}
		if (userInfoDto != null && !userInfoDto.isAdmin() && userInfoDto.isUser() && filterUserOrg != null
				&& !filterUserOrg.isEmpty()) {
			q.setParameter("filterUserOrg", filterUserOrg);
			qCount.setParameter("filterUserOrg", filterUserOrg);
		}
		int startPosition = pageIndex * pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<ReportDto> entities = q.getResultList();
//		long count = (long) qCount.getSingleResult();
//
//		Pageable pageable = PageRequest.of(pageIndex, pageSize);
//		Page<ReportDto> result = new PageImpl<ReportDto>(entities, pageable, count);

		return entities;
	}
	@Override
	public List<ReportDto> reportNumberOfTestsByResult(SampleSearchDto dto) {
		if (dto == null) {
			return null;
		}
		UserInfoDto userInfoDto = userOrganizationService.getAllInfoByUserLogin();
		List<UUID> filterUserOrg = null;

		if (userInfoDto.isUser()) {
			if (userInfoDto.getUserOrganization() == null) {
				return null;
			}
			filterUserOrg = userInfoDto.getListChildHealthOrganizationId();
		}
		int pageIndex = dto.getPageIndex();
		int pageSize = dto.getPageSize();

		if (pageIndex > 0) {
			pageIndex--;
		} else {
			pageIndex = 0;
		}

		String whereClause = "";
		String orderBy = " ORDER BY s.createDate DESC ";
		String groupBy = " GROUP BY s.sampleResult ";
		String sqlCount = "select count(s.id) from Sample as s where (1=1) ";
		String sql = "select new com.globits.covid19.test.dto.ReportDto(s.sampleResult,count(s.id)) from Sample as s where s.sampleResult is not null AND s.labTest is not null ";

		if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
			whereClause += " AND (s.name LIKE :text " + "OR s.code LIKE :text " + "OR s.sampleType LIKE :text "
					+ "OR s.sampleStatus LIKE :text) ";
		}
		if (dto.getFromDate() != null && dto.getToDate() == null) {
			whereClause += " AND  s.sampleDate > :fromDate  ";
		}
		if (dto.getFromDate() == null && dto.getToDate() != null) {
			whereClause += " AND  s.sampleDate < :toDate  ";
		}
		if (dto.getFromDate() != null && dto.getToDate() != null) {
			whereClause += " AND ( s.sampleDate BETWEEN :fromDate AND :endDate ) ";
		}
		// Lấy danh sách mẫu thường
		if (dto.getIsParent() != null && dto.getIsParent() == true) {
			whereClause += " AND ( s.parent IS NULL) ";
		}
		// lấy danh sách mẫu gộp
		if (dto.getIsChidren() != null && dto.getIsChidren() == true) {
			whereClause += " AND s.person IS NULL ";
		}
		if (dto.getIsChidren() != null && dto.getIsChidren() == false) {
			whereClause += " AND ( s.person IS NOT NULL ) ";
		}
		
		if (dto.getLabTest() != null && dto.getLabTest().getId() != null) {
			whereClause += " AND s.labTest.id =: labTestId";
		}
		if (dto.getCollectSampleOrg() != null && dto.getCollectSampleOrg().getId() != null) {
			whereClause += " AND s.collectSampleOrg.id =: collectSampleOrgId";
		}
		if (filterUserOrg != null && !filterUserOrg.isEmpty()) {
			whereClause += " AND ( s.labTest.id IN :filterUserOrg OR s.sampleCollectOrg IN :filterUserOrg)";
		}
		sql += whereClause + groupBy;
		sqlCount += whereClause;
		Query q = manager.createQuery(sql, ReportDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
			q.setParameter("text", '%' + dto.getText().trim() + '%');
			qCount.setParameter("text", '%' + dto.getText().trim() + '%');
		}
		if (dto.getFromDate() != null && dto.getToDate() == null) {
			q.setParameter("fromDate", dto.getFromDate());
			qCount.setParameter("fromDate", dto.getFromDate());
		}
		if (dto.getFromDate() == null && dto.getToDate() != null) {
			q.setParameter("endDate", dto.getToDate());
			qCount.setParameter("endDate", dto.getToDate());
		}
		if (dto.getFromDate() != null && dto.getToDate() != null) {
			q.setParameter("fromDate", dto.getFromDate());
			q.setParameter("endDate", dto.getToDate());
			qCount.setParameter("fromDate", dto.getFromDate());
			qCount.setParameter("endDate", dto.getToDate());
		}
		if (dto.getLabTest() != null && dto.getLabTest().getId() != null) {
			q.setParameter("labTestId", dto.getLabTest().getId());
			qCount.setParameter("labTestId", dto.getLabTest().getId());
		}
		if (dto.getCollectSampleOrg() != null && dto.getCollectSampleOrg().getId() != null) {
			q.setParameter("collectSampleOrgId", dto.getCollectSampleOrg().getId());
			qCount.setParameter("collectSampleOrgId", dto.getCollectSampleOrg().getId());
		}
		if (userInfoDto != null && !userInfoDto.isAdmin() && userInfoDto.isUser() && filterUserOrg != null
				&& !filterUserOrg.isEmpty()) {
			q.setParameter("filterUserOrg", filterUserOrg);
			qCount.setParameter("filterUserOrg", filterUserOrg);
		}
		int startPosition = pageIndex * pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<ReportDto> entities = q.getResultList();

		return entities;
	}
	
	@Override
	public List<ReportDto> reportNumberOrgOfTestsByStatus(SampleSearchDto dto) {
		if (dto == null) {
			return null;
		}
		UserInfoDto userInfoDto = userOrganizationService.getAllInfoByUserLogin();
		List<UUID> filterUserOrg = null;

		if (userInfoDto.isUser()) {
			if (userInfoDto.getUserOrganization() == null) {
				return null;
			}
			filterUserOrg = userInfoDto.getListChildHealthOrganizationId();
		}
		int pageIndex = dto.getPageIndex();
		int pageSize = dto.getPageSize();

		if (pageIndex > 0) {
			pageIndex--;
		} else {
			pageIndex = 0;
		}

		String whereClause = "";
		String orderBy = " ORDER BY s.createDate DESC ";
		String groupBy = " GROUP BY s.labTest.id ";
		String sql = "select new com.globits.covid19.test.dto.ReportDto(s.labTest.name,s.sampleStatus,count(s.id)) from Sample as s where (1=1) ";

		if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
			whereClause += " AND (s.name LIKE :text " + "OR s.code LIKE :text " + "OR s.sampleType LIKE :text "
					+ "OR s.sampleStatus LIKE :text) ";
		}
		if (dto.getFromDate() != null && dto.getToDate() == null) {
			whereClause += " AND  s.sampleDate > :fromDate  ";
		}
		if (dto.getFromDate() == null && dto.getToDate() != null) {
			whereClause += " AND  s.sampleDate < :toDate  ";
		}
		if (dto.getFromDate() != null && dto.getToDate() != null) {
			whereClause += " AND ( s.sampleDate BETWEEN :fromDate AND :endDate ) ";
		}
		if (dto.getSampleStatus() != null) {
			whereClause += " AND (s.sampleStatus =:sampleStatus) ";
		}
		if (dto.getIsParent() != null && dto.getIsParent() == true) {
			whereClause += " AND s.parent IS NULL AND s.person IS NOT NULL ";
		}
		if (dto.getIsChidren() != null && dto.getIsChidren() == false) {
			whereClause += " AND ( s.person IS NOT NULL ) ";
		}
		if (dto.getLabTest() != null && dto.getLabTest().getId() != null) {
			whereClause += " AND s.labTest.id =: labTestId";
		}
		if (dto.getCollectSampleOrg() != null && dto.getCollectSampleOrg().getId() != null) {
			whereClause += " AND s.collectSampleOrg.id =: collectSampleOrgId";
		}
		if (filterUserOrg != null && !filterUserOrg.isEmpty()) {
			whereClause += " AND ( s.labTest.id IN :filterUserOrg OR s.sampleCollectOrg IN :filterUserOrg)";
		}
		sql += whereClause + groupBy;
		Query q = manager.createQuery(sql, ReportDto.class);

		if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
			q.setParameter("text", '%' + dto.getText().trim() + '%');
		}
		if (dto.getFromDate() != null && dto.getToDate() == null) {
			q.setParameter("fromDate", dto.getFromDate());
		}
		if (dto.getFromDate() == null && dto.getToDate() != null) {
			q.setParameter("endDate", dto.getToDate());
		}
		if (dto.getFromDate() != null && dto.getToDate() != null) {
			q.setParameter("fromDate", dto.getFromDate());
			q.setParameter("endDate", dto.getToDate());
		}
		if (dto.getLabTest() != null && dto.getLabTest().getId() != null) {
			q.setParameter("labTestId", dto.getLabTest().getId());
		}
		if (dto.getSampleStatus() != null) {
			q.setParameter("sampleStatus", dto.getSampleStatus());
		}
		if (dto.getCollectSampleOrg() != null && dto.getCollectSampleOrg().getId() != null) {
			q.setParameter("collectSampleOrgId", dto.getCollectSampleOrg().getId());
		}
		if (userInfoDto != null && !userInfoDto.isAdmin() && userInfoDto.isUser() && filterUserOrg != null
				&& !filterUserOrg.isEmpty()) {
			q.setParameter("filterUserOrg", filterUserOrg);
		}
		int startPosition = pageIndex * pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<ReportDto> entities = q.getResultList();

		return entities;
	}

	@Override
	public List<ReportDto> reportNumberOrgOfTestsByResult(SampleSearchDto dto) {
		if (dto == null) {
			return null;
		}
		UserInfoDto userInfoDto = userOrganizationService.getAllInfoByUserLogin();
		List<UUID> filterUserOrg = null;

		if (userInfoDto.isUser()) {
			if (userInfoDto.getUserOrganization() == null) {
				return null;
			}
			filterUserOrg = userInfoDto.getListChildHealthOrganizationId();
		}
		int pageIndex = dto.getPageIndex();
		int pageSize = dto.getPageSize();

		if (pageIndex > 0) {
			pageIndex--;
		} else {
			pageIndex = 0;
		}

		String whereClause = "";
		String orderBy = " ORDER BY s.createDate DESC ";
		String groupBy = " GROUP BY s.labTest.id ";
		String sql = "select new com.globits.covid19.test.dto.ReportDto(s.labTest.name,s.sampleResult,count(s.id)) from Sample as s where (1=1) ";

		if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
			whereClause += " AND (s.name LIKE :text " + "OR s.code LIKE :text " + "OR s.sampleType LIKE :text "
					+ "OR s.sampleStatus LIKE :text) ";
		}
		if (dto.getFromDate() != null && dto.getToDate() == null) {
			whereClause += " AND  s.sampleDate > :fromDate  ";
		}
		if (dto.getFromDate() == null && dto.getToDate() != null) {
			whereClause += " AND  s.sampleDate < :toDate  ";
		}
		if (dto.getFromDate() != null && dto.getToDate() != null) {
			whereClause += " AND ( s.sampleDate BETWEEN :fromDate AND :endDate ) ";
		}
		if (dto.getSampleStatus() != null) {
			whereClause += " AND (s.sampleResult =:sampleStatus) ";
		}
		// Lấy danh sách mẫu thường
		if (dto.getIsParent() != null && dto.getIsParent() == true) {
			whereClause += " AND ( s.parent IS NULL) ";
		}
		// lấy danh sách mẫu gộp
		if (dto.getIsChidren() != null && dto.getIsChidren() == true) {
			whereClause += " AND s.person IS NULL ";
		}
		if (dto.getIsChidren() != null && dto.getIsChidren() == false) {
			whereClause += " AND ( s.person IS NOT NULL ) ";
		}
		if (dto.getLabTest() != null && dto.getLabTest().getId() != null) {
			whereClause += " AND s.labTest.id =: labTestId";
		}
		if (dto.getCollectSampleOrg() != null && dto.getCollectSampleOrg().getId() != null) {
			whereClause += " AND s.collectSampleOrg.id =: collectSampleOrgId";
		}
		if (filterUserOrg != null && !filterUserOrg.isEmpty()) {
			whereClause += " AND ( s.labTest.id IN :filterUserOrg OR s.sampleCollectOrg IN :filterUserOrg)";
		}
		sql += whereClause + groupBy;
		
		Query q = manager.createQuery(sql, ReportDto.class);
		

		if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
			q.setParameter("text", '%' + dto.getText().trim() + '%');
		}
		if (dto.getFromDate() != null && dto.getToDate() == null) {
			q.setParameter("fromDate", dto.getFromDate());
		}
		if (dto.getFromDate() == null && dto.getToDate() != null) {
			q.setParameter("endDate", dto.getToDate());
		}
		if (dto.getFromDate() != null && dto.getToDate() != null) {
			q.setParameter("fromDate", dto.getFromDate());
			q.setParameter("endDate", dto.getToDate());
		}
		if (dto.getLabTest() != null && dto.getLabTest().getId() != null) {
			q.setParameter("labTestId", dto.getLabTest().getId());
		}
		if (dto.getSampleStatus() != null) {
			q.setParameter("sampleStatus", dto.getSampleStatus());
		}
		if (dto.getCollectSampleOrg() != null && dto.getCollectSampleOrg().getId() != null) {
			q.setParameter("collectSampleOrgId", dto.getCollectSampleOrg().getId());
			
		}
		if (userInfoDto != null && !userInfoDto.isAdmin() && userInfoDto.isUser() && filterUserOrg != null
				&& !filterUserOrg.isEmpty()) {
			q.setParameter("filterUserOrg", filterUserOrg);
			
		}
		int startPosition = pageIndex * pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<ReportDto> entities = q.getResultList();

		return entities;
	}

	
	@Override
	public List<ReportSampleByHealthOrgDto> reportSampleByHealthOrgDto(ReportSearchDto dto) {

		if (dto == null) {
			return null;
		}
		dto.setIsParent(true);
		//Lấy tổng
		dto.setIsLabTest(true);
		List<ReportSampleByHealthOrgDto> listTotal = this.getSampleByHealthOrgDto(dto);
		//Danh sách bản nháp
//		dto.setIsPresent(true);
		dto.setIsDraft(true);
		List<ReportSampleByHealthOrgDto> listDraft = this.getSampleByHealthOrgDto(dto);
		//Danh sách chờ
		dto.setIsDraft(false);
		dto.setIsPending(true);
		List<ReportSampleByHealthOrgDto> listPending = this.getSampleByHealthOrgDto(dto);
		
		//Danh sách đã xác nhận
		dto.setIsPending(false);
		dto.setIsAccepted(true);
		List<ReportSampleByHealthOrgDto> listAccepted = this.getSampleByHealthOrgDto(dto);
		
		if (listTotal != null && listTotal.size() > 0) {
			for (ReportSampleByHealthOrgDto t : listTotal) {
				for (ReportSampleByHealthOrgDto d : listDraft) {
					if (t.getHealthOrgId().equals(d.getHealthOrgId())) {
						t.setTotalDraft(d.getTotalSample());
						break;
					}
				}
				
				for(ReportSampleByHealthOrgDto p : listPending) {
					if (t.getHealthOrgId().equals(p.getHealthOrgId())) {
						t.setTotalPending(p.getTotalSample());
						break;
					}
				}
				
				for(ReportSampleByHealthOrgDto a : listAccepted) {
					if (t.getHealthOrgId().equals(a.getHealthOrgId())) {
						t.setTotalAccepted(a.getTotalSample());
						break;
					}
				}
			}
		}

		return listTotal;
	}

	@Override
	public List<ReportSampleByHealthOrgDto> reportSampleByCollectOrg(ReportSearchDto dto) {
		if (dto == null) {
			return null;
		}
		dto.setIsCollectOrg(true);
		dto.setIsParent(true);
		//Lấy tổng
		List<ReportSampleByHealthOrgDto> listTotal = this.getSampleByHealthOrgDto(dto);
		//Danh sách bản nháp
//		dto.setIsPresent(true);
		dto.setIsDraft(true);
		List<ReportSampleByHealthOrgDto> listDraft = this.getSampleByHealthOrgDto(dto);
		//Danh sách chờ
		dto.setIsDraft(false);
		dto.setIsPending(true);
		List<ReportSampleByHealthOrgDto> listPending = this.getSampleByHealthOrgDto(dto);
		
		//Danh sách đã xác nhận
		dto.setIsPending(false);
		dto.setIsAccepted(true);
		List<ReportSampleByHealthOrgDto> listAccepted = this.getSampleByHealthOrgDto(dto);
		if (listTotal != null && listTotal.size() > 0) {
			for (ReportSampleByHealthOrgDto t : listTotal) {
				for (ReportSampleByHealthOrgDto d : listDraft) {
					if (t.getHealthOrgId().equals(d.getHealthOrgId())) {
						t.setTotalDraft(d.getTotalSample());
						break;
					}
				}
				
				for(ReportSampleByHealthOrgDto p : listPending) {
					if (t.getHealthOrgId().equals(p.getHealthOrgId())) {
						t.setTotalPending(p.getTotalSample());
						break;
					}
				}
				
				for(ReportSampleByHealthOrgDto a : listAccepted) {
					if (t.getHealthOrgId().equals(a.getHealthOrgId())) {
						t.setTotalAccepted(a.getTotalSample());
						break;
					}
				}
			}
		}

		return listTotal;
	}

	public List<ReportSampleByHealthOrgDto> getSampleByHealthOrgDto(ReportSearchDto dto) {

		if (dto == null) {
			return null;
		}

		UserInfoDto userInfoDto = userOrganizationService.getAllInfoByUserLogin();
		List<UUID> filterUserOrg = null;

		if (userInfoDto.isUser()) {
			if (userInfoDto.getUserOrganization() == null) {
				return null;
			}
			filterUserOrg = userInfoDto.getListChildHealthOrganizationId();
		}

		String whereClause = " Where (1=1) ";
		String groupBy = " GROUP BY h.id ";
		String sql = "";
		if (dto.getIsLabTest() != null && dto.getIsLabTest() == true) {
			sql = "select new com.globits.covid19.test.report.ReportSampleByHealthOrgDto(h.id, h.name, count(s.id)) "
					+ "from Sample s right join HealthOrganization h on s.labTest.id = h.id ";
		}

		if (dto.getIsCollectOrg() != null && dto.getIsCollectOrg() == true) {
			sql = "select new com.globits.covid19.test.report.ReportSampleByHealthOrgDto(h.id, h.name, count(s.id)) "
					+ "from Sample s right join HealthOrganization h on s.sampleCollectOrg.id = h.id ";
		}

		// tìm số lượng mẫu hiện tại
		if (dto.getIsPresent() != null && dto.getIsPresent() == true) {
			whereClause += " AND ( s.sampleStatus in ( '" + Enums.SampleStatusEnum.Draft.getName() + "','"
					+ Enums.SampleStatusEnum.Pending.getName() + "')) ";
		}
		if(dto.getIsDraft() != null && dto.getIsDraft() == true) {
			whereClause += " AND ( s.sampleStatus in ( '" + Enums.SampleStatusEnum.Draft.getName() + "') ) ";
		}
		if(dto.getIsPending() != null && dto.getIsPending() == true) {
			whereClause += " AND ( s.sampleStatus in ( '" + Enums.SampleStatusEnum.Pending.getName() + "') ) ";
		}
		if(dto.getIsAccepted() != null && dto.getIsAccepted() == true) {
			whereClause += " AND ( s.sampleStatus in ( '" + Enums.SampleStatusEnum.Accepted.getName() + "') ) ";
		}
		if (dto.getFromDate() != null && dto.getToDate() == null) {
			whereClause += " AND  s.sampleDate > :fromDate  ";
		}
		if (dto.getFromDate() == null && dto.getToDate() != null) {
			whereClause += " AND  s.sampleDate < :toDate  ";
		}
		if (dto.getFromDate() != null && dto.getToDate() != null) {
			whereClause += " AND ( s.sampleDate BETWEEN :fromDate AND :endDate ) ";
		}
		if (dto.getLabTest() != null && dto.getLabTest().getId() != null) {
			whereClause += " AND ( s.labTest.id = :labTestId) ";
		}

		if (dto.getCollectOrg() != null && dto.getCollectOrg().getId() != null) {
			whereClause += " AND ( s.sampleCollectOrg.id = :sampleCollectOrgId) ";
		}
		// Lấy danh sách mẫu thường
		if (dto.getIsParent() != null && dto.getIsParent() == true) {
			whereClause += " AND ( s.person IS NOT NULL ) ";
		}
		if (filterUserOrg != null && !filterUserOrg.isEmpty()) {
			whereClause += " AND ( s.labTest.id IN (:filterUserOrg) OR s.sampleCollectOrg IN (:filterUserOrg) ) ";
		}
		if (dto.getIsLabTest() != null && dto.getIsLabTest() == true) {
			whereClause += " AND (h.orgType IN ('" + Enums.OrganizationTypeEnum.Both.getType() + "', '"+ Enums.OrganizationTypeEnum.LabTest.getType() + "'))";
		}
		if (dto.getIsCollectOrg() != null && dto.getIsCollectOrg() == true) {
			whereClause += " AND (h.orgType IN ('" + Enums.OrganizationTypeEnum.Both.getType() + "', '"+ Enums.OrganizationTypeEnum.CollectSample.getType() + "'))";
		}
		sql += whereClause + groupBy;

		Query q = manager.createQuery(sql, ReportSampleByHealthOrgDto.class);
		if (dto.getFromDate() != null && dto.getToDate() == null) {
			q.setParameter("fromDate", dto.getFromDate());
		}
		if (dto.getFromDate() == null && dto.getToDate() != null) {
			q.setParameter("endDate", dto.getToDate());
		}
		if (dto.getFromDate() != null && dto.getToDate() != null) {
			q.setParameter("fromDate", dto.getFromDate());
			q.setParameter("endDate", dto.getToDate());
		}
		if (dto.getLabTest() != null && dto.getLabTest().getId() != null) {
			q.setParameter("labTestId", dto.getLabTest().getId());
		}

		if (dto.getCollectOrg() != null && dto.getCollectOrg().getId() != null) {
			q.setParameter("sampleCollectOrgId", dto.getCollectOrg().getId());
		}

		if (userInfoDto != null && !userInfoDto.isAdmin() && userInfoDto.isUser() && filterUserOrg != null
				&& !filterUserOrg.isEmpty()) {
			q.setParameter("filterUserOrg", filterUserOrg);
		}

		List<ReportSampleByHealthOrgDto> result = q.getResultList();

		return result;
	}

	@Override
	public List<SampleReportBySuspectedLevelDto> SampleReportBySuspectedLevel(ReportSearchDto dto) {
		if (dto == null) {
			return null;
		}
		dto.setIsLabTest(true);
//		dto.setIsPresent(true);
//		List<SampleReportBySuspectedLevelDto> listPresent = this.getSampleReportBySuspectedLevelDto(dto);
		dto.setIsParent(true);
		//Lấy tổng
		List<SampleReportBySuspectedLevelDto> listTotal = this.getSampleReportBySuspectedLevelDto(dto);
		//Danh sách bản nháp
		dto.setIsDraft(true);
		List<SampleReportBySuspectedLevelDto> listDraft = this.getSampleReportBySuspectedLevelDto(dto);
		//Danh sách chờ
		dto.setIsDraft(false);
		dto.setIsPending(true);
		List<SampleReportBySuspectedLevelDto> listPending = this.getSampleReportBySuspectedLevelDto(dto);
		
		//Danh sách đã xác nhận
		dto.setIsPending(false);
		dto.setIsAccepted(true);
		List<SampleReportBySuspectedLevelDto> listAccepted = this.getSampleReportBySuspectedLevelDto(dto);
		if (listTotal != null && listTotal.size() > 0) {
			for (SampleReportBySuspectedLevelDto t : listTotal) {
				for (SampleReportBySuspectedLevelDto p : listDraft) {
					if (t.getSuspectedLevelId().equals(p.getSuspectedLevelId())) {
						t.setTotalDraft(p.getCompletedQuantity());
						break;
					}
				}
				
				for (SampleReportBySuspectedLevelDto p : listPending) {
					if (t.getSuspectedLevelId().equals(p.getSuspectedLevelId())) {
						t.setTotalPending(p.getCompletedQuantity());
						break;
					}
				}
				
				for (SampleReportBySuspectedLevelDto p : listAccepted) {
					if (t.getSuspectedLevelId().equals(p.getSuspectedLevelId())) {
						t.setTotalAccepted(p.getCompletedQuantity());
						break;
					}
				}
				
			}
		}
		return listTotal;
	}

	public List<SampleReportBySuspectedLevelDto> getSampleReportBySuspectedLevelDto(ReportSearchDto dto) {
		if (dto == null) {
			return null;
		}
		UserInfoDto userInfoDto = userOrganizationService.getAllInfoByUserLogin();
		List<UUID> filterUserOrg = null;

		if (userInfoDto.isUser()) {
			if (userInfoDto.getUserOrganization() == null) {
				return null;
			}
			filterUserOrg = userInfoDto.getListChildHealthOrganizationId();
		}
		String whereClause = " Where (1=1) ";
		String groupBy = " GROUP BY  sl.id ";
		String orderBy = " ORDER BY sl.code ";
		String sql = "select new com.globits.covid19.test.report.SampleReportBySuspectedLevelDto(sl.id ,sl.code, sl.name , count(s.id)) "
				+ "from Sample s right join SuspectedPerson sp on s.person.id = sp.id "
				+ " right join SuspectedLevel sl on sp.suspectedLevel.id = sl.id ";

		// tìm số lượng mẫu hiện tại
		if (dto.getIsPresent() != null && dto.getIsPresent() == true) {
			whereClause += " AND ( s.sampleStatus in ( '" + Enums.SampleStatusEnum.Draft.getName() + "','"
					+ Enums.SampleStatusEnum.Pending.getName() + "')) ";
		}
		if(dto.getIsDraft() != null && dto.getIsDraft() == true) {
			whereClause += " AND ( s.sampleStatus in ( '" + Enums.SampleStatusEnum.Draft.getName() + "') ) ";
		}
		if(dto.getIsPending() != null && dto.getIsPending() == true) {
			whereClause += " AND ( s.sampleStatus in ( '" + Enums.SampleStatusEnum.Pending.getName() + "') ) ";
		}
		if(dto.getIsAccepted() != null && dto.getIsAccepted() == true) {
			whereClause += " AND ( s.sampleStatus in ( '" + Enums.SampleStatusEnum.Accepted.getName() + "') ) ";
		}
		if (dto.getFromDate() != null && dto.getToDate() == null) {
			whereClause += " AND  s.sampleDate > :fromDate  ";
		}
		if (dto.getFromDate() == null && dto.getToDate() != null) {
			whereClause += " AND  s.sampleDate < :toDate  ";
		}
		if (dto.getFromDate() != null && dto.getToDate() != null) {
			whereClause += " AND ( s.sampleDate BETWEEN :fromDate AND :endDate ) ";
		}
		// Lấy danh sách mẫu thường
		if (dto.getIsParent() != null && dto.getIsParent() == true) {
			whereClause += " AND ( s.person IS NOT NULL ) ";
		}
		if (dto.getLabTest() != null && dto.getLabTest().getId() != null) {
			whereClause += " AND ( s.labTest.id = :labTestId) ";
		}
		if (dto.getCollectOrg() != null && dto.getCollectOrg().getId() != null) {
			whereClause += " AND ( s.sampleCollectOrg.id = :sampleCollectOrgId) ";
		}

		if (filterUserOrg != null && !filterUserOrg.isEmpty()) {
			whereClause += " AND ( s.labTest.id IN (:filterUserOrg) OR s.sampleCollectOrg IN (:filterUserOrg) ) ";
		}
		sql += whereClause + groupBy + orderBy;

		Query q = manager.createQuery(sql, SampleReportBySuspectedLevelDto.class);

		if (dto.getFromDate() != null && dto.getToDate() == null) {
			q.setParameter("fromDate", dto.getFromDate());
		}
		if (dto.getFromDate() == null && dto.getToDate() != null) {
			q.setParameter("endDate", dto.getToDate());
		}
		if (dto.getFromDate() != null && dto.getToDate() != null) {
			q.setParameter("fromDate", dto.getFromDate());
			q.setParameter("endDate", dto.getToDate());
		}
		if (dto.getLabTest() != null && dto.getLabTest().getId() != null) {
			q.setParameter("labTestId", dto.getLabTest().getId());
		}
		if (dto.getCollectOrg() != null && dto.getCollectOrg().getId() != null) {
			q.setParameter("sampleCollectOrgId", dto.getCollectOrg().getId());
		}
		if (userInfoDto != null && !userInfoDto.isAdmin() && userInfoDto.isUser() && filterUserOrg != null
				&& !filterUserOrg.isEmpty()) {
			q.setParameter("filterUserOrg", filterUserOrg);
		}

		List<SampleReportBySuspectedLevelDto> result = q.getResultList();

		return result;
	}

	@Override
	public ByteArrayResource reportNumberOfTestsByStatusToExcel(List<ReportDto> list) throws Exception {
		String[] COLUMNs = { "No.", "Tên trạng thái", "Số lượng mẫu" };

		if (list != null && list.size() > 0) {
			Workbook workbook = new XSSFWorkbook();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			CreationHelper createHelper = workbook.getCreationHelper();
			Sheet sheet = workbook.createSheet("Báo Cáo Tổng Hợp Mẫu Theo Trạng Thái Mẫu");
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
			// CellStyle for row
			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setAlignment(HorizontalAlignment.CENTER);

			int rowIdx = 1;
			long stt = 0;
			for (ReportDto report : list) {
				Row row = sheet.createRow(rowIdx++);
				stt++;
				row.createCell(0).setCellValue(stt);
				// Tên trạng thái
				if (report.getStatusName() != null) {
					String nameStatus = "";
					if (report.getStatusName().equals(Enums.SampleStatusEnum.Draft.getName())) {
						nameStatus = "Chưa chuyển";
					} else if (report.getStatusName().equals(Enums.SampleStatusEnum.Accepted.getName())) {
						nameStatus = "Đã chấp nhận";
					} else if (report.getStatusName().equals(Enums.SampleStatusEnum.Canceled.getName())) {
						nameStatus = "Mẫu bị hủy";
					} else if (report.getStatusName().equals(Enums.SampleStatusEnum.Pending.getName())) {
						nameStatus = "Chờ xử lý";
					} else if (report.getStatusName().equals(Enums.SampleStatusEnum.Rejected.getName())) {
						nameStatus = "Mẫu không thể sử dụng";
					}
					Cell statusName = row.createCell(1);
					statusName.setCellValue(nameStatus);
					statusName.setCellStyle(cellStyle);
				}
				// Số lượng mẫu
				if (report.getTotalSample() != null) {
					Cell totalSample = row.createCell(2);
					totalSample.setCellValue(report.getTotalSample());
					totalSample.setCellStyle(cellStyle);
				} else if (report.getTotalSample() == null) {
					Cell totalSample = row.createCell(2);
					totalSample.setCellValue(0);
					totalSample.setCellStyle(cellStyle);
				}
			}
			for (int columnIndex = 0; columnIndex < COLUMNs.length; columnIndex++) {
				sheet.autoSizeColumn(columnIndex);
			}
			ByteArrayOutputStream excelFile = new ByteArrayOutputStream();
			workbook.write(excelFile);
			workbook.close();
			return new ByteArrayResource(excelFile.toByteArray());
		}
		return null;
	}

	@Override
	public ByteArrayResource reportSampleByHealthOrgToExcel(List<ReportSampleByHealthOrgDto> list) throws Exception {
		String[] COLUMNs = { "No.", "Tên phòng xét ngiệm", "Số mẫu chưa chuyển",  "Số mẫu chờ xác nhận", "Số mẫu đã chấp nhận", "Số lượng mẫu" };

//		try () {
		if (list != null && list.size() > 0) {
			Workbook workbook = new XSSFWorkbook();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			CreationHelper createHelper = workbook.getCreationHelper();
			Sheet sheet = workbook.createSheet("Báo Cáo Tổng Hợp Mẫu Theo Phòng Xét Nghiệm");
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
			// CellStyle for row
			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setAlignment(HorizontalAlignment.CENTER);

			int rowIdx = 1;
			long stt = 0;
			for (ReportSampleByHealthOrgDto report : list) {
				Row row = sheet.createRow(rowIdx++);
				stt++;
				row.createCell(0).setCellValue(stt);
				// Tên phòng xét ngiệm
				Cell statusName = row.createCell(1);
				statusName.setCellValue(report.getHealthOrgName());
				statusName.setCellStyle(cellStyle);
				// Số mẫu chưa chuyển
				if (report.getTotalDraft() != null) {
					Cell totalDraft = row.createCell(2);
					totalDraft.setCellValue(report.getTotalDraft());
					totalDraft.setCellStyle(cellStyle);
				} else if (report.getTotalDraft() == null) {
					Cell totalDraft = row.createCell(2);
					totalDraft.setCellValue(0);
					totalDraft.setCellStyle(cellStyle);
				}
				// Số mẫu chờ xác nhận
				if (report.getTotalPending() != null) {
					Cell totalPending = row.createCell(3);
					totalPending.setCellValue(report.getTotalPending());
					totalPending.setCellStyle(cellStyle);
				} else if (report.getTotalPending() == null) {
					Cell totalPending = row.createCell(3);
					totalPending.setCellValue(0);
					totalPending.setCellStyle(cellStyle);
				}
				// Số mẫu đã xác nhận
				if (report.getTotalAccepted() != null) {
					Cell totalAccepted = row.createCell(4);
					totalAccepted.setCellValue(report.getTotalAccepted());
					totalAccepted.setCellStyle(cellStyle);
				} else if (report.getTotalAccepted() == null) {
					Cell totalAccepted = row.createCell(4);
					totalAccepted.setCellValue(0);
					totalAccepted.setCellStyle(cellStyle);
				}
				// Số lượng mẫu
				if (report.getTotalSample() != null) {
					Cell totalSample = row.createCell(5);
					totalSample.setCellValue(report.getTotalSample());
					totalSample.setCellStyle(cellStyle);
				} else if (report.getTotalSample() == null) {
					Cell totalPresent = row.createCell(5);
					totalPresent.setCellValue(0);
					totalPresent.setCellStyle(cellStyle);
				}

			}
			for (int columnIndex = 0; columnIndex < COLUMNs.length; columnIndex++) {
				sheet.autoSizeColumn(columnIndex);
			}
			ByteArrayOutputStream excelFile = new ByteArrayOutputStream();
			workbook.write(excelFile);
			workbook.close();
			return new ByteArrayResource(excelFile.toByteArray());
		}
		return null;
	}

	@Override
	public ByteArrayResource reportSampleByCollectOrgToExcel(List<ReportSampleByHealthOrgDto> list) throws Exception {
		String[] COLUMNs = { "No.", "Tên phòng xét ngiệm", "Số mẫu chưa chuyển",  "Số mẫu chờ xác nhận", "Số mẫu đã chấp nhận", "Số lượng mẫu" };

//		try () {
		if (list != null && list.size() > 0) {
			Workbook workbook = new XSSFWorkbook();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			CreationHelper createHelper = workbook.getCreationHelper();
			Sheet sheet = workbook.createSheet("Báo Cáo Tổng Hợp Mẫu Theo Phòng Xét Nghiệm");
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
			// CellStyle for row
			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setAlignment(HorizontalAlignment.CENTER);

			int rowIdx = 1;
			long stt = 0;
			for (ReportSampleByHealthOrgDto report : list) {
				Row row = sheet.createRow(rowIdx++);
				stt++;
				row.createCell(0).setCellValue(stt);
				// Tên phòng xét ngiệm
				Cell statusName = row.createCell(1);
				statusName.setCellValue(report.getHealthOrgName());
				statusName.setCellStyle(cellStyle);
				// Số mẫu chưa chuyển
				if (report.getTotalDraft() != null) {
					Cell totalDraft = row.createCell(2);
					totalDraft.setCellValue(report.getTotalDraft());
					totalDraft.setCellStyle(cellStyle);
				} else if (report.getTotalDraft() == null) {
					Cell totalDraft = row.createCell(2);
					totalDraft.setCellValue(0);
					totalDraft.setCellStyle(cellStyle);
				}
				// Số mẫu chờ xác nhận
				if (report.getTotalPending() != null) {
					Cell totalPending = row.createCell(3);
					totalPending.setCellValue(report.getTotalPending());
					totalPending.setCellStyle(cellStyle);
				} else if (report.getTotalPending() == null) {
					Cell totalPending = row.createCell(3);
					totalPending.setCellValue(0);
					totalPending.setCellStyle(cellStyle);
				}
				// Số mẫu đã xác nhận
				if (report.getTotalAccepted() != null) {
					Cell totalAccepted = row.createCell(4);
					totalAccepted.setCellValue(report.getTotalAccepted());
					totalAccepted.setCellStyle(cellStyle);
				} else if (report.getTotalAccepted() == null) {
					Cell totalAccepted = row.createCell(4);
					totalAccepted.setCellValue(0);
					totalAccepted.setCellStyle(cellStyle);
				}
				// Số lượng mẫu
				if (report.getTotalSample() != null) {
					Cell totalSample = row.createCell(5);
					totalSample.setCellValue(report.getTotalSample());
					totalSample.setCellStyle(cellStyle);
				} else if (report.getTotalSample() == null) {
					Cell totalPresent = row.createCell(5);
					totalPresent.setCellValue(0);
					totalPresent.setCellStyle(cellStyle);
				}
			}
			for (int columnIndex = 0; columnIndex < COLUMNs.length; columnIndex++) {
				sheet.autoSizeColumn(columnIndex);
			}
			ByteArrayOutputStream excelFile = new ByteArrayOutputStream();
			workbook.write(excelFile);
			workbook.close();
			return new ByteArrayResource(excelFile.toByteArray());
		}
		return null;
	}

	@Override
	public ByteArrayResource SampleReportBySuspectedLevelToExcel(List<SampleReportBySuspectedLevelDto> list)
			throws Exception {
		String[] COLUMNs = { "No.", " Mã mức độ nghi nhiễm", "Tên mức độ nghi nhiễm", "Số mẫu chưa chuyển",  "Số mẫu chờ xác nhận", "Số mẫu đã chấp nhận",
				"Tổng số mẫu" };

//		try () {
		if (list != null && list.size() > 0) {
			Workbook workbook = new XSSFWorkbook();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			CreationHelper createHelper = workbook.getCreationHelper();
			Sheet sheet = workbook.createSheet("Báo Cáo Tổng Hợp Mẫu Theo Cấp Độ Nghi Nghiễm");
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
			// CellStyle for row
			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setAlignment(HorizontalAlignment.CENTER);

			int rowIdx = 1;
			long stt = 0;
			for (SampleReportBySuspectedLevelDto report : list) {
				Row row = sheet.createRow(rowIdx++);
				stt++;
				row.createCell(0).setCellValue(stt);
				// Mã mức độ nghi nhiễm
				if (report.getSuspectedLeveCode() != null) {
					Cell suspectedLeveCode = row.createCell(1);
					suspectedLeveCode.setCellValue(report.getSuspectedLeveCode());
					suspectedLeveCode.setCellStyle(cellStyle);
				}
				// Tên mức độ nghi nhiễm
				if (report.getSuspectedLeveName() != null) {
					Cell suspectedLeveName = row.createCell(2);
					suspectedLeveName.setCellValue(report.getSuspectedLeveName());
					suspectedLeveName.setCellStyle(cellStyle);
				}
//				// Số lượng mẫu đang chờ
//				if (report.getUnfinishedQuantity() != null) {
//					Cell unfinishedQuantity = row.createCell(3);
//					unfinishedQuantity.setCellValue(report.getUnfinishedQuantity());
//					unfinishedQuantity.setCellStyle(cellStyle);
//				} else if (report.getUnfinishedQuantity() == null) {
//					Cell totalPresent = row.createCell(3);
//					totalPresent.setCellValue(0);
//					totalPresent.setCellStyle(cellStyle);
//				}
				// Số mẫu chưa chuyển
				if (report.getTotalDraft() != null) {
					Cell totalDraft = row.createCell(3);
					totalDraft.setCellValue(report.getTotalDraft());
					totalDraft.setCellStyle(cellStyle);
				} else if (report.getTotalDraft() == null) {
					Cell totalDraft = row.createCell(3);
					totalDraft.setCellValue(0);
					totalDraft.setCellStyle(cellStyle);
				}
				// Số mẫu chờ xác nhận
				if (report.getTotalPending() != null) {
					Cell totalPending = row.createCell(4);
					totalPending.setCellValue(report.getTotalPending());
					totalPending.setCellStyle(cellStyle);
				} else if (report.getTotalPending() == null) {
					Cell totalPending = row.createCell(4);
					totalPending.setCellValue(0);
					totalPending.setCellStyle(cellStyle);
				}
				// Số mẫu đã xác nhận
				if (report.getTotalAccepted() != null) {
					Cell totalAccepted = row.createCell(5);
					totalAccepted.setCellValue(report.getTotalAccepted());
					totalAccepted.setCellStyle(cellStyle);
				} else if (report.getTotalAccepted() == null) {
					Cell totalAccepted = row.createCell(5);
					totalAccepted.setCellValue(0);
					totalAccepted.setCellStyle(cellStyle);
				}
				// Tổng số mẫu
				if (report.getCompletedQuantity() != null) {
					Cell completedQuantity = row.createCell(6);
					completedQuantity.setCellValue(report.getCompletedQuantity());
					completedQuantity.setCellStyle(cellStyle);
				} else if (report.getCompletedQuantity() == null) {
					Cell totalPresent = row.createCell(6);
					totalPresent.setCellValue(0);
					totalPresent.setCellStyle(cellStyle);
				}

//		} catch (Exception e) {
//			e.printStackTrace();
//		}
			}
			for (int columnIndex = 0; columnIndex < COLUMNs.length; columnIndex++) {
				sheet.autoSizeColumn(columnIndex);
			}
			ByteArrayOutputStream excelFile = new ByteArrayOutputStream();
			workbook.write(excelFile);
			workbook.close();
			return new ByteArrayResource(excelFile.toByteArray());
		}
		return null;
	}

	@Override
	public ByteArrayResource reportNumberOfTestsByResultToExcel(List<ReportDto> list) throws Exception {
		String[] COLUMNs = { "No.", "Tên kết quả mẫu", "Số lượng mẫu" };

		if (list != null && list.size() > 0) {
			Workbook workbook = new XSSFWorkbook();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			CreationHelper createHelper = workbook.getCreationHelper();
			Sheet sheet = workbook.createSheet("Báo Cáo Tổng Hợp Mẫu Theo Kết Quả Mẫu");
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
			// CellStyle for row
			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setAlignment(HorizontalAlignment.CENTER);

			int rowIdx = 1;
			long stt = 0;
			for (ReportDto report : list) {
				Row row = sheet.createRow(rowIdx++);
				stt++;
				row.createCell(0).setCellValue(stt);
				// Tên trạng thái
				if (report.getStatusName() != null) {
					String nameResult = "";
					if (report.getStatusName().equals(Enums.SampleResultEnum.Checking.getName())) {
						nameResult = "Dương tính cần xác nhận";
					} else if (report.getStatusName().equals(Enums.SampleResultEnum.Negative.getName())) {
						nameResult = "Âm tính";
					} else if (report.getStatusName().equals(Enums.SampleResultEnum.Positive.getName())) {
						nameResult = "Dương tính";
					}
					Cell statusName = row.createCell(1);
					statusName.setCellValue(nameResult);
					statusName.setCellStyle(cellStyle);
				}
				// Số lượng mẫu
				if (report.getTotalSample() != null) {
					Cell totalSample = row.createCell(2);
					totalSample.setCellValue(report.getTotalSample());
					totalSample.setCellStyle(cellStyle);
				} else if (report.getTotalSample() == null) {
					Cell totalSample = row.createCell(2);
					totalSample.setCellValue(0);
					totalSample.setCellStyle(cellStyle);
				}
			}
			for (int columnIndex = 0; columnIndex < COLUMNs.length; columnIndex++) {
				sheet.autoSizeColumn(columnIndex);
			}
			ByteArrayOutputStream excelFile = new ByteArrayOutputStream();
			workbook.write(excelFile);
			workbook.close();
			return new ByteArrayResource(excelFile.toByteArray());
		}
		return null;
	}
}
