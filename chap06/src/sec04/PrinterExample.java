package sec04;

public class PrinterExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Printer printer = new Printer();  //객체 생성
		
		
		printer.println(10);             // 객체 생성없이 메소드 호출
		printer.println(true);
		printer.println(5.7);
		printer.println("홍길동");
	}
}
