package vo;

import enums.Result;

/**
 * 支付VO
 * <br>
 * created on 2019/01/06
 *
 * @author 巽
 **/
public class PayVO {
	public Result result;
	public double price;
	public double actualPayment;

	public PayVO(Result result, double price, double actualPayment) {
		this.result = result;
		this.price = price;
		this.actualPayment = actualPayment;
	}
}
