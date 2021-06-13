package com.trans.transpiladorCobolJava.identificationDivision;

public enum ParagrafosIdentificationDivision {
	PROGRAMID("PROGRAM-ID"), 
	AUTHOR("AUTHOR"), 
	INSTALLATION("INSTALLATION"), 
	DATAWRITTEN("DATA-WRITTEN"), 
	DATACOMPILED("DATE-COMPILED"), 
	SECURITY("SECURTIY"),
	OUTRO("Outros");
	
	String descricao;
	
	public String getDescricao() {
		return descricao;
	}

	ParagrafosIdentificationDivision (String descricao){
		this.descricao = descricao;
	}
	
	public static ParagrafosIdentificationDivision encontraParagrafo(String paragrafoProcurado) {
		for(ParagrafosIdentificationDivision paragrafo : ParagrafosIdentificationDivision.values()) {
			if(paragrafo.getDescricao().equals(paragrafoProcurado)) {
				return paragrafo;
			}
		}
		return ParagrafosIdentificationDivision.OUTRO;
	}
}
