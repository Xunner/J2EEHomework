package dao;

import po.CommodityPO;

import java.util.List;

/**
 * 商品Dao
 * <br>
 * created on 2019/01/04
 *
 * @author 巽
 **/
public interface CommodityDao {
	CommodityPO getById(int comId);

	List<CommodityPO> getAll();
}
