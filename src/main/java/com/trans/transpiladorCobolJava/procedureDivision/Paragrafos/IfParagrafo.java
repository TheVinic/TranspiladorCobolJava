package com.trans.transpiladorCobolJava.procedureDivision.Paragrafos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.trans.transpiladorCobolJava.arquivo.Codigo;
import com.trans.transpiladorCobolJava.dataDivision.DataDivision;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.Atributo;
import com.trans.transpiladorCobolJava.procedureDivision.ParagrafosProcedureDivision;
import com.trans.transpiladorCobolJava.procedureDivision.ProcedureDivision;

public class IfParagrafo extends Paragrafo {

	ArrayList<Atributo> condicao = new ArrayList<Atributo>();

	ArrayList<Paragrafo> entao;
	ArrayList<Paragrafo> seNao;

	public IfParagrafo(Codigo umaSecao, DataDivision dataDivision) {
		for (; !ParagrafosProcedureDivision.acabouParagrafoAtual(umaSecao.getInstrucaoAtualLeitura()); umaSecao
				.avancaPosicaoLeitura()) {
			Atributo atributo;
			if (false) {
				// TODO colocar validação se não houver espaço
			} else {
				atributo = encontraCriaAtributo(umaSecao, dataDivision);
				condicao.add(atributo);
				if (atributo.getClasses() != null) {
					imports.add(atributo.getImport());
				}
			}
		}
		entao = new ProcedureDivision().analiseSemantica(umaSecao, dataDivision);
		if (umaSecao.getInstrucaoAtualLeitura().equals("ELSE")) {
			umaSecao.getProximaInstrucaoLeitura();
			seNao = new ProcedureDivision().analiseSemantica(umaSecao, dataDivision);
		} else {
			umaSecao.getProximaInstrucaoLeitura();
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
			imprimirCondicao += paragrafo.escreveArquivo(nivel+1);
		}
		imprimirCondicao += fazTabulacao(nivel) + "}";

		if (seNao != null) {
			imprimirCondicao += " else {\n";
			for (Paragrafo paragrafo : seNao) {
				imprimirCondicao +=  paragrafo.escreveArquivo(nivel+1);
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
		for (Atributo elemento : condicao) {
			if (elemento.getNome() != null && !elemento.getNome().isEmpty()) {
				imprimir.addAll(escreveImportsParagrago(imports));
			}
		}
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

}