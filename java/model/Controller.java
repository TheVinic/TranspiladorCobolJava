package com.trans.transpiladorCobolJava.model.Controller;

import com.trans.transpiladorCobolJava.model.Char2;
 import com.trans.transpiladorCobolJava.model.Dadosprincipal;
 import com.trans.transpiladorCobolJava.model.Char1;

public class Controller {

	Dadosprincipal dadosprincipal = new Dadosprincipal();

	Char1 char1 = new Char1();

	Char2 char2 = new Char2();

	public void home(){

		System.out.println("HELLO WORLD???" );
		System.out.println("EQUUAL 2" );
		System.out.println("COMPUATATIONAL1 = "  + dadosprincipal.getComput1());
		System.out.println("COMPUATATIONAL2 = "  + dadosprincipal.getComput2());
		System.out.println("DEC1 = "  + dadosprincipal.getDec1());
		System.out.println("DEC2 = "  + dadosprincipal.getDec2());
		System.out.println("CHAR1 = "  + char1.toTrancode());
		System.out.println("CHAR2 = "  + char2.toTrancode());
		System.out.println("CHARN = "  + dadosprincipal.getCharn());

	}
}
