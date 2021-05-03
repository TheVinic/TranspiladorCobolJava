package com.trans.transpiladorCobolJava.dataDivision;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.trans.transpiladorCobolJava.arquivo.Codigo;
import com.trans.transpiladorCobolJava.dataDivision.model.TipoAtributo;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.Atributo;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.AtributoElementar;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.AtributoGrupo;
import com.trans.transpiladorCobolJava.main.Divisoes;

public class WorkingStorageSection {

	ArrayList<Atributo> atributos = new ArrayList<Atributo>();

	AtributoGrupo DadosPrincipais;

	String classeMain = "DadosPrincipal";

	Integer contadorFiller = 1;

	@Autowired
	Codigo codigoCompleto;

	public AtributoGrupo popula(Codigo codigoCobol) {
		List<String> classes = new ArrayList<String>();
		while (!codigoCobol.getProximaInstrucaoLeitura().isEmpty()
				&& !SecoesDataDivision.acabouParagrafoAtual(codigoCobol.getInstrucaoAtualLeitura())
				&& !Divisoes.acabouDivisaoAtual(codigoCobol.getInstrucaoAtualLeitura())) {
			atributos.add(
					criaItem(new Codigo(codigoCobol.getInstrucaoAtualLeitura().split("\\s")), codigoCobol, classes));
		}
		DadosPrincipais = new AtributoGrupo("DadosPrincipais", 0, atributos, null, null);
		return DadosPrincipais;
	}

	private Atributo criaItem(Codigo instrucao, Codigo codigoCobol, List<String> classe) {

		Integer nivel = Integer.parseInt(instrucao.getInstrucaoAtualLeitura());

		String nomeAtributo = instrucao.getProximaInstrucaoLeitura();

		if (nomeAtributo.equalsIgnoreCase("FILLER")) {
			nomeAtributo += contadorFiller++;
		}

		if (instrucao.getProximaInstrucaoLeitura().isEmpty() || instrucao.getInstrucaoAtualLeitura().equals("OCCURS")) {
			return criaGrupo(nivel, nomeAtributo, instrucao, codigoCobol, classe);
		} else {
			return criaElemento(nivel, nomeAtributo, instrucao, classe);
		}
	}

	private AtributoGrupo criaGrupo(Integer nivel, String nomeAtributo, Codigo instrucaoAtual, Codigo codigoCobol,
			List<String> classe) {

		List<String> novaClasse = new ArrayList<String>();
		if (!nivel.equals(1)) {
			novaClasse.addAll(classe);
		}
		novaClasse.add(nomeAtributo);

		List<Atributo> filhos = new ArrayList<Atributo>();
		Codigo instrucao = new Codigo(codigoCobol.getProximaInstrucaoLeitura().split("\\s"));

		while (instrucao.getCodigoCobol().length > 1 && nivel < Integer.parseInt(instrucao.getInstrucaoLeitura(0))) {
			filhos.add(criaItem(instrucao, codigoCobol, (novaClasse == null) ? classe : novaClasse));
			instrucao = new Codigo(codigoCobol.getProximaInstrucaoLeitura().split("\\s"));
		}

		Integer occurs = null;
		if (instrucaoAtual.getInstrucaoAtualLeitura().equals("OCCURS")) {
			occurs = Integer.parseInt(instrucaoAtual.getProximaInstrucaoLeitura());
		}

		codigoCobol.setVoltaPosicaoLeitura();
		return new AtributoGrupo(nomeAtributo, nivel, filhos, (novaClasse == null) ? classe : novaClasse, occurs);
	}

	private AtributoElementar criaElemento(Integer nivel, String nomeAtributo, Codigo instrucao, List<String> classe) {

		if (nivel.equals(1)) {
			classe = new ArrayList<String>();
			classe.add(classeMain);
		}

		if (!instrucao.getInstrucaoAtualLeitura().equals("PIC")
				&& !instrucao.getInstrucaoAtualLeitura().equals("PICTURE")) {
			System.out.println("Erro ao criar elemento " + nivel + " " + nomeAtributo + " " + instrucao);
		}

		TipoAtributo tipoAtributo = validaTipoAtributo(instrucao.getProximaInstrucaoLeitura());

		Integer comprimento = comprimentoAtributo(instrucao.getInstrucaoAtualLeitura().split("V")[0]);

		Integer comprimentoDecimal = (tipoAtributo == TipoAtributo.DECIMAL)
				? comprimentoAtributo(instrucao.getInstrucaoAtualLeitura().split("V")[1])
				: null;

		String valorAtributo = null;
		if (instrucao.getProximaInstrucaoLeitura().equals("VALUE")) {
			valorAtributo = instrucao.getProximaInstrucaoLeitura();
			if (valorAtributo.equals("IS")) {
				valorAtributo = instrucao.getProximaInstrucaoLeitura();
			}
			while (valorAtributo.startsWith("\"") && !valorAtributo.endsWith("\"")) {
				valorAtributo += " ";
				valorAtributo += instrucao.getProximaInstrucaoLeitura();
			}
		} else {
			instrucao.setVoltaPosicaoLeitura();
		}

		Integer occurs = null;
		if (instrucao.getProximaInstrucaoLeitura().equals("OCCURS")) {
			occurs = Integer.parseInt(instrucao.getProximaInstrucaoLeitura());
		}

		return new AtributoElementar(nomeAtributo, nivel, comprimento, comprimentoDecimal, tipoAtributo, valorAtributo,
				classe, occurs);
	}

	private Integer comprimentoAtributo(String stringComprimentoElemento) {
		if (stringComprimentoElemento.contains("(")) {
			return comprimentoParenteses(stringComprimentoElemento);
		} else {
			return comprimentoTamanho(stringComprimentoElemento);
		}
	}

	private Integer comprimentoParenteses(String stringComprimentoElemento) {
		return Integer.parseInt(stringComprimentoElemento.split("\\(|\\)")[1]);
	}

	private Integer comprimentoTamanho(String stringComprimentoElemento) {
		return stringComprimentoElemento.replace("S", "").length();
	}

	public TipoAtributo validaTipoAtributo(String palavra) { // Valida o tipo do atributo
		TipoAtributo tipoAtributo;
		if (palavra.startsWith("X") || palavra.startsWith("A")) {
			tipoAtributo = TipoAtributo.CARACTERE;
		} else if (palavra.contains("V")) {
			tipoAtributo = TipoAtributo.DECIMAL;
		} else {
			tipoAtributo = TipoAtributo.NUMERO;
		}
		return tipoAtributo;
	}
}
