package com.trans.transpiladorCobolJava.arquivo;

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
		return (posicaoLeitura >= codigoCobol.length) ? new String() : codigoCobol[posicaoLeitura];
	}

	
	public String getInstrucaoLeitura(Integer i) {
		return (i >= codigoCobol.length) ? new String() : codigoCobol[i];
	}
	
	private boolean acabarNaProximaIteracao = false;
	
	public boolean isOver() {
		if(acabarNaProximaIteracao || posicaoLeitura >= codigoCobol.length) {
			acabarNaProximaIteracao = false;
			return true;
		}else {
			acabarNaProximaIteracao = (codigoCobol[posicaoLeitura].endsWith(".")) ? true : false; 
			return false;
		}
	}

	public void avancaPosicaoLeitura() {
		posicaoLeitura++;
	}

	public boolean isLastLine() {
		if(posicaoLeitura >= codigoCobol.length) {
			return true;
		}else {
			return false;
		}
	}
	
}
