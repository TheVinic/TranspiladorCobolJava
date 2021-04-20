package com.trans.transpiladorCobolJava.dataDivision.model.valorAtributo;

import java.math.BigDecimal;

import com.trans.transpiladorCobolJava.dataDivision.model.ValorAtributo;

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
