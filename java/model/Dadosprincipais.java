package com.trans.transpiladorCobolJava.model.Dadosprincipais;

import com.trans.transpiladorCobolJava.model.Char1;

import com.trans.transpiladorCobolJava.model.Char2;

import java.math.BigDecimal;

public class Dadosprincipais{
	private BigDecimal e = 34.26;
	private Integer comput1;
	private Integer comput2;
	private Integer comput11;
	private Integer comput22;
	private BigDecimal dec1 = 1024.1024;
	private BigDecimal dec2 = 1024.1024;
	private Char1 char1 = new Char1();
	private Char2 char2 = new Char2();
	private String charn;

	public BigDecimal getE() {
		return e;
	}

	public void setE(BigDecimal e) {
		this.e = e;
	}

	public Integer getComput1() {
		return comput1;
	}

	public void setComput1(Integer comput1) {
		this.comput1 = comput1;
	}

	public Integer getComput2() {
		return comput2;
	}

	public void setComput2(Integer comput2) {
		this.comput2 = comput2;
	}

	public Integer getComput11() {
		return comput11;
	}

	public void setComput11(Integer comput11) {
		this.comput11 = comput11;
	}

	public Integer getComput22() {
		return comput22;
	}

	public void setComput22(Integer comput22) {
		this.comput22 = comput22;
	}

	public BigDecimal getDec1() {
		return dec1;
	}

	public void setDec1(BigDecimal dec1) {
		this.dec1 = dec1;
	}

	public BigDecimal getDec2() {
		return dec2;
	}

	public void setDec2(BigDecimal dec2) {
		this.dec2 = dec2;
	}

	public Char1 getChar1() {
		return char1;
	}

	public void setChar1(Char1 char1) {
		this.char1 = char1;
	}

	public Char2 getChar2() {
		return char2;
	}

	public void setChar2(Char2 char2) {
		this.char2 = char2;
	}

	public String getCharn() {
		return charn;
	}

	public void setCharn(String charn) {
		this.charn = charn;
	}

	public String toTrancode() { 
		return String.format("|0%10d|", e) + String.format("|0%15d|", comput1) + String.format("|0%15d|", comput2) + String.format("|0%15d|", comput11) + String.format("|0%15d|", comput22) + String.format("|0%11d|", dec1) + String.format("|0%11d|", dec2) + char1.toTrancode() + char2.toTrancode() + String.format("|%99d|", charn);
	}

	public void toObject(String trancode) { 
		this.e = trancode.substring(0, 10);
		this.comput1 = trancode.substring(10, 25);
		this.comput2 = trancode.substring(25, 40);
		this.comput11 = trancode.substring(40, 55);
		this.comput22 = trancode.substring(55, 70);
		this.dec1 = trancode.substring(70, 81);
		this.dec2 = trancode.substring(81, 92);
		this.char1.toObject(trancode.substring(92, 93));
		this.char2.toObject(trancode.substring(93, 95));
		this.charn = trancode.substring(95, 194);
	}
}
