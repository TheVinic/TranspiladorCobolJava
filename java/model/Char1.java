package com.trans.transpiladorCobolJava.model.Char1;

import com.trans.transpiladorCobolJava.model.Char2;

import java.math.BigDecimal;

public class Char1{
	private String char12;
	private Char2 char2 = new Char2();

	public String getChar12() {
		return char12;
	}

	public void setChar12(String char12) {
		this.char12 = char12;
	}

	public Char2 getChar2() {
		return char2;
	}

	public void setChar2(Char2 char2) {
		this.char2 = char2;
	}

	public String toTrancode() { 
		return String.format("|%1d|", char12) + char2.toTrancode();
	}

	public void toObject(String trancode) { 
		this.char12 = trancode.substring(0, 1);
		this.char2.toObject(trancode.substring(1, 5));
	}
}
