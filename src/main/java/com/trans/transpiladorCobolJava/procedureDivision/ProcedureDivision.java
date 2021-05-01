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
import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.ParagrafoImpl;

public class ProcedureDivision {

	ArrayList<ParagrafoImpl> paragrafos = new ArrayList<ParagrafoImpl>();

	public void popula(String codigoCobol, DataDivision dataDivision) {
		System.out.println("Inicio leitura da PROCEDURE DIVISION");

		if (codigoCobol.isEmpty()) {
			System.out.println("PROCEDURE DIVISION vazia.");
			return;
		}

		Codigo codigoEmSecoes = new Codigo(codigoCobol.split("SECTION"));

		for (String secao : codigoEmSecoes.getCodigoCobol()) {
			Codigo umParagrafo = new Codigo(secao.split("\\.\\s+"));

			for (String porPalavraString : umParagrafo.getCodigoCobol()) {
				Codigo porPalavra = new Codigo(porPalavraString.split("\\s+"));
				while (!porPalavra.isOver()
						&& ParagrafosProcedureDivision.acabouParagrafoAtual(porPalavra.getInstrucaoAtualLeitura())) {

					ParagrafosProcedureDivision paragrafo = ParagrafosProcedureDivision
							.valueOf(porPalavra.getInstrucaoAtualLeitura());

					if (paragrafo.isConstruido()) {
						// Eh uma paragrafo a ser construido
						switch (paragrafo) {
						case ACCEPT:
							// paragrafos.add(new AcceptParagrafo(umaSecao, dataDivision));
							break;
						case ADD:
							porPalavra.avancaPosicaoLeitura();
							paragrafos.add(new AddParagrafo(porPalavra, dataDivision));
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
							porPalavra.avancaPosicaoLeitura();
							paragrafos.add(new DisplayParagrafo(porPalavra, dataDivision));
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
						porPalavra.avancaPosicaoLeitura();
					}
					for (; !porPalavra.getInstrucaoAtualLeitura().isEmpty() && !ParagrafosProcedureDivision
							.acabouParagrafoAtual(porPalavra.getInstrucaoAtualLeitura()); porPalavra
									.avancaPosicaoLeitura()) {
					}
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
		for (ParagrafoImpl paragrafo : paragrafos) {
			Set<String> texto = paragrafo.escreveImports();
			if (texto != null && !texto.isEmpty()) {
				imports.addAll(texto);
				declaracao.addAll(paragrafo.getImports());
			}
		}
		arquivoEscrita.escreveLinha(imports.toString().replaceAll("\\[|\\]|\\,", ""));

		arquivoEscrita.escreveLinha("public class " + nomeClasse + " {");

		// For para inicializar variaveis
		for (String elemento : declaracao) {
			arquivoEscrita
					.escreveLinha("\n\t" + elemento + " " + toLowerFistCase(elemento) + " = new " + elemento + "();");
		}

		arquivoEscrita.escreveLinha("\tpublic void home(){\n");

		// For para escrever
		for (ParagrafoImpl paragrafo : paragrafos) {
			arquivoEscrita.escreveLinha(paragrafo.escreveArquivo());
		}
		arquivoEscrita.escreveLinha("\n\t}\n}");
		arquivoEscrita.fechaArquivo();
	}

	private static String toLowerFistCase(String nome) {
		return nome.substring(0, 1).toLowerCase() + nome.substring(1);
	}

}
