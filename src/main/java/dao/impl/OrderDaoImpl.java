package dao.impl;

import dao.CommodityDao;
import dao.OrderDao;
import enums.Result;
import factory.DaoFactory;
import po.CommodityPO;
import po.OrderPO;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * no_description
 * <br>
 * created on 2019/01/06
 *
 * @author 巽
 **/
public class OrderDaoImpl implements OrderDao {
	private static OrderDaoImpl singleImplement = new OrderDaoImpl();
	private final CommodityDao commodityDao = DaoFactory.getCommodityDao();
	private DataSource dataSource;

	private OrderDaoImpl() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/J2EEHomework");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public static OrderDaoImpl getInstance() {
		return singleImplement;
	}

	@Override
	public OrderPO getById(int orderId) {
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement preparedStatement = connection.prepareStatement(
				     "SELECT * FROM `order` WHERE order_id = ?")) {
			preparedStatement.setInt(1, orderId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				resultSet.first();
//				return new OrderPO(resultSet.getInt("order_id"),
//						resultSet.getString("user_id"),
//						resultSet.getDouble("price"),
//						resultSet.getDouble("actual_payment"),
//						resultSet.getTimestamp("time").toLocalDateTime(),
//						OrderState.of(resultSet.getString("state")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Result addOrder(OrderPO order) {
		Result ret = Result.FAILED;
		try (Connection connection = dataSource.getConnection()) {
			try (PreparedStatement newOrderPS = connection.prepareStatement(
					"INSERT INTO `order`(user_id, price, actual_payment, time, state) VALUES(?,?,?,?,?)",
					PreparedStatement.RETURN_GENERATED_KEYS);
			     PreparedStatement orderMapPS = connection.prepareStatement(
					     "INSERT INTO order_com_map(order_id, com_id, number) VALUES(?,?,?)")) {
				// 添加订单
				newOrderPS.setString(1, order.getUserId());
				newOrderPS.setDouble(2, order.getPrice());
				newOrderPS.setDouble(3, order.getActualPayment());
				newOrderPS.setObject(4, order.getTime());
				newOrderPS.setString(5, order.getState().getValue());
				// 事务：添加订单及详情
				connection.setAutoCommit(false);
				newOrderPS.executeUpdate();
				int orderId;
				try (ResultSet resultSet = newOrderPS.getGeneratedKeys()) {
					resultSet.first();
					orderId = resultSet.getInt(1);
				}
				if (orderId > 0) {
					// 添加订单成功，依据orderId添加详情
					for (CommodityPO commodity : order.getCommoditiesList().keySet()) {
						orderMapPS.setInt(1, orderId);
						orderMapPS.setInt(2, commodity.getComId());
						orderMapPS.setInt(3, order.getCommoditiesList().get(commodity));
						orderMapPS.addBatch();      // 批量提交
					}
					orderMapPS.executeBatch();
					connection.commit();            // 提交事务
					connection.setAutoCommit(true); // 恢复自动commit
					ret = Result.SUCCESS;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				if (!connection.getAutoCommit()) {
					connection.rollback();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}
}
