package com.trans.transpiladorCobolJava.dataDivision;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.trans.transpiladorCobolJava.arquivo.ArquivoEscrita;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.AtributoGrupo;


@Component
public class EscritaWorkingStorageSection {

	public String getNomeClasse() {
		return nomeClasse;
	}

	public void setNomeClasse(String nomeClasse) {
		this.nomeClasse = nomeClasse;
	}

	ArquivoEscrita arquivoEscrita = new ArquivoEscrita();
	String nomeClasse = "DadosPrincipais";
	String localArquivo = "model";

	public void escreve(AtributoGrupo atributosWorkingStorage) throws IOException {

		// escrever atributos
		atributosWorkingStorage.escreveImportDataDivision(localArquivo);

		// escrever declaração variaveis
		atributosWorkingStorage.escreveVariaveis();
		
		// escrever Get e Set
		atributosWorkingStorage.escreveGetSet();

		// escrever toTrancode
		atributosWorkingStorage.escreveToString();

		// escrever toObjeto
		atributosWorkingStorage.escreveToObject();
		
		atributosWorkingStorage.escreveRepository();
		
	}

	@Override
	public String toString() {
		return "EscritaWorkingStorageSection [arquivoEscrita=" + arquivoEscrita + ", nomeClasse=" + nomeClasse + "]";
	}

}
