package com.trans.transpiladorCobolJava.model.Char2;

import java.math.BigDecimal;

public class Char2{
	private String char22;

	public String getChar22() {
		return char22;
	}

	public void setChar22(String char22) {
		this.char22 = char22;
	}

	public String toTrancode() { 
		return String.format("|%2d|", char22);
	}

	public void toObject(String trancode) { 
		this.char22 = trancode.substring(0, 2);
	}
}
