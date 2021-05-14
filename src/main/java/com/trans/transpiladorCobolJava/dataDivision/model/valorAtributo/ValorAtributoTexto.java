package com.trans.transpiladorCobolJava.dataDivision.model.valorAtributo;

public class ValorAtributoTexto implements ValorAtributo {

	private String valor;

	public ValorAtributoTexto(String valorAtributo) {
		if (valorAtributo.equals("SPACE") || valorAtributo.equals("SPACES"))
			valor = new String();
		else
			valor = valorAtributo;
	}

	@Override
	public Object getValor() {
		return valor;
	}

	@Override
	public String toString() {
		return "ValorAtributoTexto [valor=" + valor + "]";
	}

}
