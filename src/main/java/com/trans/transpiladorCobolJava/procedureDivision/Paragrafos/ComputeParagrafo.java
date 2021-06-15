package com.trans.transpiladorCobolJava.procedureDivision.Paragrafos;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.trans.transpiladorCobolJava.dataDivision.DataDivision;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.Atributo;

public class ComputeParagrafo extends Paragrafo {

	ArrayList<Atributo> resultado = new ArrayList<Atributo>();

	ArrayList<Atributo> calculo = new ArrayList<Atributo>();

	public ComputeParagrafo(String instrucao, DataDivision dataDivision) {

		String regex = "(?i)COMPUTE\\s(?<resultado>[a-zA-Z0-9+-/()* ]+)(=|(EQUAL))(?<calculo>[a-zA-Z0-9+-/=()* ]+)";

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(instrucao);
		matcher.find();
		matcher.group();
		matcher.group("resultado");
		matcher.group("calculo");

		matcherOf = patternOf.matcher(matcher.group("resultado"));
		while (matcherOf.find()) {
			Atributo atributo = validaAtributo(dataDivision);
			resultado.add(atributo);
			if (atributo.getClasses() != null) {
				imports.add(atributo.getImport());
			}
		}

		Pattern patternInterno = Pattern
				.compile("(?i)(?<variavel>(\\w+)|[+-/()]|[*]+)|(?<variavelOf>(\\w+)\\sOF\\s(?<of>\\w+))");
		matcherOf = patternInterno.matcher(matcher.group("calculo"));
		while (matcherOf.find()) {
			Atributo atributo = validaAtributo(dataDivision);
			calculo.add(atributo);
			if (atributo.getClasses() != null) {
				imports.add(atributo.getImport());
			}
		}
	}

	@Override
	public String escreveArquivo(Integer nivel) {
		String imprimirResultado = new String();
		String imprimirCalculo = new String();

		for (Atributo elemento : calculo) {
			String elementoParaImprimir = elemento.getStringEscritaPorTipo();
			if (elementoParaImprimir.equals("**")) {
				// TODO incluir trato para exponencial
				imprimirCalculo += "/*Exponencial não implementado*/*";
			} else

			{
				imprimirCalculo += elemento.getStringEscritaPorTipo();
			}
		}

		for (Atributo elemento : resultado) {
			imprimirResultado += (fazTabulacao(nivel) + toLowerFistCase(elemento.getClassesSucessoras())
					+ elemento.getSentencaSet(imprimirCalculo) + ";\n");
		}

		return imprimirResultado;
	}
}
