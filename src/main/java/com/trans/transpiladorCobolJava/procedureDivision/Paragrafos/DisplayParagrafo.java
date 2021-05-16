package com.trans.transpiladorCobolJava.procedureDivision.Paragrafos;

import java.util.ArrayList;
import java.util.Set;

import com.trans.transpiladorCobolJava.arquivo.Codigo;
import com.trans.transpiladorCobolJava.dataDivision.DataDivision;
import com.trans.transpiladorCobolJava.dataDivision.model.TipoAtributo;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.Atributo;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.AtributoElementar;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.AtributoGrupo;
import com.trans.transpiladorCobolJava.procedureDivision.ParagrafosProcedureDivision;

public class DisplayParagrafo extends Paragrafo implements ParagrafoImpl {

	ArrayList<Atributo> texto = new ArrayList<Atributo>();

	public DisplayParagrafo(Codigo umaSecao, DataDivision dataDivision) {
		for (; !umaSecao.isOver()
				&& !ParagrafosProcedureDivision.acabouParagrafoAtual(umaSecao.getInstrucaoAtualLeitura()); umaSecao
						.avancaPosicaoLeitura()) {
			if (umaSecao.getInstrucaoAtualLeitura().substring(0, 1).equals("\"")) {
				String frase = new String();
				frase += umaSecao.getInstrucaoAtualLeitura() + " ";
				while (!frase.endsWith("\" ") && !frase.endsWith("\". ")) {
					frase += umaSecao.getProximaInstrucaoLeitura() + " ";
				}
				texto.add(new AtributoElementar(new String(), null, frase.length(), null, TipoAtributo.CARACTERE, frase,
						null, null, null));
			} else if (!PalavrasReservadasDisplayParagrafo.isPresent(umaSecao.getInstrucaoAtualLeitura())) {
				// Identificador
				Atributo atributo = encontraIdentificador(umaSecao, dataDivision);
				texto.add(atributo);
				imports.add(atributo.getImport());
			}

		}
	}

	@Override
	public Set<String> escreveImports() {
		return escreveImportsParagrago(imports);
	}

	@Override
	public String escreveArquivo() {
		String imprimir = new String();
		for (Atributo elemento : texto) {
			if (elemento.getNome() == null || elemento.getNome().isEmpty()) {
				imprimir += ((AtributoElementar) elemento).getValor() + " + ";
			} else {
				imprimir += toLowerFistCase(elemento.getClassesSucessoras())
						+ ((elemento instanceof AtributoGrupo) ? "toTrancode()" : elemento.getSentencaGet()) + " + ";
			}
		}
		imprimir = imprimir.substring(0, imprimir.length() - 3);
		return "\t\tSystem.out.println(" + imprimir + ");";
	}

	public ArrayList<Atributo> getTexto() {
		return texto;
	}
}