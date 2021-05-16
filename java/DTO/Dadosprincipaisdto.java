package com.trans.transpiladorCobolJava.DTO.Dadosprincipaisdto;

import com.trans.transpiladorCobolJava.DTO.Number;

import java.math.BigDecimal;

public class Dadosprincipaisdto{
	private Number number = new Number();

	public Number getNumber() {
		return number;
	}

	public void setNumber(Number number) {
		this.number = number;
	}

	public String toTrancode() { 
		return number.toTrancode();
	}

	public void toObject(String trancode) { 
		this.number.toObject(trancode.substring(0, 6));
	}
}
