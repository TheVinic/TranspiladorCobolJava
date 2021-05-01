package com.trans.transpiladorCobolJava.model.Sub_class;

import java.math.BigDecimal;

public class Sub_class{
	private String contents_2;

	public String getContents_2() {
		return contents_2;
	}

	public void setContents_2(String contents_2) {
		this.contents_2 = contents_2;
	}

	public String toTrancode() { 
		return String.format("|%30d|", contents_2);
	}

	public void toObject(String trancode) { 
		this.contents_2 = trancode.substring(0, 30);
	}
}
