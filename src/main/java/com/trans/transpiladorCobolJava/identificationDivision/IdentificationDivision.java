package com.trans.transpiladorCobolJava.identificationDivision;

import lombok.Getter;

public class IdentificationDivision {
	@Getter private String programId;
	@Getter private String author;
	@Getter private String installation;
	@Getter private String dataWritten;
	@Getter private String dateCompiled;
	@Getter private String security;
	
	public void popula(String codigoCobol) {

		System.out.println("Inicio da IDENTIFICATION DIVISION");
		
		if(codigoCobol.isEmpty()) {
			System.out.println("IDENTIFICATION DIVISION vazia.");
			// TODO erro
		}
		
		String[] paragrafos = codigoCobol.split("\\.\\s");
		
		for(int i = 0; i<paragrafos.length; i++) {
			switch (ParagrafosIdentificationDivision.encontraParagrafo(paragrafos[i])){
			case AUTHOR:
				System.out.println("Author: " + (author = paragrafos[++i]));
				break;
			case DATACOMPILED:
				System.out.println("Data Compiled: " + (dateCompiled = paragrafos[++i]));
				break;
			case DATAWRITTEN:
				System.out.println("Data Written: " + (dataWritten = paragrafos[++i]));
				break;
			case INSTALLATION:
				System.out.println("Installation: " + (installation = paragrafos[++i]));
				break;
			case PROGRAMID:
				System.out.println("Program Id: " + (programId = paragrafos[++i]));
				break;
			case SECURITY:
				System.out.println("Security: " + (security = paragrafos[++i]));
				break;
			}
		}
		System.out.println("Fim da IDENTIFICATION DIVISION");
	}
}
