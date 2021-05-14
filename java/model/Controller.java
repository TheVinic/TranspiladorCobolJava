package com.trans.transpiladorCobolJava.model.Controller;

import com.trans.transpiladorCobolJava.model.Dadosprincipal;

public class Controller {

	Dadosprincipal dadosprincipal = new Dadosprincipal();

	public void home(){

		System.out.println("ENTER NUMBER A" );
		System.out.println("ENTER NUMBER B" );
		dadosprincipal.setNumtmp(100 + dadosprincipal.getNumtmp());
		dadosprincipal.setNumb(100 + dadosprincipal.getNumb());

		dadosprincipal.setNumtmp(dadosprincipal.getNumb() + dadosprincipal.getNumtmp());

		dadosprincipal.setSuma(dadosprincipal.getNumtmp() / dadosprincipal.getNumb());

		System.out.println("SUM OF NUMBERS : "  + dadosprincipal.getSuma());

	}
}
