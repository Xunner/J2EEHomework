package po;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

/**
 * 用户对象
 * <br>
 * created on 2019/01/04
 *
 * @author 巽
 **/
@Entity
@Table(name = "user", schema = "j2eehomework")
@Data
public class UserPO {
	private String userId;
	private String password;

	public UserPO(String userId, String password) {
		this.userId = userId;
		this.password = password;
	}

	public UserPO() {
	}

	@Id
	@Column(name = "user_id")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Basic
	@Column(name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserPO userPO = (UserPO) o;
		return Objects.equals(userId, userPO.userId) &&
				Objects.equals(password, userPO.password);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId, password);
	}
}
