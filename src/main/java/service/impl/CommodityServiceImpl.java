package service.impl;

import dao.CommodityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
@Service
public class CommodityServiceImpl implements CommodityService {
	private final CommodityDao commodityDao;

	@Autowired
	public CommodityServiceImpl(CommodityDao commodityDao) {
		this.commodityDao = commodityDao;
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
