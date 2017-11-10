package com.edu118.bookstore.user.web.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.edu118.bookstore.user.domain.User;
import com.edu118.bookstore.user.service.UserService;
import com.edu118.bookstore.user.utils.MailUtils;
import com.edu118.user.web.servlet.BaseServlet;
import com.edu118.utils.CommonUtils;

public class UserServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	private UserService userService = new UserService();

	public String register(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// �������ݷ�װ��user������
		Map<String, String[]> map = request.getParameterMap();
		User userForm = CommonUtils.getBean(map, User.class);
		// ��ȫ��
		userForm.setUid(UUID.randomUUID().toString().replace("-", ""));
		userForm.setCode(UUID.randomUUID().toString().replace("-", ""));
		userForm.setState(0);

		// ���б�����
		String username = userForm.getUsername();
		String password = userForm.getPassword();
		String email = userForm.getEmail();
		Map<String, String> error = new HashMap<String, String>();
		if (username.isEmpty() || username.trim().isEmpty()) {
			error.put("username", "�û�������Ϊ��");
		} else if (username.length() < 3 || username.length() > 15) {
			error.put("username", "�û�������Ҫ��3-15���ַ�֮��");
		}
		if (password.isEmpty() || password.trim().isEmpty()) {
			error.put("password", "���벻��Ϊ��");
		} else if (password.length() < 5 || password.length() > 15) {
			error.put("password", "���볤��Ҫ��5-15���ַ�֮��");
		}
		boolean matches = Pattern.compile("^\\w+@\\w+.\\w+$").matcher(email).matches();
		if (email.isEmpty() || email.trim().isEmpty()) {
			error.put("email", "���䲻��Ϊ��");
		} else if (!matches) {
			error.put("email", "�����ʽ����");
		}

		// ���error��Ϊ�գ������userService�еķ���
		if (error.isEmpty()) {
			try {
				// ע��ɹ�
				userService.registerUser(userForm);
				/*
				 * �����ʼ������䣺 1.���������ļ�
				 */
				Properties prop = new Properties();
				// ���������ļ�
				InputStream in = this.getClass().getClassLoader().getResourceAsStream("emailConfig.properties");
				prop.load(in);
				String emailMsg = MessageFormat.format(prop.getProperty("EmailMessage"), userForm.getCode());
				MailUtils.sendMail(prop.getProperty("from"), userForm.getEmail(), prop.getProperty("subject"),
						emailMsg);

				request.setAttribute("msg", "��ϲע��ɹ�����������м�����ܵ�¼");
				return "f:/jsps/msg.jsp";
			} catch (Exception e) {
				request.setAttribute("msg", e.getMessage());
				return "f:/jsps/user/regist.jsp";
			}
		} else {
			// ���򣬽�������Ϣ�������У�ת��regist.jsp
			request.setAttribute("userForm", userForm);
			request.setAttribute("error", error);
			return "f:/jsps/user/regist.jsp";
		}
	}

	// ���伤��
	public String activation(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String code = request.getParameter("code");
		try {
			User user = userService.activeUser(code);
			// ����ɹ���ת��login.jspҳ��
			request.setAttribute("msg", "����ɹ������Ե�¼��");
			request.setAttribute("username", user.getUsername());
			return "f:/jsps/user/login.jsp";
		} catch (Exception e) {
			// �����ڸü���������Ѽ��ת��msg.jsp
			request.setAttribute("msg", e.getMessage());
			return "f:/jsps/msg.jsp";
		}
	}

	// �û���½
	public String login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		request.setAttribute("username", username);
		Map<String, String> error = new HashMap<String, String>();
		if (username.isEmpty() || username.trim().isEmpty()) {
			error.put("username", "�û�������Ϊ��");
		}
		if (password.isEmpty() || password.trim().isEmpty()) {
			error.put("password", "���벻��Ϊ��");
		}
		if (!error.isEmpty()) {
			request.setAttribute("error", error);
			return "f:/jsps/user/login.jsp";
		}
		// �����ݿ��в��Ҹ��û���Ϣ
		try {
			// ��½�ɹ�
			User user = userService.checkUser(username, password);
			// ��username����session��
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			// request.setAttribute("user", user);
			return "f:/index.jsp";
		} catch (Exception e) {

			// �û����������Ϣ����
			request.setAttribute("msg", e.getMessage());
			return "f:/jsps/user/login.jsp";
		}
	}

	// �û��˳�
	public String exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Object user = session.getAttribute("user");
		if (user != null){
			session.removeAttribute("user");
		}
		//ת������ҳ��
		return "f:/index.jsp";
	}
	
	
}
