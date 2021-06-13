package com.trans.transpiladorCobolJava.procedureDivision;

public enum ParagrafosProcedureDivision {
	ACCEPT("ACCEPT", false),
	ADD("ADD", true),
	ALTER("ALTER", false),
	CALL("CALL", false),
	CANCEL("CANCEL", false),
	CLOSE("CLOSE", false),
	COMPUTE("COMPUTE", true),
	CONTINUE("CONTINUE", false),
	DELETE("DELETE", false),
	DISPLAY("DISPLAY", true),
	DIVIDE("DIVIDE", true),
	ENDIF("END-IF", true),
	ENDPERFORM("END-PERFORM", true),
	ELSE("ELSE", true),
	ENTRY("ENTRY", false),
	EVALUATE("EVALUATE", false),
	EXIT("EXIT", true),
	EXITMETHOD("EXIT-METHOD", false),
	EXITPROGRAM("EXIT-PROGRAM", false),
	GOBACK("GOBACK", false),
	IF("IF", true),
	INITIALIZE("INITIALIZE", false),
	INSPECT("INSPECT", false),
	INVOKE("INVOKE", false),
	MERGE("MERGE", false),
	MOVE("MOVE", true),
	MULTIPLY("MULTIPLY", true), 
	OPEN("OPEN", false),
	PERFORM("PERFORM", true),
	READ("READ", false),
	RELEASE("RELEASE", false),
	RETURN("RETURN", false),
	REWRITE("REWRITE", false),
	SEARCH("SEARCH", false),
	SET("SET", false),
	SORT("SORT", false),
	START("START", false),
	STOP("STOP", false),
	STRING("STRING", false),
	SUBTRACT("SUBTRACT", true),
	UNSTRING("UNSTRING", false),
	WRITE("WRITE", false),
	XMLGENERATE("XML", false),
	XMLPARSE("XML", false),
	OUTRO("", true);

	String descricao;
	boolean construido;

	ParagrafosProcedureDivision(String descricao, boolean construido) {
		this.descricao = descricao;
		this.construido = construido;
	}

	public String getDescricao() {
		return descricao;
	}

	public boolean isConstruido() {
		return construido;
	}

	public static boolean acabouParagrafoAtual(String instrucaoAtualLeitura) {
		instrucaoAtualLeitura = instrucaoAtualLeitura.replace(".", "");
		for (ParagrafosProcedureDivision paragrafo : ParagrafosProcedureDivision.values()) {
			if (paragrafo.getDescricao().equals(instrucaoAtualLeitura)) {
				return true;
			}
		}
		return false;
	}

	public static ParagrafosProcedureDivision encontraParagrafo(String instrucaoAtualLeitura) {
		instrucaoAtualLeitura = instrucaoAtualLeitura.replace(".", "");
		for(ParagrafosProcedureDivision paragrafo : ParagrafosProcedureDivision.values()) {
			if (paragrafo.getDescricao().equals(instrucaoAtualLeitura)) {
				return paragrafo;
			}
		}
		return ParagrafosProcedureDivision.OUTRO;
	}
}
