package sec02.casting;

public class ChildExample {

	public static void main(String[] args) {
		
		Parent parent = new Child(); //자동 형변환
		parent.field1 = "data1";
		parent.method1();
		parent.method2();
		
		/*
		 parent.field2 = "data2";  /error
		 parent.method3();         /error
		 */
			
		Child child = (Child) parent; //강제형변환(casting)
		child.field2 = "yyy";
		child.method3();
		
		
	/*	Parent parent2 = new Parent();
		Child child2 = (Child) parent2;
		child2.field2 = "yyy";
		child2.method3();   // runtime error
*/
	}

}
