package com.trans.transpiladorCobolJava.main;

public enum Divisoes {
	IDENTIFICATION_DIVISION("IDENTIFICATION DIVISION"), 
	ENVIRONMENT_DIVISION("ENVIRONMENT DIVISION"), 
	DATA_DIVISION("DATA DIVISION"), 
	PROCEDURE_DIVISION("PROCEDURE DIVISION"),
	OUTRO("Erro na divisao");
	
	String descricao;
	
	public String getDescricao() {
		return descricao;
	}

	Divisoes (String descricao){
		this.descricao = descricao;
	}

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
	
	public static Divisoes encontraDivisao(String divisaoProcurada) {
		for(Divisoes divisao : Divisoes.values()) {
			if(divisao.getDescricao().equals(divisaoProcurada)) {
				return divisao;
			}
		}
		return Divisoes.OUTRO;
	}
	
}
