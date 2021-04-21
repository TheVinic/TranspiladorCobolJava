package com.trans.transpiladorCobolJava.dataDivision;

import java.io.IOException;
import java.util.Set;

import com.trans.transpiladorCobolJava.arquivo.ArquivoEscrita;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.Atributo;

public class EscritaWorkingStorageSection {

	ArquivoEscrita arquivoEscrita = new ArquivoEscrita();
	String nomeClasse = "DadosPrincipal";
	
	public void escreve(Set<Atributo> atributosWorkingStorage) throws IOException {

		arquivoEscrita.abreArquivo("model\\" + nomeClasse + ".java");
		arquivoEscrita.escreveLinha("package com.trans.transpiladorCobolJava.model." + nomeClasse + ";\n");
		for (Atributo atributoUnitario : atributosWorkingStorage) {
			arquivoEscrita.escreveLinha(atributoUnitario.escreveImport());
		}
		arquivoEscrita.escreveLinha("\npublic class DadosPrincipais {"); 
		for (Atributo atributoUnitario : atributosWorkingStorage) {
			arquivoEscrita.escreveLinha(atributoUnitario.escreveArquivo());
		}
		arquivoEscrita.escreveLinha("}"); 
		arquivoEscrita.fechaArquivo();
	}

}
