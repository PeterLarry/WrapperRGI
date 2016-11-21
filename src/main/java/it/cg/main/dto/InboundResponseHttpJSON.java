package it.cg.main.dto;

import com.pass.global.CalcolaPremioProdottoResponse;
import com.pass.global.CalculatePremiumResponse;

import it.cg.main.conf.DtoImpl;

/**
 * DTO exposed for the response for Direct Line
 * @author RiccardoEstia
 *
 */
public class InboundResponseHttpJSON
{
	private static final long serialVersionUID = 8248795967018752526L;
	private CalculatePremiumResponse calcolaPremioProdottoResponse;
	private String typeOfService;

	public CalculatePremiumResponse getCalcolaPremioProdottoResponse() {
		return calcolaPremioProdottoResponse;
	}

	public void setCalcolaPremioProdottoResponse(CalculatePremiumResponse calcolaPremioProdottoResponse) {
		this.calcolaPremioProdottoResponse = calcolaPremioProdottoResponse;
	}

	public String getTypeOfService() {
		return typeOfService;
	}

	public void setTypeOfService(String typeOfService) {
		this.typeOfService = typeOfService;
	}

}
