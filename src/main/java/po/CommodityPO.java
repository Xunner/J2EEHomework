package po;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

/**
 * 商品对象
 * <br>
 * created on 2019/01/04
 *
 * @author 巽
 **/
@Entity
@Table(name = "commodity", schema = "j2eehomework")
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

	public CommodityPO() {
	}

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "com_id")
	public int getComId() {
		return comId;
	}

	public void setComId(int comId) {
		this.comId = comId;
	}

	@Basic
	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic
	@Column(name = "price")
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Basic
	@Column(name = "comment")
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CommodityPO that = (CommodityPO) o;
		return comId == that.comId &&
				Double.compare(that.price, price) == 0 &&
				Objects.equals(name, that.name) &&
				Objects.equals(comment, that.comment);
	}

	@Override
	public int hashCode() {
		return Objects.hash(comId, name, price, comment);
	}
}
