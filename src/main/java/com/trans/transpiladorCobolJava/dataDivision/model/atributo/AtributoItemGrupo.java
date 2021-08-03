package com.trans.transpiladorCobolJava.dataDivision.model.atributo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.trans.transpiladorCobolJava.DTO.AtributoGrupoResponse;
import com.trans.transpiladorCobolJava.arquivo.ArquivoEscrita;
import com.trans.transpiladorCobolJava.dataDivision.SecoesDataDivision;
import com.trans.transpiladorCobolJava.dataDivision.model.TipoAtributo;

public class AtributoItemGrupo extends Atributo {

	private ArquivoEscrita arquivoEscrita;

	private ArquivoEscrita repositoryJPA;
	private List<String> instrucoesRepository;

	private List<Atributo> atributos;

	private Integer comprimento;

	private boolean isTabela;
	private boolean isEmbeddable;

	private Integer quantidadeQuerysRespository;

	public AtributoItemGrupo(String nomeAtributo, Integer nivel, List<Atributo> atributos, List<String> classe,
			String occurs, SecoesDataDivision local) {
		super(nomeAtributo, nivel, classe, occurs, local);
		this.atributos = atributos;
		this.comprimento = 0;
		for (Atributo atributo : this.atributos) {
			comprimento += atributo.getComprimento();
		}
		this.arquivoEscrita = new ArquivoEscrita();
		this.isTabela = false;
		this.isEmbeddable = false;
		this.quantidadeQuerysRespository = 0;
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
			if (atributoUnitario instanceof AtributoItemGrupo) {
				arquivoEscrita.escreveLinha(((AtributoItemGrupo) atributoUnitario).escreveImportDataDivision(local));
			}
		}
		arquivoEscrita.escreveLinha("import java.math.BigDecimal;\n");
		return "import com.trans.transpiladorCobolJava." + local + "." + getNome() + ";\n";
	}

	public String escreveVariaveis() throws IOException {
		if (isTabela || isEmbeddable) {
			arquivoEscrita.escreveLinha("import javax.persistence.Entity;");
			arquivoEscrita.escreveLinha("import javax.persistence.Id;\n");
			arquivoEscrita.escreveLinha("@Entity");
			arquivoEscrita.escreveLinha("public class " + toUpperFistCase(getNome()) + "{");
			arquivoEscrita.escreveLinha("\t@Id");
		} else {
			arquivoEscrita.escreveLinha("public class " + toUpperFistCase(getNome()) + "{");
		}

		for (Atributo atributoUnitario : atributos) {
			arquivoEscrita.escreveLinha(atributoUnitario.escreveVariaveis());
		}
		return ((isEmbeddable) ? "\t@JoinColumn\n" : "") + "\t" + ((filler == true) ? "final " : "private ")
				+ toUpperFistCase(getNome()) + getStringDeclaraOccurs() + " " + getNome().toLowerCase() + " = new "
				+ toUpperFistCase(getNome()) + getIniciaOccurs() + "();";
	}

	public void escreveGetSet() throws IOException {
		for (Atributo atributoUnitario : atributos) {
			arquivoEscrita.escreveLinha(atributoUnitario.escreveGet());
			arquivoEscrita.escreveLinha(atributoUnitario.escreveSet());
			if (atributoUnitario instanceof AtributoItemGrupo) {
				((AtributoItemGrupo) atributoUnitario).escreveGetSet();
			}
		}
	}

	public void escreveToTrancode() {
		arquivoEscrita.escreveLinha("\n\tpublic String toTrancode() { ");
		String toString = "\t\treturn ";
		for (Atributo atributoUnitario : atributos) {
			if (atributoUnitario instanceof AtributoElementar) {
				toString += "String.format(" + ((AtributoElementar) atributoUnitario).getComprimentoToString() + ", "
						+ atributoUnitario.getNome().toLowerCase() + ") + ";
			} else {
				toString += atributoUnitario.getNome().toLowerCase() + ".toTrancode() + ";
				((AtributoItemGrupo) atributoUnitario).escreveToTrancode();
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
				arquivoEscrita.escreveLinha("\t\tthis." + atributoUnitario.getNome().toLowerCase() + " = "
						+ ((((AtributoElementar) atributoUnitario).getTipoAtributo().equals(TipoAtributo.NUMERO))
								? "Integer.parseInt("
								: "")
						+ "trancode.substring(" + posicao + ", " + (posicao += atributoUnitario.getComprimento()) + ")"
						+ ((((AtributoElementar) atributoUnitario).getTipoAtributo().equals(TipoAtributo.NUMERO)) ? ")"
								: "")
						+ ";");
			} else {
				arquivoEscrita.escreveLinha(
						"\t\tthis." + atributoUnitario.getNome().toLowerCase() + ".toObject(trancode.substring("
								+ posicao + ", " + (posicao += atributoUnitario.getComprimento()) + "));");
				((AtributoItemGrupo) atributoUnitario).escreveToObject();
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
			} else if (atributo instanceof AtributoItemGrupo) {
				Atributo subAtributo = ((AtributoItemGrupo) atributo).getLocalizaAtributo(nomeVariavel);
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

	public boolean isTabela() {
		return isTabela;
	}

	public void setIsTabela() {
		if (!isEmbeddable && !isTabela) {
			this.isTabela = true;
			this.instrucoesRepository = new ArrayList<String>();
			for (Atributo atributo : atributos) {
				if (atributo instanceof AtributoItemGrupo) {
					((AtributoItemGrupo) atributo).isEmbedable();
				}
			}
		}
	}

	public void isEmbedable() {
		this.isEmbeddable = true;
		for (Atributo atributo : atributos) {
			if (atributo instanceof AtributoItemGrupo) {
				((AtributoItemGrupo) atributo).isEmbedable();
			}
		}
	}

	public String setAtributoIsTabela(String nomeTabela) {
		for (Atributo atributo : atributos) {
			if (atributo instanceof AtributoItemGrupo) {
				if (atributo.getNome().equalsIgnoreCase(nomeTabela)) {
					((AtributoItemGrupo) atributo).setIsTabela();
					return atributo.getNome() + (++quantidadeQuerysRespository);
				} else {
					String nomeRepository = ((AtributoItemGrupo) atributo).setAtributoIsTabela(nomeTabela);
					if (nomeRepository != null && !nomeRepository.isEmpty()) {
						return nomeRepository + (++quantidadeQuerysRespository);
					}
				}
			}
		}
		return null;
	}

	public void escreveRepository() throws IOException {
		if (isTabela) {
			repositoryJPA = new ArquivoEscrita();
			repositoryJPA.abreArquivo("repository" + "\\" + toUpperFistCase(getNome()) + "Repository" + ".java");
			repositoryJPA.escreveLinha("package com.trans.transpiladorCobolJava." + "repository" + "."
					+ toUpperFistCase(getNome()) + "Repository" + ";\n");
			repositoryJPA.escreveLinha("import java.math.BigDecimal;");
			repositoryJPA.escreveLinha("import java.util.Optional;\n");
			repositoryJPA.escreveLinha("import org.springframework.data.jpa.repository.JpaRepository;");
			repositoryJPA.escreveLinha("import org.springframework.stereotype.Repository;");
			repositoryJPA.escreveLinha("import org.springframework.data.jpa.repository.Query;\n");
			repositoryJPA.escreveLinha("@Repository");
			repositoryJPA
					.escreveLinha("public interface " + toUpperFistCase(getNome()) + "Repository extends JpaRepository<"
							+ toUpperFistCase(getNome()) + ", " + atributos.get(0).tipoObjeto() + ">{\n");
			for (String instrucaoRepository : instrucoesRepository) {
				repositoryJPA.escreveLinha(instrucaoRepository + "\n");
			}
			repositoryJPA.escreveLinha("}");
			repositoryJPA.fechaArquivo();
		}
		for (Atributo atributoUnitario : atributos) {
			if (atributoUnitario instanceof AtributoItemGrupo) {
				((AtributoItemGrupo) atributoUnitario).escreveRepository();
			}
		}
	}

	public boolean setIntrucaoRepository(String instrucao, String tabela) {
		for (Atributo atributo : atributos) {
			if (atributo instanceof AtributoItemGrupo) {
				if (atributo.getNome().equalsIgnoreCase(tabela)) {
					((AtributoItemGrupo) atributo).setInstrucao(instrucao);
					return true;
				} else {
					String nomeRepository = ((AtributoItemGrupo) atributo).setAtributoIsTabela(tabela);
					if (nomeRepository != null && !nomeRepository.isEmpty()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private void setInstrucao(String instrucao) {
		instrucoesRepository.add(instrucao);
	}
}
