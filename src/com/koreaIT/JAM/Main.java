package com.koreaIT.JAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.koreaIT.JAM.dto.Article;
import com.koreaIT.JAM.util.DBUtil;
import com.koreaIT.JAM.util.SecSql;

public class Main {
	public static void main(String[] args) {
		System.out.println("== 프로그램 시작 ==");
		
		Scanner sc = new Scanner(System.in);
		
		String url = "jdbc:mysql://localhost:3306/2024_08_JAM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
        String username = "root";
        String password = "";
		
        Connection conn = null;
        
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
    				
					SecSql sql = new SecSql();
					sql.append("INSERT INTO article");
					sql.append("SET regDate = NOW()");
					sql.append(", updateDate = NOW()");
					sql.append(", title = ?", title);
					sql.append(", `body` = ?", body);
					
					int id = DBUtil.insert(conn, sql);
					
    				System.out.printf("%d번 게시물이 작성되었습니다\n", id);
    				
    			} else if (cmd.equals("article list")) {
    		        List<Article> articles = new ArrayList<>();
    		        
		        	SecSql sql = new SecSql();
					sql.append("SELECT * FROM article");
					sql.append("ORDER BY id DESC");
		            
					List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);
					
					for (Map<String, Object> articleMap : articleListMap) {
						articles.add(new Article(articleMap));
					}
					
    				if (articles.size() == 0) {
    					System.out.println("게시물이 없습니다");
    					continue;
    				}
    				
    				System.out.println("번호	|	제목	|	작성일");
    				for (Article article : articles) {
    					System.out.printf("%d	|	%s	|	%s\n", article.getId(), article.getTitle(), article.getUpdateDate());
    				}
    			} else if (cmd.startsWith("article detail ")) {
    				int id = -1;
    				
    		        try {
    		        	id = Integer.parseInt(cmd.split(" ")[2]);
    		        } catch (NumberFormatException e) {
    		        	System.out.println("명령어를 확인해주세요");
    		        	continue;
					}
    		        
    		        SecSql sql = SecSql.from("SELECT * FROM article");
					sql.append("WHERE id = ?", id);
					
					Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);
					
					if (articleMap.isEmpty()) {
		            	System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
		            	continue;
			        }
					
		            Article article = new Article(articleMap);
		            
		            System.out.printf("번호 : %d\n", article.getId());
		            System.out.printf("작성일 : %s\n", article.getRegDate());
		            System.out.printf("수정일 : %s\n", article.getUpdateDate());
		            System.out.printf("제목 : %s\n", article.getTitle());
		            System.out.printf("내용 : %s\n", article.getBody());
    			} else if (cmd.startsWith("article modify ")) {
    		        try {
    		        	int id = Integer.parseInt(cmd.split(" ")[2]);
    		        	
    		            String sql = "SELECT * FROM article";
    		            sql += " WHERE id = " + id + ";";
    		            
    		            pstmt = conn.prepareStatement(sql);
    		            rs = pstmt.executeQuery();
    		            
    		            Article article = null;
    		            
//    		            while (rs.next()) {
//    		            	article = new Article(rs.getInt("id"), rs.getString("regDate"), rs.getString("updateDate"), rs.getString("title"), rs.getString("body"));
//    		            }
    		            
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
        				sql += " WHERE id = " + id + ";";
    		            
        				pstmt = conn.prepareStatement(sql);
        				pstmt.executeUpdate();
        				
        				System.out.printf("%d번 게시물이 수정되었습니다\n", id);
    		        } catch (NumberFormatException e) {
    		        	System.out.println("명령어를 확인해주세요");
					} catch (SQLException e) {
    		            e.printStackTrace();
    		        }
    			} else if (cmd.startsWith("article delete ")) {
    		        try {
    		        	int id = Integer.parseInt(cmd.split(" ")[2]);
    		        	
    		            String sql = "SELECT * FROM article";
    		            sql += " WHERE id = " + id + ";";
    		            
    		            pstmt = conn.prepareStatement(sql);
    		            rs = pstmt.executeQuery();
    		            
    		            Article article = null;
    		            
//    		            while (rs.next()) {
//    		            	article = new Article(rs.getInt("id"), rs.getString("regDate"), rs.getString("updateDate"), rs.getString("title"), rs.getString("body"));
//    		            }
    		            
    		            if (article == null) {
    		            	System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
    		            	continue;
    		            }
    		            
        				sql = "DELETE FROM article";
        				sql += " WHERE id = " + id + ";";
    		            
        				pstmt = conn.prepareStatement(sql);
        				pstmt.executeUpdate();
        				
        				System.out.printf("%d번 게시물이 삭제되었습니다\n", id);
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
