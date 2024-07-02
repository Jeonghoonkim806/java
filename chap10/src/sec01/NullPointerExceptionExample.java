package sec01;

public class NullPointerExceptionExample {
	public static void main(String[] args) {
	//	String data = "test";
		String data = null;
		System.out.println(data.toString());  //NullPointerException 발생
	}

}
