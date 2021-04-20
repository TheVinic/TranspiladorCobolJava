package com.trans.transpiladorCobolJava;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.trans.transpiladorCobolJava.main.Transpilador;

@SpringBootApplication
public class TranspiladorCobolJavaApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(TranspiladorCobolJavaApplication.class, args);

		Transpilador transpilador = new Transpilador();
		
		transpilador.processa();
	}

}
