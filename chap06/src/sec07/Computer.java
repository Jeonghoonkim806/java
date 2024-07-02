package sec07;

public class Computer extends Calculator {

	@Override // 부모 클레스의 메소드를 재정의 하겠다고 선언
	double areaCircle(double r) {
		System.out.println("Computer 객체의 areaCircle() 싱행");
		return Math.PI * r * r;
	}
	
	
	}

