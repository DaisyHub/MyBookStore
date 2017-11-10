package com.edu118.bookstore.user.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.edu118.bookstore.user.domain.User;
import com.edu118.util.JdbcUtils;

public class UserDao {
	protected String findUserByUName = "select * from tb_user where username=?";
	protected String findUserByEmail = "select * from tb_user where email=?";
	protected String findUserByCode = "select * from tb_user where code=?";
	protected String updateStateByCode = "update tb_user set state=1 where code=?";
	protected String findUser = "select * from tb_user where username=? and password=?";
	protected String insertUser = "insert into tb_user values(?,?,?,?,?,?)";
	
	QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource());
	
	//ͨ���û��������û�
	public User findUserByUName(String username) {
		try {
			return qr.query(findUserByUName, new BeanHandler<User>(User.class), username);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	//ͨ��Email�����û�
	public User findUserByEmail(String email) {
		try {
			return qr.query(findUserByEmail, new BeanHandler<User>(User.class), email);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}		
	}
	
	//����û�
	public void insertUser(User userForm) {
		Object[] params = {
				userForm.getUid(),userForm.getUsername(),userForm.getPassword(),
				userForm.getEmail(),userForm.getCode(),userForm.getState()
		};		
		try {
			qr.update(insertUser, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public User findUserByCode(String code) {
		try {
			return qr.query(findUserByCode, new BeanHandler<User>(User.class), code);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	//��״̬���Ϊ1�������˻�
	public void updateStateByCode(String code) {
		try {
			qr.update(updateStateByCode, code);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	//��½��֤
	public User haveUser(String username, String password) {
		Object[] params = {username, password};
		try {
			return qr.query(findUser, new BeanHandler<User>(User.class), params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

}
