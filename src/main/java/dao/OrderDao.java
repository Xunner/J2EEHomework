package dao;

import po.OrderPO;

/**
 * 订单Dao
 * <br>
 * created on 2019/01/04
 *
 * @author 巽
 **/
public interface OrderDao {
	OrderPO getById(int orderId);
	int addOrder(OrderPO orderPO);
}
