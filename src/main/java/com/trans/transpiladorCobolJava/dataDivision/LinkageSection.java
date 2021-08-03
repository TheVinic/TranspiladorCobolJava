package com.trans.transpiladorCobolJava.dataDivision;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.trans.transpiladorCobolJava.dataDivision.model.atributo.Atributo;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.AtributoItemGrupo;

@Component
public class LinkageSection extends DataDivisionCriaVariaveis {

	private ArrayList<Atributo> atributos = new ArrayList<Atributo>();

	private AtributoItemGrupo DadosPrincipais;

	public AtributoItemGrupo analisesLinkageSection(String codigoCobol) {

		Pattern pattern = Pattern
				.compile("(?i)" + regexNivelNome + regexPicTipoTamanho + regexComp + regexValue + regexOccurs + "\\.");
		Matcher matcher = pattern.matcher(codigoCobol);
		List<String> classes = new ArrayList<String>();
		// TODO correção do local após a criação do item quando vem da linkage section,
		// ao tentar consultar esta mostrando Dadosprincipais
		if (matcher.find()) {
			do {
				atributos.add(criaItem(matcher, classes, SecoesDataDivision.LINKAGESECTION));
			} while ((atributos.get(atributos.size() - 1) instanceof AtributoItemGrupo)
					? ((matcher.group("nivel") == null) ? false : true)
					: matcher.find());
			DadosPrincipais = new AtributoItemGrupo("DadosPrincipaisDTO", 0, atributos, null, null,
					SecoesDataDivision.LINKAGESECTION);
		}
		return DadosPrincipais;
	}
}
