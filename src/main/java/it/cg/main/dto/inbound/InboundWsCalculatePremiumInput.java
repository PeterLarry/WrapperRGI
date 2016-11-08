package it.cg.main.dto.inbound;

import java.io.Serializable;

public class InboundWsCalculatePremiumInput implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7628367507408160342L;

	/**
	 * NULL o FALSE
	 */
	private Boolean quoteMode;
	
	/**
	 * VERO
	 */
	private Boolean adaptToMinimumPremium;
	
	/**
	 * VERO
	 */
	private Boolean applyDiscount;
	
	private InboundWsProductDTO product;
}
