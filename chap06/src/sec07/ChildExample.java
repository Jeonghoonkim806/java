package sec07;

public class ChildExample {
	
	public static void main(String[] args) {
		
		Child child = new Child();
		
		child.method1(); //Parent method1
		child.method2(); //Child method2
		child.method3(); //Child method3
		
		Parent parent = new Parent();
		parent.method2(); //Parent method2
	}

}
