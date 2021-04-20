package com.trans.transpiladorCobolJava.dataDivision;

public enum SecoesDataDivision {
	FILESECTION("FILE SECTION"), 
	WORKINGSTORAGESECTION("WORKING-STORAGE SECTION"), 
	LOCALSTORAGESECTION("LOCAL-STORAGE SECTION"), 
	LINKAGESECTION("LINKAGE SECTION"),
	OUTRO("Outro");
	
	String descricao;
	
	public String getDescricao() {
		return descricao;
	}

	SecoesDataDivision (String descricao){
		this.descricao = descricao;
	}
	
	public static SecoesDataDivision encontraParagrafo(String paragrafoProcurado) {
		for(SecoesDataDivision paragrafo : SecoesDataDivision.values()) {
			if(paragrafo.getDescricao().equals(paragrafoProcurado)) {
				return paragrafo;
			}
		}
		return SecoesDataDivision.OUTRO;
	}
}