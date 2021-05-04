package com.trans.transpiladorCobolJava.procedureDivision.Paragrafos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.trans.transpiladorCobolJava.arquivo.Codigo;
import com.trans.transpiladorCobolJava.dataDivision.DataDivision;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.Atributo;
import com.trans.transpiladorCobolJava.procedureDivision.ParagrafosProcedureDivision;

public class DivideParagrafo extends Paragrafo implements ParagrafoImpl {

	ArrayList<Atributo> dividendo = new ArrayList<Atributo>();

	ArrayList<Atributo> divisor = new ArrayList<Atributo>();

	ArrayList<Atributo> quociente = new ArrayList<Atributo>();

	ArrayList<Atributo> resto = new ArrayList<Atributo>();

	Set<String> imports = new HashSet<String>();

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
				Atributo atributo = encontraIdentificador(umaSecao, dataDivision);
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> escreveImports() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> getImports() {
		// TODO Auto-generated method stub
		return null;
	}

}
