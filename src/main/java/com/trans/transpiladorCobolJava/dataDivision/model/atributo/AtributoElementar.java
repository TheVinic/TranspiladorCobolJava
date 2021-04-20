package com.trans.transpiladorCobolJava.dataDivision.model.atributo;

import java.math.BigDecimal;

import com.trans.transpiladorCobolJava.dataDivision.model.TipoAtributo;
import com.trans.transpiladorCobolJava.dataDivision.model.ValorAtributo;
import com.trans.transpiladorCobolJava.dataDivision.model.valorAtributo.ValorAtributoDecimal;
import com.trans.transpiladorCobolJava.dataDivision.model.valorAtributo.ValorAtributoNumero;
import com.trans.transpiladorCobolJava.dataDivision.model.valorAtributo.ValorAtributoTexto;

public class AtributoElementar extends Atributo {

	private Integer comprimento;

	private Integer comprimentoDecimal;

	private TipoAtributo tipoAtributo;

	private ValorAtributo valor;

	public AtributoElementar(String nomeAtributo, Integer nivel, Integer comprimento, Integer comprimentoDecimal,
			TipoAtributo tipoAtributo, String valorAtributo) {
		super(nomeAtributo, nivel);
		this.comprimento = comprimento;
		this.comprimentoDecimal = comprimentoDecimal;
		this.tipoAtributo = tipoAtributo;

		if (valorAtributo != null) {
			switch (tipoAtributo) {
			case CARACTERE:
				valor = new ValorAtributoTexto(valorAtributo);
				break;
			case DECIMAL:
				valor = new ValorAtributoDecimal(BigDecimal.valueOf(Double.parseDouble(valorAtributo.replace(",", "."))));
				break;
			case NUMERO:
				valor = new ValorAtributoNumero(Integer.parseInt(valorAtributo));
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

	public TipoAtributo getTipoAtributo() {
		return tipoAtributo;
	}

	public ValorAtributo getValor() {
		return valor;
	}

	@Override
	public String toString() {
		return "\n[[nivel=" + getNivel() + ", nome=" + getNome() + ", comprimento=" + comprimento
				+ ", comprimentoDecimal=" + comprimentoDecimal + ", tipoAtributo=" + tipoAtributo + ", valor=" + valor
				+ "]";
	}
	
	@Override
	public String escreveArquivo() {
		return "\tprivate " + tipoAtributo.getDescricao() + " " + getNome() + ((valor==null) ? ";" : " = " + valor.getValor() + ";");
	}

}
