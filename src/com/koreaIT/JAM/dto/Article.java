package com.koreaIT.JAM.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class Article {
	private int id;
	private LocalDateTime regDate;
	private LocalDateTime updateDate;
	private int memberId;
	private String writerName;
	private String title;
	private String body;
	private int vCnt;
	
	public Article(Map<String, Object> articleMap) {
		this.id = (int) articleMap.get("id");
		this.regDate = (LocalDateTime) articleMap.get("regDate");
		this.updateDate = (LocalDateTime) articleMap.get("updateDate");
		this.memberId = (int) articleMap.get("memberId");
		this.writerName = (String) articleMap.get("writerName");
		this.title = (String) articleMap.get("title");
		this.body = (String) articleMap.get("body");
		this.vCnt = (int) articleMap.get("vCnt");
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDateTime getRegDate() {
		return regDate;
	}
	public void setRegDate(LocalDateTime regDate) {
		this.regDate = regDate;
	}
	public LocalDateTime getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}
	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	public String getWriterName() {
		return writerName;
	}
	public void setWriterName(String writerName) {
		this.writerName = writerName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public int getvCnt() {
		return vCnt;
	}
	public void setvCnt(int vCnt) {
		this.vCnt = vCnt;
	}
}
