package com.koreaIT.JAM;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.koreaIT.JAM.dto.Article;

public class Main {
	public static void main(String[] args) {
		System.out.println("== 프로그램 시작 ==");
		
		Scanner sc = new Scanner(System.in);
		
		int lastArticleId = 1;
		List<Article> articles = new ArrayList<>();
		
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
				
				Article article = new Article(lastArticleId, title, body);
				
				articles.add(article);
				
				System.out.printf("%d번 게시물이 작성되었습니다\n", lastArticleId);
				lastArticleId++;
			} else if (cmd.equals("article list")) {
				if (articles.size() == 0) {
					System.out.println("게시물이 없습니다");
					continue;
				}
				
				System.out.println("번호	|	제목");
				for (int i = articles.size() - 1; i >= 0; i--) {
					System.out.printf("%d	|	%s\n", articles.get(i).getId(), articles.get(i).getTitle());
				}
			} else {
				System.out.println("존재하지 않는 명령어 입니다");
			}
			
		}
		
		sc.close();
		
		System.out.println("== 프로그램 끝 ==");
	}
}
