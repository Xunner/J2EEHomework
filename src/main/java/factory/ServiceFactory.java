package factory;

import service.UserService;
import service.impl.UserServiceImpl;

/**
 * Service工厂
 * <br>
 * created on 2019/01/06
 *
 * @author 巽
 **/
public class ServiceFactory {
	public static UserService getUserService() {
		return UserServiceImpl.getInstance();
	}
}
