package com.trans.transpiladorCobolJava.main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ArquivoEscrita {

	FileWriter arquivoJava;
	PrintWriter gravaCodigo;
	
	public void abreArquivo(String string) throws IOException {
		arquivoJava = new FileWriter("java\\" + string);
		gravaCodigo = new PrintWriter(arquivoJava);
		
	}

	public void escreveLinha(String escreveArquivo) {
		gravaCodigo.println(escreveArquivo);
	}

	public void fechaArquivo() {
		gravaCodigo.close();
	}

}
