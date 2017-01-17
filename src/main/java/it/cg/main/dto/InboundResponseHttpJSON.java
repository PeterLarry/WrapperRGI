package it.cg.main.dto;

import it.cg.main.dto.main.Quote;
import it.cg.main.process.DtoImpl;

/**
 * DTO response to Direct Line
 * @author RiccardoEstia
 *
 */
public class InboundResponseHttpJSON extends DtoImpl
{
	private static final long serialVersionUID = 8248795967018752526L;
	private Quote quote;
	private Boolean success;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public Quote getQuote() {
		return quote;
	}

	public void setQuote(Quote quote) {
		this.quote = quote;
	}

}
