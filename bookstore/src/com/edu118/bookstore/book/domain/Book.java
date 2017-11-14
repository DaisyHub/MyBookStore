package com.edu118.bookstore.book.domain;

public class Book {
	/*
	 * 	bid CHAR(32) PRIMARY KEY,
  		bname VARCHAR(100),
  		price DECIMAL(5,1),
  		author VARCHAR(20),
  		image VARCHAR(200),
  		cid CHAR(32),
	 */	
	private String bid;//图书id
	private String bname;
	private Double price;
	private String author;
	private String image;
	private String cid;//图书类别id
	private Integer state;
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public String getBname() {
		return bname;
	}
	public void setBname(String bname) {
		this.bname = bname;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "Book [bid=" + bid + ", bname=" + bname + ", price=" + price + ", author=" + author + ", image=" + image
				+ ", cid=" + cid + ", state=" + state + "]";
	}
	
}
