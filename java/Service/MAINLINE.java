package com.trans.transpiladorCobolJava.Service.MAINLINE;

import com.trans.transpiladorCobolJava.mainline;
import com.trans.transpiladorCobolJava.Dadosprincipal;

public class MAINLINE {

	public void mainline(mainline mainline, Dadosprincipal dadosprincipal){

		mainline.mainline(mainline, dadosprincipal);
		if( 10 > 5 ) {
			dadosprincipal.setNumb((100) - dadosprincipal.getNuma());
		} else {
			System.out.println("Enter NUmber a");
		}
		System.out.println("Enter NUmber a");


	}
}
