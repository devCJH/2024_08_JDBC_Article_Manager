package com.koreaIT.JAM.controller;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import com.koreaIT.JAM.dto.Article;
import com.koreaIT.JAM.service.ArticleService;
import com.koreaIT.JAM.session.Session;
import com.koreaIT.JAM.util.Util;

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

	public void showList(String cmd) {
		String searchKeyword = cmd.substring("article list".length()).trim();

		List<Article> articles = articleService.getArticles(searchKeyword);
		
		if (searchKeyword.length() > 0) {
			System.out.println("검색어 : " + searchKeyword);
			
			if (articles.size() == 0) {
				System.out.println("검색결과가 없습니다");
				return;
			}
		}
		
		if (articles.size() == 0) {
			System.out.println("게시물이 없습니다");
			return;
		}
		
		System.out.println("번호	|	제목	|	작성자	|		작성일		|	조회수");
		for (Article article : articles) {
			System.out.printf("%d	|	%s	|	%s	|	%s	|	%d\n", article.getId(), article.getTitle(), article.getWriterName(), Util.datetimeFormat(article.getUpdateDate()), article.getvCnt());
		}
	}

	public void showDetail(String cmd) {
		int id = articleService.getCmdNum(cmd);
		
		if (id == -1) {
			System.out.println("명령어를 확인해주세요");
			return;
		}
		
		int affectedRow = articleService.increaseVCnt(id);
		
		if (affectedRow == 0) {
			System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
			return;
		}
		
		Article article = articleService.getArticle(id);
		
        System.out.printf("번호 : %d\n", article.getId());
        System.out.printf("작성일 : %s\n", Util.datetimeFormat(article.getRegDate()));
        System.out.printf("수정일 : %s\n", Util.datetimeFormat(article.getUpdateDate()));
        System.out.printf("작성자 : %s\n", article.getWriterName());
        System.out.printf("조회수 : %s\n", article.getvCnt());
        System.out.printf("제목 : %s\n", article.getTitle());
        System.out.printf("내용 : %s\n", article.getBody());
	}

	public void doModify(String cmd) {
		if (Session.getLoginedMemberId() == -1) {
			System.out.println("로그인 후 이용해주세요");
			return;
		}
		
		int id = articleService.getCmdNum(cmd);
		
		if (id == -1) {
			System.out.println("명령어를 확인해주세요");
			return;
		}
        
        Article article = articleService.getArticleById(id);
        
        if (article == null) {
        	System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
        	return;
        }
        
        if (article.getMemberId() != Session.getLoginedMemberId()) {
        	System.out.println("해당 게시물에 대한 권한이 없습니다");
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
		if (Session.getLoginedMemberId() == -1) {
			System.out.println("로그인 후 이용해주세요");
			return;
		}
		
		int id = articleService.getCmdNum(cmd);
		
		if (id == -1) {
			System.out.println("명령어를 확인해주세요");
			return;
		}
        
		Article article = articleService.getArticleById(id);
        
        if (article == null) {
        	System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
        	return;
        }
        
        if (article.getMemberId() != Session.getLoginedMemberId()) {
        	System.out.println("해당 게시물에 대한 권한이 없습니다");
        	return;
        }
        
        articleService.deleteArticle(id);
        
        System.out.printf("%d번 게시물이 삭제되었습니다\n", id);
	}
	
}
