package com.trans.transpiladorCobolJava.procedureDivision.Paragrafos;

import java.util.ArrayList;
import java.util.Set;

import com.trans.transpiladorCobolJava.arquivo.Codigo;
import com.trans.transpiladorCobolJava.dataDivision.DataDivision;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.Atributo;
import com.trans.transpiladorCobolJava.procedureDivision.ParagrafosProcedureDivision;

public class ComputeParagrafo extends Paragrafo {

	public ComputeParagrafo(Codigo umaSecao, DataDivision dataDivision) {

		ArrayList<Atributo> resultado = new ArrayList<>();

		ArrayList<Atributo> calculo = new ArrayList<>();

		Codigo stringDividadiPelasOperacoes = null;

		boolean encontrouIgual = false;

		for (; !encontrouIgual && !umaSecao.getInstrucaoAtualLeitura().equals("="); umaSecao.avancaPosicaoLeitura()) {
			Atributo atributo;
			if (umaSecao.getInstrucaoAtualLeitura().contains("=")) {
				stringDividadiPelasOperacoes = new Codigo(umaSecao.getInstrucaoAtualLeitura().split("="));
				resultado.add(encontraIdentificador(stringDividadiPelasOperacoes, dataDivision));
				encontrouIgual = true;
			} else {
				atributo = encontraIdentificador(umaSecao, dataDivision);
				resultado.add(atributo);
				if (atributo.getClasses() != null) {
					imports.add(atributo.getClasses().get(0));
				}
			}
		}

		if (encontrouIgual) {
			stringDividadiPelasOperacoes.avancaPosicaoLeitura();
			stringDividadiPelasOperacoes = new Codigo(stringDividadiPelasOperacoes.getInstrucaoAtualLeitura()
					.replaceAll("\\+", " + ").replaceAll("\\-", " - ").replaceAll("\\*", " * ").replaceAll("\\/", " / ")
					.replaceAll("\s\\*\s\s\\*\s", " ** ").split("\s"));
			for (; !stringDividadiPelasOperacoes.isOver(); stringDividadiPelasOperacoes.avancaPosicaoLeitura()) {
				Atributo atributo = encontraCriaAtributo(stringDividadiPelasOperacoes, dataDivision);
				calculo.add(atributo);
				if (atributo.getClasses() != null) {
					imports.add(atributo.getClasses().get(0));
				}
			}
			umaSecao.setVoltaPosicaoLeitura();
		}

		for (umaSecao.avancaPosicaoLeitura(); !umaSecao.isOver() && !umaSecao.getInstrucaoAtualLeitura().equals("=")
				&& !ParagrafosProcedureDivision.acabouParagrafoAtual(umaSecao.getInstrucaoAtualLeitura()); umaSecao
						.avancaPosicaoLeitura()) {
			if (umaSecao.getInstrucaoAtualLeitura().contains("+") || umaSecao.getInstrucaoAtualLeitura().contains("-")
					|| umaSecao.getInstrucaoAtualLeitura().contains("*")
					|| umaSecao.getInstrucaoAtualLeitura().contains("/")) {
				stringDividadiPelasOperacoes = new Codigo(umaSecao.getInstrucaoAtualLeitura().replaceAll("\\+", " + ")
						.replaceAll("\\-", " - ").replaceAll("\\*", " * ").replaceAll("\\/", " / ")
						.replaceAll("\s\\*\s\s\\*\s", " ** ").split("\s"));
				for (; !stringDividadiPelasOperacoes.isOver(); stringDividadiPelasOperacoes.avancaPosicaoLeitura()) {
					Atributo atributo = encontraCriaAtributo(stringDividadiPelasOperacoes, dataDivision);
					calculo.add(atributo);
					if (atributo.getClasses() != null) {
						imports.add(atributo.getClasses().get(0));
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
	public String escreveArquivo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> escreveImports() {
		// TODO Auto-generated method stub
		return null;
	}

}
