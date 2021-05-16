package com.trans.transpiladorCobolJava.main;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trans.transpiladorCobolJava.DTO.VariaveisResponse;
import com.trans.transpiladorCobolJava.arquivo.ArquivoLeitura;
import com.trans.transpiladorCobolJava.arquivo.OrdemExecucaoDivisao;
import com.trans.transpiladorCobolJava.dataDivision.DataDivision;
import com.trans.transpiladorCobolJava.identificationDivision.IdentificationDivision;
import com.trans.transpiladorCobolJava.procedureDivision.ProcedureDivision;

@Service
public class GeraDataDivisionService {

	@Autowired
	IdentificationDivision identificationDivision = new IdentificationDivision();
	@Autowired
	DataDivision dataDivision = new DataDivision();
	@Autowired
	ProcedureDivision procedureDivision = new ProcedureDivision();

	public VariaveisResponse processa(String path) throws IOException {
		// Lê código cobol
		ArquivoLeitura arquivoLeitura = new ArquivoLeitura();
		arquivoLeitura.abreArquivo(path);
		OrdemExecucaoDivisao ordemExecucao = new OrdemExecucaoDivisao();
		
		String[] codigoCobol = arquivoLeitura.lerTodoSeparaDivisao(ordemExecucao);
		arquivoLeitura.fechaArquivo();
		
		identificationDivision.popula(codigoCobol[ordemExecucao.getIdentificationDivision()]);
		dataDivision.popula(codigoCobol[ordemExecucao.getDataDivision()]);
		procedureDivision.popula(codigoCobol[ordemExecucao.getProcedureDivision()], dataDivision);
		
		dataDivision.escreve();
		procedureDivision.escreve();
		
		return new VariaveisResponse(identificationDivision, dataDivision);
	}

}
