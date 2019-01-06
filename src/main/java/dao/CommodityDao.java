package dao;

import po.CommodityPO;

/**
 * 商品Dao
 * <br>
 * created on 2019/01/04
 *
 * @author 巽
 **/
public interface CommodityDao {
	CommodityPO getById(int comId);
}
