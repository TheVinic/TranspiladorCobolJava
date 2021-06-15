package com.trans.transpiladorCobolJava.Controller.Controller;

import com.trans.transpiladorCobolJava.mainline;
import com.trans.transpiladorCobolJava.Dadosprincipal;

public class Controller {

	mainline mainline = new mainline();

	Dadosprincipal dadosprincipal = new Dadosprincipal();

	public void controller(){

		mainline.mainline(mainline, dadosprincipal);


	}
}
