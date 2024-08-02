package com.koreaIT.JAM.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.koreaIT.JAM.dto.Article;

public class JDBCSelectTest {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/2024_08_JAM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
        String username = "root";
        String password = "";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        List<Article> articles = new ArrayList<>();
        
        try {
            conn = DriverManager.getConnection(url, username, password);

            String sql = "SELECT * FROM article";
            sql += " ORDER BY id DESC;";
            
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
            	int id = rs.getInt("id");
            	String regDate = rs.getString("regDate");
            	String updateDate = rs.getString("updateDate");
            	String title = rs.getString("title");
            	String body = rs.getString("body");
            	
            	Article article = new Article(id, regDate, updateDate, title, body);
            	
            	articles.add(article);
            	
            }
            
            System.out.println(articles);
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	if (rs != null) {
        		try {
        			rs.close();
        		} catch (SQLException e) {
        			e.printStackTrace();
        		}
        	}
        	if (pstmt != null) {
        		try {
        			pstmt.close();
        		} catch (SQLException e) {
        			e.printStackTrace();
        		}
        	}
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

