package it.cg.main.dto;

import com.pass.global.CalcolaPremioProdottoResponse;

import it.cg.main.conf.error.DtoImpl;

public class InboundResponseHttpJSON extends DtoImpl
{
	private static final long serialVersionUID = 8248795967018752526L;
	private CalcolaPremioProdottoResponse calcolaPremioProdottoResponse;
	private String typeOfService;

	public CalcolaPremioProdottoResponse getCalcolaPremioProdottoResponse() {
		return calcolaPremioProdottoResponse;
	}

	public void setCalcolaPremioProdottoResponse(CalcolaPremioProdottoResponse calcolaPremioProdottoResponse) {
		this.calcolaPremioProdottoResponse = calcolaPremioProdottoResponse;
	}

	public String getTypeOfService() {
		return typeOfService;
	}

	public void setTypeOfService(String typeOfService) {
		this.typeOfService = typeOfService;
	}

}
