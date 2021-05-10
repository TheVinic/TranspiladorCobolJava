package com.trans.transpiladorCobolJava.procedureDivision.Paragrafos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.trans.transpiladorCobolJava.arquivo.Codigo;
import com.trans.transpiladorCobolJava.dataDivision.DataDivision;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.Atributo;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.AtributoElementar;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.AtributoGrupo;

public class MoveParagrafo extends Paragrafo implements ParagrafoImpl {

	Atributo de;

	ArrayList<Atributo> para = new ArrayList<Atributo>();

	public MoveParagrafo(Codigo umaSecao, DataDivision dataDivision) {

		Atributo atributo = encontraCriaAtributo(umaSecao, dataDivision);
		de = atributo;
		if (atributo.getClasses() != null) {
			imports.add(atributo.getClasses().get(0));
		}
		umaSecao.avancaPosicaoLeitura();
		for (umaSecao.avancaPosicaoLeitura(); !umaSecao.isOver(); umaSecao.avancaPosicaoLeitura()) {
			atributo = encontraIdentificador(umaSecao, dataDivision);
			para.add(atributo);
			if (atributo.getClasses() != null) {
				imports.add(atributo.getClasses().get(0));
			}
		}
	}

	@Override
	public String escreveArquivo() {
		String imprimirDe = new String();
		String imprimirMove = new String();

		if (de instanceof AtributoElementar) {
			if (de.getNome() == null || de.getNome().isEmpty()) {
				imprimirDe += ((AtributoElementar) de).getValor().toString();
			} else {
				if (de instanceof AtributoElementar) {
					//TODO validação da movimentação de um tipo para outro
					switch (((AtributoElementar) de).getTipoAtributo()) {
					case CARACTERE:
						imprimirDe += toLowerFistCase(de.getClassesSucessoras()) + de.getSentencaGet();
						break;
					case DECIMAL:
					case NUMERO:
						imprimirDe += toLowerFistCase(de.getClassesSucessoras()) + de.getSentencaGet();
						break;
					}
				}
			}
		} else if (de instanceof AtributoGrupo) {
			imprimirDe += toLowerFistCase(de.getClassesSucessoras()) + de.getSentencaGet() + ".toTrancode()";
		}

		for (Atributo elemento : para) {
			imprimirMove += ("\t\t" + toLowerFistCase(elemento.getClassesSucessoras())
					+ elemento.getSentencaSet(imprimirDe) + ";\n");
		}
		return imprimirMove;
	}

	@Override
	public Set<String> escreveImports() {
		Set<String> imprimir = new HashSet<String>();
		if (de.getNome() != null && !de.getNome().isEmpty()) {
			imprimir.addAll(escreveImportsParagrago(imports));
		}
		for (Atributo elemento : para) {
			if (elemento.getNome() != null && !elemento.getNome().isEmpty()) {
				imprimir.addAll(escreveImportsParagrago(imports));
			}
		}
		return imprimir;
	}

}
