package com.trans.transpiladorCobolJava.dataDivision.model.atributo;

import java.io.IOException;
import java.util.List;

import com.trans.transpiladorCobolJava.arquivo.ArquivoEscrita;

public class AtributoGrupo extends Atributo {

	ArquivoEscrita arquivoEscrita;

	private List<Atributo> atributos;

	public AtributoGrupo(String nomeAtributo, Integer nivel, List<Atributo> atributos, List<String> classe) {
		super(toUpperFistCase(nomeAtributo), nivel, classe);
		this.atributos = atributos;
		arquivoEscrita = new ArquivoEscrita();
	}

	public List<Atributo> getAtributos() {
		return atributos;
	}

	public void setNovoAtributo(Atributo atributo) {
		this.atributos.add(atributo);
	}

	@Override
	public String toString() {
		return "\nAtributoGrupo [nivel=" + getNivel() + ", nome=" + getNome() + " atributos=" + atributos + "]";
	}

	public String escreveImportWorkingStorage() throws IOException {
		arquivoEscrita.abreArquivo("model\\" + getNome() + ".java");
		arquivoEscrita.escreveLinha("package com.trans.transpiladorCobolJava.model." + getNome() + ";\n");
		for (Atributo atributoUnitario : atributos) {
			if (atributoUnitario instanceof AtributoGrupo) {
				arquivoEscrita.escreveLinha(atributoUnitario.escreveImportWorkingStorage());
			}
		}
		arquivoEscrita.escreveLinha("import java.math.BigDecimal;\n");
		return "import com.trans.transpiladorCobolJava.model." + getNome() + ";\n";
	}

	public String escreveVariaveis() throws IOException {
		arquivoEscrita.escreveLinha("public class " + getNome() + "{");
		/*
		 * for(Atributo atributoUnitario : atributos) {
		 * arquivoEscrita.escreveLinha(atributoUnitario.escreveImport()); }
		 */
		for (Atributo atributoUnitario : atributos) {
			arquivoEscrita.escreveLinha(atributoUnitario.escreveVariaveis());
		}
		arquivoEscrita.escreveLinha("}");
		arquivoEscrita.fechaArquivo();
		return "\tprivate " + getNome() + " " + getNome().toLowerCase() + ";";
	}

	private static String toUpperFistCase(String nome) {
		return nome.substring(0, 1).toUpperCase() + nome.toLowerCase().substring(1);
	}


	@Override
	public String escreveGet() throws IOException {
		return "\n\tpublic " + getNome() + " get" + getNome() + "() {\n\t\t\treturn " + getNome() + ";\n\t}";
	}

	@Override
	public String escreveSet() {
		return "\n\tpublic " + getNome() + " set" + getNome() + "() {\n\t\t\treturn " + getNome() + ";\n\t}";
	}

	@Override
	public String getSentencaGet() {
		return "get" + getNome() + "()";
	}
	

	@Override
	public String getSentencaSet() {
		return "set" + getNome() + "(" + getNome() + " " + getNome().toLowerCase() + ")";
	}

	@Override
	@Deprecated
	public Object getValor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Deprecated
	public String getImport() {
		// TODO Auto-generated method stub
		return null;
	}
}
