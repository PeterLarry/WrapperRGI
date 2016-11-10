package it.cg.main.dto.inbound;

import java.io.Serializable;

public class InboundWsAssetSectionDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5063286799467616224L;

	/**
	 * I valori possibili di code saranno gestiti con le enum o tramite conf esterne
	 * Lista di valori possibili di code:
	 *  S1,S2,S3,S4,S5,S6
	 */
	private String code;
	
	/**
	 * Per questi parametri controllare bene il foglio excel
	 */
	private InboundWsFactorDTO factors;
	private InboundWsClauseDTO clauses;
	private InboundWsAssetUnitDTO units;
	

}
