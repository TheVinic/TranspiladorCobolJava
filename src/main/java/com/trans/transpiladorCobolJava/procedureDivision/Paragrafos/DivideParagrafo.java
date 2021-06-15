package com.trans.transpiladorCobolJava.procedureDivision.Paragrafos;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.trans.transpiladorCobolJava.dataDivision.DataDivision;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.Atributo;

public class DivideParagrafo extends Paragrafo {

	ArrayList<Atributo> dividendo = new ArrayList<Atributo>();

	Atributo divisor;

	ArrayList<Atributo> quociente = new ArrayList<Atributo>();

	ArrayList<Atributo> resto = new ArrayList<Atributo>();

	public DivideParagrafo(String instrucao, DataDivision dataDivision) {

		instrucao = instrucao.trim().replaceAll("(?i)\\sINTO\\s", "  INTO ").replaceAll("(?i)\\sBY\\s", "  BY ")
				.replaceAll("(?i)\\sGIVING\\s", "  GIVING ").replaceAll("(?i)\\sREMAINDER\\s", "  REMAINDER ");

		String regex1 = "(?i)DIVIDE\\s(?<identifier1>\\w+)";
		String regex2 = "(\\s\\sINTO\\s(?<into>(\\w+\\s?)+))";
		String regex3 = "(\\s\\sBY\\s(?<by>(\\w+)))";
		String regex4 = "(\\s?\\sGIVING\\s(?<giving>(\\w+\\s?)+))?";
		String regex5 = "(\\s?\\sREMAINDER\\s(?<remainder>(\\w+\\s?)))?";

		Pattern pattern = Pattern.compile(regex1 + "(" + regex2 + "|" + regex3 + ")" + regex4 + regex5);
		Matcher matcher = pattern.matcher(instrucao);

		if (matcher.find()) {
			matcher.group();
			matcherOf = patternOf.matcher(matcher.group("identifier1"));
			if (matcherOf.find()) {
				Atributo atributo = validaAtributo(dataDivision);
				divisor = atributo;
				if (atributo.getClasses() != null) {
					imports.add(atributo.getImport());
				}
			}
			if (matcher.group("into") != null) {
				matcherOf = patternOf.matcher(matcher.group("into"));
				while (matcherOf.find()) {
					Atributo atributo = validaAtributo(dataDivision);
					dividendo.add(atributo);
					if (atributo.getClasses() != null) {
						imports.add(atributo.getImport());
					}
				}
			} else {
				dividendo.add(divisor);
				matcherOf = patternOf.matcher(matcher.group("by"));
				if (matcherOf.find()) {
					Atributo atributo = validaAtributo(dataDivision);
					divisor = atributo;
					if (atributo.getClasses() != null) {
						imports.add(atributo.getImport());
					}
				}
			}
			if (matcher.group("giving") != null) {
				matcherOf = patternOf.matcher(matcher.group("giving"));
				while (matcherOf.find()) {
					Atributo atributo = validaAtributo(dataDivision);
					quociente.add(atributo);
					if (atributo.getClasses() != null) {
						imports.add(atributo.getImport());
					}
				}
			}
			if (matcher.group("remainder") != null) {
				matcherOf = patternOf.matcher(matcher.group("remainder"));
				while (matcherOf.find()) {
					Atributo atributo = validaAtributo(dataDivision);
					resto.add(atributo);
					if (atributo.getClasses() != null) {
						imports.add(atributo.getImport());
					}
				}
			}
		}
	}

	@Override
	public String escreveArquivo(Integer nivel) {
		String imprimirDividento = new String();
		String imprimirDivisor;
		String imprimirQuociente = new String();
		String imprimirResto = new String();

		imprimirDivisor = divisor.getStringEscritaPorTipo();

		if (quociente.isEmpty()) {
			for (Atributo elemento : dividendo) {
				imprimirDividento = elemento.getStringEscritaPorTipo();
				imprimirQuociente += (fazTabulacao(nivel) + toLowerFistCase(elemento.getClassesSucessoras())
						+ elemento.getSentencaSet(imprimirDividento + " / " + imprimirDivisor) + ";\n");

			}
		} else {
			imprimirDividento = dividendo.get(0).getStringEscritaPorTipo();
			for (Atributo elemento : quociente) {
				imprimirQuociente += (fazTabulacao(nivel) + toLowerFistCase(elemento.getClassesSucessoras())
						+ elemento.getSentencaSet(imprimirDividento + " / " + imprimirDivisor) + ";\n");
			}
		}

		for (Atributo elemento : resto) {
			imprimirResto += (fazTabulacao(nivel) + toLowerFistCase(elemento.getClassesSucessoras())
					+ elemento.getSentencaSet(imprimirDividento + " % " + imprimirDivisor) + ";\n");
		}
		return imprimirQuociente + imprimirResto;
	}
}
