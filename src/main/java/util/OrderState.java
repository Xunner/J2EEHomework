package util;

/**
 * 订单状态
 * <br>
 * created on 2019/01/04
 *
 * @author 巽
 **/
public enum OrderState {
	PAID("paid"), UNPAID("unpaid"), CANCELED("canceled");

	private String value;

	OrderState(String value) {
		this.value = value;
	}
}
