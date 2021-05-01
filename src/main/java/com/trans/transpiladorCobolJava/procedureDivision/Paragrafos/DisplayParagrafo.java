package com.trans.transpiladorCobolJava.procedureDivision.Paragrafos;

import java.util.ArrayList;
import java.util.HashSet;
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
	
	Set<String> imports = new HashSet<String>();

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
						null, null));
			} else if (!PalavrasReservadasDisplayParagrafo.isPresent(umaSecao.getInstrucaoAtualLeitura())) {
				// Identificador
				if (umaSecao.getInstrucaoLeitura(umaSecao.getPosicaoLeitura() + 1).equals("OF")) {
					//texto.add(dataDivision.localizaAtributo(umaSecao.getInstrucaoAtualLeitura(),
					//		umaSecao.getProximaInstrucaoLeitura()));
				} else {
					Atributo atributo = dataDivision.localizaAtributo(umaSecao.getInstrucaoAtualLeitura());
					texto.add(atributo);
					imports.add(atributo.getClasses().get(0));
				}
			}

		}
	}

	@Override
	public Set<String> escreveImports() {
		Set<String> imprimir = new HashSet<String>();
		for (String elemento : imports) {
			imprimir.add("import com.trans.transpiladorCobolJava.model." + elemento + ";\n");
		}
		return imprimir;
	}

	@Override
	public String escreveArquivo() {
		String imprimir = new String();
		for (Atributo elemento : texto) {
			if (elemento.getNome() == null || elemento.getNome().isEmpty()) {
				imprimir += ((AtributoElementar) elemento).getValor();
			} else {
				imprimir += toLowerFistCase(elemento.getClassesSucessoras()) + ((elemento instanceof AtributoGrupo) ? "toTrancode()" : elemento.getSentencaGet());
			}
		}
		return "\t\tSystem.out.println(" + imprimir + ");";
	}

	public ArrayList<Atributo> getTexto() {
		return texto;
	}

	public Set<String> getImports() {
		return imports;
	}
}