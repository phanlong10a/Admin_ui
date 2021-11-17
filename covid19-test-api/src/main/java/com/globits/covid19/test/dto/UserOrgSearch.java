package com.globits.covid19.test.dto;

import java.util.UUID;

public class UserOrgSearch extends SearchDto{
	private UUID idHealthOrg;

	public UUID getIdHealthOrg() {
		return idHealthOrg;
	}

	public void setIdHealthOrg(UUID idHealthOrg) {
		this.idHealthOrg = idHealthOrg;
	}
	
	
}
