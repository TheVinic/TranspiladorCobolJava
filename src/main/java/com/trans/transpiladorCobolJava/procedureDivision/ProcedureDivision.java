package com.trans.transpiladorCobolJava.procedureDivision;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.trans.transpiladorCobolJava.arquivo.ArquivoEscrita;
import com.trans.transpiladorCobolJava.arquivo.Codigo;
import com.trans.transpiladorCobolJava.dataDivision.DataDivision;
import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.AddParagrafo;
import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.ComputeParagrafo;
import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.DisplayParagrafo;
import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.DivideParagrafo;
import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.IfParagrafo;
import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.MoveParagrafo;
import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.MultiplyParagrafo;
import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.Paragrafo;
import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.ParagrafoImpl;
import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.SubtractParagrafo;

@Component
public class ProcedureDivision {

	ArrayList<Paragrafo> paragrafos = new ArrayList<Paragrafo>();

	public ArrayList<Paragrafo> analiseSemantica(Codigo codigo, DataDivision dataDivision) {
		boolean inicio = (codigo.getPosicaoLeitura() == 0) ? true : false;
		System.out.println("Inicio leitura da PROCEDURE DIVISION");

		for (; !codigo.isOver();) {

			ParagrafosProcedureDivision paragrafo = ParagrafosProcedureDivision
					.encontraParagrafo(codigo.getInstrucaoAtualLeitura());

			if (paragrafo.isConstruido()) {
				// Eh uma paragrafo a ser construido
				switch (paragrafo) {
				case ACCEPT:
					// paragrafos.add(new AcceptParagrafo(umaSecao, dataDivision));
					break;
				case ADD:
					codigo.avancaPosicaoLeitura();
					paragrafos.add(new AddParagrafo(codigo, dataDivision));
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
					codigo.avancaPosicaoLeitura();
					paragrafos.add(new ComputeParagrafo(codigo, dataDivision));
					break;
				case CONTINUE:
					break;
				case DELETE:
					break;
				case DISPLAY:
					codigo.avancaPosicaoLeitura();
					paragrafos.add(new DisplayParagrafo(codigo, dataDivision));
					break;
				case DIVIDE:
					codigo.avancaPosicaoLeitura();
					paragrafos.add(new DivideParagrafo(codigo, dataDivision));
					break;
				case ELSE:
					return paragrafos;
				case ENDIF:
					// TODO validar o if inicial
					if (inicio) {
						codigo.avancaPosicaoLeitura();
						break;
					} else {
						return paragrafos;
					}
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
					codigo.avancaPosicaoLeitura();
					paragrafos.add(new IfParagrafo(codigo, dataDivision));
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
					codigo.avancaPosicaoLeitura();
					paragrafos.add(new MoveParagrafo(codigo, dataDivision));
					break;
				case MULTIPLY:
					codigo.avancaPosicaoLeitura();
					paragrafos.add(new MultiplyParagrafo(codigo, dataDivision));
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
					codigo.avancaPosicaoLeitura();
					paragrafos.add(new SubtractParagrafo(codigo, dataDivision));
					break;
				case UNSTRING:
					break;
				case WRITE:
					break;
				case XMLGENERATE:
					break;
				case XMLPARSE:
					break;
				case OUTRO:
					break;
				default:
					break;
				}
			} else {
				codigo.avancaPosicaoLeitura();
			}
			for (; !codigo.getInstrucaoAtualLeitura().isEmpty()
					&& !ParagrafosProcedureDivision.acabouParagrafoAtual(codigo.getInstrucaoAtualLeitura()); codigo
							.avancaPosicaoLeitura()) {
			}

		}
		return paragrafos;
	}

	ArquivoEscrita arquivoEscrita = new ArquivoEscrita();
	String nomeClasse = "Controller";
	String path = "Controller";

	public void escreve() throws IOException {
		System.out.println("Inicio escrita da PROCEDURE DIVISION");

		arquivoEscrita.abreArquivo(path + "\\" + nomeClasse + ".java");
		arquivoEscrita.escreveLinha("package com.trans.transpiladorCobolJava." + path + "." + nomeClasse + ";\n");

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
		for (String elemento : declaracao) {
			arquivoEscrita
					.escreveLinha("\n\t" + elemento + " " + toLowerFistCase(elemento) + " = new " + elemento + "();");
		}

		arquivoEscrita.escreveLinha("\n\tpublic void home(){\n");

		// For para escrever
		for (ParagrafoImpl paragrafo : paragrafos) {
			arquivoEscrita.escreveLinha(paragrafo.escreveArquivo(2));
		}
		arquivoEscrita.escreveLinha("\n\t}\n}");
		arquivoEscrita.fechaArquivo();
	}

	private static String toLowerFistCase(String nome) {
		return nome.substring(0, 1).toLowerCase() + nome.substring(1);
	}

}
