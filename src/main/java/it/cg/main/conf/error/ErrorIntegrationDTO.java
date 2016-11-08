package it.cg.main.conf.error;

import java.io.Serializable;

public class ErrorIntegrationDTO implements Serializable
{

	private static final long serialVersionUID = 2061127219313719019L;
	
	private String errorType;
	private String errorMessage;
	
	public String getErrorType() {
		return errorType;
	}
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
