package factory;

import service.CommodityService;
import service.OrderService;
import service.UserService;
import service.impl.CommodityServiceImpl;
import service.impl.OrderServiceImpl;
import service.impl.UserServiceImpl;

/**
 * Service工厂
 * <br>
 * created on 2019/01/06
 *
 * @author 巽
 **/
public class ServiceFactory {
	public static UserService getUserService() {
		return UserServiceImpl.getInstance();
	}

	public static OrderService getOrderService() {
		return OrderServiceImpl.getInstance();
	}

	public static CommodityService getCommodityService() {
		return CommodityServiceImpl.getInstance();
	}
}
