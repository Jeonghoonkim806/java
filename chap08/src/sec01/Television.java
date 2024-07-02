package sec01;

// implements로 세팅된 interface안의 모든 추상 메소드 들은 반드시 classs에서 구현해야한다.
public class Television implements RemoteControl {
	private int volume; //필드(멤버 변수)
	
	public void turnOn() {
		System.out.println("TV를 킵니다.");
	}
	// RemoteControl에 있는 turnOn메소드
	
	public void turnOff() {
		System.out.println("TV를 끕니다.");
	}
	
	// RemoteControl에 있는 turnOff메소드
	
	public void setVolume(int volume) {
		if(volume > RemoteControl.MAX_VOLUME) {
			this.volume = RemoteControl.MAX_VOLUME;
		} else if(volume < RemoteControl.MIN_VOLUME) {
			this.volume = RemoteControl.MIN_VOLUME;
		} else {
			this.volume = volume;
		}
		System.out.println("현재 TV 볼륨: " + this.volume);
	}
	// RemoteControl에 있는 setVolume메소드

	
}
