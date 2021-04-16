package com.trans.transpiladorCobolJava.environmentDivision;

import com.trans.transpiladorCobolJava.arquivo.Arquivo;
import com.trans.transpiladorCobolJava.arquivo.Palavra;

public class EnvironmentDivision {

	public Palavra popula(Arquivo arquivo) {
		System.out.println("Iniciando ENVIRONMENT DIVISION");
		
		Palavra paragrafoEnvironmentDivision = arquivo.lerAte('.');
		
		return null;
	}

}
