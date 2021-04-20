package com.trans.transpiladorCobolJava.dataDivision;

import java.io.IOException;
import java.util.Set;

import com.trans.transpiladorCobolJava.main.ArquivoEscrita;

public class EscritaWorkingStorageSection {

	ArquivoEscrita arquivoEscrita = new ArquivoEscrita();
	
	public void escreve(Set<Atributo> atributosWorkingStorage) throws IOException {

		arquivoEscrita.abreArquivo("DadosPrincipal.java");
		arquivoEscrita.escreveLinha("public class DadosPrincipais {"); 
		for (Atributo atributoUnitario : atributosWorkingStorage) {
			arquivoEscrita.escreveLinha(atributoUnitario.escreveArquivo());
		}
		arquivoEscrita.escreveLinha("}"); 
		arquivoEscrita.fechaArquivo();
	}

}
