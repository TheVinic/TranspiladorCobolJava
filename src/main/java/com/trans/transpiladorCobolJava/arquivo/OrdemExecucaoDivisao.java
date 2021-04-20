package com.trans.transpiladorCobolJava.arquivo;

public class OrdemExecucaoDivisao {

	Integer identificationDivision = 0;
	Integer dataDivision = 0;
	Integer environmentDivision = 0;
	Integer procedureDivision = 0;
	
	public Integer getIdentificationDivision() {
		return identificationDivision;
	}
	
	public void setIdentificationDivision(Integer identificationDivision) {
		this.identificationDivision = identificationDivision;
	}
	
	public Integer getDataDivision() {
		return dataDivision;
	}
	
	public void setDataDivision(Integer dataDivision) {
		this.dataDivision = dataDivision;
	}
	
	public Integer getEnvironmentDivision() {
		return environmentDivision;
	}
	
	public void setEnvironmentDivision(Integer environmentDivision) {
		this.environmentDivision = environmentDivision;
	}
	
	public Integer getProcedureDivision() {
		return procedureDivision;
	}
	
	public void setProcedureDivision(Integer procedureDivision) {
		this.procedureDivision = procedureDivision;
	}
	
	public boolean naoPassouIdentification() {
		return (identificationDivision == 0) ? true : false;
	}
	
	public boolean naoPassouEnvironment() {
		return (environmentDivision == 0) ? true : false;
	}
	
	public boolean naoPassouData() {
		return (dataDivision == 0) ? true : false;
	}
	
	public boolean naoPassouProcedure() {
		return (procedureDivision == 0) ? true : false;
	}
	
}
