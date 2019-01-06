package factory;

import dao.CommodityDao;
import dao.OrderDao;
import dao.UserDao;
import dao.impl.CommodityDaoImpl;
import dao.impl.OrderDaoImpl;
import dao.impl.UserDaoImpl;

/**
 * Dao工厂
 * <br>
 * created on 2019/01/06
 *
 * @author 巽
 **/
public class DaoFactory {
	public static UserDao getUserDao() {
		return UserDaoImpl.getInstance();
	}

	public static OrderDao getOrderDao() {
		return OrderDaoImpl.getInstance();
	}

	public static CommodityDao getCommodityDao() {
		return CommodityDaoImpl.getInstance();
	}
}
