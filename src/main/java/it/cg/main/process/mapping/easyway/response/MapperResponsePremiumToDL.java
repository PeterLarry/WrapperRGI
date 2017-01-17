package it.cg.main.process.mapping.easyway.response;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mapfre.engines.rating.business.objects.wrapper.Coverage;
import com.mapfre.engines.rating.business.objects.wrapper.Premium;
import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.ICoverage;
import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.IPremium;
import com.pass.global.CalculatePremiumResponse;
import com.pass.global.WsAssetSection;
import com.pass.global.WsAssetUnit;

import it.cg.main.dto.main.Quote;

public class MapperResponsePremiumToDL
{
private Logger logger = Logger.getLogger(getClass());
	
	/**
	 * Get principal response from premium pass
	 * @param responsePremium
	 * @return
	 */
	public Quote getInitQuoteResponse(CalculatePremiumResponse responsePremium)
	{
		logger.info("into getInitQuoteResponse with input : "+responsePremium);
		Quote responseQuote = new Quote();
		
		IPremium premiumObjResponse = new Premium();
		premiumObjResponse.setGross(responsePremium.getReturn().getOutput().getProduct().getPremium().getAnnual().getGross());
		premiumObjResponse.setNet(responsePremium.getReturn().getOutput().getProduct().getPremium().getAnnual().getNet());
		premiumObjResponse.setTax(responsePremium.getReturn().getOutput().getProduct().getPremium().getAnnual().getTaxes());
		premiumObjResponse.setSsn(responsePremium.getReturn().getOutput().getProduct().getPremium().getAnnual().getSSN());
		
		responseQuote.setPremium(premiumObjResponse );
		
		logger.info("into getInitQuoteResponse with output : "+responseQuote);
		return responseQuote;
	}
	
	/**
	 * Return warranties list from response
	 * @param responsePremium
	 * @return ICoverage, null if some error
	 */
	public List<ICoverage> getCoveragesFromPass(CalculatePremiumResponse responsePremium)
	{
		logger.info("into getCoveragesFromPass with input : "+responsePremium);
		List<ICoverage> responseListCoverages = new ArrayList<>(0);
		WsAssetSection assetSection = getAssetUnitsValorized(responsePremium);
		if(assetSection == null)
		{
			responseListCoverages = null ;
		}
		else
		{
			for (WsAssetUnit assetUnitTemp : assetSection.getUnits())
			{
				ICoverage coverageToAdd = new Coverage();
				coverageToAdd.getAmount().setNet(assetUnitTemp.getInstances().get(0).getPremium().getAnnual().getNet());
				coverageToAdd.getAmount().setGross(assetUnitTemp.getInstances().get(0).getPremium().getAnnual().getGross());
				coverageToAdd.getAmount().setSsn(assetUnitTemp.getInstances().get(0).getPremium().getAnnual().getSSN());
				coverageToAdd.getAmount().setTax(assetUnitTemp.getInstances().get(0).getPremium().getAnnual().getTaxes());
			}
		}
		
		logger.info("into getCoveragesFromPass with input : "+responseListCoverages);
		return responseListCoverages;
	}
	
	
	/**
	 * If The assetsection or the unitinstaces or the assetunit are empty, the return is null
	 * @param cpResponse
	 * @return AssetSection with unitinstances
	 */
	private WsAssetSection getAssetUnitsValorized(CalculatePremiumResponse cpResponse)
	{
		WsAssetSection responseAssetSection = null; 
		try
		{
			responseAssetSection = cpResponse.getReturn().getOutput().getProduct().getAssets().get(0).getInstances().get(0).getSections().get(0);
			if(responseAssetSection.getUnits().isEmpty() || responseAssetSection.getUnits().get(0).getInstances().isEmpty())
			{
				logger.debug("no UnitInstance or AssetUnit found on response CalculatePremium");
				responseAssetSection = null;
			}
		}
		catch(NullPointerException ex)
		{
			logger.debug("no AssetSections found on response CalculatePremium");
		}
		
		return responseAssetSection;
	}
	


}
