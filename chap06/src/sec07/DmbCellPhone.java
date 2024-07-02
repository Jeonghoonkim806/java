package sec07;

public class DmbCellPhone extends CellPhone {
	//필드
	int channel;
	
	public DmbCellPhone() {
		super(); // CellPhone의 기본생성자 호출
	}
	
	//생성자
	DmbCellPhone(String model, String color, int channel) {
		// this.model은 CellPhone에 있는 모델
		// 뒤의 model은 매개변수 model
	//	super(); // 해당 코드가 없어도 자동으로 호출
		
		this.model = model;
		this.color = color;
		this.channel = channel;
	}
	
	//메소드
	void turnOnDmb() {
		System.out.println("채널 " + channel + "번 DMB 방송 수신을 시작합니다.");
	}
	
	void changeChannelDmb(int channel) {
		this.channel = channel;
		System.out.println("채널 " + channel + "번으로 바꿉니다.");
	}
	void turnOffDmb() {
		System.out.println("DMB 방송 수신을 멈춥니다.");
	}

}
