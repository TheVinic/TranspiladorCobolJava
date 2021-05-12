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

public class MultiplyParagrafo extends Paragrafo {

	Atributo multiplica;

	ArrayList<Atributo> por = new ArrayList<Atributo>();

	ArrayList<Atributo> resultado = new ArrayList<Atributo>();

	public MultiplyParagrafo(Codigo umaSecao, DataDivision dataDivision) {
		multiplica = encontraCriaAtributo(umaSecao, dataDivision);
		if (multiplica.getClasses() != null) {
			imports.add(multiplica.getClasses().get(0));
		}
		umaSecao.avancaPosicaoLeitura();
		Atributo atributo = null;
		for (umaSecao.avancaPosicaoLeitura(); !umaSecao.isOver()
				&& !umaSecao.getInstrucaoAtualLeitura().equals("GIVING")
				&& !ParagrafosProcedureDivision.acabouParagrafoAtual(umaSecao.getInstrucaoAtualLeitura()); umaSecao
						.avancaPosicaoLeitura()) {
			atributo = encontraCriaAtributo(umaSecao, dataDivision);
			por.add(atributo);
			if (atributo.getClasses() != null) {
				imports.add(atributo.getClasses().get(0));
			}
		}

		if (!umaSecao.isOver() && umaSecao.getInstrucaoAtualLeitura().equals("GIVING")) {
			for (umaSecao.avancaPosicaoLeitura(); !umaSecao.isOver()
					&& !ParagrafosProcedureDivision.acabouParagrafoAtual(umaSecao.getInstrucaoAtualLeitura()); umaSecao
							.avancaPosicaoLeitura()) {
				atributo = encontraIdentificador(umaSecao, dataDivision);
				resultado.add(atributo);
				if (atributo.getClasses() != null) {
					imports.add(atributo.getClasses().get(0));
				}
			}
		}
	}

	@Override
	public String escreveArquivo() {
		String imprimirMultiplicar = new String();
		String imprimirPor;
		String imprimirResultado = new String();

		if (multiplica instanceof AtributoElementar) {
			if (multiplica.getNome() == null || multiplica.getNome().isEmpty()) {
				imprimirMultiplicar += ((AtributoElementar) multiplica).getValor().toString();
			} else {
				if (multiplica instanceof AtributoElementar) {
					switch (((AtributoElementar) multiplica).getTipoAtributo()) {
					case CARACTERE:
						imprimirMultiplicar += "Integer.parseInt(" + toLowerFistCase(multiplica.getClassesSucessoras())
								+ multiplica.getSentencaGet() + ")";
						break;
					case DECIMAL:
					case NUMERO:
						imprimirMultiplicar += toLowerFistCase(multiplica.getClassesSucessoras())
								+ multiplica.getSentencaGet();
						break;
					}
				}
			}
		} else if (multiplica instanceof AtributoGrupo) {
			imprimirMultiplicar += "Integer.parseInt(" + toLowerFistCase(multiplica.getClassesSucessoras())
					+ multiplica.getSentencaGet() + ".toTrancode())";
		}

		if (resultado.isEmpty()) {
			for (Atributo elemento : por) {
				imprimirPor = new String();
				if (elemento instanceof AtributoElementar) {
					if (elemento.getNome() == null || elemento.getNome().isEmpty()) {
						imprimirPor += ((AtributoElementar) elemento).getValor().toString();
					} else {
						if (elemento instanceof AtributoElementar) {
							switch (((AtributoElementar) elemento).getTipoAtributo()) {
							case CARACTERE:
								imprimirPor += "Integer.parseInt(" + toLowerFistCase(elemento.getClassesSucessoras())
										+ elemento.getSentencaGet() + ")";
								break;
							case DECIMAL:
							case NUMERO:
								imprimirPor += toLowerFistCase(elemento.getClassesSucessoras())
										+ elemento.getSentencaGet();
								break;
							}
						}
					}
				} else if (elemento instanceof AtributoGrupo) {
					imprimirPor += "Integer.parseInt(" + toLowerFistCase(elemento.getClassesSucessoras())
							+ elemento.getSentencaGet() + ".toTrancode())";
				}
				imprimirResultado += ("\t\t" + toLowerFistCase(elemento.getClassesSucessoras())
						+ elemento.getSentencaSet(imprimirMultiplicar + " * " + imprimirPor) + ";\n");

			}
		} else {
			imprimirPor = new String();
			if (por.get(0) instanceof AtributoElementar) {
				if (por.get(0).getNome() == null || por.get(0).getNome().isEmpty()) {
					imprimirPor += ((AtributoElementar) por.get(0)).getValor().toString();
				} else {
					if (por.get(0) instanceof AtributoElementar) {
						switch (((AtributoElementar) por.get(0)).getTipoAtributo()) {
						case CARACTERE:
							imprimirPor += "Integer.parseInt(" + toLowerFistCase(por.get(0).getClassesSucessoras())
									+ por.get(0).getSentencaGet() + ")";
							break;
						case DECIMAL:
						case NUMERO:
							imprimirPor += toLowerFistCase(por.get(0).getClassesSucessoras())
									+ por.get(0).getSentencaGet();
							break;
						}
					}
				}
			} else if (por.get(0) instanceof AtributoGrupo) {
				imprimirPor += "Integer.parseInt(" + toLowerFistCase(por.get(0).getClassesSucessoras())
						+ por.get(0).getSentencaGet() + ".toTrancode())";
			}
			for (Atributo elemento : resultado) {
				imprimirResultado += ("\t\t" + toLowerFistCase(elemento.getClassesSucessoras())
						+ elemento.getSentencaSet(imprimirMultiplicar + " * " + imprimirPor) + ";\n");

			}
		}

		return imprimirResultado;
	}

	@Override
	public Set<String> escreveImports() {
		Set<String> imprimir = new HashSet<String>();

		if (multiplica.getNome() != null && !multiplica.getNome().isEmpty()) {
			imprimir.addAll(escreveImportsParagrago(imports));
		}

		for (Atributo elemento : por) {
			if (elemento.getNome() != null && !elemento.getNome().isEmpty()) {
				imprimir.addAll(escreveImportsParagrago(imports));
			}
		}

		for (Atributo elemento : resultado) {
			if (elemento.getNome() != null && !elemento.getNome().isEmpty()) {
				imprimir.addAll(escreveImportsParagrago(imports));
			}
		}

		return imprimir;
	}

}
