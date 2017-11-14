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

    //查看分类
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
    //添加分类
    public String addCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String cname = request.getParameter("cname");
    	String cid = UUID.randomUUID().toString().replace("-", "");
    	categoryService.addCategory(cname, cid);
    	showCategory(request, response);
    	return "f:/adminjsps/admin/category/list.jsp";
    }
    //修改分类：1.要先回显数据
    public String toModify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String cid = request.getParameter("cid");
    	Category category = categoryService.findCategory(cid);
    	request.setAttribute("category", category);
    	return "f:/adminjsps/admin/category/mod.jsp";
    }
    //2.开始修改
    public String modifyCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String cid = request.getParameter("cid");
    	String cname = request.getParameter("cname");
    	categoryService.modCategory(cid, cname);
    	showCategory(request, response);
    	return "f:/adminjsps/admin/category/list.jsp";
    }
    
    //删除分类：先回显
    public String toDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String cid = request.getParameter("cid");
    	Category category = categoryService.findCategory(cid);
    	request.setAttribute("category", category);
    	return "f:/adminjsps/admin/category/del.jsp";
    }
    //2.开始删除
    public String delCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String cid = request.getParameter("cid");
    	categoryService.delCategory(cid);
    	showCategory(request, response);
    	return "f:/adminjsps/admin/category/list.jsp";
    }
}
