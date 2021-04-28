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
			TipoAtributo tipoAtributo, String valorAtributo, List<String> classe) {
		super((nomeAtributo == null) ? null : nomeAtributo.toLowerCase(), nivel, classe);
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
	public String toString() {
		return "\n[[nivel=" + getNivel() + ", nome=" + getNome() + ", comprimento=" + comprimento
				+ ", comprimentoDecimal=" + comprimentoDecimal + ", tipoAtributo=" + tipoAtributo + ", valor=" + valor
				+ "]";
	}

	@Override
	public String escreveVariaveis() {
		return "\tprivate " + tipoAtributo.getDescricao() + " " + getNome()
				+ ((valor == null) ? ";" : " = " + valor.getValor() + ";");
	}

	@Override
	public String escreveImportWorkingStorage() throws IOException {
		return null;
	}

	@Override
	public String escreveGet() throws IOException {
		return "\n\tpublic " + tipoAtributo.getDescricao() + " " + getSentencaGet() + " {\n\t\treturn this." + getNome()
				+ ";\n\t}";
	}

	@Override
	public String escreveSet() {
		return "\n\tpublic void set" + getNome() + "(" + tipoAtributo.getDescricao() + " " + getNome()
				+ ") {\n\t\tthis." + getNome() + " = " + getNome() + ";\n\t}";
	}

	@Override
	public String getSentencaGet() {
		return "get" + toUpperFistCase(getNome()) + "()";
	}

	@Override
	public String getSentencaSet() {
		return "set" + getNome() + "(" + tipoAtributo.getDescricao() + " " + getNome().toLowerCase() + ")";
	}

	@Override
	public String getSentencaSet(String valor) {
		return "set" + toUpperFistCase(getNome()) + "(" + valor + ")";
	}

	private static String toUpperFistCase(String nome) {
		return nome.substring(0, 1).toUpperCase() + nome.toLowerCase().substring(1);
	}

	@Override
	@Deprecated
	public String getImport() {
		return "import com.trans.transpiladorCobolJava.model." + getClasses().get(0) + ";\n";
	}

}
