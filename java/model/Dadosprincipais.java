package com.trans.transpiladorCobolJava.model.Dadosprincipais;

import java.math.BigDecimal;

public class Dadosprincipais{
	private Integer suma;
	private Integer numa;
	private Integer numb;
	private Integer numtmp;

	public Integer getSuma() {
		return suma;
	}

	public void setSuma(Integer suma) {
		this.suma = suma;
	}

	public Integer getNuma() {
		return numa;
	}

	public void setNuma(Integer numa) {
		this.numa = numa;
	}

	public Integer getNumb() {
		return numb;
	}

	public void setNumb(Integer numb) {
		this.numb = numb;
	}

	public Integer getNumtmp() {
		return numtmp;
	}

	public void setNumtmp(Integer numtmp) {
		this.numtmp = numtmp;
	}

	public String toTrancode() { 
		return String.format("|0%12d|", suma) + String.format("|0%12d|", numa) + String.format("|0%12d|", numb) + String.format("|0%12d|", numtmp);
	}

	public void toObject(String trancode) { 
		this.suma = trancode.substring(0, 12);
		this.numa = trancode.substring(12, 24);
		this.numb = trancode.substring(24, 36);
		this.numtmp = trancode.substring(36, 48);
	}
}
