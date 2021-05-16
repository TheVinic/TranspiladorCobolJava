package com.trans.transpiladorCobolJava.DTO;

public class AtributoResponse {

	protected String nome;

	protected Integer nivel;

	protected Integer occurs;

	public AtributoResponse(String nome, Integer nivel, Integer occurs) {
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

	public Integer getOccurs() {
		return occurs;
	}
	
}
