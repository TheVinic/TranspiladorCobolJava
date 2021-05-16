package com.trans.transpiladorCobolJava.dataDivision;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.trans.transpiladorCobolJava.arquivo.ArquivoEscrita;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.AtributoGrupo;

@Component
public class EscritaLinkageSection {

	public String getNomeClasse() {
		return nomeClasse;
	}

	public void setNomeClasse(String nomeClasse) {
		this.nomeClasse = nomeClasse;
	}

	ArquivoEscrita arquivoEscrita = new ArquivoEscrita();
	String nomeClasse = "DadosPrincipaisDTO";
	String localArquivo = "DTO";

	public void escreve(AtributoGrupo atributosLinkiageSection) throws IOException {

		// escrever atributos
		atributosLinkiageSection.escreveImportDataDivision(localArquivo);

		// escrever declaração variaveis
		atributosLinkiageSection.escreveVariaveis();
		
		// escrever Get e Set
		atributosLinkiageSection.escreveGetSet();

		// escrever toTrancode
		atributosLinkiageSection.escreveToString();

		// escrever toObjeto
		atributosLinkiageSection.escreveToObject();
	}
}
