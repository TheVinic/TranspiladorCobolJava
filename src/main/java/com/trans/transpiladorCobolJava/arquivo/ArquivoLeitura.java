package com.trans.transpiladorCobolJava.arquivo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ArquivoLeitura {

	private FileReader arquivoCobol;
	private BufferedReader textoCobol;

	private Integer comecaLeitura = 6;

	// Realiza a abertura do arquivo com o código cobol
	public void abreArquivo(String path) {
		for (File file : new File("java\\controller").listFiles()) {
			if (!file.isDirectory()) {
				file.delete();
			}
		}
		for (File file : new File("java\\DTO").listFiles()) {
			if (!file.isDirectory()) {
				file.delete();
			}
		}
		for (File file : new File("java\\model").listFiles()) {
			if (!file.isDirectory()) {
				file.delete();
			}
		}
		for (File file : new File("java\\repository").listFiles()) {
			if (!file.isDirectory()) {
				file.delete();
			}
		}
		for (File file : new File("java\\service").listFiles()) {
			if (!file.isDirectory()) {
				file.delete();
			}
		}
		System.out.println("Abertura do arquivo");
		try {
			arquivoCobol = new FileReader(path);
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
		System.out.println("Arquivo fechado");
	}

	public String lerLinha() throws IOException {
		String linha;
		try {
			linha = textoCobol.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException("Erro na leitura da linha");
		}
		return linha;
	}

	public String trataComentario(String linha) {
		return (linha.length() == 0) ? linha : linha.replaceAll("\t", "    ").substring(comecaLeitura);
	}

	public String[] lerTodoSeparaDivisao() throws IOException {
		System.out.println("Iniciando leitura do arquivo");
		Integer contador = 0;

		String[] codigoPorDivisao = new String[8];

		for (int i = 0; i < 8; i++) {
			codigoPorDivisao[i] = new String();
		}

		String codigoLido = lerLinha();
		if (codigoLido.substring(0, 5).contains("I")) {
			comecaLeitura = 0;
			System.out.println("Arquivo começa na posição 0");
		} else {
			System.out.println("Arquivo possui espaço para sequencial");
		}

		while (codigoLido != null) {
			codigoLido = trataComentario(codigoLido);
			if (codigoLido.matches("(?i)\\s*IDENTIFICATION\\s+DIVISION\\s*\\.\\s*")) {
				contador = 0;
				System.out.println("Iniciando Identification Division");
			} else if (codigoLido.matches("(?i)\\s*ENVIRONMENT\\s+DIVISION\\s*\\.\\s*")) {
				contador = 1;
				System.out.println("Iniciando Environment Division");
			} else if (codigoLido.matches("(?i)\\s*DATA\\s+DIVISION\\s*\\.\\s*")) {
				contador = 2;
				System.out.println("Iniciando Data Division");
			} else if (codigoLido.matches("(?i)\\s*FILE\\s+SECTION\\s*\\.\\s*")) {
				contador = 3;
				System.out.println("Iniciando File Section");
			} else if (codigoLido.matches("(?i)\\s*WORKING[-]STORAGE\\s+SECTION\\s*\\.\\s*")) {
				contador = 4;
				System.out.println("Iniciando Working-storage Section");
			} else if (codigoLido.matches("(?i)\\s*LOCAL[-]STORAGE\\s+SECTION\\s*\\.\\s*")) {
				contador = 5;
				System.out.println("Iniciando local-storage section");
			} else if (codigoLido.matches("(?i)\\s*LINKAGE\\s+SECTION\\s*\\.\\s*")) {
				contador = 6;
				System.out.println("Iniciando linkage section");
			} else if (codigoLido.matches("(?i)\\s*PROCEDURE\\s+DIVISION\\s*\\.\\s*")) {
				contador = 7;
				System.out.println("Iniciando Procedure Division");
			}
			codigoPorDivisao[contador] += (codigoLido.startsWith("*") ? "" : codigoLido);
			codigoLido = lerLinha();
		}
		return codigoPorDivisao;
	}

}
