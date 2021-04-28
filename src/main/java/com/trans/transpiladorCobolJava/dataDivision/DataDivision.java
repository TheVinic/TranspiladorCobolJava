package com.trans.transpiladorCobolJava.dataDivision;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.trans.transpiladorCobolJava.arquivo.Codigo;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.Atributo;

public class DataDivision {

	@Autowired
	WorkingStorageSection workingStorageSection = new WorkingStorageSection();

	EscritaWorkingStorageSection escritaWorkingStorageSection = new EscritaWorkingStorageSection();

	ArrayList<Atributo> atributosWorkingStorage;

	public void popula(String codigoCobol) {

		System.out.println("Iniciando DATA DIVISION");

		if (codigoCobol.isEmpty()) {
			System.out.println("DATA DIVISION vazia.");
			return;
		}

		Codigo codigo = new Codigo(codigoCobol.split("\\.\\s+"));

		for (; codigo.getPosicaoLeitura() < codigo.getCodigoCobol().length;) {
			switch (SecoesDataDivision.encontraParagrafo(codigo.getInstrucaoAtualLeitura())) {
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
			case OUTRO:
				System.out.println("Seção incorreta na DATA DIVISION: " + codigo.getInstrucaoAtualLeitura());
				codigo.getProximaInstrucaoLeitura();
				break;
			}
		}
	}

	public void escreve() throws IOException {
		escritaWorkingStorageSection.escreve(atributosWorkingStorage);
	}

	public Atributo localizaAtributo(String nomeVariavel) {
		nomeVariavel = nomeVariavel.replace(".", "");
		for (Atributo atributo : atributosWorkingStorage) {
			// TODO validar se pode procurar dentro de um item de grupo
			if (atributo.getNome().equalsIgnoreCase(nomeVariavel)) {
				return atributo;
			}
		}
		return null;
	}

	public Atributo localizaAtributo(String elemento, String proximaInstrucaoLeitura) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
