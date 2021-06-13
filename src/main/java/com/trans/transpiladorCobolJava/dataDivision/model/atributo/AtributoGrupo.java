package com.trans.transpiladorCobolJava.dataDivision.model.atributo;

import java.io.IOException;
import java.util.List;

import com.trans.transpiladorCobolJava.DTO.AtributoGrupoResponse;
import com.trans.transpiladorCobolJava.arquivo.ArquivoEscrita;
import com.trans.transpiladorCobolJava.dataDivision.SecoesDataDivision;

public class AtributoGrupo extends Atributo {

	ArquivoEscrita arquivoEscrita;

	private List<Atributo> atributos;

	private Integer comprimento;

	public AtributoGrupo(String nomeAtributo, Integer nivel, List<Atributo> atributos, List<String> classe,
			String occurs, SecoesDataDivision local) {
		super(nomeAtributo, nivel, classe, occurs, local);
		this.atributos = atributos;
		this.comprimento = 0;
		for (Atributo atributo : this.atributos) {
			comprimento += atributo.getComprimento();
		}
		this.arquivoEscrita = new ArquivoEscrita();
	}

	public List<Atributo> getAtributos() {
		return atributos;
	}

	public void setNovoAtributo(Atributo atributo) {
		this.atributos.add(atributo);
	}

	@Override
	public Integer getComprimento() {
		return comprimento;
	}

	public String escreveImportDataDivision(String local) throws IOException {
		arquivoEscrita.abreArquivo(local + "\\" + getNome() + ".java");
		arquivoEscrita.escreveLinha("package com.trans.transpiladorCobolJava." + local + "." + getNome() + ";\n");
		for (Atributo atributoUnitario : atributos) {
			if (atributoUnitario instanceof AtributoGrupo) {
				arquivoEscrita.escreveLinha(((AtributoGrupo) atributoUnitario).escreveImportDataDivision(local));
			}
		}
		arquivoEscrita.escreveLinha("import java.math.BigDecimal;\n");
		return "import com.trans.transpiladorCobolJava." + local + "." + getNome() + ";\n";
	}

	public String escreveVariaveis() throws IOException {
		arquivoEscrita.escreveLinha("public class " + getNome() + "{");

		for (Atributo atributoUnitario : atributos) {
			arquivoEscrita.escreveLinha(atributoUnitario.escreveVariaveis());
		}
		return "\t" + ((filler == true) ? "final " : "private ") + getNome() + getStringDeclaraOccurs() + " " + getNome().toLowerCase() + " = new "
				+ getNome() + getIniciaOccurs() + "();";
	}

	public void escreveGetSet() throws IOException {
		for (Atributo atributoUnitario : atributos) {
			arquivoEscrita.escreveLinha(atributoUnitario.escreveGet());
			arquivoEscrita.escreveLinha(atributoUnitario.escreveSet());
			if (atributoUnitario instanceof AtributoGrupo) {
				((AtributoGrupo) atributoUnitario).escreveGetSet();
			}
		}
	}

	public void escreveToString() {
		arquivoEscrita.escreveLinha("\n\tpublic String toTrancode() { ");
		String toString = "\t\treturn ";
		for (Atributo atributoUnitario : atributos) {
			if (atributoUnitario instanceof AtributoElementar) {
				toString += "String.format(" + ((AtributoElementar) atributoUnitario).getComprimentoToString() + ", "
						+ atributoUnitario.getNome().toLowerCase() + ") + ";
			} else {
				toString += atributoUnitario.getNome().toLowerCase() + ".toTrancode() + ";
				((AtributoGrupo) atributoUnitario).escreveToString();
			}
		}
		toString = toString.substring(0, toString.length() - 3);
		arquivoEscrita.escreveLinha(toString + ";\n\t}");
	}

	public void escreveToObject() {
		arquivoEscrita.escreveLinha("\n\tpublic void toObject(String trancode) { ");
		Integer posicao = 0;
		for (Atributo atributoUnitario : atributos) {
			if (atributoUnitario instanceof AtributoElementar) {
				arquivoEscrita.escreveLinha(
						"\t\tthis." + atributoUnitario.getNome().toLowerCase() + " = " + "trancode.substring(" + posicao
								+ ", " + (posicao += atributoUnitario.getComprimento()) + ");");
			} else {
				arquivoEscrita.escreveLinha(
						"\t\tthis." + atributoUnitario.getNome().toLowerCase() + ".toObject(trancode.substring("
								+ posicao + ", " + (posicao += atributoUnitario.getComprimento()) + "));");
				((AtributoGrupo) atributoUnitario).escreveToObject();
			}
		}
		arquivoEscrita.escreveLinha("\t}");
		arquivoEscrita.escreveLinha("}");
		arquivoEscrita.fechaArquivo();
	}

	@Override
	public String tipoObjeto() {
		return toUpperFistCase(getNome());
	}

	public Atributo getLocalizaAtributo(String nomeVariavel) {
		for (Atributo atributo : atributos) {
			if (atributo.getNome().equalsIgnoreCase(nomeVariavel)) {
				return atributo;
			} else if (atributo instanceof AtributoGrupo) {
				Atributo subAtributo = ((AtributoGrupo) atributo).getLocalizaAtributo(nomeVariavel);
				if (subAtributo != null) {
					return subAtributo;
				}
			}
		}
		return null;
	}

	public AtributoGrupoResponse toResponse() {
		return new AtributoGrupoResponse(getNome(), getNivel(), getOccurs(), atributos);
	}
}
