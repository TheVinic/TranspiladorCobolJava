package com.trans.transpiladorCobolJava.dataDivision;

import java.util.HashSet;
import java.util.Set;

public class Atributo {

	private String nome;

	private Integer comprimento;

	private Integer comprimentoDecimal;

	private Integer nivel;

	private TipoAtributo tipoAtributo;

	private ValorAtributo valor;

	private ItemTipo itemTipo;

	private Set<Atributo> atributos;

	private Atributo atributoPai;

	public Atributo() {	}
	
	public Atributo(Integer nivel, String nomeAtributo, Atributo atributoPai, Integer comprimento,
			Integer comprimentoDecimal, TipoAtributo tipoAtributo, ItemTipo itemTipo) {
		this.nome = nomeAtributo;
		this.nivel = nivel;
		this.atributoPai = atributoPai;
		this.itemTipo = itemTipo;
		this.atributos = new HashSet<Atributo>();
		if (itemTipo == ItemTipo.ELEMENTO) {
			this.comprimento = comprimento;
			this.tipoAtributo = tipoAtributo;
			if(tipoAtributo == TipoAtributo.DECIMAL) {
				this.comprimentoDecimal = comprimentoDecimal;				
			}
		}
	}

	public String getNome() {
		return nome;
	}

	public Integer getComprimento() {
		return comprimento;
	}

	public Integer getNivel() {
		return nivel;
	}

	public Set<Atributo> getAtributos() {
		return atributos;
	}

	public Integer getComprimentoDecimal() {
		return comprimentoDecimal;
	}

	public TipoAtributo getTipoAtributo() {
		return tipoAtributo;
	}

	public ValorAtributo getValor() {
		return valor;
	}

	public ItemTipo getItemTipo() {
		return itemTipo;
	}

	public Atributo getAtributoPai() {
		return atributoPai;
	}

}
