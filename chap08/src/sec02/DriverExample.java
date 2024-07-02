package sec02;

public class DriverExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Driver driver = new Driver();
		
		Bus bus = new Bus();
		Taxi taxi = new Taxi();
		
		//자동형변환이 되어 인수전달
		driver.drive(taxi);
		driver.drive(bus);

	}

}
