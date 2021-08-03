package com.trans.transpiladorCobolJava.procedureDivision.Paragrafos;

import java.util.ArrayList;
import java.util.Set;

public class SectionParagrafo extends Paragrafo {

	String nome;

	Set<String> importsSecao;

	public SectionParagrafo(String name) {
		this.nome = name;
		this.imports.add(name);
	}

	public void setImportSecao(ArrayList<ProcedureDivisionSection> secoes) {
		for (ProcedureDivisionSection elemento : secoes) {
			if (elemento.getNomeClasse().equalsIgnoreCase(nome)) {
				importsSecao = elemento.getImportsSecao();
				imports.addAll(importsSecao);
				return;
			}
		}
	}

	@Override
	public String escreveArquivo(Integer nivel) {
		String imprimir = fazTabulacao(nivel) + nome.toLowerCase() + "." + nome.toLowerCase() + "(";
		if (!importsSecao.isEmpty()) {
			for (String elemento : importsSecao) {
				imprimir += elemento.toLowerCase() + ", ";
			}
			imprimir = imprimir.substring(0, imprimir.length() - 2) + ");";
		}else {
			imprimir += ");";
		}
		return imprimir;
	}

}
