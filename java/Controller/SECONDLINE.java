package com.trans.transpiladorCobolJava.Controller.SECONDLINE;

public class SECONDLINE {
import com.trans.transpiladorCobolJava.Teste;
 import com.trans.transpiladorCobolJava.Dadosprincipal;


	public void sECONDLINE(Dadosprincipal dadosprincipal, Teste teste){

		teste.getTeste2().setNumtmp(100 + teste.getTeste2().getNumtmp());
		dadosprincipal.setNumb(100 + dadosprincipal.getNumb());
		teste.getTeste2().setNumtmp(dadosprincipal.getNumb() + teste.getTeste2().getNumtmp());
		dadosprincipal.setSuma(teste.getTeste2().getNumtmp() / dadosprincipal.getNumb());
		dadosprincipal.setNuma((dadosprincipal.getNumb() + 10) - teste.getTeste2().getNumtmp());
		dadosprincipal.setSuma(teste.getTeste2().getNumtmp());
		System.out.println("SUM OF NUMBERS : "  + dadosprincipal.getSuma());

	}
}
