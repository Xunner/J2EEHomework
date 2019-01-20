package util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import po.CommodityPO;
import po.OrderPO;
import po.UserPO;

/**
 * Session获取工具类
 * <br>
 * created on 2019/01/20
 *
 * @author 巽
 **/
public class HibernateUtil {
	private static final SessionFactory sessionFactory;

	static {
		try {
			Configuration configuration = new Configuration();
			configuration.configure();
			configuration.addAnnotatedClass(CommodityPO.class);
			configuration.addAnnotatedClass(OrderPO.class);
			configuration.addAnnotatedClass(UserPO.class);

			sessionFactory = configuration.buildSessionFactory();
		} catch (Throwable ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static Session getSession() throws HibernateException {
		return sessionFactory.openSession();
	}
}
