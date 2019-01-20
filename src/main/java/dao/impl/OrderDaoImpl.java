package dao.impl;

import dao.OrderDao;
import enums.Result;
import org.hibernate.Session;
import org.hibernate.Transaction;
import po.OrderPO;
import util.HibernateUtil;

/**
 * no_description
 * <br>
 * created on 2019/01/06
 *
 * @author 巽
 **/
public class OrderDaoImpl implements OrderDao {
	private static OrderDaoImpl singleImplement = new OrderDaoImpl();

	private OrderDaoImpl() {
	}

	public static OrderDaoImpl getInstance() {
		return singleImplement;
	}

	@Override
	public OrderPO getById(int orderId) {
		Session session = HibernateUtil.getSession();
		return session.load(OrderPO.class, orderId);
	}

	@Override
	public Result addOrder(OrderPO order) {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		session.save(order);
		tx.commit();
		return Result.SUCCESS;
	}
}
