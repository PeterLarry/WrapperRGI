package it.cg.main.dto;

public class RoutingDTO
{
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

	public String getExternalTypeForEasyWay() {
		return externalTypeForEasyWay;
	}

	public void setExternalTypeForEasyWay(String externalTypeForEasyWay) {
		this.externalTypeForEasyWay = externalTypeForEasyWay;
	}

	public String getExternalTypeForHardWay() {
		return externalTypeForHardWay;
	}

	public void setExternalTypeForHardWay(String externalTypeForHardWay) {
		this.externalTypeForHardWay = externalTypeForHardWay;
	}

	
}
