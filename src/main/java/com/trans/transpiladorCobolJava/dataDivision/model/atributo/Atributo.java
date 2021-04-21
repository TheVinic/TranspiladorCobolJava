package com.trans.transpiladorCobolJava.dataDivision.model.atributo;

import java.io.IOException;

public abstract class Atributo{

	protected String nome;

	protected Integer nivel;

	@Deprecated
	public Atributo() {	}
	
	protected Atributo(String nomeAtributo, Integer nivel) {
		this.nome = nomeAtributo.replaceAll("-", "_");
		this.nivel = nivel;
	}

	protected String getNome() {
		return nome;
	}

	protected Integer getNivel() {
		return nivel;
	}

	@Override
	public String toString() {
		return "Atributo [nome=" + nome + ", nivel=" + nivel + "]";
	}

	public abstract String escreveArquivo() throws IOException;

	protected abstract Object getTipoAtributo();

	public abstract String escreveImport() throws IOException;

}
