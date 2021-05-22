package com.trans.transpiladorCobolJava.main;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.trans.transpiladorCobolJava.DTO.VariaveisResponse;
import com.trans.transpiladorCobolJava.arquivo.ArquivoLeitura;
import com.trans.transpiladorCobolJava.arquivo.Codigo;
import com.trans.transpiladorCobolJava.arquivo.OrdemExecucaoDivisao;
import com.trans.transpiladorCobolJava.dataDivision.DataDivision;
import com.trans.transpiladorCobolJava.identificationDivision.IdentificationDivision;
import com.trans.transpiladorCobolJava.procedureDivision.ProcedureDivision;

@Service
public class GeraDataDivisionService {

	IdentificationDivision identificationDivision = new IdentificationDivision();
	
	DataDivision dataDivision = new DataDivision();
	
	ProcedureDivision procedureDivision = new ProcedureDivision("Controller");

	public VariaveisResponse processa(String path) throws IOException {
		// Lê código cobol
		ArquivoLeitura arquivoLeitura = new ArquivoLeitura();
		arquivoLeitura.abreArquivo(path);
		OrdemExecucaoDivisao ordemExecucao = new OrdemExecucaoDivisao();
		
		String[] codigoCobol = arquivoLeitura.lerTodoSeparaDivisao(ordemExecucao);
		arquivoLeitura.fechaArquivo();
		
		identificationDivision.popula(codigoCobol[ordemExecucao.getIdentificationDivision()]);
		dataDivision.popula(codigoCobol[ordemExecucao.getDataDivision()]);
		procedureDivision.analiseSemantica(new Codigo(codigoCobol[ordemExecucao.getProcedureDivision()].split("\\.\\s+|\\s")), dataDivision);
		
		dataDivision.escreve();
		procedureDivision.escreve();
		
		return new VariaveisResponse(identificationDivision, dataDivision);
	}

}
