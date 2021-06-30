package com.trans.transpiladorCobolJava.procedureDivision.Paragrafos;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.trans.transpiladorCobolJava.dataDivision.DataDivision;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.Atributo;

public class EvaluateParagrafo extends Paragrafo {

	EvaluateModel evaluate = new EvaluateModel();

	public EvaluateParagrafo(String instrucao, Matcher matcherGeral, DataDivision dataDivision,
			ArrayList<ProcedureDivisionSection> secoes) {
		instrucao = instrucao.replaceAll("\\sALSO\\s", "  ALSO ");
		String regex = "(?i)(EVALUATE|WHEN)\\s((?<identifier1>(([a-zA-Z0-9-]+|([<]|[>])[=]|[*/()<>=]+|[+])\\s?)+)"
				+ "\\s?(?<identifierAlso>(ALSO\\s(([a-zA-Z0-9-]+|([<]|[>])[=]|[*/()<>=+]+)\\s?))*)" + "\\s?)";
		String regexAlso = "(?i)(ALSO\\s(?<identifierAlso>[a-zA-Z0-9-]+))";

		Pattern pattern = Pattern.compile(regex);
		Matcher matcherInterno = pattern.matcher(instrucao);
		if (matcherInterno.find()) {
			matcherInterno.group();
			matcherInterno.group("identifier1");
			matcherInterno.group("identifierAlso");

			// Adiciona evaluate e Also
			evaluate.addEvaluate(dataDivision, regexAlso, matcherInterno.group("identifier1"),
					matcherInterno.group("identifierAlso"));

			// Leitura dos Whens

			String ultimoValorDoWhen = null;

			if (matcherGeral.find() && (matcherGeral.group().matches(regex))) {
				do {
					ArrayList<Paragrafo> instrucoes;
					WhenEvaluateModel when = new WhenEvaluateModel();
					ArrayList<ArrayList<Atributo>> atributosWhen = new ArrayList<ArrayList<Atributo>>();

					ultimoValorDoWhen = matcherGeral.group("instrucao2");
					do {
						// Inclui novo When
						matcherInterno = pattern.matcher(matcherGeral.group().replaceAll("\\sALSO\\s", "  ALSO "));
						if (matcherGeral.group().matches(regex) && matcherInterno.find()) {
							atributosWhen = evaluate.criaWhen(dataDivision, regexAlso,
									matcherInterno.group("identifier1"), matcherInterno.group("identifierAlso"));
						}

						instrucoes = new ProcedureDivisionWhenEvaluate().leitura(matcherGeral, dataDivision, secoes);
					} while (instrucoes == null && (matcherGeral.group().matches(regex)));
					when.setWhen(atributosWhen);

					when.setInstrucoes(instrucoes);
					evaluate.addWhen(when);
				} while ((matcherGeral.group().matches(regex)) && !ultimoValorDoWhen.equalsIgnoreCase("OTHER"));
			}
		}
	}

	@Override
	public String escreveArquivo(Integer nivel) {
		String imprimir = new String();
		imprimir += evaluate.escreveArquivo(nivel);
		return imprimir;
	}

}
