package po;

import lombok.Data;

/**
 * 用户对象
 * <br>
 * created on 2019/01/04
 *
 * @author 巽
 **/
@Data
public class UserPO {
	private String userId;
	private String password;

	public UserPO(String userId, String password) {
		this.userId = userId;
		this.password = password;
	}
}
