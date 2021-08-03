package com.trans.transpiladorCobolJava.dataDivision;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.trans.transpiladorCobolJava.arquivo.ArquivoEscrita;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.AtributoItemGrupo;

@Component
public class EscritaLinkageSection {

	private ArquivoEscrita arquivoEscrita = new ArquivoEscrita();
	private String nomeClasse = "DadosPrincipaisDTO";
	private String localArquivo = "DTO";

	public void escreve(AtributoItemGrupo atributosLinkiageSection) throws IOException {

		// escrever atributos
		atributosLinkiageSection.escreveImportDataDivision(localArquivo);

		// escrever declaração variaveis
		atributosLinkiageSection.escreveVariaveis();
		
		// escrever Get e Set
		atributosLinkiageSection.escreveGetSet();

		// escrever toTrancode
		atributosLinkiageSection.escreveToTrancode();

		// escrever toObjeto
		atributosLinkiageSection.escreveToObject();
	}

	public String getNomeClasse() {
		return nomeClasse;
	}

	public void setNomeClasse(String nomeClasse) {
		this.nomeClasse = nomeClasse;
	}
}
