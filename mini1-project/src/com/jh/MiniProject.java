package com.jh;

import java.io.*;
import java.util.*;

public class MiniProject implements Serializable {
    
	private static final long serialVersionUID = 1L;
	private int pno;
	private String name;
	private int price;
	private int stock;
	
	public MiniProject() {
        this.pno = 0;
        this.name = "";
        this.price = 0;
        this.stock = 0;
	}
	
	public int getPno() {
		return pno;
	}
	public void setPno(int pno) {
		this.pno = pno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
}
}
	
	
	
	
	