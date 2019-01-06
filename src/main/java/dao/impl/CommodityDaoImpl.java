package dao.impl;

import dao.CommodityDao;
import po.CommodityPO;
import po.UserPO;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品Dao实现类
 * <br>
 * created on 2019/01/06
 *
 * @author 巽
 **/
public class CommodityDaoImpl implements CommodityDao {
	private static CommodityDaoImpl singleImplement = new CommodityDaoImpl();
	private DataSource dataSource;

	private CommodityDaoImpl() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/J2EEHomework");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public static CommodityDaoImpl getInstance() {
		return singleImplement;
	}

	@Override
	public CommodityPO getById(int comId) {
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement preparedStatement = connection.prepareStatement(
				     "SELECT * FROM `commodity` WHERE com_id = ?")) {
			preparedStatement.setInt(1, comId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				resultSet.first();
				return new CommodityPO(resultSet.getInt("com_id"),
						resultSet.getString("name"),
						resultSet.getDouble("price"),
						resultSet.getString("comment"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<CommodityPO> getAll() {
		List<CommodityPO> ret = new ArrayList<>();
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement preparedStatement = connection.prepareStatement(
				     "SELECT * FROM `commodity` LIMIT 100")) {
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					ret.add(new CommodityPO(resultSet.getInt("com_id"),
							resultSet.getString("name"),
							resultSet.getDouble("price"),
							resultSet.getString("comment")));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}
}
