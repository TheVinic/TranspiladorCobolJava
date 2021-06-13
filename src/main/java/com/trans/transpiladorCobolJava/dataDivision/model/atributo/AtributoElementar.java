package com.trans.transpiladorCobolJava.dataDivision.model.atributo;

import java.math.BigDecimal;
import java.util.List;

import com.trans.transpiladorCobolJava.dataDivision.SecoesDataDivision;
import com.trans.transpiladorCobolJava.dataDivision.model.TipoAtributo;
import com.trans.transpiladorCobolJava.dataDivision.model.valorAtributo.ValorAtributo;
import com.trans.transpiladorCobolJava.dataDivision.model.valorAtributo.ValorAtributoDecimal;
import com.trans.transpiladorCobolJava.dataDivision.model.valorAtributo.ValorAtributoNumero;
import com.trans.transpiladorCobolJava.dataDivision.model.valorAtributo.ValorAtributoTexto;

public class AtributoElementar extends Atributo {

	private Integer comprimento;

	private Integer comprimentoDecimal;

	private TipoAtributo tipoAtributo;

	private ValorAtributo valor;

	public AtributoElementar(String nomeAtributo, Integer nivel, Integer comprimento, Integer comprimentoDecimal,
			TipoAtributo tipoAtributo, String valorAtributo, List<String> classe, String occurs, SecoesDataDivision local) {
		super((nomeAtributo == null) ? null : nomeAtributo.toLowerCase(), nivel, classe, occurs, local);
		this.comprimento = comprimento;
		this.comprimentoDecimal = comprimentoDecimal;
		this.tipoAtributo = tipoAtributo;
		if (valorAtributo != null) {
			switch (tipoAtributo) {
			case CARACTERE:
				valor = new ValorAtributoTexto(valorAtributo);
				break;
			case DECIMAL:
				valor = new ValorAtributoDecimal(
						BigDecimal.valueOf(Double.parseDouble(valorAtributo.replace(",", "."))));
				break;
			case NUMERO:
				if (valorAtributo.equals("ZERO") || valorAtributo.equals("ZEROS")) {
					valor = new ValorAtributoNumero(0);
				} else {
					valor = new ValorAtributoNumero(Integer.parseInt(valorAtributo));
				}
				break;
			}
		}
	}

	public Integer getComprimento() {
		switch(tipoAtributo) {
		case CARACTERE:
		case NUMERO:
			return comprimento;
		case DECIMAL:
			return comprimento + comprimentoDecimal;
		}
		return comprimento;
	}
	
	public String getComprimentoToString() {
		switch(tipoAtributo) {
		case CARACTERE:
			return "\"|%" + comprimento + "d|\"";
		case DECIMAL:
			return "\"|0%" + (comprimento + comprimentoDecimal) + "d|\"";
		case NUMERO:
			return "\"|0%" + comprimento + "d|\"";
		}
		return null;
	}

	public Integer getComprimentoDecimal() {
		return comprimentoDecimal;
	}

	public TipoAtributo getTipoAtributo() {
		return tipoAtributo;
	}

	public Object getValor() {
		return valor.getValor();
	}

	@Override
	public String escreveVariaveis() {
		return "\t" + ((filler == true) ? "final " : "private ") + tipoAtributo.getDescricao() + getStringDeclaraOccurs() + " " + getNome().toLowerCase()
				+ ((valor == null) ? ";" : " = " + valor.getValor() + getStringDeclaraOccurs() + ";");
	}

	@Override
	public String tipoObjeto() {
		return tipoAtributo.getDescricao();
	}

}
