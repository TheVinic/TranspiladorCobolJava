package com.trans.transpiladorCobolJava.dataDivision.model.valorAtributo;

public class ValorAtributoNumero implements ValorAtributo{

	private Integer valor;
	
	public ValorAtributoNumero(int valorAtributo) {
		this.valor = valorAtributo;
	}

	@Override
	public Object getValor() {
		return valor;
	}

	@Override
	public String toString() {
		return "ValorAtributoNumero [valor=" + valor + "]";
	}

}
