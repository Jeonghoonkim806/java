package sec04;

public class ClassName {
	int method1(int x, int y) {
		int result = x + y;
		return result;
	}
	
	void mrethod2() {
		int result = method1(10, 20); //내부 메소드 (method1) 호출
		double result2 = method1(10, 20); //내부 메소드 (method1) 호출
		
//		return result;
	}

}
