package it.cg.main.conf.mapping.easyway;

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

import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.ICoverage;
import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.IFigure;
import com.mapfre.engines.rating.common.enums.EnumFlowType;
import com.mapfre.engines.rating.common.enums.EnumProductType;
import com.mapfre.engines.rating.common.enums.EnumPublicRegisterUse;
import com.mapfre.engines.rating.common.enums.EnumRiskType;
import com.mapfre.engines.rating.common.enums.EnumRole;
import com.pass.global.TypeBooleano;
import com.pass.global.TypeData;
import com.pass.global.WsAssetInstance;
import com.pass.global.WsClause;
import com.pass.global.WsFactor;
import com.pass.global.WsProduct;
import com.pass.global.WsVehicle;

import it.cg.main.conf.mapping.easyway.utilities.MapUty;
import it.cg.main.dto.InboundRequestHttpJSON;
import it.cg.main.dto.main.Quote;
import it.cg.main.integration.mapper.enumerations.ENUMInternalAssetInstanceFactors;
import it.cg.main.integration.mapper.enumerations.ENUMInternalCodeProduct;
import it.cg.main.integration.mapper.enumerations.ENUMInternalUnitInstanceFactors;
import it.cg.main.integration.mapper.enumerations.ENUMInternalWsProductFactors;

@Service
public class MapperProductAssetToPASS
{
	private Logger logger = Logger.getLogger(getClass());
	
	private TypeBooleano typeB =  new TypeBooleano();
//	Pattern aggiornato to PASS
	private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	
	/**
	 * Get factors for WsProduct from FIGURES
	 * EnumRole.POLICY_HOLDER
	 * @param role
	 * @param listFigure
	 * @return
	 */
	private List<WsFactor> getFactorForFigureFromRoleWSProduct(List<IFigure> listFigure)
	{
		List<WsFactor> listFactor = new ArrayList<WsFactor>();
		WsFactor wsFactor = new WsFactor();
		
		
		for (IFigure figureTemp : listFigure)
		{
//			Il wsproduct prevede che i dati vengano presi dal PH per i factors 
			if(figureTemp.getRole().equals(EnumRole.POLICY_HOLDER))
			{
				if(figureTemp.getAge() != null)
				{
					wsFactor = new WsFactor();
					wsFactor.setCode(ENUMInternalWsProductFactors.FACTOR__1CETA.value());
					wsFactor.setValue(figureTemp.getAge().toString());
					listFactor.add(wsFactor);
				}
				if(figureTemp.getResidenceAddress() != null)
				{
					if(figureTemp.getResidenceAddress().getCap() != null)
					{
						wsFactor = new WsFactor();
						wsFactor.setCode(ENUMInternalWsProductFactors.FACTOR__1CNCA.value());
						wsFactor.setValue(figureTemp.getResidenceAddress().getCap());
						listFactor.add(wsFactor);
					}
				}
				if(figureTemp.getYearsWithLicense() != null)
				{
					wsFactor = new WsFactor();
					wsFactor.setCode(ENUMInternalWsProductFactors.FACTOR_1PHAP.value());
					if(figureTemp.getLicensed())
					{
						wsFactor.setValue(figureTemp.getYearsWithLicense().toString());
					}
					else
					{
						wsFactor.setValue("-1");
					}
					listFactor.add(wsFactor);
				}
				
				wsFactor = new WsFactor();
				wsFactor.setCode(ENUMInternalWsProductFactors.FACTOR_1PHSC.value());
				wsFactor.setValue(figureTemp.getMaritalStatus().getWrapperCode().toString());
				listFactor.add(wsFactor);
				
				break;
			}
		}
		
		return listFactor;
	}
	
	
	
	/**
	 * Metodo custom di quoteDtoToProduct
	 * @param quote
	 * @return
	 */
	public WsProduct quoteToListWsProduct(Quote quote)
	{
		WsProduct prod = new WsProduct();
		
//		Fields
		prod.setCurrencyCode("000001");
		prod.setOpenDate(dataToTypeData(quote.getRateFromDate()));

		if(quote.getInstallments()==1)
		{
			prod.setPaymentFrequencyCode("000001");
		}
		else if(quote.getInstallments()==2)
		{
			prod.setPaymentFrequencyCode("000002");
		}

//				Operation code
		String opCode = null;
		if(quote.getContext().getFlowType().equals(EnumFlowType.REVIEW_GENERATION))
		{
			opCode="000074";
		}
		else if(quote.getContext().getFlowType().equals(EnumFlowType.RENQUOTE_GENERATION))
		{
			opCode="000075";
		}
		prod.setOperationCode(opCode);
		
		
//		code product
		if(quote.getContext().getRiskType() != null && quote.getContext().getProductType() != null && 
				quote.getContext().getDirectlineSelfService() != null)
		{
			if(quote.getContext().getRiskType().equals(EnumRiskType.CAR) &&
					quote.getContext().getProductType().equals(EnumProductType.DLI)&&
					quote.getContext().getDirectlineSelfService()==false)
			{
//				prod.setCode(quote.getContext().getRiskType().getWrapperCode().toString());
				prod.setCode(ENUMInternalCodeProduct.CODE_AUTODLI.value());
			}
			else if(  quote.getContext().getProductType().equals(EnumProductType.DLI) &&
					  	( quote.getContext().getRiskType().equals(EnumRiskType.MOTORBIKE) ||
					  		quote.getContext().getRiskType().equals(EnumRiskType.MOPED) ) &&
					  		quote.getContext().getDirectlineSelfService()==false)
			{
				prod.setCode(ENUMInternalCodeProduct.CODE_MOTOCICLODLI.value());
			}
			else if(quote.getContext().getRiskType().equals(EnumRiskType.CAR) &&
						quote.getContext().getProductType().equals(EnumProductType.DLI)&&
						quote.getContext().getDirectlineSelfService()==true)
			{
				prod.setCode(ENUMInternalCodeProduct.CODE_AUTODLSS.value());
			}
			else if ( (quote.getContext().getProductType().equals(EnumProductType.ADI))&&
					   		quote.getContext().getRiskType().equals(EnumRiskType.CAR) &&
					   		quote.getContext().getDirectlineSelfService()==false)
			{
				prod.setCode(ENUMInternalCodeProduct.CODE_AUTOADI.value());
			}
			else if ( ( !quote.getContext().getRiskType().equals(EnumRiskType.CAR) &&
							  !quote.getContext().getRiskType().equals(EnumRiskType.MOTORBIKE) &&
							  !quote.getContext().getRiskType().equals(EnumRiskType.MOPED) ) &&
							    quote.getContext().getProductType().equals(EnumProductType.DLI) &&
							    quote.getContext().getDirectlineSelfService()==false)
			{
				prod.setCode(ENUMInternalCodeProduct.CODE_ALTRIVEICOLIDLI.value());
			}
		}
//		Factors
		ArrayList<WsFactor> factProp = new ArrayList<WsFactor>();
		WsFactor wsFactor = new WsFactor();
		if(quote.getAffinity() != null)
		{
			wsFactor.setCode(ENUMInternalWsProductFactors.FACTOR_1AFF.value());
			wsFactor.setValue(quote.getAffinity());
			factProp.add(wsFactor);
		}
		if(quote.getRenewalYears() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalWsProductFactors.FACTOR_1ANRIN.value());
			wsFactor.setValue(quote.getRenewalYears().toString());
			factProp.add(wsFactor);
		}
		if(quote.getPreviousRenewalYears() != null )
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalWsProductFactors.FACTOR_1FRRIN.value());
			wsFactor.setValue(quote.getPreviousRenewalYears().toString());
			factProp.add(wsFactor);
		}
		if(quote.getCampaign() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalWsProductFactors.FACTOR_1CSC.value());
			//wsFactor.setValue(figureTemp.getOccupation().getWrapperCode().toString());
//			TODO da avere il ENUM da proxy
			wsFactor.setValue(quote.getCampaign().toString());
			factProp.add(wsFactor);
		}
		
		prod.getFactors().addAll(factProp);
		List<WsFactor> listFactorsFigures = getFactorForFigureFromRoleWSProduct(quote.getFigures());
		prod.getFactors().addAll(listFactorsFigures);
		
		return prod;
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
		List<WsFactor> listFactor = new ArrayList<WsFactor>();
		WsFactor wsFactor = new WsFactor();
		
		for (IFigure figureTemp : listFigure)
		{
//			USUAL_DRIVER 
			if(figureTemp.getRole().equals(EnumRole.USUAL_DRIVER))
			{
				if(figureTemp.getAge() != null)
				{
					wsFactor = new WsFactor();
					wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR__2CETA.value());
					wsFactor.setValue(figureTemp.getAge().toString());
					listFactor.add(wsFactor);
				}
				if(figureTemp.getResidenceAddress() != null)
				{
					if(figureTemp.getResidenceAddress().getCap() != null)
					{
						wsFactor = new WsFactor();
						wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR__2CRCA.value());
						wsFactor.setValue(figureTemp.getResidenceAddress().getCap());
						listFactor.add(wsFactor);
					}
				}
				if(figureTemp.getYearsWithLicense() != null)
				{
					wsFactor = new WsFactor();
					wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2MDAP.value());
					if(figureTemp.getLicensed())
					{
						wsFactor.setValue(figureTemp.getYearsWithLicense().toString());
					}
					else
					{
						wsFactor.setValue("-1");
					}
					listFactor.add(wsFactor);
				}
				
				wsFactor = new WsFactor();
				wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2MDPR.value());
				wsFactor.setValue(figureTemp.getOccupation().getWrapperCode().toString());
				listFactor.add(wsFactor);
				
				wsFactor = new WsFactor();
				wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2MDSC.value());
				wsFactor.setValue(figureTemp.getMaritalStatus().getWrapperCode().toString());
				listFactor.add(wsFactor);
			}
//			OWNER
			if(figureTemp.getRole().equals(EnumRole.OWNER))
			{
				if(figureTemp.getAge() != null)
				{
					wsFactor = new WsFactor();
					wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR__2PETA.value());
					wsFactor.setValue(figureTemp.getAge().toString());
					listFactor.add(wsFactor);
				}
				if(figureTemp.getResidenceAddress() != null)
				{
					if(figureTemp.getResidenceAddress().getCap() != null)
					{
						wsFactor = new WsFactor();
						wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR__2PRCA.value());
						wsFactor.setValue(figureTemp.getResidenceAddress().getCap());
						listFactor.add(wsFactor);
					}
				}
				if(figureTemp.getYearsWithLicense() != null)
				{
					wsFactor = new WsFactor();
					wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2OWAP.value());
					if(figureTemp.getLicensed())
					{
						wsFactor.setValue(figureTemp.getYearsWithLicense().toString());
					}
					else
					{
						wsFactor.setValue("-1");
					}
					listFactor.add(wsFactor);
				}
				
				wsFactor = new WsFactor();
				wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2OWSC.value());
				wsFactor.setValue(figureTemp.getMaritalStatus().getWrapperCode().toString());
				listFactor.add(wsFactor);
			}
//			FIRST YOUNG DRIVER
			if(figureTemp.getRole().equals(EnumRole.FIRST_YOUNG_DRIVER))
			{
				if(figureTemp.getYearsWithLicense() != null)
				{
					wsFactor = new WsFactor();
					wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2RD1AP.value());
					if(figureTemp.getLicensed())
					{
						wsFactor.setValue(figureTemp.getYearsWithLicense().toString());
					}
					else
					{
						wsFactor.setValue("-1");
					}
					listFactor.add(wsFactor);
				}
				if(figureTemp.getResidenceAddress() != null)
				{
					if(figureTemp.getResidenceAddress().getCap() != null)
					{
						wsFactor = new WsFactor();
						wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2RD1CA.value());
						wsFactor.setValue(figureTemp.getResidenceAddress().getCap());
						listFactor.add(wsFactor);
					}
				}
				if(figureTemp.getAge() != null)
				{
					wsFactor = new WsFactor();
					wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2RD1ET.value());
					wsFactor.setValue(figureTemp.getAge().toString());
					listFactor.add(wsFactor);
				}
				
				wsFactor = new WsFactor();
				wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2RD1SC.value());
				wsFactor.setValue(figureTemp.getMaritalStatus().getWrapperCode().toString());
				listFactor.add(wsFactor);
			}
//			FIRST_YOUNG_DRIVER
			if(figureTemp.getRole().equals(EnumRole.FIRST_YOUNG_DRIVER))
			{
				if(figureTemp.getYearsWithLicense() != null)
				{
					wsFactor = new WsFactor();
					wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2RD2AP.value());
					if(figureTemp.getLicensed())
					{
						wsFactor.setValue(figureTemp.getYearsWithLicense().toString());
					}
					else
					{
						wsFactor.setValue("-1");
					}
					listFactor.add(wsFactor);
				}
				if(figureTemp.getResidenceAddress() != null)
				{
					if(figureTemp.getResidenceAddress().getCap() != null)
					{
						wsFactor = new WsFactor();
						wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2RD2CA.value());
						wsFactor.setValue(figureTemp.getResidenceAddress().getCap());
						listFactor.add(wsFactor);
					}
				}
				if(figureTemp.getAge() != null)
				{
					wsFactor = new WsFactor();
					wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2RD2ET.value());
					wsFactor.setValue(figureTemp.getAge().toString());
					listFactor.add(wsFactor);
				}
				
				wsFactor = new WsFactor();
				wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2RD2SC.value());
				wsFactor.setValue(figureTemp.getMaritalStatus().getWrapperCode().toString());
				listFactor.add(wsFactor);
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
		WsAssetInstance assetInstanceresponse = new WsAssetInstance();
		
//		factor 2Loyal and lastYears 3 and 6
		MapUty mapUty = new MapUty();

		String loyalCode = mapUty.get2Loyal(quote.getRatingInfo().getCompanyChangesDetails());
		WsFactor factorToAdd2Loyal = new WsFactor();
		factorToAdd2Loyal.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2LOYAL.value());
		factorToAdd2Loyal.setValue(loyalCode);
		assetInstanceresponse.getFactors().add(factorToAdd2Loyal);
		
		String last3YearsCode = mapUty.getLast3Years(quote.getRatingInfo().getLast3YearsAggregatedClaims());
		WsFactor factorToAddLast3Years = new WsFactor();
		factorToAddLast3Years.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2SIN3.value());
		factorToAddLast3Years.setValue(last3YearsCode);
		assetInstanceresponse.getFactors().add(factorToAddLast3Years);
		
		String last6YearsCode = mapUty.getLast6Years(quote.getRatingInfo().getAggregatedClaims());
		WsFactor factorToAddLast6Years = new WsFactor();
		factorToAddLast6Years.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2SIN6.value());
		factorToAddLast6Years.setValue(last6YearsCode);
		assetInstanceresponse.getFactors().add(factorToAddLast6Years);
		
		List<WsFactor> factAsset= new ArrayList<WsFactor>();
		WsFactor wsFactor = new WsFactor();
		
		if(quote.getNumberOfClaimsInLastYear() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_SXTOT.value());
			wsFactor.setValue(quote.getNumberOfClaimsInLastYear().toString());
			factAsset.add(wsFactor);
		}
		if(quote.getUsualDriverOwnerRelationship() != null && quote.getUsualDriverOwnerRelationship().getWrapperCode() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2ROWMD.value());
			wsFactor.setValue(quote.getUsualDriverOwnerRelationship().getWrapperCode().toString());
			factAsset.add(wsFactor);
		}
		//Non è sicuro se il mappaggio è giusto
		if(quote.getVehicle().getPreviousVehicleAgeInMonth()!= null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR__2MIMM.value());
			wsFactor.setValue(quote.getVehicle().getPreviousVehicleAgeInMonth().toString());
			factAsset.add(wsFactor);
		}
		
		//Non è sicuro se il mappaggio è giusto
		if(quote.getFiddleFactorCalculationRequired()!= null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalWsProductFactors.FACTOR_1FLRFF.value());
			wsFactor.setValue(quote.getFiddleFactorCalculationRequired().toString());
			factAsset.add(wsFactor);
		}
		//Non è sicuro se il mappaggio è giusto. Controllare se ci sono novità
		if(quote.getPreviousFlagClaimsInLastYear()!= null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalWsProductFactors.FACTOR_3FRC1.value());
			
//			TODO CAMBIARE IL SET DENTRO QUESTA CONDIZIONE!!
			if(quote.getPreviousFlagClaimsInLastYear()==true){
				
				wsFactor.setValue("false");
			}else {
				
				wsFactor.setValue("true");
				
			}
			wsFactor.setValue(quote.getPreviousFlagClaimsInLastYear().toString());
			factAsset.add(wsFactor);
		}
//		TODO Non è sicuro se il mappaggio è giusto
		if(quote.getPreviousCleanIn5()!= null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalWsProductFactors.FACTOR_3FRC5.value());
			wsFactor.setValue(quote.getPreviousCleanIn5().toString());
			factAsset.add(wsFactor);
		}
		if(quote.getBmalType() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2BERS.value());
			wsFactor.setValue(quote.getBmalType().getWrapperCode().toString());
			factAsset.add(wsFactor);
		}
		if(quote.getVehicle().getMatriculationYearMonth() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR__2DIMM.value());
			wsFactor.setValue(dateFormat.format(quote.getVehicle().getMatriculationYearMonth()));
			factAsset.add(wsFactor);
		}
		if(quote.getVehicle().getVehicleAge() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2ETAV.value());
			wsFactor.setValue(quote.getVehicle().getVehicleAge().toString());
			factAsset.add(wsFactor);
		}
		if(quote.getVehicle().getHabitualUse() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2USOVE.value());
			wsFactor.setValue(quote.getVehicle().getHabitualUse().getWrapperCode().toString());
			factAsset.add(wsFactor);
		}
		if(quote.getVehicle().getCarAgeAtPurchase() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2EVACQ.value());
			wsFactor.setValue(quote.getVehicle().getCarAgeAtPurchase().toString());
			factAsset.add(wsFactor);
		}
		if(quote.getNumberOfVehiclesOwned() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2ANUCF.value());
			wsFactor.setValue(quote.getNumberOfVehiclesOwned().toString());
			factAsset.add(wsFactor);
		}
		if(quote.getRatingInfo().getUwClass() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2CLUWR.value());
			wsFactor.setValue(quote.getRatingInfo().getUwClass());
			factAsset.add(wsFactor);
		}
		
		if(quote.getRatingInfo().getMajorClassA() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2PRIMC.value());
			wsFactor.setValue(quote.getRatingInfo().getMajorClassA());
			factAsset.add(wsFactor);
		}
				
		if(quote.getGoodDriverClass() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR__2BM.value());
			wsFactor.setValue(quote.getGoodDriverClass());// TODO diventa un enum 
			factAsset.add(wsFactor);
		}
				
		if(quote.getInnerClass() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR__2CU.value());
			wsFactor.setValue(quote.getInnerClass());
			factAsset.add(wsFactor);
		}
				
		if(quote.getDriverNumber() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2NDRIV.value());
			wsFactor.setValue(quote.getDriverNumber().toString());
			factAsset.add(wsFactor);
		}
		
		if(quote.getFnb() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2RINNB.value());
			wsFactor.setValue(quote.getFnb().toString());
			factAsset.add(wsFactor);
		}
		
		if(quote.getClean5() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_3CLIN5.value());
			wsFactor.setValue(quote.getClean5().toString());
			factAsset.add(wsFactor);
		}
		
		if(quote.getClean1() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_3CLIN1.value());
			wsFactor.setValue(quote.getClean1().toString());
			factAsset.add(wsFactor);
		}
		
		if(quote.getVehicle().getTechnicalData().getMakerId() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR__2CMAR.value());
			wsFactor.setValue(quote.getVehicle().getTechnicalData().getMakerId());
			factAsset.add(wsFactor);
		}
		
		if(quote.getVehicle().getTechnicalData().getType() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2RROAD.value());
			wsFactor.setValue(quote.getVehicle().getTechnicalData().getType().getWrapperCode().toString());
			factAsset.add(wsFactor);
		}
		
		if(quote.getVehicle().getTechnicalData().getModelId() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR__2CMOD.value());
			wsFactor.setValue(quote.getVehicle().getTechnicalData().getModelId());
			factAsset.add(wsFactor);
		}
		
		if(quote.getVehicle().getTechnicalData().getKw() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR__2KW.value());
			wsFactor.setValue(quote.getVehicle().getTechnicalData().getKw().toString());
			factAsset.add(wsFactor);
		}
		
		if(quote.getVehicle().getInsuredValue() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR__2VVAL.value());
			wsFactor.setValue(quote.getVehicle().getInsuredValue().toString());
			factAsset.add(wsFactor);
		}
		
		if(quote.getVehicle().getLeasingType() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2TVINC.value());
			wsFactor.setValue(quote.getVehicle().getLeasingType().getWrapperCode().toString());
			factAsset.add(wsFactor);
		}
		
		if(quote.getVehicle().getMatriculationYear() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2APIMM.value());
			wsFactor.setValue(quote.getVehicle().getMatriculationYear().toString());
			factAsset.add(wsFactor);
		}
		
		if(quote.getVehicle().getAntitheftType() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2ANTIF.value());
			wsFactor.setValue(quote.getVehicle().getAntitheftType().getWrapperCode().toString());
			factAsset.add(wsFactor);
		}
		
		if(quote.getVehicle().getTechnicalData() != null && quote.getVehicle().getTechnicalData() != null )
		{
			if(quote.getVehicle().getTechnicalData().getBodyType() != null &&
					quote.getVehicle().getTechnicalData().getBodyType().getWrapperCode() != null)
			{
				wsFactor = new WsFactor();
				wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2BOMBK.value());
				wsFactor.setValue(quote.getVehicle().getTechnicalData().getBodyType().getWrapperCode().toString());
				factAsset.add(wsFactor);
			}
			if(quote.getVehicle().getTechnicalData().getTheftAndFireRiskClass() != null)
			{
				wsFactor = new WsFactor();
				wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2MKMDL.value());
				wsFactor.setValue(quote.getVehicle().getTechnicalData().getTheftAndFireRiskClass().toString());
				factAsset.add(wsFactor);
			}
			
			if(quote.getVehicle().getTechnicalData().getAirbagType() != null)
			{
				wsFactor = new WsFactor();
				wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2NAIRB.value());
				wsFactor.setValue(quote.getVehicle().getTechnicalData().getAirbagType().getWrapperCode().toString());
				factAsset.add(wsFactor);
			}
			if(quote.getVehicle().getShelterType() != null && quote.getVehicle().getShelterType().getWrapperCode() != null)
			{
				wsFactor = new WsFactor();
				wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2PARKN.value());
				wsFactor.setValue(quote.getVehicle().getShelterType().getWrapperCode().toString());
				factAsset.add(wsFactor);
			}
//			TODO Controllare se lato DL ci passano 10 anzichè 10000
			if(quote.getVehicle().getKmPerYear() != null)
			{
				wsFactor = new WsFactor();
				wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2KMAN.value());
				wsFactor.setValue(quote.getVehicle().getKmPerYear().toString());
				factAsset.add(wsFactor);
			}
		}

		if(quote.getRatingInfo() != null)
		{
//			TODO prevdere un enum, aspettare risp
			if(quote.getRatingInfo().getPromocode() != null)
			{
				wsFactor = new WsFactor();
				wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2PROMC.value());
				wsFactor.setValue(quote.getRatingInfo().getPromocode().getWrapperCode().toString());
				factAsset.add(wsFactor);
			}
			if(quote.getRatingInfo().getRegressionClass() != null)
			{
				wsFactor = new WsFactor();
				wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2REGRE.value());
				wsFactor.setValue(quote.getRatingInfo().getRegressionClass());
				factAsset.add(wsFactor);
			}
		}
		if(quote.getContext().getSection() != null && quote.getContext().getSection().getWrapperCode() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2TIPGU.value());
			wsFactor.setValue(quote.getContext().getSection().getWrapperCode().toString());
			factAsset.add(wsFactor);
		}
		
		// // Aggiunto fattore  mamarino -> verificare fattori nell'else
		if (  quote.getOtherVehicle() != null && (		
				!quote.getContext().getRiskType().equals(EnumRiskType.CAR) ||
		    !quote.getContext().getRiskType().equals(EnumRiskType.MOTORBIKE) ||
		    !quote.getContext().getRiskType().equals(EnumRiskType.MOPED))  )
		{
			if(quote.getOtherVehicle().getTaxableHorsePower() != null)
			{
				wsFactor = new WsFactor();
				wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR__2CF.value());
				wsFactor.setValue(quote.getOtherVehicle().getTaxableHorsePower().toString());
				factAsset.add(wsFactor);
			}
		
			if(quote.getOtherVehicle().getCcCapacity() != null)
			{
				wsFactor = new WsFactor();
				wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR__2CC.value());
				wsFactor.setValue(quote.getOtherVehicle().getCcCapacity().toString());
				factAsset.add(wsFactor);
			}
			
			if(quote.getOtherVehicle().getNumberOfCitizens() != null)
			{
				wsFactor = new WsFactor();
				wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2NUMAB.value());
				wsFactor.setValue(quote.getOtherVehicle().getNumberOfCitizens().toString());
				factAsset.add(wsFactor);
			}
			
			if(quote.getOtherVehicle().getWeight() != null)
			{
				wsFactor = new WsFactor();
				wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_3MASSA.value());
				wsFactor.setValue(quote.getOtherVehicle().getWeight().toString());
				factAsset.add(wsFactor);
			}
			
			if(quote.getOtherVehicle().getNumberOfSeats() != null)
			{
				wsFactor = new WsFactor();
				wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2SEATS.value());
				wsFactor.setValue(quote.getOtherVehicle().getNumberOfSeats().toString());
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
					factAsset.add(wsFactor);
				}
				if(quote.getVehicle().getTechnicalData().getCcCapacity() != null)
				{
					wsFactor = new WsFactor();
					wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR__2CC.value());
					wsFactor.setValue(quote.getVehicle().getTechnicalData().getCcCapacity().toString());
					factAsset.add(wsFactor);
				}
			}
	
		}
//		TODO da controllare le regole per l'other vheicle
		if(quote.getOtherVehiclesInsuredWithUs() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2ALAUT.value());
			if(quote.getOtherVehiclesInsuredWithUs())
				wsFactor.setValue("true");
			else
				wsFactor.setValue("false");
			factAsset.add(wsFactor);
		}


		if(quote.getVehicle() != null &&
				quote.getVehicle().getTechnicalData() != null && quote.getVehicle().getTechnicalData().getAlimentation() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR__2ALIM.value());
			wsFactor.setValue(quote.getVehicle().getTechnicalData().getAlimentation().getWrapperCode().toString());
			factAsset.add(wsFactor);
		}
		
		assetInstanceresponse.getFactors().addAll(factAsset);
		
		List<WsFactor> listFactorFigures =  getFactorForFigureFromRoleAssetInstance(quote.getFigures());
		assetInstanceresponse.getFactors().addAll(listFactorFigures);
		
		listAssetInstResponse.add(assetInstanceresponse);
				
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

		String sectorCode = "" ;
		String ccode = getRiskTypeAsset(inb, sectorCode);
		
		logger.debug("ClassCode = "+ccode);
		wsVe.setClassCode(ccode);
		logger.debug("SectorCode = "+sectorCode);
		wsVe.setSectorCode(sectorCode);
		
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
		
		logger.debug("UseCode = "+useCode);
		wsVe.setUseCode(useCode);
		
		ve.add(wsVe);
		
		logger.debug("quoteToWsVehicle set from vehicle with output : "+ve);
		return ve;
	}
	
	/**
	 * For level WsAsset->vehicle , calculate of risk type
	 * @param inb
	 * @return ClassCode and edit sectorCode
	 */
	private String getRiskTypeAsset(InboundRequestHttpJSON inb, String sectorCode)
	{
		logger.debug("getRiskTypeAsset with input : "+inb);
		
//		TODO da definire bene tutti i casi, alcuni sull'excel non sono chiari
		String response = "" ;
		if(inb.getInboundQuoteDTO().getContext().getRiskType().equals(EnumRiskType.CAR) ||
				inb.getInboundQuoteDTO().getContext().getRiskType().equals(EnumRiskType.CAR_TRAILER) )
		{
			response = "01";
			sectorCode = "000001";
		}
		else if(inb.getInboundQuoteDTO().getContext().getRiskType().equals(EnumRiskType.MOTORBIKE))
		{
			response = "991";
			sectorCode = "000005";
		}
		if(inb.getInboundQuoteDTO().getContext().getRiskType().equals(EnumRiskType.MOPED))
		{
			response = "30";
			sectorCode = "000005";
		}
		if(inb.getInboundQuoteDTO().getContext().getRiskType().equals(EnumRiskType.URBAN_BUS))
		{
			response = "06";
			sectorCode = "000004";
		}
		if(inb.getInboundQuoteDTO().getContext().getRiskType().equals(EnumRiskType.OUT_OF_TOWN_TURISTIC_BUS))
		{
			response = "02";
			sectorCode = "000004";
		}
		if(inb.getInboundQuoteDTO().getContext().getRiskType().equals(EnumRiskType.BUS_TRAILER))
		{
			response = "988";
			sectorCode = "000004";
		}
		if(inb.getInboundQuoteDTO().getContext().getRiskType().equals(EnumRiskType.TRUCK_UPTO_60000KG))
		{
			response = "09";
			sectorCode = "000006";
		}
		if(inb.getInboundQuoteDTO().getContext().getRiskType().equals(EnumRiskType.TRUCK_MORE_THAN_60000KG))
		{
			response = "10";
			sectorCode = "000006";
		}
		if(inb.getInboundQuoteDTO().getContext().getRiskType().equals(EnumRiskType.CAMPER))
		{
			response = "11";
			sectorCode = "000006";
		}
		if(inb.getInboundQuoteDTO().getContext().getRiskType().equals(EnumRiskType.MOTORCYCLE_FREIGHT_TRANSPORT))
		{
			response = "995";
			sectorCode = "000006";
		}
		if(inb.getInboundQuoteDTO().getContext().getRiskType().equals(EnumRiskType.SPECIAL_VEHICLE))
		{
			response = "980";
			sectorCode = "000007";
		}
		if(inb.getInboundQuoteDTO().getContext().getRiskType().equals(EnumRiskType.AGRICULTURAL_MACHINERY))
		{
			response = "19";
			sectorCode = "000008";
		}
		if(inb.getInboundQuoteDTO().getContext().getRiskType().equals(EnumRiskType.AGRICULTURAL_MACHINERY_TRAILER))
		{
			response = "76";
			sectorCode = "000008";
		}
		if(inb.getInboundQuoteDTO().getContext().getRiskType().equals(EnumRiskType.SPECIAL_VEHICLE_TRAILER))
		{
			response = "22";
			sectorCode = "000007";
		}
		
		logger.debug("getRiskTypeAsset with output : "+response+" and sectorCode : "+sectorCode);
		return response;
	}
	
	/**
	 * Metodo custom di quoteDtoToUnitInst
	 * @param inb
	 * @return
	 */
	public List<WsClause> quoteToClause(InboundRequestHttpJSON inb)
	{
		ArrayList<WsClause> listClause = new ArrayList<WsClause>();
		WsClause cla = new WsClause();
		typeB =  new TypeBooleano();
//		xx11 rimappa e togli da parsingout
//		typeB.setBoolean(inb.isSelectedClause());
		
//		cla.setCode(inb.getCodeClause());
		cla.setSelected(typeB);
		
		listClause.add(cla);
		
		return listClause;
	}
	
	/**
	 * Metodo custom di quoteDtoToUnitInst
	 * @param quote
	 * @return
	 */
	public List<WsFactor> quoteToUnitFactor(Quote quote)
	{
		ArrayList<WsFactor> factorUnit = new ArrayList<WsFactor>();
		WsFactor wsFactor = new WsFactor();
		
		List<ICoverage> listCoverage = quote.getCoverages();
		for (ICoverage coverageTemp : listCoverage)
		{
			if( coverageTemp.getLimit() != null &&
					coverageTemp.getLimit().getCode() != null)
			{
				wsFactor = new WsFactor();
				wsFactor.setCode(ENUMInternalUnitInstanceFactors.FACTOR_3CRLMT.value());
				wsFactor.setValue(coverageTemp.getLimit().getCode());
				factorUnit.add(wsFactor);
			}
			
			if( coverageTemp.getDeductible() != null &&
					coverageTemp.getDeductible().getCode() != null)
			{
				wsFactor = new WsFactor();
				wsFactor.setCode(ENUMInternalUnitInstanceFactors.FACTOR_3CRDED.value());
				wsFactor.setValue(coverageTemp.getDeductible().getCode());
				factorUnit.add(wsFactor);
			}
			
		}
		
		factorUnit.add(wsFactor);
		
		return factorUnit;
	
	}
	
//	Types mappings 
	
	public TypeBooleano boolToTypeBool(Boolean bool)
	{
		typeB = new TypeBooleano();
		typeB.setBoolean(bool);
		
		return typeB;
	}
	
	public TypeData dataToTypeData(Date data)
	{
		
		TypeData dataOpenTypeData  = new TypeData(); 
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(data); // dopo ottobre
		XMLGregorianCalendar dataOpen = null;
		try
		{
			dataOpen = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		}
		catch (DatatypeConfigurationException e)
		{
			logger.error("Error conversion for data : "+data+" ");
		}
		dataOpenTypeData.setData(dataOpen);
		
		return dataOpenTypeData;
	
	}


}
