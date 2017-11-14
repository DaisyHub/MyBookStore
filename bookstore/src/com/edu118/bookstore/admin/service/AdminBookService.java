package com.edu118.bookstore.admin.service;

import java.util.List;
import com.edu118.bookstore.admin.dao.AdminBookDao;
import com.edu118.bookstore.book.dao.BookDao;
import com.edu118.bookstore.book.domain.Book;

public class AdminBookService {

	private AdminBookDao adminBookDao = new AdminBookDao();

	public List<Book> listAllBook() {
		List<Book> books = adminBookDao.selectAllBook();
		if(books == null) throw new NullPointerException("书库中没有任何书籍，添加一些吧");
		return books;		
	}

	//BookDao中已经有通过bid查找书籍信息，不再重复写了
	public Book descBook(String bid) {
		return new BookDao().selectBookById(bid);
	}

	public void deleteBook(String bid) {
		adminBookDao.deleteBook(bid);
	}

	public void modifyBook(Book book) {
		adminBookDao.updateBook(book);
	}

	public void addBook(Book book) {
		adminBookDao.insertBook(book);
	}
}
