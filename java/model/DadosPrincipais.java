package com.trans.transpiladorCobolJava.model.DadosPrincipais;

import com.trans.transpiladorCobolJava.model.Item_1;

import com.trans.transpiladorCobolJava.model.Item_2;

import com.trans.transpiladorCobolJava.model.Char1;

import com.trans.transpiladorCobolJava.model.Char2;


public class DadosPrincipais{
	private Item_1 item_1;
	private Item_2 item_2;
	private BigDecimal ws_num;
	private Integer b = 7;
	private BigDecimal e = 34.26;
	private Integer comput1;
	private Integer comput2;
	private Integer comput11;
	private Integer comput22;
	private BigDecimal dec1 = 1024.1024;
	private BigDecimal dec2 = 1024.1024;
	private Char1 char1;
	private Char2 char2;
	private String charn;

	public Item_1 getItem_1() {
			return Item_1;
	}

	public Item_1 setItem_1() {
			return Item_1;
	}

	public Item_2 getItem_2() {
			return Item_2;
	}

	public Item_2 setItem_2() {
			return Item_2;
	}

	public BigDecimal getWs_num() {
		return this.ws_num;
	}

	public void setws_num(BigDecimal ws_num) {
		this.ws_num = ws_num;
	}

	public Integer getB() {
		return this.b;
	}

	public void setb(Integer b) {
		this.b = b;
	}

	public BigDecimal getE() {
		return this.e;
	}

	public void sete(BigDecimal e) {
		this.e = e;
	}

	public Integer getComput1() {
		return this.comput1;
	}

	public void setcomput1(Integer comput1) {
		this.comput1 = comput1;
	}

	public Integer getComput2() {
		return this.comput2;
	}

	public void setcomput2(Integer comput2) {
		this.comput2 = comput2;
	}

	public Integer getComput11() {
		return this.comput11;
	}

	public void setcomput11(Integer comput11) {
		this.comput11 = comput11;
	}

	public Integer getComput22() {
		return this.comput22;
	}

	public void setcomput22(Integer comput22) {
		this.comput22 = comput22;
	}

	public BigDecimal getDec1() {
		return this.dec1;
	}

	public void setdec1(BigDecimal dec1) {
		this.dec1 = dec1;
	}

	public BigDecimal getDec2() {
		return this.dec2;
	}

	public void setdec2(BigDecimal dec2) {
		this.dec2 = dec2;
	}

	public Char1 getChar1() {
			return Char1;
	}

	public Char1 setChar1() {
			return Char1;
	}

	public Char2 getChar2() {
			return Char2;
	}

	public Char2 setChar2() {
			return Char2;
	}

	public String getCharn() {
		return this.charn;
	}

	public void setcharn(String charn) {
		this.charn = charn;
	}
}
