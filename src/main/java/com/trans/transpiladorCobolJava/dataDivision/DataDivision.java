package com.trans.transpiladorCobolJava.dataDivision;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trans.transpiladorCobolJava.DTO.DataDivisionResponse;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.Atributo;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.AtributoItemGrupo;

@Component
public class DataDivision {

	@Autowired
	private WorkingStorageSection workingStorageSection;
	
	private AtributoItemGrupo atributosWorkingStorage;

	@Autowired
	private EscritaWorkingStorageSection escritaWorkingStorageSection;

	@Autowired
	private LinkageSection linkageSection;
	
	private AtributoItemGrupo atributosLinkageSection;

	@Autowired
	private EscritaLinkageSection escritaLinkageSection;
	
	
	private EscritaLocalStorageSection escritaLocalStorageSection;
	
	private AtributoItemGrupo atributosLocalStorageSection;
	
	
	private EscritaFileSection escritaFileSection;
	
	private AtributoItemGrupo atributosFileSection;
	
	public void analisesDataDivision(String file, String working, String local, String linkage) {

		System.out.println("Iniciando DATA DIVISION");

		// File Section
		if (file != null && !file.isEmpty()) {
			System.out.println("File Section não implementado.");
		}

		// Linkage Section
		if (linkage != null && !linkage.isEmpty()) {
			System.out.println("Iniciando Linkage Section");
			linkageSection = new LinkageSection();
			atributosLinkageSection = linkageSection.analisesLinkageSection(linkage);
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
			atributosWorkingStorage = workingStorageSection.analisesWorkingStorageSection(working);
			System.out.println("Fim WORKING-STOREAGE");
		}
	}

	public void traduzDataDivision() throws IOException {
		if (atributosWorkingStorage != null) {
			escritaWorkingStorageSection = new EscritaWorkingStorageSection();
			escritaWorkingStorageSection.escreve(atributosWorkingStorage);
		}
		if (atributosLinkageSection != null) {
			escritaLinkageSection = new EscritaLinkageSection();
			escritaLinkageSection.escreve(atributosLinkageSection);
		}
	}

	public Atributo localizaAtributoArvoreObjetos(String nomeVariavel) {
		nomeVariavel = nomeVariavel.replace(".", "").replaceAll("-", "_");
		Atributo atributo = null;
		if (atributosWorkingStorage != null) {
			atributo = atributosWorkingStorage.getLocalizaAtributo(nomeVariavel);
		}
		if (atributo == null && atributosLinkageSection != null) {
			atributo = atributosLinkageSection.getLocalizaAtributo(nomeVariavel);
		}
		return atributo;
	}

	public Atributo localizaAtributoArvoreObjetoOf(String nomeVariavel, String proximaInstrucaoLeitura) {
		nomeVariavel = nomeVariavel.replace(".", "");
		proximaInstrucaoLeitura = proximaInstrucaoLeitura.replace(".", "");
		Atributo grupo = atributosWorkingStorage.getLocalizaAtributo(proximaInstrucaoLeitura);
		if (grupo instanceof AtributoItemGrupo) {
			return ((AtributoItemGrupo) grupo).getLocalizaAtributo(nomeVariavel);
		}
		grupo = atributosLinkageSection.getLocalizaAtributo(proximaInstrucaoLeitura);
		if (grupo instanceof AtributoItemGrupo) {
			return ((AtributoItemGrupo) grupo).getLocalizaAtributo(nomeVariavel);
		}
		return null;
	}

	public DataDivisionResponse toResponse() {
		return new DataDivisionResponse(atributosWorkingStorage, atributosLinkageSection);
	}

	public String setAtributoIsTabela(String nomeTabela) {
		String nomeRepository;
		if (atributosWorkingStorage != null) {
			nomeRepository = atributosWorkingStorage.setAtributoIsTabela(nomeTabela);
			if (nomeRepository != null && !nomeRepository.isEmpty()) {
				return nomeRepository;
			}
		}
		if (atributosLinkageSection != null) {
			nomeRepository = atributosLinkageSection.setAtributoIsTabela(nomeTabela);
			if (nomeRepository != null && !nomeRepository.isEmpty()) {
				return nomeRepository;
			}
		}
		return null;
	}

	public void setIntrucaoRepository(String instrucao, String tabela) {
		if (atributosWorkingStorage != null) {
			if (atributosWorkingStorage.setIntrucaoRepository(instrucao, tabela)) {
				return;
			}
		}
		if (atributosLinkageSection != null) {
			if (atributosLinkageSection.setIntrucaoRepository(instrucao, tabela)) {
				return;
			}
		}
	}
}
