package com.trans.transpiladorCobolJava.dataDivision.model.atributo;

import java.io.IOException;
import java.util.List;

import com.trans.transpiladorCobolJava.arquivo.ArquivoEscrita;

public class AtributoGrupo extends Atributo {

	ArquivoEscrita arquivoEscrita;

	private List<Atributo> atributos;

	public AtributoGrupo(String nomeAtributo, Integer nivel, List<Atributo> atributos, List<String> classe,
			Integer occurs) {
		super(toUpperFistCase(nomeAtributo), nivel, classe, occurs);
		this.atributos = atributos;
		arquivoEscrita = new ArquivoEscrita();
	}

	public List<Atributo> getAtributos() {
		return atributos;
	}

	public void setNovoAtributo(Atributo atributo) {
		this.atributos.add(atributo);
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

		for (Atributo atributoUnitario : atributos) {
			arquivoEscrita.escreveLinha(atributoUnitario.escreveVariaveis());
		}
		return "\tprivate " + getNome() + getStringDeclaraOccurs() + " " + getNome().toLowerCase() + " = new " + getNome() + getIniciaOccurs() + ";";
	}

	@Override
	public void escreveGetSet() throws IOException {
		for (Atributo atributoUnitario : atributos) {
			arquivoEscrita.escreveLinha(atributoUnitario.escreveGet());
			arquivoEscrita.escreveLinha(atributoUnitario.escreveSet());
			if (atributoUnitario instanceof AtributoGrupo) {
				atributoUnitario.escreveGetSet();
			}
		}
		arquivoEscrita.escreveLinha("}");
		arquivoEscrita.fechaArquivo();
	}

	@Override
	@Deprecated
	public Object getValor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String tipoObjeto() {
		return toUpperFistCase(getNome());
	}
}
