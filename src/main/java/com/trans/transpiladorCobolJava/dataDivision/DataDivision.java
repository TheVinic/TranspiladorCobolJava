package com.trans.transpiladorCobolJava.dataDivision;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.trans.transpiladorCobolJava.arquivo.Codigo;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.Atributo;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.AtributoGrupo;

public class DataDivision {

	@Autowired
	WorkingStorageSection workingStorageSection = new WorkingStorageSection();

	EscritaWorkingStorageSection escritaWorkingStorageSection = new EscritaWorkingStorageSection();

	AtributoGrupo atributosWorkingStorage;

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
				System.out.println(codigo.getInstrucaoAtualLeitura() + " não implementado.");
				codigo.getProximaInstrucaoLeitura();
				break;
			case LOCALSTORAGESECTION:
				System.out.println(codigo.getInstrucaoAtualLeitura() + " não implementado.");
				codigo.getProximaInstrucaoLeitura();
				break;
			case WORKINGSTORAGESECTION:
				atributosWorkingStorage = workingStorageSection.popula(codigo);
				break;
			}
		}
	}

	public void escreve() throws IOException {
		escritaWorkingStorageSection.escreve(atributosWorkingStorage);
	}

	public Atributo localizaAtributo(String nomeVariavel) {
		nomeVariavel = nomeVariavel.replace(".", "").replaceAll("-", "_");
		return atributosWorkingStorage.getLocalizaAtributo(nomeVariavel);
	}

	public Atributo localizaAtributo(String nomeVariavel, String proximaInstrucaoLeitura) {
		nomeVariavel = nomeVariavel.replace(".", "");
		proximaInstrucaoLeitura = proximaInstrucaoLeitura.replace(".", "");
		Atributo grupo = atributosWorkingStorage.getLocalizaAtributo(proximaInstrucaoLeitura);
		if(grupo instanceof AtributoGrupo) {
			return ((AtributoGrupo) grupo).getLocalizaAtributo(nomeVariavel);
		}
		return null;
	}

}
