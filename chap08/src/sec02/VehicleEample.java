package sec02;

public class VehicleEample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Vehicle vehicle = new Bus();
		vehicle.run(); //버스가 달립니다.
	//	vehicle.checkFare(); //error -> Vehicle 인터페이스에는 cherFare메소드가 없음
		
		Bus bus = (Bus)vehicle; // 강제형변환(casting)
		bus.run();
		bus.checkFare(); // Bus에서 checkFare메소드가 존재하므로 실행가능

	}

}
