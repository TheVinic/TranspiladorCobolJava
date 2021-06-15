package com.trans.transpiladorCobolJava.procedureDivision.Paragrafos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.trans.transpiladorCobolJava.dataDivision.DataDivision;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.Atributo;

public class IfParagrafo extends Paragrafo {

	ArrayList<Atributo> condicao = new ArrayList<Atributo>();

	ArrayList<Paragrafo> entao;
	ArrayList<Paragrafo> seNao;

	public IfParagrafo(String instrucao, Matcher matcherGeral, DataDivision dataDivision,
			ArrayList<ProcedureDivisionSection> secoes) {

		String regex = "(?i)IF\\s(?<condition>[a-zA-Z0-9+-/()* <>=]+)";

		Pattern pattern = Pattern.compile(regex);
		Matcher matcherInterno = pattern.matcher(instrucao);

		if (matcherInterno.find()) {
			Pattern patternInterno = Pattern
					.compile("(?i)(?<variavel>((\\w+)|[+-/()<>=]|[*]+))|(?<variavelOf>(\\w+)\\sOF\\s(?<of>\\w+))");
			matcherOf = patternInterno.matcher(matcherInterno.group("condition"));
			while (matcherOf.find()) {
				Atributo atributo = validaAtributo(dataDivision);
				condicao.add(atributo);
				if (atributo.getClasses() != null) {
					imports.add(atributo.getImport());
				}
			}
		}

		entao = new ProcedureDivisionIf().leitura(matcherGeral, dataDivision, secoes);

		if (matcherGeral.group().toUpperCase().startsWith("ELSE")) {
			seNao = new ProcedureDivisionIf().leitura(matcherGeral, dataDivision, secoes);
		}
	}

	@Override
	public String escreveArquivo(Integer nivel) {
		String imprimirCondicao = fazTabulacao(nivel) + "if( ";

		for (Atributo elemento : condicao) {
			imprimirCondicao += elemento.getStringEscritaPorTipo() + " ";
		}
		imprimirCondicao += ") {\n";

		for (Paragrafo paragrafo : entao) {
			imprimirCondicao += paragrafo.escreveArquivo(nivel + 1);
		}
		imprimirCondicao += fazTabulacao(nivel) + "}";

		if (seNao != null) {
			imprimirCondicao += " else {\n";
			for (Paragrafo paragrafo : seNao) {
				imprimirCondicao += paragrafo.escreveArquivo(nivel + 1);
			}
			imprimirCondicao += fazTabulacao(nivel) + "}";
		} else {
			imprimirCondicao += "\n";
		}

		return imprimirCondicao;
	}

	@Override
	public Set<String> escreveImports() {
		Set<String> imprimir = new HashSet<>();
		imprimir.addAll(escreveImportsParagrago(imports));
		for (Paragrafo paragrafo : entao) {
			imprimir.addAll(paragrafo.escreveImports());
		}
		if (seNao != null) {
			for (Paragrafo paragrafo : seNao) {
				imprimir.addAll(paragrafo.escreveImports());
			}
		}
		return imprimir;
	}

	@Override
	public Set<String> getImports() {
		Set<String> importsDosParagrafos = new HashSet<String>();

		importsDosParagrafos = imports;

		for (Paragrafo elemento : entao) {
			importsDosParagrafos.addAll(elemento.getImports());
		}
		if (seNao != null) {
			for (Paragrafo elemento : seNao) {
				importsDosParagrafos.addAll(elemento.getImports());
			}
		}

		return importsDosParagrafos;
	}

}
