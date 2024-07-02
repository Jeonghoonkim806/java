package sec07;

public class DmbCellPhoneExample {

	public static void main(String[] args) {
	
		// DmbCellPhone 객체 설정
		DmbCellPhone dmbCellPhone = new DmbCellPhone("자바폰", "검정", 10);
		
		// CellPhone 클래스로부터 상속받은 필드(멤버 변수)
		System.out.println("모델: " + dmbCellPhone.model);
		System.out.println("색상: " + dmbCellPhone.color);
		
		// DmbCellPhone 클래스의 필드
		System.out.println("채널: " + dmbCellPhone.channel);
		
		// CellPhone 클래스로부터 상속받은 메소드 호출
		dmbCellPhone.powerOn();
		dmbCellPhone.bell();
		dmbCellPhone.sendVoice("여보세요.");
		dmbCellPhone.receiveVoice("안녕하세요! 저는 김정훈입니다.");
		dmbCellPhone.sendVoice("아~ 예 반가워요");
		dmbCellPhone.hangUp();
		
		// DmbCellPhone 클래스의 메소드 호출
		dmbCellPhone.turnOnDmb();
		dmbCellPhone.changeChannelDmb(12);
		dmbCellPhone.turnOffDmb();
		
		DmbCellPhone dmbCellPhone2 = new DmbCellPhone();
		
	}
}
