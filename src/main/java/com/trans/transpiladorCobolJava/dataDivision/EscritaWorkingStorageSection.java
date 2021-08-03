package com.trans.transpiladorCobolJava.dataDivision;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.trans.transpiladorCobolJava.arquivo.ArquivoEscrita;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.AtributoItemGrupo;


@Component
public class EscritaWorkingStorageSection {

	private ArquivoEscrita arquivoEscrita = new ArquivoEscrita();
	private String nomeClasse = "DadosPrincipais";
	private String localArquivo = "model";

	public void escreve(AtributoItemGrupo atributosWorkingStorage) throws IOException {
		// escrever atributos
		atributosWorkingStorage.escreveImportDataDivision(localArquivo);
		// escrever declaração variaveis
		atributosWorkingStorage.escreveVariaveis();
		// escrever Get e Set
		atributosWorkingStorage.escreveGetSet();
		// escrever toTrancode
		atributosWorkingStorage.escreveToTrancode();
		// escrever toObjeto
		atributosWorkingStorage.escreveToObject();
		atributosWorkingStorage.escreveRepository();
		
	}

	@Override
	public String toString() {
		return "EscritaWorkingStorageSection [arquivoEscrita=" + arquivoEscrita + ", nomeClasse=" + nomeClasse + "]";
	}

	public String getNomeClasse() {
		return nomeClasse;
	}

	public void setNomeClasse(String nomeClasse) {
		this.nomeClasse = nomeClasse;
	}

}
