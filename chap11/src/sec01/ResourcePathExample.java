package sec01;

public class ResourcePathExample {

	public static void main(String[] args) {
		Class clazz = Car.class;
		
		String photo1Path = clazz.getResource("Photo1.jpg").getPath();
		String photo2Path = clazz.getResource("images/Photo2.jpg").getPath();
		
		System.out.println(photo1Path);
		System.out.println(photo2Path);
	

	}

}
