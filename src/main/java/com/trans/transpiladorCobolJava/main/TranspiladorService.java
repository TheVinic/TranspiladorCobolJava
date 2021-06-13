package com.trans.transpiladorCobolJava.main;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.trans.transpiladorCobolJava.DTO.VariaveisResponse;
import com.trans.transpiladorCobolJava.arquivo.ArquivoLeitura;
import com.trans.transpiladorCobolJava.dataDivision.DataDivision;
import com.trans.transpiladorCobolJava.identificationDivision.IdentificationDivision;
import com.trans.transpiladorCobolJava.procedureDivision.ProcedureDivision;

@Service
public class TranspiladorService {

	IdentificationDivision identificationDivision = new IdentificationDivision();

	DataDivision dataDivision = new DataDivision();

	ProcedureDivision procedureDivision = new ProcedureDivision("Controller", "Controller");

	public VariaveisResponse processa(String path) throws IOException {
		// Lê código cobol
		ArquivoLeitura arquivoLeitura = new ArquivoLeitura();
		arquivoLeitura.abreArquivo(path);
		// Código cobol separado em um vetor de string
		String[] codigoCobol = arquivoLeitura.lerTodoSeparaDivisao();
		arquivoLeitura.fechaArquivo();

		identificationDivision.leitura(codigoCobol[0]);
		dataDivision.leitura(codigoCobol[3], codigoCobol[4], codigoCobol[5], codigoCobol[6]);
		//procedureDivision.leitura(codigoCobol[7], dataDivision);

		dataDivision.escreve();
		//procedureDivision.escreve();

		return new VariaveisResponse(identificationDivision, dataDivision);
	}

}
