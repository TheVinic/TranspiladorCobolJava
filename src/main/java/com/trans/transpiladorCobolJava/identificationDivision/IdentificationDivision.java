package com.trans.transpiladorCobolJava.identificationDivision;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.trans.transpiladorCobolJava.DTO.IdentificationDivisionResponse;

@Component
public class IdentificationDivision {
	private String programId;
	private String author;
	private String installation;
	private String dataWritten;
	private String dateCompiled;
	private String security;

	public void leitura(String codigoCobol) {

		System.out.println("Inicio da IDENTIFICATION DIVISION");

		if (codigoCobol.isEmpty()) {
			System.out.println("IDENTIFICATION DIVISION vazia.");
			return;
			// TODO erro
		}

		LinkedHashMap<String, String> string = getByRegex(codigoCobol);
		for (Map.Entry<String, String> valor : string.entrySet()) {
			switch (ParagrafosIdentificationDivision.encontraParagrafo(valor.getKey())) {
			case AUTHOR:
				System.out.println("Author: " + (author = valor.getValue()));
				break;
			case DATACOMPILED:
				System.out.println("Data Compiled: " + (dateCompiled = valor.getValue()));
				break;
			case DATAWRITTEN:
				System.out.println("Data Written: " + (dataWritten = valor.getValue()));
				break;
			case INSTALLATION:
				System.out.println("Installation: " + (installation = valor.getValue()));
				break;
			case PROGRAMID:
				System.out.println("Program Id: " + (programId = valor.getValue()));
				break;
			case SECURITY:
				System.out.println("Security: " + (security = valor.getValue()));
				break;
			case OUTRO:
				break;
			}
		}
		System.out.println("Fim da IDENTIFICATION DIVISION");
	}

	private static LinkedHashMap<String, String> getByRegex(String texto) {
		LinkedHashMap<String, String> divisao = new LinkedHashMap<String, String>();

		String regex = "(?i)(PROGRAM-ID|AUTHOR|INSTALLATION|DATA-WRITTEN|DATA-COMPILED|SECURITY)\\.\\s*(([a-zA-Z\"()]+\\s*)+)\\.";

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(texto);

		while (matcher.find()) {
			divisao.put(matcher.group(1), matcher.group(2));
		}

		return divisao;
	}

	public IdentificationDivisionResponse toResponse() {
		return new IdentificationDivisionResponse(programId, author, installation, dataWritten, dateCompiled, security);
	}

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getInstallation() {
		return installation;
	}

	public void setInstallation(String installation) {
		this.installation = installation;
	}

	public String getDataWritten() {
		return dataWritten;
	}

	public void setDataWritten(String dataWritten) {
		this.dataWritten = dataWritten;
	}

	public String getDateCompiled() {
		return dateCompiled;
	}

	public void setDateCompiled(String dateCompiled) {
		this.dateCompiled = dateCompiled;
	}

	public String getSecurity() {
		return security;
	}

	public void setSecurity(String security) {
		this.security = security;
	}
}
