package com.globits.covid19.test.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.globits.core.dto.BaseObjectDto;
import com.globits.covid19.test.domain.NCoVAdministrativeUnit;

public class NCoVAdministrativeUnitDto extends BaseObjectDto {

	private String name;
	private String code;
	private Integer level;

	private NCoVAdministrativeUnitDto parent;

	private Set<NCoVAdministrativeUnitDto> subAdministrativeUnitsDto;
	private boolean isDuplicate;
	private String dupName;
	private String dupCode;

	private List<NCoVAdministrativeUnitDto> children;
	private String mapCode;
	private String longitude;// Kinh độ
	private String latitude;// Vĩ độ
	private String gMapX;// Google map X	
	private String gMapY;// Google map Y
	private Double totalAcreage;// Tổng Diện tích
	private UUID parentId;
	
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

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public NCoVAdministrativeUnitDto getParent() {
		return parent;
	}

	public void setParent(NCoVAdministrativeUnitDto parent) {
		this.parent = parent;
	}

	public Set<NCoVAdministrativeUnitDto> getSubAdministrativeUnitsDto() {
		return subAdministrativeUnitsDto;
	}

	public void setSubAdministrativeUnitsDto(Set<NCoVAdministrativeUnitDto> subAdministrativeUnitsDto) {
		this.subAdministrativeUnitsDto = subAdministrativeUnitsDto;
	}

	public boolean isDuplicate() {
		return isDuplicate;
	}

	public void setDuplicate(boolean isDuplicate) {
		this.isDuplicate = isDuplicate;
	}

	public String getDupName() {
		return dupName;
	}

	public void setDupName(String dupName) {
		this.dupName = dupName;
	}

	public String getDupCode() {
		return dupCode;
	}

	public void setDupCode(String dupCode) {
		this.dupCode = dupCode;
	}	

	public List<NCoVAdministrativeUnitDto> getChildren() {
		return children;
	}

	public void setChildren(List<NCoVAdministrativeUnitDto> children) {
		this.children = children;
	}	

	public String getMapCode() {
		return mapCode;
	}

	public void setMapCode(String mapCode) {
		this.mapCode = mapCode;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getgMapX() {
		return gMapX;
	}

	public void setgMapX(String gMapX) {
		this.gMapX = gMapX;
	}

	public String getgMapY() {
		return gMapY;
	}

	public void setgMapY(String gMapY) {
		this.gMapY = gMapY;
	}	

	public Double getTotalAcreage() {
		return totalAcreage;
	}

	public void setTotalAcreage(Double totalAcreage) {
		this.totalAcreage = totalAcreage;
	}

	public UUID getParentId() {
		return parentId;
	}

	public void setParentId(UUID parentId) {
		this.parentId = parentId;
	}

	public NCoVAdministrativeUnitDto() {
		super();
	}
	public NCoVAdministrativeUnitDto(NCoVAdministrativeUnit administrativeUnit,boolean simple, int child) {
		super();
		this.id = administrativeUnit.getId();
		this.name = administrativeUnit.getName();
		this.code = administrativeUnit.getCode();
		this.level = administrativeUnit.getLevel();

//		if(administrativeUnit.getRegion()!=null) {
//			this.regionDto = new FmsRegionDto(administrativeUnit.getRegion());
//		}
		
//		if(administrativeUnit.getParent()!=null) {
//			NCoVAdministrativeUnit parent = administrativeUnit.getParent();
//			parent.setId(administrativeUnit.getParent().getId());
//			parent.setCode(administrativeUnit.getParent().getCode());
//			parent.setName(administrativeUnit.getParent().getName());
//			parent.setLevel(administrativeUnit.getParent().getLevel());
//
//			this.parentDto = new NCoVAdministrativeUnitDto(parent,true);
//		}
	}
	public NCoVAdministrativeUnitDto(NCoVAdministrativeUnit administrativeUnit,boolean simple) {
		super();
		this.id = administrativeUnit.getId();
		this.name = administrativeUnit.getName();
		this.code = administrativeUnit.getCode();
		this.level = administrativeUnit.getLevel();
		this.latitude=administrativeUnit.getLatitude();
		this.longitude=administrativeUnit.getLongitude();
		this.mapCode=administrativeUnit.getMapCode();
		this.gMapX=administrativeUnit.getgMapX();
		this.gMapY=administrativeUnit.getgMapY();
		this.totalAcreage=administrativeUnit.getTotalAcreage();
				
		if(administrativeUnit.getParent()!=null) {
			NCoVAdministrativeUnit parent = administrativeUnit.getParent();
			parent.setId(administrativeUnit.getParent().getId());
			parent.setCode(administrativeUnit.getParent().getCode());
			parent.setName(administrativeUnit.getParent().getName());
			parent.setLevel(administrativeUnit.getParent().getLevel());
			parent.setMapCode(administrativeUnit.getParent().getMapCode());
			parent.setLatitude(administrativeUnit.getParent().getLatitude());
			parent.setLongitude(administrativeUnit.getParent().getLongitude());
			parent.setgMapX(administrativeUnit.getParent().getgMapX());
			parent.setgMapY(administrativeUnit.getParent().getgMapY());
			parent.setTotalAcreage(administrativeUnit.getParent().getTotalAcreage());
			
			this.parent = new NCoVAdministrativeUnitDto(parent,true);
		}
	}
	public NCoVAdministrativeUnitDto(NCoVAdministrativeUnit administrativeUnit) {
		super();
		this.id = administrativeUnit.getId();
		this.name = administrativeUnit.getName();
		this.code = administrativeUnit.getCode();
		this.level = administrativeUnit.getLevel();
		this.mapCode=administrativeUnit.getMapCode();
		this.latitude=administrativeUnit.getLatitude();
		this.longitude=administrativeUnit.getLongitude();
		this.gMapX=administrativeUnit.getgMapX();
		this.gMapY=administrativeUnit.getgMapY();
		this.totalAcreage=administrativeUnit.getTotalAcreage();
		
		if(administrativeUnit.getParent()!=null) {
			NCoVAdministrativeUnit parent = administrativeUnit.getParent();
			parent.setId(administrativeUnit.getParent().getId());
			parent.setCode(administrativeUnit.getParent().getCode());
			parent.setName(administrativeUnit.getParent().getName());
			parent.setLevel(administrativeUnit.getParent().getLevel());
			parent.setMapCode(administrativeUnit.getParent().getMapCode());
			parent.setLatitude(administrativeUnit.getParent().getLatitude());
			parent.setLongitude(administrativeUnit.getParent().getLongitude());
			parent.setgMapX(administrativeUnit.getParent().getgMapX());
			parent.setgMapY(administrativeUnit.getParent().getgMapY());
			parent.setTotalAcreage(administrativeUnit.getParent().getTotalAcreage());
			
			this.parent = new NCoVAdministrativeUnitDto(parent);
			this.parentId =administrativeUnit.getParent().getId();
		}
		
		Set<NCoVAdministrativeUnitDto> administrativeUnitsDtos = new HashSet<NCoVAdministrativeUnitDto>();
		if (administrativeUnit != null && administrativeUnit.getSubAdministrativeUnits() != null
				&& administrativeUnit.getSubAdministrativeUnits().size() > 0) {
			for (NCoVAdministrativeUnit adu : administrativeUnit.getSubAdministrativeUnits()) {
				NCoVAdministrativeUnitDto subAdministrativeUnitsDto = new NCoVAdministrativeUnitDto();
				subAdministrativeUnitsDto.setId(adu.getId());
				subAdministrativeUnitsDto.setCode(adu.getCode());
				subAdministrativeUnitsDto.setName(adu.getName());
				
				subAdministrativeUnitsDto.setMapCode(adu.getMapCode());
				subAdministrativeUnitsDto.setLatitude(adu.getLatitude());
				subAdministrativeUnitsDto.setLongitude(adu.getLongitude());
				subAdministrativeUnitsDto.setgMapX(adu.getgMapX());
				subAdministrativeUnitsDto.setgMapY(adu.getgMapY());
				subAdministrativeUnitsDto.setTotalAcreage(adu.getTotalAcreage());
				
				administrativeUnitsDtos.add(subAdministrativeUnitsDto);

			}
			this.subAdministrativeUnitsDto = administrativeUnitsDtos;
		}
		//this.setChildren(getListChildren(administrativeUnit));
	}
	
	private List<NCoVAdministrativeUnitDto> getListChildren(NCoVAdministrativeUnit unit){
		List<NCoVAdministrativeUnitDto> ret = new ArrayList<NCoVAdministrativeUnitDto>();
		
		if(unit.getSubAdministrativeUnits()!=null &&unit.getSubAdministrativeUnits().size()>0) {
			for(NCoVAdministrativeUnit s : unit.getSubAdministrativeUnits()) {
				NCoVAdministrativeUnitDto sDto = new NCoVAdministrativeUnitDto();
				sDto.setId(s.getId());
				sDto.setCode(s.getCode());
				sDto.setName(s.getName());
				sDto.setLevel(s.getLevel());
				sDto.setChildren(getListChildren(s));
				
				sDto.setMapCode(s.getMapCode());
				sDto.setLatitude(s.getLatitude());
				sDto.setLongitude(s.getLongitude());
				sDto.setgMapX(s.getgMapX());
				sDto.setgMapY(getgMapY());
				sDto.setTotalAcreage(getTotalAcreage());
				
				ret.add(sDto);
			}
		}
		return ret;
	}
	
	public NCoVAdministrativeUnitDto(NCoVAdministrativeUnit administrativeUnit, int chi) {
		super();
		this.id = administrativeUnit.getId();
		this.name = administrativeUnit.getName();
		this.code = administrativeUnit.getCode();
		this.level = administrativeUnit.getLevel();
		this.mapCode = administrativeUnit.getMapCode();
		this.latitude = administrativeUnit.getLatitude();
		this.longitude = administrativeUnit.getLongitude();
		this.gMapX = administrativeUnit.getgMapX();
		this.gMapY = administrativeUnit.getgMapY();
		this.totalAcreage=administrativeUnit.getTotalAcreage();
				
		if(administrativeUnit.getParent()!=null) {
			NCoVAdministrativeUnit parent = administrativeUnit.getParent();
			parent.setId(administrativeUnit.getParent().getId());
			parent.setCode(administrativeUnit.getParent().getCode());
			parent.setName(administrativeUnit.getParent().getName());
			parent.setLevel(administrativeUnit.getParent().getLevel());
			
			parent.setMapCode(administrativeUnit.getParent().getMapCode());
			parent.setLatitude(administrativeUnit.getParent().getLatitude());
			parent.setLongitude(administrativeUnit.getParent().getLongitude());
			parent.setgMapX(administrativeUnit.getParent().getgMapX());
			parent.setgMapY(administrativeUnit.getParent().getgMapY());
			parent.setTotalAcreage(administrativeUnit.getParent().getTotalAcreage());
			
			this.parent = new NCoVAdministrativeUnitDto(parent);
		}
		
		Set<NCoVAdministrativeUnitDto> administrativeUnitsDtos = new HashSet<NCoVAdministrativeUnitDto>();
		if (administrativeUnit != null && administrativeUnit.getSubAdministrativeUnits() != null
				&& administrativeUnit.getSubAdministrativeUnits().size() > 0) {
			for (NCoVAdministrativeUnit adu : administrativeUnit.getSubAdministrativeUnits()) {
				NCoVAdministrativeUnitDto subAdministrativeUnitsDto = new NCoVAdministrativeUnitDto();
				subAdministrativeUnitsDto.setId(adu.getId());
				subAdministrativeUnitsDto.setCode(adu.getCode());
				subAdministrativeUnitsDto.setName(adu.getName());
				administrativeUnitsDtos.add(subAdministrativeUnitsDto);

			}
			this.subAdministrativeUnitsDto = administrativeUnitsDtos;
		}
		this.setChildren(getListChildren(administrativeUnit));
	}
}
