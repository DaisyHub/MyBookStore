package com.edu118.bookstore.admin.service;

import java.util.List;
import com.edu118.bookstore.admin.dao.AdminBookDao;
import com.edu118.bookstore.book.dao.BookDao;
import com.edu118.bookstore.book.domain.Book;

public class AdminBookService {

	private AdminBookDao adminBookDao = new AdminBookDao();

	public List<Book> listAllBook() {
		List<Book> books = adminBookDao.selectAllBook();
		if(books == null) throw new NullPointerException("�����û���κ��鼮�����һЩ��");
		return books;		
	}

	//BookDao���Ѿ���ͨ��bid�����鼮��Ϣ�������ظ�д��
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
