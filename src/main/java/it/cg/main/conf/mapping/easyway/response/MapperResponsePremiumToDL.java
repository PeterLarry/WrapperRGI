package it.cg.main.conf.mapping.easyway.response;

import com.mapfre.engines.rating.business.objects.wrapper.Premium;
import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.IPremium;
import com.pass.global.CalculatePremiumResponse;

import it.cg.main.dto.main.Quote;

public class MapperResponsePremiumToDL
{
	public Quote getInitQuoteResponse(CalculatePremiumResponse responsePremium)
	{
		Quote responseQuote = new Quote();
		
		IPremium premiumObjResponse = new Premium();
		premiumObjResponse.setGross(responsePremium.getReturn().getOutput().getProduct().getPremium().getAnnual().getGross());
		premiumObjResponse.setNet(responsePremium.getReturn().getOutput().getProduct().getPremium().getAnnual().getNet());
		premiumObjResponse.setTax(responsePremium.getReturn().getOutput().getProduct().getPremium().getAnnual().getTaxes());
		premiumObjResponse.setSsn(responsePremium.getReturn().getOutput().getProduct().getPremium().getAnnual().getSSN());
		
		responseQuote.setPremium(premiumObjResponse );
		
		return responseQuote;
	}
}
