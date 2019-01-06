package vo;

/**
 * 主页数据VO
 * <br>
 * created on 2019/01/06
 *
 * @author 巽
 **/
public class HomeVO {
	public int comId;
	public String name;
	public double unitPrice;
	public int number;

	public HomeVO(int comId, String name, double unitPrice, int number) {
		this.comId = comId;
		this.name = name;
		this.unitPrice = unitPrice;
		this.number = number;
	}
}
