package com.trans.transpiladorCobolJava.procedureDivision.Paragrafos;

import java.util.ArrayList;

import com.trans.transpiladorCobolJava.arquivo.Codigo;
import com.trans.transpiladorCobolJava.dataDivision.DataDivision;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.Atributo;
import com.trans.transpiladorCobolJava.procedureDivision.ParagrafosProcedureDivision;

public class ComputeParagrafo extends Paragrafo {

	ArrayList<Atributo> resultado = new ArrayList<Atributo>();

	ArrayList<Atributo> calculo = new ArrayList<Atributo>();

	public ComputeParagrafo(Codigo umaSecao, DataDivision dataDivision) {

		Codigo stringDividadiPelasOperacoes = null;

		boolean encontrouIgual = false;

		for (; !encontrouIgual && !umaSecao.getInstrucaoAtualLeitura().equals("="); umaSecao.avancaPosicaoLeitura()) {
			Atributo atributo;
			if (umaSecao.getInstrucaoAtualLeitura().contains("=")) {
				stringDividadiPelasOperacoes = new Codigo(umaSecao.getInstrucaoAtualLeitura().split("="));
				if (!stringDividadiPelasOperacoes.getInstrucaoAtualLeitura().isEmpty()) {
					atributo = encontraIdentificador(stringDividadiPelasOperacoes, dataDivision);
					resultado.add(atributo);
					if (atributo.getClasses() != null) {
						imports.add(atributo.getImport());
					}
				}
				encontrouIgual = true;
			} else {
				atributo = encontraIdentificador(umaSecao, dataDivision);
				resultado.add(atributo);
				if (atributo.getClasses() != null) {
					imports.add(atributo.getImport());
				}
			}
		}

		if (encontrouIgual) {
			umaSecao.setVoltaPosicaoLeitura();
			umaSecao.isOver();
			if (!stringDividadiPelasOperacoes.getProximaInstrucaoLeitura().isEmpty()) {

				stringDividadiPelasOperacoes = new Codigo(stringDividadiPelasOperacoes.getInstrucaoAtualLeitura()
						.replaceAll("\\+", " + ").replaceAll("\\-", " - ").replaceAll("\\*", " * ")
						.replaceAll("\\/", " / ").replaceAll("\\(", " ( ").replaceAll("\\)", " ) ")
						.replaceAll("\s\\*\s\s\\*\s", " ** ").split("\s"));
				for (; !stringDividadiPelasOperacoes.isOver(); stringDividadiPelasOperacoes.avancaPosicaoLeitura()) {
					Atributo atributo = encontraCriaAtributo(stringDividadiPelasOperacoes, dataDivision);
					calculo.add(atributo);
					if (atributo.getClasses() != null) {
						imports.add(atributo.getImport());
					}
				}
			}
		}

		for (umaSecao.avancaPosicaoLeitura(); !umaSecao.isOver() && !umaSecao.getInstrucaoAtualLeitura().equals("=")
				&& !ParagrafosProcedureDivision.acabouParagrafoAtual(umaSecao.getInstrucaoAtualLeitura()); umaSecao
						.avancaPosicaoLeitura()) {
			if (umaSecao.getInstrucaoAtualLeitura().contains("+") || umaSecao.getInstrucaoAtualLeitura().contains("-")
					|| umaSecao.getInstrucaoAtualLeitura().contains("*")
					|| umaSecao.getInstrucaoAtualLeitura().contains("/")
					|| umaSecao.getInstrucaoAtualLeitura().contains("(")
					|| umaSecao.getInstrucaoAtualLeitura().contains(")")) {
				stringDividadiPelasOperacoes = new Codigo(
						umaSecao.getInstrucaoAtualLeitura().replaceAll("\\+", " + ").replaceAll("\\-", " - ")
								.replaceAll("\\*", " * ").replaceAll("\\/", " / ").replaceAll("\\(", " ( ")
								.replaceAll("\\)", " ) ").replaceAll("\s\\*\s\s\\*\s", " ** ").split("\s"));
				for (; !stringDividadiPelasOperacoes.isOver(); stringDividadiPelasOperacoes.avancaPosicaoLeitura()) {
					Atributo atributo = encontraCriaAtributo(stringDividadiPelasOperacoes, dataDivision);
					calculo.add(atributo);
					if (atributo.getClasses() != null) {
						imports.add(atributo.getImport());
					}
				}
			} else {
				Atributo atributo = encontraCriaAtributo(umaSecao, dataDivision);
				calculo.add(atributo);
				if (atributo.getClasses() != null) {
					imports.add(atributo.getClasses().get(0));
				}
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
			} else {
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
