package po;

import enums.OrderState;
import lombok.Data;

import java.time.LocalDateTime;

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
}