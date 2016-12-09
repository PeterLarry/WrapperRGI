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

//import javax.inject.Qualifier;
import org.springframework.stereotype.Service;

import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.ICoverage;
import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.IFigure;
import com.mapfre.engines.rating.common.enums.EnumFlowType;
import com.mapfre.engines.rating.common.enums.EnumPersonType;
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

import it.cg.main.dto.InboundRequestHttpJSON;
import it.cg.main.dto.main.Quote;
import it.cg.main.integration.mapper.enumerations.ENUMInternalAssetInstanceFactors;
import it.cg.main.integration.mapper.enumerations.ENUMInternalCodeProduct;
import it.cg.main.integration.mapper.enumerations.ENUMInternalUnitInstanceFactors;
import it.cg.main.integration.mapper.enumerations.ENUMInternalWsProductFactors;

@Service
public class ExternalCustomMapper
{
	private TypeBooleano typeB =  new TypeBooleano();
	//TODO DA verificare pattern
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
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
				
				wsFactor = new WsFactor();
				wsFactor.setCode(ENUMInternalWsProductFactors.FACTOR__1CTPF.value());
				if(figureTemp.getPersonType().equals(EnumPersonType.NATURAL_PERSON))
				{
					wsFactor.setValue("true");
				}
				else if(figureTemp.getPersonType().equals(EnumPersonType.LEGAL_PERSON))
				{
					wsFactor.setValue("false");
				}
				listFactor.add(wsFactor);
				
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
				wsFactor.setCode(ENUMInternalWsProductFactors.FACTOR_1PHPR.value());
				wsFactor.setValue(figureTemp.getOccupation().getWrapperCode().toString());
				listFactor.add(wsFactor);

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
//	TODO aggiungi directLineSelfservice, confronta con platform
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

		//		Operation code
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
		
		if(quote.getContext().getRiskType() != null && quote.getContext().getProductType() != null)
		{
			if(quote.getContext().getRiskType().equals(EnumRiskType.CAR) &&
					quote.getContext().getProductType().equals(EnumProductType.DLI))
			{
				prod.setCode(quote.getContext().getRiskType().getWrapperCode().toString());
			}
			else if(  quote.getContext().getProductType().equals(EnumProductType.DLI) &&
					  		(quote.getContext().getRiskType().equals(EnumRiskType.MOTORBIKE) ||
					  		quote.getContext().getRiskType().equals(EnumRiskType.MOPED))  )
			{
				prod.setCode(ENUMInternalCodeProduct.CODE_MOTOCICLODLI.value());
			}
			else if(quote.getContext().getRiskType().equals(EnumRiskType.URBAN_BUS) &&
						quote.getContext().getProductType().equals(EnumProductType.DLI))//cambiare la stringa
			{
				prod.setCode(ENUMInternalCodeProduct.CODE_AUTODLSS.value());
			}
			else if ( (quote.getContext().getProductType().equals(EnumProductType.ADI))&&
					   		quote.getContext().getRiskType().equals(EnumRiskType.CAR) )
			{
				prod.setCode(ENUMInternalCodeProduct.CODE_AUTOADI.value());
			}
			else if ( ( !quote.getContext().getRiskType().equals(EnumRiskType.CAR) &&
							  !quote.getContext().getRiskType().equals(EnumRiskType.MOTORBIKE) &&
							  !quote.getContext().getRiskType().equals(EnumRiskType.MOPED) ) &&
							    quote.getContext().getProductType().equals(EnumProductType.DLI)  )
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

		if(quote.getEffectiveDate() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalWsProductFactors.FACTOR__1PEFF.value());
			wsFactor.setValue(dateFormat.format(quote.getEffectiveDate()));	
			factProp.add(wsFactor);
		}

		if(quote.getRenewalYears() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalWsProductFactors.FACTOR_1ANRIN.value());
			wsFactor.setValue(quote.getRenewalYears().toString());
			factProp.add(wsFactor);
		}
		
		// Aggiunto fattore  mamarino
		if(quote.getPreviousRenewalYears() != null )
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalWsProductFactors.FACTOR_1FRRIN.value());
			wsFactor.setValue(quote.getPreviousRenewalYears().toString());
			factProp.add(wsFactor);
		}
		// Aggiunto fattore  mamarino
		if(quote.getCampaign() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalWsProductFactors.FACTOR_1CSC.value());
			
			//wsFactor.setValue(figureTemp.getOccupation().getWrapperCode().toString());
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
				if(figureTemp.getBirthDate() != null)
				{
					wsFactor = new WsFactor();
					wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR__2CNAS.value());
					wsFactor.setValue(dateFormat.format(figureTemp.getBirthDate()));
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
				
				wsFactor = new WsFactor();
				wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR__2CTPF.value());
				if(figureTemp.getPersonType().equals(EnumPersonType.NATURAL_PERSON))
				{
					wsFactor.setValue("true");
				}
				else if(figureTemp.getPersonType().equals(EnumPersonType.LEGAL_PERSON))
				{
					wsFactor.setValue("false");
				}
				listFactor.add(wsFactor);
				
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
				if(figureTemp.getBirthDate() != null)
				{
					wsFactor = new WsFactor();
					wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR__2PNAS.value());
					wsFactor.setValue(dateFormat.format(figureTemp.getBirthDate()));
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
				
				wsFactor = new WsFactor();
				wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR__2PTPF.value());
				if(figureTemp.getPersonType().equals(EnumPersonType.NATURAL_PERSON))
				{
					wsFactor.setValue("true");
				}
				else if(figureTemp.getPersonType().equals(EnumPersonType.LEGAL_PERSON))
				{
					wsFactor.setValue("false");
				}
				listFactor.add(wsFactor);
				
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
				wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2OWPR.value());
				wsFactor.setValue(figureTemp.getOccupation().getWrapperCode().toString());
				listFactor.add(wsFactor);
				
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
	 * Metodo custon di quoteDtoToAsset
	 * @param quote
	 * @return
	 */
	public List<WsAssetInstance> quoteToWsAsset(Quote quote)
	{
		List<WsAssetInstance> assetInst = new ArrayList<WsAssetInstance>();
		WsAssetInstance aInstance = new WsAssetInstance();
		assetInst.add(aInstance);
		
		List<WsFactor> factAsset= new ArrayList<WsFactor>();
		WsFactor wsFactor = new WsFactor();
		
		if(quote.getNumberOfClaimsInLastYear() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_SXTOT.value());
			wsFactor.setValue(quote.getNumberOfClaimsInLastYear().toString());
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
				
		if(quote.getGoodDriverClass() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR__2BM.value());
			wsFactor.setValue(quote.getGoodDriverClass());// diventa un enum 
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
		
		// // Aggiunto fattore  mamarino -> verificare fattori nell'else
		if (!quote.getContext().getRiskType().equals(EnumRiskType.CAR) ||
		    !quote.getContext().getRiskType().equals(EnumRiskType.MOTORBIKE) ||
		    !quote.getContext().getRiskType().equals(EnumRiskType.MOPED))
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR__2CF.value());
			wsFactor.setValue(quote.getOtherVehicle().getTaxableHorsePower().toString());
			factAsset.add(wsFactor);

			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR__2CC.value());
			wsFactor.setValue(quote.getOtherVehicle().getCcCapacity().toString());
			factAsset.add(wsFactor);
			
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2NUMAB.value());
			wsFactor.setValue(quote.getOtherVehicle().getNumberOfCitizens().toString());
			factAsset.add(wsFactor);
			
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_3MASSA.value());
			wsFactor.setValue(quote.getOtherVehicle().getWeight().toString());
			factAsset.add(wsFactor);
			
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_2SEATS.value());
			wsFactor.setValue(quote.getOtherVehicle().getNumberOfSeats().toString());
			factAsset.add(wsFactor);
			
			if(quote.getOtherVehicle().getOwnBehalf() != null)
			{
				wsFactor = new WsFactor();
				wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR_3CONTO.value());
				if(quote.getOtherVehicle().getOwnBehalf() == true) {
					wsFactor.setValue("1");
					factAsset.add(wsFactor);
				}else{
					wsFactor.setValue("2");
					factAsset.add(wsFactor);
				}
			
			}
		}else{			
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR__2CF.value());
			wsFactor.setValue(quote.getVehicle().getTechnicalData().getTaxableHorsePower().toString());
			factAsset.add(wsFactor);
			
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR__2CC.value());
			wsFactor.setValue(quote.getVehicle().getTechnicalData().getCcCapacity().toString());
			factAsset.add(wsFactor);
		}
		
		
		
		// // Aggiunto fattore  mamarino ->  _2alim
		if(quote.getVehicle().getTechnicalData().getAlimentation() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(ENUMInternalAssetInstanceFactors.FACTOR__2ALIM.value());
			wsFactor.setValue(quote.getVehicle().getTechnicalData().getAlimentation().toString());
			factAsset.add(wsFactor);
		}
		
		

		aInstance.getFactors().addAll(factAsset);
		List<WsFactor> listFactorFigures =  getFactorForFigureFromRoleAssetInstance(quote.getFigures());
		aInstance.getFactors().addAll(listFactorFigures);
				
		return assetInst;
	}
	
	/**
	 * Metodo custom di quoteDtoToAsset
	 * @param inb
	 * @return
	 */
	public List<WsVehicle> quoteToWsVehicle(InboundRequestHttpJSON inb)
	{
		ArrayList<WsVehicle> ve = new ArrayList<WsVehicle>();
		WsVehicle wsVe = new WsVehicle();

		String sectorCode = "" ;
		String ccode = getRiskTypeAsset(inb, sectorCode);
		
		wsVe.setClassCode(ccode);
		wsVe.setSectorCode(sectorCode);
		
		String useCode = "";
		if(inb.getInboundQuoteDTO().getVehicle().getTechnicalData().getPraUse().equals(EnumPublicRegisterUse.MIXED))
		{
			useCode = "000007";
		}
		else if(inb.getInboundQuoteDTO().getVehicle().getTechnicalData().getPraUse().equals(EnumPublicRegisterUse.PRIVATE_CAR))
		{
			useCode = "000005";
		}
		wsVe.setUseCode(useCode);
		
		ve.add(wsVe);
		
		return ve;
	}
	
	/**
	 * For level WsAsset, calculate of risk type
	 * @param inb
	 * @return
	 */
	private String getRiskTypeAsset(InboundRequestHttpJSON inb, String sectorCode)
	{
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
		
		return response;
	}
	
	/**
	 * Metodo custom di quoteDtoToAssetSection
	 * @param inb
	 * @return
	 */
//	public List<WsAssetUnit> quoteToAssetUnit(InboundRequestHttpJSON inb)
//	{
//		ArrayList<WsAssetUnit> listAssetUnit = new ArrayList<WsAssetUnit>();
//		WsAssetUnit assetUnit = new WsAssetUnit();
//		typeB = new TypeBooleano();
//		
//		typeB.setBoolean(inb.isSelectionAssetUnit());
//		assetUnit.setCode(inb.getCodeAssetUnit());
//		assetUnit.setSelection(typeB);
//
//		listAssetUnit.add(assetUnit);
//		
//		return listAssetUnit;
//	
//	}
	
	/**
	 * Metodo custom di quoteDtoToUnitInst
	 * @param inb
	 * @return
	 */
	public List<WsClause> quoteToClause(InboundRequestHttpJSON inb)
	{
		ArrayList<WsClause> listClause = new ArrayList<WsClause>();
		WsClause cla = new WsClause();
		TypeBooleano typeB =  new TypeBooleano();
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
			
			if(coverageTemp.getLimit().getCode() != null)
			{
				wsFactor = new WsFactor();
				wsFactor.setCode(ENUMInternalUnitInstanceFactors.FACTOR_3CRLMT.value());
				wsFactor.setValue(coverageTemp.getLimit().getCode());
				factorUnit.add(wsFactor);
			}
			
			if(coverageTemp.getDeductible().getCode() != null)
			{
				wsFactor = new WsFactor();
				wsFactor.setCode(ENUMInternalUnitInstanceFactors.FACTOR_3CRDED.value());
				wsFactor.setValue(coverageTemp.getDeductible().getCode());
				factorUnit.add(wsFactor);
			}
			
			if(quote.getClean1() != null)
			{
				wsFactor = new WsFactor();
				wsFactor.setCode(ENUMInternalUnitInstanceFactors.FACTOR_3CLIN1.value());
				wsFactor.setValue(quote.getClean1().toString());
				factorUnit.add(wsFactor);
			}
			
			if(coverageTemp.getLimit().getCode() != null)
			{
				wsFactor = new WsFactor();
				wsFactor.setCode(ENUMInternalUnitInstanceFactors.FACTOR_3CRLMT.value());
				wsFactor.setValue(coverageTemp.getLimit().getCode());
				factorUnit.add(wsFactor);
			}
			
			if(coverageTemp.getDeductible().getCode() != null)
			{
				wsFactor = new WsFactor();
				wsFactor.setCode(ENUMInternalUnitInstanceFactors.FACTOR_3CRDED.value());
				wsFactor.setValue(coverageTemp.getDeductible().getCode());
				factorUnit.add(wsFactor);
			}
			
			if(quote.getClean1() != null)
			{
				wsFactor = new WsFactor();
				wsFactor.setCode(ENUMInternalUnitInstanceFactors.FACTOR_3CLIN1.value());
				wsFactor.setValue(quote.getClean1().toString());
				factorUnit.add(wsFactor);
			}
		}
		
		factorUnit.add(wsFactor);
		
		return factorUnit;
	
	}
	
//	Types mappings 
	
	public TypeBooleano boolToTypeBool(Boolean bool)
	{
		TypeBooleano typeB = new TypeBooleano();
		typeB.setBoolean(bool);
		
		return typeB;
	}
	
	public TypeData dataToTypeData(Date data)
	{
		
		TypeData dataOpenTypeData  = new TypeData(); 
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(data); // dopo ottobre
		XMLGregorianCalendar dataOpen = null;
		try {
			dataOpen = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dataOpenTypeData.setData(dataOpen);
		
		return dataOpenTypeData;
	
	}


}
