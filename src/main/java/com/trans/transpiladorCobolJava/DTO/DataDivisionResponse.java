package com.trans.transpiladorCobolJava.DTO;

import com.trans.transpiladorCobolJava.dataDivision.model.atributo.AtributoItemGrupo;

public class DataDivisionResponse {
	
	private AtributoGrupoResponse atributosWorkingStorage;
	private AtributoGrupoResponse atributosLinkageSection;

	public DataDivisionResponse(AtributoItemGrupo atributosWorkingStorage, AtributoItemGrupo atributosLinkageSection) {
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
