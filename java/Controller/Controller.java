package com.trans.transpiladorCobolJava.Controller.Controller;

import com.trans.transpiladorCobolJava.model.Dadosprincipal;
 import com.trans.transpiladorCobolJava.Dadosprincipal;

public class Controller {

	model.Dadosprincipal model.Dadosprincipal = new model.Dadosprincipal();

	Dadosprincipal dadosprincipal = new Dadosprincipal();

	public void home(){

		System.out.println("ENTER NUMBER A" );
		System.out.println("ENTER NUMBER B" );
		if( 5 = 10 || 5 > 8 ) {
			dadosprincipal.setSuma(0+1-(5/9)*6/*Exponencial não implementado*/*5);
		} else {
			dadosprincipal.setNumtmp(0);
		}
		dadosprincipal.setNumtmp(100 + dadosprincipal.getNumtmp());
		dadosprincipal.setNumb(100 + dadosprincipal.getNumb());

		dadosprincipal.setNumtmp(dadosprincipal.getNumb() + dadosprincipal.getNumtmp());

		dadosprincipal.setSuma(dadosprincipal.getNumtmp() / dadosprincipal.getNumb());

		dadosprincipal.setNuma((dadosprincipal.getNumb() + 10) - dadosprincipal.getNumtmp());

		dadosprincipal.setSuma(dadosprincipal.getNumtmp());

		System.out.println("SUM OF NUMBERS : "  + dadosprincipal.getSuma());

	}
}
