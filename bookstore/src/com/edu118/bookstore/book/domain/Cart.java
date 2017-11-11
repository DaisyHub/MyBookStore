package com.edu118.bookstore.book.domain;

public class Cart {
	/*
	 * book.image,book.bname,book.author,book.price,orderitem.count,"
			+ "orderitem.subtotal
	 */
	private String image;
	private String bname;
	private String author;
	private Double price;
	private Integer count;
	private Double subtotal;
	private String bid;
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
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
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
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
	@Override
	public String toString() {
		return "Cart [image=" + image + ", bname=" + bname + ", author=" + author + ", price=" + price + ", count="
				+ count + ", subtotal=" + subtotal + ", bid=" + bid + "]";
	}
	
	
}
