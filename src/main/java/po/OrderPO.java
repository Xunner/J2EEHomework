package po;

import enums.OrderState;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 订单对象
 * <br>
 * created on 2019/01/04
 *
 * @author 巽
 **/
@Data
public class OrderPO {
	private int orderId;
	private String userId;
	private double price;
	private double actualPayment;
	private LocalDateTime time;
	private OrderState state;
	private Map<CommodityPO, Integer> commoditiesList;

	public OrderPO(String userId, double price, double actualPayment, LocalDateTime time, OrderState state, Map<CommodityPO, Integer> commoditiesList) {
		this.userId = userId;
		this.price = price;
		this.actualPayment = actualPayment;
		this.time = time;
		this.state = state;
		this.commoditiesList = commoditiesList;
	}

	public OrderPO(int orderId, String userId, double price, double actualPayment, LocalDateTime time, OrderState state, Map<CommodityPO, Integer> commoditiesList) {
		this.orderId = orderId;
		this.userId = userId;
		this.price = price;
		this.actualPayment = actualPayment;
		this.time = time;
		this.state = state;
		this.commoditiesList = commoditiesList;
	}
}
