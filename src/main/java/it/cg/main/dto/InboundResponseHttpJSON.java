package it.cg.main.dto;

import it.cg.main.conf.DtoImpl;
import it.cg.main.dto.inbound.InboundQuoteDTO;

/**
 * DTO response to Direct Line
 * @author RiccardoEstia
 *
 */
public class InboundResponseHttpJSON extends DtoImpl
{
	private static final long serialVersionUID = 8248795967018752526L;
	private InboundQuoteDTO inboundQuoteDTO;
	private Boolean success;
//	----------------------
//	private CalculatePremiumResponse calcolaPremioProdottoResponse;
//	private String typeOfService;
//	private GetFactorsResponse getFactorsResponse;


	public InboundQuoteDTO getInboundQuoteDTO() {
		return inboundQuoteDTO;
	}

	public void setInboundQuoteDTO(InboundQuoteDTO inboundQuoteDTO) {
		this.inboundQuoteDTO = inboundQuoteDTO;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

}
