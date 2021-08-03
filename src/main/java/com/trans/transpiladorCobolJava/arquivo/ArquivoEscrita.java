package com.trans.transpiladorCobolJava.arquivo;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ArquivoEscrita {

	private FileWriter arquivoJava;
	private PrintWriter gravaCodigo;
	
	public void abreArquivo(String nomeClasse) throws IOException {
		arquivoJava = new FileWriter("java\\" + nomeClasse);
		gravaCodigo = new PrintWriter(arquivoJava);
		
	}

	public void escreveLinha(String escreveArquivo) {
		if(escreveArquivo != null) {
			gravaCodigo.println(escreveArquivo);
		}
	}

	public void fechaArquivo() {
		gravaCodigo.close();
	}

}
