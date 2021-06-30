package com.trans.transpiladorCobolJava.dataDivision;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.trans.transpiladorCobolJava.arquivo.Codigo;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.Atributo;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.AtributoGrupo;

@Component
public class LinkageSection extends DataDivisionCriaVariaveis {

	ArrayList<Atributo> atributos = new ArrayList<Atributo>();

	AtributoGrupo DadosPrincipais;

	Codigo codigoCompleto;

	public AtributoGrupo popula(String codigoCobol) {

		Pattern pattern = Pattern
				.compile("(?i)" + regexNivelNome + regexPicTipoTamanho + regexValue + regexOccurs + "\\.");
		Matcher matcher = pattern.matcher(codigoCobol);
		matcher.find();
		List<String> classes = new ArrayList<String>();
		if (matcher.find()) {
			do {
				atributos.add(criaItem(matcher, classes, SecoesDataDivision.WORKINGSTORAGESECTION));
			} while ((atributos.get(atributos.size() - 1) instanceof AtributoGrupo)
					? ((matcher.group("nivel") == null) ? false : true)
					: matcher.find());
			DadosPrincipais = new AtributoGrupo("DadosPrincipaisDTO", 0, atributos, null, null,
					SecoesDataDivision.LINKAGESECTION);
		}
		return DadosPrincipais;
	}
}
