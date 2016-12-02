package it.cg.main.dto;

import it.cg.main.conf.DtoImpl;
import it.cg.main.dto.main.Quote;

/**
 * DTO exposed for the request from Direct Line
 * @author RiccardoEstia
 *
 */
public class InboundRequestHttpJSON extends DtoImpl
{
	private static final long serialVersionUID = -4058572266373539270L;

	private Quote inboundQuoteDTO;
	/**
	 * Service type variable request from external service
	 */
	private String serviceType;

	/**
	 * @return the serviceType
	 */
	public String getServiceType() {
		return serviceType;
	}
	/**
	 * @param serviceType the serviceType to set
	 */
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public Quote getInboundQuoteDTO() {
		return inboundQuoteDTO;
	}
	public void setInboundQuoteDTO(Quote inboundQuoteDTO) {
		this.inboundQuoteDTO = inboundQuoteDTO;
	}
	

	
}
