package enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单状态
 * <br>
 * created on 2019/01/04
 *
 * @author 巽
 **/
public enum OrderState {
	PAID("paid"), UNPAID("unpaid"), CANCELED("canceled");

	private final static Map<String, OrderState> map = new HashMap<>();

	private String value;

	static {
		for (OrderState orderState : OrderState.values()) {
			map.put(orderState.value, orderState);
		}
	}

	OrderState(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static OrderState of(String value) {
		return map.get(value);
	}
}
