package com.edu118.bookstore.book.domain;

public class Category {

	/*
	 * cid CHAR(32) PRIMARY KEY, cname VARCHAR(100) NOT NULL
	 */
	private String cid;// 书类id
	private String cname;// 书类名

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	@Override
	public String toString() {
		return "Category [cid=" + cid + ", cname=" + cname + "]";
	}

}
