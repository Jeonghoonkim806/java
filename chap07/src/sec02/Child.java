package sec02;

public class Child extends Parent {
	
	void method3() {
		System.out.println("Child의 method3");
	}
	
	void method2() {
		System.out.println("Child의 method2");
		super.method2(); //부모의 method2 호출
	}

}
