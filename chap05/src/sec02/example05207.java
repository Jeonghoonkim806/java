package sec02;

import java.util.Scanner;

public class example05207 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		boolean run = true;
		int studentNum = 0;
		int[] scores = null;
		Scanner scanner = new Scanner(System.in);
		
		while(run) {
			System.out.println("--------------------------------------");
			System.out.println("1.학생수 | 2.점수입력 | 3. 점수리스트 | 4.분석 | 5.종료");
			System.out.println("선택 > ");
			
			int selectNo = Integer.parseInt(scanner.nextLine());
			
			if (selectNo == 1) {
				System.out.println("학생수 > ");
				studentNum = Integer.parseInt(scanner.nextLine());
				scores = new int[studentNum];
			} else if (selectNo == 2) {
				for (int i = 0; i < scores.length; i++) {
					System.out.println("scores[" + i + "]>"); 
					scores[i] = Integer.parseInt(scanner.nextLine());
				}
			} else if (selectNo == 3) {
				for (int i = 0; i < scores.length; i++) {
					System.out.println("scores[" + i + "] : " + scores[i]); 
				}
			} else if (selectNo == 4) {
				int 최고점수 = 0;
				double 평균점수 = 0.0;
				
				int sum = 0;
				for (int i = 0; i < scores.length; i++) {
					if (최고점수 < scores[i]) {
						최고점수 = scores[i];
					}
			//		최고점수 = 최고점수 < scores[i] ? scores[i] : 최고점수;
					sum += scores[i];
				}
				평균점수 = sum / (double)studentNum;
				
				System.out.println("최고 점수: " + 최고점수);
				System.out.println("평균 점수: " + 평균점수);
				
			} else if(selectNo == 5) {
			    run = false;	
			}
		}
		System.out.println("프로그램 종료");

	}

}
