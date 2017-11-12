package com.edu118.bookstore.book.domain;

public class Cart {
	/*
	 * ¹ºÎï³µ
	 */
	private String caid;
	private String bid;
	private Integer count;
	private Book book;
	private Double subtotal;
	private String uid;
	public String getCaid() {
		return caid;
	}
	public void setCaid(String caid) {
		this.caid = caid;
	}
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
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
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	@Override
	public String toString() {
		return "Cart [caid=" + caid + ", bid=" + bid + ", book=" + book + ", count=" + count + ", subtotal=" + subtotal
				+ ", uid=" + uid + "]";
	}
	
	
}
