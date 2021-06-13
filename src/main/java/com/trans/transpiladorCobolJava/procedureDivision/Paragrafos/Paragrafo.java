package com.trans.transpiladorCobolJava.procedureDivision.Paragrafos;

import java.util.HashSet;
import java.util.Set;

import com.trans.transpiladorCobolJava.arquivo.Codigo;
import com.trans.transpiladorCobolJava.dataDivision.DataDivision;
import com.trans.transpiladorCobolJava.dataDivision.model.TipoAtributo;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.Atributo;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.AtributoElementar;

public abstract class Paragrafo implements ParagrafoImpl {

	protected Set<String> imports = new HashSet<String>();

	protected static String toLowerFistCase(String nome) {
		return nome.substring(0, 1).toLowerCase() + nome.substring(1);
	}

	protected Atributo encontraIdentificador(Codigo umaSecao, DataDivision dataDivision) {
		if (umaSecao.getInstrucaoLeitura(umaSecao.getPosicaoLeitura() + 1).equals("OF")) {
			Atributo atributo = dataDivision.localizaAtributo(umaSecao.getInstrucaoAtualLeitura(),
					umaSecao.getInstrucaoLeitura(umaSecao.getPosicaoLeitura() + 2));
			umaSecao.setPosicaoLeitura(umaSecao.getPosicaoLeitura() + 3);
			return atributo;
		} else {
			return dataDivision.localizaAtributo(umaSecao.getInstrucaoAtualLeitura());
		}
	}

	public Set<String> escreveImportsParagrago(Set<String> imports) {
		Set<String> imprimir = new HashSet<String>();
		for (String elemento : imports) {
			imprimir.add("import com.trans.transpiladorCobolJava." + elemento + ";");
		}
		return imprimir;
	}

	protected Atributo encontraCriaAtributo(Codigo umaSecao, DataDivision dataDivision) {
		if (umaSecao.getInstrucaoAtualLeitura().isEmpty()) {
			umaSecao.getProximaInstrucaoLeitura();
		}
		String elemento = umaSecao.getInstrucaoAtualLeitura().replace(".", "");
		if (elemento.matches("[0-9]+")) {
			// Tipo númerico
			return (new AtributoElementar(null, null, elemento.length(), null, TipoAtributo.NUMERO, elemento, null,
					null, null));
		} else if (elemento.matches("[0-9]+\\,[0-9]+")) {
			// Tipo decimal
			return (new AtributoElementar(null, null, elemento.length(), null, TipoAtributo.DECIMAL, elemento, null,
					null, null));
		} else if (validaCondicao(elemento)){
			return (new AtributoElementar(null, null, elemento.length(), null, TipoAtributo.CARACTERE, elemento, null,
					null, null));
		} else if (elemento.equals("LESS") || elemento.equals("GREATER") || elemento.equals("OR")) {
			String valor = null;
			switch (elemento) {
			case "OR":
				valor = "||";
				break;
			case "GRATER":
				umaSecao.getProximaInstrucaoLeitura();
				valor = ">";
				break;
			case "LESS":
				umaSecao.getProximaInstrucaoLeitura();
				valor = "<";
				break;
			}
			return (new AtributoElementar(null, null, elemento.length(), null, TipoAtributo.CARACTERE,
					valor, null, null, null));
		} else {
			// Identificador
			return encontraIdentificador(umaSecao, dataDivision);
		}
	}

	private boolean validaCondicao(String elemento) {
		if (elemento.equals("+") || elemento.equals("-") || elemento.equals("*") || elemento.equals("**")
				|| elemento.equals("/") || elemento.equals("(") || elemento.equals(")") || elemento.equals("<") || elemento.equals(">")
				|| elemento.equals("=") || elemento.equals("<=") || elemento.equals(">=")) {
			return true;
		}
		return false;
	}

	public Set<String> getImports() {
		return imports;
	}

	protected String fazTabulacao(Integer nivel) {
		String tabulacao = new String();
		for (int i = 0; i < nivel; i++) {
			tabulacao += "\t";
		}

		return tabulacao;
	}

	public Set<String> escreveImports() {
		return escreveImportsParagrago(imports);
	}

}
