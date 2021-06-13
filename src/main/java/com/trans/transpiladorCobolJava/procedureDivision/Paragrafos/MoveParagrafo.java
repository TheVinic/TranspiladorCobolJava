package com.trans.transpiladorCobolJava.procedureDivision.Paragrafos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.trans.transpiladorCobolJava.arquivo.Codigo;
import com.trans.transpiladorCobolJava.dataDivision.DataDivision;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.Atributo;

public class MoveParagrafo extends Paragrafo  {

	Atributo de;

	ArrayList<Atributo> para = new ArrayList<Atributo>();

	public MoveParagrafo(Codigo umaSecao, DataDivision dataDivision) {

		Atributo atributo = encontraCriaAtributo(umaSecao, dataDivision);
		de = atributo;
		if (atributo.getClasses() != null) {
			imports.add(atributo.getImport());
		}
		umaSecao.avancaPosicaoLeitura();
		for (umaSecao.avancaPosicaoLeitura(); !umaSecao.isOver(); umaSecao.avancaPosicaoLeitura()) {
			atributo = encontraIdentificador(umaSecao, dataDivision);
			para.add(atributo);
			if (atributo.getClasses() != null) {
				imports.add(atributo.getImport());
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
					+ elemento.getSentencaSet(imprimirDe) + ";\n");
		}
		return imprimirMove;
	}

}
