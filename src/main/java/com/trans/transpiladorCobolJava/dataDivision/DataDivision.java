package com.trans.transpiladorCobolJava.dataDivision;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trans.transpiladorCobolJava.DTO.DataDivisionResponse;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.Atributo;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.AtributoGrupo;

@Component
public class DataDivision {

	@Autowired
	WorkingStorageSection workingStorageSection;

	@Autowired
	LinkageSection linkageSection;

	AtributoGrupo atributosWorkingStorage;

	@Autowired
	EscritaWorkingStorageSection escritaWorkingStorageSection;

	AtributoGrupo atributosLinkageSection;

	@Autowired
	EscritaLinkageSection escritaLinkageSection;

	public void leitura(String file, String working, String local, String linkage) {

		System.out.println("Iniciando DATA DIVISION");

		// File Section
		if (file != null && !file.isEmpty()) {
			System.out.println("File Section não implementado.");
		}

		// Linkage Section
		if (linkage != null && !linkage.isEmpty()) {
			System.out.println("Iniciando Linkage Section");
			linkageSection = new LinkageSection();
			atributosLinkageSection = linkageSection.popula(linkage);
			System.out.println("Fim Linkage Section");
		}

		// Local-storage Section
		if (local != null && !local.isEmpty()) {
			System.out.println("Local-storage Section não implementado.");
		}

		// WORKING-STOREAGE
		if (working != null && !working.isEmpty()) {
			System.out.println("Iniciando WORKING-STOREAGE");
			workingStorageSection = new WorkingStorageSection();
			atributosWorkingStorage = workingStorageSection.popula(working);
			System.out.println("Fim WORKING-STOREAGE");
		}
	}

	public void escreve() throws IOException {
		if (atributosWorkingStorage != null) {
			escritaWorkingStorageSection = new EscritaWorkingStorageSection();
			escritaWorkingStorageSection.escreve(atributosWorkingStorage);
		}
		if (atributosLinkageSection != null) {
			escritaLinkageSection = new EscritaLinkageSection();
			escritaLinkageSection.escreve(atributosLinkageSection);
		}
	}

	public Atributo localizaAtributo(String nomeVariavel) {
		nomeVariavel = nomeVariavel.replace(".", "").replaceAll("-", "_");
		if (atributosWorkingStorage != null) {
			Atributo atributo = atributosWorkingStorage.getLocalizaAtributo(nomeVariavel);
			if (atributo == null) {
				atributo = atributosLinkageSection.getLocalizaAtributo(nomeVariavel);
			}
			return atributo;
		} else {
			return null;
		}
	}

	public Atributo localizaAtributo(String nomeVariavel, String proximaInstrucaoLeitura) {
		nomeVariavel = nomeVariavel.replace(".", "");
		proximaInstrucaoLeitura = proximaInstrucaoLeitura.replace(".", "");
		Atributo grupo = atributosWorkingStorage.getLocalizaAtributo(proximaInstrucaoLeitura);
		if (grupo instanceof AtributoGrupo) {
			return ((AtributoGrupo) grupo).getLocalizaAtributo(nomeVariavel);
		}
		grupo = atributosLinkageSection.getLocalizaAtributo(proximaInstrucaoLeitura);
		if (grupo instanceof AtributoGrupo) {
			return ((AtributoGrupo) grupo).getLocalizaAtributo(nomeVariavel);
		}
		return null;
	}

	public DataDivisionResponse toResponse() {
		return new DataDivisionResponse(atributosWorkingStorage, atributosLinkageSection);
	}

}
