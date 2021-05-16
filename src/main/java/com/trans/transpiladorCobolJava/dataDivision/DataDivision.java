package com.trans.transpiladorCobolJava.dataDivision;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trans.transpiladorCobolJava.DTO.DataDivisionResponse;
import com.trans.transpiladorCobolJava.arquivo.Codigo;
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

	public void popula(String codigoCobol) {

		System.out.println("Iniciando DATA DIVISION");

		if (codigoCobol.isEmpty()) {
			System.out.println("DATA DIVISION vazia.");
			return;
		}

		Codigo codigo = new Codigo(codigoCobol.split("\\.\\s+"));

		for (; codigo.getPosicaoLeitura() < codigo.getCodigoCobol().length;) {
			switch (SecoesDataDivision.valueOf(codigo.getInstrucaoAtualLeitura().replaceAll("\\s|-", ""))) {
			case FILESECTION:
				System.out.println(codigo.getInstrucaoAtualLeitura() + " não implementado.");
				codigo.getProximaInstrucaoLeitura();
				break;
			case LINKAGESECTION:
				linkageSection = new LinkageSection();
				atributosLinkageSection = linkageSection.popula(codigo);
				break;
			case LOCALSTORAGESECTION:
				System.out.println(codigo.getInstrucaoAtualLeitura() + " não implementado.");
				codigo.getProximaInstrucaoLeitura();
				break;
			case WORKINGSTORAGESECTION:
				workingStorageSection = new WorkingStorageSection();
				atributosWorkingStorage = workingStorageSection.popula(codigo);
				break;
			}
		}
	}

	public void escreve() throws IOException {
		if (atributosWorkingStorage != null ) {
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
		Atributo atributo = atributosWorkingStorage.getLocalizaAtributo(nomeVariavel);
		if(atributo == null) {
			atributo = atributosLinkageSection.getLocalizaAtributo(nomeVariavel);
		}
		return atributo;
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
