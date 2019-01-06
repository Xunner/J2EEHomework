package service.impl;

import dao.CommodityDao;
import factory.DaoFactory;
import po.CommodityPO;
import service.CommodityService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 商品相关Service实现类
 * <br>
 * created on 2019/01/06
 *
 * @author 巽
 **/
public class CommodityServiceImpl implements CommodityService {
	private final static CommodityServiceImpl singleImplement = new CommodityServiceImpl();
	private final CommodityDao commodityDao = DaoFactory.getCommodityDao();

	private CommodityServiceImpl() {
	}

	public static CommodityServiceImpl getInstance() {
		return singleImplement;
	}

	@Override
	public List<CommodityPO> getByIds(Set<Integer> comIds) {
		List<CommodityPO> ret = new ArrayList<>();
		for (Integer comId : comIds) {
			CommodityPO found = commodityDao.getById(comId);
			if (found != null) {
				ret.add(found);
			}
		}
		return ret;
	}

	@Override
	public List<CommodityPO> getAll() {
		return commodityDao.getAll();
	}
}
