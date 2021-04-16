package com.trans.transpiladorCobolJava.dataDivision;

import org.springframework.beans.factory.annotation.Autowired;

import com.trans.transpiladorCobolJava.arquivo.Arquivo;
import com.trans.transpiladorCobolJava.arquivo.Palavra;
import com.trans.transpiladorCobolJava.main.Divisoes;

public class DataDivision {
	
	@Autowired
	WorkingStorageSection workingStorageSection = new WorkingStorageSection();

	public Palavra popula(Arquivo arquivo) {

		System.out.println("Iniciando DATA DIVISION");
		
		Palavra secaoDataDivision = arquivo.lerAte('.');
		while(!Divisoes.fimDivisaoAtual(secaoDataDivision.getPalavra())) {
			switch(SecoesDataDivision.encontraParagrafo(secaoDataDivision.getPalavra())) {
			case FILESECTION:
				System.out.println(secaoDataDivision + " não implementado.");
				break;
			case LINKAGESECTION:
				System.out.println(secaoDataDivision + " não implementado.");
				break;
			case LOCALSTORAGESECTION:
				System.out.println(secaoDataDivision + " não implementado.");
				break;
			case WORKINGSTORAGESECTION:
				secaoDataDivision = workingStorageSection.popula(arquivo);
				break;
			case OUTRO:
				System.out.println("Seção incorreta na DATA DIVISION: " + secaoDataDivision);
				break;
			default:
				break;
			
			}
		}
		
		return null;
	}

}
