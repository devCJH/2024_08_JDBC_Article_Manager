package com.koreaIT.JAM.controller;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import com.koreaIT.JAM.dto.Article;
import com.koreaIT.JAM.service.ArticleService;
import com.koreaIT.JAM.session.Session;

public class ArticleController {

	private Scanner sc;
	private ArticleService articleService;
	
	public ArticleController(Connection conn, Scanner sc) {
		this.sc = sc;
		this.articleService = new ArticleService(conn);
	}

	public void doWrite() {
		if (Session.getLoginedMemberId() == -1) {
			System.out.println("로그인 후 이용해주세요");
			return;
		}
		
		System.out.printf("제목 : ");
		String title = sc.nextLine().trim();
		System.out.printf("내용 : ");
		String body = sc.nextLine().trim();
		
		int id = articleService.writeArticle(Session.getLoginedMemberId(), title, body);
		
		System.out.printf("%d번 게시물이 작성되었습니다\n", id);
	}

	public void showList() {
		List<Article> articles = articleService.getArticles();
        
		if (articles.size() == 0) {
			System.out.println("게시물이 없습니다");
			return;
		}
		
		System.out.println("번호	|	제목	|	작성자	|	작성일");
		for (Article article : articles) {
			System.out.printf("%d	|	%s	|	%s	|	%s\n", article.getId(), article.getTitle(), article.getWriterName(), article.getUpdateDate());
		}
	}

	public void showDetail(String cmd) {
		int id = articleService.getCmdNum(cmd);
		
		if (id == -1) {
			System.out.println("명령어를 확인해주세요");
			return;
		}
        
        Article article = articleService.getArticle(id);
        
		if (article == null) {
        	System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
        	return;
        }
		
        System.out.printf("번호 : %d\n", article.getId());
        System.out.printf("작성일 : %s\n", article.getRegDate());
        System.out.printf("수정일 : %s\n", article.getUpdateDate());
        System.out.printf("작성자 : %s\n", article.getWriterName());
        System.out.printf("제목 : %s\n", article.getTitle());
        System.out.printf("내용 : %s\n", article.getBody());
	}

	public void doModify(String cmd) {
		int id = articleService.getCmdNum(cmd);
		
		if (id == -1) {
			System.out.println("명령어를 확인해주세요");
			return;
		}
        
        int articleCnt = articleService.getArticleCnt(id);
        
        if (articleCnt == 0) {
        	System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
        	return;
        }
        
        System.out.printf("수정할 제목 : ");
		String title = sc.nextLine().trim();
		System.out.printf("수정할 내용 : ");
		String body = sc.nextLine().trim();
		
		articleService.modifyArticle(id, title, body);
		
		System.out.printf("%d번 게시물이 수정되었습니다\n", id);
	}

	public void doDelete(String cmd) {
		int id = articleService.getCmdNum(cmd);
		
		if (id == -1) {
			System.out.println("명령어를 확인해주세요");
			return;
		}
        
        boolean isExists = articleService.isExists(id);
        
        if (isExists == false) {
        	System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
        	return;
        }
        
        articleService.deleteArticle(id);
        
        System.out.printf("%d번 게시물이 삭제되었습니다\n", id);
	}
	
}
