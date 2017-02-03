package it.cg.main.dto;

import it.cg.main.process.DtoImpl;

public class RoutingDTO extends DtoImpl
{
	private static final long serialVersionUID = 2645400659518697356L;

	private String typeOf;

	private String externalTypeForEasyWay;
	
	private InboundRequestHttpJSON inboundRequestHttpJSON;
	
	
	public String getTypeOf() {
		return typeOf;
	}

	public void setTypeOf(String typeOf) {
		this.typeOf = typeOf;
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
	 * @return the inboundRequestHttpJSON
	 */
	public InboundRequestHttpJSON getInboundRequestHttpJSON() {
		return inboundRequestHttpJSON;
	}

	/**
	 * @param inboundRequestHttpJSON the inboundRequestHttpJSON to set
	 */
	public void setInboundRequestHttpJSON(InboundRequestHttpJSON inboundRequestHttpJSON) {
		this.inboundRequestHttpJSON = inboundRequestHttpJSON;
	}

	
}
