package com.trans.transpiladorCobolJava.model.Char2;

import com.trans.transpiladorCobolJava.model.Char3;

import java.math.BigDecimal;

public class Char2{
	private String char22;
	private Char3 char3 = new Char3();

	public String getChar22() {
		return char22;
	}

	public void setChar22(String char22) {
		this.char22 = char22;
	}

	public Char3 getChar3() {
		return char3;
	}

	public void setChar3(Char3 char3) {
		this.char3 = char3;
	}

	public String toTrancode() { 
		return String.format("|%2d|", char22) + char3.toTrancode();
	}

	public void toObject(String trancode) { 
		this.char22 = trancode.substring(0, 2);
		this.char3.toObject(trancode.substring(2, 4));
	}
}
