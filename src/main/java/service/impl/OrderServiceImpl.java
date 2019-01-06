package service.impl;

import dao.CommodityDao;
import dao.OrderDao;
import enums.OrderState;
import enums.Result;
import factory.DaoFactory;
import po.CommodityPO;
import po.OrderPO;
import service.OrderService;
import servlet.MainServlet;
import vo.PayVO;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 订单Service实现类
 * <br>
 * created on 2019/01/06
 *
 * @author 巽
 **/
public class OrderServiceImpl implements OrderService {
	private final static OrderServiceImpl singleImplement = new OrderServiceImpl();
	private final OrderDao orderDao = DaoFactory.getOrderDao();
	private final CommodityDao commodityDao = DaoFactory.getCommodityDao();

	private OrderServiceImpl() {
	}

	public static OrderServiceImpl getInstance() {
		return singleImplement;
	}

	@Override
	public PayVO generateOrder(String userId, Map<Integer, Integer> cart) {
		double price = 0.0;
		Map<CommodityPO, Integer> commodities = new HashMap<>();
		for (Integer comId : cart.keySet()) {
			CommodityPO commodity = commodityDao.getById(comId);
			if (commodity == null) return null;
			commodities.put(commodity, cart.get(comId));
			price += commodity.getPrice() * cart.get(comId);
		}
		double actualPayment = price;
		if (price >= MainServlet.DISCOUNT_THRESHOLD) {  // 消费金额高：打折
			actualPayment *= MainServlet.DISCOUNT;
		}
		Result result = orderDao.addOrder(new OrderPO(userId, price, actualPayment, LocalDateTime.now(), OrderState.UNPAID, commodities));
		return new PayVO(result, price, actualPayment);
	}
}
