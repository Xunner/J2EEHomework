package service;

import po.CommodityPO;

import java.util.List;
import java.util.Set;

/**
 * 商品相关Service
 * <br>
 * created on 2019/01/06
 *
 * @author 巽
 **/
public interface CommodityService {
	List<CommodityPO> getByIds(Set<Integer> comIds);

	List<CommodityPO> getAll();
}
