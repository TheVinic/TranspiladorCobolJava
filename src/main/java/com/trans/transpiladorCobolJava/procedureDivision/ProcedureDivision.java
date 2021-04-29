package com.trans.transpiladorCobolJava.procedureDivision;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.trans.transpiladorCobolJava.arquivo.ArquivoEscrita;
import com.trans.transpiladorCobolJava.arquivo.Codigo;
import com.trans.transpiladorCobolJava.dataDivision.DataDivision;
import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.AddParagrafo;
import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.DisplayParagrafo;
import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.Paragrafo;

public class ProcedureDivision {

	ArrayList<Paragrafo> paragrafos = new ArrayList<Paragrafo>();

	public void popula(String codigoCobol, DataDivision dataDivision) {
		System.out.println("Inicio leitura da PROCEDURE DIVISION");

		if (codigoCobol.isEmpty()) {
			System.out.println("PROCEDURE DIVISION vazia.");
			return;
		}

		Codigo codigoEmSecoes = new Codigo(codigoCobol.split("SECTION"));

		for (String secao : codigoEmSecoes.getCodigoCobol()) {
			Codigo umaSecao = new Codigo(secao.split("\\s+"));
			while (!umaSecao.isOver()
					&& ParagrafosProcedureDivision.acabouParagrafoAtual(umaSecao.getInstrucaoAtualLeitura())) {
				
				ParagrafosProcedureDivision paragrafo = ParagrafosProcedureDivision
						.valueOf(umaSecao.getInstrucaoAtualLeitura());

				if (paragrafo.isConstruido()) {
					// Eh uma paragrafo a ser construido
					switch (paragrafo) {
					case ACCEPT:
						// paragrafos.add(new AcceptParagrafo(umaSecao, dataDivision));
						break;
					case ADD:
						umaSecao.avancaPosicaoLeitura();
						paragrafos.add(new AddParagrafo(umaSecao, dataDivision));
						break;
					case ALTER:
						break;
					case CALL:
						break;
					case CANCEL:
						break;
					case CLOSE:
						break;
					case COMPUTE:
						break;
					case CONTINUE:
						break;
					case DELETE:
						break;
					case DISPLAY:
						umaSecao.avancaPosicaoLeitura();
						paragrafos.add(new DisplayParagrafo(umaSecao, dataDivision));
						break;
					case DIVIDE:
						break;
					case ENTRY:
						break;
					case EVALUATE:
						break;
					case EXIT:
					case EXITMETHOD:
					case EXITPROGRAM:
						break;
					case GOBACK:
						break;
					case IF:
						break;
					case INITIALIZE:
						break;
					case INSPECT:
						break;
					case INVOKE:
						break;
					case MERGE:
						break;
					case MOVE:
						break;
					case MULTIPLY:
						break;
					case OPEN:
						break;
					case PERFORM:
						break;
					case READ:
						break;
					case RELEASE:
						break;
					case RETURN:
						break;
					case REWRITE:
						break;
					case SEARCH:
						break;
					case SET:
						break;
					case SORT:
						break;
					case START:
						break;
					case STOP:
						break;
					case STRING:
						break;
					case SUBTRACT:
						break;
					case UNSTRING:
						break;
					case WRITE:
						break;
					case XMLGENERATE:
						break;
					case XMLPARSE:
						break;
					default:
						break;
					}
				} else {
					umaSecao.avancaPosicaoLeitura();
				}
				for (; !umaSecao.getInstrucaoAtualLeitura().isEmpty() && !ParagrafosProcedureDivision
						.acabouParagrafoAtual(umaSecao.getInstrucaoAtualLeitura()); umaSecao.avancaPosicaoLeitura()) {
				}
			}

		}
	}

	ArquivoEscrita arquivoEscrita = new ArquivoEscrita();
	String nomeClasse = "Controller";

	public void escreve() throws IOException {
		System.out.println("Inicio escrita da PROCEDURE DIVISION");

		arquivoEscrita.abreArquivo("model\\" + nomeClasse + ".java");
		arquivoEscrita.escreveLinha("package com.trans.transpiladorCobolJava.model." + nomeClasse + ";\n");

		// For para escrever atributos
		Set<String> imports = new HashSet<String>();
		Set<String> declaracao = new HashSet<String>();
		for (Paragrafo paragrafo : paragrafos) {
			Set<String> texto = paragrafo.escreveImports();
			if (texto != null && !texto.isEmpty()) {
				imports.addAll(texto);
				declaracao.addAll(paragrafo.getImports());
			}
		}
		arquivoEscrita.escreveLinha(imports.toString().replaceAll("\\[|\\]|\\,", ""));

		arquivoEscrita.escreveLinha("public class " + nomeClasse + " {");

		// For para inicializar variaveis
		for(String elemento : declaracao) {
			arquivoEscrita.escreveLinha("\n\t" + elemento + " " + toLowerFistCase(elemento) + " = new " + elemento + "();\n");
		}
		
		arquivoEscrita.escreveLinha("\tpublic void home(){\n");
		
		// For para escrever
		for (Paragrafo paragrafo : paragrafos) {
			arquivoEscrita.escreveLinha(paragrafo.escreveArquivo());
		}
		arquivoEscrita.escreveLinha("\n\t}\n}");
		arquivoEscrita.fechaArquivo();
	}
	
	private static String toLowerFistCase(String nome) {
		return nome.substring(0, 1).toLowerCase() + nome.substring(1);
	}
	
}
