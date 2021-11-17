package com.globits.covid19.test.rest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.globits.covid19.test.dto.ReportDto;
import com.globits.covid19.test.dto.SampleDto;
import com.globits.covid19.test.dto.SampleSearchDto;
import com.globits.covid19.test.dto.SearchDto;
import com.globits.covid19.test.dto.SuspectedPersonDto;
import com.globits.covid19.test.dto.SuspectedPersonSearchDto;
import com.globits.covid19.test.report.ReportSampleByHealthOrgDto;
import com.globits.covid19.test.report.ReportSearchDto;
import com.globits.covid19.test.report.SampleReportBySuspectedLevelDto;
import com.globits.covid19.test.service.ExportExcelService;
import com.globits.covid19.test.service.ReportService;
import com.globits.covid19.test.service.SampleService;
import com.globits.covid19.test.service.SuspectedPersonService;
import com.globits.covid19.test.utilities.DownloadExcelUtils;


@RestController
@RequestMapping("/api/downloadExcel")
public class RestDownloadExcelController {
	@Autowired
	private ExportExcelService exportExcelService;

	@Autowired
	private SampleService sampleService;
	
	@Autowired
	private SuspectedPersonService suspectedPersonService;
	
	@Autowired
	private ReportService reportService;

	@GetMapping("/sample")
	public void exportSampleBag(HttpServletResponse response) {
		ByteArrayResource byteArrayResource = null;

		try {
			byteArrayResource = exportExcelService.exportSample();

			InputStream ins = new ByteArrayInputStream(byteArrayResource.getByteArray());
			IOUtils.copy(ins, response.getOutputStream());

			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			// response.setContentType("application/vnd.ms-excel.sheet.macroEnabled.12");
			response.addHeader("Content-Disposition", "attachment; filename=file.xlsx");
			response.flushBuffer();
		} catch (Exception e) {
			System.out.println(e + "");
		}
	}

	
	@RequestMapping(value = "/exportExcel", method = RequestMethod.POST)
    public void getLogFile(HttpSession session, HttpServletResponse response, @RequestBody SampleSearchDto dto)
            throws IOException {
        try {
            List<SampleDto> listExcel = null;
            dto.setPageSize(10000);
            listExcel = sampleService.searchByDto(dto).getContent();
            ByteArrayResource excelFile;
            excelFile = sampleService.sampleToExcel(listExcel);
            InputStream ins = new ByteArrayInputStream(excelFile.getByteArray());
            org.apache.commons.io.IOUtils.copy(ins, response.getOutputStream());
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.addHeader("Content-Disposition", "attachment; filename=Asset.xlsx");
            response.flushBuffer();
        } catch (Exception e) {
            System.out.println(e + "WAdwadwad");
        }
    }

	
	@RequestMapping(value="/exportExcelSuspectedPerson", method = RequestMethod.POST)
	public void exportSuspectedPersonToExcelTable(HttpSession session, HttpServletResponse response, @RequestBody SuspectedPersonSearchDto dto) throws IOException {
		Page<SuspectedPersonDto> data = suspectedPersonService.searchByDto(dto);
		List<SuspectedPersonDto> dataList = data.getContent();
		
		ByteArrayResource excelFile = null;
		if (dataList != null && dataList.size() > 0) {
			excelFile = DownloadExcelUtils.exportSuspectedPersonToExcel(dataList);
			InputStream ins = new ByteArrayInputStream(excelFile.getByteArray());
			org.apache.commons.io.IOUtils.copy(ins, response.getOutputStream());
		}
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.addHeader("Content-Disposition", "attachment; filename=DanhSachBanhNhanNghiNhiem.xlsx");
	}
	
	@RequestMapping(value="/exportExcelreportNumberOfTestsByStatus", method = RequestMethod.POST)
	public void exportExcelReportNumberOfTestsByStatus(HttpSession session, HttpServletResponse response, @RequestBody SampleSearchDto dto) throws IOException {
		try {
			List<ReportDto> dataList = null;
			dataList = reportService.reportNumberOfTestsByStatus(dto);
			ByteArrayResource excelFile;
	        excelFile = reportService.reportNumberOfTestsByStatusToExcel(dataList);
	        InputStream ins = new ByteArrayInputStream(excelFile.getByteArray());
	        org.apache.commons.io.IOUtils.copy(ins, response.getOutputStream());
	        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	        response.addHeader("Content-Disposition", "attachment; filename=Asset.xlsx");
	        response.flushBuffer();
		} catch (Exception e) {
			System.out.println(e + "Lỗi xuất Excel");
		}
		
	}
	
	@RequestMapping(value="/exportExcelReportSampleByHealthOrg", method = RequestMethod.POST)
	public void exportExcelReportSampleByHealthOrg(HttpSession session, HttpServletResponse response, @RequestBody ReportSearchDto dto) throws IOException { 
		try {
			List<ReportSampleByHealthOrgDto> dataList = reportService.reportSampleByHealthOrgDto(dto);
			ByteArrayResource excelFile;
	        excelFile = reportService.reportSampleByHealthOrgToExcel(dataList);
	        InputStream ins = new ByteArrayInputStream(excelFile.getByteArray());
	        org.apache.commons.io.IOUtils.copy(ins, response.getOutputStream());
	        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	        response.addHeader("Content-Disposition", "attachment; filename=Asset.xlsx");
	        response.flushBuffer();
		} catch (Exception e) {
			System.out.println(e + "Lỗi xuất Excel");
		}
		
	}
	
	@RequestMapping(value="/exportExcelReportSampleByCollectOrg", method = RequestMethod.POST)
	public void exportExcelReportSampleByCollectOrg(HttpSession session, HttpServletResponse response, @RequestBody ReportSearchDto dto) throws IOException { 
		try {
			List<ReportSampleByHealthOrgDto> dataList = null;
			dataList = reportService.reportSampleByCollectOrg(dto);
			ByteArrayResource excelFile;
	        excelFile = reportService.reportSampleByCollectOrgToExcel(dataList);
	        InputStream ins = new ByteArrayInputStream(excelFile.getByteArray());
	        org.apache.commons.io.IOUtils.copy(ins, response.getOutputStream());
	        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	        response.addHeader("Content-Disposition", "attachment; filename=Asset.xlsx");
	        response.flushBuffer();
		} catch (Exception e) {
			System.out.println(e + "  Lỗi xuất Excel");
		}
		
	}
	
	@RequestMapping(value="/exportExcelSampleReportBySuspectedLevel", method = RequestMethod.POST)
	public void exportExcelSampleReportBySuspectedLevel(HttpSession session, HttpServletResponse response, @RequestBody ReportSearchDto dto) throws IOException { 
		List<SampleReportBySuspectedLevelDto> dataList = reportService.SampleReportBySuspectedLevel(dto);
		
		try {
			ByteArrayResource excelFile;
	        excelFile = reportService.SampleReportBySuspectedLevelToExcel(dataList);
	        InputStream ins = new ByteArrayInputStream(excelFile.getByteArray());
	        org.apache.commons.io.IOUtils.copy(ins, response.getOutputStream());
	        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	        response.addHeader("Content-Disposition", "attachment; filename=Asset.xlsx");
	        response.flushBuffer();
		} catch (Exception e) {
			System.out.println(e + "Lỗi xuất Excel");
		}
		
	}
	
	@RequestMapping(value="/exportExcelreportNumberOfTestsByResult", method = RequestMethod.POST)
	public void exportExcelreportNumberOfTestsByResult(HttpSession session, HttpServletResponse response, @RequestBody SampleSearchDto dto) throws IOException {
		try {
			List<ReportDto> dataList = null;
			dataList = reportService.reportNumberOfTestsByResult(dto);
			ByteArrayResource excelFile;
	        excelFile = reportService.reportNumberOfTestsByResultToExcel(dataList);
	        InputStream ins = new ByteArrayInputStream(excelFile.getByteArray());
	        org.apache.commons.io.IOUtils.copy(ins, response.getOutputStream());
	        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	        response.addHeader("Content-Disposition", "attachment; filename=BaoCaoTheoKetQua.xlsx");
	        response.flushBuffer();
		} catch (Exception e) {
			System.out.println(e + "Lỗi xuất Excel");
		}
		
	}

}
