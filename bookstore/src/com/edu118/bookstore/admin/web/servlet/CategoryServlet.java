package com.edu118.bookstore.admin.web.servlet;


import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.edu118.bookstore.admin.service.CategoryService;
import com.edu118.bookstore.book.domain.Category;
import com.edu118.user.web.servlet.BaseServlet;


public class CategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    private CategoryService categoryService = new CategoryService();

    //�鿴����
    public String showCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	List<Category> catList;
    	try{
    		catList = categoryService.showCategory();
    	}catch(Exception e){
    		request.setAttribute("msg", e.getMessage());
    		return "f:/adminjsps/admin/msg.jsp";
    	}
    	request.setAttribute("catList", catList);
    	return "f:/adminjsps/admin/category/list.jsp";
    }
    //��ӷ���
    public String addCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String cname = request.getParameter("cname");
    	String cid = UUID.randomUUID().toString().replace("-", "");
    	categoryService.addCategory(cname, cid);
    	showCategory(request, response);
    	return "f:/adminjsps/admin/category/list.jsp";
    }
    //�޸ķ��ࣺ1.Ҫ�Ȼ�������
    public String toModify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String cid = request.getParameter("cid");
    	Category category = categoryService.findCategory(cid);
    	request.setAttribute("category", category);
    	return "f:/adminjsps/admin/category/mod.jsp";
    }
    //2.��ʼ�޸�
    public String modifyCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String cid = request.getParameter("cid");
    	String cname = request.getParameter("cname");
    	categoryService.modCategory(cid, cname);
    	showCategory(request, response);
    	return "f:/adminjsps/admin/category/list.jsp";
    }
    
    //ɾ�����ࣺ�Ȼ���
    public String toDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String cid = request.getParameter("cid");
    	Category category = categoryService.findCategory(cid);
    	request.setAttribute("category", category);
    	return "f:/adminjsps/admin/category/del.jsp";
    }
    //2.��ʼɾ��
    public String delCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String cid = request.getParameter("cid");
    	categoryService.delCategory(cid);
    	showCategory(request, response);
    	return "f:/adminjsps/admin/category/list.jsp";
    }
}
