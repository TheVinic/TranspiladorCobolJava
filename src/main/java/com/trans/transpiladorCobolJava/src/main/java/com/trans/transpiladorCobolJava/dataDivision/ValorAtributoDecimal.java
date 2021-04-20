package com.trans.transpiladorCobolJava.dataDivision;

import java.math.BigDecimal;

public class ValorAtributoDecimal implements ValorAtributo{

	private BigDecimal valor;
	
	public ValorAtributoDecimal(BigDecimal valorAtributo) {
		valor = valorAtributo;
	}

	@Override
	public Object getValor() {
		return valor;
	}

	@Override
	public String toString() {
		return "ValorAtributoDecimal [valor=" + valor + "]";
	}

}
