package com.trans.transpiladorCobolJava.model.Dadosprincipais;

import com.trans.transpiladorCobolJava.model.Item_1;

import com.trans.transpiladorCobolJava.model.Item_2;

import com.trans.transpiladorCobolJava.model.Char1;

import java.math.BigDecimal;

public class Dadosprincipais{
	private Item_1[] item_1 = new Item_1[5]();
	private Item_2 item_2 = new Item_2();
	private BigDecimal ws_num;
	private Integer b = 7;
	private BigDecimal e = 34.26;
	private BigDecimal dec1 = 1024.1024;
	private BigDecimal dec2 = 1024.1024;
	private Char1 char1 = new Char1();
	private String charn;
	final String filler1 = "OLA";
	final String filler2;
	final String filler3;

	public Item_1[] getItem_1() {
		return item_1;
	}

	public void setItem_1(Item_1[] item_1) {
		this.item_1 = item_1;
	}

	public Item_2 getItem_2() {
		return item_2;
	}

	public void setItem_2(Item_2 item_2) {
		this.item_2 = item_2;
	}

	public BigDecimal getWs_num() {
		return ws_num;
	}

	public void setWs_num(BigDecimal ws_num) {
		this.ws_num = ws_num;
	}

	public Integer getB() {
		return b;
	}

	public void setB(Integer b) {
		this.b = b;
	}

	public BigDecimal getE() {
		return e;
	}

	public void setE(BigDecimal e) {
		this.e = e;
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

	public String getCharn() {
		return charn;
	}

	public void setCharn(String charn) {
		this.charn = charn;
	}

	public String getFiller1() {
		return filler1;
	}

	public void setFiller1(String filler1) {
		this.filler1 = filler1;
	}

	public String getFiller2() {
		return filler2;
	}

	public void setFiller2(String filler2) {
		this.filler2 = filler2;
	}

	public String getFiller3() {
		return filler3;
	}

	public void setFiller3(String filler3) {
		this.filler3 = filler3;
	}

	public String toTrancode() { 
		return item_1.toTrancode() + item_2.toTrancode() + String.format("|0%12d|", ws_num) + String.format("|0%10d|", b) + String.format("|0%10d|", e) + String.format("|0%11d|", dec1) + String.format("|0%11d|", dec2) + char1.toTrancode() + String.format("|%99d|", charn) + String.format("|%9d|", filler1) + String.format("|%9d|", filler2) + String.format("|%9d|", filler3);
	}

	public void toObject(String trancode) { 
		this.item_1.toObject(trancode.substring(0, 113));
		this.item_2.toObject(trancode.substring(113, 211));
		this.ws_num = trancode.substring(211, 223);
		this.b = trancode.substring(223, 233);
		this.e = trancode.substring(233, 243);
		this.dec1 = trancode.substring(243, 254);
		this.dec2 = trancode.substring(254, 265);
		this.char1.toObject(trancode.substring(265, 270));
		this.charn = trancode.substring(270, 369);
		this.filler1 = trancode.substring(369, 378);
		this.filler2 = trancode.substring(378, 387);
		this.filler3 = trancode.substring(387, 396);
	}
}
