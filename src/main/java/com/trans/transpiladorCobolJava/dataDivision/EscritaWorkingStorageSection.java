package com.trans.transpiladorCobolJava.dataDivision;

import java.io.IOException;

import com.trans.transpiladorCobolJava.arquivo.ArquivoEscrita;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.AtributoGrupo;

public class EscritaWorkingStorageSection {

	public String getNomeClasse() {
		return nomeClasse;
	}

	public void setNomeClasse(String nomeClasse) {
		this.nomeClasse = nomeClasse;
	}

	ArquivoEscrita arquivoEscrita = new ArquivoEscrita();
	String nomeClasse = "DadosPrincipais";

	public void escreve(AtributoGrupo atributosWorkingStorage) throws IOException {

		// escrever atributos
		atributosWorkingStorage.escreveImportWorkingStorage();

		// escrever declaração variaveis
		atributosWorkingStorage.escreveVariaveis();
		
		// escrever Get e Set
		atributosWorkingStorage.escreveGetSet();

		// escrever toTrancode
		atributosWorkingStorage.escreveToString();

		// escrever toObjeto
		atributosWorkingStorage.escreveToObject();
	}

	@Override
	public String toString() {
		return "EscritaWorkingStorageSection [arquivoEscrita=" + arquivoEscrita + ", nomeClasse=" + nomeClasse + "]";
	}

}
