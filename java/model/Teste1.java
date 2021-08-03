package com.trans.transpiladorCobolJava.model.Teste1;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Teste1{
	@Id
	private Integer numtmp;
	private Integer numc;

	public Integer getNumtmp() {
		return numtmp;
	}

	public void setNumtmp(Integer numtmp) {
		this.numtmp = numtmp;
	}

	public Integer getNumc() {
		return numc;
	}

	public void setNumc(Integer numc) {
		this.numc = numc;
	}

	public String toTrancode() { 
		return String.format("|0%12d|", numtmp) + String.format("|0%12d|", numc);
	}

	public void toObject(String trancode) { 
		this.numtmp = Integer.parseInt(trancode.substring(0, 12));
		this.numc = Integer.parseInt(trancode.substring(12, 24));
	}
}
