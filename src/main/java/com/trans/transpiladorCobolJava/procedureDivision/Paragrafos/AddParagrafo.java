package com.trans.transpiladorCobolJava.procedureDivision.Paragrafos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.trans.transpiladorCobolJava.arquivo.Codigo;
import com.trans.transpiladorCobolJava.dataDivision.DataDivision;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.Atributo;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.AtributoElementar;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.AtributoGrupo;
import com.trans.transpiladorCobolJava.procedureDivision.ParagrafosProcedureDivision;

public class AddParagrafo extends Paragrafo implements ParagrafoImpl {

	ArrayList<Atributo> somar = new ArrayList<>();

	ArrayList<Atributo> somarCom = new ArrayList<>();

	ArrayList<Atributo> gravarEm = new ArrayList<>();

	public AddParagrafo(Codigo umaSecao, DataDivision dataDivision) {
		for (; !umaSecao.getInstrucaoAtualLeitura().equals("TO")
				&& !umaSecao.getInstrucaoAtualLeitura().equals("GIVING"); umaSecao.avancaPosicaoLeitura()) {
			Atributo atributo = encontraCriaAtributo(umaSecao, dataDivision);
			somar.add(atributo);
			if (atributo.getClasses() != null) {
				imports.add(atributo.getClasses().get(0));
			}
		}

		if (!umaSecao.getInstrucaoAtualLeitura().equals("GIVING")) {
			for (umaSecao.avancaPosicaoLeitura(); !umaSecao.isOver()
					&& !ParagrafosProcedureDivision.acabouParagrafoAtual(umaSecao.getInstrucaoAtualLeitura())
					&& !umaSecao.getInstrucaoAtualLeitura().equals("GIVING"); umaSecao.avancaPosicaoLeitura()) {
				Atributo atributo = encontraIdentificador(umaSecao, dataDivision);
				somarCom.add(atributo);
				imports.add(atributo.getClasses().get(0));
			}
		}

		if (!umaSecao.isOver() && umaSecao.getInstrucaoAtualLeitura().equals("GIVING")) {
			for (umaSecao.avancaPosicaoLeitura(); !umaSecao.isOver()
					&& !ParagrafosProcedureDivision.acabouParagrafoAtual(umaSecao.getInstrucaoAtualLeitura()); umaSecao
							.avancaPosicaoLeitura()) {
				Atributo atributo = encontraIdentificador(umaSecao, dataDivision);
				gravarEm.add(atributo);
				imports.add(atributo.getClasses().get(0));
			}

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
		for (Atributo elemento : somarCom) {
			if (elemento.getNome() != null && !elemento.getNome().isEmpty()) {
				imprimir.addAll(escreveImportsParagrago(imports));
			}
		}
		return imprimir;
	}

	@Override
	public String escreveArquivo() {
		String imprimirSomar = new String();
		String imprimirSomarLocal = new String();
		String imprimirSomarEm = new String();

		for (Atributo elemento : somar) {
			if (elemento.getNome() == null || elemento.getNome().isEmpty()) {
				imprimirSomar += ((AtributoElementar) elemento).getValor().toString() + " + ";
			} else {
				if (elemento instanceof AtributoElementar) {
					switch (((AtributoElementar) elemento).getTipoAtributo()) {
					case CARACTERE:
						imprimirSomar += "Integer.parseInt(" + toLowerFistCase(elemento.getClassesSucessoras())
								+ elemento.getSentencaGet() + ") + ";
						break;
					case DECIMAL:
					case NUMERO:
						imprimirSomar += toLowerFistCase(elemento.getClassesSucessoras()) + elemento.getSentencaGet()
								+ " + ";
						break;
					}
				} else if (elemento instanceof AtributoGrupo) {
					imprimirSomar += "Integer.parseInt(" + toLowerFistCase(elemento.getClassesSucessoras())
							+ elemento.getSentencaGet() + ".toTrancode()) + ";
				}
			}
		}

		for (Atributo elemento : somarCom) {
			imprimirSomarLocal = imprimirSomar + toLowerFistCase(elemento.getClassesSucessoras())
					+ elemento.getSentencaGet();
			imprimirSomarEm += ("\t\t" + toLowerFistCase(elemento.getClassesSucessoras())
					+ elemento.getSentencaSet(imprimirSomarLocal) + ";\n");
		}

		if (!gravarEm.isEmpty()) {
			for (Atributo elemento : gravarEm) {
				imprimirSomarEm += ("\t\t" + toLowerFistCase(elemento.getClassesSucessoras())
						+ elemento.getSentencaSet((somarCom.isEmpty()) ? imprimirSomar : imprimirSomarLocal) + ";\n");
			}
			imprimirSomarEm = imprimirSomarEm.replaceAll("\s\\+\s\\)", ")");
		}

		return imprimirSomarEm;
	}

}
