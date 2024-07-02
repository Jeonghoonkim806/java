package com.bochung1;

//<T>는 제너릭(generic)
//T는 어떠한 타입도 가능 (ex) int, short, Class etc...)
public class BoxGeneric<A> {
	private A t;
	
	public A get() {
		return this.t;
	}
	
	public void set(A t) {
		this.t = t;
		
	}
}
