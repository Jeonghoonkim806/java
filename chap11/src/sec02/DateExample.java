package sec02;

import java.util.*;
import java.text.*;

public class DateExample {

	public static void main(String[] args) {
		Date now = new Date();
		String strNow1 = now.toString();
		System.out.println(strNow1);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 mm월 dd일 hh시 mm분 ss초");
		String strNow2 = sdf.format(now);
		System.err.println(strNow2);

		// yyyy-mm-dd hh:mm:ss
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-mm-dd-HH:mm-ss");
		String strNow3 = sdf2.format(now);
		System.out.println(strNow3);

	}

}
