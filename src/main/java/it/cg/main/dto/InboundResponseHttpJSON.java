package it.cg.main.dto;

import com.pass.global.CalcolaPremioProdottoResponse;

public class InboundResponseHttpJSON
{
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
