package com.trans.transpiladorCobolJava.procedureDivision.Paragrafos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.trans.transpiladorCobolJava.arquivo.Codigo;
import com.trans.transpiladorCobolJava.dataDivision.DataDivision;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.Atributo;
import com.trans.transpiladorCobolJava.procedureDivision.ParagrafosProcedureDivision;

public class MultiplyParagrafo extends Paragrafo {

	Atributo multiplica;

	ArrayList<Atributo> por = new ArrayList<Atributo>();

	ArrayList<Atributo> resultado = new ArrayList<Atributo>();

	public MultiplyParagrafo(Codigo umaSecao, DataDivision dataDivision) {
		multiplica = encontraCriaAtributo(umaSecao, dataDivision);
		if (multiplica.getClasses() != null) {
			imports.add(multiplica.getImport());
		}
		umaSecao.avancaPosicaoLeitura();
		Atributo atributo = null;
		for (umaSecao.avancaPosicaoLeitura(); !umaSecao.isOver()
				&& !umaSecao.getInstrucaoAtualLeitura().equals("GIVING")
				&& !ParagrafosProcedureDivision.acabouParagrafoAtual(umaSecao.getInstrucaoAtualLeitura()); umaSecao
						.avancaPosicaoLeitura()) {
			atributo = encontraCriaAtributo(umaSecao, dataDivision);
			por.add(atributo);
			if (atributo.getClasses() != null) {
				imports.add(atributo.getImport());
			}
		}

		if (!umaSecao.isOver() && umaSecao.getInstrucaoAtualLeitura().equals("GIVING")) {
			for (umaSecao.avancaPosicaoLeitura(); !umaSecao.isOver()
					&& !ParagrafosProcedureDivision.acabouParagrafoAtual(umaSecao.getInstrucaoAtualLeitura()); umaSecao
							.avancaPosicaoLeitura()) {
				atributo = encontraIdentificador(umaSecao, dataDivision);
				resultado.add(atributo);
				if (atributo.getClasses() != null) {
					imports.add(atributo.getImport());
				}
			}
		}
	}

	@Override
	public String escreveArquivo() {
		String imprimirMultiplicar = new String();
		String imprimirPor;
		String imprimirResultado = new String();

		imprimirMultiplicar = multiplica.getStringEscritaPorTipo();

		if (resultado.isEmpty()) {
			for (Atributo elemento : por) {
				imprimirPor = elemento.getStringEscritaPorTipo();
				imprimirResultado += ("\t\t" + toLowerFistCase(elemento.getClassesSucessoras())
						+ elemento.getSentencaSet(imprimirMultiplicar + " * " + imprimirPor) + ";\n");

			}
		} else {
			imprimirPor = por.get(0).getStringEscritaPorTipo();
			for (Atributo elemento : resultado) {
				imprimirResultado += ("\t\t" + toLowerFistCase(elemento.getClassesSucessoras())
						+ elemento.getSentencaSet(imprimirMultiplicar + " * " + imprimirPor) + ";\n");
			}
		}

		return imprimirResultado;
	}

	@Override
	public Set<String> escreveImports() {
		Set<String> imprimir = new HashSet<String>();

		if (multiplica.getNome() != null && !multiplica.getNome().isEmpty()) {
			imprimir.addAll(escreveImportsParagrago(imports));
		}

		for (Atributo elemento : por) {
			if (elemento.getNome() != null && !elemento.getNome().isEmpty()) {
				imprimir.addAll(escreveImportsParagrago(imports));
			}
		}

		for (Atributo elemento : resultado) {
			if (elemento.getNome() != null && !elemento.getNome().isEmpty()) {
				imprimir.addAll(escreveImportsParagrago(imports));
			}
		}

		return imprimir;
	}

}
