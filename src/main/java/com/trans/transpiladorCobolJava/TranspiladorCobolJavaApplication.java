package com.trans.transpiladorCobolJava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.trans.transpiladorCobolJava.main.Transpilador;

@SpringBootApplication
public class TranspiladorCobolJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TranspiladorCobolJavaApplication.class, args);

		Transpilador transpilador = new Transpilador();
		
		transpilador.processa();
	}

}
