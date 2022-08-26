package com.app.testbancasella.dto;

import java.io.Serializable;

public class Creditore implements Serializable {

	private String name;
	private ContoDto account;
	private IndirizzoDto address;

	public Creditore(String name, ContoDto account, IndirizzoDto address) {
		this.name = name;
		this.account = account;
		this.address = address;
	}

	public Creditore() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ContoDto getAccount() {
		return account;
	}

	public void setAccount(ContoDto account) {
		this.account = account;
	}

	public IndirizzoDto getAddress() {
		return address;
	}

	public void setAddress(IndirizzoDto address) {
		this.address = address;
	}

}
