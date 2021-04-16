package com.trans.transpiladorCobolJava.identificationDivision;

import com.trans.transpiladorCobolJava.arquivo.Arquivo;
import com.trans.transpiladorCobolJava.arquivo.Palavra;
import com.trans.transpiladorCobolJava.main.Divisoes;

import lombok.Getter;

public class IdentificationDivision {
	@Getter private String programId;
	@Getter private String author;
	@Getter private String installation;
	@Getter private String dataWritten;
	@Getter private String dateCompiled;
	@Getter private String security;
	
	public Palavra popula(Arquivo arquivo) {
		
		Palavra identificationDivision = arquivo.lerAte('.');
		if(!identificationDivision.getPalavra().equals(Divisoes.IDENTIFICATION_DIVISION.getDescricao())) {
			System.out.println("Erro ao iniciar IDENTIFICATION DIVISION");
		}else {

			System.out.println("Iniciando a " + Divisoes.IDENTIFICATION_DIVISION.getDescricao());
		}
		
		Palavra paragrafoIdentificationDivision = arquivo.lerAte('.');
		if(!paragrafoIdentificationDivision.getPalavra().equals("PROGRAM-ID")) {
			System.out.println("Erro na PROGRAM-ID");
		}
		
		programId = arquivo.lerAte('.').getPalavra();
		System.out.println("Nome do programa: " + programId);

		paragrafoIdentificationDivision = arquivo.lerAte('.');
		while(!Divisoes.fimDivisaoAtual(paragrafoIdentificationDivision.getPalavra())) {
			switch(ParagrafosIdentificationDivision.encontraParagrafo(paragrafoIdentificationDivision.getPalavra())) {
				case AUTHOR:{
					author = arquivo.lerAte('.').getPalavra();
					System.out.println("Autor: " + author);
					break;
				}
				case DATACOMPILED:{
					dateCompiled = arquivo.lerAte('.').getPalavra();
					System.out.println("Data Compilação: " + dateCompiled);
					break;
				}
				case DATAWRITTEN:{
					dataWritten = arquivo.lerAte('.').getPalavra();
					System.out.println("Data Escrito: " + dataWritten);
					break;
				}
				case INSTALLATION:{
					installation = arquivo.lerAte('.').getPalavra();
					System.out.println("Instalação: " + installation);
					break;
				}	
				case PROGRAMID:{
					System.out.println("ERRO: PROGRAM-ID já foi informado.");
					break;
				}
				case SECURITY:{
					security = arquivo.lerAte('.').getPalavra();
					System.out.println("Segurança: " + security);
					break;
				}
				case OUTRO:
					System.out.println("Paragrafo incorreto na IDENTIFICATION DIVISION: " + paragrafoIdentificationDivision);
					break;
			}
			paragrafoIdentificationDivision = arquivo.lerAte('.');
		}
		System.out.println("Fim da IDENTIFICATION DIVISION");
		return paragrafoIdentificationDivision;
	}
}
