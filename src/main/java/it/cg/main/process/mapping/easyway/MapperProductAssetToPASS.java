package it.cg.main.process.mapping.easyway;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;
//import javax.inject.Qualifier;
import org.springframework.stereotype.Service;

import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.IFigure;
import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.IOtherVehicle;
import com.mapfre.engines.rating.common.enums.EnumFlowType;
import com.mapfre.engines.rating.common.enums.EnumProductType;
import com.mapfre.engines.rating.common.enums.EnumPublicRegisterUse;
import com.mapfre.engines.rating.common.enums.EnumRiskType;
import com.mapfre.engines.rating.common.enums.EnumRole;
import com.pass.global.TypeBooleano;
import com.pass.global.TypeData;
import com.pass.global.WsAssetInstance;
import com.pass.global.WsFactor;
import com.pass.global.WsProduct;
import com.pass.global.WsVehicle;

import it.cg.main.dto.InboundRequestHttpJSON;
import it.cg.main.dto.main.Quote;
import it.cg.main.integration.mapper.enumerations.ENUMInternalAssetInstanceFactors;
import it.cg.main.integration.mapper.enumerations.ENUMInternalCodeProduct;
import it.cg.main.integration.mapper.enumerations.ENUMInternalWsProductFactors;
import it.cg.main.process.mapping.utilities.MapperHashmapUtilitiesToPASS;

@Service
public class MapperProductAssetToPASS
{
	private Logger logger = Logger.getLogger(getClass());
	
	private TypeBooleano typeB =  new TypeBooleano();
//	Pattern aggiornato to PASS
	private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	private String sectorCode = "";
	
	/**
	 * Get factors for WsProduct from FIGURES
	 * EnumRole.POLICY_HOLDER
	 * @param role
	 * @param listFigure
	 * @return  List<WsFactor> never null
	 */
	private List<WsFactor> getFactorForFigureFromRoleWSProduct(List<IFigure> listFigure)
	{
		List<WsFactor> listFactor = new ArrayList<WsFactor>();
		WsFactor wsFactor = new WsFactor();
		
		for (IFigure figureTemp : listFigure)
		{
//			POLICY_HOLDER
//			Il wsproduct prevede che i dati vengano presi dal PH per i factors 
			if(figureTemp.getRole().equals(EnumRole.POLICY_HOLDER))
			{
				if(figureTemp.getAge() != null)
				{
					wsFactor = new WsFactor();
					wsFactor.setCode(ENUMInternalWsProductFactors.FACTOR__1CETA.value());
					wsFactor.setValue(figureTemp.getAge().toString());
					logger.debug("getFactorForFigureFromRoleWSProduct add to wsproduct factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
					listFactor.add(wsFactor);
				}
				if(figureTemp.getResidenceAddress() != null)
				{
					if(figureTemp.getResidenceAddress().getCap() != null)
					{
						wsFactor = new WsFactor();
						wsFactor.setCode(ENUMInternalWsProductFactors.FACTOR__1CNCA.value());
						wsFactor.setValue(figureTemp.getResidenceAddress().getCap());
						logger.debug("getFactorForFigureFromRoleWSProduct add to wsproduct factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
						listFactor.add(wsFactor);
					}
				}
				if(figureTemp.getYearsWithLicense() != null)
				{
					wsFactor = new WsFactor();
					wsFactor.setCode(ENUMInternalWsProductFactors.FACTOR_1PHAP.value());
					if(figureTemp.getYearsWithLicense() == 0)
					{
						if(figureTemp.getLicensed() != null && figureTemp.getLicensed())
							wsFactor.setValue(figureTemp.getYearsWithLicense().toString());
						else
							wsFactor.setValue("-1");
					}
					else
					{
						wsFactor.setValue(figureTemp.getYearsWithLicense().toString());
					}
					logger.debug("getFactorForFigureFromRoleWSProduct add to wsproduct factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
					listFactor.add(wsFactor);
				}
				
				if(figureTemp.getMaritalStatus() != null)
				{
					wsFactor = new WsFactor();
					wsFactor.setCode(ENUMInternalWsProductFactors.FACTOR_1PHSC.value());
					wsFactor.setValue(figureTemp.getMaritalStatus().getWrapperCode().toString());
					logger.debug("getFactorForFigureFromRoleWSProduct add to wsproduct factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
					listFactor.add(wsFactor);
				}
				
				break;
			}
		}
		
		return listFactor;
	}
	
	
	
	/**
	 * Metodo custom di quoteDtoToProduct
	 * @param quoteRequest
	 * @return WsProduct init
	 */
	public WsProduct quoteToListWsProduct(Quote quoteRequest)
	{
		WsProduct wsProductResponse = new WsProduct();
		
//		Fields
		wsProductResponse.setCurrencyCode("000001");
		logger.debug("quoteToListWsProduct setted wsproduct.CurrencyCode : "+wsProductResponse.getCurrencyCode());
		wsProductResponse.setOpenDate(dataToTypeData(quoteRequest.getRateFromDate()));
		logger.debug("quoteToListWsProduct setted wsproduct.OpenDate : "+wsProductResponse.getOpenDate());

		if(quoteRequest.getInstallments()==1)
		{
			wsProductResponse.setPaymentFrequencyCode("000001");
		}
		else if(quoteRequest.getInstallments()==2)
		{
			wsProductResponse.setPaymentFrequencyCode("000002");
		}
		logger.debug("quoteToListWsProduct setted wsproduct.PaymentFrequencyCode : "+wsProductResponse.getPaymentFrequencyCode());

//				Operation code
		String opCode = null;
		if(quoteRequest.getContext() != null && quoteRequest.getContext().getFlowType() != null)
		{
			if(quoteRequest.getContext().getFlowType().equals(EnumFlowType.REVIEW_GENERATION))
			{
				opCode="000074";
			}
			else if(quoteRequest.getContext().getFlowType().equals(EnumFlowType.RENQUOTE_GENERATION))
			{
				opCode="000075";
			}
		}
		logger.debug("quoteToListWsProduct set wsproduct.OperationCode : "+opCode);
		wsProductResponse.setOperationCode(opCode);
		
		
//		code product
		if(quoteRequest.getContext().getRiskType() != null && quoteRequest.getContext().getProductType() != null && 
				quoteRequest.getContext().getDirectlineSelfService() != null)
		{
			if(quoteRequest.getContext().getRiskType().equals(EnumRiskType.CAR) &&
					quoteRequest.getContext().getProductType().equals(EnumProductType.DLI)&&
					quoteRequest.getContext().getDirectlineSelfService()==false)
			{
				wsProductResponse.setCode(ENUMInternalCodeProduct.CODE_AUTODLI.value());
			}
			else if(  quoteRequest.getContext().getProductType().equals(EnumProductType.DLI) &&
					  	( quoteRequest.getContext().getRiskType().equals(EnumRiskType.MOTORBIKE) ||
					  		quoteRequest.getContext().getRiskType().equals(EnumRiskType.MOPED) ) &&
					  		quoteRequest.getContext().getDirectlineSelfService()==false)
			{
				wsProductResponse.setCode(ENUMInternalCodeProduct.CODE_MOTOCICLODLI.value());
			}
			else if(quoteRequest.getContext().getRiskType().equals(EnumRiskType.CAR) &&
						quoteRequest.getContext().getProductType().equals(EnumProductType.DLI)&&
						quoteRequest.getContext().getDirectlineSelfService()==true)
			{
				wsProductResponse.setCode(ENUMInternalCodeProduct.CODE_AUTODLSS.value());
			}
			else if ( (quoteRequest.getContext().getProductType().equals(EnumProductType.ADI))&&
					   		quoteRequest.getContext().getRiskType().equals(EnumRiskType.CAR) &&
					   		quoteRequest.getContext().getDirectlineSelfService()==false)
			{
				wsProductResponse.setCode(ENUMInternalCodeProduct.CODE_AUTOADI.value());
			}
			else if ( ( !quoteRequest.getContext().getRiskType().equals(EnumRiskType.CAR) &&
							  !quoteRequest.getContext().getRiskType().equals(EnumRiskType.MOTORBIKE) &&
							  !quoteRequest.getContext().getRiskType().equals(EnumRiskType.MOPED) ) &&
							    quoteRequest.getContext().getProductType().equals(EnumProductType.DLI) &&
							    quoteRequest.getContext().getDirectlineSelfService()==false)
			{
				wsProductResponse.setCode(ENUMInternalCodeProduct.CODE_ALTRIVEICOLIDLI.value());
			}
			logger.debug("quoteToListWsProduct setted wsproduct.code : "+wsProductResponse.getCode());
		}
//		Factors
		logger.debug("quoteToListWsProduct begin factor wsproduct");
		ArrayList<WsFactor> factProp = new ArrayList<WsFactor>();
		WsFactor wsFactor = new WsFactor();
		if(quoteRequest.getAffinity() != null)
		{
			wsFactor.setCode(ENUMInternalWsProductFactors.FACTOR_1AFF.value());
			wsFactor.setValue(quoteRequest.getAffinity());
			logger.debug("quoteToListWsProduct add to wsproduct factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
			factProp.add(wsFactor);
		}
		if(quoteRequest.getRenewalYears() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalWsProductFactors.FACTOR_1ANRIN.value());
			wsFactor.setValue(quoteRequest.getRenewalYears().toString());
			logger.debug("quoteToListWsProduct add to wsproduct factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
			factProp.add(wsFactor);
		}
		if(quoteRequest.getPreviousRenewalYears() != null )
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalWsProductFactors.FACTOR_1FRRIN.value());
			wsFactor.setValue(quoteRequest.getPreviousRenewalYears().toString());
			logger.debug("quoteToListWsProduct add to wsproduct factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
			factProp.add(wsFactor);
		}
		if(quoteRequest.getCampaign() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalWsProductFactors.FACTOR_1CSC.value());
			wsFactor.setValue(quoteRequest.getCampaign().getWrapperCode().toString());
			logger.debug("quoteToListWsProduct add to wsproduct factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
			factProp.add(wsFactor);
		}
		
		logger.debug("quoteToListWsProduct add "+factProp.size()+" factors to product");
		wsProductResponse.getFactors().addAll(factProp);
		List<WsFactor> listFactorsFigures = getFactorForFigureFromRoleWSProduct(quoteRequest.getFigures());
		logger.debug("quoteToListWsProduct add "+listFactorsFigures.size()+" factors to product");
		wsProductResponse.getFactors().addAll(listFactorsFigures);
		
		return wsProductResponse;
	}
	
	
	/**
	 * Factors of figures for ASSET INSTANCE
	 * EnumRole.OWNER
	 * EnumRole.USUAL_DRIVER
	 * EnumRole.FIRST_YOUNG_DRIVER
	 * EnumRole.SECOND_YOUNG_DRIVER
	 * @param listFigure
	 * @return
	 */
	private List<WsFactor> getFactorForFigureFromRoleAssetInstance(List<IFigure> listFigure)
	{
		logger.info("into getFactorForFigureFromRoleAssetInstance with request : "+listFigure);
		
		List<WsFactor> listFactor = new ArrayList<WsFactor>();
		WsFactor wsFactor = new WsFactor();
		
		logger.debug("getFactorForFigureFromRoleAssetInstance found "+listFigure.size()+" figures input");
		for (IFigure figureTemp : listFigure)
		{
//			years with licensed calculated
			String valForYearsWithLicensedFactor = "";
			if(figureTemp.getYearsWithLicense() != null && figureTemp.getYearsWithLicense() == 0)
			{
				if(figureTemp.getLicensed() != null && figureTemp.getLicensed())
					valForYearsWithLicensedFactor = figureTemp.getYearsWithLicense().toString();
				else
					valForYearsWithLicensedFactor = "-1";
			}
			else
			{
				valForYearsWithLicensedFactor = figureTemp.getYearsWithLicense() == null ? 
													"" : figureTemp.getYearsWithLicense().toString() ;
			}
			logger.debug("getFactorForFigureFromRoleAssetInstance set YearsWithLicense for all figure->factors : "+valForYearsWithLicensedFactor);
			
			logger.debug("getFactorForFigureFromRoleAssetInstance factors for "+figureTemp.getRole()+" ROLE (quote.figure value)");
//			USUAL_DRIVER 
			if(figureTemp.getRole().equals(EnumRole.USUAL_DRIVER))
			{
				if(figureTemp.getAge() != null)
				{
					wsFactor = new WsFactor();
					wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR__2CETA.value());
					wsFactor.setValue(figureTemp.getAge().toString());
					logger.debug("getFactorForFigureFromRoleAssetInstance add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
					listFactor.add(wsFactor);
				}
				if(figureTemp.getResidenceAddress() != null)
				{
					if(figureTemp.getResidenceAddress().getCap() != null)
					{
						wsFactor = new WsFactor();
						wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR__2CRCA.value());
						wsFactor.setValue(figureTemp.getResidenceAddress().getCap());
						logger.debug("getFactorForFigureFromRoleAssetInstance add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
						listFactor.add(wsFactor);
					}
				}
				if(figureTemp.getYearsWithLicense() != null)
				{
					wsFactor = new WsFactor();
					wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2MDAP.value());
					wsFactor.setValue(valForYearsWithLicensedFactor);
					logger.debug("getFactorForFigureFromRoleAssetInstance add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
					listFactor.add(wsFactor);
				}
				
				if(figureTemp.getOccupation() != null)
				{
					wsFactor = new WsFactor();
					wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2MDPR.value());
					wsFactor.setValue(figureTemp.getOccupation().getWrapperCode().toString());
					logger.debug("getFactorForFigureFromRoleAssetInstance add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
					listFactor.add(wsFactor);
				}
				
				if(figureTemp.getMaritalStatus() != null)
				{
					wsFactor = new WsFactor();
					wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2MDSC.value());
					wsFactor.setValue(figureTemp.getMaritalStatus().getWrapperCode().toString());
					logger.debug("getFactorForFigureFromRoleAssetInstance add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
					listFactor.add(wsFactor);
				}
			}
//			OWNER
			if(figureTemp.getRole().equals(EnumRole.OWNER))
			{
				if(figureTemp.getAge() != null)
				{
					wsFactor = new WsFactor();
					wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR__2PETA.value());
					wsFactor.setValue(figureTemp.getAge().toString());
					logger.debug("getFactorForFigureFromRoleAssetInstance add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
					listFactor.add(wsFactor);
				}
				if(figureTemp.getResidenceAddress() != null)
				{
					if(figureTemp.getResidenceAddress().getCap() != null)
					{
						wsFactor = new WsFactor();
						wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR__2PRCA.value());
						wsFactor.setValue(figureTemp.getResidenceAddress().getCap());
						logger.debug("getFactorForFigureFromRoleAssetInstance add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
						listFactor.add(wsFactor);
					}
				}
				if(figureTemp.getYearsWithLicense() != null)
				{
					wsFactor = new WsFactor();
					wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2OWAP.value());
					wsFactor.setValue(valForYearsWithLicensedFactor);
					logger.debug("getFactorForFigureFromRoleAssetInstance add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
					listFactor.add(wsFactor);
				}
				
				if(figureTemp.getMaritalStatus() != null)
				{
					wsFactor = new WsFactor();
					wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2OWSC.value());
					wsFactor.setValue(figureTemp.getMaritalStatus().getWrapperCode().toString());
					logger.debug("getFactorForFigureFromRoleAssetInstance add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
					listFactor.add(wsFactor);
				}
			}
//			FIRST YOUNG DRIVER
			if(figureTemp.getRole().equals(EnumRole.FIRST_YOUNG_DRIVER))
			{
				if(figureTemp.getYearsWithLicense() != null)
				{
					wsFactor = new WsFactor();
					wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2RD1AP.value());
					wsFactor.setValue(valForYearsWithLicensedFactor);
					logger.debug("getFactorForFigureFromRoleAssetInstance add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
					listFactor.add(wsFactor);
				}
				if(figureTemp.getResidenceAddress() != null)
				{
					if(figureTemp.getResidenceAddress().getCap() != null)
					{
						wsFactor = new WsFactor();
						wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2RD1CA.value());
						wsFactor.setValue(figureTemp.getResidenceAddress().getCap());
						logger.debug("getFactorForFigureFromRoleAssetInstance add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
						listFactor.add(wsFactor);
					}
				}
				if(figureTemp.getAge() != null)
				{
					wsFactor = new WsFactor();
					wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2RD1ET.value());
					wsFactor.setValue(figureTemp.getAge().toString());
					logger.debug("getFactorForFigureFromRoleAssetInstance add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
					listFactor.add(wsFactor);
				}
				
				if(figureTemp.getMaritalStatus() != null)
				{
					wsFactor = new WsFactor();
					wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2RD1SC.value());
					wsFactor.setValue(figureTemp.getMaritalStatus().getWrapperCode().toString());
					logger.debug("getFactorForFigureFromRoleAssetInstance add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
					listFactor.add(wsFactor);
				}
			}
//			SECOND_YOUNG_DRIVER
			if(figureTemp.getRole().equals(EnumRole.SECOND_YOUNG_DRIVER))
			{
				if(figureTemp.getYearsWithLicense() != null)
				{
					wsFactor = new WsFactor();
					wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2RD2AP.value());
					wsFactor.setValue(valForYearsWithLicensedFactor);
					logger.debug("getFactorForFigureFromRoleAssetInstance add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
					listFactor.add(wsFactor);
				}
				if(figureTemp.getResidenceAddress() != null)
				{
					if(figureTemp.getResidenceAddress().getCap() != null)
					{
						wsFactor = new WsFactor();
						wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2RD2CA.value());
						wsFactor.setValue(figureTemp.getResidenceAddress().getCap());
						logger.debug("getFactorForFigureFromRoleAssetInstance add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
						listFactor.add(wsFactor);
					}
				}
				if(figureTemp.getAge() != null)
				{
					wsFactor = new WsFactor();
					wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2RD2ET.value());
					wsFactor.setValue(figureTemp.getAge().toString());
					logger.debug("getFactorForFigureFromRoleAssetInstance add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
					listFactor.add(wsFactor);
				}
				
				if(figureTemp.getMaritalStatus() != null)
				{
					wsFactor = new WsFactor();
					wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2RD2SC.value());
					wsFactor.setValue(figureTemp.getMaritalStatus().getWrapperCode().toString());
					logger.debug("getFactorForFigureFromRoleAssetInstance add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
					listFactor.add(wsFactor);
				}
			}
		}
		
		return listFactor;
	}
	
	/**
	 * Metodo custom di quoteDtoToAsset
	 * @param quote
	 * @return
	 */
	public List<WsAssetInstance> quoteToWsAsset(Quote quote)
	{
		List<WsAssetInstance> listAssetInstResponse = new ArrayList<WsAssetInstance>();
		WsAssetInstance assetInstanceResponse = new WsAssetInstance();
		
//		factor 2Loyal and lastYears 3 and 6
		MapperHashmapUtilitiesToPASS mapUty = new MapperHashmapUtilitiesToPASS();

		String loyalCode = mapUty.get2Loyal(quote.getRatingInfo().getCompanyChangesDetails());
		WsFactor factorToAdd2Loyal = new WsFactor();
		factorToAdd2Loyal.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2LOYAL.value());
		factorToAdd2Loyal.setValue(loyalCode);
		assetInstanceResponse.getFactors().add(factorToAdd2Loyal);
		logger.debug("quoteToWsAsset setted factor : "+factorToAdd2Loyal.getCode()+" value : "+factorToAdd2Loyal.getValue());
		
		String last3YearsCode = mapUty.getLast3Years(quote.getRatingInfo().getLast3YearsAggregatedClaims());
		WsFactor factorToAddLast3Years = new WsFactor();
		factorToAddLast3Years.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2SIN3.value());
		factorToAddLast3Years.setValue(last3YearsCode);
		assetInstanceResponse.getFactors().add(factorToAddLast3Years);
		logger.debug("quoteToWsAsset setted factor : "+factorToAddLast3Years.getCode()+" value : "+factorToAddLast3Years.getValue());
		
		String last6YearsCode = mapUty.getLast6Years(quote.getRatingInfo().getAggregatedClaims());
		WsFactor factorToAddLast6Years = new WsFactor();
		factorToAddLast6Years.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2SIN6.value());
		factorToAddLast6Years.setValue(last6YearsCode);
		assetInstanceResponse.getFactors().add(factorToAddLast6Years);
		logger.debug("quoteToWsAsset setted factor : "+factorToAddLast6Years.getCode()+" value : "+factorToAddLast6Years.getValue());
		
		List<WsFactor> factAsset= new ArrayList<WsFactor>();
		WsFactor wsFactor = new WsFactor();
		
		if(quote.getNumberOfClaimsInLastYear() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_SXTOT.value());
			wsFactor.setValue(quote.getNumberOfClaimsInLastYear().toString());
			logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
			factAsset.add(wsFactor);
		}
		if(quote.getUsualDriverOwnerRelationship() != null && quote.getUsualDriverOwnerRelationship().getWrapperCode() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2ROWMD.value());
			wsFactor.setValue(quote.getUsualDriverOwnerRelationship().getWrapperCode().toString());
			logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
			factAsset.add(wsFactor);
		}
		if(quote.getVehicle().getPreviousVehicleAgeInMonth()!= null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR__2MIMM.value());
			wsFactor.setValue(quote.getVehicle().getPreviousVehicleAgeInMonth().toString());
			logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
			factAsset.add(wsFactor);
		}
		if(quote.getFiddleFactorCalculationRequired()!= null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalWsProductFactors.FACTOR_1FLRFF.value());
			wsFactor.setValue(quote.getFiddleFactorCalculationRequired().toString());
			logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
			factAsset.add(wsFactor);
		}
		if(quote.getPreviousFlagClaimsInLastYear()!= null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalWsProductFactors.FACTOR_3FRC1.value());
			if(quote.getPreviousFlagClaimsInLastYear()==true)
			{
				wsFactor.setValue("false");
			}
			else
			{
				wsFactor.setValue("true");
			}
			wsFactor.setValue(quote.getPreviousFlagClaimsInLastYear().toString());
			logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
			factAsset.add(wsFactor);
		}
		if(quote.getPreviousCleanIn5()!= null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalWsProductFactors.FACTOR_3FRC5.value());
			wsFactor.setValue(quote.getPreviousCleanIn5().toString());
			logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
			factAsset.add(wsFactor);
		}
		if(quote.getBmalType() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2BERS.value());
			wsFactor.setValue(quote.getBmalType().getWrapperCode().toString());
			logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
			factAsset.add(wsFactor);
		}
		if(quote.getVehicle().getMatriculationYearMonth() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR__2DIMM.value());
			wsFactor.setValue(dateFormat.format(quote.getVehicle().getMatriculationYearMonth()));
			logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
			factAsset.add(wsFactor);
		}
		if(quote.getVehicle().getVehicleAge() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2ETAV.value());
			wsFactor.setValue(quote.getVehicle().getVehicleAge().toString());
			logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
			factAsset.add(wsFactor);
		}
		if(quote.getVehicle().getHabitualUse() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2USOVE.value());
			wsFactor.setValue(quote.getVehicle().getHabitualUse().getWrapperCode().toString());
			logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
			factAsset.add(wsFactor);
		}
		if(quote.getVehicle().getCarAgeAtPurchase() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2EVACQ.value());
			wsFactor.setValue(quote.getVehicle().getCarAgeAtPurchase().toString());
			logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
			factAsset.add(wsFactor);
		}
		if(quote.getNumberOfVehiclesOwned() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2ANUCF.value());
			wsFactor.setValue(quote.getNumberOfVehiclesOwned().toString());
			logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
			factAsset.add(wsFactor);
		}
		if(quote.getRatingInfo().getUwClass() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2CLUWR.value());
			wsFactor.setValue(quote.getRatingInfo().getUwClass());
			logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
			factAsset.add(wsFactor);
		}
		
		if(quote.getRatingInfo().getMajorClassA() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2PRIMC.value());
			wsFactor.setValue(quote.getRatingInfo().getMajorClassA());
			logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
			factAsset.add(wsFactor);
		}
				
		if(quote.getGoodDriverClass() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR__2BM.value());
			wsFactor.setValue(quote.getGoodDriverClass().getWrapperCode().toString()); 
			logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
			factAsset.add(wsFactor);
		}
				
		if(quote.getInnerClass() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR__2CU.value());
			wsFactor.setValue(quote.getInnerClass());
			logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
			factAsset.add(wsFactor);
		}
				
		if(quote.getDriverNumber() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2NDRIV.value());
			wsFactor.setValue(quote.getDriverNumber().toString());
			logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
			factAsset.add(wsFactor);
		}
		
		if(quote.getFnb() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2RINNB.value());
			wsFactor.setValue(quote.getFnb().toString());
			logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
			factAsset.add(wsFactor);
		}
		
		if(quote.getClean5() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_3CLIN5.value());
			wsFactor.setValue(quote.getClean5().toString());
			logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
			factAsset.add(wsFactor);
		}
		
		if(quote.getClean1() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_3CLIN1.value());
			wsFactor.setValue(quote.getClean1().toString());
			logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
			factAsset.add(wsFactor);
		}
		
		if(quote.getVehicle().getTechnicalData().getMakerId() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR__2CMAR.value());
			wsFactor.setValue(quote.getVehicle().getTechnicalData().getMakerId());
			logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
			factAsset.add(wsFactor);
		}
		
		if(quote.getVehicle().getTechnicalData().getType() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2RROAD.value());
			wsFactor.setValue(quote.getVehicle().getTechnicalData().getType().getWrapperCode().toString());
			logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
			factAsset.add(wsFactor);
		}
		
		if(quote.getVehicle().getTechnicalData().getModelId() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR__2CMOD.value());
			wsFactor.setValue(quote.getVehicle().getTechnicalData().getModelId());
			logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
			factAsset.add(wsFactor);
		}
		
		if(quote.getVehicle().getTechnicalData().getKw() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR__2KW.value());
			wsFactor.setValue(quote.getVehicle().getTechnicalData().getKw().toString());
			logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
			factAsset.add(wsFactor);
		}
		
		if(quote.getVehicle().getInsuredValue() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR__2VVAL.value());
			wsFactor.setValue(quote.getVehicle().getInsuredValue().toString());
			logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
			factAsset.add(wsFactor);
		}
		
		if(quote.getVehicle().getLeasingType() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2TVINC.value());
			wsFactor.setValue(quote.getVehicle().getLeasingType().getWrapperCode().toString());
			logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
			factAsset.add(wsFactor);
		}
		
		if(quote.getVehicle().getMatriculationYear() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2APIMM.value());
			wsFactor.setValue(quote.getVehicle().getMatriculationYear().toString());
			logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
			factAsset.add(wsFactor);
		}
		
		if(quote.getVehicle().getAntitheftType() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2ANTIF.value());
			wsFactor.setValue(quote.getVehicle().getAntitheftType().getWrapperCode().toString());
			logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
			factAsset.add(wsFactor);
		}
		
		if(quote.getVehicle().getTechnicalData() != null && quote.getVehicle().getTechnicalData() != null )
		{
			if(quote.getVehicle().getTechnicalData().getBodyType() != null &&
					quote.getVehicle().getTechnicalData().getBodyType().getWrapperCode() != null)
			{
				wsFactor = new WsFactor();
				String factorCode = null ;
				if(quote.getContext().getRiskType().equals(EnumRiskType.CAR))
				{
					factorCode = ENUMInternalAssetInstanceFactors.FACTOR_2TICAR.value();
				}
				else if(quote.getContext().getRiskType().equals(EnumRiskType.MOPED) ||
									quote.getContext().getRiskType().equals(EnumRiskType.MOTORBIKE))
				{
					factorCode = ENUMInternalAssetInstanceFactors.FACTOR_2BOMBK.value();
				}
				
				if(factorCode != null)
				{
					wsFactor.setCode(factorCode);
					wsFactor.setValue(quote.getVehicle().getTechnicalData().getBodyType().getWrapperCode().toString());
					logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
					factAsset.add(wsFactor);
				}
			}
			if(quote.getVehicle().getTechnicalData().getTheftAndFireRiskClass() != null)
			{
				wsFactor = new WsFactor();
				wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2MKMDL.value());
				wsFactor.setValue(quote.getVehicle().getTechnicalData().getTheftAndFireRiskClass().toString());
				logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
				factAsset.add(wsFactor);
			}
			
			if(quote.getVehicle().getTechnicalData().getAirbagType() != null)
			{
				wsFactor = new WsFactor();
				wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2NAIRB.value());
				wsFactor.setValue(quote.getVehicle().getTechnicalData().getAirbagType().getWrapperCode().toString());
				logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
				factAsset.add(wsFactor);
			}
			if(quote.getVehicle().getShelterType() != null && quote.getVehicle().getShelterType().getWrapperCode() != null)
			{
				wsFactor = new WsFactor();
				wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2PARKN.value());
				wsFactor.setValue(quote.getVehicle().getShelterType().getWrapperCode().toString());
				logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
				factAsset.add(wsFactor);
			}
			if(quote.getVehicle().getKmPerYear() != null)
			{
				wsFactor = new WsFactor();
				wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2KMAN.value());
				wsFactor.setValue(quote.getVehicle().getKmPerYear().toString());
				logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
				factAsset.add(wsFactor);
			}
		}

		if(quote.getRatingInfo() != null)
		{
			if(quote.getRatingInfo().getPromocode() != null)
			{
				wsFactor = new WsFactor();
				wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2PROMC.value());
				wsFactor.setValue(quote.getRatingInfo().getPromocode().getWrapperCode().toString());
				logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
				factAsset.add(wsFactor);
			}
			if(quote.getRatingInfo().getRegressionClass() != null)
			{
				wsFactor = new WsFactor();
				wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2REGRE.value());
				wsFactor.setValue(quote.getRatingInfo().getRegressionClass().getWrapperCode().toString());
				logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
				factAsset.add(wsFactor);
			}
		}
		if(quote.getContext().getSection() != null && quote.getContext().getSection().getWrapperCode() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2TIPGU.value());
			wsFactor.setValue(quote.getContext().getSection().getWrapperCode().toString());
			logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
			factAsset.add(wsFactor);
		}
		
		if (  quote.getOtherVehicle() != null && (
					!quote.getContext().getRiskType().equals(EnumRiskType.CAR) ||
				    !quote.getContext().getRiskType().equals(EnumRiskType.MOTORBIKE) ||
				    !quote.getContext().getRiskType().equals(EnumRiskType.MOPED))  )
		{
			logger.debug("quoteToWsAsset is othervehicle, risktype :"+quote.getContext().getRiskType());
			
			if(quote.getOtherVehicle().getTaxableHorsePower() != null)
			{
				wsFactor = new WsFactor();
				wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR__2CF.value());
				wsFactor.setValue(quote.getOtherVehicle().getTaxableHorsePower().toString());
				logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
				factAsset.add(wsFactor);
			}
		
			if(quote.getOtherVehicle().getCcCapacity() != null)
			{
				wsFactor = new WsFactor();
				wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR__2CC.value());
				wsFactor.setValue(quote.getOtherVehicle().getCcCapacity().toString());
				logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
				factAsset.add(wsFactor);
			}
			
			if(quote.getOtherVehicle().getNumberOfCitizens() != null)
			{
				wsFactor = new WsFactor();
				wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2NUMAB.value());
				wsFactor.setValue(quote.getOtherVehicle().getNumberOfCitizens().toString());
				logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
				factAsset.add(wsFactor);
			}
			
			if(quote.getOtherVehicle().getWeight() != null)
			{
				wsFactor = new WsFactor();
				wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_3MASSA.value());
				wsFactor.setValue(quote.getOtherVehicle().getWeight().toString());
				logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
				factAsset.add(wsFactor);
			}
			
			if(quote.getOtherVehicle().getNumberOfSeats() != null)
			{
				wsFactor = new WsFactor();
				wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2SEATS.value());
				wsFactor.setValue(quote.getOtherVehicle().getNumberOfSeats().toString());
				logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
				factAsset.add(wsFactor);
			}
			if(quote.getOtherVehicle().getOwnBehalf() != null)
			{
				wsFactor = new WsFactor();
				wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_3CONTO.value());
				if(quote.getOtherVehicle().getOwnBehalf()) {
					wsFactor.setValue("1");
					factAsset.add(wsFactor);
				}
				else
				{
					wsFactor.setValue("2");
					factAsset.add(wsFactor);
				}
				logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
			}
		}
		else
		{
			if(quote.getVehicle() != null && quote.getVehicle().getTechnicalData() != null)
			{
				if(quote.getVehicle().getTechnicalData().getTaxableHorsePower() != null)
				{
					wsFactor = new WsFactor();
					wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR__2CF.value());
					wsFactor.setValue(quote.getVehicle().getTechnicalData().getTaxableHorsePower().toString());
					logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
					factAsset.add(wsFactor);
				}
				if(quote.getVehicle().getTechnicalData().getCcCapacity() != null)
				{
					wsFactor = new WsFactor();
					wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR__2CC.value());
					wsFactor.setValue(quote.getVehicle().getTechnicalData().getCcCapacity().toString());
					logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
					factAsset.add(wsFactor);
				}
			}
		}
		if(quote.getOtherVehiclesInsuredWithUs() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2ALAUT.value());
			if(quote.getOtherVehiclesInsuredWithUs())
				wsFactor.setValue("true");
			else
				wsFactor.setValue("false");
			logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
			factAsset.add(wsFactor);
		}
		if(quote.getVehicle() != null &&
				quote.getVehicle().getTechnicalData() != null && quote.getVehicle().getTechnicalData().getAlimentation() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR__2ALIM.value());
			wsFactor.setValue(quote.getVehicle().getTechnicalData().getAlimentation().getWrapperCode().toString());
			logger.debug("quoteToWsAsset add factor : "+wsFactor.getCode()+" - value :"+wsFactor.getValue());
			factAsset.add(wsFactor);
		}
		
		logger.debug("quoteToWsAsset add "+factAsset.size()+" factors to assetInstance");
		assetInstanceResponse.getFactors().addAll(factAsset);
		
		List<WsFactor> listFactorFigures =  getFactorForFigureFromRoleAssetInstance(quote.getFigures());
		logger.debug("quoteToWsAsset add "+listFactorFigures.size()+" factors from figures to assetInstance");
		assetInstanceResponse.getFactors().addAll(listFactorFigures);

		listAssetInstResponse.add(assetInstanceResponse);

		logger.info("quoteToWsAsset out with response : "+listAssetInstResponse);
		return listAssetInstResponse;
	}
	
	
	/**
	 * Metodo custom di quoteDtoToAsset
	 * @param inb
	 * @return
	 */
	public List<WsVehicle> quoteToWsVehicle(InboundRequestHttpJSON inb)
	{
		logger.debug("quoteToWsVehicle set frome vehicle with input : "+inb);
		ArrayList<WsVehicle> ve = new ArrayList<WsVehicle>();
		WsVehicle wsVe = new WsVehicle();

		String ccode = getRiskTypeAsset(inb, inb.getInboundQuoteDTO().getOtherVehicle());
		logger.debug("quoteToWsVehicle ClassCode = "+ccode);
		wsVe.setClassCode(ccode);
		logger.debug("quoteToWsVehicle SectorCode = " + this.sectorCode);
		wsVe.setSectorCode(this.sectorCode);
		
		String useCode = "";
		try
		{
			if(inb.getInboundQuoteDTO().getVehicle().getTechnicalData().getPraUse().equals(EnumPublicRegisterUse.MIXED))
			{
				useCode = "000007";
			}
			else if(inb.getInboundQuoteDTO().getVehicle().getTechnicalData().getPraUse().equals(EnumPublicRegisterUse.PRIVATE_CAR))
			{
				useCode = "000005";
			}
		}
		catch(NullPointerException ex)
		{
			logger.error("Into quoteToWsVehicle something null into quote, impossible set useCode for Vehicle : "+ex.getMessage());
		}
		
		logger.debug("quoteToWsVehicle UseCode = "+useCode);
		wsVe.setUseCode(useCode);
		
		ve.add(wsVe);
		
		logger.debug("quoteToWsVehicle set from vehicle with output : "+ve);
		return ve;
	}
	
	/**
	 * For level WsAsset->vehicle , calculate of risk type
	 * @param inb
	 * @param iOtherVehicle 
	 * @return ClassCode and edit this.sectorCode
	 */
	private String getRiskTypeAsset(InboundRequestHttpJSON inb, IOtherVehicle iOtherVehicle)
	{
		logger.debug("getRiskTypeAsset with input : "+inb);
		
		EnumRiskType riskTypeQuoteEnum = inb.getInboundQuoteDTO().getContext().getRiskType();
		
		String responseClassCodeForPASS = "" ;
		if(riskTypeQuoteEnum.equals(EnumRiskType.CAR) || riskTypeQuoteEnum.equals(EnumRiskType.CAR_TRAILER))
		{
			responseClassCodeForPASS = "01";
			this.sectorCode = "000001";
		}
		else if(riskTypeQuoteEnum.equals(EnumRiskType.MOTORBIKE))
		{
			responseClassCodeForPASS = "991";
			this.sectorCode = "000005";
		}
		if(riskTypeQuoteEnum.equals(EnumRiskType.MOPED))
		{
			responseClassCodeForPASS = "30";
			this.sectorCode = "000005";
		}
		if(riskTypeQuoteEnum.equals(EnumRiskType.TAXI))
		{
			responseClassCodeForPASS = "03";
			this.sectorCode = "000003";
		}
		if(riskTypeQuoteEnum.equals(EnumRiskType.URBAN_BUS))
		{
			responseClassCodeForPASS = "06";
			this.sectorCode = "000004";
		}
		if(riskTypeQuoteEnum.equals(EnumRiskType.OUT_OF_TOWN_TURISTIC_BUS))
		{
			responseClassCodeForPASS = "02";
			this.sectorCode = "000004";
		}
		if(riskTypeQuoteEnum.equals(EnumRiskType.BUS_TRAILER))
		{
			responseClassCodeForPASS = "988";
			this.sectorCode = "000004";
		}
		if(riskTypeQuoteEnum.equals(EnumRiskType.TRUCK_UPTO_60000KG))
		{
			responseClassCodeForPASS = "09";
			this.sectorCode = "000006";
		}
		if(riskTypeQuoteEnum.equals(EnumRiskType.TRUCK_MORE_THAN_60000KG))
		{
			responseClassCodeForPASS = "10";
			this.sectorCode = "000006";
		}
		if(riskTypeQuoteEnum.equals(EnumRiskType.CAMPER))
		{
			responseClassCodeForPASS = "11";
			this.sectorCode = "000006";
		}
		if(riskTypeQuoteEnum.equals(EnumRiskType.MOTORCYCLE_FREIGHT_TRANSPORT))
		{
			responseClassCodeForPASS = "995";
			this.sectorCode = "000006";
		}
		if(riskTypeQuoteEnum.equals(EnumRiskType.TRUCK_TRAILER))
		{
			if(iOtherVehicle != null && iOtherVehicle.getWeight() > 60)
			{
				responseClassCodeForPASS = "15";
			}
			else if(iOtherVehicle != null && iOtherVehicle.getWeight() <= 60)
			{
				responseClassCodeForPASS = "996";
			}
			this.sectorCode = "000006";
		}
		if(riskTypeQuoteEnum.equals(EnumRiskType.SPECIAL_VEHICLE))
		{
			responseClassCodeForPASS = "980";
			this.sectorCode = "000007";
		}
		if(riskTypeQuoteEnum.equals(EnumRiskType.AGRICULTURAL_MACHINERY))
		{
			responseClassCodeForPASS = "19";
			this.sectorCode = "000008";
		}
		if(riskTypeQuoteEnum.equals(EnumRiskType.AGRICULTURAL_MACHINERY_TRAILER))
		{
			responseClassCodeForPASS = "76";
			this.sectorCode = "000008";
		}
		if(riskTypeQuoteEnum.equals(EnumRiskType.SPECIAL_VEHICLE_TRAILER))
		{
			responseClassCodeForPASS = "22";
			this.sectorCode = "000007";
		}
		logger.debug("getRiskTypeAsset for  riskType :"+riskTypeQuoteEnum);
		
		logger.info("getRiskTypeAsset with output classCode : "+responseClassCodeForPASS+" and sectorCode : "+this.sectorCode);
		return responseClassCodeForPASS;
	}

//	Generic types mappings 
	
	/**
	 * Return a TypeBooleano from input for PASS
	 * @param bool
	 * @return TypeBooleano from Boolean
	 */
	public TypeBooleano boolToTypeBool(Boolean bool)
	{
		typeB = new TypeBooleano();
		typeB.setBoolean(bool);
		
		return typeB;
	}
	
	/**
	 * Return a TypeData for PASS from a passed Date
	 * @param Date data
	 * @return TypeData from input
	 */
	public TypeData dataToTypeData(Date data)
	{
		
		TypeData dataOpenTypeData  = new TypeData(); 
		GregorianCalendar c = new GregorianCalendar();
		XMLGregorianCalendar dataOpen = null;
		try
		{
			c.setTime(data);
			dataOpen = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		}
		catch (NullPointerException ex)
		{
			logger.error("Date passed is null : "+data+" ");
		}
		catch (DatatypeConfigurationException ex)
		{
			logger.error("Error conversion for data : "+data+" ");
		}
		dataOpenTypeData.setData(dataOpen);
		
		return dataOpenTypeData;
	
	}


}
