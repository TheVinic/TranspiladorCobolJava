package com.trans.transpiladorCobolJava.dataDivision;

import java.io.IOException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.trans.transpiladorCobolJava.main.Codigo;

public class DataDivision {
	
	@Autowired
	WorkingStorageSection workingStorageSection = new WorkingStorageSection();
	
	EscritaWorkingStorageSection escritaWorkingStorageSection = new EscritaWorkingStorageSection();

	Set<Atributo> atributosWorkingStorage;
	
	public void popula(String codigoCobol) throws IOException {

		System.out.println("Iniciando DATA DIVISION");
		
		if(codigoCobol.isEmpty()) {
			System.out.println("IDENTIFICATION DIVISION vazia.");
			return;
		}
		
		Codigo codigo = new Codigo(codigoCobol.split("\\."));
		
		for(; codigo.getPosicaoLeitura()<codigo.getCodigoCobol().length;) {
			switch(SecoesDataDivision.encontraParagrafo(codigo.getInstrucaoAtualLeitura())) {
			case FILESECTION:
				System.out.println(codigo.getInstrucaoAtualLeitura() + " não implementado.");
				break;
			case LINKAGESECTION:
				System.out.println(codigo.getInstrucaoAtualLeitura() + " não implementado.");
				break;
			case LOCALSTORAGESECTION:
				System.out.println(codigo.getInstrucaoAtualLeitura() + " não implementado.");
				break;
			case WORKINGSTORAGESECTION:
				atributosWorkingStorage = workingStorageSection.popula(codigo);
				break;
			case OUTRO:
				System.out.println("Seção incorreta na DATA DIVISION: " + codigo.getInstrucaoAtualLeitura());
				break;
			}
		}
	}

	public void escreve() throws IOException {
		escritaWorkingStorageSection.escreve(atributosWorkingStorage);
	}

}
