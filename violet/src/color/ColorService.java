package color;

import java.sql.*;
import java.util.*;

import color.utils.DBManager;

public class ColorService {
    Scanner scanner = new Scanner(System.in); // 키보드입력 받는 객체

    /**
     * 설명: 색상 정보 작성
     */
    public int insertColor() {
        // 1. 색상 이름 받기
        System.out.print("색상 이름(취소: quit): ");
        String colorName = scanner.nextLine(); // 키보드로 '색상 이름' 받기
        if (colorName.equals("quit")) {
            System.out.println("작성이 취소되었습니다.");
            return -1;
        }

        // 2. 색상 설명 받기
        System.out.print("색상 설명(취소: quit): ");
        String colorDescription = scanner.nextLine(); // 키보드로 '색상 설명' 받기
        if (colorDescription.equals("quit")) {
            System.out.println("작성이 취소되었습니다.");
            return -1;
        }

        // 3. DB 접속 준비
        Connection conn = DBManager.getDBConnection();

        // 4. DB에 데이터를 삽입하기 위한 SQL 작성
        String insertSql = """
                INSERT INTO color_board(seq, color_name, color_description, read_count) 
                VALUES(seq_color_board_no.NEXTVAL, ?, ?, 0)
        """;

        int resultRows = 0;
        PreparedStatement pstmt = null;
        
        try {
        	pstmt = conn.prepareStatement(insertSql);
            pstmt.setString(1, colorName); // 첫 번째 ?에 입력받은 색상 이름 세팅
            pstmt.setString(2, colorDescription); // 두 번째 ?에 입력받은 색상 설명 세팅
            resultRows = pstmt.executeUpdate();
            System.out.println("색상 정보가 성공적으로 등록되었습니다.");
        } catch (SQLException se) {
            System.out.println("색상 정보 삽입 도중 에러 발생 -> " + se.getMessage());
        } finally {
            DBManager.dbClose(conn, pstmt, null);
        }

        return resultRows;
    }

    /**
     * 설명: 색상 목록 조회
     */
    public void selectAllColors() {
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println("번호       색상 이름                           작성일             수정일            조회수  ");
        System.out.println("----------------------------------------------------------------------------------------");

        Connection conn = DBManager.getDBConnection();
        String selectSql = "SELECT seq, color_name, create_date, read_count, update_date FROM color_board ORDER BY seq DESC";
        try (PreparedStatement pstmt = conn.prepareStatement(selectSql);
             ResultSet rs = pstmt.executeQuery()) {
            int countRows = 0;
            while (rs.next()) {
                countRows++;
                System.out.println(
                    String.format("%-10s", rs.getString("seq")) + 
                    String.format("%-33s", rs.getString("color_name")) + 
                    String.format("%-15s", rs.getDate("create_date")) + 
                    String.format("%-15s", rs.getDate("update_date") == null ? "" : rs.getDate("update_date")) +
                    String.format("%10s", rs.getString("read_count"))
                );
            }
            if (countRows == 0) {
                System.out.println("색상 정보가 존재하지 않습니다.");
            }
        } catch (SQLException se) {
            System.out.println("색상 목록 조회 도중 에러 발생 -> " + se.getMessage());
        } finally {
            DBManager.dbClose(conn, null, null);
        }
    }

    /**
     * 설명: 색상 상세 조회
     */
    public void selectOneColor() {
        int colorNo = getColorNo();

        Connection conn = DBManager.getDBConnection();
        String updateSql = "UPDATE color_board SET read_count = read_count + 1 WHERE seq = ?";
        String detailSelectSql = "SELECT * FROM color_board WHERE seq = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
            pstmt.setInt(1, colorNo);
            pstmt.executeUpdate();
            pstmt.close();

            pstmt.setInt(1, colorNo);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("-------------------------------------------------------------");
                    System.out.println("색상 번호: " + rs.getInt(1));
                    System.out.println("색상 이름: " + rs.getString(2));
                    System.out.println("색상 설명: " + rs.getString(3));
                    System.out.println("작성일: " + rs.getDate(4));
                    System.out.println("-------------------------------------------------------------");
                } else {
                    System.out.println("상세 조회할 색상 번호가 없습니다.");
                }
            }
        } catch (SQLException se) {
            System.out.println("색상 상세 조회 쿼리 실행 오류: " + se.getMessage());
        } finally {
            DBManager.dbClose(conn, null, null);
        }
    }

    /**
     * 설명: 색상 삭제
     */
    public void deleteOneColor() {
        int colorNo = getColorNo();
        System.out.print("정말 " + colorNo + "번 색상 정보를 삭제하시겠습니까? (Y/N) ");
        String confirmYN = scanner.nextLine();
        if (!confirmYN.equalsIgnoreCase("Y")) return;

        Connection conn = DBManager.getDBConnection();
        String countSql = "SELECT COUNT(*) FROM color_board WHERE seq = ?";
        String deleteOneSql = "DELETE FROM color_board WHERE seq = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(countSql)) {
            pstmt.setInt(1, colorNo);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    System.out.println("삭제할 색상 번호가 없습니다.");
                    return;
                }
            }

            pstmt.close();
            pstmt.setInt(1, colorNo);
            pstmt.executeUpdate();
            System.out.println("색상 번호 " + colorNo + "를 성공적으로 삭제하였습니다.");
        } catch (SQLException se) {
            System.out.println("색상 삭제 쿼리 실행 오류: " + se.getMessage());
        } finally {
            DBManager.dbClose(conn, null, null);
        }
    }

    /**
     * 설명: 색상 수정
     */
    public void updateOneColor() {
        int colorNo = getColorNo();

        String countSql = "SELECT COUNT(*) FROM color_board WHERE seq = ?";
        Connection conn = DBManager.getDBConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(countSql)) {
            pstmt.setInt(1, colorNo);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    System.out.println("없는 색상 번호입니다.");
                    return;
                }
            }
        } catch (SQLException se) {
            System.out.println("색상 번호 개수 쿼리 실행 오류: " + se.getMessage());
        }

        System.out.print("수정할 색상 이름을 입력하세요: ");
        String updateColorName = scanner.nextLine();
        System.out.print("수정할 색상 설명을 입력하세요: ");
        String updateColorDescription = scanner.nextLine();

        String updateOneSql = "UPDATE color_board SET color_name = ?, color_description = ?, update_date = sysdate WHERE seq = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(updateOneSql)) {
            pstmt.setString(1, updateColorName);
            pstmt.setString(2, updateColorDescription);
            pstmt.setInt(3, colorNo);
            pstmt.executeUpdate();
            System.out.println("색상 번호 " + colorNo + "를 성공적으로 수정하였습니다.");
        } catch (SQLException se) {
            System.out.println("색상 수정 쿼리 실행 오류: " + se.getMessage());
        } finally {
            DBManager.dbClose(conn, null, null);
        }
    }

    /**
     * 설명: 콘솔에서 사용자의 키보드 입력받은 값을 리턴
     * @return 색상 번호
     */
    private int getColorNo() {
        int colorNo = 0;
        while (true) {
            System.out.print("색상 번호를 입력하세요: ");
            try {
                colorNo = Integer.parseInt(scanner.nextLine());
                break;
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("잘못된 값을 입력하셨습니다. 다시 입력해주세요.");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("알 수 없는 오류 -> " + e.getMessage());
            }
        }
        return colorNo;
    }
}
