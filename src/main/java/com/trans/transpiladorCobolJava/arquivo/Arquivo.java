package com.trans.transpiladorCobolJava.arquivo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Arquivo {

	FileReader arquivoCobol;
	BufferedReader  textoCobol;
	
	//Realiza a abertura do arquivo com o código cobol
	public void abreArquivo() {
		
		Scanner ler = new Scanner(System.in);
		
		//System.out.println("Informe o nome do arquivo texto:\n");
		//String nomeArquivo = ler.nextLine();
		
		System.out.println("Iniciando leitura\n\n");
		try {
			arquivoCobol = new FileReader ("cobol.txt");
			textoCobol = new BufferedReader(arquivoCobol);
		}catch (IOException e) {
			System.out.printf("\nErro na abertura do arquivo %s.\n", e.getMessage());
			e.printStackTrace();
		}

		ler.close();
	}
	
	//Realiza o fechamento do arquivo com o código cobol	
	public void fechaArquivo() {
		try {
			arquivoCobol.close();
		} catch (IOException e) {
			System.out.printf("Erro no fechamento do arquivo %s.\n", e.getMessage());
			e.printStackTrace();
		}
	}

	//Le um caractere
	public char ler() {
		
		int caractere = 0;
		
		try {
			caractere = textoCobol.read();
		} catch (IOException e) {
			System.out.printf("Erro na leitura do arquivo %s.\n", e.getMessage());
			e.printStackTrace();
		}
		
		//System.out.println(caractere);
		if(caractere == -1)
			return Character.MIN_VALUE;
		else
			return (char) caractere;
		
	}

	//Le uma sequencia de carateres até o caractere informado
	public Palavra lerAte(char ateEsteCaractere) {
		char caractereAnterior = 0;
		char caractere;
		String textoLido = new String();

		caractere = ler();
		
		if(caractere == Character.MIN_VALUE)
			return null;
		
		while((caractere == ' ' || caractere == '\n' || caractere == '\r')) {
			caractere = ler();
		}
		
		while(caractere != ateEsteCaractere) {
			if(caractereAnterior == ' ' && caractere == ' ') {
				caractere = ler();
			}else {
				textoLido += caractere;
				caractereAnterior = caractere;
				caractere = ler();
			}
		}
		
		return new Palavra(textoLido.toUpperCase(), caractere);
	}

	public Palavra lerAte(char ateEsteCaractere1, char ateEsteCaractere2) {
		char caractereAnterior = 0;
		char caractere;
		String textoLido = new String();

		caractere = ler();
		
		if(caractere == Character.MIN_VALUE)
			return null;
		
		while((caractere == ' ' || caractere == '\n' || caractere == '\r')) {
			caractere = ler();
		}
		
		while(caractere != ateEsteCaractere1 && caractere != ateEsteCaractere2) {
			if(caractereAnterior == ' ' && caractere == ' ') {
				caractere = ler();
			}else {
				textoLido += caractere;
				caractereAnterior = caractere;
				caractere = ler();
			}
		}
		
		return new Palavra(textoLido.toUpperCase(), caractere);
	}

	public Palavra lerAte(char ateEsteCaractere1, char ateEsteCaractere2, char ateEsteCaractere3) {
		char caractereAnterior = 0;
		char caractere;
		String textoLido = new String();

		caractere = ler();
		
		if(caractere == Character.MIN_VALUE)
			return null;
		
		while((caractere == ' ' || caractere == '\n' || caractere == '\r')) {
			caractere = ler();
		}
		
		while(caractere != ateEsteCaractere1 && caractere != ateEsteCaractere2 && caractere != ateEsteCaractere3) {
			if(caractereAnterior == ' ' && caractere == ' ') {
				caractere = ler();
			}else {
				textoLido += caractere;
				caractereAnterior = caractere;
				caractere = ler();
			}
		}
		return new Palavra(textoLido.toUpperCase(), caractere);
	}

	public Palavra lerAte(char ateEsteCaractere1, char ateEsteCaractere2, char ateEsteCaractere3, char ateEsteCaractere4) {
		char caractereAnterior = 0;
		char caractere;
		String textoLido = new String();

		caractere = ler();
		
		if(caractere == Character.MIN_VALUE)
			return null;
		
		while((caractere == ' ' || caractere == '\n' || caractere == '\r')) {
			caractere = ler();
		}
		
		while(caractere != ateEsteCaractere1 && caractere != ateEsteCaractere2 && caractere != ateEsteCaractere3 && caractere !=ateEsteCaractere4) {
			if(caractereAnterior == ' ' && caractere == ' ') {
				caractere = ler();
			}else {
				textoLido += caractere;
				caractereAnterior = caractere;
				caractere = ler();
			}
		}
		return new Palavra(textoLido.toUpperCase(), caractere);
	}
}
