package com.app.testbancasella.dto;

import java.io.Serializable;

public class LegalPersonBeneficiaryDto implements Serializable {

	private String fiscalCode;
	private String legalRepresentativeFiscalCode;

	public LegalPersonBeneficiaryDto(String fiscalCode, String legalRepresentativeFiscalCode) {
		this.fiscalCode = fiscalCode;
		this.legalRepresentativeFiscalCode = legalRepresentativeFiscalCode;
	}

	public LegalPersonBeneficiaryDto() {
	}

	public String getFiscalCode() {
		return fiscalCode;
	}

	public void setFiscalCode(String fiscalCode) {
		this.fiscalCode = fiscalCode;
	}

	public String getLegalRepresentativeFiscalCode() {
		return legalRepresentativeFiscalCode;
	}

	public void setLegalRepresentativeFiscalCode(String legalRepresentativeFiscalCode) {
		this.legalRepresentativeFiscalCode = legalRepresentativeFiscalCode;
	}

}
