package it.cg.main.conf.mapping.easyway;

import org.springframework.stereotype.Service;

import com.mapfre.engines.rating.business.objects.wrapper.Premium;
import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.IPremium;
import com.pass.global.TypeReal;
import com.pass.global.WsPremium;
import com.pass.global.WsProduct;

import it.cg.main.dto.main.Quote;

@Service
public class ExternalCustomMapperResponse
{
	
	public Quote getCustomOutput(WsProduct prod)
	{
		Quote response = new Quote();
		
		
		
		return response;
	}
	
	/**
	 * Premium annual from WsProduct's Level 
	 * @param premiumAnnual
	 * @return
	 */
	public IPremium getPremiumQuoteFromProdAnnual(WsPremium premiumAnnual)
	{
		Premium pre = new Premium();
		if(premiumAnnual != null)
		{
			pre.setNet(premiumAnnual.getNet());
			pre.setGross(premiumAnnual.getGross());
			pre.setTax(premiumAnnual.getTaxes());
			pre.setSsn(premiumAnnual.getSSN());
		}
		
		return pre;
	}
	
	/**
	 * Generic type double
	 * @param doub
	 * @return
	 */
	public TypeReal boolToTypeBool(Double doub) {
		
		TypeReal typeR = new TypeReal();
		
		typeR.setReal(doub);
		
		return typeR;
	}
}
