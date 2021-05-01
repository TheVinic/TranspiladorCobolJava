package com.trans.transpiladorCobolJava.model.Char3;

import java.math.BigDecimal;

public class Char3{
	private String char33;

	public String getChar33() {
		return char33;
	}

	public void setChar33(String char33) {
		this.char33 = char33;
	}

	public String toTrancode() { 
		return String.format("|%2d|", char33);
	}

	public void toObject(String trancode) { 
		this.char33 = trancode.substring(0, 2);
	}
}
