package com.trans.transpiladorCobolJava.main;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trans.transpiladorCobolJava.DTO.VariaveisResponse;
import com.trans.transpiladorCobolJava.arquivo.ArquivoLeitura;
import com.trans.transpiladorCobolJava.dataDivision.DataDivision;
import com.trans.transpiladorCobolJava.environmentDivision.EnvironmentDivision;
import com.trans.transpiladorCobolJava.identificationDivision.IdentificationDivision;
import com.trans.transpiladorCobolJava.procedureDivision.ProcedureDivision;

@Service
public class TranspiladorService {

	@Autowired
	private IdentificationDivision identificationDivision;
	@Autowired
	private DataDivision dataDivision;
	@Autowired
	private ProcedureDivision procedureDivision;
	@Autowired
	private EnvironmentDivision environmentDivision;
	
	private String[] codigoCobol;

	public VariaveisResponse processaNovaTranspilacao(String path) throws IOException {
		// Lê código cobol
		ArquivoLeitura arquivoLeitura = new ArquivoLeitura();
		arquivoLeitura.abreArquivo(path);
		// Código cobol separado em um vetor de string
		codigoCobol = arquivoLeitura.lerTodoSeparaDivisao();
		arquivoLeitura.fechaArquivo();

		identificationDivision.leitura(codigoCobol[0]);
		dataDivision.analisesDataDivision(codigoCobol[3], codigoCobol[4], codigoCobol[5], codigoCobol[6]);
		procedureDivision = new ProcedureDivision("Controller", "controller", identificationDivision.getProgramId());
		procedureDivision.preparaAnalises(codigoCobol[7], dataDivision);

		dataDivision.traduzDataDivision();
		procedureDivision.preTratamentoTraducaoEscrita();

		return new VariaveisResponse(identificationDivision, dataDivision);
	}

}
