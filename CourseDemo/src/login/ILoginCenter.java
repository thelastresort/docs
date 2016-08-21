package login;

/**
 * 接口不关心内部实现，是否用activity、dialog或者只是底层请求
 * @author jammy
 *
 */
public interface ILoginCenter {
	
	public boolean login(String userName, String password);
	
	public boolean login(LoginParam loginParam);
	
	public void login(LoginParam loginParam, ILoginObsv obsv);
}
