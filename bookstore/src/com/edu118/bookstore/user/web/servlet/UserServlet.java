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
		// 将表单数据封装到user对象中
		Map<String, String[]> map = request.getParameterMap();
		User userForm = CommonUtils.getBean(map, User.class);
		// 补全表单
		userForm.setUid(UUID.randomUUID().toString().replace("-", ""));
		userForm.setCode(UUID.randomUUID().toString().replace("-", ""));
		userForm.setState(0);

		// 进行表单检验
		String username = userForm.getUsername();
		String password = userForm.getPassword();
		String email = userForm.getEmail();
		Map<String, String> error = new HashMap<String, String>();
		if (username.isEmpty() || username.trim().isEmpty()) {
			error.put("username", "用户名不能为空");
		} else if (username.length() < 3 || username.length() > 15) {
			error.put("username", "用户名长度要在3-15个字符之间");
		}
		if (password.isEmpty() || password.trim().isEmpty()) {
			error.put("password", "密码不能为空");
		} else if (password.length() < 5 || password.length() > 15) {
			error.put("password", "密码长度要在5-15个字符之间");
		}
		boolean matches = Pattern.compile("^\\w+@\\w+.\\w+$").matcher(email).matches();
		if (email.isEmpty() || email.trim().isEmpty()) {
			error.put("email", "邮箱不能为空");
		} else if (!matches) {
			error.put("email", "邮箱格式不符");
		}

		// 如果error中为空，则调用userService中的方法
		if (error.isEmpty()) {
			try {
				// 注册成功
				userService.registerUser(userForm);
				/*
				 * 发送邮件到邮箱： 1.加载配置文件
				 */
				Properties prop = new Properties();
				// 载入配置文件
				InputStream in = this.getClass().getClassLoader().getResourceAsStream("emailConfig.properties");
				prop.load(in);
				String emailMsg = MessageFormat.format(prop.getProperty("EmailMessage"), userForm.getCode());
				MailUtils.sendMail(prop.getProperty("from"), userForm.getEmail(), prop.getProperty("subject"),
						emailMsg);

				request.setAttribute("msg", "恭喜注册成功，到邮箱进行激活后方能登录");
				return "f:/jsps/msg.jsp";
			} catch (Exception e) {
				request.setAttribute("msg", e.getMessage());
				return "f:/jsps/user/regist.jsp";
			}
		} else {
			// 否则，将错误信息存在域中，转到regist.jsp
			request.setAttribute("userForm", userForm);
			request.setAttribute("error", error);
			return "f:/jsps/user/regist.jsp";
		}
	}

	// 邮箱激活
	public String activation(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String code = request.getParameter("code");
		try {
			User user = userService.activeUser(code);
			// 激活成功，转到login.jsp页面
			request.setAttribute("msg", "激活成功，可以登录了");
			request.setAttribute("username", user.getUsername());
			return "f:/jsps/user/login.jsp";
		} catch (Exception e) {
			// 不存在该激活码或者已激活，转到msg.jsp
			request.setAttribute("msg", e.getMessage());
			return "f:/jsps/msg.jsp";
		}
	}

	// 用户登陆
	public String login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		request.setAttribute("username", username);
		Map<String, String> error = new HashMap<String, String>();
		if (username.isEmpty() || username.trim().isEmpty()) {
			error.put("username", "用户名不能为空");
		}
		if (password.isEmpty() || password.trim().isEmpty()) {
			error.put("password", "密码不能为空");
		}
		if (!error.isEmpty()) {
			request.setAttribute("error", error);
			return "f:/jsps/user/login.jsp";
		}
		// 从数据库中查找该用户信息
		try {
			// 登陆成功
			User user = userService.checkUser(username, password);
			// 将username放入session中
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			// request.setAttribute("user", user);
			return "f:/index.jsp";
		} catch (Exception e) {

			// 用户名与错误信息回显
			request.setAttribute("msg", e.getMessage());
			return "f:/jsps/user/login.jsp";
		}
	}

	// 用户退出
	public String exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Object user = session.getAttribute("user");
		if (user != null){
			session.removeAttribute("user");
		}
		//转发到主页面
		return "f:/index.jsp";
	}
	
	
}
