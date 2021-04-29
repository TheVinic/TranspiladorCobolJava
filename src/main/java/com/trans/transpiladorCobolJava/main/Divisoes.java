package com.trans.transpiladorCobolJava.main;

public enum Divisoes {
	IDENTIFICATION_DIVISION("IDENTIFICATION DIVISION"), 
	ENVIRONMENT_DIVISION("ENVIRONMENT DIVISION"), 
	DATA_DIVISION("DATA DIVISION"), 
	PROCEDURE_DIVISION("PROCEDURE DIVISION");
	
	String descricao;
	
	public String getDescricao() {
		return descricao;
	}

	Divisoes (String descricao){
		this.descricao = descricao;
	}

	@Deprecated
	public static boolean fimDivisaoAtual(String palavra) {
		if(palavra == null) {
			return true;
		}
		
		for(Divisoes divisao : Divisoes.values()) {
			if(divisao.getDescricao().equals(palavra)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean acabouDivisaoAtual(String divisaoProcurada) {
		for(Divisoes divisao : Divisoes.values()) {
			if(divisao.getDescricao().contains(divisaoProcurada)) {
				return true;
			}
		}
		return false;
	}
	
}
