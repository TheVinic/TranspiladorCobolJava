package com.trans.transpiladorCobolJava.procedureDivision.Paragrafos;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.trans.transpiladorCobolJava.dataDivision.DataDivision;
import com.trans.transpiladorCobolJava.dataDivision.model.TipoAtributo;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.Atributo;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.AtributoElementar;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.AtributoGrupo;

public class DisplayParagrafo extends Paragrafo implements ParagrafoImpl {

	ArrayList<Atributo> texto = new ArrayList<Atributo>();

	public DisplayParagrafo(String instrucao, DataDivision dataDivision) {
		instrucao = instrucao.replaceFirst("(?i)DISPLAY", "");
		Pattern pattern = Pattern
				.compile("(?i)((\"(?<string>[a-zA-Z0-9+-/=()*: ]+)\"\\s*)|(?<valor>\\w+\\s*))");
		Matcher matcher = pattern.matcher(instrucao);

		while (matcher.find()) {
			matcher.group();
			if (matcher.group("string") != null) {
				texto.add(new AtributoElementar(new String(), null, matcher.group("string").length(), null,
						TipoAtributo.CARACTERE, matcher.group("string"), null, null, null));
			} else {
				matcherOf = patternOf.matcher(matcher.group("valor"));
				while (matcherOf.find()) {
					Atributo atributo = validaAtributo(dataDivision);
					texto.add(atributo);
					if (atributo.getClasses() != null) {
						imports.add(atributo.getImport());
					}
				}
			}
		}
	}

	@Override
	public String escreveArquivo(Integer nivel) {
		String imprimir = new String();
		for (Atributo elemento : texto) {
			if (elemento.getNome() == null || elemento.getNome().isEmpty()) {
				imprimir += "\"" + ((AtributoElementar) elemento).getValor() + "\"" + " + ";
			} else {
				imprimir += toLowerFistCase(elemento.getClassesSucessoras())
						+ ((elemento instanceof AtributoGrupo) ? "toTrancode()" : elemento.getSentencaGet()) + " + ";
			}
		}
		imprimir = imprimir.substring(0, imprimir.length() - 3);
		return fazTabulacao(nivel) + "System.out.println(" + imprimir + ");\n";
	}

	public ArrayList<Atributo> getTexto() {
		return texto;
	}
}