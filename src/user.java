import java.io.Serializable;

abstract public class user implements Serializable{
	private String loginName;
	private String hashPassword;// plain password

	public user(String loginName, String password) {
		this.loginName = loginName;
		this.hashPassword = Utility.getHash(password);
	}

	public String getLoginName() {
		return loginName;
	}

	//should be done using HASH algorithm
	public boolean checkPassword(String  password) {
		return this.hashPassword.equals(Utility.getHash(password));
	}
	public void display() {
		System.out.println(this.loginName+","+this.hashPassword);
//		System.out.println(this.loginName);
	}

	public String getHashPassword() {
		return hashPassword;
	}

	public void setHashPassword(String Password) {
		this.hashPassword = Utility.getHash(Password);
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}



}