package com.trans.transpiladorCobolJava.Controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.trans.transpiladorCobolJava.DTO.VariaveisResponse;
import com.trans.transpiladorCobolJava.main.TranspiladorService;

@RestController
@Valid
public class TranspiladorController {

	@Autowired
	private TranspiladorService transpilador;

	@Autowired
	private ServletContext servletContext;

	@PostMapping("/transpilacoes/geraVariaveis")
	public ResponseEntity<?> NovaTranspilacao(@NotNull @NotEmpty @RequestParam("file") MultipartFile file) throws IOException {
		String path = null;
		if (file != null && !file.isEmpty()) {
			path = servletContext.getRealPath("/") + "resources\\" + "codigoCobol" + ".txt";
			saveFile(path, file);
		}

		transpilador = new TranspiladorService();

		VariaveisResponse variaveisReponse = transpilador.processa(path);

		return ResponseEntity.ok(variaveisReponse);
	}

	public static void saveFile(String path, MultipartFile file) {

		File saveFile = new File(path);
		try {
			FileUtils.writeByteArrayToFile(saveFile, file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
