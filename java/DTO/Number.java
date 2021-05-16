package com.trans.transpiladorCobolJava.DTO.Number;

import java.math.BigDecimal;

public class Number{
	private String variavel1;
	private Integer variavel2;

	public String getVariavel1() {
		return variavel1;
	}

	public void setVariavel1(String variavel1) {
		this.variavel1 = variavel1;
	}

	public Integer getVariavel2() {
		return variavel2;
	}

	public void setVariavel2(Integer variavel2) {
		this.variavel2 = variavel2;
	}

	public String toTrancode() { 
		return String.format("|%1d|", variavel1) + String.format("|0%5d|", variavel2);
	}

	public void toObject(String trancode) { 
		this.variavel1 = trancode.substring(0, 1);
		this.variavel2 = trancode.substring(1, 6);
	}
}
