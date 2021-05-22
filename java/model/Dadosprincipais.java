package com.trans.transpiladorCobolJava.model.Dadosprincipais;

import com.trans.transpiladorCobolJava.model.Teste;

import java.math.BigDecimal;

public class Dadosprincipais{
	private Integer suma;
	private Integer numa;
	private Integer numb;
	private Teste teste = new Teste();

	public Integer getSuma() {
		return suma;
	}

	public void setSuma(Integer suma) {
		this.suma = suma;
	}

	public Integer getNuma() {
		return numa;
	}

	public void setNuma(Integer numa) {
		this.numa = numa;
	}

	public Integer getNumb() {
		return numb;
	}

	public void setNumb(Integer numb) {
		this.numb = numb;
	}

	public Teste getTeste() {
		return teste;
	}

	public void setTeste(Teste teste) {
		this.teste = teste;
	}

	public String toTrancode() { 
		return String.format("|0%12d|", suma) + String.format("|0%12d|", numa) + String.format("|0%12d|", numb) + teste.toTrancode();
	}

	public void toObject(String trancode) { 
		this.suma = trancode.substring(0, 12);
		this.numa = trancode.substring(12, 24);
		this.numb = trancode.substring(24, 36);
		this.teste.toObject(trancode.substring(36, 48));
	}
}
