package com.trans.transpiladorCobolJava.procedureDivision.Paragrafos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.trans.transpiladorCobolJava.arquivo.Codigo;
import com.trans.transpiladorCobolJava.dataDivision.DataDivision;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.Atributo;
import com.trans.transpiladorCobolJava.procedureDivision.ParagrafosProcedureDivision;

public class PerformParagrafo extends Paragrafo {

	String nomeSecao;
	String nomeSecaoFinaliza;

	ArrayList<Paragrafo> instrucoes;

	Atributo times;

	String test;

	Atributo varying;
	Atributo from;
	Atributo by;
	ArrayList<Atributo> until;

	public PerformParagrafo(Codigo codigo, DataDivision dataDivision, ArrayList<ProcedureDivisionSection> secoes) {

		Atributo atributo = (codigo.getInstrucaoAtualLeitura().matches("[0-9]+"))
				? encontraCriaAtributo(codigo, dataDivision)
				: encontraIdentificador(codigo, dataDivision);

		if (ParagrafosProcedureDivision
				.encontraParagrafo(codigo.getInstrucaoAtualLeitura()) != ParagrafosProcedureDivision.OUTRO) {
			// Basic PERFORM para imperative-statement
			instrucoes = new ProcedureDivisionPerforming().leitura(codigo, dataDivision, secoes);
		} else if (atributo == null && (!codigo.getInstrucaoAtualLeitura().equals("WITH")
				&& !codigo.getInstrucaoAtualLeitura().equals("TEST")
				&& !codigo.getInstrucaoAtualLeitura().equals("UNTIL")
				&& !codigo.getInstrucaoAtualLeitura().equals("VARYING"))) {
			nomeSecao = codigo.getInstrucaoAtualLeitura().replace(".", "");
			if (ParagrafosProcedureDivision
					.encontraParagrafo(codigo.getProximaInstrucaoLeitura()) == ParagrafosProcedureDivision.OUTRO) {
				if (codigo.getInstrucaoAtualLeitura().equals("THROUGHT")
						|| codigo.getInstrucaoAtualLeitura().equals("THRU")) {
					nomeSecaoFinaliza = codigo.getProximaInstrucaoLeitura();
					codigo.avancaPosicaoLeitura();
				}
			}
			percorrePerform(codigo, dataDivision);
		} else if (atributo == null) {
			// PERFORM para UNTIL ou VARYING
			percorrePerform(codigo, dataDivision);
			instrucoes = new ProcedureDivisionPerforming().leitura(codigo, dataDivision, secoes);
		} else if (atributo != null) {
			// PERFORM with TIME
			String validar = codigo.getProximaInstrucaoLeitura();
			if (validar.equals("TIMES")) {
				times = atributo;
			}
			if (times.getClasses() != null) {
				imports.add(times.getImport());
			}
			codigo.avancaPosicaoLeitura();
			instrucoes = new ProcedureDivisionPerforming().leitura(codigo, dataDivision, secoes);
		}
	}

	private void percorrePerform(Codigo codigo, DataDivision dataDivision) {
		Atributo atributo;
		// PERFORM para seção
		if (codigo.getInstrucaoAtualLeitura().equals("WITH") || codigo.getInstrucaoAtualLeitura().equals("TEST")) {
			if (codigo.getInstrucaoAtualLeitura().equals("WITH")) {
				codigo.avancaPosicaoLeitura();
			}
			test = codigo.getProximaInstrucaoLeitura();
			codigo.avancaPosicaoLeitura();
		}
		if (codigo.getInstrucaoAtualLeitura().matches("[0-9]+")) {
			times = encontraCriaAtributo(codigo, dataDivision);
			if (times.getClasses() != null) {
				imports.add(times.getImport());
			}
			if (!codigo.getProximaInstrucaoLeitura().equals("TIMES")) {
				System.out.println("Erro no times");
			}
		} else if (codigo.getInstrucaoAtualLeitura().equals("VARYING")) {
			codigo.avancaPosicaoLeitura();
			varying = encontraIdentificador(codigo, dataDivision);
			if (varying.getClasses() != null) {
				imports.add(varying.getImport());
			}
			if (!codigo.getProximaInstrucaoLeitura().equals("FROM")) {
				System.out.println("Erro no Varying no FROM");
			}
			codigo.avancaPosicaoLeitura();
			from = encontraCriaAtributo(codigo, dataDivision);
			if (from.getClasses() != null) {
				imports.add(from.getImport());
			}
			if (!codigo.getProximaInstrucaoLeitura().equals("BY")) {
				System.out.println("Erro no Varying no BY");
			}
			codigo.avancaPosicaoLeitura();
			by = encontraCriaAtributo(codigo, dataDivision);
			if (by.getClasses() != null) {
				imports.add(by.getImport());
			}
			codigo.avancaPosicaoLeitura();

		}
		if (codigo.getInstrucaoAtualLeitura().equals("UNTIL")) {
			until = new ArrayList<Atributo>();
			for (codigo.avancaPosicaoLeitura(); !ParagrafosProcedureDivision
					.acabouParagrafoAtual(codigo.getInstrucaoAtualLeitura()); codigo.avancaPosicaoLeitura()) {
				if (false) {
					// TODO colocar validação se não houver espaço
				} else {
					atributo = encontraCriaAtributo(codigo, dataDivision);
					until.add(atributo);
					if (atributo.getClasses() != null) {
						imports.add(atributo.getImport());
					}
				}
			}
		}
	}

	@Override
	public String escreveArquivo(Integer nivel) {
		String imprimirCondicao = new String();
		if (nomeSecao == null) {
			if (times != null) {
				imprimirCondicao += fazTabulacao(nivel) + "for(int i=0; i<" + times.getStringEscritaPorTipo()
						+ "; i++){\n";
			} else if (test == null || test.equals("BEFORE")) {
				imprimirCondicao += fazTabulacao(nivel) + "while (";
				for (Atributo elemento : until) {
					imprimirCondicao += elemento.getStringEscritaPorTipo() + " ";
				}
				imprimirCondicao += ") {\n";
			} else {
				imprimirCondicao += "do{\n";
			}
			for (Paragrafo paragrafo : instrucoes) {
				imprimirCondicao += paragrafo.escreveArquivo(nivel + 1);
			}
			if (test != null && test.equals("AFTER")) {
				imprimirCondicao += "} while (";
				for (Atributo elemento : until) {
					imprimirCondicao += elemento.getStringEscritaPorTipo() + " ";
				}
				imprimirCondicao += ");\n";
			} else {
				imprimirCondicao += fazTabulacao(nivel) + "}\n";
			}
		} else {
			imprimirCondicao += fazTabulacao(nivel) + nomeSecao.toLowerCase() + "." + nomeSecao.toLowerCase() + "(";

		}
		return imprimirCondicao;
	}

	@Override
	public Set<String> escreveImports() {
		Set<String> importsDosParagrafos = new HashSet<String>();

		importsDosParagrafos = escreveImportsParagrago(imports);

		if (instrucoes != null) {
			for (Paragrafo elemento : instrucoes) {
				if (!(elemento instanceof PerformParagrafo)) {
					importsDosParagrafos.addAll(elemento.escreveImports());
				}
			}
		}
		if (nomeSecao != null) {
			importsDosParagrafos.add("import com.trans.transpiladorCobolJava." + nomeSecao + ";");
		}

		return importsDosParagrafos;
	}

	@Override
	public Set<String> getImports() {
		Set<String> importsDosParagrafos = new HashSet<String>();

		importsDosParagrafos = imports;

		if (instrucoes != null) {
			for (Paragrafo elemento : instrucoes) {
				if (!(elemento instanceof PerformParagrafo)) {
					importsDosParagrafos.addAll(elemento.getImports());
				}
			}
		}

		if (nomeSecao != null) {
			importsDosParagrafos.add(nomeSecao);
		}
		
		return importsDosParagrafos;
	}

	public String getNomeSecao() {
		return nomeSecao;
	}

}
