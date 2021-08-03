package com.trans.transpiladorCobolJava.controller.Controller;

import com.trans.transpiladorCobolJava.Dadosprincipais;
import com.trans.transpiladorCobolJava.Teste1repository;
import com.trans.transpiladorCobolJava.Teste1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	@Autowired
	Dadosprincipais dadosprincipais;

	@Autowired
	Teste1 teste1;

	@Autowired
	Teste1repository teste1repository;

	@PostMapping("/crud")
	public void controller(){

		teste1repository.teste11select();
		teste1repository.teste12insert(dadosprincipais.getNumb(), teste1.getNumc());
		teste1repository.teste13update(dadosprincipais.getSuma());
		teste1repository.teste14delete();

	}
}
