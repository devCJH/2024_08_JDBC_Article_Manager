package com.koreaIT.JAM.dao;

import java.sql.Connection;
import java.util.Map;

import com.koreaIT.JAM.util.DBUtil;
import com.koreaIT.JAM.util.SecSql;

public class MemberDao {

	private Connection conn;
	
	public MemberDao(Connection conn) {
		this.conn = conn;
	}
	
	public void joinMember(String loginId, String loginPw, String name) {
		
		SecSql sql = new SecSql();
		sql.append("INSERT INTO `member`");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", loginId = ?", loginId);
		sql.append(", loginPw = ?", loginPw);
		sql.append(", `name` = ?", name);
		
		DBUtil.insert(conn, sql);
	}
	
	public boolean isLoginIdDup(String loginId) {
		
		SecSql sql = new SecSql();
		sql.append("SELECT COUNT(id) > 0 FROM `member`");
		sql.append("WHERE loginId = ?", loginId);
		
		return DBUtil.selectRowBooleanValue(conn, sql);
	}

	public Map<String, Object> getMemberByLoginId(String loginId) {
		SecSql sql = new SecSql();
		sql.append("SELECT * FROM `member`");
		sql.append("WHERE loginId = ?", loginId);
		
		return DBUtil.selectRow(conn, sql);
	}
}
