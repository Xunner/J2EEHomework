package dao;

import enums.Result;
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

	Result addOrder(OrderPO orderPO);
}
