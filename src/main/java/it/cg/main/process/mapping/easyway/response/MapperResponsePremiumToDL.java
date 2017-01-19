package it.cg.main.process.mapping.easyway.response;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mapfre.engines.rating.business.objects.wrapper.Coverage;
import com.mapfre.engines.rating.business.objects.wrapper.Premium;
import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.ICoverage;
import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.IPremium;
import com.mapfre.engines.rating.common.enums.EnumCoverageCode;
import com.pass.global.CalculatePremiumResponse;
import com.pass.global.WsAsset;
import com.pass.global.WsAssetInstance;
import com.pass.global.WsAssetSection;
import com.pass.global.WsAssetUnit;
import com.pass.global.WsUnitInstance;

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
		
		responseQuote.setPremium(premiumObjResponse);
		
//		log messages response
		String logTariffFormulaLogFormatted = getLogTariffFormulaLog(responsePremium);
		responseQuote.setDebuggingLog(logTariffFormulaLogFormatted);
		
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
		List<WsAssetSection> assetSectionList = getAssetUnitsValorized(responsePremium);
		if(assetSectionList == null)
		{
			responseListCoverages = null ;
		}
		else
		{
			for (WsAssetSection wsAssetSectionTemp : assetSectionList)
			{
				for (WsAssetUnit assetUnitTemp : wsAssetSectionTemp.getUnits())
				{
					ICoverage coverageToAdd = new Coverage();
					
					EnumCoverageCode coverageCode = getCoverageCode(assetUnitTemp);
					coverageToAdd.setCode(coverageCode);
					
					coverageToAdd.getAmount().setNet(assetUnitTemp.getInstances().get(0).getPremium().getAnnual().getNet());
					coverageToAdd.getAmount().setGross(assetUnitTemp.getInstances().get(0).getPremium().getAnnual().getGross());
					coverageToAdd.getAmount().setSsn(assetUnitTemp.getInstances().get(0).getPremium().getAnnual().getSSN());
					coverageToAdd.getAmount().setTax(assetUnitTemp.getInstances().get(0).getPremium().getAnnual().getTaxes());
					
					responseListCoverages.add(coverageToAdd);
				}
			}
		}
		
		logger.info("into getCoveragesFromPass with input : "+responseListCoverages);
		return responseListCoverages;
	}
	
	/**
	 * Return coverageCode
	 * @param assetUnitTemp
	 * @return EnumCoverageCode
	 */
	private EnumCoverageCode getCoverageCode(WsAssetUnit assetUnitTemp)
	{
		EnumCoverageCode covCodeResponse = null ;
		for (WsUnitInstance unitInstanceTemp : assetUnitTemp.getInstances())
		{
			String nameUnitInstance = unitInstanceTemp.getName();
			if(nameUnitInstance != null)
			{
				covCodeResponse = EnumCoverageCode.getEnumFromCode(unitInstanceTemp.getName());
				logger.debug("Coverage code output found : "+covCodeResponse);
			}
		}
		
		return covCodeResponse;
	}

	/**
	 * If The assetsection or the unitinstaces or the assetunit are empty, the return is null
	 * @param cpResponse
	 * @return AssetSection with unitinstances
	 */
	private List<WsAssetSection> getAssetUnitsValorized(CalculatePremiumResponse cpResponse)
	{
		List<WsAssetSection> responseListAssetSection = null; 
		try
		{
			responseListAssetSection = cpResponse.getReturn().getOutput().getProduct().getAssets().get(0).getInstances().get(0).getSections();
			logger.debug("Found "+responseListAssetSection.size()+" AssetSections");
		}
		catch(NullPointerException ex)
		{
			logger.debug("no AssetSections found in response CalculatePremium : "+cpResponse);
		}
		
		responseListAssetSection = new ArrayList<WsAssetSection>();
		for (WsAssetSection assetSectionTemp : responseListAssetSection)
		{
			if(assetSectionTemp.getUnits().isEmpty() || assetSectionTemp.getUnits().get(0).getInstances().isEmpty())
			{
				responseListAssetSection = null;
				logger.debug("no UnitInstance or AssetUnit found on response CalculatePremium");
			}
			else
			{
				responseListAssetSection.add(assetSectionTemp);
				logger.debug("add assetSectionTemp to response : "+assetSectionTemp);
			}
			
		}
		
		return responseListAssetSection;
	}
	
	/**
	 * Log all the tariffFormulaLog, 
	 * @param responsePremium
	 * @return logTariffFormattedResponse formatted
	 */
	private String getLogTariffFormulaLog(CalculatePremiumResponse responsePremium)
	{
		String logTariffFormattedResponse = "";
		
		for (WsAsset wsAssetTemp : responsePremium.getReturn().getOutput().getProduct().getAssets())
		{
			for (WsAssetInstance wsAssetInstanceTemp : wsAssetTemp.getInstances())
			{
				for (WsAssetSection wsAssetSectionTemp : wsAssetInstanceTemp.getSections())
				{
					for (WsAssetUnit wsAssetUnitTemp : wsAssetSectionTemp.getUnits())
					{
						for (WsUnitInstance wsUnitInstanceTemp : wsAssetUnitTemp.getInstances())
						{
							logTariffFormattedResponse += wsUnitInstanceTemp.getTariffFormulaLog();
							logger.debug("Log for tariffFormulaLog output => "+wsUnitInstanceTemp.getTariffFormulaLog());
						}
					}
				}
			}
		}
		return logTariffFormattedResponse;
	}
	


}
