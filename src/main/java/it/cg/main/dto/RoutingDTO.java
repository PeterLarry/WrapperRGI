package it.cg.main.dto;

import it.cg.main.conf.DtoImpl;

public class RoutingDTO
{
	private static final long serialVersionUID = 2645400659518697356L;

	private String typeOf;

	private String externalTypeForEasyWay;
	private String externalTypeForHardWay;
	
	private InboundRequestHttpJSON requestHttpService;
	
	
	public String getTypeOf() {
		return typeOf;
	}

	public void setTypeOf(String typeOf) {
		this.typeOf = typeOf;
	}

	
	public InboundRequestHttpJSON getRequestHttpService() {
		return requestHttpService;
	}

	public void setRequestHttpService(InboundRequestHttpJSON requestHttpService) {
		this.requestHttpService = requestHttpService;
	}
	/**
	 * external constant for the EASY way field check
	 */
	public String getExternalTypeForEasyWay() {
		return externalTypeForEasyWay;
	}

	public void setExternalTypeForEasyWay(String externalTypeForEasyWay) {
		this.externalTypeForEasyWay = externalTypeForEasyWay;
	}
	/**
	 * external constant for the HARD way field check
	 */
	public String getExternalTypeForHardWay() {
		return externalTypeForHardWay;
	}

	public void setExternalTypeForHardWay(String externalTypeForHardWay) {
		this.externalTypeForHardWay = externalTypeForHardWay;
	}

	
}
