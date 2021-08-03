package com.trans.transpiladorCobolJava.procedureDivision.Paragrafos;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.data.jpa.repository.Query;

import com.trans.transpiladorCobolJava.dataDivision.DataDivision;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.Atributo;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.AtributoElementar;

public class ExecSqlParagrafo extends Paragrafo {

	String nomeQuery;

	String declaracao;
	ArrayList<String> parametros = new ArrayList<String>();
	ArrayList<String> intoInsert = new ArrayList<String>();
	ArrayList<Atributo> intoSelect = new ArrayList<Atributo>();
	ArrayList<String> whereEsquerda = new ArrayList<String>();
	ArrayList<String> whereOperacao = new ArrayList<String>();
	ArrayList<ArrayList<Atributo>> whereDireita = new ArrayList<ArrayList<Atributo>>();
	ArrayList<String> whereLigacao = new ArrayList<String>();
	// TODO varias tabelas
	ArrayList<Atributo> tabelas = new ArrayList<Atributo>();
	ArrayList<String> set = new ArrayList<String>();
	ArrayList<Atributo> values = new ArrayList<Atributo>();

	private boolean isWhere = false;

	/**
	 * @param instrucao
	 * @param dataDivision
	 */
	public ExecSqlParagrafo(String instrucao, DataDivision dataDivision) {

		instrucao = instrucao.trim().replaceAll("(?i)\\sEND-EXEC\\s", "  END-EXEC ")
				.replaceAll("(?i)\\sFROM\\s", "  FROM ").replaceAll("(?i)\\sINNER\\sJOIN\\s", "  INNER JOIN ")
				.replaceAll("(?i)\\sINTO\\s", "  INTO ").replaceAll("(?i)\\sON\\s", "  ON ")
				.replaceAll("(?i)\\sSET\\s", "  SET ").replaceAll("(?i)\\sVALUES\\s", "  VALUES ")
				.replaceAll("(?i)\\sWHERE\\s", "  WHERE ");

		String regexDeclaracao = "(?i)(?<declaracao>(SELECT|UPDATE|DELETE|INSERT))\\s(?<parametros>([a-zA-Z0-9-,.]+\\s)+)?";
		String regexInto = "(\\sINTO\\s(?<into>([a-zA-Z0-9-()-,:]+\\s?)+))?";
		String regexSet = "(\\sSET\\s(?<set>([a-zA-Z0-9:',-=\"]+\\s?)))?";
		String regexFrom = "((\\sFROM\\s(?<from>([a-zA-Z0-9-,.]+\\s?)+)))?";
		String regexInnerJoin = "(\\sINNER\sJOIN\\s(?<innerJoin>([a-zA-Z0-9-,.]+\\s)+)\\sON\\s(?<on>([a-zA-Z0-9-,.=]+\\s?)+))?";
		String regexValues = "(\\s((VALUES)|(VALUE))\\s(?<values>([a-zA-Z0-9:()\"',-]+\\s?)+))?";
		String regexWhere = "(\\sWHERE\\s(?<where>([a-zA-Z0-9=\"',-.:<>()]+\\s?)+))?";
		String regexEnd = "\\sEND-EXEC";

		Pattern pattern = Pattern.compile(regexDeclaracao + regexInto + regexSet + regexFrom + regexInnerJoin
				+ regexValues + regexWhere + regexEnd);
		Matcher matcher = pattern.matcher(instrucao);

		// TODO colocar tratamento para STRING

		if (matcher.find()) {
			declaracao = matcher.group("declaracao");

			// TODO terminar INNER JOING e ORDER BY

			switch (matcher.group("declaracao")) {
			case "SELECT":
				intoSelect(dataDivision, matcher);
				parametros(matcher);
				where(dataDivision, matcher);
				from(dataDivision, matcher);
				break;
			case "UPDATE":
				tabelaUpdate(dataDivision, matcher);
				set(dataDivision, matcher);
				where(dataDivision, matcher);
				break;
			case "DELETE":
				from(dataDivision, matcher);
				where(dataDivision, matcher);
				break;
			case "INSERT":
				intoInsert(dataDivision, matcher);
				values(dataDivision, matcher);
				break;
			}

		} else {
			System.out.println("Erro na leitura da instrução. Recebida: \"" + instrucao + "\"");
		}
		for (Atributo elemento : tabelas) {
			dataDivision.setIntrucaoRepository(getInstrucao(), elemento.getNome());
		}
		imports.add(toUpperFistCase(tabelas.get(0).getNome().toLowerCase() + "Repository"));
	}

	private void set(DataDivision dataDivision, Matcher matcher) {
		Pattern patternInterno;
		Matcher matcherInterno;
		patternInterno = Pattern.compile("(?<set>[a-zA-Z0-9-]+)\\s?[=][:]\\s?(?<value>[a-zA-Z0-9-]+)");
		matcherInterno = patternInterno.matcher(matcher.group("set"));
		// TODO colocar tratamento string
		while (matcherInterno.find()) {
			set.add(matcherInterno.group("set"));
			matcherOf = patternOf.matcher(matcherInterno.group("value"));
			if (matcherOf.find()) {
				Atributo atributo = validaAtributo(dataDivision);
				values.add(atributo);
				if (atributo.getClasses() != null) {
					imports.add(atributo.getImport());
				}
			}
		}
	}

	private void tabelaUpdate(DataDivision dataDivision, Matcher matcher) {
		matcherOf = patternOf.matcher(matcher.group("parametros"));
		if (matcherOf.find()) {
			Atributo atributo = validaAtributo(dataDivision);
			tabelas.add(atributo);
			if (atributo.getClasses() != null) {
				imports.add(atributo.getImport());
			}
			nomeQuery = dataDivision.setAtributoIsTabela(matcherOf.group()) + declaracao;
		}
	}

	private void values(DataDivision dataDivision, Matcher matcher) {
		Pattern patternInterno;
		Matcher matcherInterno;
		patternInterno = Pattern.compile("[:](?<values>[a-zA-Z0-9-]+)");
		// TODO colocar tratamento string
		matcherInterno = patternInterno.matcher(matcher.group("values"));
		while (matcherInterno.find()) {
			matcherOf = patternOf.matcher(matcherInterno.group("values"));
			if (matcherOf.find()) {
				Atributo atributo = validaAtributo(dataDivision);
				values.add(atributo);
				if (atributo.getClasses() != null) {
					imports.add(atributo.getImport());
				}
			}
		}
	}

	private void intoInsert(DataDivision dataDivision, Matcher matcher) {
		Pattern patternInterno;
		Matcher matcherInterno;
		patternInterno = Pattern.compile("(?<tabela>[a-zA-Z0-9-]+)\\s?[(]\\s?(?<into>[a-zA-Z0-9-, ]+)\\s?[)]");
		matcherInterno = patternInterno.matcher(matcher.group("into"));
		if (matcherInterno.find()) {
			matcherOf = patternOf.matcher(matcherInterno.group("tabela"));
			if (matcherOf.find()) {
				Atributo atributo = validaAtributo(dataDivision);
				tabelas.add(atributo);
				if (atributo.getClasses() != null) {
					imports.add(atributo.getImport());
				}
			}
			// Set atributo como tabela
			nomeQuery = dataDivision.setAtributoIsTabela(matcherInterno.group("tabela")) + declaracao;
			Pattern patternInternoInto;
			Matcher matcherInternoInto;
			patternInternoInto = Pattern.compile("(?<into>[a-zA-Z0-9-]+)");
			matcherInternoInto = patternInternoInto.matcher(matcherInterno.group("into"));
			while (matcherInternoInto.find()) {
				intoInsert.add(matcherInternoInto.group("into"));
			}

		}
	}

	private void from(DataDivision dataDivision, Matcher matcher) {
		Pattern patternInterno;
		Matcher matcherInterno;
		patternInterno = Pattern.compile("(?<tabela>[a-zA-Z0-9-]+)(\\sAS\\s(?<nomeInterno>>)[a-zA-Z0-9-]+)?");
		matcherInterno = patternInterno.matcher(matcher.group("from"));
		while (matcherInterno.find()) {
			// TODO nome interno da tabela AS
			matcherOf = patternOf.matcher(matcherInterno.group("tabela"));
			if (matcherOf.find()) {
				Atributo atributo = validaAtributo(dataDivision);
				tabelas.add(atributo);
				if (atributo.getClasses() != null) {
					imports.add(atributo.getImport());
				}
			}
			nomeQuery = dataDivision.setAtributoIsTabela(matcherInterno.group("tabela")) + declaracao;
		}
	}

	private void where(DataDivision dataDivision, Matcher matcher) {
		if (matcher.group("where") != null) {
			Pattern patternInterno;
			Matcher matcherInterno;
			patternInterno = Pattern.compile("(?i)(?<ligacao>(AND|OR))"
					+ "|((?<whereInLeft>[a-zA-Z0-9-]+)\\sIN\\s(?<whereInRight>([a-zA-Z0-9-(),]+//s?)+))"
					+ "|((?<whereLeft>[a-zA-Z0-9-]+)\\s?(?<operacao>(([<]|[>])[=])|[+-/()<>=])[:]?\\s?(?<whereRight>[a-zA-Z0-9-]+))");
			matcherInterno = patternInterno.matcher(matcher.group("where"));
			while (matcherInterno.find()) {
				ArrayList<Atributo> whereLocal = new ArrayList<Atributo>();
				isWhere = true;
				// TODO completar patterOfInterno com o modo de tabela para o of
				if (matcherInterno.group("ligacao") != null) {
					whereLigacao.add(matcherInterno.group("ligacao"));
				} else {
					if (matcherInterno.group("whereLeft") != null) {
						whereEsquerda.add(matcherInterno.group("whereLeft"));
						whereOperacao.add(matcherInterno.group("operacao"));
						matcherOf = patternOf.matcher(matcherInterno.group("whereRight"));
						if (matcherOf.find()) {
							Atributo atributo = validaAtributo(dataDivision);
							whereLocal.add(atributo);
							if (atributo.getClasses() != null) {
								imports.add(atributo.getImport());
							}
						}
					} else if (matcherInterno.group("whereInLeft") != null) {
						whereEsquerda.add(matcherInterno.group("whereInLeft"));
						whereOperacao.add("IN");
						matcherOf = patternOf.matcher(matcherInterno.group("whereInRight"));
						while (matcherOf.find()) {
							Atributo atributo = validaAtributo(dataDivision);
							whereLocal.add(atributo);
							if (atributo.getClasses() != null) {
								imports.add(atributo.getImport());
							}
						}
					}
					whereDireita.add(whereLocal);
				}
			}
		}
	}

	private void intoSelect(DataDivision dataDivision, Matcher matcher) {
		Pattern patternInterno;
		Matcher matcherInterno;
		patternInterno = Pattern.compile("[:](?<into>[a-zA-Z0-9-]+)");
		matcherInterno = patternInterno.matcher(matcher.group("into"));
		while (matcherInterno.find()) {
			matcherOf = patternOf.matcher(matcherInterno.group("into"));
			if (matcherOf.find()) {
				Atributo atributo = validaAtributo(dataDivision);
				intoSelect.add(atributo);
				if (atributo.getClasses() != null) {
					imports.add(atributo.getImport());
				}
			}
		}
	}

	private void parametros(Matcher matcher) {
		Pattern patternInterno;
		Matcher matcherInterno;
		patternInterno = Pattern.compile("[a-zA-Z0-9-]+");
		matcherInterno = patternInterno.matcher(matcher.group("parametros"));
		while (matcherInterno.find()) {
			parametros.add(matcherInterno.group());
		}
	}

	public String getInstrucao() {
		String instrucao = "\t@Query(value = \"" + declaracao + " ";
		ArrayList<String> tipo = new ArrayList<String>();
		ArrayList<String> nome = new ArrayList<String>();
		int i = 0;
		switch (declaracao) {
		case "INSERT":
			instrucao += "INTO " + tabelas.get(0).getNome() + "(";
			for (String elemento : intoInsert) {
				instrucao += elemento + ", ";
			}
			instrucao = instrucao.substring(0, instrucao.length() - 2);
			instrucao += ") VALUES (";
			for (Atributo elemento : values) {
				if (elemento.getNome() != null && !elemento.getNome().isEmpty()) {
					instrucao += "?" + (++i) + ", ";
					nome.add(elemento.getNome() + i);
					if (elemento instanceof AtributoElementar) {
						tipo.add(((AtributoElementar) elemento).getTipoAtributo().getDescricao());
					} else {
						tipo.add(toUpperFistCase(elemento.getNome()));
					}
				} else {
					instrucao += ((AtributoElementar) elemento).getValor() + ", ";
				}
			}
			instrucao = instrucao.substring(0, instrucao.length() - 2);
			instrucao += ")";
			break;
		case "UPDATE":
			instrucao += tabelas.get(0).getNome() + " SET ";
			for (int j = 0; j < set.size(); j++) {
				instrucao += set.get(j) + " = ";
				if (values.get(j).getNome() != null && !values.get(j).getNome().isEmpty()) {
					instrucao += "?" + (++i) + ", ";
					nome.add(values.get(j).getNome() + i);
					if (values.get(j) instanceof AtributoElementar) {
						tipo.add(((AtributoElementar) values.get(j)).getTipoAtributo().getDescricao());
					} else {
						tipo.add(toUpperFistCase(values.get(j).getNome()));
					}
				} else {
					instrucao += ((AtributoElementar) values.get(j)).getValor() + ", ";
				}
			}
			instrucao = instrucao.substring(0, instrucao.length() - 2);
			break;
		case "DELETE":
			instrucao += "FROM " + tabelas.get(0).getNome();
			break;
		case "SELECT":
			for (String elemento : parametros) {
				instrucao += elemento + ", ";
			}
			instrucao = instrucao.substring(0, instrucao.length() - 2);
			instrucao += " FROM " + toUpperFistCase(tabelas.get(0).getNome());
			break;
		}
		if (isWhere) {
			instrucao += " WHERE ";
			for (int j = 0; j < whereOperacao.size(); j++) {
				instrucao += whereEsquerda.get(j) + " " + whereOperacao.get(j) + " ";
				if (whereOperacao.get(j).equalsIgnoreCase("IN")) {
					instrucao += "(";
				}
				for (Atributo elemento : whereDireita.get(j)) {
					if (elemento.getNome() != null && !elemento.getNome().isEmpty()) {
						instrucao += "?" + (++i) + " ";
						nome.add(elemento.getNome() + i);
						if (elemento instanceof AtributoElementar) {
							tipo.add(((AtributoElementar) elemento).getTipoAtributo().getDescricao());
						} else {
							tipo.add(toUpperFistCase(elemento.getNome()));
						}
					} else {
						instrucao += ((AtributoElementar) elemento).getValor() + " ";
					}
				}
				if (whereOperacao.get(j).equalsIgnoreCase("IN")) {
					instrucao += ")";
				}
				if (j < whereLigacao.size()) {
					instrucao += whereLigacao.get(j) + " ";
				}
			}
		}
		instrucao += "\", nativeQuery = true)\n";
		switch (declaracao) {
		case "INSERT":
		case "UPDATE":
		case "DELETE":
			instrucao += "\tvoid ";
			break;
		case "SELECT":
			// TODO validar como q cobol trata SELECT com vários campos
			if (parametros.size() > 1) {
				instrucao += "\tList<Object> ";
			} else {
				instrucao += parametros.get(0) + " ";
			}
		}
		instrucao += nomeQuery.toLowerCase() + "(";
		if (tipo.size() > 0) {
			for (i = 0; i < tipo.size(); i++) {
				instrucao += tipo.get(i) + " " + nome.get(i) + ", ";
			}
			instrucao = instrucao.substring(0, instrucao.length() - 2);
		}
		instrucao += ")";
		instrucao += ";";
		return instrucao;
	}

	@Override
	public String escreveArquivo(Integer nivel) {
		String texto = fazTabulacao(nivel);
		if (declaracao == "SELECT") {
			// TODO validar como q cobol trata SELECT com vários campos
			if (parametros.size() > 1) {
				texto += "List<Object> " + nomeQuery.toLowerCase() + " = ";
			} else {
				texto += parametros.get(0) + " = ";
			}
		}
		texto += tabelas.get(0).getNome().toLowerCase() + "repository." + nomeQuery.toLowerCase() + "(";
		if (values != null && !values.isEmpty()) {
			for (Atributo elemento : values) {
				texto += elemento.getClassesSucessoras().toLowerCase() + elemento.getSentencaGet() + ", ";
			}
			texto = texto.substring(0, texto.length() - 2);
		}
		texto += ");";
		return texto;
	}

}
