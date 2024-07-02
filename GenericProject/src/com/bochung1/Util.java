package com.bochung1;

// 제너릭 메소드 사용 예제
public class Util {
	public static <T> BoxGeneric<T> boxing(T t) {
		BoxGeneric<T> boxGeneric = new BoxGeneric<T>();
		boxGeneric.set(t);
		
		return boxGeneric;
	}
	
	//T로 될수있는 타입은 숫자로 된 타입으로 제한
	public static <T extends Number> int compare(T t1, T t2) {
		double v1 = t1.doubleValue();
		double v2 = t2.doubleValue();
		
		return Double.compare(v1, v2);
	}
}
