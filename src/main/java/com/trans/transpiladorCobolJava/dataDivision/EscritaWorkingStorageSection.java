package com.trans.transpiladorCobolJava.dataDivision;

import java.io.IOException;
import java.util.ArrayList;

import com.trans.transpiladorCobolJava.arquivo.ArquivoEscrita;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.Atributo;

public class EscritaWorkingStorageSection {

	public String getNomeClasse() {
		return nomeClasse;
	}

	public void setNomeClasse(String nomeClasse) {
		this.nomeClasse = nomeClasse;
	}

	ArquivoEscrita arquivoEscrita = new ArquivoEscrita();
	String nomeClasse = "DadosPrincipais";
	
	public void escreve(ArrayList<Atributo> atributosWorkingStorage) throws IOException {

		arquivoEscrita.abreArquivo("model\\" + nomeClasse + ".java");
		arquivoEscrita.escreveLinha("package com.trans.transpiladorCobolJava.model." + nomeClasse + ";\n");
		//For para escrever atributos
		for (Atributo atributoUnitario : atributosWorkingStorage) {
			arquivoEscrita.escreveLinha(atributoUnitario.escreveImportWorkingStorage());
		}
		//For para escrever declaração variaveis
		arquivoEscrita.escreveLinha("\npublic class " + nomeClasse + "{"); 
		for (Atributo atributoUnitario : atributosWorkingStorage) {
			arquivoEscrita.escreveLinha(atributoUnitario.escreveVariaveis());
		}
		//For para escrever Get e Set
		for (Atributo atributoUnitario : atributosWorkingStorage) {
			arquivoEscrita.escreveLinha(atributoUnitario.escreveGet());
			arquivoEscrita.escreveLinha(atributoUnitario.escreveSet());
		}
		arquivoEscrita.escreveLinha("}"); 
		arquivoEscrita.fechaArquivo();
	}

}
