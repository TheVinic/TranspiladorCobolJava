package com.trans.transpiladorCobolJava.dataDivision;

public enum SecoesDataDivision {
	FILESECTION("FILE SECTION", ""), 
	WORKINGSTORAGESECTION("WORKING-STORAGE SECTION", "model"), 
	LOCALSTORAGESECTION("LOCAL-STORAGE SECTION", ""), 
	LINKAGESECTION("LINKAGE SECTION", "DTO");
	
	String descricao;
	String local;
	
	public String getDescricao() {
		return descricao;
	}

	public String getLocal() {
		return local;
	}

	SecoesDataDivision (String descricao, String local){
		this.descricao = descricao;
		this.local = local;
	}
	
	public static Boolean acabouParagrafoAtual(String paragrafoProcurado) {
		for(SecoesDataDivision paragrafo : SecoesDataDivision.values()) {
			if(paragrafo.getDescricao().equals(paragrafoProcurado)) {
				return true;
			}
		}
		return false;
	}
}
