package com.trans.transpiladorCobolJava.procedureDivision;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.trans.transpiladorCobolJava.arquivo.ArquivoEscrita;
import com.trans.transpiladorCobolJava.dataDivision.DataDivision;
import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.AddParagrafo;
import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.ComputeParagrafo;
import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.DisplayParagrafo;
import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.DivideParagrafo;
import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.EvaluateParagrafo;
import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.ExecSqlParagrafo;
import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.IfParagrafo;
import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.MoveParagrafo;
import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.MultiplyParagrafo;
import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.Paragrafo;
import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.PerformParagrafo;
import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.ProcedureDivisionIf;
import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.ProcedureDivisionPerforming;
import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.ProcedureDivisionSection;
import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.ProcedureDivisionWhenEvaluate;
import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.SectionParagrafo;
import com.trans.transpiladorCobolJava.procedureDivision.Paragrafos.SubtractParagrafo;

@Component
public class ProcedureDivision {
	
	private ArrayList<Paragrafo> paragrafos = new ArrayList<Paragrafo>();

	private ArrayList<ProcedureDivisionSection> secoes = new ArrayList<ProcedureDivisionSection>();

	private ArquivoEscrita arquivoEscrita = new ArquivoEscrita();
	private String nomeClasse;
	private String path;
	private String nomePrograma;

	public ProcedureDivision() {
		super();
	}

	public ProcedureDivision(String nomeClasse, String path, String nomePrograma) {
		super();
		this.nomeClasse = nomeClasse;
		this.path = path;
		this.nomePrograma = nomePrograma;
	}

	public ProcedureDivision(String nomeClasse, String path) {
		super();
		this.nomeClasse = nomeClasse;
		this.path = path;
	}

	public void preparaAnalises(String codigoCobol, DataDivision dataDivision) {
		System.out.println("Inicio leitura da PROCEDURE DIVISION");

		codigoCobol = codigoCobol.trim().replaceAll("\\s+", " ").replaceAll("(?i)PROCEDURE\\s+DIVISION\\s*\\.", "")
				.replaceAll("(?i)\\sACCEPT\\s", "  ACCEPT ").replaceAll("(?i)\\sADD\\s", "  ADD ")
				.replaceAll("(?i)\\sALTER\\s", "  ALTER ").replaceAll("(?i)\\sCALL\\s", "  CALL ")
				.replaceAll("(?i)\\sCANCEL\\s", "  CANCEL ").replaceAll("(?i)\\sCLOSE\\s", "  CLOSE ")
				.replaceAll("(?i)\\sCOMPUTE\\s", "  COMPUTE ").replaceAll("(?i)\\sCONTINUE\\s", "  CONTINUE ")
				/* .replaceAll("(?i)\\sDELETE\\s", "  DELETE ") */.replaceAll("(?i)\\sDISPLAY\\s", "  DISPLAY ")
				.replaceAll("(?i)\\sDIVIDE\\s", "  DIVIDE ").replaceAll("(?i)\\sELSE\\s", "  ELSE ")
				.replaceAll("(?i)\\sEND-IF(\\s|\\.)", "  END-IF ")
				.replaceAll("(?i)\\sEND-EVALUATE(\\s|\\.)", "  END-EVALUATE ")
				.replaceAll("(?i)\\sEND-PERFORM(\\s|\\.)", "  END-PERFORM ").replaceAll("(?i)\\sENTRY\\s", "  ENTRY ")
				.replaceAll("(?i)\\sEVALUATE\\s", "  EVALUATE ").replaceAll("(?i)\\sEXEC\\sSQL\\s", "  EXEC SQL ")
				.replaceAll("(?i)\\sEXIT(\\s|\\.)", "  EXIT ").replaceAll("(?i)\\sEXITMETHOD(\\s|\\.)", "  EXITMETHOD ")
				.replaceAll("(?i)\\sEXITPROGRAM(\\s|\\.)", "  EXITPROGRAM ").replaceAll("(?i)\\sGOBACK\\s", "  GOBACK ")
				.replaceAll("(?i)\\sIF\\s", "  IF ").replaceAll("(?i)\\sINITIALIZE\\s", "  INITIALIZE ")
				.replaceAll("(?i)\\sINSPECT\\s", "  INSPECT ").replaceAll("(?i)\\sINVOKE\\s", "  INVOKE ")
				.replaceAll("(?i)\\sMERGE\\s", "  MERGE ").replaceAll("(?i)\\sMOVE\\s", "  MOVE ")
				.replaceAll("(?i)\\sMULTIPLY\\s", "  MULTIPLY ")
				.replaceAll("(?i)\\sNEXT\\sSENTENCE(\\s|\\.)", "  NEXT SENTENCE ")
				.replaceAll("(?i)\\sOPEN\\s", "  OPEN ").replaceAll("(?i)\\sPERFORM\\s", "  PERFORM ")
				.replaceAll("(?i)\\sREAD\\s", "  READ ").replaceAll("(?i)\\sRELEASE\\s", "  RELEASE ")
				.replaceAll("(?i)\\sRETURN(\\s|\\.)", "  RETURN ").replaceAll("(?i)\\sREWRITE\\s", "  REWRITE ")
				.replaceAll("(?i)\\sSEARCH\\s", "  SEARCH ")
				/* .replaceAll("(?i)\\sSET\\s", "  SET ") */.replaceAll("(?i)\\sSORT\\s", "  SORT ")
				.replaceAll("(?i)\\sSTART\\s", "  START ").replaceAll("(?i)\\sSTOP\\s", "  STOP ")
				.replaceAll("(?i)\\sSTRING\\s", "  STRING ").replaceAll("(?i)\\sSUBTRACT\\s", "  SUBTRACT ")
				.replaceAll("(?i)\\sUNSTRING\\s", "  UNSTRING ").replaceAll("(?i)\\sWHEN\\s", "  WHEN ")
				.replaceAll("(?i)\\sWRITE\\s", "  WRITE ").replaceAll("(?i)\\sXMLGENERATE\\s", "  XMLGENERATE ")
				.replaceAll("(?i)\\sXMLPARSE\\s", "  XMLPARSE ") + "  ";

		String regex = "((?<display>DISPLAY)\\s+(((\")([a-zA-Z0-9+-/=()*:.]+\\s*)+(\"))|(([a-zA-Z0-9-+/=()*]+\\s?)))*(\\s\\s))"
				+ "|"
				+ "((?<instrucao>[a-zA-Z0-9-]+)\\s?(((?<instrucao2>[a-zA-Z0-9-]+)\\s?)?(([a-zA-Z0-9<>=*+/'\"():,]|[-])+\\s?)*)"
				+ "([.]|(\\s\\s)))";

		Pattern pattern = Pattern.compile("(?i)" + regex);
		Matcher matcher = pattern.matcher(codigoCobol);
		analisesProcedureDivision(matcher, dataDivision, secoes);
	}

	public ArrayList<Paragrafo> analisesProcedureDivision(Matcher matcher, DataDivision atributosDataDivision,
			ArrayList<ProcedureDivisionSection> secoesProcedureDivision) {

		while (matcher.find()) {
			matcher.group();
			String instrucao = (matcher.group("instrucao") != null) ? matcher.group("instrucao").toUpperCase()
					: matcher.group("display").toUpperCase();

			ParagrafosProcedureDivision paragrafo = ParagrafosProcedureDivision.encontraParagrafo(instrucao);
			if (paragrafo == ParagrafosProcedureDivision.SECTION) {
				instrucao += (matcher.group("instrucao2") != null) ? " " + matcher.group("instrucao2").toUpperCase()
						: "";
				paragrafo = ParagrafosProcedureDivision.encontraParagrafo(instrucao);
			}

			if (paragrafo.isConstruido()) {
				switch (paragrafo) {
				case ACCEPT:
					break;
				case ADD:
					paragrafos.add(new AddParagrafo(matcher.group(), atributosDataDivision));
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
					paragrafos.add(new ComputeParagrafo(matcher.group(), atributosDataDivision));
					break;
				case CONTINUE:
					break;
				case DELETE:
					break;
				case DISPLAY:
					paragrafos.add(new DisplayParagrafo(matcher.group(), atributosDataDivision));
					break;
				case DIVIDE:
					paragrafos.add(new DivideParagrafo(matcher.group(), atributosDataDivision));
					break;
				case ELSE:
					return paragrafos;
				case ENDIF:
					return paragrafos;
				case ENDEVALUATE:
					return paragrafos;
				case ENDPERFORM:
					return paragrafos;
				case ENTRY:
					break;
				case EVALUATE:
					paragrafos.add(new EvaluateParagrafo(matcher.group(), matcher, atributosDataDivision,
							secoesProcedureDivision));
					break;
				case EXECSQL:
					paragrafos.add(new ExecSqlParagrafo(matcher.group(), atributosDataDivision));
					break;
				case EXIT:
					return paragrafos;
				case EXITMETHOD:
				case EXITPROGRAM:
					break;
				case GOBACK:
					break;
				case IF:
					paragrafos.add(
							new IfParagrafo(matcher.group(), matcher, atributosDataDivision, secoesProcedureDivision));
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
					paragrafos.add(new MoveParagrafo(matcher.group(), atributosDataDivision));
					break;
				case MULTIPLY:
					paragrafos.add(new MultiplyParagrafo(matcher.group(), atributosDataDivision));
					break;
				case NEXTSENTENSE:
					break;
				case OPEN:
					break;
				case PERFORM:
					paragrafos.add(new PerformParagrafo(matcher.group(), matcher, atributosDataDivision,
							secoesProcedureDivision));
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
					paragrafos.add(new SubtractParagrafo(matcher.group(), atributosDataDivision));
					break;
				case UNSTRING:
					break;
				case WHEN:
					return (paragrafos.isEmpty()) ? null : paragrafos;
				case WRITE:
					break;
				case XMLGENERATE:
					break;
				case XMLPARSE:
					break;
				case SECTION:
					ProcedureDivisionSection secao = new ProcedureDivisionSection(matcher.group("instrucao"));
					secao.analisesProcedureDivision(matcher, atributosDataDivision, secoesProcedureDivision);
					secoesProcedureDivision.add(secao);

					if (paragrafos.isEmpty()) {
						paragrafos.add(new SectionParagrafo(instrucao));
					}
					break;
				default:
					break;
				}
			} else {
				System.out.println("Instrução não criada: " + matcher.group());
			}

			if ((this instanceof ProcedureDivisionPerforming || this instanceof ProcedureDivisionIf
					|| this instanceof ProcedureDivisionWhenEvaluate) && (matcher.group().endsWith("."))) {
				return paragrafos;
			}

		}
		for (Paragrafo paragrafo : paragrafos) {
			if (paragrafo instanceof SectionParagrafo) {
				((SectionParagrafo) paragrafo).setImportSecao(secoesProcedureDivision);
			}
		}
		return paragrafos;
	}

	public void preTratamentoTraducaoEscrita() throws IOException {
		for (ProcedureDivisionSection secao : secoes) {
			secao.traduzEscrita(secoes);
		}
		traduzEscrita(secoes);
	}

	public void traduzEscrita(ArrayList<ProcedureDivisionSection> secoes) throws IOException {
		System.out.println("Inicio escrita da PROCEDURE DIVISION " + nomeClasse);

		if (paragrafos == null || paragrafos.isEmpty()) {
			return;
		}

		arquivoEscrita.abreArquivo(path + "\\" + nomeClasse + ".java");
		arquivoEscrita.escreveLinha("package com.trans.transpiladorCobolJava." + path + "." + nomeClasse + ";\n");

		// For para escrever atributos
		Set<String> importCompleto = new HashSet<String>();
		Set<String> declaracao = new HashSet<String>();
		List<String> textoDoMetodo = new ArrayList<String>();

		for (Paragrafo paragrafo : paragrafos) {
			Set<String> textoComImport = paragrafo.escreveImports();
			if (textoComImport != null && !textoComImport.isEmpty()) {
				importCompleto.addAll(textoComImport);
				declaracao.addAll(paragrafo.getImports());
			}
			if (paragrafo instanceof PerformParagrafo && ((PerformParagrafo) paragrafo).getNomeSecao() != null) {
				String nomeSecao = ((PerformParagrafo) paragrafo).getNomeSecao();
				for (ProcedureDivisionSection secao : secoes) {
					if (secao.getNomeClasse().equalsIgnoreCase(nomeSecao)) {
						String imprimir = new String();
						for (String elemento : secao.getImportsSecao()) {
							imprimir += elemento.toLowerCase() + ", ";
						}
						if (!imprimir.isEmpty()) {
							imprimir = imprimir.substring(0, imprimir.length() - 2);
						}
						textoDoMetodo.add(((PerformParagrafo) paragrafo).escreveArquivo(2, imprimir));
						break;
					}
				}
			} else if (paragrafo instanceof ExecSqlParagrafo) {
				textoDoMetodo.add(paragrafo.escreveArquivo(2));
			} else {
				textoDoMetodo.add(paragrafo.escreveArquivo(2));
			}
		}

		for (String elemento : importCompleto) {
			arquivoEscrita.escreveLinha(elemento);
		}

		if (nomePrograma != null && !nomePrograma.isEmpty()) {
			arquivoEscrita.escreveLinha("");
			arquivoEscrita.escreveLinha("import org.springframework.beans.factory.annotation.Autowired;");
			arquivoEscrita.escreveLinha("import org.springframework.web.bind.annotation.RequestBody;");
			arquivoEscrita.escreveLinha("import org.springframework.web.bind.annotation.PostMapping;");
			arquivoEscrita.escreveLinha("import org.springframework.web.bind.annotation.RestController;");
			arquivoEscrita.escreveLinha("");
			arquivoEscrita.escreveLinha("@RestController");
		} else {
			arquivoEscrita.escreveLinha("");
		}

		arquivoEscrita.escreveLinha("public class " + toUpperFistCase(nomeClasse) + " {");

		// For para inicializar variaveis

		if (!declaracao.isEmpty()) {
			if (this instanceof ProcedureDivisionSection) {
				String parametros = new String();
				if (!declaracao.isEmpty()) {
					for (String elemento : declaracao) {
						parametros += elemento + " " + elemento.toLowerCase() + ", ";
					}
					parametros = parametros.substring(0, parametros.length() - 2);
				}
				arquivoEscrita.escreveLinha("\n\tpublic void " + nomeClasse.toLowerCase() + "(" + parametros + "){\n");
			} else {
				String stringAutowired = "";
				if (nomePrograma != null && !nomePrograma.isEmpty()) {
					stringAutowired = "@Autowired";
					for (String elemento : declaracao) {
						arquivoEscrita.escreveLinha("\n\t" + stringAutowired + "\n\t" + toUpperFistCase(elemento) + " "
								+ elemento.toLowerCase() + ";");
					}
					arquivoEscrita.escreveLinha(
							"\n\t@PostMapping(\"/" + nomePrograma.toLowerCase().replaceAll("\\s", "").replace("\"", "") + "\")");
					arquivoEscrita.escreveLinha("\tpublic void " + nomeClasse.toLowerCase() + "(){\n");
				} else {
					for (String elemento : declaracao) {
						arquivoEscrita.escreveLinha("\n\t" + stringAutowired + "\n\t" + toUpperFistCase(elemento) + " "
								+ elemento.toLowerCase() + " = new " + toUpperFistCase(elemento) + "();");
					}
					arquivoEscrita.escreveLinha("\n\tpublic void " + nomeClasse.toLowerCase() + "(){\n");
				}
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
	}

	public String getNomeClasse() {
		return nomeClasse;
	}

	protected static String toUpperFistCase(String nome) {
		return (nome == null || nome.isEmpty()) ? null
				: nome.substring(0, 1).toUpperCase() + nome.toLowerCase().substring(1);
	}

	public Set<String> getImportsSecao() {
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
