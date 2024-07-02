package sec02;

public class Car {
	Tire frontLeftTire = new HankookTire();  //인터페이스 자동형변환
	Tire frontRightTire = new HankookTire(); //인터페이스 자동형변환
	Tire backLeftTire = new HankookTire();   //인터페이스 자동형변환  
	Tire backRightTire = new HankookTire();  //인터페이스 자동형변환
	
	void run() {
		frontLeftTire.roll();
		frontRightTire.roll();
		backLeftTire.roll();
		backRightTire.roll();
		
	}

}
