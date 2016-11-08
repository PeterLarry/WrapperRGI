package it.cg.main.conf.error;

import java.io.Serializable;

public class ErrorIntegrationDTO implements Serializable
{

	private static final long serialVersionUID = 2061127219313719019L;

	private String errorCauseJava;
	private String errorMessageJava;
	private String errorSpecCauseJava;
	private String errorRootCauseJava;
	
	public String getErrorCauseJava() {
		return errorCauseJava;
	}
	public void setErrorCauseJava(String errorCauseJava) {
		this.errorCauseJava = errorCauseJava;
	}
	public String getErrorMessageJava() {
		return errorMessageJava;
	}
	public void setErrorMessageJava(String errorMessageJava) {
		this.errorMessageJava = errorMessageJava;
	}
	public String getErrorSpecCauseJava() {
		return errorSpecCauseJava;
	}
	public void setErrorSpecCauseJava(String errorSpecCauseJava) {
		this.errorSpecCauseJava = errorSpecCauseJava;
	}
	public String getErrorRootCauseJava() {
		return errorRootCauseJava;
	}
	public void setErrorRootCauseJava(String errorRootCauseJava) {
		this.errorRootCauseJava = errorRootCauseJava;
	}
	

}
