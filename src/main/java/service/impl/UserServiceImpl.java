package service.impl;

import dao.UserDao;
import enums.Result;
import factory.DaoFactory;
import po.UserPO;
import service.UserService;

/**
 * 用户Service实现类
 * <br>
 * created on 2019/01/06
 *
 * @author 巽
 **/
public class UserServiceImpl implements UserService {
	private final static UserServiceImpl singleImplement = new UserServiceImpl();
	private final UserDao userDao = DaoFactory.getUserDao();

	private UserServiceImpl() {
	}

	public static UserServiceImpl getInstance() {
		return singleImplement;
	}

	@Override
	public Result logIn(String userId, String password) {
		UserPO userPO = userDao.getById(userId);
		if (userPO != null && userPO.getPassword().equals(password)) {
			return Result.SUCCESS;
		}
		return Result.FAILED;
	}
}
