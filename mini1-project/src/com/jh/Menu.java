package com.jh;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Menu {

	private Scanner scanner = new Scanner(System.in);
	private int counter = 0;
	private List<MiniProject> list = new ArrayList<>();
	private static final String OpenFile = "miniproject.dat";
	
	public Menu() {
        try {
            qwerList();
        } catch (Exception e) {
            System.out.println("파일 읽기 오류: " + e.getMessage());
        }
	}
	

	public void showMenu() {
		while (true) {
			System.out.println("----------------------------------------------------------");
			System.out.println("1. 담배등록 | 2.담배목록 | 3.담배수정 | 4.담배삭제 | 5.담배저장 | 6.종료");
			System.out.println("----------------------------------------------------------");
			System.out.print("선택: ");
			String selectNo = scanner.nextLine();
			switch (selectNo) {
				case "1": registerMiniProject(); break;
				case "2": showMiniProject(); break;
				case "3": modifyMiniProject(); break;
				case "4": deleteMiniProject(); break;
				case "5": saveMiniProject(); break;
				case "6": saveMiniProject(); return;
				default: System.out.println("잘못된 선택입니다.");
			}
		}
	}

	public void registerMiniProject() {
		try {
			System.out.print("상품명: ");
			String name = scanner.nextLine();

			System.out.print("가격: ");
			int price = Integer.parseInt(scanner.nextLine());

			System.out.print("수량: ");
			int stock = Integer.parseInt(scanner.nextLine());
			
			boolean exists = false;
			for (MiniProject miniproject : list) {
				if (miniproject.getName().equals(name)) {
					miniproject.setPrice(price);
					miniproject.setStock(miniproject.getStock() + stock);
					exists = true;
					break;
				}
			}
			    if (!exists) {
	                MiniProject miniproject = new MiniProject();
	                miniproject.setPno(++counter);
	                miniproject.setName(name);
	                miniproject.setPrice(price);
	                miniproject.setStock(stock);
	                list.add(miniproject);
			 }
		} catch (Exception e) {
			System.out.println("등록 에러: " + e.getMessage());
		}
	}

	public void showMiniProject() {
		for (MiniProject p : list) {
			System.out.println(p.getPno() + "\t 제품명: " + p.getName() + "\t 가격: " + 
							   p.getPrice() + "\t 수량: " + p.getStock());
		}
	}

	public void deleteMiniProject() {
		System.out.print("삭제할 담배이름: ");
		String name = scanner.nextLine();
		Iterator<MiniProject> iterator = list.iterator();
		while (iterator.hasNext()) {
			MiniProject project = iterator.next();
			if (project.getName().equals(name)) {
				iterator.remove();
				System.out.println("삭제 완료.");
				return;
			}
		}
		System.out.println("상품을 찾을 수 없습니다.");
	}

	public void modifyMiniProject() {
		System.out.print("수정할 담배이름: ");
		String name = scanner.nextLine();
		for (MiniProject project : list) {
			if (project.getName().equals(name)) {
				try {
					System.out.print("새 가격: ");
					project.setPrice(Integer.parseInt(scanner.nextLine()));

					System.out.print("새 재고: ");
					project.setStock(Integer.parseInt(scanner.nextLine()));

					System.out.println("수정 완료.");
					return;
				} catch (Exception e) {
					System.out.println("수정 에러: " + e.getMessage());
					return;
				}
			}
		}
		System.out.println("상품을 찾을 수 없습니다.");
	}

	public void saveMiniProject() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(OpenFile))) {
            outputStream.writeObject(list);
            System.out.println("저장 완료.");
        } catch (IOException e) {
            System.out.println("저장 에러: " + e.getMessage());
        }
    }

    public void qwerList() throws Exception {
        File file = new File(OpenFile);
        if (file.exists()) {
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(OpenFile))) {
                list = (List<MiniProject>) inputStream.readObject();
    }               
    }
}

	public static void main(String[] args) {
		Menu menu = new Menu();
		menu.showMenu();
	}
}