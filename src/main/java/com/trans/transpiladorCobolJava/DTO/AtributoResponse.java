package com.trans.transpiladorCobolJava.DTO;

public class AtributoResponse {

	protected String nome;

	protected Integer nivel;

	protected String occurs;

	public AtributoResponse(String nome, Integer nivel, String occurs) {
		this.nome = nome;
		this.nivel = nivel;
		this.occurs = occurs;
	}

	public String getNome() {
		return nome;
	}

	public Integer getNivel() {
		return nivel;
	}

	public String getOccurs() {
		return occurs;
	}
	
}
