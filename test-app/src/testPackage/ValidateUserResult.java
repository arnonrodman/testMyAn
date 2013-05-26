package testPackage;

import java.io.Serializable;

public class ValidateUserResult implements Serializable {

	private String result;

	public ValidateUserResult(String result) {
		super();
		this.result = result;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	
}
