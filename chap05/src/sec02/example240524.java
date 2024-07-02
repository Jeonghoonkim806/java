package sec02;

public class example240524 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
/*		int max = 0;
		int[] array = { 1, 5, 3, 8, 2};
		
		for (int i = 0; i < array.length; i++) {
			if (array[i] > max) {
				max = array[i];
			}
		}
		
		System.out.println("max: " + max);
*/	
	
		int[][] array = {
				{95, 86},
				{83, 92, 96},
				{78, 83, 93, 87, 88}
		};
		int sum = 0;
		double avg = 0.0;
		
		int count = 0;
		for (int i = 0; i <array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				sum += array[i][j];
				count ++;
			}
			double avg = (double) sum / count;
		}
		System.out.println("sum: " + sum);
		System.out.println("avg: " + avg);

		
		
	}

}
