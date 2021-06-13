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
public class WorkingStorageSection extends DataDivisionCriaVariaveis {

	ArrayList<Atributo> atributos = new ArrayList<Atributo>();

	AtributoGrupo DadosPrincipais;

	Codigo codigoCompleto;

	public AtributoGrupo popula(String codigoCobol) {

		String regexNivelNome = "(?<nivel>\\d+)\\s+(?<nome>\\w+)";
		String regexPicTipoTamanho = "\\s*((PIC|PICTURE)\\s+(?<tipo>\\w+)\\s*(\\((?<tamanho>\\d+)\\))?(V(?<tipoDecimal>\\w+)\\s*(\\((?<tamanhoDecimal>\\d+)\\))?)?)?";
		String regexValue = "\\s*(VALUE\\s+(IS\\s+)?(\\'(?<valor>(\\w+\\s*)+)\\'|(?<valorNumerico>\\d+\\.?\\d+?)))?";
		String regexOccurs = "\\s*(OCCURS\\s+\\(?\\s*(?<occurs>\\w+)\\s*\\)?\\s+?TIMES)?";

		Pattern pattern = Pattern
				.compile("(?i)" + regexNivelNome + regexPicTipoTamanho + regexValue + regexOccurs + "\\.");
		Matcher matcher = pattern.matcher(codigoCobol);
		List<String> classes = new ArrayList<String>();
		if (matcher.find()) {
			do {
				atributos.add(criaItem(matcher, classes, SecoesDataDivision.WORKINGSTORAGESECTION));
			} while ((atributos.get(atributos.size() - 1) instanceof AtributoGrupo)
					? ((matcher.group("nivel") == null) ? false : true)
					: matcher.find());
			DadosPrincipais = new AtributoGrupo("DadosPrincipais", 0, atributos, null, null,
					SecoesDataDivision.WORKINGSTORAGESECTION);
		}
		return DadosPrincipais;
	}
}
