package com.trans.transpiladorCobolJava.dataDivision;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import com.trans.transpiladorCobolJava.dataDivision.model.TipoAtributo;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.Atributo;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.AtributoElementar;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.AtributoGrupo;

public abstract class DataDivisionCriaVariaveis {

	private Integer contadorFiller = 1;

	String classeMain = "DadosPrincipal";

	protected Atributo criaItem(Matcher matcher, List<String> classe, SecoesDataDivision local) {

		Integer nivel = null;
		String nomeAtributo = null;
		String tipo = null;
		Integer tamanho = null;
		String tipoDecimal = null;
		Integer tamanhoDecimal = null;
		String valor = null;
		String occurs = null;

		nivel = Integer.parseInt(matcher.group("nivel"));
		nomeAtributo = matcher.group("nome");
		tipo = matcher.group("tipo");
		tamanho = matcher.group("tamanho") != null ? Integer.parseInt(matcher.group("tamanho")) : null;
		tipoDecimal = matcher.group("tipoDecimal");
		tamanhoDecimal = matcher.group("tamanhoDecimal") != null ? Integer.parseInt(matcher.group("tamanhoDecimal"))
				: null;
		valor = (matcher.group("valor") == null) ? matcher.group("valorNumerico") : matcher.group("valor");
		occurs = matcher.group("occurs");

		System.out.println("Criando novo item " + nomeAtributo);

		if (nomeAtributo.equalsIgnoreCase("FILLER")) {
			nomeAtributo += contadorFiller++;
		}

		if (tipo == null) {
			return criaGrupo(nivel, nomeAtributo, occurs, classe, local, matcher);
		} else {
			if (nivel.equals(1) || classe.isEmpty()) {
				classe = new ArrayList<String>();
				classe.add(classeMain);
			}

			System.out.println("Item Elementar " + nomeAtributo + " criado.");
			return new AtributoElementar(nomeAtributo, nivel, (tamanho == null) ? tipo.length() : tamanho,
					(tamanhoDecimal == null) ? (tipoDecimal == null) ? null : tipoDecimal.length() : tamanhoDecimal,
					(tipoDecimal == null) ? validaTipoAtributo(tipo) : TipoAtributo.DECIMAL, valor, classe, occurs,
					local);
		}
	}

	protected AtributoGrupo criaGrupo(Integer nivel, String nomeAtributo, String occurs, List<String> classe,
			SecoesDataDivision local, Matcher matcher) {

		List<String> novaClasse = new ArrayList<String>();
		if (!nivel.equals(1)) {
			novaClasse.addAll(classe);
		}
		novaClasse.add(nomeAtributo);

		List<Atributo> filhos = new ArrayList<Atributo>();

		System.out.println("Item de Grupo " + nomeAtributo + " criado.");

		matcher.find();
		do {
			filhos.add(criaItem(matcher, (novaClasse == null) ? classe : novaClasse, local));
		} while (((filhos.get(filhos.size() - 1) instanceof AtributoGrupo) ? ((matcher.group("nivel") == null) ? false : true) : matcher.find())
				&& ((matcher.group("nivel") == null) ? false : Integer.parseInt(matcher.group("nivel")) > nivel));

		return new AtributoGrupo(nomeAtributo, nivel, filhos, (novaClasse == null) ? classe : novaClasse, occurs,
				local);
	}

	private TipoAtributo validaTipoAtributo(String palavra) { // Valida o tipo do atributo
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
