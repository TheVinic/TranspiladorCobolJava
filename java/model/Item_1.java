package com.trans.transpiladorCobolJava.model.Item_1;

import java.math.BigDecimal;

public class Item_1{
	private Integer key_1 = 0;
	private String[] line_1;
	private String name_1;
	private String unqual_name_1;
	private String sub_1_1;
	private String sub_2_1;
	private String sub_3_1;
	private String contents_1;

	public Integer getKey_1() {
		return key_1;
	}

	public void setKey_1(Integer key_1) {
		this.key_1 = key_1;
	}

	public String[] getLine_1() {
		return line_1;
	}

	public void setLine_1(String[] line_1) {
		this.line_1 = line_1;
	}

	public String getName_1() {
		return name_1;
	}

	public void setName_1(String name_1) {
		this.name_1 = name_1;
	}

	public String getUnqual_name_1() {
		return unqual_name_1;
	}

	public void setUnqual_name_1(String unqual_name_1) {
		this.unqual_name_1 = unqual_name_1;
	}

	public String getSub_1_1() {
		return sub_1_1;
	}

	public void setSub_1_1(String sub_1_1) {
		this.sub_1_1 = sub_1_1;
	}

	public String getSub_2_1() {
		return sub_2_1;
	}

	public void setSub_2_1(String sub_2_1) {
		this.sub_2_1 = sub_2_1;
	}

	public String getSub_3_1() {
		return sub_3_1;
	}

	public void setSub_3_1(String sub_3_1) {
		this.sub_3_1 = sub_3_1;
	}

	public String getContents_1() {
		return contents_1;
	}

	public void setContents_1(String contents_1) {
		this.contents_1 = contents_1;
	}

	public String toTrancode() { 
		return String.format("|0%2d|", key_1) + String.format("|%6d|", line_1) + String.format("|%30d|", name_1) + String.format("|%30d|", unqual_name_1) + String.format("|%5d|", sub_1_1) + String.format("|%5d|", sub_2_1) + String.format("|%5d|", sub_3_1) + String.format("|%30d|", contents_1);
	}

	public void toObject(String trancode) { 
		this.key_1 = trancode.substring(0, 2);
		this.line_1 = trancode.substring(2, 8);
		this.name_1 = trancode.substring(8, 38);
		this.unqual_name_1 = trancode.substring(38, 68);
		this.sub_1_1 = trancode.substring(68, 73);
		this.sub_2_1 = trancode.substring(73, 78);
		this.sub_3_1 = trancode.substring(78, 83);
		this.contents_1 = trancode.substring(83, 113);
	}
}