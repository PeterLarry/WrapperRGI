package it.cg.main.dto;

import com.blog.samples.webservices.servicedetail.ServiceCallRequest;

public class RoutingDTO
{
	private String typeOf;
	private ServiceCallRequest requestService;
	private InboundRequestHttpJSON requestHttpService;
	
	
	public String getTypeOf() {
		return typeOf;
	}

	public void setTypeOf(String typeOf) {
		this.typeOf = typeOf;
	}

	public ServiceCallRequest getRequestService() {
		return requestService;
	}

	public void setRequestService(ServiceCallRequest requestService) {
		this.requestService = requestService;
	}

	public InboundRequestHttpJSON getRequestHttpService() {
		return requestHttpService;
	}

	public void setRequestHttpService(InboundRequestHttpJSON requestHttpService) {
		this.requestHttpService = requestHttpService;
	}

	
}
