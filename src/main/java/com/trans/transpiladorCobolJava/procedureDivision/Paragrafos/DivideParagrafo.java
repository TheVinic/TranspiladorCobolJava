package com.trans.transpiladorCobolJava.procedureDivision.Paragrafos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.trans.transpiladorCobolJava.arquivo.Codigo;
import com.trans.transpiladorCobolJava.dataDivision.DataDivision;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.Atributo;
import com.trans.transpiladorCobolJava.procedureDivision.ParagrafosProcedureDivision;

public class DivideParagrafo extends Paragrafo {

	ArrayList<Atributo> dividendo = new ArrayList<Atributo>();

	Atributo divisor;

	ArrayList<Atributo> quociente = new ArrayList<Atributo>();

	ArrayList<Atributo> resto = new ArrayList<Atributo>();

	public DivideParagrafo(Codigo umaSecao, DataDivision dataDivision) {

		divisor = encontraCriaAtributo(umaSecao, dataDivision);
		if (divisor.getClasses() != null) {
			imports.add(divisor.getClasses().get(0));
		}
		umaSecao.avancaPosicaoLeitura();

		if (umaSecao.getInstrucaoAtualLeitura().equals("INTO")) {
			for (umaSecao.avancaPosicaoLeitura(); !umaSecao.isOver()
					&& !umaSecao.getInstrucaoAtualLeitura().equals("GIVING")
					&& !umaSecao.getInstrucaoAtualLeitura().equals("REMAINDER")
					&& !ParagrafosProcedureDivision.acabouParagrafoAtual(umaSecao.getInstrucaoAtualLeitura()); umaSecao
							.avancaPosicaoLeitura()) {
				Atributo atributo = encontraIdentificador(umaSecao, dataDivision);
				dividendo.add(atributo);
				if (atributo.getClasses() != null) {
					imports.add(atributo.getImport());
				}
			}
		} else {
			umaSecao.avancaPosicaoLeitura();
			dividendo.add(divisor);
			divisor = encontraCriaAtributo(umaSecao, dataDivision);
			if (divisor.getClasses() != null) {
				imports.add(divisor.getImport());
			}
			umaSecao.avancaPosicaoLeitura();
		}

		if (!umaSecao.isOver() && umaSecao.getInstrucaoAtualLeitura().equals("GIVING")) {
			for (umaSecao.avancaPosicaoLeitura(); !umaSecao.isOver()
					&& !umaSecao.getInstrucaoAtualLeitura().equals("REMAINDER")
					&& !ParagrafosProcedureDivision.acabouParagrafoAtual(umaSecao.getInstrucaoAtualLeitura()); umaSecao
							.avancaPosicaoLeitura()) {
				Atributo atributo = encontraIdentificador(umaSecao, dataDivision);
				quociente.add(atributo);
				if (atributo.getClasses() != null) {
					imports.add(atributo.getImport());
				}
			}
		}

		if (!umaSecao.isOver() && umaSecao.getInstrucaoAtualLeitura().equals("REMAINDER")) {
			umaSecao.avancaPosicaoLeitura();
			Atributo atributo = encontraIdentificador(umaSecao, dataDivision);
			resto.add(atributo);
			if (atributo.getClasses() != null) {
				imports.add(atributo.getImport());
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

	@Override
	public Set<String> escreveImports() {
		Set<String> imprimir = new HashSet<String>();
		for (Atributo elemento : dividendo) {
			if (elemento.getNome() != null && !elemento.getNome().isEmpty()) {
				imprimir.addAll(escreveImportsParagrago(imports));
			}
		}

		if (divisor.getNome() != null && !divisor.getNome().isEmpty()) {
			imprimir.addAll(escreveImportsParagrago(imports));
		}

		for (Atributo elemento : quociente) {
			if (elemento.getNome() != null && !elemento.getNome().isEmpty()) {
				imprimir.addAll(escreveImportsParagrago(imports));
			}
		}
		for (Atributo elemento : resto) {
			if (elemento.getNome() != null && !elemento.getNome().isEmpty()) {
				imprimir.addAll(escreveImportsParagrago(imports));
			}
		}
		return imprimir;
	}
}
