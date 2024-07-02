package color;

import java.util.Scanner;

public class ColorProject {
    public static void main(String[] args) {
        System.out.println("게시판 프로그램 시작");
        
        ColorService colorService = new ColorService(); // 게시판 접근 및 제어 객체 생성
        Scanner scanner = new Scanner(System.in);                 // 키보드입력 받는 객체
        
        // 0. 게시판 프로그램 대기를 위해 무한루프 코드 작성
        while(true) {
            // 게시판 제목
            System.out.println("                    << 게시판 >>");
            // 게시판 메뉴
            System.out.println("(1.색상등록, 2.색상목록, 3.상세보기, 4.수정, 5.삭제, 6.종료)>> ");
            
            // 1. 메뉴 선택번호를 입력받기
            int menuNumber = 0;
            try {
                menuNumber = Integer.parseInt(scanner.nextLine());
            } catch(Exception e) {
                System.out.println("잘못된 값을 입력하셨습니다. 다시 입력을 해주세요.");
                continue;
            }
            System.out.println("선택된 번호 -> " + menuNumber);
            
            if (menuNumber < 1 || menuNumber > 6) {
                System.out.println("\n메뉴 번호는 1~6번 사이의 숫자만 가능");
                continue;
            }
            
            switch(menuNumber) {
                case 1: // 글작성 실행
                    System.out.println("(색상등록)");
                    colorService.insertColor();
                    break;
                case 2: // 글목록 실행
                    System.out.println("(색상목록)");
                    colorService.selectAllColors();
                    break;
                case 3: // 상세보기 실행
                    System.out.println("(상세보기)");
                    colorService.selectOneColor();
                    break;
                case 4: // 글수정 실행
                    System.out.println("(수정)");
                    colorService.updateOneColor();
                    break;
                case 5: // 글삭제 실행
                    System.out.println("(삭제)");
                    colorService.deleteOneColor();
                    break;
            }
            if(menuNumber == 6) { // 6번일 때는 무한루프를 빠져나가게 하여 프로그램을 종료
                System.out.println("(종료)");
                break;
            }
        }
        
        // 프로그램 종료 표시
        System.out.println("게시판 프로그램 종료");
    }
}