package com.edu118.bookstore.admin.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.edu118.bookstore.admin.service.AdminBookService;
import com.edu118.bookstore.admin.service.CategoryService;
import com.edu118.bookstore.book.domain.Book;
import com.edu118.bookstore.book.domain.Category;
import com.edu118.user.web.servlet.BaseServlet;
import com.edu118.utils.CommonUtils;

/**
 * Servlet implementation class AdminBookServlet
 */
public class AdminBookServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    private AdminBookService adminBookService = new AdminBookService();

    //�г�����ͼ��
    public String listAllBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	List<Book> bookList = adminBookService.listAllBook();
    	request.setAttribute("bookList", bookList);
    	return "f:/adminjsps/admin/book/list.jsp";
    }
    //ͨ��bid�鿴ͼ�����ϸ��Ϣ
    public String descBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String bid = request.getParameter("bid");
    	Book book = adminBookService.descBook(bid);  
    	//��dao�ñ���ķ���
    	Category thisBookCate = new CategoryService().findCategory(book.getCid());
    	//���з���
    	List<Category> categoryList = new CategoryService().showCategory();
    	categoryList.remove(thisBookCate);
    	
    	request.setAttribute("book", book);
    	request.setAttribute("thisBookCate", thisBookCate);
    	request.setAttribute("categoryList", categoryList);
    	return "f:/adminjsps/admin/book/desc.jsp";
    }

    public String toAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setAttribute("categoryList", new CategoryService().showCategory());
    	return "f:/adminjsps/admin/book/add.jsp";
    }
    //ɾ��ͼ�飬�����������ݿ������ɾ����ֻ���¼ܶ���
    public String del(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
    	String bid = request.getParameter("bid");
    	adminBookService.deleteBook(bid);
    	return listAllBook(request, response);
    }
    
    //�޸�ͼ�����Ϣ
    public String mod(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
//    	String cid = request.getParameter("cid");
    	Book book = CommonUtils.getBean(request.getParameterMap(), Book.class);
//    	book.setCid(cid);
    	System.out.println(book);
    	adminBookService.modifyBook(book);
    	return listAllBook(request, response);
    }
}
