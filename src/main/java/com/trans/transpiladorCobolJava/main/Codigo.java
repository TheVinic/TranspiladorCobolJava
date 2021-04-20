package com.trans.transpiladorCobolJava.main;

public class Codigo {

	String[] codigoCobol;
	Integer posicaoLeitura;
	
	public Codigo(String[] codigoDividido) {
		this.codigoCobol = codigoDividido;
		this.posicaoLeitura = 0;
	}

	public String[] getCodigoCobol() {
		return codigoCobol;
	}
	
	public Integer getPosicaoLeitura() {
		return posicaoLeitura;
	}

	public void setPosicaoLeitura(Integer posicaoLeitura) {
		this.posicaoLeitura = posicaoLeitura;
	}
	

	public void setVoltaPosicaoLeitura() {
		this.posicaoLeitura-=1;
	}
	
	public String getProximaInstrucaoLeitura() {
		posicaoLeitura+=1;
		return (posicaoLeitura >= codigoCobol.length) ? new String() : codigoCobol[posicaoLeitura];
	}

	
	public String getInstrucaoAtualLeitura() {
		return codigoCobol[posicaoLeitura];
	}

	
	public String getInstrucaoLeitura(Integer i) {
		return (i >= codigoCobol.length) ? new String() : codigoCobol[i];
	}
	
}
