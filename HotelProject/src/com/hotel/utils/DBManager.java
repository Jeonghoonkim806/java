package com.hotel.utils;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;

public class DBManager {
    /**
     * 설명: 오라클 접속 메소드
     * @return Connection -> 오라클 접속 클래스 객체
     */
    public static Connection getDBConnection() {
        Connection conn = null;

        try {
            Class.forName("oracle.jdbc.OracleDriver"); // 오라클 JDBC 드라이버 로드

            String url = "jdbc:oracle:thin:@localhost:1521:orcl"; // 오라클 서버 URL
            String username = "hotel"; // 오라클 접속 계정 이름
            String password = "1234"; // 오라클 접속 계정 비밀번호

            // 오라클 데이터베이스에 접속
            conn = DriverManager.getConnection(url, username, password);

        } catch (ClassNotFoundException e) {
            System.out.println("Oracle JDBC 드라이버를 찾을 수 없습니다.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("오라클 데이터베이스에 접속할 수 없습니다.");
            e.printStackTrace();
        }

        return conn;
    }

    /**
     * 설명: 데이터베이스 자원(close)
     * @param conn - 데이터베이스 접속 클래스 객체
     * @param pstmt - 쿼리저장소
     * @param rs - 결과값
     */
    public static void dbClose(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null ) { // rs가 null값이 아니면
                rs.close();
            }
            if (pstmt != null ) { // pstmt가 null값이 아니면
                pstmt.close();
            }
            if (conn != null ) { // conn이 null값이 아니면
                conn.close();
            }
        } catch (SQLException se) {
            System.out.println("Oracle DB IO 오류 -> " + se.getMessage());
        }
    }
}