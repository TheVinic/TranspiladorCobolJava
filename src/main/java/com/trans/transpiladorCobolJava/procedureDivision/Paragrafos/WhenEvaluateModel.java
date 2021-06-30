package com.trans.transpiladorCobolJava.procedureDivision.Paragrafos;

import java.util.ArrayList;

import com.trans.transpiladorCobolJava.dataDivision.model.atributo.Atributo;

public class WhenEvaluateModel  {

	ArrayList<ArrayList<Atributo>> when = new ArrayList<ArrayList<Atributo>>();

	ArrayList<Paragrafo> instrucoes = new ArrayList<Paragrafo>();

	public ArrayList<ArrayList<Atributo>> getWhen() {
		return when;
	}

	public void setWhen(ArrayList<ArrayList<Atributo>> when) {
		this.when = when;
	}

	public ArrayList<Paragrafo> getInstrucoes() {
		return instrucoes;
	}

	public void setInstrucoes(ArrayList<Paragrafo> instrucoes) {
		this.instrucoes = instrucoes;
	}

	public void addWhen(ArrayList<Atributo> auxiliar) {
		this.when.add(auxiliar);
	}

}
