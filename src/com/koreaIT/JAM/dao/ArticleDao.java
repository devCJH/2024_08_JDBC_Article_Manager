package com.koreaIT.JAM.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.koreaIT.JAM.util.DBUtil;
import com.koreaIT.JAM.util.SecSql;

public class ArticleDao {

	private Connection conn;
	
	public ArticleDao(Connection conn) {
		this.conn = conn;
	}

	public void writeArticle(String title, String body) {
		SecSql sql = new SecSql();
		sql.append("INSERT INTO article");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", title = ?", title);
		sql.append(", `body` = ?", body);
		
		DBUtil.insert(conn, sql);
	}
	
	public int getLastInsertId() {
		SecSql sql = new SecSql();
		sql.append("SELECT LAST_INSERT_ID()");
		return 0;
	}

	public List<Map<String, Object>> getArticles() {
		SecSql sql = new SecSql();
		sql.append("SELECT * FROM article");
		sql.append("ORDER BY id DESC");
		
		return DBUtil.selectRows(conn, sql);
	}

	public Map<String, Object> getArticle(int id) {
		SecSql sql = SecSql.from("SELECT * FROM article");
		sql.append("WHERE id = ?", id);
		
		return DBUtil.selectRow(conn, sql);
	}

	public int getArticleCnt(int id) {
		SecSql sql = new SecSql();
        sql.append("SELECT COUNT(id) FROM article");
        sql.append("WHERE id = ?", id);
        
		return DBUtil.selectRowIntValue(conn, sql);
	}

	public void modifyArticle(int id, String title, String body) {
		SecSql sql = new SecSql();
        sql.append("UPDATE article");
        sql.append("SET updateDate = NOW()");
        sql.append(", title = ?", title);
        sql.append(", `body` = ?", body);
        sql.append("WHERE id = ?", id);

        DBUtil.update(conn, sql);
	}

	public boolean isExists(int id) {
		SecSql sql = new SecSql();
        sql.append("SELECT COUNT(id) > 0 FROM article");
        sql.append("WHERE id = ?", id);
        
		return DBUtil.selectRowBooleanValue(conn, sql);
	}

	public void deleteArticle(int id) {
		SecSql sql = new SecSql();
        sql.append("DELETE FROM article");
        sql.append("WHERE id = ?", id);
        
        DBUtil.delete(conn, sql);
	}
}
