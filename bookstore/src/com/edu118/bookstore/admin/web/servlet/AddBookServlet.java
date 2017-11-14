package com.edu118.bookstore.admin.web.servlet;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.edu118.bookstore.admin.service.AdminBookService;
import com.edu118.bookstore.book.domain.Book;
import com.edu118.utils.CommonUtils;

/**
 * Servlet implementation class AddBookServlet
 */
public class AddBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private AdminBookService adminBookService = new AdminBookService();  
	//添加图书
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload sf = new ServletFileUpload(factory);
		List<FileItem> fileItem;
		try {
			fileItem = sf.parseRequest(request);
		} catch (FileUploadException e) {
			throw new RuntimeException(e);
		}
		//将普通的属性封装到Map中
		Map<String, String> map = new HashMap<String, String>();
		for(FileItem file : fileItem){
			if(file.isFormField()){
				map.put(file.getFieldName(), file.getString("utf-8"));
			}
		}
		//将Map里的值封装到对象中
		Book book = CommonUtils.getBean(map, Book.class);
		book.setBid(UUID.randomUUID().toString().replace("-", ""));
		book.setState(1);
		
		//设置图片		
		String savePath = this.getServletContext().getRealPath("book_img");
		String fileName = UUID.randomUUID().toString().replace("-", "")+"_"+fileItem.get(1).getName();
		File targetFile = new File(savePath+"\\"+fileName);
		try {
			//将得到的File写入目标文件中
			fileItem.get(1).write(targetFile);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		book.setImage("book_img/"+fileName);
		
		adminBookService.addBook(book);
		//转发到显示所有图书
		request.getRequestDispatcher("/AdminBookServlet?method=listAllBook").forward(request, response);
	}

}
