package com.trans.transpiladorCobolJava.procedureDivision.Paragrafos;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.trans.transpiladorCobolJava.dataDivision.DataDivision;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.Atributo;

public class MoveParagrafo extends Paragrafo {

	Atributo de;

	ArrayList<Atributo> para = new ArrayList<Atributo>();

	public MoveParagrafo(String instrucao, DataDivision dataDivision) {

		Pattern pattern = Pattern.compile("(?i)MOVE\\s(?<identifier1>\\w+)\\sTO\\s(?<identifier2>(\\w+\\s?)+)");
		Matcher matcher = pattern.matcher(instrucao);

		if (matcher.find()) {
			matcherOf = patternOf.matcher(matcher.group("identifier1"));
			if (matcherOf.find()) {
				Atributo atributo = validaAtributo(dataDivision);
				de = atributo;
				if (atributo.getClasses() != null) {
					imports.add(atributo.getImport());
				}
			}

			matcherOf = patternOf.matcher(matcher.group("identifier2"));
			while (matcherOf.find()) {
				Atributo atributo = validaAtributo(dataDivision);
				para.add(atributo);
				if (atributo.getClasses() != null) {
					imports.add(atributo.getImport());
				}
			}
		}
	}

	@Override
	public String escreveArquivo(Integer nivel) {
		String imprimirDe = new String();
		String imprimirMove = new String();

		imprimirDe = de.getStringEscritaPorTipo();

		for (Atributo elemento : para) {
			imprimirMove += (fazTabulacao(nivel) + toLowerFistCase(elemento.getClassesSucessoras())
					+ elemento.getSentencaSet(imprimirDe) + ";");
		}
		return imprimirMove;
	}

}
