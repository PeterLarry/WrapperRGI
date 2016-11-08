package it.cg.main.dto.inbound;

import java.io.Serializable;

public class InboundWsVehicleDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2939006407282259810L;

	/**
	 * I valori possibili di classCode saranno gestiti con le enum o tramite conf esterne
	 * Lista di valori possibili di classCode:
	 * 01,02,06,07,09,10,11,12,15,19,22,30,38,74,76,980,988,989,991,995,996
	 */
	private String classCode;
	
	/**
	 * Codifica Pass
	 * LIsta valori possibili sectorCode
	 * 000001,000003,000004,000005,000006,000007,000008
	 */
	private String sectorCode;
	
	
	/**
	 * Codifica Pass
	 * Lista valori possibili useCode
	 * 000001,000002,000003,000004,000005,000006,000007,000008,000009,000010,000011,000012,000013,000014,000015,000016,000017,000018,000022,000023,000024,000025,000026,000027,000028,000029
	 */
	private String useCode;

}
