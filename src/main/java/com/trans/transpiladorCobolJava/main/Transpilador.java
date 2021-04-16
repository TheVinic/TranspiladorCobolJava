package com.trans.transpiladorCobolJava.main;

import org.springframework.beans.factory.annotation.Autowired;

import com.trans.transpiladorCobolJava.arquivo.Arquivo;
import com.trans.transpiladorCobolJava.arquivo.Palavra;
import com.trans.transpiladorCobolJava.dataDivision.DataDivision;
import com.trans.transpiladorCobolJava.environmentDivision.EnvironmentDivision;
import com.trans.transpiladorCobolJava.identificationDivision.IdentificationDivision;
import com.trans.transpiladorCobolJava.procedureDivision.ProcedureDivision;

public class Transpilador {

	Arquivo arquivo;

	boolean passouIdentificationDivision = false;
	boolean passouEnvironmentDivision = false;
	boolean passouDataDivision = false;
	boolean passouProcedureDivision = false;

	@Autowired
	IdentificationDivision identificationDivision = new IdentificationDivision();
	@Autowired
	EnvironmentDivision environmentDivision = new EnvironmentDivision();
	@Autowired
	DataDivision dataDivision = new DataDivision();
	@Autowired
	ProcedureDivision procedureDivision = new ProcedureDivision();

	public void processa() {
		// Lê código cobol
		arquivo = new Arquivo();
		arquivo.abreArquivo();

		// Lê Identification Division
		Palavra divisao = identificationDivision.popula(arquivo);
		passouIdentificationDivision = true;

		while (divisao != null) {
			switch (Divisoes.encontraDivisao(divisao.getPalavra())) {
			case DATA_DIVISION:
				if (!passouDataDivision) {
					divisao = dataDivision.popula(arquivo);
				}
				break;
			case ENVIRONMENT_DIVISION:
				if (passouEnvironmentDivision) {
					// Lê Environment Division
					divisao = environmentDivision.popula(arquivo);
				} else {
					System.out.println("Erro: Já passou na ENVIRONMENT DIVISION");
				}
				break;
			case IDENTIFICATION_DIVISION:
				System.out.println("Erro: Já passou na IDENTIFICATION DIVISION");
				break;
			case PROCEDURE_DIVISION:
				break;
			default:
				break;
			}
		}
	}

}
