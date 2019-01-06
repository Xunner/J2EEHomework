package service;

import enums.Result;

/**
 * 用户Service
 * <br>
 * created on 2019/01/06
 *
 * @author 巽
 **/
public interface UserService {
	Result logIn(String userId, String password);
}
