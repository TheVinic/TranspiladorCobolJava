package com.trans.transpiladorCobolJava.model.Dadosprincipais;

import com.trans.transpiladorCobolJava.model.Times;

import java.math.BigDecimal;

public class Dadosprincipais{
	private String suma = 56;
	private Times times = new Times();
	private Integer teste3;

	public String getSuma() {
		return suma;
	}

	public void setSuma(String suma) {
		this.suma = suma;
	}

	public Times getTimes() {
		return times;
	}

	public void setTimes(Times times) {
		this.times = times;
	}

	public Integer getTeste3() {
		return teste3;
	}

	public void setTeste3(Integer teste3) {
		this.teste3 = teste3;
	}

	public String toTrancode() { 
		return String.format("|%1d|", suma) + times.toTrancode() + String.format("|0%1d|", teste3);
	}

	public void toObject(String trancode) { 
		this.suma = trancode.substring(0, 1);
		this.times.toObject(trancode.substring(1, 13));
		this.teste3 = trancode.substring(13, 14);
	}
}
