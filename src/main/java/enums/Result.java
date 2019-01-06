package enums;

/**
 * 执行结果
 * <br>
 * created on 2019/01/06
 *
 * @author 巽
 **/
public enum Result {
	SUCCESS("success"), FAILED("failed");

	private String value;

	Result(String value) {
		this.value = value;
	}
}
