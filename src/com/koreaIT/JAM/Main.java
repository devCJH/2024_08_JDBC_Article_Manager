package com.koreaIT.JAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.koreaIT.JAM.dto.Article;

public class Main {
	public static void main(String[] args) {
		System.out.println("== 프로그램 시작 ==");
		
		Scanner sc = new Scanner(System.in);
		
		int lastArticleId = 1;
		
		String url = "jdbc:mysql://localhost:3306/2024_08_JAM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
        String username = "root";
        String password = "";
		
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
        	conn = DriverManager.getConnection(url, username, password);
            
    		while (true) {
    			System.out.printf("명령어) ");
    			String cmd = sc.nextLine().trim();
    			
    			if (cmd.equals("exit")) {
    				break;
    			}
    			
    			if (cmd.length() == 0) {
    				System.out.println("명령어를 입력해주세요");
    				continue;
    			}
    			
    			if (cmd.equals("article write")) {
    				System.out.printf("제목 : ");
    				String title = sc.nextLine().trim();
    				System.out.printf("내용 : ");
    				String body = sc.nextLine().trim();
    				
    				try {
    		            String sql = "INSERT INTO article";
    		            sql += " SET regDate = NOW()";
    		            sql += ", updateDate = NOW()";
    		            sql += ", title = '" + title + "'";
    		            sql += ", `body` = '" + body + "';";
    		            
    		            pstmt = conn.prepareStatement(sql);
    		            pstmt.executeUpdate();
    		            
    		        } catch (SQLException e) {
    		            e.printStackTrace();
    		        }
    				
    				System.out.printf("%d번 게시물이 작성되었습니다\n", lastArticleId);
    				lastArticleId++;
    			} else if (cmd.equals("article list")) {
    		        List<Article> articles = new ArrayList<>();
    		        
    		        try {
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
    		            
    		        } catch (SQLException e) {
    		            e.printStackTrace();
    		        }
    				
    				if (articles.size() == 0) {
    					System.out.println("게시물이 없습니다");
    					continue;
    				}
    				
    				System.out.println("번호	|	제목");
    				for (Article article : articles) {
    					System.out.printf("%d	|	%s\n", article.getId(), article.getTitle());
    				}
    			} else if (cmd.startsWith("article modify ")) {
    		        try {
    		        	int id = Integer.parseInt(cmd.split(" ")[2]);
    		        	
    		            String sql = "SELECT * FROM article";
    		            sql += " WHERE id = " + id + ";";
    		            
    		            pstmt = conn.prepareStatement(sql);
    		            rs = pstmt.executeQuery();
    		            
    		            Article article = null;
    		            
    		            while (rs.next()) {
    		            	article = new Article(rs.getInt("id"), rs.getString("regDate"), rs.getString("updateDate"), rs.getString("title"), rs.getString("body"));
    		            }
    		            
    		            if (article == null) {
    		            	System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
    		            	continue;
    		            }
    		            
    		            System.out.printf("수정할 제목 : ");
        				String title = sc.nextLine().trim();
        				System.out.printf("수정할 내용 : ");
        				String body = sc.nextLine().trim();
        				
        				sql = "UPDATE article";
        				sql += " SET updateDate = NOW()";
        				sql += ", title = '" + title + "'";
        				sql += ", body = '" + body + "'";
        				sql += "WHERE id = " + id + ";";
    		            
        				pstmt = conn.prepareStatement(sql);
        				pstmt.executeUpdate();
        				
        				System.out.printf("%d번 게시물이 수정되었습니다\n", id);
    		        } catch (NumberFormatException e) {
    		        	System.out.println("명령어를 확인해주세요");
					} catch (SQLException e) {
    		            e.printStackTrace();
    		        }
    			} else {
    				System.out.println("존재하지 않는 명령어 입니다");
    			}
    		}
    		sc.close();
    		
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
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
		
		System.out.println("== 프로그램 끝 ==");
	}
}
