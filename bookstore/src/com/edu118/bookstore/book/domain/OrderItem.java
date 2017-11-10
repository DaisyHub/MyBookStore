package com.edu118.bookstore.book.domain;

public class OrderItem {
	/*
	 *	iid CHAR(32) PRIMARY KEY,
  		`count` INT,
  		subtotal DECIMAL(10,0)
  		oid CHAR(32),
  		bid CHAR(32),
	 */
	private String iid;
	private Integer count;//����
	private Double subtotal;//����*����
	private String oid;//�Ƕ�����oid�ĴӼ�
	private String bid;//��book��bid�ĵĴӼ�
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
	@Override
	public String toString() {
		return "OrderItem [iid=" + iid + ", count=" + count + ", subtotal=" + subtotal + ", oid=" + oid + ", bid=" + bid
				+ "]";
	}	
}
