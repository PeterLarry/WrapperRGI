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
import com.mapfre.engines.rating.common.enums.EnumTaxCode;
import com.pass.global.CalculatePremiumResponse;
import com.pass.global.PassProWarrantyUnitShare;
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
	private MapperHashmapUtilitiesToDL mapperHashmapUtilitiesToDL = new MapperHashmapUtilitiesToDL();
	
	/**
	 * Create rating info with  2WCAP and 2WPR mapped
	 * @return IRatingInfo
	 */
	public IRatingInfo getRatingInfo()
	{
		logger.info("into getRatingInfo");
		
		logger.debug("getRatingInfo wsProduct.WsAsset.WsAssetInstance.WsFactor --> proxyQuote.RatingInfo begin");
		
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
						logger.debug("getRatingInfo factor 2WCAP = "+factorAssetInstanceTemp.getValue()+" --> "+
												" assetinstance quote.WorstCap = "+ratingInfoResponse.getWorstCap());
					}
					else if(factorAssetInstanceTemp.getCode().equals("2WPR"))
					{
						String worstProvince = mapperHashmapUtilitiesToDL.getWorstProvince(factorAssetInstanceTemp.getValue());
						ratingInfoResponse.setWorstProvince(worstProvince);
						logger.debug("getRatingInfo factor assetinstance 2WPR = "+factorAssetInstanceTemp.getValue()+" --> "+
											" (decode) --> quote.WorstProvince="+ratingInfoResponse.getWorstProvince());
					}
				}
			}
		}
		
		logger.debug("getRatingInfo wsProduct.WsAsset.WsAssetInstance.WsFactor --> proxyQuote.RatingInfo end");
		
		logger.info("out getRatingInfo with response ratingInfoResponse:"+ratingInfoResponse);
		return ratingInfoResponse;
	}
	
	/**
	 * Init responsePremium for this class
	 * If null, exception occurred in other methods 
	 * @param responsePremium
	 */
	public MapperResponsePremiumToDL(CalculatePremiumResponse responsePremiumRequest)
	{
		this.responsePremium = responsePremiumRequest;
	}

	/**
	 * Create a list of figures with high risk driver, from PASS response
	 * 
	 * @param this.responsePremium
	 * @return List\<IFigure\>
	 */
	public List<IFigure> getFiguresMapped()
	{
		logger.info("getFiguresMapped for request : "+this.responsePremium);
		
		logger.debug("getFiguresMapped wsProduct --> proxyQuote.figures begin");
		
		List<IFigure> figuresListResponse = new ArrayList<>();
		Map<EnumRole, IFigure> mapFigures = createMapFiguresPASS();
		
		figuresListResponse.addAll(mapFigures.values());
		logger.debug("figuresListResponse Found "+figuresListResponse.size()+" figures");
		
		for (IFigure figureTemp : figuresListResponse)
		{
			logger.debug("getFiguresMapped for role:"+figureTemp.getRole());
			
			Boolean higRiskDriver =  getHighRiskDriver(figureTemp.getRole());
			figureTemp.setHighRiskDriver(higRiskDriver);
			logger.debug("getFiguresMapped set HighRiskDriver="+higRiskDriver+" --> "+figureTemp.getRole());
		}
		
		logger.debug("getFiguresMapped wsProduct --> proxyQuote.figures end");
		
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
		
		logger.debug("getInitQuoteResponse wsProduct --> proxyQuote begin");
		
		Quote responseQuote = new Quote();
		IPremium premiumObjResponse = new Premium();
		
		premiumObjResponse.setNet(this.responsePremium.getReturn().getOutput().getProduct().getPremium().getAnnual().getNet());
		premiumObjResponse.setGross(this.responsePremium.getReturn().getOutput().getProduct().getPremium().getAnnual().getGross());
		premiumObjResponse.setTax(this.responsePremium.getReturn().getOutput().getProduct().getPremium().getAnnual().getTaxes());
		premiumObjResponse.setSsn(this.responsePremium.getReturn().getOutput().getProduct().getPremium().getAnnual().getSSN());
//		loggging
		logger.debug("getInitQuoteResponse setted generic NET : "+this.responsePremium.getReturn().getOutput().getProduct().getPremium().getAnnual().getNet());
		logger.debug("getInitQuoteResponse setted generic GROSS : "+this.responsePremium.getReturn().getOutput().getProduct().getPremium().getAnnual().getGross());
		logger.debug("getInitQuoteResponse setted generic TAX : "+this.responsePremium.getReturn().getOutput().getProduct().getPremium().getAnnual().getTaxes());
		logger.debug("getInitQuoteResponse setted generic SSN : "+this.responsePremium.getReturn().getOutput().getProduct().getPremium().getAnnual().getSSN());
		
		responseQuote.setPremium(premiumObjResponse);
		
		String productCode = this.responsePremium.getReturn().getOutput().getProduct().getCode();
		String dateProductOpenDate = this.responsePremium.getReturn().getOutput().getProduct().getOpenDate().getData().toString();
		logger.debug("getInitQuoteResponse for getLogTariffFormulaLog productCode="+productCode+" product.OpenDate="+dateProductOpenDate);
		
		String logTariffFormulaLogFormatted = getLogTariffFormulaLog();
		responseQuote.setDebuggingLog(logTariffFormulaLogFormatted);
		
		logger.debug("getInitQuoteResponse wsProduct --> proxyQuote end");
		
		logger.info("out getInitQuoteResponse with output : "+responseQuote);
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
		
		logger.debug("getCoveragesFromPass wsProduct --> proxyQuote.coverages begin");
		
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
					logger.debug("getCoveragesFromPass found coverageCode : "+coverageCode+" begin");
					
					Double fiddleFactor = getFiddleFactor(assetUnitTemp);
					coverageToAdd.setFiddleFactor(fiddleFactor);
					logger.debug("getCoveragesFromPass Set  FiddleFactor = "+fiddleFactor);
					
					EnumTaxCode taxCode1 = null;
					List<PassProWarrantyUnitShare> listWarrantyUnitShares = assetUnitTemp.getWarrantyUnitShares();
					for (PassProWarrantyUnitShare passProWarrantyUnitShareTemp : listWarrantyUnitShares)
					{
						try
						{
							logger.debug("getCoveragesFromPass for coverageCode:"+coverageCode+" output TaxTypeTariffArticle:"+passProWarrantyUnitShareTemp.getTaxTypeTariffArticle());
							taxCode1 = EnumTaxCode.getEnumFromCode(passProWarrantyUnitShareTemp.getTaxTypeTariffArticle());
						}
						catch(IllegalArgumentException ix)
						{
							logger.error("getCoveragesFromPass error settingup taxCode1 with:"+passProWarrantyUnitShareTemp.getTaxTypeTariffArticle()+" not corresponding");
						}
					}
					logger.debug("getCoveragesFromPass settingup taxCode1 with value :"+taxCode1);
					coverageToAdd.getAmount().setTaxCode1(taxCode1);
					
					Double antiracket = assetUnitTemp.getInstances().get(0).getPremium().getAnnual().getAntiracket();
					coverageToAdd.getAmount().setAntiracket(antiracket);
					logger.debug("getCoveragesFromPass "+coverageCode+" setted antiracket : "+antiracket);
					
					EnumTaxCode taxCode2 = null;
					Double amountSSN = assetUnitTemp.getInstances().get(0).getPremium().getAnnual().getSSN();
					if(antiracket.compareTo(0D) > 0 )
						taxCode2 = EnumTaxCode.ANTI_RACKET_TAX;
					else if(amountSSN > 0)
						taxCode2 = EnumTaxCode.SSN_HEALTH_TAX;
					logger.debug("getCoveragesFromPass for coverageCode = "+coverageCode+" setted taxCode2 = "+taxCode2+
									" (Annual.Antiracket="+antiracket+" annual.SSN="+amountSSN+")");
					coverageToAdd.getAmount().setTaxCode2(taxCode2);
					
					coverageToAdd.getAmount().setNet(assetUnitTemp.getInstances().get(0).getPremium().getAnnual().getNet());
					coverageToAdd.getAmount().setGross(assetUnitTemp.getInstances().get(0).getPremium().getAnnual().getGross());
					coverageToAdd.getAmount().setSsn(amountSSN);
					coverageToAdd.getAmount().setTax(assetUnitTemp.getInstances().get(0).getPremium().getAnnual().getTaxes());
					
					logger.debug("getCoveragesFromPass "+coverageCode+" setted NET : "+assetUnitTemp.getInstances().get(0).getPremium().getAnnual().getNet());
					logger.debug("getCoveragesFromPass "+coverageCode+" setted GROSS : "+assetUnitTemp.getInstances().get(0).getPremium().getAnnual().getGross()+" for coverage : "+coverageCode);
					logger.debug("getCoveragesFromPass "+coverageCode+" setted SSN : "+amountSSN+" for coverage : "+coverageCode);
					logger.debug("getCoveragesFromPass "+coverageCode+" setted TAX : "+assetUnitTemp.getInstances().get(0).getPremium().getAnnual().getTaxes()+" for coverage : "+coverageCode);
					
					responseListCoverages.add(coverageToAdd);
					
					logger.debug("getCoveragesFromPass added coverageCode : "+coverageCode+" end");
				}
			}
		}
		
		logger.debug("getCoveragesFromPass wsProduct --> proxyQuote.coverages end");
		
		logger.info("out getCoveragesFromPass with output responseListCoverages:"+responseListCoverages);
		return responseListCoverages;
	}
	
	/**
	 * Return the right fiddleFactor from unitinstance , factor : 3FIDRC 
	 * @param assetUnitTemp
	 * @return 3FIDRC -> fiddleFactor, null if not found
	 */
	private Double getFiddleFactor(WsAssetUnit assetUnitTemp)
	{
		Double fiddleFactorResponse = null;
		for (WsUnitInstance unitInstanceTemp : assetUnitTemp.getInstances())
		{
			for (WsFactor factorUnitInstanceTemp : unitInstanceTemp.getFactors())
			{
				if(factorUnitInstanceTemp.getCode().equals(ENUMInternalUnitInstanceFactors.FACTOR_3FIDRC.value()))
				{
					fiddleFactorResponse = new Double(factorUnitInstanceTemp.getValue());
					logger.debug("getFiddleFactor found factor "+factorUnitInstanceTemp.getCode()+" = "+factorUnitInstanceTemp.getValue());
					break;
				}
			}
		}
		return fiddleFactorResponse;
	}

	/**
	 * Return coverageCode from unitInstance list
	 * @param assetUnitTemp
	 * @return EnumCoverageCode
	 */
	private EnumCoverageCode getCoverageCode(WsAssetUnit assetUnitTemp)
	{
		EnumCoverageCode covCodeResponse = null ;
		for (WsUnitInstance unitInstanceTemp : assetUnitTemp.getInstances())
		{
			String nameUnitInstance = unitInstanceTemp.getName();
			try
			{
				if(nameUnitInstance != null)
					covCodeResponse = EnumCoverageCode.getEnumFromCode(unitInstanceTemp.getName());
			}
			catch(IllegalArgumentException ix)
			{
				logger.error("getCoverageCode the code"+unitInstanceTemp.getName()+" its not a CoverageCode, probably input problems (no coverages?)");
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
			logger.debug("getAssetUnitsValorized Found "+responseListAssetSection.size()+" AssetSections");
		}
		catch(NullPointerException ex)
		{
			logger.debug("getAssetUnitsValorized NO AssetSections found in response CalculatePremium : "+cpResponse);
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
		logger.info("into getLogTariffFormulaLog");
		String logTariffFormattedResponse = "";
		EnumCoverageCode covCodeProxy = null;

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
							try
							{
								covCodeProxy = EnumCoverageCode.getEnumFromCode(wsUnitInstanceTemp.getName());
								logger.debug("getLogTariffFormulaLog tarifformulalog for CoverageCode:"+covCodeProxy+
												" , wsUnitInstance.name from Proxy:"+wsUnitInstanceTemp.getName());
								logger.debug("getLogTariffFormulaLog Log for tariffFormulaLog output XML => "+wsUnitInstanceTemp.getTariffFormulaLog());
							}
							catch(IllegalArgumentException ix)
							{ }
						}
					}
				}
			}
		}
		
		logger.info("out getLogTariffFormulaLog");
		return logTariffFormattedResponse;
	}
	

	/**
	 * Check the 3Hd factor from UnitInstance
	 * @param roleSelected
	 * @return higRiskResponse 
	 */
	private boolean getHighRiskDriver(EnumRole roleSelected)
	{
		logger.info("into getHighRiskDriver");
		
		logger.debug("getHighRiskDriver roleSelected="+roleSelected);
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
									if(codeUnitInstance.equals(ENUMInternalUnitInstanceFactors.FACTOR_3HD))
									{
										String value3HD = factorUnitInstanceTemp.getValue();
										logger.debug("getHighRiskDriver check for role"+roleSelected+" factor"+codeUnitInstance+
												" value="+value3HD);
										if(EnumRole.FIRST_YOUNG_DRIVER.equals(roleSelected) && value3HD.equals("4"))
										{
											higRiskResponse = true;
											logger.debug("getHighRiskDriver found for factor"+codeUnitInstance+" --> "+
															" role"+roleSelected+" Setted HighRiskDriver="+higRiskResponse);
											break;
										}
										else if(EnumRole.OWNER.equals(roleSelected) && value3HD.equals("3"))
										{
											higRiskResponse = true;
											logger.debug("getHighRiskDriver found for factor"+codeUnitInstance+" --> "+
													" role"+roleSelected+" Setted HighRiskDriver="+higRiskResponse);
											break;
										}
										else if(EnumRole.POLICY_HOLDER.equals(roleSelected) && value3HD.equals("2"))
										{
											higRiskResponse = true;
											logger.debug("getHighRiskDriver found for factor"+codeUnitInstance+" --> "+
													" role"+roleSelected+" Setted HighRiskDriver="+higRiskResponse);
											break;
										}
										else if(EnumRole.SECOND_YOUNG_DRIVER.equals(roleSelected) && value3HD.equals("5"))
										{
											higRiskResponse = true;
											logger.debug("getHighRiskDriver found for factor"+codeUnitInstance+" --> "+
													" role"+roleSelected+" Setted HighRiskDriver="+higRiskResponse);
											break;
										}
										else if(EnumRole.USUAL_DRIVER.equals(roleSelected) && value3HD.equals("1"))
										{
											higRiskResponse = true;
											logger.debug("getHighRiskDriver found for factor"+codeUnitInstance+" --> "+
													" role"+roleSelected+" Setted HighRiskDriver="+higRiskResponse);
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
		logger.debug("createMapFiguresPASS check the wsFactor.wsFactors");
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
				
				logger.debug("createMapFiguresPASS Add figure : "+figureToAdd.getRole());
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
//						logger.debug("createMapFiguresPASS Figure check for value; CODE="+factorAssetInstanceTemp.getCode()+" value="+factorAssetInstanceTemp.getValue()); 
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
							
							logger.debug("createMapFiguresPASS Add figure : "+figureToAdd.getRole());
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
							
							logger.debug("createMapFiguresPASS Add figure : "+figureToAdd.getRole());
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
							
							logger.debug("createMapFiguresPASS Add figure : "+figureToAdd.getRole());
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
							
							logger.debug("createMapFiguresPASS Add figure : "+figureToAdd.getRole());
							continue;
						}
					}
				}
			}
		}
		
		return mapFiguresResponse;
	}
















}