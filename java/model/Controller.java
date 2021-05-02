package com.trans.transpiladorCobolJava.model.Controller;

import com.trans.transpiladorCobolJava.model.Char1;
 import com.trans.transpiladorCobolJava.model.Dadosprincipal;

public class Controller {

	Dadosprincipal dadosprincipal = new Dadosprincipal();

	Char1 char1 = new Char1();

	public void home(){

		System.out.println("HELLO WORLD???" );
		System.out.println(char1.getChar2().getChar3().toTrancode());
		System.out.println("DEC1 = "  + dadosprincipal.getDec1());
		System.out.println("DEC2 = "  + dadosprincipal.getDec2());
		System.out.println("CHAR1 = "  + char1.getChar2().getChar3().toTrancode());
		System.out.println("CHAR2 = "  + char1.getChar2().getChar3().toTrancode());
		System.out.println("CHARN = "  + dadosprincipal.getCharn());

	}
}
