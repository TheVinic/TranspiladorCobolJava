package com.trans.transpiladorCobolJava.procedureDivision.Paragrafos;

import java.util.ArrayList;

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
		boolean acabou = false;
		for (; !ParagrafosProcedureDivision.acabouParagrafoAtual(umaSecao.getInstrucaoAtualLeitura())
				&& !acabou; umaSecao.avancaPosicaoLeitura()) {
			if (umaSecao.getInstrucaoAtualLeitura().substring(0, 1).equals("\"")) {
				String frase = new String();
				frase += umaSecao.getInstrucaoAtualLeitura() + " ";
				while (!frase.endsWith("\" ") && !frase.endsWith("\". ")) {
					frase += umaSecao.getProximaInstrucaoLeitura() + " ";
				}
				if (frase.endsWith("\". ")) {
					frase = frase.substring(0, frase.length() - 2);
					acabou = true;
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
		umaSecao.isOver();
	}

	@Override
	public String escreveArquivo(Integer nivel) {
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
		return fazTabulacao(nivel) + "System.out.println(" + imprimir + ");\n";
	}

	public ArrayList<Atributo> getTexto() {
		return texto;
	}
}