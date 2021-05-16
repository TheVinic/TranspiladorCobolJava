package com.trans.transpiladorCobolJava.DTO;

public class IdentificationDivisionResponse {

	private String programId;
	private String author;
	private String installation;
	private String dataWritten;
	private String dateCompiled;
	private String security;
	
	public IdentificationDivisionResponse(String programId, String author, String installation, String dataWritten,
			String dateCompiled, String security) {
		super();
		this.programId = programId;
		this.author = author;
		this.installation = installation;
		this.dataWritten = dataWritten;
		this.dateCompiled = dateCompiled;
		this.security = security;
	}

	public String getProgramId() {
		return programId;
	}

	public String getAuthor() {
		return author;
	}

	public String getInstallation() {
		return installation;
	}

	public String getDataWritten() {
		return dataWritten;
	}

	public String getDateCompiled() {
		return dateCompiled;
	}

	public String getSecurity() {
		return security;
	}
}
