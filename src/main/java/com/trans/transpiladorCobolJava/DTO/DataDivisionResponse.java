package com.trans.transpiladorCobolJava.DTO;

import com.trans.transpiladorCobolJava.dataDivision.model.atributo.AtributoGrupo;

public class DataDivisionResponse {
	
	private AtributoGrupoResponse atributosWorkingStorage;
	private AtributoGrupoResponse atributosLinkageSection;

	public DataDivisionResponse(AtributoGrupo atributosWorkingStorage, AtributoGrupo atributosLinkageSection) {
		this.atributosWorkingStorage = atributosWorkingStorage.toResponse();
		this.atributosLinkageSection = atributosLinkageSection.toResponse();
	}

	public AtributoGrupoResponse getAtributosWorkingStorage() {
		return atributosWorkingStorage;
	}

	public AtributoGrupoResponse getAtributosLinkageSection() {
		return atributosLinkageSection;
	}
	
}
