package com.globits.covid19.test.dto;

public class TestingStatusDto {
	private String name;
	private Long count;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public TestingStatusDto(String name, Long count) {
		super();
		this.name = name;
		this.count = count;
	}
	
}
