package dao.impl;

import dao.UserDao;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import po.UserPO;
import util.HibernateUtil;

/**
 * 用户Dao实现类
 * <br>
 * created on 2019/01/06
 *
 * @author 巽
 **/
@Repository
public class UserDaoImpl implements UserDao {
	@Override
	public UserPO getById(String userId) {
		Session session = HibernateUtil.getSession();
		return session.load(UserPO.class, userId);
	}
}
