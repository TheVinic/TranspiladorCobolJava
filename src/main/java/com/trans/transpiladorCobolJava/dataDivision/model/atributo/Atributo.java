package com.trans.transpiladorCobolJava.dataDivision.model.atributo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Atributo {

	protected String nome;

	protected Integer nivel;

	protected List<String> classes;

	protected Integer occurs;

	@Deprecated
	public Atributo() {
	}

	protected Atributo(String nomeAtributo, Integer nivel, List<String> classes, Integer occurs) {
		this.nome = toUpperFistCase(nomeAtributo.replaceAll("-", "_"));
		this.nivel = nivel;
		if (classes != null) {
			this.classes = new ArrayList<String>();
			for (String classe : classes) {
				this.classes.add(toUpperFistCase(classe));
			}
		} else {
			this.classes = classes;
		}
		this.occurs = occurs;
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

	public Integer getOccurs() {
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

	public String getSentencaSet(String valor) {
		return "set" + toUpperFistCase(nome) + "(" + valor + ")";
	}

	public String getSentencaSet() {
		return "set" + toUpperFistCase(nome) + "(" + tipoObjeto() + " " + nome.toLowerCase() + ")";
	}

	protected static String toUpperFistCase(String nome) {
		return (nome == null || nome.isEmpty()) ? null
				: nome.substring(0, 1).toUpperCase() + nome.toLowerCase().substring(1);
	}

	public abstract String escreveVariaveis() throws IOException;

	public abstract String tipoObjeto();
	
	public abstract Integer getComprimento();

}
