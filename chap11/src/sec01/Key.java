package sec01;

public class Key {
	public int number;
	
	//생성자
	public Key(int number) {
		// super(); //생략되어 있음(Object의 기본 생성자)
		this.number = number;
	}
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Key) {
			Key compareKey = (Key)obj; // 강제형변환(casting)
		
			if (this.number == compareKey.number) {
				return true;
		}
		}
		 return false;
	}
		public int hashCode() { //주소값이 hashCode 값이 아니라 number가 hashCode가 되도록 재정의
			return number;
		}
}