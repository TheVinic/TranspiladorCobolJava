package com.trans.transpiladorCobolJava.model.Controller;

import com.trans.transpiladorCobolJava.model.DadosPrincipal;

public class Controller {

	DadosPrincipal dadosPrincipal = new DadosPrincipal();

	public void home(){

		System.out.println("HELLO WORLD???". );
		System.out.println("DEC1 = " dadosPrincipal.getITEM-1().getITEM-2().getSUB-CLASS().getCHAR1().getCHAR2().getDec1());
		System.out.println("DEC2 = " dadosPrincipal.getITEM-1().getITEM-2().getSUB-CLASS().getCHAR1().getCHAR2().getDec2());
		System.out.println("CHAR1 = " dadosPrincipal.getITEM-1().getITEM-2().getSUB-CLASS().getCHAR1().getCHAR2().getChar1());
		System.out.println("CHAR2 = " dadosPrincipal.getITEM-1().getITEM-2().getSUB-CLASS().getCHAR1().getCHAR2().getChar2());
		System.out.println("CHARN = " dadosPrincipal.getITEM-1().getITEM-2().getSUB-CLASS().getCHAR1().getCHAR2().getCharn());

	}
}
