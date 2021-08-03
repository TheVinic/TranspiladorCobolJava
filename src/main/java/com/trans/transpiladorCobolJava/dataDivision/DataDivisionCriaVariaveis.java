package com.trans.transpiladorCobolJava.dataDivision;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import com.trans.transpiladorCobolJava.dataDivision.model.TipoAtributo;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.Atributo;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.AtributoElementar;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.AtributoItemGrupo;

public abstract class DataDivisionCriaVariaveis {

	private Integer contadorFiller = 1;

	private String classeMain = "DadosPrincipais";

	private Integer nivel;
	private String nomeAtributo;
	private String tipo;
	private Integer comprimento;
	private Integer comprimentoDecimal;
	private String value;
	private String occurs;

	protected String regexNivelNome = "(?<nivel>\\d+)\\s+(?<nome>[a-zA-Z1-9-]+)";
	protected String regexPicTipoTamanho = "\\s*((PIC|PICTURE)(\\sIS)?\\s+(?<tipo>\\w+)\\s*(\\((?<tamanho>\\d+)\\))?(V(?<tipoDecimal>\\w+)\\s*(\\((?<tamanhoDecimal>\\d+)\\))?)?)?";
	protected String regexComp = "\\s*(COMP([-]\\d+)?)?";
	protected String regexValue = "\\s*(VALUE\\s+(IS\\s+)?((?<valorNumerico>\\d+(\\.?\\d+)?)|((\'|\")?(?<valor>(\\w+\\s*)+)(\'|\")?)))?";
	protected String regexOccurs = "\\s*(OCCURS\\s+\\(?\\s*(?<occurs>\\w+)\\s*\\)?\\s+?TIMES)?";
	
	protected Atributo criaItem(Matcher matcher, List<String> classe, SecoesDataDivision local) {

		String tipoDecimal;
		
		nivel = Integer.parseInt(matcher.group("nivel"));
		nomeAtributo = matcher.group("nome");
		//TODO Corrigir tipo do campo e comprimento quando S9999999V99999
		tipo = matcher.group("tipo");
		comprimento = matcher.group("tamanho") != null ? Integer.parseInt(matcher.group("tamanho")) : null;
		tipoDecimal = matcher.group("tipoDecimal");
		comprimentoDecimal = matcher.group("tamanhoDecimal") != null ? Integer.parseInt(matcher.group("tamanhoDecimal"))
				: null;
		value = (matcher.group("valor") == null) ? matcher.group("valorNumerico") : matcher.group("valor");
		occurs = matcher.group("occurs");

		System.out.println("Criando novo item " + nomeAtributo);

		if (nomeAtributo.equalsIgnoreCase("FILLER")) {
			nomeAtributo += contadorFiller++;
		}

		if (tipo == null) {
			return criaItemGrupo(nivel, nomeAtributo, occurs, classe, local, matcher);
		} else {
			if (nivel.equals(1) || classe.isEmpty()) {
				classe = new ArrayList<String>();
				classe.add(classeMain);
			}

			System.out.println("Item Elementar " + nomeAtributo + " criado.");
			return new AtributoElementar(nomeAtributo, nivel, (comprimento == null) ? tipo.length() : comprimento,
					(comprimentoDecimal == null) ? (tipoDecimal == null) ? null : tipoDecimal.length() : comprimentoDecimal,
					(tipoDecimal == null) ? validaTipoAtributo(tipo) : TipoAtributo.DECIMAL, value, classe, occurs,
					local);
		}
	}

	protected AtributoItemGrupo criaItemGrupo(Integer nivel, String nomeAtributo, String occurs, List<String> classe,
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
		} while (((filhos.get(filhos.size() - 1) instanceof AtributoItemGrupo)
				? ((matcher.group("nivel") == null) ? false : true)
				: matcher.find())
				&& ((matcher.group("nivel") == null) ? false : Integer.parseInt(matcher.group("nivel")) > nivel));

		return new AtributoItemGrupo(nomeAtributo, nivel, filhos, (novaClasse == null) ? classe : novaClasse, occurs,
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
