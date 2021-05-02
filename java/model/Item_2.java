package com.trans.transpiladorCobolJava.model.Item_2;

import com.trans.transpiladorCobolJava.model.Sub_class;

import java.math.BigDecimal;

public class Item_2{
	private Integer key_2;
	private String line_2 = "VINI";
	private String name_2;
	private String unqual_name_2;
	private Sub_class[] sub_class = new Sub_class[5]();

	public Integer getKey_2() {
		return key_2;
	}

	public void setKey_2(Integer key_2) {
		this.key_2 = key_2;
	}

	public String getLine_2() {
		return line_2;
	}

	public void setLine_2(String line_2) {
		this.line_2 = line_2;
	}

	public String getName_2() {
		return name_2;
	}

	public void setName_2(String name_2) {
		this.name_2 = name_2;
	}

	public String getUnqual_name_2() {
		return unqual_name_2;
	}

	public void setUnqual_name_2(String unqual_name_2) {
		this.unqual_name_2 = unqual_name_2;
	}

	public Sub_class[] getSub_class() {
		return sub_class;
	}

	public void setSub_class(Sub_class[] sub_class) {
		this.sub_class = sub_class;
	}

	public String toTrancode() { 
		return String.format("|0%2d|", key_2) + String.format("|%6d|", line_2) + String.format("|%30d|", name_2) + String.format("|%30d|", unqual_name_2) + sub_class.toTrancode();
	}

	public void toObject(String trancode) { 
		this.key_2 = trancode.substring(0, 2);
		this.line_2 = trancode.substring(2, 8);
		this.name_2 = trancode.substring(8, 38);
		this.unqual_name_2 = trancode.substring(38, 68);
		this.sub_class.toObject(trancode.substring(68, 98));
	}
}
