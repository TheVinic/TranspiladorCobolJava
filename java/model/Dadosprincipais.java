package com.trans.transpiladorCobolJava.model.Dadosprincipais;

import com.trans.transpiladorCobolJava.model.Teste1;

import java.math.BigDecimal;

import javax.persistence.JoinColumn;

public class Dadosprincipais{
	private Integer suma;
	private Integer numa;
	private Integer numb;
	private Teste1 teste1 = new Teste1();
	private Integer numc;

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

	public Teste1 getTeste1() {
		return teste1;
	}

	public void setTeste1(Teste1 teste1) {
		this.teste1 = teste1;
	}

	public Integer getNumc() {
		return numc;
	}

	public void setNumc(Integer numc) {
		this.numc = numc;
	}

	public String toTrancode() { 
		return String.format("|0%12d|", suma) + String.format("|0%12d|", numa) + String.format("|0%12d|", numb) + teste1.toTrancode() + String.format("|0%12d|", numc);
	}

	public void toObject(String trancode) { 
		this.suma = trancode.substring(0, 12);
		this.numa = trancode.substring(12, 24);
		this.numb = trancode.substring(24, 36);
		this.teste1.toObject(trancode.substring(36, 48));
		this.numc = trancode.substring(48, 60);
	}
}
