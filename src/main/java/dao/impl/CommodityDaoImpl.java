package dao.impl;

import dao.CommodityDao;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import po.CommodityPO;
import util.HibernateUtil;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/**
 * 商品Dao实现类
 * <br>
 * created on 2019/01/06
 *
 * @author 巽
 **/
@Repository
public class CommodityDaoImpl implements CommodityDao {
	@Override
	public CommodityPO getById(int comId) {
		Session session = HibernateUtil.getSession();
		return session.load(CommodityPO.class, comId);
	}

	@Override
	public List<CommodityPO> getAll() {
		Session session = HibernateUtil.getSession();
		CriteriaQuery<CommodityPO> criteriaQuery = session.getCriteriaBuilder().createQuery(CommodityPO.class);
		criteriaQuery.from(CommodityPO.class);
		return session.createQuery(criteriaQuery).getResultList();
	}
}
