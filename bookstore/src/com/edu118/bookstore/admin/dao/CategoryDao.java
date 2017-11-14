package com.edu118.bookstore.admin.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.edu118.bookstore.book.domain.Category;
import com.edu118.util.JdbcUtils;

public class CategoryDao {

	protected String selectCategory = "select * from category";
	protected String insertCat = "insert into category values(?,?)";
	protected String findCatById = "select * from category where cid=?";
	protected String updateCate = "update category set cname=? where cid=?";
	protected String delCate = "delete from category where cid=?";
	protected String delBookByCid = "delete from book where cid=?";
	QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource());
	
	public List<Category> selectCategory() {
		try {
			return qr.query(selectCategory, new BeanListHandler<Category>(Category.class));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	//ÃÌº”∑÷¿‡
	public void insertCategory(String cname, String cid) {
		try {
			qr.update(insertCat, cid,cname);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Category findCategory(String cid) {
		try {
			return qr.query(findCatById, new BeanHandler<Category>(Category.class), cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void updateCate(String cid, String cname) {
		try {
			qr.update(updateCate, cname,cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void delCate(Connection conn, String cid) {
		try {
			qr.update(conn, delCate, cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void delBookByCid(Connection conn, String cid) {
		try {
			qr.update(conn, delBookByCid, cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
