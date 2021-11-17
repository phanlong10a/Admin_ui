package com.globits.covid19.test.rest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.globits.covid19.test.dto.*;
import com.globits.covid19.test.service.SuspectedPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.globits.covid19.test.domain.Sample;
import com.globits.covid19.test.service.NCoVAdministrativeUnitService;
import com.globits.covid19.test.service.SampleService;
import com.globits.covid19.test.utilities.ImportExcelUtil;
import com.globits.covid19.test.utilities.NCoVConstant;

@RestController
@RequestMapping("/api/file/import")
public class RestFileImportController {
	@Autowired
	NCoVAdministrativeUnitService administrativeUnitService;
	@Autowired
	SampleService sampleService;
	@Autowired
	SuspectedPersonService susPersonService;

	@Secured({NCoVConstant.ROLE_ADMIN})
	@RequestMapping(value = "/importAdministrativeUnit", method = RequestMethod.POST)
	public ResponseEntity<?> importAdministrativeUnitFile(@RequestParam("uploadfile") MultipartFile uploadfile)
			throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(uploadfile.getBytes());
		List<NCoVAdministrativeUnitDto> listFmsAdministrativeUnit = ImportExcelUtil
				.importAdministrativeUnitFromInputStream(bis);
		administrativeUnitService.saveOrUpdateList(listFmsAdministrativeUnit);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Secured({NCoVConstant.ROLE_ADMIN,NCoVConstant.ROLE_USER})
	@RequestMapping(value = "/importSample", method = RequestMethod.POST)
	public ResponseEntity<?> importSample(@RequestParam("uploadfile") MultipartFile uploadfile)
			throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(uploadfile.getBytes());
		List<SampleDto> listSampleDto = null;
		ImportSampleResultsDto result = ImportExcelUtil
				.importSampleFromInputStream(bis);
		if (result != null) {
			listSampleDto = result.getListData();
			if (result.isSuccess()) {
				result = sampleService.importSample(listSampleDto);
				if (result.isSuccess()) {
					result = sampleService.saveListData(result.getListData());
				}
			}

			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
	}

	@RequestMapping(value = "/importSuspectedPerson", method = RequestMethod.POST)
	public ResponseEntity<?> importSuspectedPerson(@RequestParam("uploadfile") MultipartFile uploadfile)
			throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(uploadfile.getBytes());
		List<SuspectedPersonDto> listSuspectedPersonDto = null;
		ImportSuspectedPersonResultDto result = ImportExcelUtil
				.importSuspectedPersonFromInputStream(bis);
		if (result != null) {
			listSuspectedPersonDto = result.getListData();
			if (result.isSuccess()) {
				result = susPersonService.importSusPerson(listSuspectedPersonDto);
				System.out.println(result.isSuccess());
				if (result.isSuccess()) {
					System.out.println(result.getListData().size());
					result = susPersonService.saveListData(result.getListData());
				}
			}
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

	}
	
	@Secured({NCoVConstant.ROLE_ADMIN,NCoVConstant.ROLE_USER})
	@RequestMapping(value = "/importSampleGroup", method = RequestMethod.POST)
	public ResponseEntity<?> importSampleGroup(@RequestParam("uploadfile") MultipartFile uploadfile)
			throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(uploadfile.getBytes());
		List<SampleDto> listSampleDto = null;
		List<SampleDto> listSampleGroupDto = null;
		ImportSampleResultsDto result = ImportExcelUtil
				.importSampleGroupFromInputStream(bis);
		if (result != null) {
			listSampleDto = result.getListData();
			listSampleGroupDto = result.getListSampleGroup();
			
			if (result.isSuccess()) {
				result = sampleService.importSampleGroup(listSampleDto, listSampleGroupDto);
				if (result.isSuccess()) {
					result.setSuccess(false);
					result = sampleService.saveListData(result.getListData());
				}
				if(result.isSuccess()) {
					result.setSuccess(false);
					result = sampleService.importSampleGroupData(listSampleGroupDto);
					if (result.isSuccess()) {
						result = sampleService.saveListData(result.getListSampleGroup());
					}
				}
			}
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
	}
}
