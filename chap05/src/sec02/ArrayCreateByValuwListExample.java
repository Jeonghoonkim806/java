package sec02;

public class ArrayCreateByValuwListExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] scores = { 83, 90, 87 };
		
		System.out.println("score[0] : " + scores[0]);
		System.out.println("score[0] : " + scores[1]);
		System.out.println("score[0] : " + scores[2]);
		
		scores[2] = 88; // 87 -> 88
		
		int sum = 0;
		for(int i = 0; i< scores.length; i++) {
			sum += scores[i];
		}
		System.out.println("총합 : " + sum);
		double avg = (double) sum / 3;
		System.out.println("평균 : " + avg);

	}

}
