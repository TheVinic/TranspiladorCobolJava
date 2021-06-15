package com.trans.transpiladorCobolJava.procedureDivision.Paragrafos;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.trans.transpiladorCobolJava.dataDivision.DataDivision;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.Atributo;

public class MultiplyParagrafo extends Paragrafo {

	Atributo multiplica;

	ArrayList<Atributo> por = new ArrayList<Atributo>();

	ArrayList<Atributo> resultado = new ArrayList<Atributo>();

	public MultiplyParagrafo(String instrucao, DataDivision dataDivision) {

		instrucao = instrucao.trim().replaceAll("(?i)\\sGIVING\\s", "  GIVING ");

		String regex = "(?i)MULTIPLY\\s(?<identifier1>\\w+)\\sBY\\s(?<identifier2>(\\w+\\s?)+)(\\sGIVING\\s(?<giving>(\\w+\\s?)+))?";

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(instrucao);
		
		if (matcher.find()) {
			matcher.group();
			matcherOf = patternOf.matcher(matcher.group("identifier1"));
			if (matcherOf.find()) {
				Atributo atributo = validaAtributo(dataDivision);
				multiplica = atributo;
				if (atributo.getClasses() != null) {
					imports.add(atributo.getImport());
				}
			}
			matcherOf = patternOf.matcher(matcher.group("identifier2"));
			while (matcherOf.find()) {
				Atributo atributo = validaAtributo(dataDivision);
				por.add(atributo);
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
		String imprimirMultiplicar = new String();
		String imprimirPor;
		String imprimirResultado = new String();

		imprimirMultiplicar = multiplica.getStringEscritaPorTipo();

		if (resultado.isEmpty()) {
			for (Atributo elemento : por) {
				imprimirPor = elemento.getStringEscritaPorTipo();
				imprimirResultado += (fazTabulacao(nivel) + toLowerFistCase(elemento.getClassesSucessoras())
						+ elemento.getSentencaSet(imprimirMultiplicar + " * " + imprimirPor) + ";\n");

			}
		} else {
			imprimirPor = por.get(0).getStringEscritaPorTipo();
			for (Atributo elemento : resultado) {
				imprimirResultado += (fazTabulacao(nivel) + toLowerFistCase(elemento.getClassesSucessoras())
						+ elemento.getSentencaSet(imprimirMultiplicar + " * " + imprimirPor) + ";\n");
			}
		}

		return imprimirResultado;
	}

}
