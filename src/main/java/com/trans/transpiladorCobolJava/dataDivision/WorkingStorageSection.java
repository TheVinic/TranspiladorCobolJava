package com.trans.transpiladorCobolJava.dataDivision;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.trans.transpiladorCobolJava.dataDivision.model.atributo.Atributo;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.AtributoItemGrupo;

@Component
public class WorkingStorageSection extends DataDivisionCriaVariaveis {

	private ArrayList<Atributo> atributos = new ArrayList<Atributo>();

	private AtributoItemGrupo DadosPrincipais;

	public AtributoItemGrupo analisesWorkingStorageSection(String codigoCobol) {

		Pattern pattern = Pattern
				.compile("(?i)" + regexNivelNome + regexPicTipoTamanho + regexComp + regexValue + regexOccurs + "\\.");
		Matcher matcher = pattern.matcher(codigoCobol);
		List<String> classes = new ArrayList<String>();
		if (matcher.find()) {
			do {
				matcher.group();
				atributos.add(criaItem(matcher, classes, SecoesDataDivision.WORKINGSTORAGESECTION));
			} while ((atributos.get(atributos.size() - 1) instanceof AtributoItemGrupo)
					? (matcher.hitEnd() || (matcher.group("nivel") == null) ? false : true)
					: matcher.find());
			DadosPrincipais = new AtributoItemGrupo("DadosPrincipais", 0, atributos, null, null,
					SecoesDataDivision.WORKINGSTORAGESECTION);
		}
		return DadosPrincipais;
	}
}
