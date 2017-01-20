package it.cg.main.process.mapping.easyway.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mapfre.engines.rating.business.objects.wrapper.Coverage;
import com.mapfre.engines.rating.business.objects.wrapper.Figure;
import com.mapfre.engines.rating.business.objects.wrapper.Premium;
import com.mapfre.engines.rating.business.objects.wrapper.RatingInfo;
import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.ICoverage;
import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.IFigure;
import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.IPremium;
import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.IRatingInfo;
import com.mapfre.engines.rating.common.enums.EnumCoverageCode;
import com.mapfre.engines.rating.common.enums.EnumRole;
import com.pass.global.CalculatePremiumResponse;
import com.pass.global.WsAsset;
import com.pass.global.WsAssetInstance;
import com.pass.global.WsAssetSection;
import com.pass.global.WsAssetUnit;
import com.pass.global.WsFactor;
import com.pass.global.WsUnitInstance;

import it.cg.main.dto.main.Quote;
import it.cg.main.integration.mapper.enumerations.ENUMInternalAssetInstanceFactors;
import it.cg.main.integration.mapper.enumerations.ENUMInternalUnitInstanceFactors;
import it.cg.main.integration.mapper.enumerations.ENUMInternalWsProductFactors;
import it.cg.main.process.mapping.utilities.MapperHashmapUtilitiesToDL;

public class MapperResponsePremiumToDL
{
	private Logger logger = Logger.getLogger(getClass());
	
	private CalculatePremiumResponse responsePremium;
	
	
	public IRatingInfo getRatingInfo()
	{
		IRatingInfo ratingInfoResponse = new RatingInfo();
		
		for (WsAsset wsAssetTemp : this.responsePremium.getReturn().getOutput().getProduct().getAssets())
		{
			for (WsAssetInstance wsAssetInstanceTemp : wsAssetTemp.getInstances())
			{
				for (WsFactor factorAssetInstanceTemp : wsAssetInstanceTemp.getFactors())
				{
					if(factorAssetInstanceTemp.getCode().equals("2WCAP"))
					{
						ratingInfoResponse.setWorstCap(factorAssetInstanceTemp.getValue());
					}
					else if(factorAssetInstanceTemp.getCode().equals("2WPR"))
					{
						String worstProvince = MapperHashmapUtilitiesToDL.getWorstProvince(factorAssetInstanceTemp.getValue());
						ratingInfoResponse.setWorstProvince(worstProvince);
					}
					
				}
			}
		}
		
		return ratingInfoResponse;
	}
	
	
	
	/**
	 * Init responsePremium for class
	 * @param responsePremium
	 */
	public MapperResponsePremiumToDL(CalculatePremiumResponse responsePremium)
	{
		this.responsePremium = responsePremium;
	}

	/**
	 * Create a list of figures
	 * @param this.responsePremium
	 * @return
	 */
	public List<IFigure> getFiguresMapped()
	{
		logger.info("getFiguresMapped for request : "+this.responsePremium);
		
		List<IFigure> figuresListResponse = new ArrayList<>();
		Map<EnumRole, IFigure> mapFigures = createMapFiguresPASS();
		
		figuresListResponse.addAll(mapFigures.values());
		logger.debug("figuresListResponse Found "+figuresListResponse.size()+" figures");
		for (IFigure figureTemp : figuresListResponse)
		{
			Boolean higRiskDriver =  getHighRiskDriver(figureTemp.getRole());
			figureTemp.setHighRiskDriver(higRiskDriver);
			
		}
		
		logger.info("getFiguresMapped return figures "+figuresListResponse);
		return figuresListResponse;
	}
	
	
	/**
	 * Get principal response from premium pass
	 * @param responsePremium
	 * @return
	 */
	public Quote getInitQuoteResponse()
	{
		logger.info("into getInitQuoteResponse with input : "+this.responsePremium);
		
		Quote responseQuote = new Quote();
		
		IPremium premiumObjResponse = new Premium();
		premiumObjResponse.setGross(this.responsePremium.getReturn().getOutput().getProduct().getPremium().getAnnual().getGross());
		premiumObjResponse.setNet(this.responsePremium.getReturn().getOutput().getProduct().getPremium().getAnnual().getNet());
		premiumObjResponse.setTax(this.responsePremium.getReturn().getOutput().getProduct().getPremium().getAnnual().getTaxes());
		premiumObjResponse.setSsn(this.responsePremium.getReturn().getOutput().getProduct().getPremium().getAnnual().getSSN());
		
		responseQuote.setPremium(premiumObjResponse);
		
//		log messages response
		String logTariffFormulaLogFormatted = getLogTariffFormulaLog();
		responseQuote.setDebuggingLog(logTariffFormulaLogFormatted);
		
		logger.info("into getInitQuoteResponse with output : "+responseQuote);
		return responseQuote;
	}
	
	/**
	 * Return warranties list from response
	 * @param this.responsePremium
	 * @return ICoverage, null if some error
	 */
	public List<ICoverage> getCoveragesFromPass()
	{
		logger.info("into getCoveragesFromPass with input : "+this.responsePremium);
		
		List<ICoverage> responseListCoverages = new ArrayList<>(0);
		List<WsAssetSection> assetSectionList = getAssetUnitsValorized(this.responsePremium);
		
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
			logger.debug("into getAssetUnitsValorized Found "+responseListAssetSection.size()+" AssetSections");
		}
		catch(NullPointerException ex)
		{
			logger.debug("no AssetSections found in response CalculatePremium : "+cpResponse);
		}
		
		return responseListAssetSection;
	}
	
	/**
	 * Log all the tariffFormulaLog, 
	 * @param this.responsePremium
	 * @return logTariffFormattedResponse formatted
	 */
	private String getLogTariffFormulaLog()
	{
		String logTariffFormattedResponse = "";
		
		for (WsAsset wsAssetTemp : this.responsePremium.getReturn().getOutput().getProduct().getAssets())
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
	

	/**
	 * Check the 3YD factor from UnitInstance
	 * @param roleSelected
	 * @return higRiskResponse 
	 */
	private boolean getHighRiskDriver(EnumRole roleSelected)
	{
		boolean higRiskResponse = false;
		
		for (WsAsset wsAssetTemp : this.responsePremium.getReturn().getOutput().getProduct().getAssets())
		{
			for (WsAssetInstance wsAssetInstanceTemp : wsAssetTemp.getInstances())
			{
				for (WsAssetSection wsAssetSectionTemp : wsAssetInstanceTemp.getSections())
				{
					for (WsAssetUnit wsAssetUnitTemp : wsAssetSectionTemp.getUnits())
					{
						for (WsUnitInstance wsUnitInstanceTemp : wsAssetUnitTemp.getInstances())
						{
							for (WsFactor factorUnitInstanceTemp : wsUnitInstanceTemp.getFactors())
							{
								try
								{
									ENUMInternalUnitInstanceFactors codeUnitInstance = 
											ENUMInternalUnitInstanceFactors.getEnumFromCode(factorUnitInstanceTemp.getCode());
									if(codeUnitInstance.equals(ENUMInternalUnitInstanceFactors.FACTOR_3YD))
									{
										String value3YD = factorUnitInstanceTemp.getValue();
										if(EnumRole.FIRST_YOUNG_DRIVER.equals(roleSelected) && value3YD.equals("4RD1"))
										{
											higRiskResponse = true;
											break;
										}
										else if(EnumRole.OWNER.equals(roleSelected) && value3YD.equals("3OW"))
										{
											higRiskResponse = true;
											break;
										}
										else if(EnumRole.POLICY_HOLDER.equals(roleSelected) && value3YD.equals("2PH"))
										{
											higRiskResponse = true;
											break;
										}
										else if(EnumRole.SECOND_YOUNG_DRIVER.equals(roleSelected) && value3YD.equals("5RD2"))
										{
											higRiskResponse = true;
											break;
										}
										else if(EnumRole.USUAL_DRIVER.equals(roleSelected) && value3YD.equals("1MD"))
										{
											higRiskResponse = true;
											break;
										}
									}
								}
								catch(IllegalArgumentException ix)
								{ }
								
							}
						}
					}
				}
			}
		}
		
		
		
		return higRiskResponse;
	}

	/**
	 * Create a map with Enum for key ,and a figure with all parameter
	 * @param this.responsePremium
	 * @return 
	 */
	private Map<EnumRole, IFigure> createMapFiguresPASS()
	{
		Map<EnumRole, IFigure> mapFiguresResponse = new HashMap<>(0);
		
//		EnumRole.POLICY_HOLDER
		List<WsFactor> factorsProduct = this.responsePremium.getReturn().getOutput().getProduct().getFactors();
		for (WsFactor factorProductTemp : factorsProduct)
		{
			IFigure figureToAdd = new Figure();
			ENUMInternalWsProductFactors codeFactorEnumPASSTemp = null ;
			try
			{
				codeFactorEnumPASSTemp = ENUMInternalWsProductFactors.getEnumFromCode(factorProductTemp.getCode());
			}
			catch(IllegalArgumentException ix)
			{ }
			
			if( codeFactorEnumPASSTemp != null && 
					(codeFactorEnumPASSTemp.equals(ENUMInternalWsProductFactors.FACTOR__1CETA) || 
							codeFactorEnumPASSTemp.equals(ENUMInternalWsProductFactors.FACTOR__1CNCA) || 
							codeFactorEnumPASSTemp.equals(ENUMInternalWsProductFactors.FACTOR_1PHAP) ||
							codeFactorEnumPASSTemp.equals(ENUMInternalWsProductFactors.FACTOR_1PHSC)) )
			{
				figureToAdd.setRole(EnumRole.POLICY_HOLDER);
				mapFiguresResponse.put(EnumRole.POLICY_HOLDER, figureToAdd);
				logger.debug("Add figure : "+EnumRole.POLICY_HOLDER+" ");
				break;
			}
		}
		
//		other EnumRole
		for (WsAsset wsAssetTemp : this.responsePremium.getReturn().getOutput().getProduct().getAssets())
		{
			for (WsAssetInstance wsAssetInstanceTemp : wsAssetTemp.getInstances())
			{
				for (WsFactor factorAssetInstanceTemp : wsAssetInstanceTemp.getFactors())
				{
					IFigure figureToAdd = new Figure();
					ENUMInternalAssetInstanceFactors codeFactorEnumPASSTemp = null ;
					try
					{
						codeFactorEnumPASSTemp = ENUMInternalAssetInstanceFactors.getEnumFromCode(factorAssetInstanceTemp.getCode());
					}
					catch(IllegalArgumentException ix)
					{ }
					
//					EnumRole.USUAL_DRIVER
					if( codeFactorEnumPASSTemp != null && 
							(codeFactorEnumPASSTemp.equals(ENUMInternalAssetInstanceFactors.FACTOR__2CETA) || 
									codeFactorEnumPASSTemp.equals(ENUMInternalAssetInstanceFactors.FACTOR__2CRCA) || 
									codeFactorEnumPASSTemp.equals(ENUMInternalAssetInstanceFactors.FACTOR_2MDAP) ||
									codeFactorEnumPASSTemp.equals(ENUMInternalAssetInstanceFactors.FACTOR_2MDPR) ||
									codeFactorEnumPASSTemp.equals(ENUMInternalAssetInstanceFactors.FACTOR_2MDSC)) )
					{
						figureToAdd = mapFiguresResponse.get(EnumRole.USUAL_DRIVER);
						if(figureToAdd == null)
						{
							figureToAdd = new Figure();
							figureToAdd.setRole(EnumRole.USUAL_DRIVER);
							mapFiguresResponse.put(EnumRole.USUAL_DRIVER, figureToAdd);
							logger.debug("Add figure : "+EnumRole.USUAL_DRIVER+" ");
							
							continue;
						}
						
					}
//					EnumRole.OWNER
					if( codeFactorEnumPASSTemp != null && 
							(codeFactorEnumPASSTemp.equals(ENUMInternalAssetInstanceFactors.FACTOR__2PETA) || 
									codeFactorEnumPASSTemp.equals(ENUMInternalAssetInstanceFactors.FACTOR__2PRCA) || 
									codeFactorEnumPASSTemp.equals(ENUMInternalAssetInstanceFactors.FACTOR_2OWAP) ||
									codeFactorEnumPASSTemp.equals(ENUMInternalAssetInstanceFactors.FACTOR_2OWSC))  )
					{
						figureToAdd = mapFiguresResponse.get(EnumRole.OWNER);
						if(figureToAdd == null)
						{
							figureToAdd = new Figure();
							figureToAdd.setRole(EnumRole.OWNER);
							mapFiguresResponse.put(EnumRole.OWNER, figureToAdd);
							logger.debug("Add figure : "+EnumRole.OWNER+" ");
							continue;
						}
						
					}
//					EnumRole.FIRST_YOUNG_DRIVER
					if( codeFactorEnumPASSTemp != null && 
							(codeFactorEnumPASSTemp.equals(ENUMInternalAssetInstanceFactors.FACTOR_2RD1AP) || 
									codeFactorEnumPASSTemp.equals(ENUMInternalAssetInstanceFactors.FACTOR_2RD1CA) || 
									codeFactorEnumPASSTemp.equals(ENUMInternalAssetInstanceFactors.FACTOR_2RD1ET) ||
									codeFactorEnumPASSTemp.equals(ENUMInternalAssetInstanceFactors.FACTOR_2RD1SC)) )
					{
						figureToAdd = mapFiguresResponse.get(EnumRole.FIRST_YOUNG_DRIVER);
						if(figureToAdd == null)
						{
							figureToAdd = new Figure();
							figureToAdd.setRole(EnumRole.FIRST_YOUNG_DRIVER);
							mapFiguresResponse.put(EnumRole.FIRST_YOUNG_DRIVER, figureToAdd);
							logger.debug("Add figure : "+EnumRole.FIRST_YOUNG_DRIVER+" ");
							continue;
						}
						
					}
//					EnumRole.SECOND_YOUNG_DRIVER
					if( codeFactorEnumPASSTemp != null && 
							(codeFactorEnumPASSTemp.equals(ENUMInternalAssetInstanceFactors.FACTOR_2RD2AP) || 
									codeFactorEnumPASSTemp.equals(ENUMInternalAssetInstanceFactors.FACTOR_2RD2CA) || 
									codeFactorEnumPASSTemp.equals(ENUMInternalAssetInstanceFactors.FACTOR_2RD2ET) ||
									codeFactorEnumPASSTemp.equals(ENUMInternalAssetInstanceFactors.FACTOR_2RD2SC)) )
					{
						figureToAdd = mapFiguresResponse.get(EnumRole.SECOND_YOUNG_DRIVER);
						if(figureToAdd == null)
						{
							figureToAdd = new Figure();
							figureToAdd.setRole(EnumRole.SECOND_YOUNG_DRIVER);
							mapFiguresResponse.put(EnumRole.SECOND_YOUNG_DRIVER, figureToAdd);
							logger.debug("Add figure : "+EnumRole.SECOND_YOUNG_DRIVER+" ");
							continue;
						}
						
					}
				}
			}
		}
		
		return mapFiguresResponse;
	}
















}