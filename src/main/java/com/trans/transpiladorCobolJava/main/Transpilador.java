package com.trans.transpiladorCobolJava.main;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.trans.transpiladorCobolJava.arquivo.ArquivoLeitura;
import com.trans.transpiladorCobolJava.arquivo.OrdemExecucaoDivisao;
import com.trans.transpiladorCobolJava.dataDivision.DataDivision;
import com.trans.transpiladorCobolJava.environmentDivision.EnvironmentDivision;
import com.trans.transpiladorCobolJava.identificationDivision.IdentificationDivision;
import com.trans.transpiladorCobolJava.procedureDivision.ProcedureDivision;

public class Transpilador {

	@Autowired
	IdentificationDivision identificationDivision = new IdentificationDivision();
	@Autowired
	EnvironmentDivision environmentDivision = new EnvironmentDivision();
	@Autowired
	DataDivision dataDivision = new DataDivision();
	@Autowired
	ProcedureDivision procedureDivision = new ProcedureDivision();

	public void processa() throws IOException {
		// Lê código cobol
		ArquivoLeitura arquivoLeitura = new ArquivoLeitura();
		arquivoLeitura.abreArquivo();
		OrdemExecucaoDivisao ordemExecucao = new OrdemExecucaoDivisao();
		
		String[] codigoCobol = arquivoLeitura.lerTodoSeparaDivisao(ordemExecucao);
		arquivoLeitura.fechaArquivo();
		
		// Lê Identification Division
		identificationDivision.popula(codigoCobol[ordemExecucao.getIdentificationDivision()]);
		environmentDivision.popula(codigoCobol[ordemExecucao.getEnvironmentDivision()]);
		dataDivision.popula(codigoCobol[ordemExecucao.getDataDivision()]);
		//TODO procedureDivision
		
		dataDivision.escreve();
	}

}
