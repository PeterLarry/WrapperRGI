package it.cg.main.dto.inbound;

import java.io.Serializable;

public class InboundWsAssetDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8510763566841584917L;

	/**
	 * Codice PASS
	 * /**
	 * I valori possibili di code saranno gestiti con le enum o tramite conf esterne
	 * Lista di valori possibili di code:
	 *  as1,av1,bp1,coll1,cri1,en1,fur1,iip1,im1,inc1,kas1,rca1,rca11,rca12,rca14,rca15,rca16,rca17,rca2,rca3,rca4,rca5,rca6,rca7,rca8,rca9,rcar1,sosp1,tg1,tg2
	 */
	private String code;
	
	private InboundWsAssetInstanceDTO instances;
	private InboundWsVehicleDTO vehicles;

}
