package com.app.testbancasella.dto;

public class TypeDto {

	private String enumeration;
	private String value;

	public TypeDto(String enumeration, String value) {
		this.enumeration = enumeration;
		this.value = value;
	}

	public TypeDto() {
	}

	public String getEnumeration() {
		return enumeration;
	}

	public void setEnumeration(String enumeration) {
		this.enumeration = enumeration;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
