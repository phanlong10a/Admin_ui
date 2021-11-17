package com.globits.covid19.test.utilities;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import com.globits.covid19.test.dto.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

public class ImportExcelUtil {

	
	private static Hashtable<String, Integer> hashAdministrativeUnitColumnConfig = new Hashtable<String, Integer>();
	private static Hashtable<String, String> hashColumnPropertyConfig = new Hashtable<String, String>();
	private static Hashtable<String, String> hashColumnSampleConfig = new Hashtable<String, String>();
	private static Hashtable<String, Integer> hashStudentColumnConfig = new Hashtable<String, Integer>();
	private static Hashtable<String, Integer> hashSampleGroupColumnConfig = new Hashtable<String, Integer>();

	public static List<NCoVAdministrativeUnitDto> importAdministrativeUnitFromInputStream(InputStream is) {
		List<NCoVAdministrativeUnitDto> ret = new ArrayList<NCoVAdministrativeUnitDto>();
		hashAdministrativeUnitColumnConfig.put("city", 0);// tỉnh thành phố
		hashAdministrativeUnitColumnConfig.put("cityCode", 1);// mã tỉnh
//		hashAdministrativeUnitColumnConfig.put("cityArea", 2);// diện tích tỉnh
//		hashAdministrativeUnitColumnConfig.put("cityLongtidu", 3);// kinh độ tỉnh
//		hashAdministrativeUnitColumnConfig.put("citylatidu", 4);// vĩ độ tỉnh
		hashAdministrativeUnitColumnConfig.put("district", 2);// quận huyện
		hashAdministrativeUnitColumnConfig.put("districtCode", 3); // mã quận huyện
//		hashAdministrativeUnitColumnConfig.put("districtArea", 7);// diện tích huyện
//		hashAdministrativeUnitColumnConfig.put("districtLongtidu", 8);// kinh độ huyện
//		hashAdministrativeUnitColumnConfig.put("districtlatidu", 9);// vĩ độ huyện
		hashAdministrativeUnitColumnConfig.put("wards", 4);// phường xã
		hashAdministrativeUnitColumnConfig.put("wardsCode", 5); // mã phường xã
//		hashAdministrativeUnitColumnConfig.put("wardsArea", 12);// diện tích xã
//		hashAdministrativeUnitColumnConfig.put("wardsLongtidu", 13);// kinh độ xã
//		hashAdministrativeUnitColumnConfig.put("wardslatidu", 14);// vĩ độ xã

		try {
			@SuppressWarnings("resource")

			Workbook workbook = new XSSFWorkbook(is);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			int rowIndex = 1;
			int num = datatypeSheet.getLastRowNum();
			while (rowIndex <= num) {
				Row currentRow = datatypeSheet.getRow(rowIndex);
				Cell currentCell = null;
				if (currentRow != null) {
					NCoVAdministrativeUnitDto unitCity = new NCoVAdministrativeUnitDto();
					Integer index = hashAdministrativeUnitColumnConfig.get("city");
					if (index != null) {
						currentCell = currentRow.getCell(index);// subjectCode
						if (currentCell != null && currentCell.getStringCellValue() != null) {
							String name = currentCell.getStringCellValue();
							unitCity.setName(name);
						}
					}
					index = hashAdministrativeUnitColumnConfig.get("cityCode");
					if (index != null) {
						currentCell = currentRow.getCell(index);// subjectName
						if (currentCell != null && currentCell.getStringCellValue() != null) {
							String code = currentCell.getStringCellValue();
							unitCity.setCode(code);
							unitCity.setLevel(1);// cấp 1 - tỉnh thành phố
						}
					}
					if (!containsUnit(unitCity.getCode(), ret)) {
						ret.add(unitCity);
					}

					NCoVAdministrativeUnitDto unitDistrict = new NCoVAdministrativeUnitDto();
					index = hashAdministrativeUnitColumnConfig.get("district");
					if (index != null) {
						currentCell = currentRow.getCell(index);// subjectNameEng
						if (currentCell != null && currentCell.getStringCellValue() != null) {
							String name = currentCell.getStringCellValue();
							unitDistrict.setName(name);
						}
					}

					index = hashAdministrativeUnitColumnConfig.get("districtCode");
					if (index != null) {
						currentCell = currentRow.getCell(index);//
						if (currentCell != null && currentCell.getStringCellValue() != null) {
							String code = currentCell.getStringCellValue();
							unitDistrict.setCode(code);
							unitDistrict.setLevel(2);// cấp 2- quận huyện
							unitDistrict.setParent(unitCity);
						}
					}
					if (!containsUnit(unitDistrict.getCode(), ret)) {
						ret.add(unitDistrict);
					}

					NCoVAdministrativeUnitDto unitWards = new NCoVAdministrativeUnitDto();
					index = hashAdministrativeUnitColumnConfig.get("wards");
					if (index != null) {
						currentCell = currentRow.getCell(index);// subjectNameEng
						if (currentCell != null && currentCell.getStringCellValue() != null) {
							String name = currentCell.getStringCellValue();
							unitWards.setName(name);
						}
					}

					index = hashAdministrativeUnitColumnConfig.get("wardsCode");
					if (index != null) {
						currentCell = currentRow.getCell(index);//
						if (currentCell != null && currentCell.getStringCellValue() != null) {
							String code = currentCell.getStringCellValue();
							unitWards.setCode(code);
							unitWards.setLevel(3);// cấp 3- phường / xã/ thị trấn
							unitWards.setParent(unitDistrict);
						}
					}
					if (!containsUnit(unitWards.getCode(), ret)) {
						ret.add(unitWards);
					}

				}
				rowIndex++;
			}
			return ret;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean containsUnit(String code, List<NCoVAdministrativeUnitDto> ret) {
		for (NCoVAdministrativeUnitDto o : ret) {
			if (o != null && o.getCode().equals(code)) {
				return true;
			}
		}
		return false;
	}

	private static void scanImportSampleColumnExcelIndex(Sheet datatypeSheet, int scanRowIndex) {
		Row row = datatypeSheet.getRow(scanRowIndex);
		int numberCell = row.getPhysicalNumberOfCells();

		hashColumnPropertyConfig.put("Mã".toLowerCase(), "code");
		hashColumnPropertyConfig.put("TT".toLowerCase(), "tt");
		hashColumnPropertyConfig.put("Họ tên".toLowerCase(), "name");
		hashColumnPropertyConfig.put("Năm sinh".toLowerCase(), "birthdate");
		hashColumnPropertyConfig.put("Giới Tính".toLowerCase(), "gender");
		hashColumnPropertyConfig.put("Điện thoai".toLowerCase(), "phoneNumber");
		hashColumnPropertyConfig.put("Địa chi cụ thể".toLowerCase(), "address");
		hashColumnPropertyConfig.put("Phường".toLowerCase(), "ward");
		hashColumnPropertyConfig.put("Quận".toLowerCase(), "district");
		hashColumnPropertyConfig.put("Tỉnh".toLowerCase(), "province");
		hashColumnPropertyConfig.put("Ngày tiếp xúc cuối cùng".toLowerCase(), "lastContactDate");
		hashColumnPropertyConfig.put("Mức độ nghi nhiễm".toLowerCase(), "suspectedLevel");
		hashColumnPropertyConfig.put("Yếu tố nguy cơ".toLowerCase(), "epidemiologicalFactors");
		hashColumnPropertyConfig.put("Chi tiết dịch tễ".toLowerCase(), "detailEpidemiologicalFactors");
		hashColumnPropertyConfig.put("Đơn vị lấy mẫu".toLowerCase(), "sampleCollectOrg");
		hashColumnPropertyConfig.put("Địa điểm lấy mẫu".toLowerCase(), "samplingLocation");
		hashColumnPropertyConfig.put("Ngày lấy mẫu".toLowerCase(), "sampleDate");
		hashColumnPropertyConfig.put("Giờ lấy mẫu".toLowerCase(), "sampleHour");
		hashColumnPropertyConfig.put("Ngày gửi mẫu".toLowerCase(), "shipDate");
		hashColumnPropertyConfig.put("Nơi xét nghiệm".toLowerCase(), "labTest");
		hashColumnPropertyConfig.put("Kết quả xét nghiệm".toLowerCase(), "sampleResult");

		for (int i = 0; i < numberCell; i++) {
			Cell cell = row.getCell(i);
			if (cell != null && cell.getCellTypeEnum() == CellType.STRING) {
				String cellValue = cell.getStringCellValue();
				if (cellValue != null && cellValue.length() > 0) {
					cellValue = cellValue.toLowerCase().trim();
					String propertyName = hashColumnPropertyConfig.get(cellValue);
					if (propertyName != null) {
						hashStudentColumnConfig.put(propertyName, i);
					}
				}
			}
		}
	}

	private static void scanImportSuspectedPersonColumnExcelIndex(Sheet datatypeSheet, int scanRowIndex) {
		Row row = datatypeSheet.getRow(scanRowIndex);
		int numberCell = row.getPhysicalNumberOfCells();


		hashColumnPropertyConfig.put("CCCD/ CMT".toLowerCase(), "idNumber");
		hashColumnPropertyConfig.put("TT".toLowerCase(), "tt");
		hashColumnPropertyConfig.put("Tên người nghi nghiễm".toLowerCase(), "name");
		hashColumnPropertyConfig.put("Năm sinh".toLowerCase(), "birthdate");
		hashColumnPropertyConfig.put("Giới Tính".toLowerCase(), "gender");
		hashColumnPropertyConfig.put("Số điện thoại".toLowerCase(), "phoneNumber");
		hashColumnPropertyConfig.put("Địa chỉ hiện tại".toLowerCase(), "address");
		hashColumnPropertyConfig.put("Xã phường".toLowerCase(), "ward");
		hashColumnPropertyConfig.put("Quận/ huyện".toLowerCase(), "district");
		hashColumnPropertyConfig.put("Tỉnh/ Thành phố".toLowerCase(), "province");
		hashColumnPropertyConfig.put("Ngày tiếp xúc cuối cùng".toLowerCase(), "lastContactDate");
		hashColumnPropertyConfig.put("Cấp độ nghi nhiễm".toLowerCase(), "suspectedLevel");
		hashColumnPropertyConfig.put("Yếu tố dịch tễ".toLowerCase(), "epidemiologicalFactors");
		hashColumnPropertyConfig.put("Yếu tố dịch tễ chi tiết".toLowerCase(), "detailEpidemiologicalFactors");
		hashColumnPropertyConfig.put("Đơn vị lấy mẫu".toLowerCase(), "sampleCollectOrg");
		hashColumnPropertyConfig.put("Địa điểm lấy mẫu".toLowerCase(), "samplingLocation");
		hashColumnPropertyConfig.put("Ngày lấy mẫu".toLowerCase(), "sampleDate");
		hashColumnPropertyConfig.put("Giờ lấy mẫu".toLowerCase(), "sampleHour");
		hashColumnPropertyConfig.put("Ngày gửi mẫu".toLowerCase(), "shipDate");
		hashColumnPropertyConfig.put("Nơi xét nghiệm".toLowerCase(), "labTest");
		hashColumnPropertyConfig.put("Kết quả xét nghiệm".toLowerCase(), "sampleResult");
		hashColumnPropertyConfig.put("Nghề nghiệp".toLowerCase(), "creer");
		hashColumnPropertyConfig.put("Triệu chứng sốt".toLowerCase(), "fever");
		hashColumnPropertyConfig.put("Triệu chứng ho".toLowerCase(), "cough");
		hashColumnPropertyConfig.put("Triệu chứng khó thở".toLowerCase(), "shortnessOfBreath");
		hashColumnPropertyConfig.put("Triệu chứng đau họng".toLowerCase(), "soreThroat");
		hashColumnPropertyConfig.put("Triệu chứng viêm phổi".toLowerCase(), "pneumonia");
		hashColumnPropertyConfig.put("Chọn đơn vị cách ly".toLowerCase(), "isolationPlace");

		for (int i = 0; i < numberCell; i++) {
			Cell cell = row.getCell(i);
			if (cell != null && cell.getCellTypeEnum() == CellType.STRING) {
				String cellValue = cell.getStringCellValue();
				if (cellValue != null && cellValue.length() > 0) {
					cellValue = cellValue.toLowerCase().trim();
					String propertyName = hashColumnPropertyConfig.get(cellValue);
					if (propertyName != null) {
						hashStudentColumnConfig.put(propertyName, i);
					}
				}
			}
		}
	}

	@SuppressWarnings("resource")
	public static ImportSampleResultsDto importSampleFromInputStream(InputStream is) throws IOException {
		ImportSampleResultsDto result = new ImportSampleResultsDto();
		List<SampleDto> listData = new ArrayList<SampleDto>();
		List<SampleDto> listDataError = new ArrayList<SampleDto>();
		String text = "";

		// cảnh báo
		Workbook workbook= null;
		Sheet datatypeSheet = null;
		try {
			workbook = new XSSFWorkbook(is);
			datatypeSheet = workbook.getSheetAt(0);
		} catch (Exception e) {
			try {
				System.out.println(e.getMessage());
				workbook = new HSSFWorkbook(is);
				datatypeSheet = workbook.getSheetAt(0);
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
				result.setSuccess(false);
				result.setText(NCoVConstant.ERROR_IMPORT_EXOPORT_TYPE_FILE);
				return result;
			}
		}
		if (workbook == null || datatypeSheet == null) {
			result.setSuccess(false);
			result.setText(NCoVConstant.ERROR_IMPORT_EXOPORT_TYPE_FILE);
			return result;
		}
		
		////////
		Row row = datatypeSheet.getRow(1);
		Cell cell = null;
		String sampleCollectOrgName = "";
		if (row != null) {
			cell = row.getCell(0);
			if (cell != null && cell.getCellTypeEnum() == CellType.NUMERIC) {
				sampleCollectOrgName = String.valueOf((int) cell.getNumericCellValue());
			} else if (cell != null && cell.getCellTypeEnum() == CellType.STRING
					&& cell.getStringCellValue() != null) {
				sampleCollectOrgName = cell.getStringCellValue().trim();
			}
		}

		int rowIndex = 7;
		scanImportSampleColumnExcelIndex(datatypeSheet, rowIndex-1); // 4 là hàng hiện tại theo file mẫu (tiêu đề)
		int falseIndex = 1;
		Calendar calendar = Calendar.getInstance();
		int num = datatypeSheet.getLastRowNum();
		while (rowIndex <= num) {
			Row currentRow = datatypeSheet.getRow(rowIndex);
			Cell currentCell = null;
			if (currentRow != null) {
				SampleDto dto = new SampleDto();
				dto.setIndexImport(rowIndex + 1);
				dto.setSampleCollectOrgName(sampleCollectOrgName);
				Integer index = hashStudentColumnConfig.get("code");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						String code = String.valueOf((int) currentCell.getNumericCellValue());
						dto.setCode(code);
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						String code = currentCell.getStringCellValue().trim();
						dto.setCode(code);
					}
				}

				index = hashStudentColumnConfig.get("name");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						String fullName = String.valueOf((int) currentCell.getNumericCellValue());
						dto.setPersonName(fullName);
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						String fullName = currentCell.getStringCellValue().trim();
						dto.setPersonName(fullName);
					}
				}
				index = hashStudentColumnConfig.get("birthdate");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					Date date = null;
					try {
						if (DateUtil.isCellDateFormatted(currentCell)) {
							calendar.setTime(currentCell.getDateCellValue());
							date = calendar.getTime();
						}
						else {
							date = formatImportDOB(currentCell);
						}
					} catch (Exception e) {
						date = formatImportDOB(currentCell);
					}
					if (date != null) {
						dto.setPersonBirthDate(date);
					}
				}
				index = hashStudentColumnConfig.get("gender");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					String gender = "";
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						gender = String.valueOf((int) currentCell.getNumericCellValue());
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						gender = currentCell.getStringCellValue().trim();
					}
					if (gender != null && StringUtils.hasLength(gender)) {
						dto.setPersonGender(ConvertToGender(gender));
					}
				}

				index = hashStudentColumnConfig.get("phoneNumber");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					String value = "";
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						value = String.valueOf((int) currentCell.getNumericCellValue());
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						value = currentCell.getStringCellValue().trim();
					}
					if (value != null && StringUtils.hasLength(value)) {
						dto.setPersonPhoneNumber(value);
					}
				}
				
				index = hashStudentColumnConfig.get("address");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						String address = String.valueOf((int) currentCell.getNumericCellValue());
						dto.setDetailAddress(address);
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						String address = currentCell.getStringCellValue().trim();
						dto.setDetailAddress(address);
					}
				}
				index = hashStudentColumnConfig.get("ward");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						String address = String.valueOf((int) currentCell.getNumericCellValue());
						dto.setWardImport(address);
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						String address = currentCell.getStringCellValue().trim();
						dto.setWardImport(address);
					}
				}
				index = hashStudentColumnConfig.get("district");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						String address = String.valueOf((int) currentCell.getNumericCellValue());
						dto.setDistrictImport(address);
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						String address = currentCell.getStringCellValue().trim();
						dto.setDistrictImport(address);
					}
				}

				index = hashStudentColumnConfig.get("province");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						String address = String.valueOf((int) currentCell.getNumericCellValue());
						dto.setProvinceImport(address);
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						String address = currentCell.getStringCellValue().trim();
						dto.setProvinceImport(address);
					}
				}

				index = hashStudentColumnConfig.get("lastContactDate");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					Date date = getDateFromCurrentCell(currentCell);
					if (date != null) {
						dto.setLastContactDate(date);
					}
				}
				
				index = hashStudentColumnConfig.get("suspectedLevel");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					String value = "";
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						value = String.valueOf((int) currentCell.getNumericCellValue());
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						value = currentCell.getStringCellValue().trim();
					}
					if (value != null && StringUtils.hasText(value)) {
						dto.setImportSuspectedLevel(value);
					}
				}
				
				index = hashStudentColumnConfig.get("epidemiologicalFactors");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					String value = "";
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						value = String.valueOf((int) currentCell.getNumericCellValue());
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						value = currentCell.getStringCellValue().trim();
					}
					if (value != null && StringUtils.hasText(value)) {
						dto.setImportEpidemiologicalFactors(value);
					}
				}
				
				index = hashStudentColumnConfig.get("detailEpidemiologicalFactors");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					String value = "";
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						value = String.valueOf((int) currentCell.getNumericCellValue());
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						value = currentCell.getStringCellValue().trim();
					}
					if (value != null && StringUtils.hasText(value)) {
						dto.setDetailEpidemiologicalFactors(value);
					}
				}
				
				index = hashStudentColumnConfig.get("sampleCollectOrg");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					String value = "";
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						value = String.valueOf((int) currentCell.getNumericCellValue());
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						value = currentCell.getStringCellValue().trim();
					}
					if (value != null && StringUtils.hasText(value)) {
						dto.setImportSampleCollectOrg(value);
					}
				}
				
				index = hashStudentColumnConfig.get("samplingLocation");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					String value = "";
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						value = String.valueOf((int) currentCell.getNumericCellValue());
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						value = currentCell.getStringCellValue().trim();
					}
					if (value != null && StringUtils.hasText(value)) {
						dto.setSamplingLocation(value);
					}
				}

				index = hashStudentColumnConfig.get("sampleDate");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					Date date = getDateFromCurrentCell(currentCell);
					if (date != null) {

						index = hashStudentColumnConfig.get("sampleHour");
						if (index != null) {
							currentCell = currentRow.getCell(index);
							Date hour = getHourFromCurrentCell(currentCell);
							if (hour != null) {
								date.setHours(hour.getHours());
								date.setMinutes(hour.getMinutes());
							}
						}
						
						dto.setSampleDate(date);
					}
				}
				
				index = hashStudentColumnConfig.get("shipDate");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					Date date = getDateFromCurrentCell(currentCell);
					if (date != null) {
						dto.setShipDate(date);
					}
				}
				
				index = hashStudentColumnConfig.get("labTest");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					String value = "";
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						value = String.valueOf((int) currentCell.getNumericCellValue());
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						value = currentCell.getStringCellValue().trim();
					}
					if (value != null && StringUtils.hasText(value)) {
						dto.setImportLabTest(value);
					}
				}
				
				index = hashStudentColumnConfig.get("sampleResult");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					String ketQua = "";
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						ketQua = String.valueOf((int) currentCell.getNumericCellValue());
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						ketQua = currentCell.getStringCellValue().trim();
					}
					if (ketQua != null && StringUtils.hasText(ketQua)) {
						dto.setSampleResult(ConvertToSampleStatus(ketQua));
					}
				}
				
				CheckValue check = checkErrorImport(dto);
				if (check != null && check.isStopReadFile()) {
				    break;
				}
				if (check != null && !check.isResult()) {
					text += String.format(NCoVConstant.ERROR_IMPORT_ROW_INDEX, dto.getIndexImport()) + check.getError() + System.lineSeparator();
					dto.setDetailsError(check.getError());
					listDataError.add(dto);
				}
				
				listData.add(dto);
			}
			rowIndex++;
		}
		if (listDataError != null && listDataError.size() > 0) {
			result.setSuccess(false);
			result.setText(text);
			result.setListData(listDataError);
		}
		else {
			result.setSuccess(true);
			result.setListData(listData);
		}
		return result;
	}

	public static ImportSuspectedPersonResultDto importSuspectedPersonFromInputStream(InputStream is) throws IOException {
		ImportSuspectedPersonResultDto result = new ImportSuspectedPersonResultDto();
		List<SuspectedPersonDto> listData = new ArrayList<SuspectedPersonDto>();
		List<SuspectedPersonDto> listDataError = new ArrayList<SuspectedPersonDto>();
		String text = "";

		// cảnh báo
		Workbook workbook= null;
		Sheet datatypeSheet = null;
		try {
			workbook = new XSSFWorkbook(is);
			datatypeSheet = workbook.getSheetAt(0);
		} catch (Exception e) {
			try {
				System.out.println(e.getMessage());
				workbook = new HSSFWorkbook(is);
				datatypeSheet = workbook.getSheetAt(0);
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
				result.setSuccess(false);
				result.setText(NCoVConstant.ERROR_IMPORT_EXOPORT_TYPE_FILE);
				return result;
			}
		}
		if (workbook == null || datatypeSheet == null) {
			result.setSuccess(false);
			result.setText(NCoVConstant.ERROR_IMPORT_EXOPORT_TYPE_FILE);
			return result;
		}

		Row row = datatypeSheet.getRow(1);
		Cell cell = null;

		int rowIndex = 7;
		scanImportSuspectedPersonColumnExcelIndex(datatypeSheet, rowIndex-1); // 4 là hàng hiện tại theo file mẫu (tiêu đề)
		int falseIndex = 1;
		Calendar calendar = Calendar.getInstance();
		int num = datatypeSheet.getLastRowNum();
		for(rowIndex = 7; rowIndex  <= num; rowIndex++) {
			Row currentRow = datatypeSheet.getRow(rowIndex);
			Cell currentCell = null;
			if (currentRow != null) {
				SuspectedPersonDto dto = new SuspectedPersonDto();
				dto.setIndexImport(rowIndex + 1);	
				
				Integer index = hashStudentColumnConfig.get("name");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						String fullName = String.valueOf((int) currentCell.getNumericCellValue());
						dto.setPersonName(fullName);
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						String fullName = currentCell.getStringCellValue().trim();
						dto.setPersonName(fullName);
					}else {
						continue;
					}	
				}
				
				index = hashStudentColumnConfig.get("birthdate");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					Date date = null;
					try {
						if (DateUtil.isCellDateFormatted(currentCell)) {
							calendar.setTime(currentCell.getDateCellValue());
							date = calendar.getTime();
						}
						else {
							date = formatImportDOB(currentCell);
						}
					} catch (Exception e) {
						date = formatImportDOB(currentCell);
					}
					if (date != null) {
						dto.setPersonBirthDate(date);
					}
				}
				index = hashStudentColumnConfig.get("gender");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					String gender = "";
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						gender = String.valueOf((int) currentCell.getNumericCellValue());
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						gender = currentCell.getStringCellValue().trim();
					}
					if (gender != null && StringUtils.hasLength(gender)) {
						dto.setPersonGender(ConvertToGender(gender));
					}
				}

				index = hashStudentColumnConfig.get("phoneNumber");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					String value = "";
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						value = String.valueOf((int) currentCell.getNumericCellValue());
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						value = currentCell.getStringCellValue().trim();
					}
					if (value != null && StringUtils.hasLength(value)) {
						dto.setPersonPhoneNumber(value);
					}
				}
				//Số căn cước
				index = hashStudentColumnConfig.get("idNumber");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						String idNumber = String.valueOf((int) currentCell.getNumericCellValue());
						dto.setPersonIdNumber(idNumber);
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						String idNumber = currentCell.getStringCellValue().trim();
						dto.setPersonIdNumber(idNumber);
					}
				}
				//Địa chỉ hiện tại
				index = hashStudentColumnConfig.get("address");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						String address = String.valueOf((int) currentCell.getNumericCellValue());
						dto.setDetailAddress(address);
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						String address = currentCell.getStringCellValue().trim();
						dto.setDetailAddress(address);
					}
				}
				//Xã
				index = hashStudentColumnConfig.get("ward");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						String address = String.valueOf((int) currentCell.getNumericCellValue());
						dto.setWardImport(address);
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						String address = currentCell.getStringCellValue().trim();
						dto.setWardImport(address);
					}
				}
				//Huyện
				index = hashStudentColumnConfig.get("district");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						String address = String.valueOf((int) currentCell.getNumericCellValue());
						dto.setDistrictImport(address);
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						String address = currentCell.getStringCellValue().trim();
						dto.setDistrictImport(address);
					}
				}
				//Tỉnh
				index = hashStudentColumnConfig.get("province");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						String address = String.valueOf((int) currentCell.getNumericCellValue());
						dto.setProvinceImport(address);
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						String address = currentCell.getStringCellValue().trim();
						dto.setProvinceImport(address);
					}
				}
				//Yếu tố dịch tễ
				index = hashStudentColumnConfig.get("epidemiologicalFactors");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					String value = "";
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						value = String.valueOf((int) currentCell.getNumericCellValue());
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						value = currentCell.getStringCellValue().trim();
					}
					if (value != null && StringUtils.hasText(value)) {
						dto.setImportEpidemiologicalFactors(value);
					}
				}
				//Yếu tố dịch tễ chi tiết
				index = hashStudentColumnConfig.get("detailEpidemiologicalFactors");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					String value = "";
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						value = String.valueOf((int) currentCell.getNumericCellValue());
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						value = currentCell.getStringCellValue().trim();
					}
					if (value != null && StringUtils.hasText(value)) {
						dto.setDetailEpidemiologicalFactors(value);
					}
				}
				//Đơn vị cách ly
				index = hashStudentColumnConfig.get("isolationPlace");
				if(index != null) {
					currentCell = currentRow.getCell(index);
					String value = "";
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						value = String.valueOf((int) currentCell.getNumericCellValue());
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						value = currentCell.getStringCellValue().trim();
					}
					if (value != null && StringUtils.hasText(value)) {
						dto.setImportIsolationPlace(value);
					}
				}
				
				//Cấp đô nghi nhiễm
				index = hashStudentColumnConfig.get("suspectedLevel");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					String value = "";
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						value = String.valueOf((int) currentCell.getNumericCellValue());
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						value = currentCell.getStringCellValue().trim();
					}
					if (value != null && StringUtils.hasText(value)) {
						dto.setImportSuspectedLevel(value);
					}
				}
//				Nghề nghiệp
				index = hashStudentColumnConfig.get("creer");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					String value = "";
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						value = String.valueOf((int) currentCell.getNumericCellValue());
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						value = currentCell.getStringCellValue().trim();
					}
					if (value != null && StringUtils.hasText(value)) {
						dto.setPersonCareer(value);
					}
				}

				//Ngày tiếp xúc cuối cùng
				index = hashStudentColumnConfig.get("lastContactDate");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					Date date = getDateFromCurrentCell(currentCell);
					if (date != null) {
						dto.setPersonLastContactDate(date);
					}
				}
				//Triệu chứng sốt
				index = hashStudentColumnConfig.get("fever");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					String value = "";
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						value = String.valueOf((int) currentCell.getNumericCellValue());
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						value = currentCell.getStringCellValue();
					}
					if (value != null && StringUtils.hasText(value)) {
						dto.setPersonFeverStatus(value);
					}
				}
				//Triệu chứng ho
				index = hashStudentColumnConfig.get("cough");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					String value = "";
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						value = String.valueOf((int) currentCell.getNumericCellValue());
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						value = currentCell.getStringCellValue();
					}
					if (value != null && StringUtils.hasText(value)) {
						dto.setPersonCoughStatus(value);
					}
				}
				//Triệu chứng khó thở
				index = hashStudentColumnConfig.get("shortnessOfBreath");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					String value = "";
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						value = String.valueOf((int) currentCell.getNumericCellValue());
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						value = currentCell.getStringCellValue();
					}
					if (value != null && StringUtils.hasText(value)) {
						dto.setPersonShortnessOfBreath(value);
					}
				}
				//Triệu chứng đau họng
				index = hashStudentColumnConfig.get("soreThroat");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					String value = "";
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						value = String.valueOf((int) currentCell.getNumericCellValue());
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						value = currentCell.getStringCellValue();
					}
					if (value != null && StringUtils.hasText(value)) {
						dto.setPersonSoreThroat(value);
					}
				}
				//Triệu chứng viên phổi
				index = hashStudentColumnConfig.get("pneumonia");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					String value = "";
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						value = String.valueOf((int) currentCell.getNumericCellValue());
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						value = currentCell.getStringCellValue();
					}
					if (value != null && StringUtils.hasText(value)) {
						dto.setPersonPneumoniaStatus(value);
					}
				}

				listData.add(dto);
			}	
		}
		if (listDataError != null && listDataError.size() > 0) {
			result.setSuccess(false);
			result.setText(text);
			result.setListData(listDataError);
		}
		else {
			result.setSuccess(true);
			result.setListData(listData);
		}
		return result;
	}

	
	private static void scanImportSampleGroupColumnExcelIndex(Sheet datatypeSheet, int scanRowIndex) {
		Row row = datatypeSheet.getRow(scanRowIndex);
		int numberCell = row.getPhysicalNumberOfCells();

		hashColumnSampleConfig.put("STT (1)".toLowerCase(), "stt");
		hashColumnSampleConfig.put("Mã số mẫu gộp (2)".toLowerCase(), "code");
		hashColumnSampleConfig.put("STT trong mẫu gộp".toLowerCase(), "sttSample");
		hashColumnSampleConfig.put("Tên các thành viên trong nhóm gộp (3)".toLowerCase(), "name");
		hashColumnSampleConfig.put("Ngày tháng năm sinh (4)".toLowerCase(), "birthdate");
		hashColumnSampleConfig.put("Ngày lấy mẫu".toLowerCase(), "sampleDate");
		hashColumnSampleConfig.put("Giới (5)".toLowerCase(), "gender");
		hashColumnSampleConfig.put("Thôn, xóm/ số nhà".toLowerCase(), "address");
		hashColumnSampleConfig.put("Xã".toLowerCase(), "ward");
		hashColumnSampleConfig.put("Huyện".toLowerCase(), "district");
		hashColumnSampleConfig.put("Tỉnh".toLowerCase(), "province");
		hashColumnSampleConfig.put("Đối tượng (7)".toLowerCase(), "suspectedLevel");
		hashColumnSampleConfig.put("SĐT(8)".toLowerCase(), "phoneNumber");
		hashColumnSampleConfig.put("Đơn vị lấy mẫu (9)".toLowerCase(), "sampleCollectOrg");
		hashColumnSampleConfig.put("Đơn vị xét nghiệm (10)".toLowerCase(), "labTest");
		hashColumnSampleConfig.put("Ghi chú (11)".toLowerCase(), "note");

		for (int i = 0; i < numberCell; i++) {
			Cell cell = row.getCell(i);
			if (cell != null && cell.getCellTypeEnum() == CellType.STRING) {
				String cellValue = cell.getStringCellValue();
				if (cellValue != null && cellValue.length() > 0) {
					cellValue = cellValue.toLowerCase().trim();
					String propertyName = hashColumnSampleConfig.get(cellValue);
					if (propertyName != null) {
						hashSampleGroupColumnConfig.put(propertyName, i);
					}
				}
			}
		}
	}
	
	@SuppressWarnings("resource")
	public static ImportSampleResultsDto importSampleGroupFromInputStream(InputStream is) throws IOException {
		
		ImportSampleResultsDto result = new ImportSampleResultsDto();
		List<SampleDto> listData = new ArrayList<SampleDto>();
		List<SampleDto> listSampleGroup = new ArrayList<SampleDto>();
		List<SampleDto> listDataError = new ArrayList<SampleDto>();
		String text = "";

		// cảnh báo
		Workbook workbook= null;
		Sheet datatypeSheet = null;
		try {
			workbook = new XSSFWorkbook(is);
			datatypeSheet = workbook.getSheetAt(0);
		} catch (Exception e) {
			try {
				System.out.println(e.getMessage());
				workbook = new HSSFWorkbook(is);
				datatypeSheet = workbook.getSheetAt(0);
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
				result.setSuccess(false);
				result.setText(NCoVConstant.ERROR_IMPORT_EXOPORT_TYPE_FILE);
				return result;
			}
		}
		if (workbook == null || datatypeSheet == null) {
			result.setSuccess(false);
			result.setText(NCoVConstant.ERROR_IMPORT_EXOPORT_TYPE_FILE);
			return result;
		}

		if(!datatypeSheet.getRow(0).getCell(0).toString().equals("Mẫu gộp")) {
			result.setSuccess(false);
			result.setText(NCoVConstant.ERROR_IMPORT_EXOPORT_READ_FILE_OR_NO_DATA);
			return result;
		}
		
		int rowIndex = 3;
		scanImportSampleGroupColumnExcelIndex(datatypeSheet, rowIndex-1); // 1 là hàng hiện tại theo file mẫu 
		scanImportSampleGroupColumnExcelIndex(datatypeSheet, rowIndex-2);
		int falseIndex = 1;
		Calendar calendar = Calendar.getInstance();
		int num = datatypeSheet.getLastRowNum();
		String codeSampleGroup = "";
		for(rowIndex  = 3 ; rowIndex < num; rowIndex++) {
			Row currentRow = datatypeSheet.getRow(rowIndex);
			Cell currentCell = null;
			if(currentRow != null) {
				SampleDto dto = new SampleDto();
				SampleDto SampleGroupdto = null;
				dto.setIndexImport(rowIndex + 1);
				Integer index = hashSampleGroupColumnConfig.get("code");//CodeMauGop
				if (index != null) {
					currentCell = currentRow.getCell(index);
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						String code = String.valueOf((int) currentCell.getNumericCellValue());				
						codeSampleGroup = code;
						SampleGroupdto = new SampleDto(); //Khi code mẫu gộp != null thì sẽ khởi tạo thằng mẫu gộp
						SampleGroupdto.setIndexImport(rowIndex + 1);
						SampleGroupdto.setCode(code);
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						String code = currentCell.getStringCellValue().trim();
						codeSampleGroup = code;
						SampleGroupdto = new SampleDto();
						SampleGroupdto.setIndexImport(rowIndex + 1);
						SampleGroupdto.setCode(code);
					}
				}
				
				index = hashSampleGroupColumnConfig.get("sttSample");//Cộng mã mẫu gộp với số thứ tự
				if (index != null) {
					currentCell = currentRow.getCell(index);
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						String sttSample = String.valueOf((int) currentCell.getNumericCellValue());
						String code = codeSampleGroup + "." + sttSample;
						dto.setCode(code);
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						String sttSample = currentCell.getStringCellValue().trim();
						String code = codeSampleGroup + "." + sttSample;
						dto.setCode(code);
					}else {
						continue;
					}
				}
				
				index = hashSampleGroupColumnConfig.get("name");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						String fullName = String.valueOf((int) currentCell.getNumericCellValue());
						dto.setPersonName(fullName);
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						String fullName = currentCell.getStringCellValue().trim();
						dto.setPersonName(fullName);
					}
				}
				
				index = hashSampleGroupColumnConfig.get("birthdate");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					Date date = null;
					try {
						if (DateUtil.isCellDateFormatted(currentCell)) {
							calendar.setTime(currentCell.getDateCellValue());
							date = calendar.getTime();
						}
						else {
							date = formatImportDOB(currentCell);
						}
					} catch (Exception e) {
						date = formatImportDOB(currentCell);
					}
					if (date != null) {
						dto.setPersonBirthDate(date);
					}
				}
				
				index = hashSampleGroupColumnConfig.get("sampleDate");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					Date date = getDateFromCurrentCell(currentCell);
					if (date != null) {

						index = hashSampleGroupColumnConfig.get("sampleHour");
						if (index != null) {
							currentCell = currentRow.getCell(index);
							Date hour = getHourFromCurrentCell(currentCell);
							if (hour != null) {
								date.setHours(hour.getHours());
								date.setMinutes(hour.getMinutes());
							}
						}
						
						dto.setSampleDate(date);
						if(SampleGroupdto != null) {
							SampleGroupdto.setSampleDate(date);
						}
					}
				}
				
				index = hashSampleGroupColumnConfig.get("gender");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					String gender = "";
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						gender = String.valueOf((int) currentCell.getNumericCellValue());
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						gender = currentCell.getStringCellValue().trim();
					}
					if (gender != null && StringUtils.hasLength(gender)) {
						dto.setPersonGender(ConvertToGender(gender));
					}
				}
				
				index = hashSampleGroupColumnConfig.get("address");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						String address = String.valueOf((int) currentCell.getNumericCellValue());
						dto.setDetailAddress(address);
						if(SampleGroupdto != null) {
							SampleGroupdto.setDetailAddress(address);
						}
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						String address = currentCell.getStringCellValue().trim();
						dto.setDetailAddress(address);
						if(SampleGroupdto != null) {
							SampleGroupdto.setDetailAddress(address);
						}
					}
				}
				
				index = hashSampleGroupColumnConfig.get("ward");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						String address = String.valueOf((int) currentCell.getNumericCellValue());
						dto.setWardImport(address);
						if(SampleGroupdto != null) {
							SampleGroupdto.setWardImport(address);
						}
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						String address = currentCell.getStringCellValue().trim();
						dto.setWardImport(address);
						if(SampleGroupdto != null) {
							SampleGroupdto.setWardImport(address);
						}
					}
				}
				index = hashSampleGroupColumnConfig.get("district");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						String address = String.valueOf((int) currentCell.getNumericCellValue());
						dto.setDistrictImport(address);
						if(SampleGroupdto != null) {
							SampleGroupdto.setDistrictImport(address);
						}
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						String address = currentCell.getStringCellValue().trim();
						dto.setDistrictImport(address);
						if(SampleGroupdto != null) {
							SampleGroupdto.setDistrictImport(address);
						}
					}
				}

				index = hashSampleGroupColumnConfig.get("province");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						String address = String.valueOf((int) currentCell.getNumericCellValue());
						dto.setProvinceImport(address);
						if(SampleGroupdto != null) {
							SampleGroupdto.setProvinceImport(address);
						}
						
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						String address = currentCell.getStringCellValue().trim();
						dto.setProvinceImport(address);
						if(SampleGroupdto != null) {
							SampleGroupdto.setProvinceImport(address);
						}
					}
				}
				
				index = hashSampleGroupColumnConfig.get("suspectedLevel");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					String value = "";
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						value = String.valueOf((int) currentCell.getNumericCellValue());
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						value = currentCell.getStringCellValue().trim();
					}
					if (value != null && StringUtils.hasText(value)) {
						dto.setImportSuspectedLevel(value);
					}
				}
				
				index = hashSampleGroupColumnConfig.get("phoneNumber");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					String value = "";
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						value = String.valueOf((int) currentCell.getNumericCellValue());
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						value = currentCell.getStringCellValue().trim();
					}
					if (value != null && StringUtils.hasLength(value)) {
						dto.setPersonPhoneNumber(value);
					}
				}
				
				index = hashSampleGroupColumnConfig.get("sampleCollectOrg");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					String value = "";
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						value = String.valueOf((int) currentCell.getNumericCellValue());
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						value = currentCell.getStringCellValue().trim();
					}
					if (value != null && StringUtils.hasText(value)) {
						dto.setImportSampleCollectOrg(value);
					}
				}
				
				index = hashSampleGroupColumnConfig.get("labTest");
				if (index != null) {
					currentCell = currentRow.getCell(index);
					String value = "";
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						value = String.valueOf((int) currentCell.getNumericCellValue());
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						value = currentCell.getStringCellValue().trim();
					}
					if (value != null && StringUtils.hasText(value)) {
						dto.setImportLabTest(value);
					}
				}
				
//				CheckValue check = checkErrorImport(dto);
//				if (check != null && check.isStopReadFile()) {
//				    break;
//				}
//				if (check != null && !check.isResult()) {
//					text += String.format(NCoVConstant.ERROR_IMPORT_ROW_INDEX, dto.getIndexImport()) + check.getError() + System.lineSeparator();
//					dto.setDetailsError(check.getError());
//					listDataError.add(dto);
//				}
				listData.add(dto);
				if(SampleGroupdto != null) {
					listSampleGroup.add(SampleGroupdto);
				}
			}
//			rowIndex++;
		}
		if (listDataError != null && listDataError.size() > 0) {
			result.setSuccess(false);
			result.setText(text);
			result.setListData(listDataError);
		}
		else {
			result.setSuccess(true);
			result.setListData(listData);
			result.setListSampleGroup(listSampleGroup);
		}
		return result;
	}
	
	
	private static Date getHourFromCurrentCell(Cell currentCell) {
		Calendar calendar = Calendar.getInstance();
		Date date = null;
		try {
			if (DateUtil.isCellDateFormatted(currentCell)) {
				calendar.setTime(currentCell.getDateCellValue());
				date = calendar.getTime();
			}
		} catch (Exception ex) {
			System.out.print(ex.getMessage());
			if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
					&& currentCell.getStringCellValue() != null) {
				String sampleDate = currentCell.getStringCellValue().trim();
				try {
					date = new SimpleDateFormat("HH:mm").parse(sampleDate);
				} catch (Exception ex2) {
					System.out.print(ex2.getMessage());
					try {
						date = new SimpleDateFormat("HH-mm").parse(sampleDate);
					} catch (Exception ex3) {
						try {
							date = new SimpleDateFormat("HH-mm:ss").parse(sampleDate);
						} catch (Exception ex4) {
							System.out.print(ex3.getMessage());
						}
					}
				}
			}
		}
		return date;
	}

	private static Date formatImportDOB(Cell currentCell) {
		Calendar calendar = Calendar.getInstance();
		Integer namSinh = null;
		Date date = null;
		if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
			try {
				namSinh = (int) currentCell.getNumericCellValue();
			} catch (Exception e2) {
			}
		} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
				&& currentCell.getStringCellValue() != null) {
			try {
				namSinh = Integer.parseInt(currentCell.getStringCellValue().trim());
			} catch (Exception e2) {
				try {
					date = new SimpleDateFormat("yyyy").parse(currentCell.getStringCellValue().trim());
				} catch (Exception e3) {
					try {
						date = new SimpleDateFormat("dd/MM/yyyy").parse(currentCell.getStringCellValue().trim());
					} catch (ParseException e4) {
					}
				}
			}
		}
		
		if (namSinh != null && namSinh>0) {
			try {
				if (namSinh < 1900) {//điền tuổi
					namSinh = calendar.get(Calendar.YEAR) - namSinh;
				}
				date = new SimpleDateFormat("yyyy").parse(String.valueOf(namSinh));
			} catch (Exception e2) {
			}
		}
		return date;
	}

	private static Date getDateFromCurrentCell(Cell currentCell) {
		Calendar calendar = Calendar.getInstance();
		Date date = null;
		try {
			if (DateUtil.isCellDateFormatted(currentCell)) {
				calendar.setTime(currentCell.getDateCellValue());
				date = calendar.getTime();
			}
		} catch (Exception ex) {
			System.out.print(ex.getMessage());
			if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
					&& currentCell.getStringCellValue() != null) {
				String sampleDate = currentCell.getStringCellValue().trim();
				try {
					date = new SimpleDateFormat("yyyy-MM-dd").parse(sampleDate);
				} catch (Exception ex2) {
					System.out.print(ex2.getMessage());
					try {
						date = new SimpleDateFormat("dd/MM/yyyy").parse(sampleDate);
					} catch (Exception ex3) {
						System.out.print(ex3.getMessage());
					}
				}
			}
		}
		return date;
	}

	private static String ConvertToGender(String gender) {
		if (gender != null && StringUtils.hasText(gender)) {
			if (gender.trim().toLowerCase().equals(NCoVConstant.MALE.toLowerCase())) {
				return Enums.Gender.male.getName();
			}
			else if (gender.trim().toLowerCase().equals(NCoVConstant.FEMALE.toLowerCase())) {
				return Enums.Gender.female.getName();
			}
			else {
				return Enums.Gender.other.getName();
			}
		}
		return null;
	}

	private static String ConvertToSampleStatus(String ketQua) {
		if (ketQua != null && StringUtils.hasText(ketQua)) {
			if (ketQua.trim().toLowerCase().equals(NCoVConstant.SAMPLE_RESULT_POSITIVE.toLowerCase())) {
				return Enums.SampleResultEnum.Positive.name();
			}
			else if (ketQua.trim().toLowerCase().equals(NCoVConstant.SAMPLE_RESULT_NEGATIVE.toLowerCase())) {
				return Enums.SampleResultEnum.Negative.name();
			}
			else if (ketQua.trim().toLowerCase().equals(NCoVConstant.SAMPLE_RESULT_CHECKING.toLowerCase())) {
				return Enums.SampleResultEnum.Checking.name();
			}
		}
		return null;
	}

	private static String seperateText(String errorText) {
		if (errorText != null && StringUtils.hasText(errorText)) {
			errorText += "--";
		}
		return errorText;
	}

	private static CheckValue checkErrorImport(SampleDto dto) {
		CheckValue result = new CheckValue();
		String errorText = "";
		boolean stopReadFile = false;
		int numberError = 0;
		if (dto != null) {
			//1.check mã
			if (dto.getCode() == null || !StringUtils.hasText(dto.getCode())) {
				errorText += NCoVConstant.ERROR_IMPORT_MISSING_CODE;
				numberError++;
			}
			//2.check Họ tên
			if (dto.getPersonName() == null || !StringUtils.hasText(dto.getPersonName())) {
				errorText = seperateText(errorText);
				errorText += NCoVConstant.ERROR_IMPORT_MISSING_PERSON_NAME;
				numberError++;
			}
			//3.check Tuổi
			if (dto.getPersonBirthDate() == null || !StringUtils.hasText(dto.getPersonBirthDate().toString())) {
				errorText = seperateText(errorText);
				errorText += NCoVConstant.ERROR_IMPORT_MISSING_PERSON_BOD;
				numberError++;
			}
			//4.check Giới Tính
			if (dto.getPersonGender() == null || !StringUtils.hasText(dto.getPersonGender())) {
				errorText = seperateText(errorText);
				errorText += NCoVConstant.ERROR_IMPORT_MISSING_PERSON_GENDER;
				numberError++;
			}
			//5.check Điện thoai
			if (dto.getPersonPhoneNumber() == null || !StringUtils.hasText(dto.getPersonPhoneNumber())) {
				errorText = seperateText(errorText);
				errorText += NCoVConstant.ERROR_IMPORT_MISSING_PERSON_PHONE_NUMBER;
				numberError++;
			}
			//6.check Địa chi cụ thể
			if (dto.getDetailAddress() == null || !StringUtils.hasText(dto.getDetailAddress())) {
				errorText = seperateText(errorText);
				errorText += NCoVConstant.ERROR_IMPORT_MISSING_PERSON_DETAIL_ADDRESS;
				numberError++;
			}
			//7.check Ngày tiếp xúc cuối cùng
			if (dto.getLastContactDate() == null || !StringUtils.hasText(dto.getLastContactDate().toString())) {
				errorText = seperateText(errorText);
				errorText += NCoVConstant.ERROR_IMPORT_MISSING_PERSON_LAST_CONTACT_DATE;
				numberError++;
			}
			//8.check yếu tố nguy cơ 
			if (dto.getImportEpidemiologicalFactors() == null || !StringUtils.hasText(dto.getImportEpidemiologicalFactors())) {
				errorText = seperateText(errorText);
				errorText += NCoVConstant.ERROR_IMPORT_MISSING_PERSON_EPIDEMIOLOGICAL_FACORS;
				numberError++;
			}
			//9.check Ngày lấy mẫu
			if (dto.getSampleDate() == null || !StringUtils.hasText(dto.getSampleDate().toString())) {
				errorText = seperateText(errorText);
				errorText += NCoVConstant.ERROR_IMPORT_MISSING_SAMPLE_DATE;
				numberError++;
			}
			//10.check Ngày gửi mẫu
			if (dto.getShipDate() == null || !StringUtils.hasText(dto.getShipDate().toString())) {
				errorText = seperateText(errorText);
				errorText += NCoVConstant.ERROR_IMPORT_MISSING_SHIP_DATE;
				numberError++;
			}
			//11.check Nơi xét nghiệm
			if (dto.getImportLabTest() == null || !StringUtils.hasText(dto.getImportLabTest().toString())) {
				errorText = seperateText(errorText);
				errorText += NCoVConstant.ERROR_IMPORT_MISSING_LAB_TEST;
				numberError++;
			}

			//check Kết quả xét nghiệm
			/*
			 * if (dto.getSampleResult() == null ||
			 * !StringUtils.hasText(dto.getSampleResult())) { if (errorText != null &&
			 * StringUtils.hasText(errorText)) { errorText += "----"; } errorText +=
			 * NCoVConstant.ERROR_IMPORT_MISSING_SAMPLE_RESULT; }
			 */
			
			if (numberError == 11) {
				//Nếu số lỗi = tất cả các lỗi cần check thì dừng k đọc file nữa
				result.setStopReadFile(true);
			}
			else {
				if (errorText != null && StringUtils.hasText(errorText)) {
					result.setError(String.format(NCoVConstant.ERROR_IMPORT_MISSING,errorText));
				}
				else {
					result.setResult(true);
				}
			}
		}
		return result;
	}

	
}

class CheckValue {
	public String error;
	public boolean result = false;
	public boolean stopReadFile = false;
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	
	public boolean isStopReadFile() {
		return stopReadFile;
	}
	public void setStopReadFile(boolean stopReadFile) {
		this.stopReadFile = stopReadFile;
	}
	public CheckValue() {
		super();
	}
	
}