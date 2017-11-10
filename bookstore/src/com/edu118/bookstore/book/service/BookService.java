package com.edu118.bookstore.book.service;

import java.util.List;

import com.edu118.bookstore.book.dao.BookDao;
import com.edu118.bookstore.book.domain.*;

public class BookService {
	private BookDao bookDao = new BookDao();

	public List<Category> listCategory() {
		return bookDao.findCategory();
	}

	public List<Book> findAllCategory() {
		return bookDao.selectAllCats();
		
	}

	public List<Book> findByCategory(String cid) {
		
		return bookDao.selectBookByCid(cid);
	}

	public Book findDetailBook(String bid) {
		return bookDao.selectBookById(bid);
	}
}
