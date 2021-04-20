package com.trans.transpiladorCobolJava.arquivo;

public class Palavra {

	String palavra;
	
	char ultimoCaractere;

	public Palavra(String textoLido, char caractere) {
		this.palavra = textoLido;
		this.ultimoCaractere = caractere;
	}

	public String getPalavra() {
		return palavra;
	}

	public char getUltimoCaractere() {
		return ultimoCaractere;
	}
	
	public Integer lenghtPalavra() {
		return palavra.length();
	}

	public void setPalavra(String palavra) {
		this.palavra = palavra;
	}
	
}
