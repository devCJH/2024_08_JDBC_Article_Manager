package com.koreaIT.JAM.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.koreaIT.JAM.dao.ArticleDao;
import com.koreaIT.JAM.dto.Article;

public class ArticleService {
	
	private ArticleDao articleDao;
	
	public ArticleService(Connection conn) {
		this.articleDao = new ArticleDao(conn);
	}

	public void writeArticle(String title, String body) {
		articleDao.writeArticle(title, body);
	}

	public int getLastInsertId() {
		return articleDao.getLastInsertId();
	}
	
	public List<Article> getArticles() {
		List<Map<String, Object>> articleListMap = articleDao.getArticles();
		
		List<Article> articles = new ArrayList<>();
		
		for (Map<String, Object> articleMap : articleListMap) {
			articles.add(new Article(articleMap));
		}
		
		return articles;
	}

	public Article getArticle(int id) {
		Map<String, Object> articleMap = articleDao.getArticle(id);
		
		if (articleMap.isEmpty()) {
			return null;
		}
		
		return new Article(articleMap);
	}

	public int getArticleCnt(int id) {
		return articleDao.getArticleCnt(id);
	}

	public void modifyArticle(int id, String title, String body) {
		articleDao.modifyArticle(id, title, body);
	}

	public boolean isExists(int id) {
		return articleDao.isExists(id);
	}

	public void deleteArticle(int id) {
		articleDao.deleteArticle(id);
	}

	public int getCmdNum(String cmd) {
		int id = -1;
		
        try {
        	id = Integer.parseInt(cmd.split(" ")[2]);
        } catch (NumberFormatException e) {
        	return id;
		}
        
		return id;
	}
}
