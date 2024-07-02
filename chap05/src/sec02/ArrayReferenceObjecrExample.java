package sec02;

public class ArrayReferenceObjecrExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] strArray = new String[3];   // {null, null, null}
		strArray[0] = "Java";
		strArray[1] = "Java";
		strArray[2] = new String("Java");
		
		System.out.println( strArray[0] == strArray[1] );      // true
		System.out.println( strArray[0] == strArray[2] );      // 서로 다른 주소값을 가지는 경우 false
		System.out.println( strArray[0].equals(strArray[2]) ); // 값만 비교할 경우에는 true

	}

}
