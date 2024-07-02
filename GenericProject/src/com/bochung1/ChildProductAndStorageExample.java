package com.bochung1;

public class ChildProductAndStorageExample {

	public static void main(String[] args) {
		ChildProduct<TV, String, String> product
			= new ChildProduct<>();
		product.setKind(new TV());
		product.setModel("SonyTV-65inch");
		product.setCompany("Sony");
		
		Storage<TV> storage = new StorageImpl<>(100);
		storage.add(new TV(), 0);
		TV tv = storage.get(0);
		

	}

}
