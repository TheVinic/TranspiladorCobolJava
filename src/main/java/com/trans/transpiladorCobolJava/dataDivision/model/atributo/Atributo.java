package com.trans.transpiladorCobolJava.dataDivision.model.atributo;

import java.io.IOException;
import java.util.List;

public abstract class Atributo{

	protected String nome;

	protected Integer nivel;
	
	private List<String> classes;

	@Deprecated
	public Atributo() {	}
	
	protected Atributo(String nomeAtributo, Integer nivel, List<String> classe) {
		this.nome = nomeAtributo.replaceAll("-", "_");
		this.nivel = nivel;
		this.classes = classe;
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

	public String getClassesSucessoras() {
		String texto = new String();
		texto += classes.get(0) + ".";
		for(int i = 1; i<classes.size(); i++) {
			texto += "get" + classes.get(i) + "().";
		}
		return texto;
	}

	public abstract String escreveVariaveis() throws IOException;

	public abstract String escreveImportWorkingStorage() throws IOException;

	public abstract String getImport();

	public abstract String escreveGet() throws IOException;

	public abstract String escreveSet();

	public abstract String getSentencaGet();

	public abstract String getSentencaSet();
	
	public abstract Object getValor();

	public String getSentencaSet(String valor) {
		// TODO Auto-generated method stub
		return null;
	}

}
