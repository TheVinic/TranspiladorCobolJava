package com.trans.transpiladorCobolJava.model.Dadosprincipais;

import com.trans.transpiladorCobolJava.model.Teste1;

import java.math.BigDecimal;

public class Dadosprincipais{
	private Integer suma;
	private Integer numa;
	private Integer numb;
	private Teste1 teste1 = new Teste1();
	private Integer numd;

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

	public Integer getNumd() {
		return numd;
	}

	public void setNumd(Integer numd) {
		this.numd = numd;
	}

	public String toTrancode() { 
		return String.format("|0%12d|", suma) + String.format("|0%12d|", numa) + String.format("|0%12d|", numb) + teste1.toTrancode() + String.format("|0%12d|", numd);
	}

	public void toObject(String trancode) { 
		this.suma = Integer.parseInt(trancode.substring(0, 12));
		this.numa = Integer.parseInt(trancode.substring(12, 24));
		this.numb = Integer.parseInt(trancode.substring(24, 36));
		this.teste1.toObject(trancode.substring(36, 60));
		this.numd = Integer.parseInt(trancode.substring(60, 72));
	}
}
