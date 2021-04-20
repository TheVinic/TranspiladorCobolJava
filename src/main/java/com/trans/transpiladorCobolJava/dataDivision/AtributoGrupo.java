package com.trans.transpiladorCobolJava.dataDivision;

import java.io.IOException;
import java.util.Set;

import com.trans.transpiladorCobolJava.main.ArquivoEscrita;

public class AtributoGrupo extends Atributo{

	ArquivoEscrita arquivoEscrita = new ArquivoEscrita();
	
	private Set<Atributo> atributos;

	public AtributoGrupo(String nomeAtributo, Integer nivel, Set<Atributo> atributos) {
		super(nomeAtributo, nivel);
		this.atributos = atributos;
	}

	public Set<Atributo> getAtributos() {
		return atributos;
	}

	public void setNovoAtributo(Atributo atributo) {
		this.atributos.add(atributo);
	}

	@Override
	public String toString() {
		return "\nAtributoGrupo [nivel=" + getNivel() + ", nome=" + getNome() + " atributos=" + atributos + "]";
	}
	
	@Override
	public String escreveArquivo() throws IOException {
		String nomeClase = toUpperFistCase(getNome());
		arquivoEscrita.abreArquivo(nomeClase + ".java");
		arquivoEscrita.escreveLinha("public class " + nomeClase + "{"); 
		for(Atributo atributoUnitario : atributos) {
			arquivoEscrita.escreveLinha(atributoUnitario.escreveArquivo());
		}
		arquivoEscrita.escreveLinha("}");
		arquivoEscrita.fechaArquivo();
		return "\tprivate " + nomeClase + " " + getNome() + ";";
	}

	private String toUpperFistCase(String nome) {
		return nome.substring( 0, 1 ).toUpperCase() + nome.substring( 1 );
	}
	
}
