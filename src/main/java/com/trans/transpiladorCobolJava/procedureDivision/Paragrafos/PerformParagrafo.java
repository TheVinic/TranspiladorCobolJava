package com.trans.transpiladorCobolJava.procedureDivision.Paragrafos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.trans.transpiladorCobolJava.dataDivision.DataDivision;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.Atributo;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.AtributoElementar;

public class PerformParagrafo extends Paragrafo {

	String nomeSecao;
	String nomeSecaoFinaliza;

	ArrayList<Paragrafo> instrucoes;

	Atributo times;

	String test;

	Atributo varying;
	Atributo from;
	Atributo by;
	ArrayList<Atributo> until = new ArrayList<Atributo>();

	public PerformParagrafo(String instrucao, Matcher matcherGeral, DataDivision dataDivision,
			ArrayList<ProcedureDivisionSection> secoes) {
		instrucao += " ";
		instrucao = instrucao.replaceAll("(?i)\\sEND-PERFORM\\s", "  END-PERFORM ")
				.replaceAll("(?i)\\sTHROUGHT\\s", "  THROUGHT ").replaceAll("(?i)\\sTHRU\\s", "  THRU ")
				.replaceAll("(?i)\\sTIMES\\s", "  TIMES ").replaceAll("(?i)\\sTIMES\\.", "  TIMES.")
				.replaceAll("(?i)\\sUNTIL\\s", "  UNTIL ").replaceAll("(?i)\\sVARYING\\s", "  VARYING ")
				.replaceAll("(?i)(\\sWITH\\sTEST\\s)|(\\sTEST\\s)", "  TEST ");

		String regex1 = "(?i)";
		String regexProcedureName = "PERFORM\\s(?<procedureName1>[a-zA-Z0-9-]+)(\\s\\s(THROUGH|THRU)\\s(?<procedureName2>))?(\\s(?<times1>(\\w)+)\\s\\sTIMES)?"
				+ "(\\s\\sTEST\\s(?<order1>(BEFORE)|(AFTER)))?"
				+ "(\\s\\sVARYING\\s(?<varying1>(\\w)+)\\sFROM\\s(?<from1>(\\w)+)\\sBY\\s(?<by1>(\\w)+))?(\\s\\sUNTIL\\s(?<until1>((\\w+)|([<]|[>])[=]|[+/()<>=]|[-])+))?"
				+ "(\\s(?<phrase2>(AFTER\\s(\\w)+\\sFROM\\s(\\w)+\\sBY\\s(\\w)+\\sUNTIL\\s(\\w)+)*))?";
		String regexImperativeStatement = "PERFORM(\\s(?<times2>(\\w)+)\\s\\sTIMES)?(\\s\\sTEST\\s(?<order2>(BEFORE)|(AFTER)))?"
				+ "(\\s\\sVARYING\\s(?<varying2>(\\w)+)\\sFROM\\s(?<from2>(\\w)+)\\sBY\\s(?<by2>(\\w)+))?"
				+ "(\\s\\sUNTIL\\s(?<until2>((\\w+)|([<]|[>])[=]|[+/()<>=]|[-]|[*]{2})+))?";

		Pattern pattern = Pattern.compile(regex1 + "((?<procedureName>" + regexProcedureName
				+ ")|(?<imperativeStatement>" + regexImperativeStatement + "))");
		Matcher matcher = pattern.matcher(instrucao);

		if (matcher.find()) {
			matcher.group();
			matcher.group("imperativeStatement");
			matcher.group("procedureName");
			matcher.group("procedureName2");
			matcher.group("order1");
			matcher.group("varying1");
			matcher.group("from1");
			matcher.group("by1");
			matcher.group("until1");
			matcher.group("phrase2");

			if (matcher.group("procedureName") != null) {
				nomeSecao = matcher.group("procedureName1");
				nomeSecaoFinaliza = matcher.group("procedureName2");
				test = matcher.group("order1");

				// VARYING
				if (matcher.group("varying1") != null) {
					matcherOf = patternOf.matcher(matcher.group("varying1"));
					if (matcherOf.find()) {
						Atributo atributo = validaAtributo(dataDivision);
						varying = atributo;
						if (atributo.getClasses() != null) {
							imports.add(atributo.getImport());
						}
					}
					if (matcher.group("from1") != null) {
						matcherOf = patternOf.matcher(matcher.group("from1"));
						if (matcherOf.find()) {
							Atributo atributo = validaAtributo(dataDivision);
							from = atributo;
							if (atributo.getClasses() != null) {
								imports.add(atributo.getImport());
							}
						}
					}
					if (matcher.group("by1") != null) {
						matcherOf = patternOf.matcher(matcher.group("by1"));
						if (matcherOf.find()) {
							Atributo atributo = validaAtributo(dataDivision);
							by = atributo;
							if (atributo.getClasses() != null) {
								imports.add(atributo.getImport());
							}
						}
					}
				}
				if (matcher.group("until1") != null) {
					matcherOf = patternOf.matcher(matcher.group("until1"));
					while (matcherOf.find()) {
						Atributo atributo = validaAtributo(dataDivision);
						until.add(atributo);
						if (atributo.getClasses() != null) {
							imports.add(atributo.getImport());
						}
					}
				}

				// TIMES
				if (matcher.group("times1") != null) {
					matcherOf = patternOf.matcher(matcher.group("times1"));
					if (matcherOf.find()) {
						Atributo atributo = validaAtributo(dataDivision);
						times = atributo;
						if (atributo.getClasses() != null) {
							imports.add(atributo.getImport());
						}
					}
				}
			} else {
				test = matcher.group("order2");

				// VARYING
				if (matcher.group("varying2") != null) {
					matcherOf = patternOf.matcher(matcher.group("varying2"));
					if (matcherOf.find()) {
						Atributo atributo = validaAtributo(dataDivision);
						varying = atributo;
						if (atributo.getClasses() != null) {
							imports.add(atributo.getImport());
						}
					}
					if (matcher.group("from2") != null) {
						matcherOf = patternOf.matcher(matcher.group("from2"));
						if (matcherOf.find()) {
							Atributo atributo = validaAtributo(dataDivision);
							from = atributo;
							if (atributo.getClasses() != null) {
								imports.add(atributo.getImport());
							}
						}
					}
					if (matcher.group("by2") != null) {
						matcherOf = patternOf.matcher(matcher.group("by2"));
						if (matcherOf.find()) {
							Atributo atributo = validaAtributo(dataDivision);
							by = atributo;
							if (atributo.getClasses() != null) {
								imports.add(atributo.getImport());
							}
						}
					}
				}

				// UNTIL
				if (matcher.group("until2") != null) {
					matcherOf = patternOf.matcher(matcher.group("until2"));
					while (matcherOf.find()) {
						Atributo atributo = validaAtributo(dataDivision);
						until.add(atributo);
						if (atributo.getClasses() != null) {
							imports.add(atributo.getImport());
						}
					}
				}

				// TIMES
				if (matcher.group("times2") != null) {
					matcherOf = patternOf.matcher(matcher.group("times2"));
					if (matcherOf.find()) {
						Atributo atributo = validaAtributo(dataDivision);
						times = atributo;
						if (atributo.getClasses() != null) {
							imports.add(atributo.getImport());
						}
					}
				}

				instrucoes = new ProcedureDivisionPerforming().leitura(matcherGeral, dataDivision, secoes);
			}
		}
	}

	public String escreveArquivo(Integer nivel, String importsSection) {
		String imprimirCondicao = new String();
		if (nomeSecao == null && times == null && varying == null) {
			for (Paragrafo paragrafo : instrucoes) {
				imprimirCondicao += paragrafo.escreveArquivo(nivel);
			}
			return imprimirCondicao;
		} else if (nomeSecao == null) {
			if (times != null) {
				imprimirCondicao += fazTabulacao(nivel) + "for (int i=0; i<" + times.getStringEscritaPorTipo()
						+ "; i++){\n";
			} else if (varying != null) {
				imprimirCondicao += fazTabulacao(nivel) + "for (" + toLowerFistCase(varying.getClassesSucessoras())
						+ varying.getSentencaSet(((from.getNome() == null) ? ((AtributoElementar) from).getValor()
								: from.getStringEscritaPorTipo()))
						+ "; ";
				for (Atributo elemento : until) {
					imprimirCondicao += elemento.getStringEscritaPorTipo() + " ";
				}
				imprimirCondicao += "; ";
				imprimirCondicao += toLowerFistCase(varying.getClassesSucessoras()) + varying
						.getSentencaSet(toLowerFistCase(varying.getClassesSucessoras()) + varying.getSentencaGet()
								+ " + " + (((by.getNome() == null) ? ((AtributoElementar) by).getValor()
										: by.getStringEscritaPorTipo())))
						+ "){\n";
			} else if (test == null || test.equals("BEFORE")) {
				imprimirCondicao += fazTabulacao(nivel) + "while (";
				for (Atributo elemento : until) {
					imprimirCondicao += elemento.getStringEscritaPorTipo() + " ";
				}
				imprimirCondicao += ") {\n";
			} else {
				imprimirCondicao += "do{\n";
			}
			for (Paragrafo paragrafo : instrucoes) {
				imprimirCondicao += paragrafo.escreveArquivo(nivel + 1);
			}
			if (test != null && test.equals("AFTER")) {
				imprimirCondicao += "} while (";
				for (Atributo elemento : until) {
					imprimirCondicao += elemento.getStringEscritaPorTipo() + " ";
				}
				imprimirCondicao += ");\n";
			} else {
				imprimirCondicao += fazTabulacao(nivel) + "}\n";
			}
		} else {
			if (times != null) {
				imprimirCondicao += fazTabulacao(nivel) + "for (int i=0; i<" + times.getStringEscritaPorTipo()
						+ "; i++){\n";
			} else if (varying != null) {
				imprimirCondicao += fazTabulacao(nivel) + "for (" + toLowerFistCase(varying.getClassesSucessoras())
						+ varying.getSentencaSet(((from.getNome() == null) ? ((AtributoElementar) from).getValor()
								: from.getStringEscritaPorTipo()))
						+ "; ";
				for (Atributo elemento : until) {
					imprimirCondicao += elemento.getStringEscritaPorTipo() + " ";
				}
				imprimirCondicao += "; ";
				imprimirCondicao += toLowerFistCase(varying.getClassesSucessoras()) + varying
						.getSentencaSet(toLowerFistCase(varying.getClassesSucessoras()) + varying.getSentencaGet()
								+ " + " + (((by.getNome() == null) ? ((AtributoElementar) by).getValor()
										: by.getStringEscritaPorTipo())))
						+ "){\n";
			} else if (test == null || test.equals("BEFORE")) {
				imprimirCondicao += fazTabulacao(nivel) + "while (";
				for (Atributo elemento : until) {
					imprimirCondicao += elemento.getStringEscritaPorTipo() + " ";
				}
			} else {
				imprimirCondicao += "do{\n";
			}
			imprimirCondicao += fazTabulacao(nivel+1) + nomeSecao.toLowerCase() + "." + nomeSecao.toLowerCase() + "(" + importsSection + ");\n";
			if (test != null && test.equals("AFTER")) {
				imprimirCondicao += "} while (";
				for (Atributo elemento : until) {
					imprimirCondicao += elemento.getStringEscritaPorTipo() + " ";
				}
				imprimirCondicao += ");\n";
			} else {
				imprimirCondicao += fazTabulacao(nivel) + "}\n";

			}
		}
		return imprimirCondicao;
	}

	@Override
	public Set<String> escreveImports() {
		Set<String> importsDosParagrafos = new HashSet<String>();

		importsDosParagrafos = escreveImportsParagrago(imports);

		if (instrucoes != null) {
			for (Paragrafo elemento : instrucoes) {
				if (!(elemento instanceof PerformParagrafo)) {
					importsDosParagrafos.addAll(elemento.escreveImports());
				}
			}
		}
		if (nomeSecao != null) {
			importsDosParagrafos.add("import com.trans.transpiladorCobolJava." + nomeSecao + ";");
		}

		return importsDosParagrafos;
	}

	@Override
	public Set<String> getImports() {
		Set<String> importsDosParagrafos = new HashSet<String>();

		importsDosParagrafos = imports;

		if (instrucoes != null) {
			for (Paragrafo elemento : instrucoes) {
				if (!(elemento instanceof PerformParagrafo)) {
					importsDosParagrafos.addAll(elemento.getImports());
				}
			}
		}

		if (nomeSecao != null) {
			importsDosParagrafos.add(nomeSecao);
		}

		return importsDosParagrafos;
	}

	public String getNomeSecao() {
		return nomeSecao;
	}

	@Override
	public String escreveArquivo(Integer nivel) {
		String imprimirCondicao = new String();
		if (nomeSecao == null && times == null && varying == null) {
			for (Paragrafo paragrafo : instrucoes) {
				imprimirCondicao += paragrafo.escreveArquivo(nivel);
			}
			return imprimirCondicao;
		} else if (nomeSecao == null) {
			if (times != null) {
				imprimirCondicao += fazTabulacao(nivel) + "for (int i=0; i<" + times.getStringEscritaPorTipo()
						+ "; i++){\n";
			} else if (varying != null) {
				imprimirCondicao += fazTabulacao(nivel) + "for (" + toLowerFistCase(varying.getClassesSucessoras())
						+ varying.getSentencaSet(((from.getNome() == null) ? ((AtributoElementar) from).getValor()
								: from.getStringEscritaPorTipo()))
						+ "; ";
				for (Atributo elemento : until) {
					imprimirCondicao += elemento.getStringEscritaPorTipo() + " ";
				}
				imprimirCondicao += "; ";
				imprimirCondicao += toLowerFistCase(varying.getClassesSucessoras()) + varying
						.getSentencaSet(toLowerFistCase(varying.getClassesSucessoras()) + varying.getSentencaGet()
								+ " + " + (((by.getNome() == null) ? ((AtributoElementar) by).getValor()
										: by.getStringEscritaPorTipo())))
						+ "){\n";
			} else if (test == null || test.equals("BEFORE")) {
				imprimirCondicao += fazTabulacao(nivel) + "while (";
				for (Atributo elemento : until) {
					imprimirCondicao += elemento.getStringEscritaPorTipo() + " ";
				}
				imprimirCondicao += ") {\n";
			} else {
				imprimirCondicao += "do{\n";
			}
			for (Paragrafo paragrafo : instrucoes) {
				imprimirCondicao += paragrafo.escreveArquivo(nivel + 1);
			}
			if (test != null && test.equals("AFTER")) {
				imprimirCondicao += "} while (";
				for (Atributo elemento : until) {
					imprimirCondicao += elemento.getStringEscritaPorTipo() + " ";
				}
				imprimirCondicao += ");\n";
			} else {
				imprimirCondicao += fazTabulacao(nivel) + "}\n";
			}
		}
		return imprimirCondicao;
	}

}
