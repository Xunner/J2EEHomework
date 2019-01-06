package dao;

import po.UserPO;

/**
 * 用户Dao
 * <br>
 * created on 2019/01/04
 *
 * @author 巽
 **/
public interface UserDao {
	UserPO getById(String userId);
}
