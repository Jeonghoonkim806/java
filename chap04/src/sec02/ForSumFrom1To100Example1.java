package sec02;

public class ForSumFrom1To100Example1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int sum = 0;
		
		for(int i = 1; i <= 100; i++) {
			for (;i % 3 == 0; i++) {
				sum +=i;
			}
		}
		System.out.println(sum);
	}

}
