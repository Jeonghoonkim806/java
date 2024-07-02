package com.hotel;

import java.util.Scanner;

public class HotelReservationSystem {
	
    public static void main(String[] args) {
    	HotelReservationService reservationService = new HotelReservationService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // 메뉴 출력
            System.out.println("\n \n                휴  먼  호  텔 ");
            System.out.println("               [ HOTEL  MENU ]");
            System.out.println("[1.호텔예약][2.예약내역][3.예약정보][4.예약수정][5.예약취소]\n[6.회원가입][7.회원정보][8.회원정보수정][9.회원탈퇴][0.종료]");

            int menuOption = 0;

            try {
                menuOption = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("잘못된 값을 입력하셨습니다. 다시 입력을 해주세요");
                continue; // 다시 메뉴를 입력 받음
            }

            // 각 메뉴 옵션에 따라 기능 실행
            switch (menuOption) {
                case 1:
                    reservationService.addReservation();
                    break;
                case 2:
                    reservationService.viewAllReservations();
                    break;
                case 3:
                    reservationService.viewReservationDetails();
                    break;
                case 4:
                    reservationService.updateReservation();
                    break;
                case 5:
                    reservationService.deleteReservation();
                    break;
                case 6:
                    reservationService.registerCustomer();
                    break;
                case 7:
                    reservationService.viewCustomerInfo();
                    break;
                case 8:
                    reservationService.updateCustomerInfo();
                    break;
                case 9:
                    reservationService.withdrawCustomer();
                    break;
                case 0:
                    System.out.println("호텔 예약 시스템을 종료합니다. 안녕히 가십시오!");
                    scanner.close(); // Scanner 닫기
                    System.exit(0); // 프로그램 종료
                    break;
                default:
                    System.out.println("유효하지 않은 메뉴 옵션입니다. 1부터 6까지의 숫자를 입력해 주세요.");
                    break;
            }
        }
    }
}
