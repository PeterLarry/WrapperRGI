package it.cg.main.dto;

import org.springframework.stereotype.Component;

import it.cg.main.dto.main.Quote;

@Component
public class QuoteInternalComponent
{
	
	private Quote quoteInternal;

	public Quote getQuoteInternal() {
		return quoteInternal;
	}

	public void setQuoteInternal(Quote quoteInternal) {
		this.quoteInternal = quoteInternal;
	}

}
