package com.trans.transpiladorCobolJava.procedureDivision.Paragrafos;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.trans.transpiladorCobolJava.dataDivision.DataDivision;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.Atributo;

public class AddParagrafo extends Paragrafo {

	ArrayList<Atributo> somar = new ArrayList<>();

	ArrayList<Atributo> somarCom = new ArrayList<>();

	ArrayList<Atributo> gravarEm = new ArrayList<>();

	public AddParagrafo(String instrucao, DataDivision dataDivision) {

		instrucao = instrucao.trim().replaceAll("(?i)\\sTO\\s", "  TO ").replaceAll("(?i)\\sGIVING\\s", "  GIVING ");

		Pattern pattern = Pattern.compile(
				"(?i)ADD\\s(?<identifier1>((\\w+\\s))+)(\\sTO\\s(?<identifier2>(\\w+\\s?)+))?(\\sGIVING\\s(?<giving>(\\w+\\s?)+))?");
		Matcher matcher = pattern.matcher(instrucao);

		if (matcher.find()) {
			matcherOf = patternOf.matcher(matcher.group("identifier1"));
			while (matcherOf.find()) {
				Atributo atributo = validaAtributo(dataDivision);
				somar.add(atributo);
				if (atributo.getClasses() != null) {
					imports.add(atributo.getImport());
				}
			}

			if (matcher.group("identifier2") != null) {
				matcherOf = patternOf.matcher(matcher.group("identifier2"));
				while (matcherOf.find()) {
					Atributo atributo = validaAtributo(dataDivision);
					somarCom.add(atributo);
					imports.add(atributo.getImport());
				}
			}

			if (matcher.group("giving") != null) {
				matcherOf = patternOf.matcher(matcher.group("giving"));
				while (matcherOf.find()) {
					Atributo atributo = validaAtributo(dataDivision);
					gravarEm.add(atributo);
					imports.add(atributo.getImport());
				}
			}
		} else {
			System.out.println("Erro no matcher do ADD: " + instrucao);
		}
	}

	@Override
	public String escreveArquivo(Integer nivel) {
		String imprimirSomar = new String();
		String imprimirSomarLocal = new String();
		String imprimirSomarEm = new String();

		for (Atributo elemento : somar) {
			imprimirSomar += elemento.getStringEscritaPorTipo() + " + ";
		}

		if (gravarEm.isEmpty()) {
			for (Atributo elemento : somarCom) {
				imprimirSomarLocal = imprimirSomar + toLowerFistCase(elemento.getClassesSucessoras())
						+ elemento.getSentencaGet();
				imprimirSomarEm += (fazTabulacao(nivel) + toLowerFistCase(elemento.getClassesSucessoras())
						+ elemento.getSentencaSet(imprimirSomarLocal) + ";\n");
			}
		} else {
			imprimirSomarLocal = somarCom.get(0).getStringEscritaPorTipo();
			for (Atributo elemento : gravarEm) {
				imprimirSomarEm += (fazTabulacao(nivel) + toLowerFistCase(elemento.getClassesSucessoras())
						+ elemento.getSentencaSet((somarCom.isEmpty()) ? imprimirSomar : imprimirSomarLocal) + ";\n");
			}
			imprimirSomarEm = imprimirSomarEm.replaceAll("\s\\+\s\\)", ")");
		}

		return imprimirSomarEm;
	}

}
