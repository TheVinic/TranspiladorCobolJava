package com.trans.transpiladorCobolJava.environmentDivision;

import lombok.Getter;

public enum SecoesEnvironmentDivision {
	CONFIGURATION_SECTION("PROGRAM-ID"), 
	INPUT_OUTPUT_SECTION("AUTHOR");
	
	@Getter String descricao;
	
	SecoesEnvironmentDivision (String descricao){
		this.descricao = descricao;
	}
}
