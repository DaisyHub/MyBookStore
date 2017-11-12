package com.edu118.bookstore.book.domain;

import java.util.List;

public class OrderItem {
	/*
	 *	iid CHAR(32) PRIMARY KEY,
  		`count` INT,
  		subtotal DECIMAL(10,0)
  		oid CHAR(32),
  		bid CHAR(32),
	 */
	private String iid;
	private Integer count;//数量
	private Double subtotal;//数量*单价
	private String oid;//是订单中oid的从键
	private String bid;//是book中bid的的从键
	private List<Book> booklist;
	public String getIid() {
		return iid;
	}
	public void setIid(String iid) {
		this.iid = iid;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public List<Book> getBooklist() {
		return booklist;
	}
	public void setBooklist(List<Book> booklist) {
		this.booklist = booklist;
	}
	@Override
	public String toString() {
		return "OrderItem [iid=" + iid + ", count=" + count + ", subtotal=" + subtotal + ", oid=" + oid + ", bid=" + bid
				+ ", booklist=" + booklist + "]";
	}
	
	
	
}
