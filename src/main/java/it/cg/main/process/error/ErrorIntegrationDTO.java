package it.cg.main.conf.error;

import java.io.Serializable;
import java.util.List;

public class ErrorIntegrationDTO implements Serializable
{

	private static final long serialVersionUID = 2061127219313719019L;
//	---- Java Exceptions ----
	private String errorCauseJava;
	private String errorMessageJava;
	private String errorSpecCauseJava;
	private String errorRootCauseJava;
//	---- Custom error PASS ----
	private short customCodiceErroreOccorso;
	private String customDescrizioneErroreOccorso;
	private List<String> customDettagliSullErroreOccorsos;
	private byte[] customEccezioneOccorsaSerializzata;
	private boolean customErroreOccorso;
	private String customExecutionId;
	private byte[] customLogs;
	private short customTipoErroreOccorso;
	
//	------------------
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
//	-----------------------
	public short getCustomCodiceErroreOccorso() {
		return customCodiceErroreOccorso;
	}
	public void setCustomCodiceErroreOccorso(short customCodiceErroreOccorso) {
		this.customCodiceErroreOccorso = customCodiceErroreOccorso;
	}
	public String getCustomDescrizioneErroreOccorso() {
		return customDescrizioneErroreOccorso;
	}
	public void setCustomDescrizioneErroreOccorso(String customDescrizioneErroreOccorso) {
		this.customDescrizioneErroreOccorso = customDescrizioneErroreOccorso;
	}
	public List<String> getCustomDettagliSullErroreOccorsos() {
		return customDettagliSullErroreOccorsos;
	}
	public void setCustomDettagliSullErroreOccorsos(List<String> customDettagliSullErroreOccorsos) {
		this.customDettagliSullErroreOccorsos = customDettagliSullErroreOccorsos;
	}
	public byte[] getCustomEccezioneOccorsaSerializzata() {
		return customEccezioneOccorsaSerializzata;
	}
	public void setCustomEccezioneOccorsaSerializzata(byte[] customEccezioneOccorsaSerializzata) {
		this.customEccezioneOccorsaSerializzata = customEccezioneOccorsaSerializzata;
	}
	public boolean isCustomErroreOccorso() {
		return customErroreOccorso;
	}
	public void setCustomErroreOccorso(boolean customErroreOccorso) {
		this.customErroreOccorso = customErroreOccorso;
	}
	public String getCustomExecutionId() {
		return customExecutionId;
	}
	public void setCustomExecutionId(String customExecutionId) {
		this.customExecutionId = customExecutionId;
	}
	public byte[] getCustomLogs() {
		return customLogs;
	}
	public void setCustomLogs(byte[] customLogs) {
		this.customLogs = customLogs;
	}
	public short getCustomTipoErroreOccorso() {
		return customTipoErroreOccorso;
	}
	public void setCustomTipoErroreOccorso(short customTipoErroreOccorso) {
		this.customTipoErroreOccorso = customTipoErroreOccorso;
	}
		

}
