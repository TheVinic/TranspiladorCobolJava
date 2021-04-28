package com.trans.transpiladorCobolJava.procedureDivision.Paragrafos;

import java.util.Set;

public interface Paragrafo {

	public String escreveArquivo();

	public Set<String> escreveImports();
	
	public Set<String> getImports();
	
}
