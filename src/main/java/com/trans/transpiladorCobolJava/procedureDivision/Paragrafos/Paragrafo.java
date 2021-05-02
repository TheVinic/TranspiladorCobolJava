package com.trans.transpiladorCobolJava.procedureDivision.Paragrafos;

import com.trans.transpiladorCobolJava.arquivo.Codigo;
import com.trans.transpiladorCobolJava.dataDivision.DataDivision;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.Atributo;

public abstract class Paragrafo {

	protected static String toLowerFistCase(String nome) {
		return nome.substring(0, 1).toLowerCase() + nome.substring(1);
	}

	protected Atributo encontraIdentificador(Codigo umaSecao, DataDivision dataDivision) {
		
		if (umaSecao.getInstrucaoLeitura(umaSecao.getPosicaoLeitura() + 1).equals("OF")) {
			Atributo atributo = dataDivision.localizaAtributo(umaSecao.getInstrucaoAtualLeitura(),
					umaSecao.getInstrucaoLeitura(umaSecao.getPosicaoLeitura() + 2));
			umaSecao.setPosicaoLeitura(umaSecao.getPosicaoLeitura() + 3);
			return atributo;
		} else {
			return dataDivision.localizaAtributo(umaSecao.getInstrucaoAtualLeitura());
		}
	}

}
