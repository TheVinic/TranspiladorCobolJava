package com.trans.transpiladorCobolJava.dataDivision.model.atributo;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

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
			TipoAtributo tipoAtributo, String valorAtributo, List<String> classe, Integer occurs) {
		super((nomeAtributo == null) ? null : nomeAtributo.toLowerCase(), nivel, classe, occurs);
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
		return comprimento;
	}

	public Integer getComprimentoDecimal() {
		return comprimentoDecimal;
	}

	public Object getTipoAtributo() {
		return tipoAtributo;
	}

	public Object getValor() {
		return valor.getValor();
	}

	@Override
	@Deprecated
	public String escreveImportWorkingStorage() throws IOException {
		return null;
	}

	@Override
	public String escreveVariaveis() {
		return "\tprivate " + tipoAtributo.getDescricao() + getStringDeclaraOccurs() + " " + getNome()
				+ ((valor == null) ? ";" : " = " + valor.getValor() + getStringDeclaraOccurs() + ";");
	}

	@Override
	public String tipoObjeto() {
		return tipoAtributo.getDescricao();
	}

	@Override
	@Deprecated
	public void escreveGetSet() {
		// TODO Auto-generated method stub
		
	}

}
