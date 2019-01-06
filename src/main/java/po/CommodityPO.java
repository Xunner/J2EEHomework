package po;

import lombok.Data;

/**
 * 商品对象
 * <br>
 * created on 2019/01/04
 *
 * @author 巽
 **/
@Data
public class CommodityPO {
	private int comId;
	private String name;
	private double price;
	private String comment;

	public CommodityPO(int comId, String name, double price, String comment) {
		this.comId = comId;
		this.name = name;
		this.price = price;
		this.comment = comment;
	}
}
