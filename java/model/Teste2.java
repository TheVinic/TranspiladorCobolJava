package com.trans.transpiladorCobolJava.model.Teste2;

import java.math.BigDecimal;

public class Teste2{
	private Integer numtmp;

	public Integer getNumtmp() {
		return numtmp;
	}

	public void setNumtmp(Integer numtmp) {
		this.numtmp = numtmp;
	}

	public String toTrancode() { 
		return String.format("|0%12d|", numtmp);
	}

	public void toObject(String trancode) { 
		this.numtmp = trancode.substring(0, 12);
	}
}
