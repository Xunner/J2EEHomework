package dao;

import model.User;

/**
 * 用户Dao
 * <br>
 * created on 2019/01/04
 *
 * @author 巽
 **/
public interface UserDao {
	User getById(String userId);
}
