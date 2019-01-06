package factory;

import dao.UserDao;
import dao.impl.UserDaoImpl;

/**
 * Dao工厂
 * <br>
 * created on 2019/01/06
 *
 * @author 巽
 **/
public class DaoFactory {
	public static UserDao getUserDao() {
		return UserDaoImpl.getInstance();
	}
}
