package com.trans.transpiladorCobolJava.model.Teste1;

import com.trans.transpiladorCobolJava.model.Teste2;

import java.math.BigDecimal;

import javax.persistence.JoinColumn;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Teste1{
	@Id
	@JoinColumn
	private Teste2 teste2 = new Teste2();

	public Teste2 getTeste2() {
		return teste2;
	}

	public void setTeste2(Teste2 teste2) {
		this.teste2 = teste2;
	}

	public String toTrancode() { 
		return teste2.toTrancode();
	}

	public void toObject(String trancode) { 
		this.teste2.toObject(trancode.substring(0, 12));
	}
}
