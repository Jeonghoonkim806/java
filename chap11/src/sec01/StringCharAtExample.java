package sec01;

public class StringCharAtExample {

	public static void main(String[] args) {
		String ssn = "012345-2345678";
		char sex = ssn.charAt(7); //인덱스번호가 7번인것의 문자1개 가져오기
		
		switch (sex) {
		case '1':
		case '3':
			System.out.println("남자 입니다.");
			break;
			
		case '2':
		case '4':
			System.out.println("여자 입니다.");
			break;
		}
	}
}
