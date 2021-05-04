package com.trans.transpiladorCobolJava.procedureDivision;

public enum ParagrafosProcedureDivision {
	ACCEPT("ACCEPT", false),
	ADD("ADD", true),
	ALTER("ALTER", false),
	CALL("CALL", false),
	CANCEL("CANCEL", false),
	CLOSE("CLOSE", false),
	COMPUTE("COMPUTE", false),
	CONTINUE("CONTINUE", false),
	DELETE("DELETE", false),
	DISPLAY("DISPLAY", true),
	DIVIDE("DIVIDE", true),
	ENTRY("ENTRY", false),
	EVALUATE("EVALUATE", false),
	EXIT("EXIT", false),
	EXITMETHOD("EXIT", false),
	EXITPROGRAM("EXIT", false),
	GOBACK("GOBACK", false),
	IF("IF", false),
	INITIALIZE("INITIALIZE", false),
	INSPECT("INSPECT", false),
	INVOKE("INVOKE", false),
	MERGE("MERGE", false),
	MOVE("MOVE", false),
	MULTIPLY("MULTIPLY", false), 
	OPEN("OPEN", false),
	PERFORM("PERFORM", false),
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
	SUBTRACT("SUBTRACT", false),
	UNSTRING("UNSTRING", false),
	WRITE("WRITE", false),
	XMLGENERATE("XML", false),
	XMLPARSE("XML", false);

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
		for (ParagrafosProcedureDivision paragrafo : ParagrafosProcedureDivision.values()) {
			if (paragrafo.getDescricao().equals(instrucaoAtualLeitura)) {
				return true;
			}
		}
		return false;
	}
}
