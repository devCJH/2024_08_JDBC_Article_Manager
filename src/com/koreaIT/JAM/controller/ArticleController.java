package com.koreaIT.JAM.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.koreaIT.JAM.dto.Article;
import com.koreaIT.JAM.util.DBUtil;
import com.koreaIT.JAM.util.SecSql;

public class ArticleController {

	private Scanner sc;
	private Connection conn;
	
	public ArticleController(Connection conn, Scanner sc) {
		this.conn = conn;
		this.sc = sc;
	}

	public void doWrite() {
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
	}

	public void showList() {
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
			return;
		}
		
		System.out.println("번호	|	제목	|	작성일");
		for (Article article : articles) {
			System.out.printf("%d	|	%s	|	%s\n", article.getId(), article.getTitle(), article.getUpdateDate());
		}
	}

	public void showDetail(String cmd) {
		int id = -1;
		
        try {
        	id = Integer.parseInt(cmd.split(" ")[2]);
        } catch (NumberFormatException e) {
        	System.out.println("명령어를 확인해주세요");
        	return;
		}
        
        SecSql sql = SecSql.from("SELECT * FROM article");
		sql.append("WHERE id = ?", id);
		
		Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);
		
		if (articleMap.isEmpty()) {
        	System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
        	return;
        }
		
        Article article = new Article(articleMap);
        
        System.out.printf("번호 : %d\n", article.getId());
        System.out.printf("작성일 : %s\n", article.getRegDate());
        System.out.printf("수정일 : %s\n", article.getUpdateDate());
        System.out.printf("제목 : %s\n", article.getTitle());
        System.out.printf("내용 : %s\n", article.getBody());
	}

	public void doModify(String cmd) {
		int id = -1;
		
        try {
        	id = Integer.parseInt(cmd.split(" ")[2]);
        } catch (NumberFormatException e) {
        	System.out.println("명령어를 확인해주세요");
		}
        
        SecSql sql = new SecSql();
        sql.append("SELECT COUNT(id) FROM article");
        sql.append("WHERE id = ?", id);

        int articleCnt = DBUtil.selectRowIntValue(conn, sql);
        
        if (articleCnt == 0) {
        	System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
        	return;
        }
        
        System.out.printf("수정할 제목 : ");
		String title = sc.nextLine().trim();
		System.out.printf("수정할 내용 : ");
		String body = sc.nextLine().trim();
		
		sql = new SecSql();
        sql.append("UPDATE article");
        sql.append("SET updateDate = NOW()");
        sql.append(", title = ?", title);
        sql.append(", `body` = ?", body);
        sql.append("WHERE id = ?", id);

        DBUtil.update(conn, sql);
		
		System.out.printf("%d번 게시물이 수정되었습니다\n", id);
	}

	public void doDelete(String cmd) {
		int id = -1;
		
        try {
        	id = Integer.parseInt(cmd.split(" ")[2]);
        } catch (NumberFormatException e) {
        	System.out.println("명령어를 확인해주세요");
		}
        
        SecSql sql = new SecSql();
        sql.append("SELECT COUNT(id) > 0 FROM article");
        sql.append("WHERE id = ?", id);

        boolean isExists = DBUtil.selectRowBooleanValue(conn, sql);
        
        if (isExists == false) {
        	System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
        	return;
        }
        
        sql = new SecSql();
        sql.append("DELETE FROM article");
        sql.append("WHERE id = ?", id);
        
        DBUtil.delete(conn, sql);
        
        System.out.printf("%d번 게시물이 삭제되었습니다\n", id);
	}
	
}
