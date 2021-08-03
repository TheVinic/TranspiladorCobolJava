package com.trans.transpiladorCobolJava.procedureDivision.Paragrafos;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.trans.transpiladorCobolJava.dataDivision.DataDivision;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.Atributo;

public class EvaluateModel extends Paragrafo {

	ArrayList<ArrayList<Atributo>> evaluate = new ArrayList<ArrayList<Atributo>>();

	ArrayList<WhenEvaluateModel> when = new ArrayList<WhenEvaluateModel>();

	
	//TODO validar utilização do also, de erro de string index outr of range
	@Override
	public String escreveArquivo(Integer nivel) {
		String imprimir = new String();
		imprimir += fazTabulacao(nivel);
		for (WhenEvaluateModel passoWhen : when) {
			if (passoWhen.getWhen().get(0).get(0).getStringEscritaPorTipo() == "OTHER") {
				imprimir += "{\n";
			} else {
				imprimir += "if (";
				for (int i = 0; i < evaluate.size(); i++) {
					for (int j = 0; j < evaluate.get(i).size(); j++) {
						imprimir += evaluate.get(i).get(j).getStringEscritaPorTipo() + " ";
					}
					imprimir += "== ";
					for (int j = 0; j < passoWhen.getWhen().get(i).size(); j++) {
						imprimir += passoWhen.getWhen().get(i).get(j).getStringEscritaPorTipo() + " ";
					}
					imprimir += "&& ";
				}
				imprimir = imprimir.substring(0, imprimir.length() - 3) + "){\n";
			}
			for (Paragrafo paragrafo : passoWhen.getInstrucoes()) {
				imprimir += paragrafo.escreveArquivo(nivel + 1);
			}
			imprimir += "\n" + fazTabulacao(nivel) + "} else ";
		}
		imprimir = imprimir.substring(0, imprimir.length() - 6) + "\n";
		return imprimir;
	}

	public ArrayList<ArrayList<Atributo>> getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(ArrayList<ArrayList<Atributo>> evaluate) {
		this.evaluate = evaluate;
	}

	public void addEvaluate(DataDivision dataDivision, String regexAlso, String identificador1,
			String identificadorAlso) {
		// Primeiro Evaluate
		matcherOf = patternOf.matcher(identificador1);
		ArrayList<Atributo> auxiliar = new ArrayList<Atributo>();
		while (matcherOf.find()) {
			Atributo atributo = validaAtributo(dataDivision);
			auxiliar.add(atributo);
			if (atributo.getClasses() != null) {
				imports.add(atributo.getImport());
			}
		}
		this.evaluate.add(auxiliar);

		// Evaluate do Also
		if (identificadorAlso != null && !identificadorAlso.isEmpty()) {
			Pattern patternAlso = Pattern.compile(regexAlso);
			Matcher matcherAlso = patternAlso.matcher(identificadorAlso);
			while (matcherAlso.find()) {
				auxiliar = new ArrayList<Atributo>();
				matcherOf = patternOf.matcher(matcherAlso.group("identifierAlso"));
				while (matcherOf.find()) {
					Atributo atributo = validaAtributo(dataDivision);
					auxiliar.add(atributo);
					if (atributo.getClasses() != null) {
						imports.add(atributo.getImport());
					}
				}
				this.evaluate.add(auxiliar);
			}
		}
	}

	public ArrayList<WhenEvaluateModel> getWhen() {
		return when;
	}

	public void setWhen(ArrayList<WhenEvaluateModel> when) {
		this.when = when;
	}

	public ArrayList<ArrayList<Atributo>> criaWhen(DataDivision dataDivision, String regexAlso, String instrucaoWhen,
			String instrucaoAlso) {
		// Primeiro Evaluate
		matcherOf = patternOf.matcher(instrucaoWhen);
		ArrayList<ArrayList<Atributo>> listaWhens = new ArrayList<ArrayList<Atributo>>();
		ArrayList<Atributo> auxiliar = new ArrayList<Atributo>();
		while (matcherOf.find()) {
			Atributo atributo = validaAtributo(dataDivision);
			auxiliar.add(atributo);
			if (atributo.getClasses() != null) {
				imports.add(atributo.getImport());
			}
		}
		listaWhens.add(auxiliar);
		auxiliar = new ArrayList<Atributo>();
		// Evaluate do Also
		if (instrucaoAlso != null && !instrucaoAlso.isEmpty()) {
			Pattern patternAlso = Pattern.compile(regexAlso);
			Matcher matcherAlso = patternAlso.matcher(instrucaoAlso);
			while (matcherAlso.find()) {
				matcherOf = patternOf.matcher(matcherAlso.group("identifierAlso"));
				while (matcherOf.find()) {
					Atributo atributo = validaAtributo(dataDivision);
					auxiliar.add(atributo);
					if (atributo.getClasses() != null) {
						imports.add(atributo.getImport());
					}
				}
			}
			listaWhens.add(auxiliar);
		}
		return listaWhens;
	}

	public void addWhen(WhenEvaluateModel when) {
		this.when.add(when);
	}

}
