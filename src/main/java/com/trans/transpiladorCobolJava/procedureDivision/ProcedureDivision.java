package com.trans.transpiladorCobolJava.procedureDivision;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.trans.transpiladorCobolJava.arquivo.ArquivoEscrita;
import com.trans.transpiladorCobolJava.arquivo.Codigo;
import com.trans.transpiladorCobolJava.dataDivision.DataDivision;
import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.AddParagrafo;
import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.ComputeParagrafo;
import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.DeleteParagrafo;
import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.DisplayParagrafo;
import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.DivideParagrafo;
import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.IfParagrafo;
import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.MoveParagrafo;
import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.MultiplyParagrafo;
import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.Paragrafo;
import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.PerformParagrafo;
import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.ProcedureDivisionIf;
import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.ProcedureDivisionSection;
import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.SectionParagrafo;
import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.SubtractParagrafo;

@Component
public class ProcedureDivision {

	ArrayList<Paragrafo> paragrafos = new ArrayList<Paragrafo>();
	
	ArrayList<ProcedureDivisionSection> secoes = new ArrayList<ProcedureDivisionSection>();

	ArquivoEscrita arquivoEscrita = new ArquivoEscrita();
	String nomeClasse;
	String path;
	
	public ProcedureDivision() {}

	public ProcedureDivision(String nomeClasse, String path) {
		super();
		this.nomeClasse = nomeClasse;
		this.path = path;
	}
	
	public void leitura(Codigo codigo, DataDivision dataDivision){
		leitura(codigo, dataDivision, secoes);
	}

	public ArrayList<Paragrafo> leitura(Codigo codigo, DataDivision dataDivision,
			ArrayList<ProcedureDivisionSection> secoes) {
		System.out.println("Inicio leitura da PROCEDURE DIVISION");

		for (; !codigo.isLastLine();) {

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
					paragrafos.add(new DeleteParagrafo(codigo, dataDivision));
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
					codigo.avancaPosicaoLeitura();
					if (!(this instanceof ProcedureDivisionIf)) {
						break;
					} else {
						return paragrafos;
					}
				case ENDPERFORM:
					codigo.avancaPosicaoLeitura();
					return paragrafos;
				case ENTRY:
					break;
				case EVALUATE:
					break;
				case EXIT:
					codigo.avancaPosicaoLeitura();
					return paragrafos;
				case EXITMETHOD:
				case EXITPROGRAM:
					break;
				case GOBACK:
					break;
				case IF:
					codigo.avancaPosicaoLeitura();
					paragrafos.add(new IfParagrafo(codigo, dataDivision, secoes));
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
					codigo.avancaPosicaoLeitura();
					paragrafos.add(new PerformParagrafo(codigo, dataDivision, secoes));
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
					Integer posicao = codigo.getPosicaoLeitura();
					String name = codigo.getInstrucaoAtualLeitura().replace(".", "");
					String proximo = codigo.getProximaInstrucaoLeitura().replace(".", "");
					if (ParagrafosProcedureDivision.acabouParagrafoAtual(proximo) || proximo.contains("SECTION")) {
						if (proximo.contains("SECTION")) {
							codigo.avancaPosicaoLeitura();
						}
						ProcedureDivisionSection secao = new ProcedureDivisionSection(name);
						secao.leitura(codigo, dataDivision, secoes);
						secoes.add(secao);
						if (posicao == 0 || !proximo.equals("SECTION")) {
							paragrafos.add(new SectionParagrafo(name));
						}
					}
					break;
				}
			} else {
				for (codigo.avancaPosicaoLeitura(); !codigo.getInstrucaoAtualLeitura().isEmpty()
						&& !ParagrafosProcedureDivision.acabouParagrafoAtual(codigo.getInstrucaoAtualLeitura())
						&& !codigo.getInstrucaoAtualLeitura().endsWith("."); codigo.avancaPosicaoLeitura()) {
				}
			}
		}
		for (Paragrafo paragrafo : paragrafos) {
			if (paragrafo instanceof SectionParagrafo) {
				((SectionParagrafo) paragrafo).setImportSecao(secoes);
			}
		}
		return paragrafos;
	}

	public void escreve() throws IOException{
		for(ProcedureDivisionSection secao : secoes) {
			secao.escreve(secoes);
		}
		escreve(secoes);
	}
	
	public Set<String> escreve(ArrayList<ProcedureDivisionSection> secoes) throws IOException {
		System.out.println("Inicio escrita da PROCEDURE DIVISION " + nomeClasse);

		arquivoEscrita.abreArquivo(path + "\\" + nomeClasse + ".java");
		arquivoEscrita.escreveLinha("package com.trans.transpiladorCobolJava." + path + "." + nomeClasse + ";\n");

		// For para escrever atributos
		Set<String> imports = new HashSet<String>();
		Set<String> declaracao = new HashSet<String>();
		List<String> textoDoMetodo = new ArrayList<String>();

		for (Paragrafo paragrafo : paragrafos) {
			Set<String> texto = paragrafo.escreveImports();
			if (texto != null && !texto.isEmpty()) {
				imports.addAll(texto);
				declaracao.addAll(paragrafo.getImports());
			}
			if (paragrafo instanceof PerformParagrafo && ((PerformParagrafo) paragrafo).getNomeSecao() != null) {
				String nomeSecao = ((PerformParagrafo) paragrafo).getNomeSecao();
				for (ProcedureDivisionSection secao : secoes) {
					if (secao.getNomeClasse().equals(nomeSecao)) {
						String imprimir = new String();
						for (String elemento : secao.getImports()) {
							imprimir += elemento.toLowerCase() + ", ";
						}
						if (!imprimir.isEmpty()) {
							imprimir = imprimir.substring(0, imprimir.length() - 2);
						}
						textoDoMetodo.add(paragrafo.escreveArquivo(2) + imprimir + ");");
						break;
					}
				}
			} else {
				textoDoMetodo.add(paragrafo.escreveArquivo(2));
			}
		}

		for (String elemento : imports) {
			arquivoEscrita.escreveLinha(elemento);
		}
		arquivoEscrita.escreveLinha("");

		arquivoEscrita.escreveLinha("public class " + nomeClasse + " {");

		// For para inicializar variaveis

		if (!declaracao.isEmpty()) {
			if (this instanceof ProcedureDivisionSection) {
				String parametros = new String();
				for (String elemento : declaracao) {
					parametros += elemento + " " + elemento.toLowerCase() + ", ";
				}
				parametros = parametros.substring(0, parametros.length() - 2);
				arquivoEscrita.escreveLinha("\n\tpublic void " + nomeClasse.toLowerCase() + "(" + parametros + "){\n");
			} else {
				for (String elemento : declaracao) {
					arquivoEscrita.escreveLinha(
							"\n\t" + elemento + " " + elemento.toLowerCase() + " = new " + elemento + "();");
				}
				arquivoEscrita.escreveLinha("\n\tpublic void " + nomeClasse.toLowerCase() + "(){\n");
			}
		} else {
			arquivoEscrita.escreveLinha("\n\tpublic void " + nomeClasse.toLowerCase() + "(){\n");
		}

		if (paragrafos.get(0) instanceof SectionParagrafo) {
			arquivoEscrita.escreveLinha(paragrafos.get(0).escreveArquivo(2));
		} else {
			for (String elemento : textoDoMetodo) {
				arquivoEscrita.escreveLinha(elemento);
			}
		}

		arquivoEscrita.escreveLinha("\n\t}\n}");
		arquivoEscrita.fechaArquivo();

		return null;
	}

	public String getNomeClasse() {
		return nomeClasse;
	}

	public Set<String> getImports() {
		Set<String> todosImports = new HashSet<String>();
		for (Paragrafo paragrafo : paragrafos) {
			Set<String> texto = paragrafo.getImports();
			if (texto != null && !texto.isEmpty()) {
				todosImports.addAll(texto);
			}
		}
		return todosImports;
	}

}
