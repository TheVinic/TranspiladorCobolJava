package com.trans.transpiladorCobolJava.dataDivision.model.atributo;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import com.trans.transpiladorCobolJava.dataDivision.model.TipoAtributo;
import com.trans.transpiladorCobolJava.dataDivision.model.valorAtributo.ValorAtributo;
import com.trans.transpiladorCobolJava.dataDivision.model.valorAtributo.ValorAtributoDecimal;
import com.trans.transpiladorCobolJava.dataDivision.model.valorAtributo.ValorAtributoNumero;
import com.trans.transpiladorCobolJava.dataDivision.model.valorAtributo.ValorAtributoTexto;

public class AtributoLiteral extends Atributo {

	private TipoAtributo tipoAtributo;

	private ValorAtributo valor;

	public AtributoLiteral(String nomeAtributo, Integer nivel, Integer comprimento, Integer comprimentoDecimal,
			TipoAtributo tipoAtributo, String valorAtributo, List<String> classe) {
		super((nomeAtributo==null) ? null : nomeAtributo.toLowerCase(), nivel, classe);
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
				valor = new ValorAtributoNumero(Integer.parseInt(valorAtributo));
				break;
			}
		}
	}

	public Object getTipoAtributo() {
		return tipoAtributo;
	}

	public Object getValor() {
		return valor.getValor();
	}

	@Override
	public String escreveImportWorkingStorage() throws IOException {
		return null;
	}

	@Override
	@Deprecated
	public String escreveGet() throws IOException {
		return "\n\tpublic " + tipoAtributo.getDescricao() + " " + getSentencaGet() + " {\n\t\treturn this." + getNome()
				+ ";\n\t}";
	}

	@Override
	@Deprecated
	public String escreveSet() {
		return "\n\tpublic void set" + getNome() + "(" + tipoAtributo.getDescricao() + " " + getNome()
				+ ") {\n\t\tthis." + getNome() + " = " + getNome() + ";\n\t}";
	}

	@Override
	@Deprecated
	public String getSentencaGet() {
		return "get" + toUpperFistCase(getNome()) + "()";
	}

	@Override
	@Deprecated
	public String getSentencaSet() {
		return "set" + tipoAtributo.getDescricao() + "(" + tipoAtributo.getDescricao() + " " + getNome().toLowerCase()
				+ ")";
	}

	private static String toUpperFistCase(String nome) {
		return nome.substring(0, 1).toUpperCase() + nome.toLowerCase().substring(1);
	}

	@Override
	@Deprecated
	public String getImport() {
		return "import com.trans.transpiladorCobolJava.model." + getClasses().get(0) + ";";
	}

	@Override
	public String escreveVariaveis() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
