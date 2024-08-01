package com.koreaIT.JAM.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnectTest {
    public static void main(String[] args) {
    	String url = "jdbc:mysql://localhost:3306/2024_08_JAM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull"; // 데이터베이스 URL
        String username = "root"; // 데이터베이스 사용자 이름
        String password = ""; // 데이터베이스 비밀번호

        Connection conn = null;
        try {
            // 데이터베이스에 연결
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connection successful!");

            // 데이터베이스 작업 수행 (예: 쿼리 실행)

        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        } finally {
            // 연결 종료
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

