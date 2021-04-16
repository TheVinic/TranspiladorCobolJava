package com.trans.transpiladorCobolJava.dataDivision;

import java.util.HashSet;
import java.util.Set;

import com.trans.transpiladorCobolJava.arquivo.Arquivo;
import com.trans.transpiladorCobolJava.arquivo.Palavra;
import com.trans.transpiladorCobolJava.main.Divisoes;

public class WorkingStorageSection {

	Set<Atributo> atributos = new HashSet<Atributo>();

	public Palavra popula(Arquivo arquivo) {

		Atributo atributoPai = new Atributo();
		Atributo atributoAnterior = new Atributo();
		Atributo atributoAtual;
		Integer nivelAnterior = 1;
		TipoAtributo tipoAtributo = null;
		Palavra valor = null;
		ItemTipo itemTipo=null;
		Integer nivel = 1;

		do {
			Integer comprimento = 0;
			Integer comprimentoDecimal = 0;

			// Lê nível
			if (itemTipo != ItemTipo.GRUPO) {
				nivel = Integer.parseInt(arquivo.lerAte(' ').getPalavra());
			}else {
				nivel = Integer.parseInt(valor.getPalavra());
			}
			// Lê nome atributo
			Palavra nomeAtributo = arquivo.lerAte(' ', '.');

			valor = arquivo.lerAte(' ', '.');
			// Valida se atributo é elementar
			if (nomeAtributo.getUltimoCaractere() == ' '
					&& (valor.getPalavra().equals("PIC") || valor.getPalavra().equals("PICTURE"))) {
				itemTipo = ItemTipo.ELEMENTO;
				valor = arquivo.lerAte(' ', '(', '.', 'V');

				if (valor.getPalavra().equals("IS")) {
					valor = arquivo.lerAte('(', '.', ' ', 'V');
				}

				// Valida o tipo do atributo

				tipoAtributo = validaTipoAtributo(valor);

				// Tratamento para o comprimento, se tipo XXX ou X(3)

				if (valor.getUltimoCaractere() != '(') {
					if (tipoAtributo.equals(TipoAtributo.CARACTERE)) {
						comprimento = valor.lenghtPalavra();
					} else if (tipoAtributo.equals(TipoAtributo.DECIMAL)) {
						comprimento = valor.lenghtPalavra();
					} else {
						if (valor.getPalavra().substring(0, 0) == "S")
							comprimento = valor.lenghtPalavra() - 1;
						else
							comprimento = valor.lenghtPalavra();
					}
				} else {
					valor = arquivo.lerAte(')');
					comprimento = Integer.parseInt(valor.getPalavra());
				}

				// Se for decimal, ver tamanho restante
				valor = arquivo.lerAte('V', '.', ' ');
				if (tipoAtributo == TipoAtributo.DECIMAL || valor.getUltimoCaractere() == 'V') {
					if (valor.getUltimoCaractere() == 'V') {
						valor = arquivo.lerAte(' ', '.', '(');
					}
					if (valor.getUltimoCaractere() != '(') {
						comprimentoDecimal = valor.lenghtPalavra();
					} else {
						valor = arquivo.lerAte(')');
						comprimentoDecimal = Integer.parseInt(valor.getPalavra());
					}
				}
			}
			// Cria elemento de grupo
			else {
				itemTipo = ItemTipo.GRUPO;
			}

			// Valida o nível
			if (nivel == 1) {
				atributoAtual = new Atributo(nivel, nomeAtributo.getPalavra(), null, comprimento, comprimentoDecimal,
						tipoAtributo, itemTipo);
				atributos.add(atributoAtual);
				atributoAnterior = atributoAtual;
				nivelAnterior = nivel;
			} else if (nivel == nivelAnterior) {
				atributoAtual = new Atributo(nivel, nomeAtributo.getPalavra(), atributoPai, comprimento,
						comprimentoDecimal, tipoAtributo, itemTipo);
				atributoPai.getAtributos().add(atributoAtual);
				atributoAnterior = atributoAtual;
			} else if (nivel > nivelAnterior) {
				atributoAtual = new Atributo(nivel, nomeAtributo.getPalavra(), atributoAnterior, comprimento,
						comprimentoDecimal, tipoAtributo, itemTipo);
				atributoAnterior.getAtributos().add(atributoAtual);
				atributoPai = atributoAnterior;
				atributoAnterior = atributoAtual;
				nivelAnterior = nivel;
			} else if (nivel < nivelAnterior) {
				do {
					atributoPai = atributoPai.getAtributoPai();
				} while (nivel > atributoPai.getNivel());
				atributoAtual = new Atributo(nivel, nomeAtributo.getPalavra(), atributoPai, comprimento,
						comprimentoDecimal, tipoAtributo, itemTipo);
				atributoPai.getAtributos().add(atributoAtual);
				atributoAnterior = atributoAtual;
			} else {
				System.out.println("Erro nos níveis");
			}
		} while (!valor.getPalavra().contains("[a-Z]"));
		return null;
	}

	public TipoAtributo validaTipoAtributo(Palavra palavra) { // Valida o tipo do atributo if
		TipoAtributo tipoAtributo;
		if (palavra.getPalavra().substring(0, 0) == "X") {
			tipoAtributo = TipoAtributo.CARACTERE;
		} else if (palavra.getUltimoCaractere() == 'V') {
			tipoAtributo = TipoAtributo.DECIMAL;
		} else {
			tipoAtributo = TipoAtributo.NUMERO;
		}
		return tipoAtributo;
	}
}
