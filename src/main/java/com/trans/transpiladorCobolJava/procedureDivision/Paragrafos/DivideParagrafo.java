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

public class DivideParagrafo extends Paragrafo implements ParagrafoImpl {

	ArrayList<Atributo> dividendo = new ArrayList<Atributo>();

	ArrayList<Atributo> divisor = new ArrayList<Atributo>();

	ArrayList<Atributo> quociente = new ArrayList<Atributo>();

	ArrayList<Atributo> resto = new ArrayList<Atributo>();

	public DivideParagrafo(Codigo umaSecao, DataDivision dataDivision) {
		for (; !umaSecao.getInstrucaoAtualLeitura().equals("INTO")
				&& !umaSecao.getInstrucaoAtualLeitura().equals("BY"); umaSecao.avancaPosicaoLeitura()) {
			Atributo atributo = encontraCriaAtributo(umaSecao, dataDivision);
			divisor.add(atributo);
			if (atributo.getClasses() != null) {
				imports.add(atributo.getClasses().get(0));
			}
		}

		if (umaSecao.getInstrucaoAtualLeitura().equals("INTO")) {
			for (umaSecao.avancaPosicaoLeitura(); !umaSecao.isOver()
					&& !umaSecao.getInstrucaoAtualLeitura().equals("GIVING")
					&& !umaSecao.getInstrucaoAtualLeitura().equals("REMAINDER")
					&& !ParagrafosProcedureDivision.acabouParagrafoAtual(umaSecao.getInstrucaoAtualLeitura()); umaSecao
							.avancaPosicaoLeitura()) {
				Atributo atributo = encontraIdentificador(umaSecao, dataDivision);
				dividendo.add(atributo);
				quociente.add(atributo);
				if (atributo.getClasses() != null) {
					imports.add(atributo.getClasses().get(0));
				}
			}
		} else {
			dividendo.addAll(divisor);
			divisor = new ArrayList<Atributo>();
			for (umaSecao.avancaPosicaoLeitura(); !umaSecao.isOver()
					&& !umaSecao.getInstrucaoAtualLeitura().equals("GIVING")
					&& !umaSecao.getInstrucaoAtualLeitura().equals("REMAINDER")
					&& !ParagrafosProcedureDivision.acabouParagrafoAtual(umaSecao.getInstrucaoAtualLeitura()); umaSecao
							.avancaPosicaoLeitura()) {
				Atributo atributo = encontraCriaAtributo(umaSecao, dataDivision);
				divisor.add(atributo);
				if (atributo.getClasses() != null) {
					imports.add(atributo.getClasses().get(0));
				}
			}
		}

		if (!umaSecao.isOver() && umaSecao.getInstrucaoAtualLeitura().equals("GIVING")) {
			for (umaSecao.avancaPosicaoLeitura(); !umaSecao.isOver()
					&& !umaSecao.getInstrucaoAtualLeitura().equals("REMAINDER")
					&& !ParagrafosProcedureDivision.acabouParagrafoAtual(umaSecao.getInstrucaoAtualLeitura()); umaSecao
							.avancaPosicaoLeitura()) {
				Atributo atributo = encontraIdentificador(umaSecao, dataDivision);
				quociente.add(atributo);
				if (atributo.getClasses() != null) {
					imports.add(atributo.getClasses().get(0));
				}
			}
		}

		if (!umaSecao.isOver() && umaSecao.getInstrucaoAtualLeitura().equals("REMAINDER")) {
			for (umaSecao.avancaPosicaoLeitura(); !umaSecao.isOver()
					&& !umaSecao.getInstrucaoAtualLeitura().equals("REMAINDER")
					&& !ParagrafosProcedureDivision.acabouParagrafoAtual(umaSecao.getInstrucaoAtualLeitura()); umaSecao
							.avancaPosicaoLeitura()) {
				Atributo atributo = encontraIdentificador(umaSecao, dataDivision);
				resto.add(atributo);
				if (atributo.getClasses() != null) {
					imports.add(atributo.getClasses().get(0));
				}
			}
		}
	}

	@Override
	public String escreveArquivo() {
		String imprimirDividento = new String();
		String imprimirDivisor = new String();
		String imprimirQuociente = new String ();
		String imprimirResto = new String();
		for (Atributo elemento : dividendo) {
			if (elemento instanceof AtributoElementar) {
				if (elemento.getNome() == null || elemento.getNome().isEmpty()) {
					imprimirDividento += ((AtributoElementar) elemento).getValor().toString() + " + ";
				} else {
					if (elemento instanceof AtributoElementar) {
						switch (((AtributoElementar) elemento).getTipoAtributo()) {
						case CARACTERE:
							imprimirDividento += "Integer.parseInt(" + toLowerFistCase(elemento.getClassesSucessoras())
									+ elemento.getSentencaGet() + ") + ";
							break;
						case DECIMAL:
						case NUMERO:
							imprimirDividento += toLowerFistCase(elemento.getClassesSucessoras())
									+ elemento.getSentencaGet() + " + ";
							break;
						}
					}
				}
			} else if (elemento instanceof AtributoGrupo) {
				imprimirDividento += "Integer.parseInt(" + toLowerFistCase(elemento.getClassesSucessoras())
						+ elemento.getSentencaGet() + ".toTrancode()) + ";
			}
		}
		imprimirDividento = imprimirDividento.substring(0, imprimirDividento.length()-3);
		for (Atributo elemento : divisor) {
			if (elemento instanceof AtributoElementar) {
				if (elemento.getNome() == null || elemento.getNome().isEmpty()) {
					imprimirDivisor += ((AtributoElementar) elemento).getValor().toString() + " + ";
				} else {
					if (elemento instanceof AtributoElementar) {
						switch (((AtributoElementar) elemento).getTipoAtributo()) {
						case CARACTERE:
							imprimirDivisor += "Integer.parseInt(" + toLowerFistCase(elemento.getClassesSucessoras())
									+ elemento.getSentencaGet() + ") + ";
							break;
						case DECIMAL:
						case NUMERO:
							imprimirDivisor += toLowerFistCase(elemento.getClassesSucessoras())
									+ elemento.getSentencaGet() + " + ";
							break;
						}
					}
				}
			} else if (elemento instanceof AtributoGrupo) {
				imprimirDivisor += "Integer.parseInt(" + toLowerFistCase(elemento.getClassesSucessoras())
						+ elemento.getSentencaGet() + ".toTrancode()) + ";
			}
		}
		imprimirDivisor = imprimirDivisor.substring(0, imprimirDivisor.length()-3);
		for(Atributo elemento : quociente) {
			imprimirQuociente += ("\t\t" + toLowerFistCase(elemento.getClassesSucessoras())
			+ elemento.getSentencaSet(imprimirDividento +  " / " + imprimirDivisor) + ";\n");
			}

		for(Atributo elemento : resto) {
			imprimirResto += ("\t\t" + toLowerFistCase(elemento.getClassesSucessoras())
					+ elemento.getSentencaSet(imprimirDividento +  " % " + imprimirDivisor) + ";\n");
		}
		return imprimirQuociente + imprimirResto;
	}

	@Override
	public Set<String> escreveImports() {
		Set<String> imprimir = new HashSet<String>();
		for (Atributo elemento : dividendo) {
			if (elemento.getNome() != null && !elemento.getNome().isEmpty()) {
				imprimir.addAll(escreveImportsParagrago(imports));
			}
		}
		for (Atributo elemento : divisor) {
			if (elemento.getNome() != null && !elemento.getNome().isEmpty()) {
				imprimir.addAll(escreveImportsParagrago(imports));
			}
		}
		for (Atributo elemento : quociente) {
			if (elemento.getNome() != null && !elemento.getNome().isEmpty()) {
				imprimir.addAll(escreveImportsParagrago(imports));
			}
		}
		for (Atributo elemento : resto) {
			if (elemento.getNome() != null && !elemento.getNome().isEmpty()) {
				imprimir.addAll(escreveImportsParagrago(imports));
			}
		}
		return imprimir;
	}
}
