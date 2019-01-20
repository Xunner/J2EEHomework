package po;

import enums.OrderState;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

/**
 * 订单对象
 * <br>
 * created on 2019/01/04
 *
 * @author 巽
 **/
@Entity
@Table(name = "`order`", schema = "j2eehomework")
@Data
public class OrderPO {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "order_id")
	private int orderId;

	@Basic
	@Column(name = "user_id")
	private String userId;

	@Basic
	@Column(name = "price")
	private double price;

	@Basic
	@Column(name = "actual_payment")
	private double actualPayment;

	@Column(name = "time")
	private LocalDateTime time;

	@Enumerated(EnumType.STRING)
	private OrderState state;

	@ElementCollection
	@CollectionTable(name="order_com_map", joinColumns = @JoinColumn(name = "order_id"))
	@MapKeyJoinColumn(name="com_id")
	@Column(name="number")
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

	public OrderPO() {
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		OrderPO orderPO = (OrderPO) o;
		return orderId == orderPO.orderId &&
				Double.compare(orderPO.price, price) == 0 &&
				Double.compare(orderPO.actualPayment, actualPayment) == 0 &&
				Objects.equals(userId, orderPO.userId) &&
				Objects.equals(time, orderPO.time) &&
				state == orderPO.state;
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderId, userId, price, actualPayment, time, state);
	}
}
