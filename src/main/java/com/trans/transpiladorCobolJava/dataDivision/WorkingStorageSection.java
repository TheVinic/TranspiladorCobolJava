package com.trans.transpiladorCobolJava.dataDivision;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.trans.transpiladorCobolJava.arquivo.Codigo;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.Atributo;
import com.trans.transpiladorCobolJava.dataDivision.model.atributo.AtributoGrupo;
import com.trans.transpiladorCobolJava.main.Divisoes;

@Component
public class WorkingStorageSection extends DataDivisionCriaVariaveis {

	ArrayList<Atributo> atributos = new ArrayList<Atributo>();

	AtributoGrupo DadosPrincipais;

	Codigo codigoCompleto;

	public AtributoGrupo popula(Codigo codigoCobol) {
		List<String> classes = new ArrayList<String>();
		while (!codigoCobol.getProximaInstrucaoLeitura().isEmpty()
				&& !SecoesDataDivision.acabouParagrafoAtual(codigoCobol.getInstrucaoAtualLeitura())
				&& !Divisoes.acabouDivisaoAtual(codigoCobol.getInstrucaoAtualLeitura())) {
			atributos.add(criaItem(new Codigo(codigoCobol.getInstrucaoAtualLeitura().split("\\s")), codigoCobol,
					classes, SecoesDataDivision.WORKINGSTORAGESECTION));
		}
		DadosPrincipais = new AtributoGrupo("DadosPrincipais", 0, atributos, null, null,
				SecoesDataDivision.WORKINGSTORAGESECTION);
		return DadosPrincipais;
	}
}
