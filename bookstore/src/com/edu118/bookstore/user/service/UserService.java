package com.edu118.bookstore.user.service;

import com.edu118.bookstore.user.dao.UserDao;
import com.edu118.bookstore.user.domain.User;

public class UserService {

	private UserDao userDao = new UserDao();

	public void registerUser(User userForm) {
		//在数据库中查找username 和 Email,看是否存在，若存在，则无法注册
		User user1 = userDao.findUserByUName(userForm.getUsername());
		User user2 = userDao.findUserByEmail(userForm.getEmail());
		
		if(user1 != null) throw new RuntimeException("该用户名已经存在,换个名字注册吧");
		if(user2 != null) throw new RuntimeException("该邮箱已被注册，换个邮箱");
		
		//数据库不存在该username和email，可以注册		
		userDao.insertUser(userForm);
		
	}

	public User activeUser(String code) {
		User user = userDao.findUserByCode(code);
		
		//这人没有注册
		if(user == null) throw new RuntimeException("你这个坏蛋，还没注册，想干嘛呢你！");
		//已激活
		if(user.getState() == 1) throw new RuntimeException("已经激活过了，不要再搞事情了");
		//还未激活，更改状态码
		userDao.updateStateByCode(code);
		return user;
	}

	public User checkUser(String username, String password) {
		User haveUser = userDao.haveUser(username, password);
		if(haveUser == null) throw new RuntimeException("没有用户信息，请检查用户名或密码是否错误");
		if(haveUser.getState() != 1) throw new RuntimeException("账户还没有激活，请先到邮箱激活");
		return haveUser;
	}
}
