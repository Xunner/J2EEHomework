package dao.impl;

import dao.UserDao;
import po.UserPO;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 用户Dao实现类
 * <br>
 * created on 2019/01/06
 *
 * @author 巽
 **/
public class UserDaoImpl implements UserDao {
	private static UserDaoImpl singleImplement = new UserDaoImpl();
	private DataSource dataSource;

	private UserDaoImpl() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/J2EEHomework");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public static UserDaoImpl getInstance() {
		return singleImplement;
	}

	@Override
	public UserPO getById(String userId) {
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement preparedStatement = connection.prepareStatement(
				     "SELECT * FROM `user` WHERE user_id = ?")) {
			preparedStatement.setString(1, userId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				resultSet.first();
				return new UserPO(resultSet.getString("user_id"), resultSet.getString("password"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
