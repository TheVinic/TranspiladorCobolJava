package com.trans.transpiladorCobolJava.DTO;

import java.util.List;

import com.trans.transpiladorCobolJava.dataDivision.model.atributo.Atributo;

public class AtributoGrupoResponse extends AtributoResponse {

	private List<AtributoResponse> atributos;

	public AtributoGrupoResponse(String nome, Integer nivel, String occurs, List<Atributo> atributos) {
		super(nome, nivel, occurs);
		/*for (Atributo elemento : atributos) {
			if (elemento instanceof AtributoGrupo) {
				this.atributos.add((AtributoGrupo) elemento.toResponse());
			} else {
			}
		}*/
	}

	public List<AtributoResponse> getAtributos() {
		return atributos;
	}

}
