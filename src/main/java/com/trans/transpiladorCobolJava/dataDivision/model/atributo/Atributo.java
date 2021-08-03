package com.trans.transpiladorCobolJava.dataDivision.model.atributo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.trans.transpiladorCobolJava.dataDivision.SecoesDataDivision;

@Component
public abstract class Atributo {

	protected String nome;

	protected Integer nivel;

	protected List<String> classes;

	protected String occurs;

	protected boolean filler;
	
	protected SecoesDataDivision local;

	@Deprecated
	public Atributo() {
	}

	protected Atributo(String nomeAtributo, Integer nivel, List<String> classes, String occurs, SecoesDataDivision local) {
		this.nome = (nomeAtributo != null) ? toUpperFistCase(nomeAtributo.replaceAll("-", "_")) : null;
		this.nivel = nivel;
		if (classes != null) {
			this.classes = new ArrayList<String>();
			for (String classe : classes) {
				this.classes.add(toUpperFistCase(classe.replaceAll("-", "_")));
			}
		} else {
			this.classes = classes;
		}
		this.occurs = occurs;
		this.filler = (nomeAtributo != null && nomeAtributo.startsWith("filler")) ? true : false;
		this.local = local;
	}

	public String getNome() {
		return nome;
	}

	protected Integer getNivel() {
		return nivel;
	}

	public List<String> getClasses() {
		return classes;
	}

	public String getOccurs() {
		return occurs;
	}

	protected String getStringDeclaraOccurs() {
		if (occurs == null) {
			return "";
		} else {
			return "[]";
		}
	}

	protected String getIniciaOccurs() {
		if (occurs == null) {
			return "";
		} else {
			return "[" + occurs + "]";
		}
	}

	public String getClassesSucessoras() {
		String texto = new String();
		texto += classes.get(0) + ".";
		for (int i = 1; i < classes.size(); i++) {
			texto += "get" + classes.get(i) + "().";
		}
		return texto;
	}

	public String escreveGet() throws IOException {

		return "\n\tpublic " + tipoObjeto() + getStringDeclaraOccurs() + " " + getSentencaGet() + " {\n\t\treturn "
				+ nome.toLowerCase() + ";\n\t}";
	}

	public String escreveSet() {
		return "\n\tpublic void set" + toUpperFistCase(nome) + "(" + tipoObjeto() + getStringDeclaraOccurs() + " "
				+ nome.toLowerCase() + ") {\n\t\tthis." + nome.toLowerCase() + " = " + nome.toLowerCase() + ";\n\t}";
	}

	public String getSentencaGet() {
		return "get" + toUpperFistCase(getNome()) + "()";
	}

	public String getSentencaSet(Object valor) {
		return "set" + toUpperFistCase(nome) + "(" + valor + ")";
	}

	public String getSentencaSet() {
		return "set" + toUpperFistCase(nome) + "(" + tipoObjeto() + " " + nome.toLowerCase() + ")";
	}

	protected static String toUpperFistCase(String nome) {
		return (nome == null || nome.isEmpty()) ? null
				: nome.substring(0, 1).toUpperCase() + nome.toLowerCase().substring(1);
	}

	protected static String toLowerFistCase(String nome) {
		return nome.substring(0, 1).toLowerCase() + nome.substring(1);
	}

	public String getStringEscritaPorTipo() {
		if (this instanceof AtributoElementar) {
			if (getNome() == null || getNome().isEmpty()) {
				return ((AtributoElementar) this).getValor().toString();
			} else {
				if (this instanceof AtributoElementar) {
					switch (((AtributoElementar) this).getTipoAtributo()) {
					case CARACTERE:
						return "Integer.parseInt(" + toLowerFistCase(getClassesSucessoras()) + getSentencaGet() + ")";
					case DECIMAL:
					case NUMERO:
						return toLowerFistCase(getClassesSucessoras()) + getSentencaGet();
					}
				}
			}
		} else if (this instanceof AtributoItemGrupo) {
			return "Integer.parseInt(" + toLowerFistCase(getClassesSucessoras()) + getSentencaGet() + ".toTrancode())";
		}
		return null;
	}

	public abstract String escreveVariaveis() throws IOException;

	public abstract String tipoObjeto();

	public abstract Integer getComprimento();

	public boolean isFiller() {
		return filler;
	}

	public SecoesDataDivision getLocal() {
		return local;
	}

	public String getImport() {
		return classes.get(0);
	}

	public String getImport2() {
		return (local==null) ? classes.get(0) : local.getLocal() + "." + classes.get(0);
	}

}
