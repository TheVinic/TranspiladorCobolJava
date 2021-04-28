package com.trans.transpiladorCobolJava.procedureDivision.Paragrafos;

public enum PalavrasReservadasDisplayParagrafo {
	WITH("WITH"), NO("NO"), ADVANCING("ADVANCING"), UPON("UPON");
	
	String texto;
	
	PalavrasReservadasDisplayParagrafo(String texto) {
		this.texto = texto;
	}
	
	public String getTexto() {
		return texto;
	}
	
	public static boolean isPresent(String nome) {
		nome = nome.replace(".", "");
		for(PalavrasReservadasDisplayParagrafo palavra : PalavrasReservadasDisplayParagrafo.values()) {
			if(palavra.getTexto().equals(nome)) {
				return true;
			}
		}
		return false;
	}
	
}
