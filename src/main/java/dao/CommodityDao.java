package dao;

import model.Commodity;

/**
 * 商品Dao
 * <br>
 * created on 2019/01/04
 *
 * @author 巽
 **/
public interface CommodityDao {
	Commodity getById(int comId);
}
