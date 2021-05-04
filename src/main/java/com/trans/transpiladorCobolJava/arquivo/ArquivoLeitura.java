package com.trans.transpiladorCobolJava.arquivo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ArquivoLeitura {

	private FileReader arquivoCobol;
	private BufferedReader textoCobol;
	
	private Integer comecaLeitura = 6;
	private Integer fimLeitura = 72;

	// Realiza a abertura do arquivo com o código cobol
	public void abreArquivo() {
		
		System.out.println("Iniciando leitura\n\n");
		try {
			arquivoCobol = new FileReader("cobol.txt");
			textoCobol = new BufferedReader(arquivoCobol);
		} catch (IOException e) {
			System.out.printf("\nErro na abertura do arquivo %s.\n", e.getMessage());
			e.printStackTrace();
		}

	}

	// Realiza o fechamento do arquivo com o código cobol
	public void fechaArquivo() {
		try {
			arquivoCobol.close();
		} catch (IOException e) {
			System.out.printf("Erro no fechamento do arquivo %s.\n", e.getMessage());
			e.printStackTrace();
		}
	}

	public String lerLinha() throws IOException {
		String linha;
		try {
			linha = textoCobol.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException("Erro na leitura da linha");
		}
		return (linha != null) ? linha.toUpperCase() : null;
	}

	public String[] lerLinhaComInstrucao() throws IOException {
		String linhaCompleta = new String();
		// TODO controle de mais de uma instruçao por linha
		do {
			linhaCompleta += lerLinha();
		} while (!linhaCompleta.contains("."));

		String[] linhaComInstrucao = linhaCompleta.trim().split("\\s+|\\.");

		return linhaComInstrucao;
	}
	
	public String trataComentario(String linha) {
		return (linha.length() >= fimLeitura) ? linha.substring(comecaLeitura, fimLeitura) : linha.substring(comecaLeitura);
	}

	public String[] lerTodoSeparaDivisao(OrdemExecucaoDivisao ordemExecucao) throws IOException {
		String codigoCompleto = new String();
		Integer contador = 1;

		String codigoLido = lerLinha();
		if(codigoLido.substring(0, 5).contains("I")){
			comecaLeitura = 0;
			fimLeitura = 66;
		}
		
		while (codigoLido != null) {
			if (ordemExecucao.naoPassouIdentification() && codigoLido.contains("IDENTIFICATION")) {
				ordemExecucao.setIdentificationDivision(contador++);
			} else if (ordemExecucao.naoPassouEnvironment() && codigoLido.contains("ENVIRONMENT")) {
				ordemExecucao.setEnvironmentDivision(contador++);
			} else if (ordemExecucao.naoPassouData() && codigoLido.contains("DATA")) {
				ordemExecucao.setDataDivision(contador++);
			} else if (ordemExecucao.naoPassouProcedure() && codigoLido.contains("PROCEDURE")) {
				ordemExecucao.setProcedureDivision(contador++);
			}
			codigoLido = trataComentario(codigoLido);
			codigoCompleto += (codigoLido.startsWith("*") ? "" : codigoLido);
			codigoLido = lerLinha();
		}

		String[] codigoDividido = codigoCompleto.trim().replaceAll("\\s+", " ").replace("'", "\"").replaceAll("“|”", "\"")
				.split("IDENTIFICATION DIVISION. |DATA DIVISION. |ENVIRONMENT DIVISION. |PROCEDURE DIVISION. |"
						+ "IDENTIFICATION DIVISION\\s+\\. |DATA DIVISION\\s+\\. |ENVIRONMENT DIVISION\\s+\\. |PROCEDURE DIVISION\\s+\\. ");

		return codigoDividido;
	}
}
