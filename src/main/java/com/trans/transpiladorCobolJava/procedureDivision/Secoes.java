package com.trans.transpiladorCobolJava.procedureDivision;

import java.util.ArrayList;

import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.Paragrafo;

public class Secoes {
	String nome;
	ArrayList<Paragrafo> paragrafos;

	public Secoes(String nome, ArrayList<Paragrafo> paragrafos) {
		this.nome = nome;
		this.paragrafos = paragrafos;
	}

	public String getNome() {
		return nome;
	}

	public ArrayList<Paragrafo> getParagrafos() {
		return paragrafos;
	}

}
