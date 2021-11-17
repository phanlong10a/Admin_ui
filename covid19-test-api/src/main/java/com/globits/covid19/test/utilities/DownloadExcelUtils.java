package com.globits.covid19.test.utilities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import com.globits.covid19.test.domain.SuspectedPerson;
import com.globits.covid19.test.dto.SampleDto;
import com.globits.covid19.test.dto.SuspectedPersonDto;
import com.globits.covid19.test.repository.SampleRepository;

//@Service
public class DownloadExcelUtils {
	//@Autowired
	HandleTimeUtils handleTimeUtils;

	//@Autowired
	HandleDateUtils handleDateUtils;

	//@Autowired
	SampleRepository sampleBagRepository;

	public static void handleSheetSample(List<SampleDto> listSample, Sheet sheet) {
		CellReference cr = new CellReference("A4");
		int rowIndexStart = cr.getRow();

		Cell cell;
		for (SampleDto sampleBag : listSample) {
			rowIndexStart++;
			Row row = sheet.createRow(rowIndexStart);

			cr = new CellReference("A" + rowIndexStart);
			cell = row.createCell(cr.getCol());
			if (sampleBag.getCode() != null)
				cell.setCellValue(sampleBag.getCode());

			cr = new CellReference("B" + rowIndexStart);
			cell = row.createCell(cr.getCol());

		}
	}
	
	
	public static ByteArrayResource exportSuspectedPersonToExcel(List<SuspectedPersonDto> dataList) throws IOException {
		XSSFWorkbook resultReportWorkbook = new XSSFWorkbook();
		XSSFSheet sheet = resultReportWorkbook.createSheet(" ");
//		XSSFCreationHelper createHelper = resultReportWorkbook.getCreationHelper();
		
		/* Tạo font */
		XSSFFont fontBold = resultReportWorkbook.createFont();
		fontBold.setBold(true); // set bold
		fontBold.setFontHeight(10); // add font size

		XSSFFont fontBoldTitle = resultReportWorkbook.createFont();
		fontBoldTitle.setBold(true); // set bold
		fontBoldTitle.setFontHeight(15); // add font size
		XSSFColor tableCellColor = new XSSFColor(java.awt.Color.WHITE);
		XSSFFont fontCell = resultReportWorkbook.createFont();
//		fontCell.setBold(true); // set bold
//		fontCell.setFontHeight(10); // add font size
		fontCell.setColor(IndexedColors.WHITE.getIndex());
		
		/* Tạo cell style */
		XSSFCellStyle titleCellStyle = resultReportWorkbook.createCellStyle();
		titleCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		titleCellStyle.setAlignment(HorizontalAlignment.CENTER);
		titleCellStyle.setFont(fontBoldTitle);
		
		
		XSSFCellStyle tableHeadCellStyle = resultReportWorkbook.createCellStyle();
		tableHeadCellStyle.setFont(fontBold);
		tableHeadCellStyle.setBorderBottom(BorderStyle.THIN);
		tableHeadCellStyle.setBorderTop(BorderStyle.THIN);
		tableHeadCellStyle.setBorderLeft(BorderStyle.THIN);
		tableHeadCellStyle.setBorderRight(BorderStyle.THIN);
//		tableHeadCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);


		XSSFCellStyle tableDataCellStyleHead = resultReportWorkbook.createCellStyle();
		tableDataCellStyleHead.setFont(fontBold);
		tableDataCellStyleHead.setBorderBottom(BorderStyle.THIN);
		tableDataCellStyleHead.setBorderTop(BorderStyle.THIN);
		tableDataCellStyleHead.setBorderLeft(BorderStyle.THIN);
		tableDataCellStyleHead.setBorderRight(BorderStyle.THIN);
		tableDataCellStyleHead.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		XSSFRow row  = sheet.createRow(0);
		XSSFCell cell =null;
		
		cell = row.createCell(0);
		cell.setCellValue("Tên người nghi nghiễm");
		cell.setCellStyle(tableHeadCellStyle);
		
		cell = row.createCell(1);
		cell.setCellValue("Năm sinh");
		cell.setCellStyle(tableHeadCellStyle);
		
		cell = row.createCell(2);
		cell.setCellValue("Giới tính");
		cell.setCellStyle(tableHeadCellStyle);
		
		cell = row.createCell(3);
		cell.setCellValue("Số điện thoại");
		cell.setCellStyle(tableHeadCellStyle);
		
		cell = row.createCell(4);
		cell.setCellValue("CCCD/ CMT");
		cell.setCellStyle(tableHeadCellStyle);
		
		cell = row.createCell(5);
		cell.setCellValue("Địa chỉ hiện tại");
		cell.setCellStyle(tableHeadCellStyle);
		
		cell = row.createCell(6);
		cell.setCellValue("Xã phường");
		cell.setCellStyle(tableHeadCellStyle);
		
		cell = row.createCell(7);
		cell.setCellValue("Quận/ huyện");
		cell.setCellStyle(tableHeadCellStyle);
		
		cell = row.createCell(8);
		cell.setCellValue("Tỉnh/ Thành phố");
		cell.setCellStyle(tableHeadCellStyle);
		
		cell = row.createCell(9);
		cell.setCellValue("Yếu tố dịch tễ");
		cell.setCellStyle(tableHeadCellStyle);
		
		cell = row.createCell(10);
		cell.setCellValue("Yếu tố dịch tễ chi tiết");
		cell.setCellStyle(tableHeadCellStyle);
		
		cell = row.createCell(11);
		cell.setCellValue("Nơi cách ly");
		cell.setCellStyle(tableHeadCellStyle);
		
		cell = row.createCell(12);
		cell.setCellValue("Cấp độ nghi nhiễm");
		cell.setCellStyle(tableHeadCellStyle);
		
		//Tạo các hàng cột dữ liệu
		XSSFRow tableDataRow;
		if(dataList != null && !dataList.isEmpty()) {
			for(int i = 0; i < dataList.size(); i++) {
				tableDataRow =  sheet.createRow(i + 1);
				SuspectedPersonDto data = dataList.get(i);
				
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
				
				String birthDate = "";
				String gender = "";
				String ward = "";
				String district = "";
				String province ="";
				String epidemiologicalFactors = "";
				String isolationPlace = "";
				String suspectedLevel = "";
				if(data.getBirthDate() != null) {
					birthDate = formatter.format(data.getBirthDate());
				}
				
				if(data.getGender() != null) {
					if(data.getGender().equals("M")) {
						gender = "Nam";
					}else if(data.getGender().equals("F")){
						gender = "Nữ";
					}
				}
				
				//Xã phường
				if(data.getWardOfResidence() != null && data.getWardOfResidence().getName() != null) {
					ward = data.getWardOfResidence().getName();
				}
				//Quận huyện
				if(data.getDistrictOfResidence() != null && data.getDistrictOfResidence().getName() != null) {
					district = data.getDistrictOfResidence().getName();
				}
				//Tỉnh
				if(data.getProvinceOfResidence() != null && data.getProvinceOfResidence().getName() != null) {
					province = data.getProvinceOfResidence().getName();
				}
				//yếu tố dịch tễ
				if(data.getEpidemiologicalFactors() != null && data.getEpidemiologicalFactors().getName() != null) {
					epidemiologicalFactors = data.getEpidemiologicalFactors().getName();
				}
				//Nơi cách ly
				if(data.getIsolationPlace() != null && data.getIsolationPlace().getName() != null) {
					isolationPlace = data.getIsolationPlace().getName();
				}
				//Cấp độ nghi nhiễm
				if(data.getSuspectedLevel() != null && data.getSuspectedLevel().getName() != null) {
					suspectedLevel = data.getSuspectedLevel().getName();
				}
				
				tableDataRow.createCell(0).setCellValue(data.getName());
				tableDataRow.createCell(1).setCellValue(birthDate);
				tableDataRow.createCell(2).setCellValue(gender);
				tableDataRow.createCell(3).setCellValue(data.getPhoneNumber());
				tableDataRow.createCell(4).setCellValue(data.getIdNumber());
				tableDataRow.createCell(5).setCellValue(data.getDetailAddress());
				tableDataRow.createCell(6).setCellValue(ward);
				tableDataRow.createCell(7).setCellValue(district);
				tableDataRow.createCell(8).setCellValue(province);
				tableDataRow.createCell(9).setCellValue(epidemiologicalFactors);
				tableDataRow.createCell(10).setCellValue(data.getDetailEpidemiologicalFactors());
				tableDataRow.createCell(11).setCellValue(isolationPlace);
				tableDataRow.createCell(12).setCellValue(suspectedLevel);
				
				sheet.autoSizeColumn(i);
			}
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			resultReportWorkbook.write(out);
		    out.close();
		    return new ByteArrayResource(out.toByteArray());
		}

		return null;
	}

}
