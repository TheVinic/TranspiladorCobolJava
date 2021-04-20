package com.trans.transpiladorCobolJava.dataDivision;

public enum TipoAtributo {
	CARACTERE("String"),
	NUMERO("Integer"),
	DECIMAL("BigDecimal");
	
	String descricao;
	
	public String getDescricao() {
		return descricao;
	}

	TipoAtributo (String descricao){
		this.descricao = descricao;
	}

}
