package com.edu118.bookstore.book.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.edu118.bookstore.book.domain.Book;
import com.edu118.bookstore.book.domain.Category;
import com.edu118.bookstore.book.service.BookService;
import com.edu118.user.web.servlet.BaseServlet;


public class BookServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private BookService bookService = new BookService();

	// ��left.jspҳ���г�����ͼ������
	public void listCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Category> allCats = bookService.listCategory();
		ServletContext context = this.getServletContext();
		context.setAttribute("allCats", allCats);
		request.getRequestDispatcher("/jsps/left.jsp").forward(request, response);
	}

	
	// �鿴ȫ�����鼮
	public String findAllCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Book> allBooks = bookService.findAllCategory();
		request.setAttribute("allBooks", allBooks);
		return "f:/jsps/book/list.jsp";		
	}

	//ͨ��ͼ������������
	public String findByCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cid = request.getParameter("cid");
		List<Book> allBooks = bookService.findByCategory(cid);
		request.setAttribute("allBooks", allBooks);
		return "f:/jsps/book/list.jsp";	
	}
	
	//����鿴��ϸ��ͼ����Ϣ
	public String detailBookInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bid = request.getParameter("bid");
		Book book = bookService.findDetailBook(bid);
		request.setAttribute("book", book);
		return "f:/jsps/book/desc.jsp";
	}
}
