package sec02;



public class exam0524 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/* 문제 2
		int sum = 0;
		for (int i = 1; i < 101; i++) {
			// 3의 배수는 나머지가 3으로 나우었을 때 나머지가 0인 숫자
			if (i % 3 == 0) {
				sum = sum + i;
			}
		}
		System.out.println("3의 배수의 합 -> " + sum);
		*/
		
		/* 문제3
		while(true) {
			int num1 = (int)(Math.random() * 6) + 1;  //1~6
			int num2 = (int)(Math.random() * 6) + 1;		
			
			System.out.println("(" + num1 + ", " + num2 + ")");
			if (num1 + num2 == 5) {
				break;
			}
		*/ 
		
		
		/* 문제4
		 for (int x = 1; x < 11; x++) {
			 for (int y = 1; y < 11; y++) {
				 if ((4 * x + 5 * y) == 60) {
					 System.out.println("(" + x + ", " + y + ")");
				 }
		*/
		
		/* 문제6
		for(int i = 1; i < 5; i++) {
			for(int j = 4; j > 0; j--) {
				if (i < j) {
					System.out.print(" ");
				} else {
					System.out.print("*");
				}
			}
			System.out.println();
		*/
		
		boolean run = true;
        int balance = 0;
        Scanner scanner = new Scanner(System.in);

        while (run) {
            System.out.println("--------------------------");
            System.out.println("1. 예금 | 2. 출금 | 3. 잔고 | 4. 종료");
            System.out.println("--------------------------");
            System.out.print("선택 > ");

            int menuNumber = Integer.parseInt(scanner.nextLine());
            
            switch(menuNumber) {
            	case 1:
            		System.out.println("예금액> ");
            		balance = balance + Integer.parseInt(scanner.nextLine());
            		break;
            	case 2:
            		System.out.println("출금액> ");
            		balance = balance - Integer.parseInt(scanner.nextLine());
            		break;
            	case 3:	
            		System.out.println("잔고> " + balance);
            		break;
            	case 4:
            		run = false;
            		break;
            }
            System.out.println();
		}
        System.out.println("프로그램 종료");
	}
}		 
	
			 
	
		
	



