package com.trans.transpiladorCobolJava.procedureDivision.Paragrafos;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.trans.transpiladorCobolJava.dataDivision.DataDivision;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.Atributo;

public class SubtractParagrafo extends Paragrafo {

	ArrayList<Atributo> subtrair = new ArrayList<>();

	ArrayList<Atributo> de = new ArrayList<>();

	ArrayList<Atributo> resultado = new ArrayList<>();

	public SubtractParagrafo(String instrucao, DataDivision dataDivision) {

		instrucao = instrucao.trim().replaceAll("(?i)\\sFROM\\s", "  FROM ").replaceAll("(?i)\\sGIVING\\s",
				"  GIVING ");

		String regex = "(?i)SUBTRACT\\s(?<identifier1>(\\w+\\s?)+)\\sFROM\\s(?<identifier2>(\\w+\\s?)+)(\\sGIVING\\s(?<giving>(\\w+\\s?)+))?";

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(instrucao);

		if (matcher.find()) {
			matcher.group();
			matcherOf = patternOf.matcher(matcher.group("identifier1"));
			while (matcherOf.find()) {
				Atributo atributo = validaAtributo(dataDivision);
				subtrair.add(atributo);
				if (atributo.getClasses() != null) {
					imports.add(atributo.getImport());
				}
			}
			matcherOf = patternOf.matcher(matcher.group("identifier2"));
			while (matcherOf.find()) {
				Atributo atributo = validaAtributo(dataDivision);
				de.add(atributo);
				if (atributo.getClasses() != null) {
					imports.add(atributo.getImport());
				}
			}
			if (matcher.group("giving") != null) {
				matcherOf = patternOf.matcher(matcher.group("giving"));
				while (matcherOf.find()) {
					Atributo atributo = validaAtributo(dataDivision);
					resultado.add(atributo);
					if (atributo.getClasses() != null) {
						imports.add(atributo.getImport());
					}
				}
			}
		}
	}

	@Override
	public String escreveArquivo(Integer nivel) {
		String imprimirSubtrair = "(";
		String imprimirDe = new String();
		String imprimirResultado = new String();

		for (Atributo elemento : subtrair) {
			imprimirSubtrair += elemento.getStringEscritaPorTipo() + " + ";
		}
		imprimirSubtrair = imprimirSubtrair.substring(0, imprimirSubtrair.length() - 3) + ")";

		if (resultado.isEmpty()) {
			for (Atributo elemento : de) {
				imprimirDe = elemento.getStringEscritaPorTipo();
				imprimirResultado += (fazTabulacao(nivel) + toLowerFistCase(elemento.getClassesSucessoras())
						+ elemento.getSentencaSet(imprimirSubtrair + " - " + imprimirDe) + ";");
			}
		} else {
			imprimirDe = de.get(0).getStringEscritaPorTipo();
			for (Atributo elemento : resultado) {
				imprimirResultado += (fazTabulacao(nivel) + toLowerFistCase(elemento.getClassesSucessoras())
						+ elemento.getSentencaSet(imprimirSubtrair + " - " + imprimirDe) + ";");
			}
		}

		return imprimirResultado;
	}

}
