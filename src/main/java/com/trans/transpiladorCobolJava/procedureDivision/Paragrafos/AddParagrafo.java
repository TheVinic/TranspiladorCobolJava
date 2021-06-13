package com.trans.transpiladorCobolJava.procedureDivision.Paragrafos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.trans.transpiladorCobolJava.arquivo.Codigo;
import com.trans.transpiladorCobolJava.dataDivision.DataDivision;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.Atributo;
import com.trans.transpiladorCobolJava.procedureDivision.ParagrafosProcedureDivision;

public class AddParagrafo extends Paragrafo {

	ArrayList<Atributo> somar = new ArrayList<>();

	ArrayList<Atributo> somarCom = new ArrayList<>();

	ArrayList<Atributo> gravarEm = new ArrayList<>();

	public AddParagrafo(Codigo umaSecao, DataDivision dataDivision) {
		for (; !umaSecao.getInstrucaoAtualLeitura().equals("TO")
				&& !umaSecao.getInstrucaoAtualLeitura().equals("GIVING"); umaSecao.avancaPosicaoLeitura()) {
			Atributo atributo = encontraCriaAtributo(umaSecao, dataDivision);
			somar.add(atributo);
			if (atributo.getClasses() != null) {
				imports.add(atributo.getImport());
			}
		}

		if (!umaSecao.getInstrucaoAtualLeitura().equals("GIVING")) {
			for (umaSecao.avancaPosicaoLeitura(); !umaSecao.isOver()
					&& !ParagrafosProcedureDivision.acabouParagrafoAtual(umaSecao.getInstrucaoAtualLeitura())
					&& !umaSecao.getInstrucaoAtualLeitura().equals("GIVING"); umaSecao.avancaPosicaoLeitura()) {
				Atributo atributo = encontraIdentificador(umaSecao, dataDivision);
				somarCom.add(atributo);
				imports.add(atributo.getImport());
			}
		}

		if (!umaSecao.isOver() && umaSecao.getInstrucaoAtualLeitura().equals("GIVING")) {
			for (umaSecao.avancaPosicaoLeitura(); !umaSecao.isOver()
					&& !ParagrafosProcedureDivision.acabouParagrafoAtual(umaSecao.getInstrucaoAtualLeitura()); umaSecao
							.avancaPosicaoLeitura()) {
				Atributo atributo = encontraIdentificador(umaSecao, dataDivision);
				gravarEm.add(atributo);
				imports.add(atributo.getImport());
			}

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
