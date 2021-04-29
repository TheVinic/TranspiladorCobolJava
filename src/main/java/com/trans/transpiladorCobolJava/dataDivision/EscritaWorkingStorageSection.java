package com.trans.transpiladorCobolJava.dataDivision;

import java.io.IOException;
import java.util.ArrayList;

import com.trans.transpiladorCobolJava.arquivo.ArquivoEscrita;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.Atributo;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.AtributoElementar;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.AtributoGrupo;

public class EscritaWorkingStorageSection {

	public String getNomeClasse() {
		return nomeClasse;
	}

	public void setNomeClasse(String nomeClasse) {
		this.nomeClasse = nomeClasse;
	}

	ArquivoEscrita arquivoEscrita = new ArquivoEscrita();
	String nomeClasse = "DadosPrincipais";

	public void escreve(ArrayList<Atributo> atributosWorkingStorage) throws IOException {

		arquivoEscrita.abreArquivo("model\\" + nomeClasse + ".java");
		arquivoEscrita.escreveLinha("package com.trans.transpiladorCobolJava.model." + nomeClasse + ";\n");

		arquivoEscrita.escreveLinha("import java.math.BigDecimal;");

		// For para escrever atributos
		for (Atributo atributoUnitario : atributosWorkingStorage) {
			String imports = atributoUnitario.escreveImportWorkingStorage();
			if (atributoUnitario instanceof AtributoElementar) {
				arquivoEscrita.escreveLinha(imports);
			}
		}
		// For para escrever declaração variaveis
		arquivoEscrita.escreveLinha("\npublic class " + nomeClasse + "{");
		for (Atributo atributoUnitario : atributosWorkingStorage) {
			String variaveis = atributoUnitario.escreveVariaveis();
			if (atributoUnitario instanceof AtributoElementar) {
				arquivoEscrita.escreveLinha(variaveis);
			}
		}
		// For para escrever Get e Set
		for (Atributo atributoUnitario : atributosWorkingStorage) {
			if (atributoUnitario instanceof AtributoElementar) {
				arquivoEscrita.escreveLinha(atributoUnitario.escreveGet());
				arquivoEscrita.escreveLinha(atributoUnitario.escreveSet());
			} else if (atributoUnitario instanceof AtributoGrupo) {
				atributoUnitario.escreveGetSet();
			}
		}
		arquivoEscrita.escreveLinha("}");
		arquivoEscrita.fechaArquivo();
	}

}
