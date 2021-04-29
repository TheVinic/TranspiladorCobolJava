package com.trans.transpiladorCobolJava.model.DadosPrincipais;

import java.math.BigDecimal;

public class DadosPrincipais{
	private BigDecimal ws_num;
	private Integer b = 7;
	private BigDecimal e = 34.26;
	private BigDecimal dec1 = 1024.1024;
	private BigDecimal dec2 = 1024.1024;
	private String charn;

	public BigDecimal getWs_num() {
		return this.ws_num;
	}

	public void setWs_num(BigDecimal ws_num) {
		this.ws_num = ws_num;
	}

	public Integer getB() {
		return this.b;
	}

	public void setB(Integer b) {
		this.b = b;
	}

	public BigDecimal getE() {
		return this.e;
	}

	public void setE(BigDecimal e) {
		this.e = e;
	}

	public BigDecimal getDec1() {
		return this.dec1;
	}

	public void setDec1(BigDecimal dec1) {
		this.dec1 = dec1;
	}

	public BigDecimal getDec2() {
		return this.dec2;
	}

	public void setDec2(BigDecimal dec2) {
		this.dec2 = dec2;
	}

	public String getCharn() {
		return this.charn;
	}

	public void setCharn(String charn) {
		this.charn = charn;
	}
}
