package it.cg.main.dto.inbound;

import java.io.Serializable;
import java.util.Date;

public class InboundWsProductDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 929296433298130347L;


	/**
	 * 	- 000001= AUTO DLI<br>
		- 000002= MOTOCICLO DLI<br>
		- 000003= ALTRI VEICOLI DLI<br>
		- 000004= AUTO ADI<br>
		Estrazione valori possibili Pass. E' la colonna B del folder Tabella sezioni unit
	 */
	private String code;

	
	/**
	 * Data effetto preventivo/polizza
	 */
	private Date openDate;
	
	
	/**
	 * 	- 000001 = Annuale
		- 000002 = Semestrale
	 */
	private String paymentFrequencyCode;
	
	/**
	 * 	  000001 = Euro
	 */
	private String currencyCode;
	
	/**
	 * 	- 000074 = Batch rinnovo I livello (-54 gg)
		- 000075 = Batch rinnovo II livello (- 45 gg)
		
		Si prevede di utilizzare il campo per censire le chiamate per il calcolo del fiddle factor 
		del batch dei rinnovi a -54 e -45 gg . Pass deve fornire i 2 codici delle 2 chiamate del batch rinnovi
		Colonna B sheet Operazioni
	 */
	private String operationCode;
	
	private InboundWsFactorProductDTO factors;
	
	/**
	 * Non c'è sull'excel delle clausole (wsclause) con nodo padre il prodotto(wsproduct)
	 */
	private InboundWsClauseDTO clauses;
	private InboundWsAssetDTO assets;
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Date getOpenDate() {
		return openDate;
	}
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}
	public String getPaymentFrequencyCode() {
		return paymentFrequencyCode;
	}
	public void setPaymentFrequencyCode(String paymentFrequencyCode) {
		this.paymentFrequencyCode = paymentFrequencyCode;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getOperationCode() {
		return operationCode;
	}
	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}
	public InboundWsClauseDTO getClauses() {
		return clauses;
	}
	public void setClauses(InboundWsClauseDTO clauses) {
		this.clauses = clauses;
	}
	public InboundWsAssetDTO getAssets() {
		return assets;
	}
	public void setAssets(InboundWsAssetDTO assets) {
		this.assets = assets;
	}
	
	
}
