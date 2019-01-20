package dao.impl;

import dao.UserDao;
import org.hibernate.Session;
import po.UserPO;
import util.HibernateUtil;

/**
 * 用户Dao实现类
 * <br>
 * created on 2019/01/06
 *
 * @author 巽
 **/
public class UserDaoImpl implements UserDao {
	private static UserDaoImpl singleImplement = new UserDaoImpl();

	private UserDaoImpl() {
	}

	public static UserDaoImpl getInstance() {
		return singleImplement;
	}

	@Override
	public UserPO getById(String userId) {
		Session session = HibernateUtil.getSession();
		return session.load(UserPO.class, userId);
	}
}
