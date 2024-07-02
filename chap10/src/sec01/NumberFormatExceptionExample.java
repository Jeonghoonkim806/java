package sec01;

public class NumberFormatExceptionExample {
	public static void main(String[] args) {
		String data1 = "100";   
		String data2 = "a100";  // 문자열 data2 a100을 숫자로 변경 시도
		
		// 문자열 data1 100을 숫자 100으로 변경 시도
		int value1 = Integer.parseInt(data1);
		// 문자열 data2 a100을 숫자로 변경 시도
		int value2 = Integer.parseInt(data2);  //a를 숫자로 바꿀수 없어서 NumberFormatException 발생
		
		int result = value1 + value2;
		System.out.println(data1 + "+" + data2 + "=" + result);
		
	}

}
