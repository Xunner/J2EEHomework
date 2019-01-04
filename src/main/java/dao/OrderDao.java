package dao;

import model.Order;

/**
 * 订单Dao
 * <br>
 * created on 2019/01/04
 *
 * @author 巽
 **/
public interface OrderDao {
	Order getById(int orderId);
	int addOrder(Order order);
}
