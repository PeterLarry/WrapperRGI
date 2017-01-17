package it.cg.main.process.mapping.easyway.response;

import org.springframework.stereotype.Service;

import com.mapfre.engines.rating.business.objects.wrapper.CoveragePremium;
import com.mapfre.engines.rating.business.objects.wrapper.Premium;
import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.ICoveragePremium;
import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.IPremium;
import com.pass.global.TypeReal;
import com.pass.global.WsPremium;

@Service
public class ExternalCustomMapperResponse
{
	
//	public Quote getCustomOutput(WsProduct prod)
//	{
//		Quote response = new Quote();
//		
//		
//		
//		return response;
//	}
//	
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
	 * Mapping WsPremium to CoveragePremium
	 * @param WsPremium
	 * @return CoveragePremium
	 */
	public ICoveragePremium getCoverageFromUnitInstance(WsPremium wsPremium)
	{
		ICoveragePremium coveragePremium = new CoveragePremium();
		
		coveragePremium.setNet(wsPremium.getNet());
		coveragePremium.setGross(wsPremium.getGross());
		coveragePremium.setTax(wsPremium.getTaxes());
		coveragePremium.setSsn(wsPremium.getSSN());
		
		return coveragePremium;
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
