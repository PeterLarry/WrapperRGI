package it.cg.main.dto;

import it.cg.main.conf.error.DtoImpl;
import it.cg.main.dto.inbound.InboundWsProductDTO;

/**
 * DTO exposed for the request from Direct Line
 * @author RiccardoEstia
 *
 */
public class InboundRequestHttpJSON extends DtoImpl
{
	private static final long serialVersionUID = -4058572266373539270L;

	/**
	 * Service type variable request from external service
	 */
	private String serviceType;
	/**
	 * Start Object for pass
	 */
	private InboundWsProductDTO wsProductDTO;
	
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public InboundWsProductDTO getWsProductDTO() {
		return wsProductDTO;
	}
	public void setWsProductDTO(InboundWsProductDTO wsProductDTO) {
		this.wsProductDTO = wsProductDTO;
	}
	
}
