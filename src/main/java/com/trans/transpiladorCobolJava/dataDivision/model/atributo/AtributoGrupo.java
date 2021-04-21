package com.trans.transpiladorCobolJava.dataDivision.model.atributo;

import java.io.IOException;
import java.util.Set;

import com.trans.transpiladorCobolJava.arquivo.ArquivoEscrita;

public class AtributoGrupo extends Atributo {

	ArquivoEscrita arquivoEscrita;
	String nomeClase;

	private Set<Atributo> atributos;

	public AtributoGrupo(String nomeAtributo, Integer nivel, Set<Atributo> atributos) {
		super(toUpperFistCase(nomeAtributo), nivel);
		this.atributos = atributos;
		arquivoEscrita = new ArquivoEscrita();
		nomeClase = toUpperFistCase(nomeAtributo);
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

	public String escreveImport() throws IOException {
		arquivoEscrita.abreArquivo("model\\" + nomeClase + ".java");
		arquivoEscrita.escreveLinha("package com.trans.transpiladorCobolJava.model." + nomeClase + ";\n");
		for (Atributo atributoUnitario : atributos) {
			if (atributoUnitario instanceof AtributoGrupo) {
				arquivoEscrita.escreveLinha(atributoUnitario.escreveImport());
			}
		}
		arquivoEscrita.escreveLinha("import java.math.BigDecimal;\n");
		return "import com.trans.transpiladorCobolJava.model." + getNome() + ";";
	}

	public String escreveArquivo() throws IOException {
		arquivoEscrita.escreveLinha("public class " + nomeClase + "{");
		/*
		 * for(Atributo atributoUnitario : atributos) {
		 * arquivoEscrita.escreveLinha(atributoUnitario.escreveImport()); }
		 */
		for (Atributo atributoUnitario : atributos) {
			arquivoEscrita.escreveLinha(atributoUnitario.escreveArquivo());
		}
		arquivoEscrita.escreveLinha("}");
		arquivoEscrita.fechaArquivo();
		return "\tprivate " + nomeClase + " " + nomeClase.toLowerCase() + ";";
	}

	private static String toUpperFistCase(String nome) {
		return nome.substring(0, 1).toUpperCase() + nome.toLowerCase().substring(1);
	}

	@Override
	protected Object getTipoAtributo() {
		// TODO Auto-generated method stub
		return null;
	}

}
