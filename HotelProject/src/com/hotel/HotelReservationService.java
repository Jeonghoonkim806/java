package com.hotel;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.hotel.utils.DBManager;

public class HotelReservationService {
	private static final Scanner scanner = new Scanner(System.in);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH");

    private boolean isCustomerExist(String customerId) {
        Connection conn = DBManager.getDBConnection();
        String selectSql = "SELECT * FROM customers WHERE customer_id = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(selectSql);
            pstmt.setString(1, customerId);
            rs = pstmt.executeQuery();
            return rs.next(); 
        } catch (SQLException se) {
            System.out.println("회원 확인 중 오류 발생: " + se.getMessage());
            return false;
        } finally {
            DBManager.dbClose(conn, pstmt, rs);
        }
    }

    private String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber.length() == 10) {
            return phoneNumber.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "$1-$2-$3");
        } else if (phoneNumber.length() == 11) {
            return phoneNumber.replaceFirst("(\\d{3})(\\d{4})(\\d+)", "$1-$2-$3");
        }
        return phoneNumber; 
    }
    
    private int getReservationId() {
    	System.out.print("예약 번호: ");
        int reservationId = -1;
        try {
            reservationId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("유효하지 않은 예약 ID입니다. 유효한 정수를 입력하세요.");
        }
        return reservationId;
    }
    
    
    private boolean isRoomAlreadyReserved(int roomNumber, java.util.Date checkInDate, java.util.Date checkOutDate) {
        Connection conn = DBManager.getDBConnection();
        String selectSql = """
                SELECT COUNT(*) AS count
                FROM reservations
                WHERE room_number = ? AND (
                       (check_in_date < ? AND check_out_date > ?) OR
                       (check_in_date < ? AND check_out_date > ?) OR
                       (check_in_date >= ? AND check_out_date <= ?)
                )
        """;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean isReserved = false;

        try {
            pstmt = conn.prepareStatement(selectSql);
            pstmt.setInt(1, roomNumber);
            pstmt.setDate(2, new java.sql.Date(checkOutDate.getTime()));
            pstmt.setDate(3, new java.sql.Date(checkInDate.getTime()));
            pstmt.setDate(4, new java.sql.Date(checkOutDate.getTime()));
            pstmt.setDate(5, new java.sql.Date(checkInDate.getTime()));
            pstmt.setDate(6, new java.sql.Date(checkInDate.getTime()));
            pstmt.setDate(7, new java.sql.Date(checkOutDate.getTime()));
            rs = pstmt.executeQuery();

            if (rs.next()) {
                isReserved = rs.getInt("count") > 0;
            }
        } catch (SQLException se) {
            System.out.println("예약 중복 확인 중 오류 발생: " + se.getMessage());
        } finally {
            DBManager.dbClose(conn, pstmt, rs);
        }

        return isReserved;
    }
    
//------------------------------------------------------------------------------------------------------
//  회원가입
    public void registerCustomer() {
    	
    
    	String customerId;
        while (true) {
            System.out.print("Customer ID: ");
            customerId = scanner.nextLine();
            if (isCustomerExist(customerId)) {
                System.out.println("이미 존재하는 Customer ID입니다. 다른 ID를 입력하세요.");
            } else {
                break;
            }
        }
    
        System.out.print("Customer Name: ");
        String customerName = scanner.nextLine();

        String email;
        while (true) {
            System.out.print("Email: ");
            email = scanner.nextLine();
            if (!email.contains("@")) {
                System.out.println("유효하지 않은 이메일 형식입니다. '@' 기호를 포함한 이메일을 입력하세요.");
            } else {
                break;
            }
        }

        String phoneNumber;
        while (true) {
            System.out.print("Phone Number: ");
            phoneNumber = scanner.nextLine();
            if (!phoneNumber.matches("\\d+")) {
                System.out.println("유효하지 않은 전화번호입니다. 숫자만 입력하세요.");
            } else if (phoneNumber.length() != 10 && phoneNumber.length() != 11) {
                System.out.println("전화번호는 10자리 또는 11자리 숫자로 입력하세요.");
            } else {
                break;
            }
        }

        Connection conn = DBManager.getDBConnection();
        String insertSql = """
                INSERT INTO customers(customer_id, customer_name, email, phone_number) 
                VALUES (?, ?, ?, ?)
        """;

        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(insertSql);
            pstmt.setString(1, customerId);
            pstmt.setString(2, customerName);
            pstmt.setString(3, email);
            pstmt.setString(4, phoneNumber);

            int insertedRows = pstmt.executeUpdate();

            if (insertedRows > 0) {
                System.out.println("회원가입 완료됬습니다.");
            } else {
                System.out.println("등록 실패하였습니다.");
            }
        } catch (SQLException se) {
            System.out.println("등록 중 오류 발생 하였습니다.: " + se.getMessage());
        } finally {
            DBManager.dbClose(conn, pstmt, null);
        }
    }
    
    
//---------------------------------------------------------------------------------------------------------------------
//  회원정보
    public void viewCustomerInfo() {
        System.out.print("Customer ID: ");
        String customerId = scanner.nextLine();

        Connection conn = DBManager.getDBConnection();
        String selectSql = """
                SELECT customer_id, customer_name, email, phone_number
                FROM customers
                WHERE customer_id = ?
        """;

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(selectSql);
            pstmt.setString(1, customerId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                String formattedPhoneNumber = formatPhoneNumber(rs.getString("phone_number"));
                System.out.println("Customer ID: " + rs.getString("customer_id"));
                System.out.println("Customer Name: " + rs.getString("customer_name"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Phone Number: " + formattedPhoneNumber);
            } else {
                System.out.println("해당 ID의 고객 정보가 없습니다.");
            }
        } catch (SQLException se) {
            System.out.println("조회 중 오류 발생: " + se.getMessage());
        } finally {
            DBManager.dbClose(conn, pstmt, rs);
        }
    }
//--------------------------------------------------------------------------------------------------------
//  회원정보 수정
    public void updateCustomerInfo() {
        while (true) {
            System.out.print("Customer ID: ");
            String customerId = scanner.nextLine();

            Connection conn = DBManager.getDBConnection();
            String selectSql = "SELECT * FROM customers WHERE customer_id = ?";
            String updateSql = "UPDATE customers SET customer_name = ?, email = ?, phone_number = ? WHERE customer_id = ?";
            PreparedStatement selectStmt = null;
            PreparedStatement updateStmt = null;
            ResultSet rs = null;

        try {
            selectStmt = conn.prepareStatement(selectSql);
            selectStmt.setString(1, customerId);
            rs = selectStmt.executeQuery();

        if (rs.next()) {
                   
           System.out.println("\n 현재 고객 정보:");
           System.out.println("Customer ID: " + rs.getString("customer_id"));
           System.out.println("Customer Name: " + rs.getString("customer_name"));
           System.out.println("Email: " + rs.getString("email"));
           System.out.println("Phone Number: " + formatPhoneNumber(rs.getString("phone_number")));

                    // 새로운 정보 입력
           System.out.println("새로운 정보를 입력하세요. (변경하지 않을 항목은 엔터를 누르세요)");
           System.out.print("New Customer Name: ");
           String newCustomerName = scanner.nextLine().trim();
           if (newCustomerName.isEmpty()) {
               newCustomerName = rs.getString("customer_name");
               }

           String newEmail;
    while (true) {
           System.out.print("New Email: ");
           newEmail = scanner.nextLine().trim();
       if (newEmail.isEmpty()) {
           newEmail = rs.getString("email");
           break; 
          } else if (!newEmail.contains("@")) {
                System.out.println("유효하지 않은 이메일 형식입니다. '@' 기호를 포함한 이메일을 입력하세요.");
          } else {
           break; 
          }
          }
                    
          String newPhoneNumber;
     while (true) {
          System.out.print("New Phone Number: ");
          newPhoneNumber = scanner.nextLine().trim();
       if (newPhoneNumber.isEmpty()) {
           newPhoneNumber = rs.getString("phone_number");
           break; 
         } else if (!newPhoneNumber.matches("\\d+")) {
                  System.out.println("유효하지 않은 전화번호입니다. 숫자만 입력하세요.");
         } else if (newPhoneNumber.length() != 10 && newPhoneNumber.length() != 11) {
                  System.out.println("전화번호는 10자리 또는 11자리 숫자로 입력하세요.");
         } else {
                  newPhoneNumber = formatPhoneNumber(newPhoneNumber); // 전화번호 포맷 변경
                  break; 
          }
          }
         
            updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setString(1, newCustomerName);
            updateStmt.setString(2, newEmail);
            updateStmt.setString(3, newPhoneNumber);
            updateStmt.setString(4, customerId);

            int updatedRows = updateStmt.executeUpdate();

            if (updatedRows > 0) {
               System.out.println("고객 정보가 업데이트 되었습니다.");
            } else {
               System.out.println("고객 정보 업데이트 실패하였습니다.");
            } break; 
            
            } else {
               System.out.println("해당 ID의 고객 정보가 없습니다. 다시 입력하세요.");
            }
            } catch (SQLException se) {
                System.out.println("고객 정보 업데이트 중 오류 발생: " + se.getMessage());
            } finally {
                DBManager.dbClose(conn, selectStmt, rs);
                DBManager.dbClose(null, updateStmt, null);
            }
        }
    }
//-------------------------------------------------------------------------------------------------------
//  회원탈퇴
    public void withdrawCustomer() {
        System.out.println("회원 탈퇴를 진행합니다.");
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            // 고객 정보 입력 받기
            System.out.print("Customer ID: ");
            String customerId = scanner.nextLine();

            System.out.print("이메일: ");
            String email = "";
            while (true) {
                email = scanner.nextLine();
                if (email.contains("@")) {
                    break; // 올바른 이메일 형식이면 입력 완료
                } else {
                    System.out.println("유효하지 않은 이메일 형식입니다. '@' 기호를 포함한 이메일을 입력하세요.");
                    System.out.print("이메일: ");
                }
            }

            System.out.print("핸드폰 번호: ");
            String phoneNumber = "";
            while (true) {
                phoneNumber = scanner.nextLine();
                if (phoneNumber.matches("\\d+") && (phoneNumber.length() == 10 || phoneNumber.length() == 11)) {
                    break; // 올바른 전화번호 형식이면 입력 완료
                } else {
                    System.out.println("유효하지 않은 전화번호입니다. 숫자만 입력하고, 10자리 또는 11자리로 입력하세요.");
                    System.out.print("핸드폰 번호: ");
                }
            }

            // 데이터베이스 연결 및 고객 정보 확인 및 삭제
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;

            try {
            	conn = DBManager.getDBConnection();
            	String selectSql = "SELECT * FROM customers WHERE customer_id = ? AND email = ? AND phone_number = ?";
                pstmt = conn.prepareStatement(selectSql);
                pstmt.setString(1, customerId);
                pstmt.setString(2, email);
                
                //phoneNumber = phoneNumber.substring(0, 2) + "-" + "";
                pstmt.setString(3, phoneNumber);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    // 일치하는 고객 정보가 있으면 삭제 진행
                	String deleteSql = "DELETE FROM customers WHERE customer_id = ?";
                	pstmt = conn.prepareStatement(deleteSql);
                	pstmt.setString(1, customerId);
                    int deletedRows = pstmt.executeUpdate();

                    if (deletedRows > 0) {
                        System.out.println("회원 탈퇴가 완료되었습니다.");
                    } else {
                        System.out.println("회원 탈퇴 실패하였습니다.");
                    }
                    break; 
                } else {
                    System.out.println("고객 정보가 일치하지 않습니다. 다시 입력해주세요.");
                }
            } catch (SQLException se) {
                System.out.println("회원 탈퇴 중 오류 발생: " + se.getMessage());
            } finally {
              
                try {
                    if (rs != null) rs.close();
                    if (pstmt != null) pstmt.close();
                    if (conn != null) conn.close();
                } catch (SQLException e) {
                    System.out.println("자원 해제 중 오류 발생: " + e.getMessage());
                }
            }
        }
    }


//----------------------------------------------------------------------------------------------
//  예약
    public int addReservation() {
        System.out.print("Guest Name: ");
        String guestName = scanner.nextLine();

        System.out.print("Customer ID: ");
        String guestId = scanner.nextLine();

        if (!isCustomerExist(guestId)) {
            System.out.println("존재하지 않는 Customer ID입니다. 회원가입 후 다시 예약해주세요.");
            return -1;
        }

        int roomNumber = 0;
        while (true) {
            try {
                System.out.print("Room Number (1~30): ");
                roomNumber = Integer.parseInt(scanner.nextLine());

                if (roomNumber >= 1 && roomNumber <= 30) {
                    break;
                } else {
                    System.out.println("1~30 사이의 방 번호를 입력하세요.");
                }
            } catch (NumberFormatException e) {
                System.out.println("유효하지 않은 방 번호입니다. 다시 입력하세요.");
            }
        }

        boolean isShortStay = false;
        while (true) {
            System.out.print("대실 예약을 하시겠습니까? (Y/N): ");
            String shortStayInput = scanner.nextLine().trim(); // 입력값 앞뒤 공백 제거

            if (shortStayInput.equalsIgnoreCase("Y")) {
                isShortStay = true;
                break;
            } else if (shortStayInput.equalsIgnoreCase("N")) {
                break;
            } else {
                System.out.println("유효하지 않은 입력입니다. Y 또는 N을 입력해주세요.");
            }
        }

        if (isShortStay) {
            Date checkInDate = null;
            // 대실 예약일 경우 체크인 시간 입력
            while (true) {
                System.out.print("Check-in Date and Time (YYYY-MM-DD HH): ");
                String checkIn = scanner.nextLine();
                try {
                    checkInDate = dateFormat2.parse(checkIn);
                    if (!checkIn.equals(dateFormat2.format(checkInDate))) {
                        throw new ParseException("입력 형식 오류", 0);
                    }
                    break;
                } catch (ParseException e) {
                    System.out.println("유효하지 않은 날짜 형식입니다. YYYY-MM-DD HH 형식으로 다시 입력해주세요.");
                }
            }
           

            // 체크아웃 시간 설정 (체크인 시간으로부터 6시간 후)
            Calendar checkOutCalendar = Calendar.getInstance();
            checkOutCalendar.setTime(checkInDate);
            checkOutCalendar.add(Calendar.HOUR_OF_DAY, 6);
            Date checkOutDate = checkOutCalendar.getTime();

            // 예약 가능 여부 확인
            if (isRoomAlreadyReserved(roomNumber, checkInDate, checkOutDate)) {
                System.out.println("해당 방은 이미 예약되었습니다.");
                return -1;
            }

            // 데이터베이스에 예약 추가
            Connection conn = DBManager.getDBConnection();
            String insertSql = "INSERT INTO reservations(guest_id, guest_name, room_number, check_in_date, check_out_date, reservation_type, check_in_time, check_out_time) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            int resultRows = 0;
            PreparedStatement pstmt = null;

            try {
                pstmt = conn.prepareStatement(insertSql);
                pstmt.setString(1, guestId);
                pstmt.setString(2, guestName);
                pstmt.setInt(3, roomNumber);
                pstmt.setTimestamp(4, new Timestamp(checkInDate.getTime()));
                pstmt.setTimestamp(5, new Timestamp(checkOutDate.getTime()));
                pstmt.setString(6, "대실");
                pstmt.setTimestamp(7, new Timestamp(checkInDate.getTime()));
                pstmt.setTimestamp(8, new Timestamp(checkOutDate.getTime()));
                resultRows = pstmt.executeUpdate();
                System.out.println("예약 되었습니다.");
            } catch (SQLException se) {
                System.out.println("예약 추가 실패하였습니다.: " + se.getMessage());
                return -1;
            } finally {
                DBManager.dbClose(conn, pstmt, null);
            }

            return resultRows;

        } else {
            // 대실 예약을 하지 않은 경우
            Date checkInDate = null;
            // 일반 예약일 경우 체크인 시간 입력
            while (true) {
                System.out.print("Check-in Date (YYYY-MM-DD): ");
                String checkIn = scanner.nextLine();
                try {
                    checkInDate = dateFormat.parse(checkIn);
                    if (!checkIn.equals(dateFormat.format(checkInDate))) {
                        throw new ParseException("입력 형식 오류", 0);
                    }
                    break;
                } catch (ParseException e) {
                    System.out.println("유효하지 않은 날짜 형식입니다. YYYY-MM-DD 형식으로 다시 입력해주세요.");
                }
            }

            // 체크아웃 시간 입력
            Date checkOutDate = null;
            while (true) {
                System.out.print("Check-out Date (YYYY-MM-DD): ");
                String checkOut = scanner.nextLine();
                try {
                    checkOutDate = dateFormat.parse(checkOut);
                    if (!checkOut.equals(dateFormat.format(checkOutDate))) {
                        throw new ParseException("입력 형식 오류", 0);
                    }
                    if (checkOutDate.before(checkInDate)) {
                        System.out.println("체크아웃 날짜는 체크인 날짜 이후여야 합니다. 다시 입력해주세요.");
                    } else {
                        break;
                    }
                } catch (ParseException e) {
                    System.out.println("유효하지 않은 날짜 형식입니다. YYYY-MM-DD 형식으로 다시 입력해주세요.");
                }
            }

            // 예약 가능 여부 확인
            if (isRoomAlreadyReserved(roomNumber, checkInDate, checkOutDate)) {
                System.out.println("해당 방은 이미 예약되었습니다.");
                return -1;
            }

            // 데이터베이스에 예약 추가
            Connection conn = DBManager.getDBConnection();
            String insertSql = "INSERT INTO reservations(guest_id, guest_name, room_number, check_in_date, check_out_date, reservation_type) VALUES(?, ?, ?, ?, ?, ?)";
            int resultRows = 0;
            PreparedStatement pstmt = null;

            try {
                pstmt = conn.prepareStatement(insertSql);
                pstmt.setString(1, guestId);
                pstmt.setString(2, guestName);
                pstmt.setInt(3, roomNumber);
                pstmt.setTimestamp(4, new Timestamp(checkInDate.getTime()));
                pstmt.setTimestamp(5, new Timestamp(checkOutDate.getTime()));
                pstmt.setString(6, "일반 예약");
                resultRows = pstmt.executeUpdate();
                System.out.println("예약 되었습니다.");
            } catch (SQLException se) {
                System.out.println("예약 추가 실패하였습니다.: " + se.getMessage());
                return -1;
            } finally {
                DBManager.dbClose(conn, pstmt, null);
            }

            return resultRows;
        }
    }

    
    
//----------------------------------------------------------------------------------------------
//  예약내역
    public void viewAllReservations() {
        Connection conn = DBManager.getDBConnection();
        String selectSql = "SELECT reservation_id, guest_id, guest_name, room_number, check_in_date, check_out_date, reservation_type, check_in_time, check_out_time FROM reservations ORDER BY reservation_id DESC";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(selectSql);
            rs = pstmt.executeQuery();
            System.out.println("----------------------------------------------------------------------------------------------------------");
            System.out.printf("%-10s %-10s %-15s %-15s %-17s %-17s %-15s\n",
                    "예약번호", "ID", "Guest Name", "Room Number", "Check-in", "Check-out", "Type");
            System.out.println("----------------------------------------------------------------------------------------------------------");

            while (rs.next()) {
                int reservationId = rs.getInt("reservation_id");
                String guestId = rs.getString("guest_id");
                String guestName = rs.getString("guest_name");
                int roomNumber = rs.getInt("room_number");
                String reservationType = rs.getString("reservation_type");

                if ("대실".equals(reservationType)) {
                    Timestamp checkInTimestamp = rs.getTimestamp("check_in_time");
                    Timestamp checkOutTimestamp = rs.getTimestamp("check_out_time");
                    Date checkInDate = new Date(checkInTimestamp.getTime());
                    Date checkOutDate = new Date(checkOutTimestamp.getTime());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                    System.out.printf("%-12d %-12s %-15s %-12d %-17s %-17s %-15s\n",
                            reservationId, guestId, guestName, roomNumber,
                            dateFormat.format(checkInDate), dateFormat.format(checkOutDate), "대실");
                } else {
                    Date checkInDate = rs.getDate("check_in_date");
                    Date checkOutDate = rs.getDate("check_out_date");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                    System.out.printf("%-12d %-12s %-15s %-12d %-17s %-17s %-15s\n",
                            reservationId, guestId, guestName, roomNumber,
                            dateFormat.format(checkInDate), dateFormat.format(checkOutDate), "일반");
                }
            }
        } catch (SQLException se) {
            System.out.println("예약 보기 중 오류 발생: " + se.getMessage());
        } finally {
            DBManager.dbClose(conn, pstmt, rs);
        }
    }
    
//----------------------------------------------------------------------------------------------
//  예약정보
    public void viewReservationDetails() {
        int reservationId = getReservationId();
        if (reservationId == -1) {
            return;
        }

        Connection conn = DBManager.getDBConnection();
        String selectSql = "SELECT * FROM reservations WHERE reservation_id = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(selectSql);
            pstmt.setInt(1, reservationId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("-------------------------------------------------------------");
                System.out.println("Reservation Number: " + rs.getString("reservation_id"));
                System.out.println("Guest Name: " + rs.getString("guest_name"));
                System.out.println("Room Number: " + rs.getInt("room_number"));
                String reservationType = rs.getString("reservation_type");
                if (reservationType.equals("대실")) {
                    System.out.println("Type: 대실");
                    System.out.println("Check-in Time: " + rs.getTime("check_in_time"));
                    System.out.println("Check-out Time: " + rs.getTime("check_out_time"));
                    System.out.println("Check-in Date: " + rs.getDate("check_in_date"));
                    System.out.println("Check-out Date: " + rs.getDate("check_out_date"));
                } else {
                    System.out.println("Type: 일반 예약");
                    System.out.println("Check-in Date: " + rs.getDate("check_in_date"));
                    System.out.println("Check-out Date: " + rs.getDate("check_out_date"));
                }
                System.out.println("-------------------------------------------------------------");
            } else {
                System.out.println("No reservation found with ID: " + reservationId);
            }
        } catch (SQLException se) {
            System.out.println("Error viewing reservation details: " + se.getMessage());
        } finally {
            DBManager.dbClose(conn, pstmt, rs);
        }
    }
    
//----------------------------------------------------------------------------------------------
//  예약수정
    public static void updateReservation() {
        System.out.print("예약번호: ");
        String reservationId = scanner.nextLine();

        Connection conn = DBManager.getDBConnection();
        String selectSql = "SELECT * FROM reservations WHERE reservation_id = ?";
        String updateSql = "UPDATE reservations SET guest_name = ?, room_number = ?, check_in_date = ?, check_out_date = ? WHERE reservation_id = ?";
        PreparedStatement selectStmt = null;
        PreparedStatement updateStmt = null;
        ResultSet rs = null;

        try {
            selectStmt = conn.prepareStatement(selectSql);
            selectStmt.setString(1, reservationId);
            rs = selectStmt.executeQuery();

            if (rs.next()) {
                System.out.println("\n현재 예약 정보:");
                System.out.println("Guest Name: " + rs.getString("guest_name"));
                System.out.println("Room Number: " + rs.getInt("room_number"));
                System.out.println("Check-in Date: " + rs.getTimestamp("check_in_date"));
                System.out.println("Check-out Date: " + rs.getTimestamp("check_out_date"));

                // 대실 예약 여부 확인
                String reservationType = rs.getString("reservation_type");

                // 새로운 정보 입력
                System.out.println("새로운 정보를 입력하세요. (변경하지 않을 항목은 엔터를 누르세요)");

                // 이름 입력
                System.out.print("New Guest Name: ");
                String newGuestName = scanner.nextLine().trim();
                if (newGuestName.isEmpty()) {
                    newGuestName = rs.getString("guest_name");
                }

                // 방 번호 입력
                int newRoomNumber;
                while (true) {
                    System.out.print("New Room Number (1~30): ");
                    String roomNumberInput = scanner.nextLine().trim();
                    if (roomNumberInput.isEmpty()) {
                        newRoomNumber = rs.getInt("room_number");
                        break;
                    } else {
                        try {
                            newRoomNumber = Integer.parseInt(roomNumberInput);
                            if (newRoomNumber >= 1 && newRoomNumber <= 30) {
                                break;
                            } else {
                                System.out.println("1~30 사이의 방 번호를 입력하세요.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("유효하지 않은 방 번호입니다. 다시 입력하세요.");
                        }
                    }
                }

             // 대실 예약인 경우 체크인 시간 입력
                Date newCheckInDate = null;
                if (reservationType.equalsIgnoreCase("대실")) {
                    while (true) {
                        System.out.print("New Check-in Date and Time (YYYY-MM-DD HH): ");
                        String checkInDateInput = scanner.nextLine().trim();
                        if (checkInDateInput.isEmpty()) {
                            newCheckInDate = rs.getTimestamp("check_in_date");
                            break;
                        } else {
                            try {
                                newCheckInDate = dateFormat2.parse(checkInDateInput);
                                if (!checkInDateInput.equals(dateFormat2.format(newCheckInDate))) {
                                    throw new ParseException("입력 형식 오류", 0);
                                }
                                break;
                            } catch (ParseException e) {
                                System.out.println("유효하지 않은 날짜 형식입니다. YYYY-MM-DD HH 형식으로 다시 입력해주세요.");
                            }
                        }
                    }
                } else { // 일반 예약인 경우에는 체크인 날짜와 체크아웃 날짜 입력
                    while (true) {
                        System.out.print("New Check-in Date (YYYY-MM-DD): ");
                        String checkInDateInput = scanner.nextLine().trim();
                        if (checkInDateInput.isEmpty()) {
                            newCheckInDate = rs.getTimestamp("check_in_date");
                            break;
                        } else {
                            try {
                                newCheckInDate = dateFormat.parse(checkInDateInput);
                                if (!checkInDateInput.equals(dateFormat.format(newCheckInDate))) {
                                    throw new ParseException("입력 형식 오류", 0);
                                }
                                break;
                            } catch (ParseException e) {
                                System.out.println("유효하지 않은 날짜 형식입니다. YYYY-MM-DD 형식으로 다시 입력해주세요.");
                            }
                        }
                    }

                    Date newCheckOutDate = null;
                    while (true) {
                        System.out.print("New Check-out Date (YYYY-MM-DD): ");
                        String checkOutDateInput = scanner.nextLine().trim();
                        if (checkOutDateInput.isEmpty()) {
                            newCheckOutDate = rs.getTimestamp("check_out_date");
                            break;
                        } else {
                            try {
                                newCheckOutDate = dateFormat.parse(checkOutDateInput);
                                if (!checkOutDateInput.equals(dateFormat.format(newCheckOutDate))) {
                                    throw new ParseException("입력 형식 오류", 0);
                                }
                                if (newCheckOutDate.before(newCheckInDate)) {
                                    System.out.println("체크아웃 날짜는 체크인 날짜 이후여야 합니다. 다시 입력해주세요.");
                                } else {
                                    break;
                                }
                            } catch (ParseException e) {
                                System.out.println("유효하지 않은 날짜 형식입니다. YYYY-MM-DD 형식으로 다시 입력해주세요.");
                            }
                        }
                    }

                    // 업데이트 쿼리 실행
                    updateStmt = conn.prepareStatement(updateSql);
                    updateStmt.setString(1, newGuestName);
                    updateStmt.setInt(2, newRoomNumber);
                    updateStmt.setTimestamp(3, new java.sql.Timestamp(newCheckInDate.getTime()));
                    updateStmt.setTimestamp(4, new java.sql.Timestamp(newCheckOutDate.getTime()));
                    updateStmt.setString(5, reservationType);
                    updateStmt.setString(6, reservationId);

                    int updatedRows = updateStmt.executeUpdate();

                    if (updatedRows > 0) {
                        System.out.println("예약 정보가 업데이트 되었습니다.");
                    } else {
                        System.out.println("예약 정보 업데이트 실패하였습니다.");
                    }
                }
            } else {
                System.out.println("해당 ID의 예약 정보가 없습니다. 다시 입력하세요.");
            }
        } catch (SQLException se) {
            System.out.println("예약 정보 업데이트 중 오류 발생: " + se.getMessage());
        } finally {
            DBManager.dbClose(conn, selectStmt, rs);
            DBManager.dbClose(null, updateStmt, null);
        }
    }

    
//----------------------------------------------------------------------------------------------
//  예약취소
    public void deleteReservation() {
        int reservationId = getReservationId();
        if (reservationId == -1) {
            return; 
        }

        System.out.print("예약을 취소하시겠습니까? " + "? (Y/N): ");
        String confirm = scanner.nextLine();

        if (!confirm.equalsIgnoreCase("Y")) {
            return;
        }
        
        System.out.print("본인 확인을 위해 핸드폰번호를 입력해주세요: ");
        String phoneNumber = scanner.nextLine();

        Connection conn = DBManager.getDBConnection();
        String deleteSql = "DELETE FROM reservations WHERE reservation_id = ?";
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(deleteSql);
            pstmt.setInt(1, reservationId);
            int deletedRows = pstmt.executeUpdate();
            System.out.println("예약 취소 되었습니다..");
        } catch (SQLException se) {
            System.out.println("Error deleting reservation: " + se.getMessage());
        } finally {
            DBManager.dbClose(conn, pstmt, null);
        }
    }
}
