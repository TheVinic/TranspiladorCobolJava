package com.trans.transpiladorCobolJava.procedureDivision.Paragrafos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.trans.transpiladorCobolJava.arquivo.Codigo;
import com.trans.transpiladorCobolJava.dataDivision.DataDivision;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.Atributo;
import com.trans.transpiladorCobolJava.procedureDivision.ParagrafosProcedureDivision;

public class SubtractParagrafo extends Paragrafo {

	ArrayList<Atributo> subtrair = new ArrayList<>();

	ArrayList<Atributo> de = new ArrayList<>();

	ArrayList<Atributo> resultado = new ArrayList<>();

	public SubtractParagrafo(Codigo umaSecao, DataDivision dataDivision) {
		for (; !umaSecao.getInstrucaoAtualLeitura().equals("FROM"); umaSecao.avancaPosicaoLeitura()) {
			Atributo atributo = encontraCriaAtributo(umaSecao, dataDivision);
			subtrair.add(atributo);
			if (atributo.getClasses() != null) {
				imports.add(atributo.getImport());
			}
		}

		for (umaSecao.avancaPosicaoLeitura(); !umaSecao.isOver()
				&& !umaSecao.getInstrucaoAtualLeitura().equals("GIVING")
				&& !ParagrafosProcedureDivision.acabouParagrafoAtual(umaSecao.getInstrucaoAtualLeitura()); umaSecao
						.avancaPosicaoLeitura()) {
			Atributo atributo = encontraCriaAtributo(umaSecao, dataDivision);
			de.add(atributo);
			if (atributo.getClasses() != null) {
				imports.add(atributo.getImport());
			}
		}

		if (!umaSecao.isOver() && umaSecao.getInstrucaoAtualLeitura().equals("GIVING")) {
			for (umaSecao.avancaPosicaoLeitura(); !umaSecao.isOver()
					&& !ParagrafosProcedureDivision.acabouParagrafoAtual(umaSecao.getInstrucaoAtualLeitura()); umaSecao
							.avancaPosicaoLeitura()) {
				Atributo atributo = encontraIdentificador(umaSecao, dataDivision);
				resultado.add(atributo);
				imports.add(atributo.getImport());
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
						+ elemento.getSentencaSet(imprimirSubtrair + " - " + imprimirDe) + ";\n");
			}
		} else {
			imprimirDe = de.get(0).getStringEscritaPorTipo();
			for (Atributo elemento : resultado) {
				imprimirResultado += (fazTabulacao(nivel) + toLowerFistCase(elemento.getClassesSucessoras())
						+ elemento.getSentencaSet(imprimirSubtrair + " - " + imprimirDe) + ";\n");
			}
		}

		return imprimirResultado;
	}

}
