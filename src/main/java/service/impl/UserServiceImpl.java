package service.impl;

import dao.UserDao;
import enums.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import po.UserPO;
import service.UserService;

/**
 * 用户Service实现类
 * <br>
 * created on 2019/01/06
 *
 * @author 巽
 **/
@Service
public class UserServiceImpl implements UserService {
	private final UserDao userDao;

	@Autowired
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
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
