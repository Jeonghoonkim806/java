package sec02.casting;

public class InstanceOfExample {
	// 부모 클레스를 자식 클레스 강제형변환을 안전하게 리턴하는 메소드
	public static Child method1(Parent parent) {
		Child child = null;
		
		if(parent instanceof Child) {
			child = (Child) parent; // 강제형변환(casting)
			System.out.println("method1 - Child로 변환 성공");
		} else {
			System.out.println("method1 - Child로 변환되지 않음");
		}
		
		return child;
}
	// 부모 클레스를 자식 클래스로 강제형변환을 불안전하게 리턴하는 메소드
	public static Child method2(Parent parent) {
		
		Child child = (Child) parent;
		System.out.println("method2 - Child로 변환 성공");
		
		return child;
	}
	public static void main(String[] args) {
		Parent parentA = new Child();
		method1(parentA);
		method2(parentA);
		
		//method1(new Child());    // instanceof -> true
		//method1(new Parent());   // instanceof -> false
		
		Parent parentB = new Parent();
		method1(parentB);
		method2(parentB);  // ClassCastException이 발생됨
		
		
	}

}
