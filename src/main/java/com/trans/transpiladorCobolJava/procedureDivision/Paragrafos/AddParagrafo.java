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

public class AddParagrafo extends Paragrafo implements ParagrafoImpl {

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
				Atributo atributo = encontraIdentificador(umaSecao, dataDivision);
				somar.add(atributo);
				imports.add(atributo.getClasses().get(0));
			}
		}

		for (umaSecao.avancaPosicaoLeitura(); !umaSecao.isOver()
				&& !ParagrafosProcedureDivision.acabouParagrafoAtual(umaSecao.getInstrucaoAtualLeitura()); umaSecao
						.avancaPosicaoLeitura()) {
			Atributo atributo = encontraIdentificador(umaSecao, dataDivision);
			adicionarEm.add(atributo);
			imports.add(atributo.getClasses().get(0));
		}

	}

	@Override
	public Set<String> escreveImports() {
		Set<String> imprimir = new HashSet<>();
		for (Atributo elemento : somar) {
			if (elemento.getNome() != null && !elemento.getNome().isEmpty()) {
				imprimir.addAll(escreveImportsParagrago(imports));
			}
		}
		for (Atributo elemento : adicionarEm) {
			if (elemento.getNome() != null && !elemento.getNome().isEmpty()) {
				imprimir.addAll(escreveImportsParagrago(imports));
			}
		}
		return imprimir;
	}

	@Override
	public String escreveArquivo() {
		String imprimirSomar = new String();
		String imprimirSomarLocal = new String ();
		String imprimirSomarEm = new String();

		for (Atributo elemento : somar) {
			if (elemento.getNome() == null || elemento.getNome().isEmpty()) {
				imprimirSomar += ((AtributoElementar) elemento).getValor().toString() + " + ";
			} else {
				if(elemento instanceof AtributoElementar) {
					switch (((AtributoElementar) elemento).getTipoAtributo()) {
					case CARACTERE:
						imprimirSomar += "Integer.parseInt(" + toLowerFistCase(elemento.getClassesSucessoras()) + elemento.getSentencaGet() + ") + ";
						break;
					case DECIMAL:
					case NUMERO:
						imprimirSomar += toLowerFistCase(elemento.getClassesSucessoras()) + elemento.getSentencaGet() + " + ";
						break;
					}
				} else if (elemento instanceof AtributoGrupo) {
					imprimirSomar += "Integer.parseInt(" + toLowerFistCase(elemento.getClassesSucessoras()) + elemento.getSentencaGet() + ".toTrancode()) + ";
				}
			}
		}

		for (Atributo elemento : adicionarEm) {
			imprimirSomarLocal = imprimirSomar + toLowerFistCase(elemento.getClassesSucessoras()) + elemento.getSentencaGet();
			imprimirSomarEm += ("\t\t" + toLowerFistCase(elemento.getClassesSucessoras())
					+ elemento.getSentencaSet(imprimirSomarLocal) + ";\n");
		}
		return imprimirSomarEm;
	}

	@Override
	public Set<String> getImports() {
		return imports;
	}

}
