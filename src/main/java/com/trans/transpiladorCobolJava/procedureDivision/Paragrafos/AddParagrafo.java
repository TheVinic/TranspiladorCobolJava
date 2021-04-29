package com.trans.transpiladorCobolJava.procedureDivision.Paragrafos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.trans.transpiladorCobolJava.arquivo.Codigo;
import com.trans.transpiladorCobolJava.dataDivision.DataDivision;
import com.trans.transpiladorCobolJava.dataDivision.model.TipoAtributo;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.Atributo;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.AtributoElementar;
import com.trans.transpiladorCobolJava.procedureDivision.ParagrafosProcedureDivision;

public class AddParagrafo implements Paragrafo {

	ArrayList<Atributo> somar = new ArrayList<>();

	ArrayList<Atributo> adicionarEm = new ArrayList<>();

	Set<String> imports = new HashSet<String>();

	public AddParagrafo(Codigo umaSecao, DataDivision dataDivision) {
		String elemento;
		for (; !umaSecao.getInstrucaoAtualLeitura().equals("TO"); umaSecao.avancaPosicaoLeitura()) {
			elemento = umaSecao.getInstrucaoAtualLeitura();
			if (elemento.matches("[0-9]+")) {
				// Tipo númerico
				somar.add(new AtributoElementar(null, null, elemento.length(), null, TipoAtributo.NUMERO, elemento,
						null, null));
			} else if (elemento.matches("[0-9]+\\,[0-9]+")) {
				// Tipo decimal
				somar.add(new AtributoElementar(null, null, elemento.length(), null, TipoAtributo.DECIMAL, elemento,
						null, null));
			} else {
				// Identificador
				if (umaSecao.getInstrucaoLeitura(umaSecao.getPosicaoLeitura() + 1).equals("OF")) {
					somar.add(dataDivision.localizaAtributo(elemento, umaSecao.getProximaInstrucaoLeitura()));
				} else {
					somar.add(dataDivision.localizaAtributo(elemento));
				}
			}
		}

		for (umaSecao.avancaPosicaoLeitura(); !umaSecao.isOver()
				&& !ParagrafosProcedureDivision.acabouParagrafoAtual(umaSecao.getInstrucaoAtualLeitura()); umaSecao
						.avancaPosicaoLeitura()) {
			elemento = umaSecao.getInstrucaoAtualLeitura();
			if (umaSecao.getInstrucaoLeitura(umaSecao.getPosicaoLeitura() + 1).equals("OF")) {
				adicionarEm.add(dataDivision.localizaAtributo(elemento, umaSecao.getProximaInstrucaoLeitura()));
			} else {
				adicionarEm.add(dataDivision.localizaAtributo(elemento));
			}

		}

	}

	@Override
	public Set<String> escreveImports() {
		Set<String> imprimir = new HashSet<>();
		for (Atributo elemento : somar) {
			if (elemento.getNome() != null && !elemento.getNome().isEmpty()) {
				//imprimir.add(elemento.getImport());
			}
		}
		for (Atributo elemento : adicionarEm) {
			if (elemento.getNome() != null && !elemento.getNome().isEmpty()) {
				//imprimir.add(elemento.getImport());
			}
		}
		return imprimir;
	}

	@Override
	public String escreveArquivo() {
		String imprimirSomar = new String();
		String imprimirSomarEm = new String();

		for (Atributo elemento : somar) {
			if (elemento.getNome() == null || elemento.getNome().isEmpty()) {
				imprimirSomar += elemento.getValor().toString();
			} else {
				imprimirSomar += toLowerFistCase(elemento.getClassesSucessoras()) + elemento.getSentencaGet() + " + ";
			}
		}
		imprimirSomar = imprimirSomar.substring(0, imprimirSomar.length()-3);

		for (Atributo elemento : adicionarEm) {
			imprimirSomarEm += ("\t\t" + toLowerFistCase(elemento.getClassesSucessoras()) + elemento.getSentencaSet(imprimirSomar) + ";\n");
		}
		return imprimirSomarEm;
	}

	@Override
	public Set<String> getImports() {
		return imports;
	}

	private static String toLowerFistCase(String nome) {
		return nome.substring(0, 1).toLowerCase() + nome.substring(1);
	}

}
