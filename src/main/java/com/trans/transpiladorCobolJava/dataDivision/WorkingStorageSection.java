package com.trans.transpiladorCobolJava.dataDivision;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.trans.transpiladorCobolJava.main.Codigo;
import com.trans.transpiladorCobolJava.main.Divisoes;

public class WorkingStorageSection {

	Set<Atributo> atributos = new HashSet<Atributo>();

	@Autowired
	Codigo codigoCompleto;

	public Set<Atributo> popula(Codigo codigoCobol) {
		while (!codigoCobol.getProximaInstrucaoLeitura().isEmpty()
				&& SecoesDataDivision
						.encontraParagrafo(codigoCobol.getInstrucaoAtualLeitura()) == SecoesDataDivision.OUTRO
				&& Divisoes.encontraDivisao(codigoCobol.getInstrucaoAtualLeitura()) == Divisoes.OUTRO) {
			atributos.add(criaItem(new Codigo(codigoCobol.getInstrucaoAtualLeitura().split("\\s")), codigoCobol));
		}
		//System.out.println(atributos);
		return atributos;
	}

	private Atributo criaItem(Codigo instrucao, Codigo codigoCobol) {

		Integer nivel = Integer.parseInt(instrucao.getProximaInstrucaoLeitura());

		String nomeAtributo = instrucao.getProximaInstrucaoLeitura();

		if (!instrucao.getProximaInstrucaoLeitura().isEmpty()) {
			return criaElemento(nivel, nomeAtributo, instrucao);
		} else {
			return criaGrupo(nivel, nomeAtributo, codigoCobol);
		}
	}

	private AtributoGrupo criaGrupo(Integer nivel, String nomeAtributo, Codigo codigoCobol) {

		Set<Atributo> filhos = new HashSet<Atributo>();
		Codigo instrucao = new Codigo(codigoCobol.getProximaInstrucaoLeitura().split("\\s"));

		while (instrucao.getCodigoCobol().length > 1 && nivel < Integer.parseInt(instrucao.getInstrucaoLeitura(1))) {
			filhos.add(criaItem(instrucao, codigoCobol));
			instrucao = new Codigo(codigoCobol.getProximaInstrucaoLeitura().split("\\s"));
		}

		codigoCobol.setVoltaPosicaoLeitura();
		return new AtributoGrupo(nomeAtributo, nivel, filhos);
	}

	private AtributoElementar criaElemento(Integer nivel, String nomeAtributo, Codigo instrucao) {

		if (!instrucao.getInstrucaoAtualLeitura().equals("PIC")
				&& !instrucao.getInstrucaoAtualLeitura().equals("PICTURE")) {
			System.out.println("Erro ao criar elemento");
		}

		TipoAtributo tipoAtributo = validaTipoAtributo(instrucao.getProximaInstrucaoLeitura());

		Integer comprimento = comprimentoAtributo(instrucao.getInstrucaoAtualLeitura().split("V")[0]);

		Integer comprimentoDecimal = (tipoAtributo == TipoAtributo.DECIMAL)
				? comprimentoAtributo(instrucao.getInstrucaoAtualLeitura().split("V")[1])
				: null;

		String valorAtributo = null;
		if(instrucao.getProximaInstrucaoLeitura().equals("VALUE")) {
			valorAtributo = instrucao.getProximaInstrucaoLeitura();
			if(valorAtributo.equals("IS")){
				valorAtributo = instrucao.getProximaInstrucaoLeitura();
			}
			while(valorAtributo.startsWith("\"") && !valorAtributo.endsWith("\"")) {
				valorAtributo += " ";
				valorAtributo += instrucao.getProximaInstrucaoLeitura();
			}
		}
		
		return new AtributoElementar(nomeAtributo, nivel, comprimento, comprimentoDecimal, tipoAtributo, valorAtributo);
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
