package com.trans.transpiladorCobolJava.DTO;

import com.trans.transpiladorCobolJava.dataDivision.DataDivision;
import com.trans.transpiladorCobolJava.identificationDivision.IdentificationDivision;

public class VariaveisResponse {

	private IdentificationDivisionResponse identificationDivisionResponse;

	//private DataDivisionResponse dataDivisionResponse;

	public VariaveisResponse(IdentificationDivision identificationDivision, DataDivision dataDivision) {
		identificationDivisionResponse = identificationDivision.toResponse();
		//dataDivisionResponse = dataDivision.toResponse();
	}

	public IdentificationDivisionResponse getIdentificationDivisionResponse() {
		return identificationDivisionResponse;
	}

}
