package com.trans.transpiladorCobolJava.dataDivision.model.atributo;

import java.io.IOException;

public class Atributo {

	protected String nome;

	protected Integer nivel;

	@Deprecated
	public Atributo() {	}
	
	public Atributo(String nomeAtributo, Integer nivel) {
		this.nome = nomeAtributo.toLowerCase();
		this.nivel = nivel;
	}

	public String getNome() {
		return nome;
	}

	public Integer getNivel() {
		return nivel;
	}

	@Override
	public String toString() {
		return "Atributo [nome=" + nome + ", nivel=" + nivel + "]";
	}

	public String escreveArquivo()  throws IOException {
		return null;
	}

}
