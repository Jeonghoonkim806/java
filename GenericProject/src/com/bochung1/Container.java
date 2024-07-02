package com.bochung1;

public class Container<E, A> {
	private E key;
	private A value;
	
	public void set(E key, A value) {
		this.key = key;
		this.value = value;
	}
	
	public E key() {
		return key;
	}
	
	public A value() {
		return value;
	}

}
