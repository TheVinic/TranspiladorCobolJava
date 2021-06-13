package com.trans.transpiladorCobolJava.procedureDivision.Paragrafos;

import java.util.ArrayList;
import java.util.Set;

public class SectionParagrafo extends Paragrafo {

	String nome;
	
	Set<String> importsSecao;	
	
	public SectionParagrafo(String name) {
		this.nome = name;
	}

	public void setImportSecao(ArrayList<ProcedureDivisionSection> secoes) {
		for(ProcedureDivisionSection elemento : secoes) {
			if(elemento.getNomeClasse().equals(nome)) {
				imports=elemento.getImports();
				return;
			}
		}
	}
	
	@Override
	public String escreveArquivo(Integer nivel) {
		String imprimir = fazTabulacao(nivel) + nome.toLowerCase() + "." + nome.toLowerCase() + "(";
		for(String elemento : imports) {
			imprimir += elemento.toLowerCase() + ", ";
		}
		imprimir = imprimir.substring(0, imprimir.length()-2) + ");\n";
		return imprimir;
	}

	
	
}
