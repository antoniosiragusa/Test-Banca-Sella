package com.app.testbancasella.dto;

import java.io.Serializable;

public class ContoDto implements Serializable {

	private String accountCode;
	private String bicCode;

	public ContoDto(String accountCode, String bicCode) {
		this.accountCode = accountCode;
		this.bicCode = bicCode;
	}

	public ContoDto() {
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getBicCode() {
		return bicCode;
	}

	public void setBicCode(String bicCode) {
		this.bicCode = bicCode;
	}

}
