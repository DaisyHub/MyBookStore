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
	//���ͼ��
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
		//����ͨ�����Է�װ��Map��
		Map<String, String> map = new HashMap<String, String>();
		for(FileItem file : fileItem){
			if(file.isFormField()){
				map.put(file.getFieldName(), file.getString("utf-8"));
			}
		}
		//��Map���ֵ��װ��������
		Book book = CommonUtils.getBean(map, Book.class);
		book.setBid(UUID.randomUUID().toString().replace("-", ""));
		book.setState(1);
		
		//����ͼƬ		
		String savePath = this.getServletContext().getRealPath("book_img");
		String fileName = UUID.randomUUID().toString().replace("-", "")+"_"+fileItem.get(1).getName();
		File targetFile = new File(savePath+"\\"+fileName);
		try {
			//���õ���Fileд��Ŀ���ļ���
			fileItem.get(1).write(targetFile);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		book.setImage("book_img/"+fileName);
		
		adminBookService.addBook(book);
		//ת������ʾ����ͼ��
		request.getRequestDispatcher("/AdminBookServlet?method=listAllBook").forward(request, response);
	}

}
