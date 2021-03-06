package it.cg.main.process.mapping.easyway;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.ICoverage;
import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.IFigure;
import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.IOtherVehicle;
import com.mapfre.engines.rating.common.enums.EnumCoverageCode;
import com.mapfre.engines.rating.common.enums.EnumRiskType;
import com.pass.global.TypeBooleano;
import com.pass.global.TypeData;
import com.pass.global.WsAssetInstance;
import com.pass.global.WsAssetSection;
import com.pass.global.WsAssetUnit;
import com.pass.global.WsClause;
import com.pass.global.WsFactor;
import com.pass.global.WsUnitInstance;

import it.cg.main.dto.InboundRequestHttpJSON;
import it.cg.main.integration.mapper.enumerations.ENUMInternalCodeAssetUnit;
import it.cg.main.integration.mapper.enumerations.ENUMInternalCodeSection;
import it.cg.main.integration.mapper.enumerations.ENUMInternalUnitInstanceFactors;
import it.cg.main.process.mapping.utilities.MapperUtilityToPASS;

public class MapperAssetSectionToPASS
{
	private Logger logger = Logger.getLogger(getClass());

	private TypeBooleano tybT = new TypeBooleano();
	private TypeBooleano tybF = new TypeBooleano();
	private boolean isEnableTariffFormulaLogActive;
	private IFigure figureOwner;
	private Integer numberOfYoungDriver;
	private MapperUtilityToPASS utilityToPass = new MapperUtilityToPASS();
	private boolean otherVehicle;
	private String otherProvince;
	
	private Date rateFromDate;

	/**
	 * Return a list of asset section -> asset unit -> unit instance.
	 * @param inbJsonRequest
	 * @return
	 */
	public void getAssetSections(InboundRequestHttpJSON inbJsonRequest, WsAssetInstance assetInReqest, String codeAssetUnitReqest,
										  IFigure figureOwnerRequest) throws NullPointerException
	{
		logger.info("into getAssetSections with > inbJsonRequest: "+inbJsonRequest+
				" assetInReqest:"+assetInReqest+" codeAssetUnitReqest:"+codeAssetUnitReqest+
				" figureOwnerRequest:"+figureOwnerRequest);
		
		otherVehicle = utilityToPass.isOtherVehicleEmpty(inbJsonRequest.getInboundQuoteDTO());
		if(!otherVehicle){
			otherProvince = inbJsonRequest.getInboundQuoteDTO().getOtherVehicle().getProvince();
		}
		EnumRiskType riskType = inbJsonRequest.getInboundQuoteDTO().getContext().getRiskType();
		logger.debug("getAssetSections init riskType="+riskType);
		List<ICoverage> listCov = inbJsonRequest.getInboundQuoteDTO().getCoverages();
		logger.debug("getAssetSections init "+listCov.size()+" listCov (coverages)");
		this.rateFromDate = inbJsonRequest.getInboundQuoteDTO().getRateFromDate();
		logger.debug("getAssetSections set rateFromDate="+rateFromDate);
		
		this.tybT.setBoolean(true);
		this.tybF.setBoolean(false);
		this.isEnableTariffFormulaLogActive = inbJsonRequest.getInboundQuoteDTO().getEnableDebugging() == null ? 
														false : inbJsonRequest.getInboundQuoteDTO().getEnableDebugging();
		logger.debug("getAssetSections init isEnableTariffFormulaLogActive="+isEnableTariffFormulaLogActive);
		this.figureOwner = figureOwnerRequest;
		logger.debug("getAssetSections init figureOwner :"+figureOwner);
		this.numberOfYoungDriver = inbJsonRequest.getInboundQuoteDTO().getNumberOfYoungDriver();
		
		logger.debug("getAssetSections begin S1");
		WsAssetSection assetSectionSx = getS1( listCov, riskType, inbJsonRequest.getInboundQuoteDTO().getOtherVehicle() );
		if(assetSectionSx != null)
			assetInReqest.getSections().add(assetSectionSx);
		logger.debug("getAssetSections end S1 is Empty ?"+assetSectionSx == null);
		
		logger.debug("getAssetSections begin S2");
		assetSectionSx = new WsAssetSection();
		assetSectionSx = getS2(listCov, riskType);
		if(assetSectionSx != null)
			assetInReqest.getSections().add(assetSectionSx);
		logger.debug("getAssetSections end S2 is Empty ?"+assetSectionSx == null);
		
		logger.debug("getAssetSections begin S3");
		assetSectionSx = new WsAssetSection();
		assetSectionSx = getS3(listCov, riskType);
		if(assetSectionSx != null)
			assetInReqest.getSections().add(assetSectionSx);
		logger.debug("getAssetSections end S3 is Empty ?"+assetSectionSx == null);
		
		logger.debug("getAssetSections begin S4");
		assetSectionSx = new WsAssetSection();
		assetSectionSx = getS4(listCov, riskType);
		if(assetSectionSx != null)
			assetInReqest.getSections().add(assetSectionSx);
		logger.debug("getAssetSections end S4 is Empty ?"+assetSectionSx == null);
		
		logger.debug("getAssetSections begin S5");
		assetSectionSx = new WsAssetSection();
		assetSectionSx = getS5(listCov, riskType);
		if(assetSectionSx != null)
			assetInReqest.getSections().add(assetSectionSx);
		logger.debug("getAssetSections end S5 is Empty ?"+assetSectionSx == null);
		
		logger.debug("getAssetSections begin S6");
		assetSectionSx = new WsAssetSection();
		assetSectionSx = getS6(listCov, riskType);
		if(assetSectionSx != null)
			assetInReqest.getSections().add(assetSectionSx);
		logger.debug("getAssetSections end S6 is Empty ?"+assetSectionSx == null);
		
	}
	
	/**
	 * Init new UnitInstace with  isEnableTariffFormulaLogActive for test mode
	 * and exceptionCode from figure type Owner (province)
	 * @param 
	 * @return new WsUnitInstance
	 */
	private WsUnitInstance getUnitInstanceInit()
	{
		logger.info("into getUnitInstanceInit, Init wsUnitInstance");
		
		WsUnitInstance unitInstance = new WsUnitInstance();
//		EnableTariffFormulalog
		if(isEnableTariffFormulaLogActive)
			unitInstance.setEnableTariffFormulaLog(this.tybT);
		else
			unitInstance.setEnableTariffFormulaLog(this.tybF);
		logger.debug("getUnitInstanceInit wsUnitInstance.EnableTariffFormulaLog="+isEnableTariffFormulaLogActive);
//		ExceptionCode
		
		if(!otherVehicle)
		{
			unitInstance.setExceptionCode(otherProvince);
			logger.debug("getUnitInstanceInit otherVehicle.Province="+
					otherProvince+" --> wsUnitInstance.ExceptionCode="+unitInstance.getExceptionCode());
		}
		else 
		if(figureOwner != null && figureOwner.getResidenceAddress() != null)
		{
			unitInstance.setExceptionCode(figureOwner.getResidenceAddress().getProvince());
			logger.debug("getUnitInstanceInit Owner.ResidenceAddress.Province="+
								figureOwner.getResidenceAddress().getProvince()+" --> wsUnitInstance.ExceptionCode="+unitInstance.getExceptionCode());
		}
		
		logger.info("out getUnitInstanceInit with response : "+unitInstance);
		return unitInstance;
	}
	
	/**
	 * Init the wsAssetUnit
	 * @return
	 */
	private WsAssetUnit getAssetUnitInit()
	{
		logger.debug("into getAssetUnitInit, Init WsAssetUnit");
		
		WsAssetUnit assetUnitResponse = new WsAssetUnit();
		TypeData data = this.utilityToPass.dataToTypeData(this.rateFromDate);
		assetUnitResponse.setTariffDate(data);
		logger.debug("getAssetUnitInit setted wsAssetUnit.rateFromDate="+rateFromDate+" --> tariffDate="+data);
		
		logger.debug("out getAssetUnitInit");
		return assetUnitResponse;
	}
	
	/**
	 * WsClause with code RCA001 and value true/false by numberOfYoungDriver
	 * @return wsClause
	 */
	private WsClause getClauseRCA()
	{
		logger.info("into getClauseRCA");
//		Clauses
		WsClause clauseUnitInstance = new WsClause();
		clauseUnitInstance.setCode("RCA001");
		if(this.numberOfYoungDriver != null && this.numberOfYoungDriver > 0)
			clauseUnitInstance.setSelected(tybT);
		else
			clauseUnitInstance.setSelected(tybF);
		
		logger.debug("getClauseRCA Add clause "+clauseUnitInstance.getCode()+" field selected="+clauseUnitInstance.getSelected());
		
		logger.info("out getClauseRCA with response : "+clauseUnitInstance);
		return clauseUnitInstance;
	}
	
	/**
	 * Set the unitinstance factors for all the asset unit <br>
	 * Edit the initinstance <br>
	 * Set the unitInstance name for output with the Enum of coverageCode <br>
	 * @param unitInstanceToEdit
	 * @param coverage
	 * @param EnumCoverageCode
	 */
	private void getFactorsForUnitInstanceGeneric(WsUnitInstance unitInstanceToEdit, ICoverage coverage, EnumCoverageCode coverageCode )
	{
		logger.debug("into getFactorsForUnitInstanceGeneric begin factors for unitinstance generic");
//		getFactorsForUnitInstanceGeneric FiddleFactor=1 --> 1FIDRC=1.0
		if(coverageCode != null)
		{
			unitInstanceToEdit.setName(coverageCode.getCode());
			logger.debug("getFactorsForUnitInstanceGeneric Setting of coverage code into unitinstance name : " + coverageCode.getCode());
		}
		WsFactor factorToAdd = new WsFactor();
//		fattori per tutte le unit instances
		if(coverage.getFiddleFactor() != null)
		{
			factorToAdd.setCode(ENUMInternalUnitInstanceFactors.FACTOR_1FIDRC.value());
			factorToAdd.setValue(coverage.getFiddleFactor().toString());
			logger.debug("getFactorsForUnitInstanceGeneric add factor : " + "coverage.getFiddleFactor()" + " = " +coverage.getFiddleFactor()+" --> "+factorToAdd.getCode()+" = "+factorToAdd.getValue());
			unitInstanceToEdit.getFactors().add(factorToAdd);
		}
		if(coverage.getDiscount() != null)
		{
			factorToAdd = new WsFactor();
			factorToAdd.setCode(ENUMInternalUnitInstanceFactors.FACTOR_3ADJ.value());
			factorToAdd.setValue(coverage.getDiscount().toString());
			logger.debug("getFactorsForUnitInstanceGeneric add factor : " + "coverage.getDiscount()" + " = " +coverage.getDiscount()+" --> "+factorToAdd.getCode()+" = "+factorToAdd.getValue());
			unitInstanceToEdit.getFactors().add(factorToAdd);
		}
		if(coverage.getQuickAndDirty() != null)
		{
			factorToAdd = new WsFactor();
			factorToAdd.setCode(ENUMInternalUnitInstanceFactors.FACTOR_3QD.value());
			factorToAdd.setValue(coverage.getQuickAndDirty().toString());
			logger.debug("getFactorsForUnitInstanceGeneric add factor : " + "coverage.getQuickAndDirty()" + " = " +coverage.getQuickAndDirty()+" --> "+factorToAdd.getCode()+" = "+factorToAdd.getValue());
			unitInstanceToEdit.getFactors().add(factorToAdd);
		}
		if(coverage.getPreviousNetAmount() != null)
		{
			factorToAdd = new WsFactor();
			factorToAdd.setCode(ENUMInternalUnitInstanceFactors.FACTOR_FRRCA.value());
			factorToAdd.setValue(coverage.getPreviousNetAmount().toString());
			logger.debug("getFactorsForUnitInstanceGeneric add factor : " + "coverage.getPreviousNetAmount()" + " = " +coverage.getPreviousNetAmount()+" --> "+factorToAdd.getCode()+" = "+factorToAdd.getValue());
			unitInstanceToEdit.getFactors().add(factorToAdd);
		}
		if(coverage.getCode() != null )
		{
			if(coverage.getCode().equals(EnumCoverageCode.MOTOR_ROAD_ASSISTANCE_BASE) )
			{
				factorToAdd = new WsFactor();
				factorToAdd.setCode(ENUMInternalUnitInstanceFactors.FACTOR_3TIPAS.value());
				factorToAdd.setValue("1");
				unitInstanceToEdit.getFactors().add(factorToAdd);
				logger.debug("getFactorsForUnitInstanceGeneric coverage BASE o DELUXE = " + coverage.getCode() + " --> "+factorToAdd.getCode()+" = "+factorToAdd.getValue());
			}
			else if(coverage.getCode().equals(EnumCoverageCode.MOTOR_ROAD_ASSISTANCE_DELUXE) )
			{
				factorToAdd = new WsFactor();
				factorToAdd.setCode(ENUMInternalUnitInstanceFactors.FACTOR_3TIPAS.value());
				factorToAdd.setValue("2");
				unitInstanceToEdit.getFactors().add(factorToAdd);
				logger.debug("getFactorsForUnitInstanceGeneric coverage BASE o DELUXE = " + coverage.getCode() + " --> "+factorToAdd.getCode()+" = "+factorToAdd.getValue());
			}
			else if(coverage.getCode().equals(EnumCoverageCode.MOTOR_LEGAL_PROTECTION_BASE) )
			{
				factorToAdd = new WsFactor();
				factorToAdd.setCode(ENUMInternalUnitInstanceFactors.FACTOR_3TIPTL.value());
				factorToAdd.setValue("1");
				unitInstanceToEdit.getFactors().add(factorToAdd);
				logger.debug("getFactorsForUnitInstanceGeneric coverage BASE o DELUXE = " + coverage.getCode() + " --> "+factorToAdd.getCode()+" = "+factorToAdd.getValue());
			}
			else if(coverage.getCode().equals(EnumCoverageCode.MOTOR_LEGAL_PROTECTION_DELUXE) )
			{
				factorToAdd = new WsFactor();
				factorToAdd.setCode(ENUMInternalUnitInstanceFactors.FACTOR_3TIPTL.value());
				factorToAdd.setValue("2");
				unitInstanceToEdit.getFactors().add(factorToAdd);
				logger.debug("getFactorsForUnitInstanceGeneric coverage BASE o DELUXE = " + coverage.getCode() + " --> "+factorToAdd.getCode()+" = "+factorToAdd.getValue());
			}
		}
		
		logger.debug("out getFactorsForUnitInstanceGeneric");
	}

	/**
	 * Set the unitinstance factors only with riskType for AssetUnit
	 * @param unitInstanceToEdit
	 * @param coverage
	 * @param codeAssetUnit
	 */
	private void getFactorsForUnitInstanceNotS1S5(WsUnitInstance unitInstanceToEdit, ICoverage coverage, String codeAssetUnit, EnumRiskType riskType)
	{
		EnumCoverageCode coverageCode = coverage.getCode();
		if(coverageCode != null)
		{
			logger.debug("(getFactorsForUnitInstanceNotS1S5)Setting of coverage code into unitinstance  name : " + coverageCode.getCode());
			unitInstanceToEdit.setName(coverageCode.getCode());
		}
		WsFactor factorToAdd = new WsFactor();
		
		if(codeAssetUnit.equals(ENUMInternalCodeAssetUnit.CODE_AV1.value()) && EnumRiskType.CAR.equals(riskType))
		{
			if(coverage.getCoinsurance() != null && coverage.getCoinsurance().getCode() != null)
			{
				factorToAdd  = new WsFactor();
				factorToAdd.setCode(ENUMInternalUnitInstanceFactors.FACTOR_3FRATV.value());
				String valueCoinsCode = "";
				switch (coverage.getCoinsurance().getCode())
				{
					case "3":
						valueCoinsCode= "1";
						break;
					case "5":
						valueCoinsCode= "2";
						break;
					case "6":
						valueCoinsCode= "3";
						break;
					default:
						break;
				}
				factorToAdd.setValue(valueCoinsCode);
				logger.debug("getFactorsForUnitInstanceNotS1S5 coverage="+coverageCode+" factor : " + "coverage.getCoinsurance()" + " = " +coverage.getCoinsurance().getCode()+" --> "+factorToAdd.getCode()+" = "+factorToAdd.getValue());
				
				unitInstanceToEdit.getFactors().add(factorToAdd);
			}
		}
		if(codeAssetUnit.equals(ENUMInternalCodeAssetUnit.CODE_COLL1.value()) && EnumRiskType.CAR.equals(riskType))
		{
			if(coverage.getCoinsurance() != null && coverage.getCoinsurance().getCode() != null)
			{
				factorToAdd  = new WsFactor();
				factorToAdd.setCode(ENUMInternalUnitInstanceFactors.FACTOR_3FRACO.value());
				String valueCoinsCode = "";
				switch (coverage.getCoinsurance().getCode())
				{
					case "3":
						valueCoinsCode= "1";
						break;
					case "5":
						valueCoinsCode= "2";
						break;
					case "6":
						valueCoinsCode= "3";
						break;
					case "7":
						valueCoinsCode= "4";
						break;
					default:
						break;
				}
				factorToAdd.setValue(valueCoinsCode);
				logger.debug("getFactorsForUnitInstanceNotS1S5 coverage="+coverageCode+" factor : " + "coverage.getCoinsurance()" + " = " +coverage.getCoinsurance().getCode()+" --> "+factorToAdd.getCode()+" = "+factorToAdd.getValue());
				
				unitInstanceToEdit.getFactors().add(factorToAdd);
			}
		}
		if(codeAssetUnit.equals(ENUMInternalCodeAssetUnit.CODE_CRI1.value()) && EnumRiskType.CAR.equals(riskType))
		{
			if(coverage.getDeductible() != null && coverage.getDeductible().getCode() != null)
			{
				factorToAdd  = new WsFactor();
				factorToAdd.setCode(ENUMInternalUnitInstanceFactors.FACTOR_3CRDED.value());
				factorToAdd.setValue(coverage.getDeductible().getCode());
				
				unitInstanceToEdit.getFactors().add(factorToAdd);
//				logger.debug("getFactorsForUnitInstanceNotS1S5 coverage="+coverageCode+" factor : "+factorToAdd.getCode()+" - value :"+factorToAdd.getValue());
				logger.debug("getFactorsForUnitInstanceNotS1S5 coverage="+coverageCode+" factor : " + "coverage.getDeductible()" + " = " +coverage.getDeductible().getCode()+" --> "+factorToAdd.getCode()+" = "+factorToAdd.getValue());
			}
			if(coverage.getLimit() != null && coverage.getLimit().getCode() != null)
			{
				factorToAdd  = new WsFactor();
				factorToAdd.setCode(ENUMInternalUnitInstanceFactors.FACTOR_3CRLMT.value());
				factorToAdd.setValue(coverage.getLimit().getCode());
				
				unitInstanceToEdit.getFactors().add(factorToAdd);
				logger.debug("getFactorsForUnitInstanceNotS1S5 coverage="+coverageCode+" factor : " + "coverage.getLimit()" + " = " +coverage.getLimit().getCode()+" --> "+factorToAdd.getCode()+" = "+factorToAdd.getValue());
			}
		}
		if(codeAssetUnit.equals(ENUMInternalCodeAssetUnit.CODE_EN1.value()) && EnumRiskType.CAR.equals(riskType))
		{
			if(coverage.getCoinsurance() != null && coverage.getCoinsurance().getCode() != null)
			{
				factorToAdd  = new WsFactor();
				factorToAdd.setCode(ENUMInternalUnitInstanceFactors.FACTOR_3FRAEN.value());
				String valueCoinsCode = "";
				switch (coverage.getCoinsurance().getCode())
				{
					case "2":
						valueCoinsCode= "1";
						break;
					case "3":
						valueCoinsCode= "2";
						break;
					case "5":
						valueCoinsCode= "3";
						break;
					case "6":
						valueCoinsCode= "4";
						break;
					default:
						break;
				}
				factorToAdd.setValue(valueCoinsCode);
				logger.debug("getFactorsForUnitInstanceNotS1S5 coverage="+coverageCode+" factor : " + "coverage.getCoinsurance()" + " = " +coverage.getCoinsurance().getCode()+" --> "+factorToAdd.getCode()+" = "+factorToAdd.getValue());
				
				unitInstanceToEdit.getFactors().add(factorToAdd);
			}
		}
	}

	/**
	 * S6 -> with assetUnit and Unitinstance
	 * @param Coverages[]
	 * @param RiskType
	 * @return AssetSection S6 , if no S6 present return null
	 */
	private WsAssetSection getS6(List<ICoverage> listCov, EnumRiskType riskType)
	{
		WsAssetSection assetSectionResultS6 = new WsAssetSection();
		assetSectionResultS6.setCode(ENUMInternalCodeSection.CODE_S6.value());
		
		for (ICoverage covTemp : listCov)
		{
			WsAssetUnit assetUnitTemp = getAssetUnitInit();
			WsUnitInstance unitInstanceToAdd = getUnitInstanceInit();
			WsFactor unitInstanceFactorToAdd  = new WsFactor();
			EnumCoverageCode coverageCode = covTemp.getCode();
			logger.debug("getS6 add factors generic to "+coverageCode);
			getFactorsForUnitInstanceGeneric(unitInstanceToAdd, covTemp, coverageCode);

			if(coverageCode.equals(EnumCoverageCode.MOTOR_VANDALISM))
			{
//			assetunit MOTOR_VANDALISM
				assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_AV1.value());
				assetUnitTemp.setSelection((covTemp.getSelected() != null && covTemp.getSelected()) ? tybT : tybF);

				getFactorsForUnitInstanceNotS1S5(unitInstanceToAdd, covTemp, ENUMInternalCodeAssetUnit.CODE_AV1.value(), riskType);
//				add Unitinstance tu AssetUnit
				assetUnitTemp.getInstances().add(unitInstanceToAdd);
//				add AssetUnit to AssetSection
				assetSectionResultS6.getUnits().add(assetUnitTemp);
			}
			else if(coverageCode.equals(EnumCoverageCode.MOTOR_COLLISION))
			{
//			assetunit MOTOR_COLLISION
				assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_COLL1.value());
				assetUnitTemp.setSelection((covTemp.getSelected() != null && covTemp.getSelected()) ? tybT : tybF);

				getFactorsForUnitInstanceNotS1S5(unitInstanceToAdd, covTemp, ENUMInternalCodeAssetUnit.CODE_COLL1.value(), riskType);
//				add Unitinstance tu AssetUnit
				assetUnitTemp.getInstances().add(unitInstanceToAdd);
//				add AssetUnit to AssetSection
				assetSectionResultS6.getUnits().add(assetUnitTemp);
			}
			else if(coverageCode.equals(EnumCoverageCode.MOTOR_CRYSTALS))
			{
//			assetunit MOTOR_CRYSTALS
				assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_CRI1.value());
				assetUnitTemp.setSelection((covTemp.getSelected() != null && covTemp.getSelected()) ? tybT : tybF);

				getFactorsForUnitInstanceNotS1S5(unitInstanceToAdd, covTemp, ENUMInternalCodeAssetUnit.CODE_CRI1.value(), riskType);
//				add Unitinstance tu AssetUnit
				assetUnitTemp.getInstances().add(unitInstanceToAdd);
//				add AssetUnit to AssetSection
				assetSectionResultS6.getUnits().add(assetUnitTemp);
			}
			else if(coverageCode.equals(EnumCoverageCode.MOTOR_NATURAL_EVENTS))
			{
//			assetunit MOTOR_NATURAL_EVENTS
				assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_EN1.value());
				assetUnitTemp.setSelection((covTemp.getSelected() != null && covTemp.getSelected()) ? tybT : tybF);

				getFactorsForUnitInstanceNotS1S5(unitInstanceToAdd, covTemp, ENUMInternalCodeAssetUnit.CODE_EN1.value(), riskType);
//				add Unitinstance tu AssetUnit
				assetUnitTemp.getInstances().add(unitInstanceToAdd);
//				add AssetUnit to AssetSection
				assetSectionResultS6.getUnits().add(assetUnitTemp);
			}
			else if(coverageCode.equals(EnumCoverageCode.MOTOR_KASKO))
			{
//			assetunit MOTOR_KASKO
				assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_KAS1.value());
				assetUnitTemp.setSelection((covTemp.getSelected() != null && covTemp.getSelected()) ? tybT : tybF);
				
				getFactorsForUnitInstanceNotS1S5(unitInstanceToAdd, covTemp, ENUMInternalCodeAssetUnit.CODE_KAS1.value(), riskType);

				if(covTemp.getCoinsurance() != null)
				{
					if(covTemp.getCoinsurance().getCode() != null)
					{
						unitInstanceFactorToAdd = new WsFactor();
						
						unitInstanceFactorToAdd.setCode(ENUMInternalUnitInstanceFactors.FACTOR_3FRAKS.value());
						if(riskType.equals(EnumRiskType.CAR))
						{
							String valueCoinsCode = "";
							switch (covTemp.getCoinsurance().getCode())
							{
								case "5":
									valueCoinsCode= "1";
									break;
								case "6":
									valueCoinsCode= "2";
									break;
								case "7":
									valueCoinsCode= "3";
									break;
								default:
									break;
							}
							unitInstanceFactorToAdd.setValue(valueCoinsCode);
							//TODO Controllare
							logger.debug("getS6 coverage="+coverageCode+" factor : " + "coverage.getCoinsurance()" + " = " +covTemp.getCoinsurance().getCode()+" --> "+unitInstanceFactorToAdd.getCode()+" = "+unitInstanceFactorToAdd.getValue());
							unitInstanceToAdd.getFactors().add(unitInstanceFactorToAdd);
						}
					}
				}
				logger.debug("getS6 assetUnit "+assetUnitTemp.getCode()+" is selected ? "+assetUnitTemp.getSelection());
				logger.debug("getS6 coverage="+coverageCode+" factor unitinstance : "+unitInstanceFactorToAdd.getCode()+" - value :"+unitInstanceFactorToAdd.getValue());
//				add Unitinstance tu AssetUnit
				assetUnitTemp.getInstances().add(unitInstanceToAdd);
//				add AssetUnit to AssetSection
				assetSectionResultS6.getUnits().add(assetUnitTemp);
			}
		}
		
		if(assetSectionResultS6.getUnits().isEmpty())
		{
			assetSectionResultS6 = null;
			logger.debug("getS6 S6 is empty");
		}
		
		return assetSectionResultS6;
	}
	
	/**
	 * S5 -> with assetUnit and Unitinstance generic and for some specific asset unit
	 * @param Coverages[]
	 * @param RiskType
	 * @return AssetSection S5 , if no S5 present return null
	 */
	private WsAssetSection getS5(List<ICoverage> listCov, EnumRiskType riskType)
	{
		WsAssetSection assetSectionResultS5 = new WsAssetSection();
		assetSectionResultS5.setCode(ENUMInternalCodeSection.CODE_S5.value());
		
		for (ICoverage covTemp : listCov)
		{
			EnumCoverageCode coverageCode = covTemp.getCode();
			
			if( (coverageCode.equals(EnumCoverageCode.MOTOR_LEGAL_PROTECTION_BASE) ||
					coverageCode.equals(EnumCoverageCode.MOTOR_LEGAL_PROTECTION_DELUXE)) &&
						(riskType.equals(EnumRiskType.CAR) || riskType.equals(EnumRiskType.MOTORBIKE)) )
			{
				logger.debug("getS5 begin create"+coverageCode);
				logger.debug("getS5 riskType="+riskType);
				
				WsAssetUnit assetUnitTemp = getAssetUnitInit();
				WsUnitInstance unitInstanceToAdd = getUnitInstanceInit();
				logger.debug("getS5 add factors generic to "+coverageCode);
				getFactorsForUnitInstanceGeneric(unitInstanceToAdd, covTemp, coverageCode);
				
				assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_TG1.value());
				assetUnitTemp.setSelection((covTemp.getSelected() != null && covTemp.getSelected()) ? tybT : tybF);
//				add Unitinstance tu AssetUnit
				assetUnitTemp.getInstances().add(unitInstanceToAdd);
//				add AssetUnit to AssetSection
				assetSectionResultS5.getUnits().add(assetUnitTemp);
				
				logger.debug("getS5 AssetSection "+assetSectionResultS5.getCode());
				logger.debug("getS5 add wsAssetUnit.code"+assetUnitTemp.getCode()+" wsAssetUnit.selected ? "+covTemp.getSelected());
				logger.debug("getS5 with "+unitInstanceToAdd.getFactors().size()+" wsAssetUnit.factors");
				logger.debug("getS5 end creation "+coverageCode);
			}
			else if( (coverageCode.equals(EnumCoverageCode.MOTOR_LEGAL_PROTECTION_BASE) ||
						coverageCode.equals(EnumCoverageCode.MOTOR_LEGAL_PROTECTION_DELUXE)) &&
							riskType.equals(EnumRiskType.MOPED) )
			{
				logger.debug("getS5 begin create"+coverageCode);
				logger.debug("getS5 riskType="+riskType);
				
				WsAssetUnit assetUnitTemp = getAssetUnitInit();
				WsUnitInstance unitInstanceToAdd = getUnitInstanceInit();
				logger.debug("getS5 add factors generic to "+coverageCode);
				getFactorsForUnitInstanceGeneric(unitInstanceToAdd, covTemp, coverageCode);
				
				assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_TG2.value());
				assetUnitTemp.setSelection((covTemp.getSelected() != null && covTemp.getSelected()) ? tybT : tybF);
//				add Unitinstance tu AssetUnit
				assetUnitTemp.getInstances().add(unitInstanceToAdd);
//				add AssetUnit to AssetSection
				assetSectionResultS5.getUnits().add(assetUnitTemp);
				
				logger.debug("getS5 AssetSection "+assetSectionResultS5.getCode());
				logger.debug("getS5 add wsAssetUnit.code"+assetUnitTemp.getCode()+" wsAssetUnit.selected ? "+covTemp.getSelected());
				logger.debug("getS5 with "+unitInstanceToAdd.getFactors().size()+" wsAssetUnit.factors");
				logger.debug("getS5 end creation "+coverageCode);
			}
		}
		
		if(assetSectionResultS5.getUnits().isEmpty())
		{
			assetSectionResultS5 = null;
			logger.debug("getS5 S5 is empty");
		}
		
		return assetSectionResultS5;
	}
	
	/**
	 * S4 -> with assetUnit and Unitinstance
	 * @param Coverages[]
	 * @param RiskType
	 * @return AssetSection S4 , if no S4 present return null
	 */
	private WsAssetSection getS4(List<ICoverage> listCov, EnumRiskType riskType)
	{
		WsAssetSection assetSectionResultS4 = new WsAssetSection();
		assetSectionResultS4.setCode(ENUMInternalCodeSection.CODE_S4.value());
		
		for (ICoverage covTemp : listCov)
		{
			EnumCoverageCode coverageCode = covTemp.getCode();
			
			if( coverageCode.equals(EnumCoverageCode.MOTOR_ROAD_ASSISTANCE_BASE) ||
					coverageCode.equals(EnumCoverageCode.MOTOR_ROAD_ASSISTANCE_DELUXE) )
			{
				logger.debug("getS4 begin create"+coverageCode);
				logger.debug("getS4 riskType="+riskType);
				
				WsAssetUnit assetUnitTemp = getAssetUnitInit();
				WsUnitInstance unitInstanceToAdd = getUnitInstanceInit();
				
				logger.debug("getS4 add factors generic to "+coverageCode);
				getFactorsForUnitInstanceGeneric(unitInstanceToAdd, covTemp, coverageCode);
				
				assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_AS1.value());
				assetUnitTemp.setSelection((covTemp.getSelected() != null && covTemp.getSelected()) ? tybT : tybF);
//				add Unitinstance tu AssetUnit
				assetUnitTemp.getInstances().add(unitInstanceToAdd);
//				add AssetUnit to AssetSection
				assetSectionResultS4.getUnits().add(assetUnitTemp);
				
				logger.debug("getS4 AssetSection "+assetSectionResultS4.getCode());
				logger.debug("getS4 add wsAssetUnit.code"+assetUnitTemp.getCode()+" wsAssetUnit.selected ? "+covTemp.getSelected());
				logger.debug("getS4 with "+unitInstanceToAdd.getFactors().size()+" wsAssetUnit.factors");
				logger.debug("getS4 end creation "+coverageCode);
			}
		}
		
		if(assetSectionResultS4.getUnits().isEmpty())
		{
			assetSectionResultS4 = null;
			logger.debug("getS4 S4 is empty");
		}
		
		return assetSectionResultS4;
	}
	
	
	/**
	 * S3 -> with assetUnit and Unitinstance
	 * @param Coverages[]
	 * @param RiskType
	 * @return AssetSection S3 , if no S3 present return null
	 */
	private WsAssetSection getS3(List<ICoverage> listCov, EnumRiskType riskType)
	{
		WsAssetSection assetSectionResultS3 = new WsAssetSection();
		assetSectionResultS3.setCode(ENUMInternalCodeSection.CODE_S3.value());
		
		for (ICoverage covTemp : listCov)
		{
			EnumCoverageCode coverageCode = covTemp.getCode();
			
			
			if( coverageCode.equals(EnumCoverageCode.MOTOR_PERMANENT_INVALIDITY_DRIVER) )
			{
				logger.debug("getS3 begin create"+coverageCode);
				logger.debug("getS3 riskType="+riskType);
				
				WsAssetUnit assetUnitTemp = getAssetUnitInit();
				WsUnitInstance unitInstanceToAdd = getUnitInstanceInit();
				WsFactor unitInstanceFactorToAdd  = new WsFactor();

				logger.debug("getS3 add factors generic to "+coverageCode);
				getFactorsForUnitInstanceGeneric(unitInstanceToAdd, covTemp, coverageCode);
				unitInstanceFactorToAdd.setCode(ENUMInternalUnitInstanceFactors.FACTOR_3SAINP.value());
				
				assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_IIP1.value());
				assetUnitTemp.setSelection((covTemp.getSelected() != null && covTemp.getSelected()) ? tybT : tybF);
				
				if(covTemp.getLimit() != null && covTemp.getLimit().getCode() != null)
				{
					if(riskType.equals(EnumRiskType.MOTORBIKE))
					{
						logger.debug("getS3 riskType="+riskType);
						
						unitInstanceFactorToAdd.setValue(covTemp.getLimit().getCode());
						logger.debug("getS3 coverage="+coverageCode+" factor : " + "coverage.getLimit()" + " = " +covTemp.getLimit().getCode()+" --> "+unitInstanceFactorToAdd.getCode()+" = "+unitInstanceFactorToAdd.getValue());
						
						unitInstanceToAdd.getFactors().add(unitInstanceFactorToAdd);
						
//						logger.debug("getS3 covTemp.getLimit().getCode()="+covTemp.getLimit().getCode()+
//								"-->"+unitInstanceFactorToAdd.getCode()+"="+unitInstanceFactorToAdd.getValue());
					}
					else if(riskType.equals(EnumRiskType.CAR))
					{
						logger.debug("getS3 riskType="+riskType);
						
						String valueCoinsCode = "";
						switch (covTemp.getLimit().getCode())
						{
							case "2":
								valueCoinsCode= "1";
								break;
							case "3":
								valueCoinsCode= "2";
								break;
							default:
								break;
						}
						unitInstanceFactorToAdd.setValue(valueCoinsCode);
						
						unitInstanceToAdd.getFactors().add(unitInstanceFactorToAdd);
						
						logger.debug("getS3 covTemp.getLimit().getCode()="+covTemp.getLimit().getCode()+
								"-->"+unitInstanceFactorToAdd.getCode()+"="+unitInstanceFactorToAdd.getValue());
					}
				}
//				add Unitinstance tu AssetUnit
				assetUnitTemp.getInstances().add(unitInstanceToAdd);
//				add AssetUnit to AssetSection
				assetSectionResultS3.getUnits().add(assetUnitTemp);
				
				logger.debug("getS3 AssetSection "+assetSectionResultS3.getCode());
				logger.debug("getS3 add wsAssetUnit.code"+assetUnitTemp.getCode()+" wsAssetUnit.selected ? "+covTemp.getSelected());
				logger.debug("getS3 with "+unitInstanceToAdd.getFactors().size()+" wsAssetUnit.factors");
				logger.debug("getS3 end creation "+coverageCode);
			}
			else if( coverageCode.equals(EnumCoverageCode.MOTOR_DEATH_DRIVER) )
			{
				logger.debug("getS3 begin create"+coverageCode);
				
				WsAssetUnit assetUnitTemp = getAssetUnitInit();
				WsUnitInstance unitInstanceToAdd = getUnitInstanceInit();
				WsFactor unitInstanceFactorToAdd  = new WsFactor();

				logger.debug("getS3 add factors generic to "+coverageCode);
				getFactorsForUnitInstanceGeneric(unitInstanceToAdd, covTemp, coverageCode);
				unitInstanceFactorToAdd.setCode(ENUMInternalUnitInstanceFactors.FACTOR_3SAINP.value());
				
				assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_IM1.value());
				assetUnitTemp.setSelection((covTemp.getSelected() != null && covTemp.getSelected()) ? tybT : tybF);
				if(covTemp.getLimit() != null && covTemp.getLimit().getCode() != null)
				{
					if(riskType.equals(EnumRiskType.MOTORBIKE))
					{
						logger.debug("getS3 riskType="+riskType);
						
						unitInstanceFactorToAdd.setValue(covTemp.getLimit().getCode());
						logger.debug("getS3 coverage="+coverageCode+" factor : " + "coverage.getLimit()" + " = " +covTemp.getLimit().getCode()+" --> "+unitInstanceFactorToAdd.getCode()+" = "+unitInstanceFactorToAdd.getValue());
						
						unitInstanceToAdd.getFactors().add(unitInstanceFactorToAdd);
						
//						logger.debug("getS3 covTemp.getLimit().getCode()="+covTemp.getLimit().getCode()+
//										"-->"+unitInstanceFactorToAdd.getCode()+"="+unitInstanceFactorToAdd.getValue());
					}
					else if(riskType.equals(EnumRiskType.CAR))
					{
						logger.debug("getS3 riskType="+riskType);
						
						String valueCoinsCode = "";
						switch (covTemp.getLimit().getCode())
						{
							case "2":
								valueCoinsCode= "1";
								break;
							case "3":
								valueCoinsCode= "2";
								break;
							default:
								break;
						}
						unitInstanceFactorToAdd.setValue(valueCoinsCode);
						
						unitInstanceToAdd.getFactors().add(unitInstanceFactorToAdd);
						
						logger.debug("getS3 covTemp.getLimit().getCode()="+covTemp.getLimit().getCode()+
										" --> "+unitInstanceFactorToAdd.getCode()+"="+unitInstanceFactorToAdd.getValue());
					}
				}
//				add Unitinstance tu AssetUnit
				assetUnitTemp.getInstances().add(unitInstanceToAdd);
//				add AssetUnit to AssetSection
				assetSectionResultS3.getUnits().add(assetUnitTemp);
				
				logger.debug("getS3 AssetSection "+assetSectionResultS3.getCode());
				logger.debug("getS3 add wsAssetUnit.code"+assetUnitTemp.getCode()+" wsAssetUnit.selected ? "+covTemp.getSelected());
				logger.debug("getS3 with "+unitInstanceToAdd.getFactors().size()+" wsAssetUnit.factors");
				logger.debug("getS3 end creation "+coverageCode);
			}
		}
		
		if(assetSectionResultS3.getUnits().isEmpty())
		{
			assetSectionResultS3 = null;
			logger.debug("getS3 S3 is empty");
		}
		
		return assetSectionResultS3;
	}
	
	/**
	 * S2 -> with assetUnit and Unitinstance
	 * @param Coverages[]
	 * @param RiskType
	 * @return AssetSection S2 , if no S2 present return null
	 */
	private WsAssetSection getS2(List<ICoverage> listCov, EnumRiskType riskType)
	{
		WsAssetSection assetSectionResultS2 = new WsAssetSection();
		assetSectionResultS2.setCode(ENUMInternalCodeSection.CODE_S2.value());
		
		for (ICoverage covTemp : listCov)
		{
			EnumCoverageCode coverageCode = covTemp.getCode();
			
			if( coverageCode.equals(EnumCoverageCode.MOTOR_THEFT) )
			{
				logger.debug("getS2 begin create"+coverageCode);
				
				WsAssetUnit assetUnitTemp = getAssetUnitInit();
				WsUnitInstance unitInstanceToAdd = getUnitInstanceInit();
				WsFactor factorsUnitInstanceToAdd  = new WsFactor();
				logger.debug("getS2 add factors generic to "+coverageCode);
				getFactorsForUnitInstanceGeneric(unitInstanceToAdd, covTemp, coverageCode);
				
				assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_FUR1.value());
				assetUnitTemp.setSelection((covTemp.getSelected() != null && covTemp.getSelected()) ? tybT : tybF);
				
				if(covTemp.getCoinsurance() != null && covTemp.getCoinsurance().getCode() != null)
				{
						factorsUnitInstanceToAdd.setCode(ENUMInternalUnitInstanceFactors.FACTOR_3FRSCF.value());
						if(riskType.equals(EnumRiskType.MOTORBIKE))
						{
							logger.debug("getS2 riskType="+riskType);
							
							String valueCoinsCode = "";
							switch (covTemp.getCoinsurance().getCode())
							{
								case "1":
									valueCoinsCode= "4";
									break;
								case "2":
									valueCoinsCode= "9";
									break;
								default:
									break;
							}
							factorsUnitInstanceToAdd.setValue(valueCoinsCode);
							
							unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd);
//							logger.debug("getS2 covTemp.getCoinsurance().getCode()="+covTemp.getCoinsurance().getCode()+
//											"-->"+factorsUnitInstanceToAdd.getCode()+"="+factorsUnitInstanceToAdd.getValue());
							logger.debug("getS2 coverage="+coverageCode+" factor : " + "coverage.getCoinsurance()" + " = " +covTemp.getCoinsurance().getCode()+" --> "+factorsUnitInstanceToAdd.getCode()+" = "+factorsUnitInstanceToAdd.getValue());
						}
						else if(riskType.equals(EnumRiskType.CAR))
						{
							logger.debug("getS2 riskType="+riskType);
							
							factorsUnitInstanceToAdd.setValue(covTemp.getCoinsurance().getCode());
							
							unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd);
//							logger.debug("getS2 covTemp.getCoinsurance().getCode()="+covTemp.getCoinsurance().getCode()+
//									"-->"+factorsUnitInstanceToAdd.getCode()+"="+factorsUnitInstanceToAdd.getValue());
							logger.debug("getS2 coverage="+coverageCode+" factor : " + "coverage.getCoinsurance()" + " = " +covTemp.getCoinsurance().getCode()+" --> "+factorsUnitInstanceToAdd.getCode()+" = "+factorsUnitInstanceToAdd.getValue());
						}
				}
				
//				add Unitinstance tu AssetUnit
				assetUnitTemp.getInstances().add(unitInstanceToAdd);
//				add AssetUnit to AssetSection
				assetSectionResultS2.getUnits().add(assetUnitTemp);
				
				logger.debug("getS2 AssetSection "+assetSectionResultS2.getCode());
				logger.debug("getS2 add wsAssetUnit.code"+assetUnitTemp.getCode()+" wsAssetUnit.selected ? "+covTemp.getSelected());
				logger.debug("getS2 with "+unitInstanceToAdd.getFactors().size()+" wsAssetUnit.factors");
				logger.debug("getS2 end creation "+coverageCode);
			}
			else if( coverageCode.equals(EnumCoverageCode.MOTOR_FIRE) )
			{
				logger.debug("getS2 begin create"+coverageCode);
				
				WsAssetUnit assetUnitTemp = getAssetUnitInit();
				WsUnitInstance unitInstanceToAdd = getUnitInstanceInit();
				WsFactor factorsUnitInstanceToAdd  = new WsFactor();
				logger.debug("getS2 add factors generic to "+coverageCode);
				getFactorsForUnitInstanceGeneric(unitInstanceToAdd, covTemp, coverageCode);
				
				assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_INC1.value());
				assetUnitTemp.setSelection((covTemp.getSelected() != null && covTemp.getSelected()) ? tybT : tybF);
				
				if(covTemp.getCoinsurance() != null && covTemp.getCoinsurance().getCode() != null)
				{
					factorsUnitInstanceToAdd.setCode(ENUMInternalUnitInstanceFactors.FACTOR_3FRSCF.value());
					if(riskType.equals(EnumRiskType.MOTORBIKE))
					{
						logger.debug("getS2 riskType="+riskType);
						
						String valueCoinsCode = "";
						switch (covTemp.getCoinsurance().getCode())
						{
							case "1":
								valueCoinsCode= "4";
								break;
							case "2":
								valueCoinsCode= "9";
								break;
							default:
								break;
						}
						factorsUnitInstanceToAdd.setValue(valueCoinsCode);
						
						unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd);
						
//						logger.debug("getS2 covTemp.getCoinsurance().getCode()="+covTemp.getCoinsurance().getCode()+
//								"-->"+factorsUnitInstanceToAdd.getCode()+"="+factorsUnitInstanceToAdd.getValue());
						logger.debug("getS2 coverage="+coverageCode+" factor : " + "coverage.getCoinsurance()" + " = " +covTemp.getCoinsurance().getCode()+" --> "+factorsUnitInstanceToAdd.getCode()+" = "+factorsUnitInstanceToAdd.getValue());
					}
					else if(riskType.equals(EnumRiskType.CAR))
					{
						logger.debug("getS2 riskType="+riskType);
						
						factorsUnitInstanceToAdd.setValue(covTemp.getCoinsurance().getCode());
						
						unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd);
						
//						logger.debug("getS2 covTemp.getCoinsurance().getCode()="+covTemp.getCoinsurance().getCode()+
//								"-->"+factorsUnitInstanceToAdd.getCode()+"="+factorsUnitInstanceToAdd.getValue());
						logger.debug("getS2 coverage="+coverageCode+" factor : " + "coverage.getCoinsurance()" + " = " +covTemp.getCoinsurance().getCode()+" --> "+factorsUnitInstanceToAdd.getCode()+" = "+factorsUnitInstanceToAdd.getValue());
					}
				}
//				add Unitinstance tu AssetUnit
				assetUnitTemp.getInstances().add(unitInstanceToAdd);
//				add AssetUnit to AssetSection
				assetSectionResultS2.getUnits().add(assetUnitTemp);
				
				logger.debug("getS2 AssetSection "+assetSectionResultS2.getCode());
				logger.debug("getS2 add wsAssetUnit.code"+assetUnitTemp.getCode()+" wsAssetUnit.selected ? "+covTemp.getSelected());
				logger.debug("getS2 with "+unitInstanceToAdd.getFactors().size()+" wsAssetUnit.factors");
				logger.debug("getS2 end creation "+coverageCode);
			}
			
		}
		
		if(assetSectionResultS2.getUnits().isEmpty())
		{
			assetSectionResultS2 = null;
			logger.debug("getS2 S2 is empty");
		}
		
		return assetSectionResultS2;
	}
	
	/**
	 * S1 -> with assetUnit and Unitinstance generic and for some specific asset unit
	 * @param Coverages[]
	 * @param RiskType
	 * @return AssetSection S1 , if no S1 present return null
	 */
	private WsAssetSection getS1(List<ICoverage> listCov, EnumRiskType riskType, IOtherVehicle otherVehicle)
	{
		logger.info("into getS1 with request : listCov="+listCov+
						" riskType="+riskType+" otherVehicle="+otherVehicle);
		
		WsAssetSection assetSectionResultS1 = new WsAssetSection();
		assetSectionResultS1.setCode(ENUMInternalCodeSection.CODE_S1.value());
		
		for (ICoverage covTemp : listCov)
		{
			EnumCoverageCode coverageCode = covTemp.getCode();
			
			if( covTemp != null && coverageCode != null && coverageCode.equals(EnumCoverageCode.MOTOR_RCA))
			{
				logger.debug("getS1 begin create"+coverageCode);
				
				WsAssetUnit assetUnitTemp = getAssetUnitInit();
				WsUnitInstance unitInstanceToAdd = getUnitInstanceInit();
				WsFactor factorsUnitInstanceToAdd3RCFRA  = new WsFactor();
				WsFactor factorsUnitInstanceToAdd3MASS  = new WsFactor();
				
				getFactorsForUnitInstanceGeneric(unitInstanceToAdd, covTemp, coverageCode);
				
//				CODE Factor for Unitinstance 
				factorsUnitInstanceToAdd3RCFRA.setCode(ENUMInternalUnitInstanceFactors.FACTOR_3RCFRA.value());
				factorsUnitInstanceToAdd3MASS.setCode(ENUMInternalUnitInstanceFactors.FACTOR_3MASS.value());
				
				if(riskType.equals(EnumRiskType.CAR) || riskType.equals(EnumRiskType.MOTORBIKE))
				{
					assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_RCA1.value());
//					unitinstance factor Value
					if(covTemp.getDeductible() != null && covTemp.getDeductible().getCode() != null)
					{
						factorsUnitInstanceToAdd3RCFRA.setValue(covTemp.getDeductible().getCode());
						logger.debug("getS1 coverage="+coverageCode+" factor : " + "coverage.getDeductible()" + " = " +covTemp.getDeductible().getCode()+" --> "+factorsUnitInstanceToAdd3RCFRA.getCode()+" = "+factorsUnitInstanceToAdd3RCFRA.getValue());
						unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3RCFRA);
					}
					if(covTemp.getLimit() != null && covTemp.getLimit().getCode() != null)
					{
						if(riskType.equals(EnumRiskType.CAR))
						{
							logger.debug("getS1 riskType="+riskType);
							
							String valueCoinsCode = "";
							switch ( covTemp.getLimit().getCode())
							{
								case "1":
									valueCoinsCode= "1";
									break;
								case "3":
									valueCoinsCode= "2";
									break;
								case "5":
									valueCoinsCode= "3";
									break;
								case "6":
									valueCoinsCode= "4";
									break;
								default:
									break;
							}
							factorsUnitInstanceToAdd3MASS.setValue(valueCoinsCode);
							logger.debug("getS1 coverage="+coverageCode+" factor : " + "coverage.getLimit()" + " = " +covTemp.getLimit().getCode()+" --> "+factorsUnitInstanceToAdd3MASS.getCode()+" = "+factorsUnitInstanceToAdd3MASS.getValue());
						}
						else if(riskType.equals(EnumRiskType.MOTORBIKE) )
						{
							logger.debug("getS1 riskType="+riskType);
							
							factorsUnitInstanceToAdd3MASS.setValue(covTemp.getLimit().getCode());
							logger.debug("getS1 coverage="+coverageCode+" factor : " + "coverage.getLimit()" + " = " +covTemp.getLimit().getCode()+" --> "+factorsUnitInstanceToAdd3MASS.getCode()+" = "+factorsUnitInstanceToAdd3MASS.getValue());
						}
						
						unitInstanceToAdd.getClauses().add(getClauseRCA());
						unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3MASS);
					}
				}
				else if(riskType.equals(EnumRiskType.BUS_TRAILER))
				{
					logger.debug("getS1 riskType="+riskType);
					
					assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_RCA11.value());
					
					if(otherVehicle.getSelectedDeductible() != null )
					{
							factorsUnitInstanceToAdd3RCFRA.setValue(otherVehicle.getSelectedDeductible().getWrapperCode().toString());
							logger.debug("getS1 coverage="+coverageCode+" factor : " + "otherVehicle.getSelectedDeductible()" + " = " +otherVehicle.getSelectedDeductible().getCode()+" --> "+factorsUnitInstanceToAdd3RCFRA.getCode()+" = "+factorsUnitInstanceToAdd3RCFRA.getValue());
							unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3RCFRA);
					}
					if(otherVehicle.getSelectedLimit()!= null )
					{
						factorsUnitInstanceToAdd3MASS.setValue(otherVehicle.getSelectedLimit().getWrapperCode().toString());
						logger.debug("getS1 coverage="+coverageCode+" factor : " + "otherVehicle.getSelectedLimit()" + " = " +otherVehicle.getSelectedLimit().getCode()+" --> "+factorsUnitInstanceToAdd3MASS.getCode()+" = "+factorsUnitInstanceToAdd3MASS.getValue());
						unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3MASS);
					}
				}
				else if(riskType.equals(EnumRiskType.TRUCK_UPTO_60000KG))
				{
					logger.debug("getS1 riskType="+riskType);
					
					assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_RCA1.value());
					
					if(otherVehicle.getSelectedDeductible() != null )
					{
						factorsUnitInstanceToAdd3RCFRA.setValue(otherVehicle.getSelectedDeductible().getWrapperCode().toString());
						logger.debug("getS1 coverage="+coverageCode+" factor : " + "otherVehicle.getSelectedDeductible()" + " = " +otherVehicle.getSelectedDeductible().getCode()+" --> "+factorsUnitInstanceToAdd3RCFRA.getCode()+" = "+factorsUnitInstanceToAdd3RCFRA.getValue());
						unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3RCFRA);
					}
					if(otherVehicle.getSelectedLimit()!= null )
					{
						factorsUnitInstanceToAdd3MASS.setValue(otherVehicle.getSelectedLimit().getWrapperCode().toString());
						logger.debug("getS1 coverage="+coverageCode+" factor : " + "otherVehicle.getSelectedLimit()" + " = " +otherVehicle.getSelectedLimit().getCode()+" --> "+factorsUnitInstanceToAdd3MASS.getCode()+" = "+factorsUnitInstanceToAdd3MASS.getValue());
						unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3MASS);
					}
				}
				else if(riskType.equals(EnumRiskType.TAXI))
				{
					logger.debug("getS1 riskType="+riskType);
					
					assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_RCA12.value());
					
					if(otherVehicle.getSelectedDeductible() != null )
					{
							factorsUnitInstanceToAdd3RCFRA.setValue(otherVehicle.getSelectedDeductible().getWrapperCode().toString());
							logger.debug("getS1 coverage="+coverageCode+" factor : " + "otherVehicle.getSelectedDeductible()" + " = " +otherVehicle.getSelectedDeductible().getCode()+" --> "+factorsUnitInstanceToAdd3RCFRA.getCode()+" = "+factorsUnitInstanceToAdd3RCFRA.getValue());
							unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3RCFRA);
					}
					if(otherVehicle.getSelectedLimit()!= null )
					{
						factorsUnitInstanceToAdd3MASS.setValue(otherVehicle.getSelectedLimit().getWrapperCode().toString());
						logger.debug("getS1 coverage="+coverageCode+" factor : " + "otherVehicle.getSelectedLimit()" + " = " +otherVehicle.getSelectedLimit().getCode()+" --> "+factorsUnitInstanceToAdd3MASS.getCode()+" = "+factorsUnitInstanceToAdd3MASS.getValue());
						unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3MASS);
					}
				}
				else if(riskType.equals(EnumRiskType.SPECIAL_VEHICLE))
				{
					logger.debug("getS1 riskType="+riskType);
					
					assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_RCA14.value());
					
					if(otherVehicle.getSelectedDeductible() != null )
					{
							factorsUnitInstanceToAdd3RCFRA.setValue(otherVehicle.getSelectedDeductible().getWrapperCode().toString());
							logger.debug("getS1 coverage="+coverageCode+" factor : " + "otherVehicle.getSelectedDeductible()" + " = " +otherVehicle.getSelectedDeductible().getCode()+" --> "+factorsUnitInstanceToAdd3RCFRA.getCode()+" = "+factorsUnitInstanceToAdd3RCFRA.getValue());
							unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3RCFRA);
					}
					if(otherVehicle.getSelectedLimit()!= null )
					{
						factorsUnitInstanceToAdd3MASS.setValue(otherVehicle.getSelectedLimit().getWrapperCode().toString());
						logger.debug("getS1 coverage="+coverageCode+" factor : " + "otherVehicle.getSelectedLimit()" + " = " +otherVehicle.getSelectedLimit().getCode()+" --> "+factorsUnitInstanceToAdd3MASS.getCode()+" = "+factorsUnitInstanceToAdd3MASS.getValue());
						unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3MASS);
					}
				}
				else if(riskType.equals(EnumRiskType.AGRICULTURAL_MACHINERY))
				{
					logger.debug("getS1 riskType="+riskType);
					
					assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_RCA15.value());
					
					if(otherVehicle.getSelectedDeductible() != null )
					{
							factorsUnitInstanceToAdd3RCFRA.setValue(otherVehicle.getSelectedDeductible().getWrapperCode().toString());
							logger.debug("getS1 coverage="+coverageCode+" factor : " + "otherVehicle.getSelectedDeductible()" + " = " +otherVehicle.getSelectedDeductible().getCode()+" --> "+factorsUnitInstanceToAdd3RCFRA.getCode()+" = "+factorsUnitInstanceToAdd3RCFRA.getValue());
							unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3RCFRA);
					}
					if(otherVehicle.getSelectedLimit()!= null )
					{
						factorsUnitInstanceToAdd3MASS.setValue(otherVehicle.getSelectedLimit().getWrapperCode().toString());
						logger.debug("getS1 coverage="+coverageCode+" factor : " + "otherVehicle.getSelectedLimit()" + " = " +otherVehicle.getSelectedLimit().getCode()+" --> "+factorsUnitInstanceToAdd3MASS.getCode()+" = "+factorsUnitInstanceToAdd3MASS.getValue());
						unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3MASS);
					}
				}
				else if(riskType.equals(EnumRiskType.AGRICULTURAL_MACHINERY_TRAILER))
				{
					logger.debug("getS1 riskType="+riskType);
					
					assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_RCA16.value());
					
					if(otherVehicle.getSelectedDeductible() != null )
					{
							factorsUnitInstanceToAdd3RCFRA.setValue(otherVehicle.getSelectedDeductible().getWrapperCode().toString());
							logger.debug("getS1 coverage="+coverageCode+" factor : " + "otherVehicle.getSelectedDeductible()" + " = " +otherVehicle.getSelectedDeductible().getCode()+" --> "+factorsUnitInstanceToAdd3RCFRA.getCode()+" = "+factorsUnitInstanceToAdd3RCFRA.getValue());
							unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3RCFRA);
					}
					if(otherVehicle.getSelectedLimit()!= null )
					{
						factorsUnitInstanceToAdd3MASS.setValue(otherVehicle.getSelectedLimit().getWrapperCode().toString());
						logger.debug("getS1 coverage="+coverageCode+" factor : " + "otherVehicle.getSelectedLimit()" + " = " +otherVehicle.getSelectedLimit().getCode()+" --> "+factorsUnitInstanceToAdd3MASS.getCode()+" = "+factorsUnitInstanceToAdd3MASS.getValue());
						unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3MASS);
					}
				}
				else if(riskType.equals(EnumRiskType.SPECIAL_VEHICLE_TRAILER))
				{
					logger.debug("getS1 riskType="+riskType);
					
					assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_RCA17.value());
					
					if(otherVehicle.getSelectedDeductible() != null )
					{
							factorsUnitInstanceToAdd3RCFRA.setValue(otherVehicle.getSelectedDeductible().getWrapperCode().toString());
							logger.debug("getS1 coverage="+coverageCode+" factor : " + "otherVehicle.getSelectedDeductible()" + " = " +otherVehicle.getSelectedDeductible().getCode()+" --> "+factorsUnitInstanceToAdd3RCFRA.getCode()+" = "+factorsUnitInstanceToAdd3RCFRA.getValue());
							unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3RCFRA);
					}
					if(otherVehicle.getSelectedLimit()!= null )
					{
						factorsUnitInstanceToAdd3MASS.setValue(otherVehicle.getSelectedLimit().getWrapperCode().toString());
						logger.debug("getS1 coverage="+coverageCode+" factor : " + "otherVehicle.getSelectedLimit()" + " = " +otherVehicle.getSelectedLimit().getCode()+" --> "+factorsUnitInstanceToAdd3MASS.getCode()+" = "+factorsUnitInstanceToAdd3MASS.getValue());
						unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3MASS);
					}
				}
				else if(riskType.equals(EnumRiskType.TRUCK_MORE_THAN_60000KG))
				{
					logger.debug("getS1 riskType="+riskType);
					
					assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_RCA2.value());
					
					if(otherVehicle.getSelectedDeductible() != null )
					{
							factorsUnitInstanceToAdd3RCFRA.setValue(otherVehicle.getSelectedDeductible().getWrapperCode().toString());
							logger.debug("getS1 coverage="+coverageCode+" factor : " + "otherVehicle.getSelectedDeductible()" + " = " +otherVehicle.getSelectedDeductible().getCode()+" --> "+factorsUnitInstanceToAdd3RCFRA.getCode()+" = "+factorsUnitInstanceToAdd3RCFRA.getValue());
							unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3RCFRA);
					}
					if(otherVehicle.getSelectedLimit()!= null )
					{
						factorsUnitInstanceToAdd3MASS.setValue(otherVehicle.getSelectedLimit().getWrapperCode().toString());
						logger.debug("getS1 coverage="+coverageCode+" factor : " + "otherVehicle.getSelectedLimit()" + " = " +otherVehicle.getSelectedLimit().getCode()+" --> "+factorsUnitInstanceToAdd3MASS.getCode()+" = "+factorsUnitInstanceToAdd3MASS.getValue());
						unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3MASS);
					}
				}
				else if(riskType.equals(EnumRiskType.TRUCK_TRAILER))
				{
					logger.debug("getS1 riskType="+riskType);
					
					if(otherVehicle.getWeight() <= 60)
						assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_RCA3.value());
					
					if(otherVehicle.getSelectedDeductible() != null )
					{
							factorsUnitInstanceToAdd3RCFRA.setValue(otherVehicle.getSelectedDeductible().getWrapperCode().toString());
							logger.debug("getS1 coverage="+coverageCode+" factor : " + "otherVehicle.getSelectedDeductible()" + " = " +otherVehicle.getSelectedDeductible().getCode()+" --> "+factorsUnitInstanceToAdd3RCFRA.getCode()+" = "+factorsUnitInstanceToAdd3RCFRA.getValue());
							unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3RCFRA);
					}
					if(otherVehicle.getSelectedLimit()!= null )
					{
						factorsUnitInstanceToAdd3MASS.setValue(otherVehicle.getSelectedLimit().getWrapperCode().toString());
						logger.debug("getS1 coverage="+coverageCode+" factor : " + "otherVehicle.getSelectedLimit()" + " = " +otherVehicle.getSelectedLimit().getCode()+" --> "+factorsUnitInstanceToAdd3MASS.getCode()+" = "+factorsUnitInstanceToAdd3MASS.getValue());
						unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3MASS);
					}
					if(otherVehicle.getWeight() > 60)
						assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_RCA4.value());
					
					if(otherVehicle.getSelectedDeductible() != null )
					{
							factorsUnitInstanceToAdd3RCFRA.setValue(otherVehicle.getSelectedDeductible().getWrapperCode().toString());
							logger.debug("getS1 coverage="+coverageCode+" factor : " + "otherVehicle.getSelectedDeductible()" + " = " +otherVehicle.getSelectedDeductible().getCode()+" --> "+factorsUnitInstanceToAdd3RCFRA.getCode()+" = "+factorsUnitInstanceToAdd3RCFRA.getValue());
							unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3RCFRA);
					}
					if(otherVehicle.getSelectedLimit()!= null )
					{
						factorsUnitInstanceToAdd3MASS.setValue(otherVehicle.getSelectedLimit().getWrapperCode().toString());
						logger.debug("getS1 coverage="+coverageCode+" factor : " + "otherVehicle.getSelectedLimit()" + " = " +otherVehicle.getSelectedLimit().getCode()+" --> "+factorsUnitInstanceToAdd3MASS.getCode()+" = "+factorsUnitInstanceToAdd3MASS.getValue());
						unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3MASS);
					}
				}
				else if(riskType.equals(EnumRiskType.CAMPER))
				{
					logger.debug("getS1 riskType="+riskType);
					
					assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_RCA5.value());
					
					if(otherVehicle.getSelectedDeductible() != null )
					{
							factorsUnitInstanceToAdd3RCFRA.setValue(otherVehicle.getSelectedDeductible().getWrapperCode().toString());
							logger.debug("getS1 coverage="+coverageCode+" factor : " + "otherVehicle.getSelectedDeductible()" + " = " +otherVehicle.getSelectedDeductible().getCode()+" --> "+factorsUnitInstanceToAdd3RCFRA.getCode()+" = "+factorsUnitInstanceToAdd3RCFRA.getValue());
							unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3RCFRA);
					}
					if(otherVehicle.getSelectedLimit()!= null )
					{
						factorsUnitInstanceToAdd3MASS.setValue(otherVehicle.getSelectedLimit().getWrapperCode().toString());
						logger.debug("getS1 coverage="+coverageCode+" factor : " + "otherVehicle.getSelectedLimit()" + " = " +otherVehicle.getSelectedLimit().getCode()+" --> "+factorsUnitInstanceToAdd3MASS.getCode()+" = "+factorsUnitInstanceToAdd3MASS.getValue());
						unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3MASS);
					}
				}
				else if(riskType.equals(EnumRiskType.MOTORCYCLE_FREIGHT_TRANSPORT))
				{
					logger.debug("getS1 riskType="+riskType);
					
					assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_RCA7.value());
					
					if(otherVehicle.getSelectedDeductible() != null )
					{
							factorsUnitInstanceToAdd3RCFRA.setValue(otherVehicle.getSelectedDeductible().getWrapperCode().toString());
							logger.debug("getS1 coverage="+coverageCode+" factor : " + "otherVehicle.getSelectedDeductible()" + " = " +otherVehicle.getSelectedDeductible().getCode()+" --> "+factorsUnitInstanceToAdd3RCFRA.getCode()+" = "+factorsUnitInstanceToAdd3RCFRA.getValue());
							unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3RCFRA);
					}
					if(otherVehicle.getSelectedLimit()!= null )
					{
						factorsUnitInstanceToAdd3MASS.setValue(otherVehicle.getSelectedLimit().getWrapperCode().toString());
						logger.debug("getS1 coverage="+coverageCode+" factor : " + "otherVehicle.getSelectedLimit()" + " = " +otherVehicle.getSelectedLimit().getCode()+" --> "+factorsUnitInstanceToAdd3MASS.getCode()+" = "+factorsUnitInstanceToAdd3MASS.getValue());
						unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3MASS);
					}
				}
				else if(riskType.equals(EnumRiskType.URBAN_BUS))
				{
					logger.debug("getS1 riskType="+riskType);
					
					assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_RCA8.value());
					
					if(otherVehicle.getSelectedDeductible() != null )
					{
							factorsUnitInstanceToAdd3RCFRA.setValue(otherVehicle.getSelectedDeductible().getWrapperCode().toString());
							logger.debug("getS1 coverage="+coverageCode+" factor : " + "otherVehicle.getSelectedDeductible()" + " = " +otherVehicle.getSelectedDeductible().getCode()+" --> "+factorsUnitInstanceToAdd3RCFRA.getCode()+" = "+factorsUnitInstanceToAdd3RCFRA.getValue());
							unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3RCFRA);
					}
					if(otherVehicle.getSelectedLimit()!= null )
					{
						factorsUnitInstanceToAdd3MASS.setValue(otherVehicle.getSelectedLimit().getWrapperCode().toString());
						logger.debug("getS1 coverage="+coverageCode+" factor : " + "otherVehicle.getSelectedLimit()" + " = " +otherVehicle.getSelectedLimit().getCode()+" --> "+factorsUnitInstanceToAdd3MASS.getCode()+" = "+factorsUnitInstanceToAdd3MASS.getValue());
						unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3MASS);
					}
				}
				else if(riskType.equals(EnumRiskType.OUT_OF_TOWN_TURISTIC_BUS))
				{
					logger.debug("getS1 riskType="+riskType);
					
					assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_RCA9.value());
					
					if(otherVehicle.getSelectedDeductible() != null )
					{
							factorsUnitInstanceToAdd3RCFRA.setValue(otherVehicle.getSelectedDeductible().getWrapperCode().toString());
							logger.debug("getS1 coverage="+coverageCode+" factor : " + "otherVehicle.getSelectedDeductible()" + " = " +otherVehicle.getSelectedDeductible().getCode()+" --> "+factorsUnitInstanceToAdd3RCFRA.getCode()+" = "+factorsUnitInstanceToAdd3RCFRA.getValue());
							unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3RCFRA);
					}
					if(otherVehicle.getSelectedLimit()!= null )
					{
						factorsUnitInstanceToAdd3MASS.setValue(otherVehicle.getSelectedLimit().getWrapperCode().toString());
						logger.debug("getS1 coverage="+coverageCode+" factor : " + "otherVehicle.getSelectedLimit()" + " = " +otherVehicle.getSelectedLimit().getCode()+" --> "+factorsUnitInstanceToAdd3MASS.getCode()+" = "+factorsUnitInstanceToAdd3MASS.getValue());
						unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3MASS);
					}
				}
				else if(riskType.equals(EnumRiskType.MOPED))
				{
					logger.debug("getS1 riskType="+riskType);
					
					assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_RCAR1.value());
					
					if(covTemp.getDeductible() != null && covTemp.getDeductible().getCode() != null)
					{
						factorsUnitInstanceToAdd3RCFRA.setValue(covTemp.getDeductible().getCode());
						logger.debug("getS1 coverage="+coverageCode+" factor : " + "coverage.getDeductible()" + " = " +covTemp.getDeductible().getCode()+" --> "+factorsUnitInstanceToAdd3RCFRA.getCode()+" = "+factorsUnitInstanceToAdd3RCFRA.getValue());
						unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3RCFRA);
					}
					if(covTemp.getLimit() != null && covTemp.getLimit().getCode() != null)
					{
						factorsUnitInstanceToAdd3MASS.setValue(covTemp.getLimit().getCode());
						logger.debug("getS1 coverage="+coverageCode+" factor : " + "coverage.getLimit()" + " = " +covTemp.getLimit().getCode()+" --> "+factorsUnitInstanceToAdd3MASS.getCode()+" = "+factorsUnitInstanceToAdd3MASS.getValue());
						unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3MASS);
					}
				}
				assetUnitTemp.setSelection((covTemp.getSelected() != null && covTemp.getSelected())  ? tybT : tybF);
				
//				add Unitinstance to AssetUnit for MOTOR_RCA
				assetUnitTemp.getInstances().add(unitInstanceToAdd);
//				add AssetUnit to AssetSection for MOTOR_RCA
				assetSectionResultS1.getUnits().add(assetUnitTemp);
				
				logger.debug("getS1 AssetSection "+assetSectionResultS1.getCode());
				logger.debug("getS1 add wsAssetUnit.code"+assetUnitTemp.getCode()+" wsAssetUnit.selected ? "+covTemp.getSelected());
				logger.debug("getS1 with "+unitInstanceToAdd.getFactors().size()+" wsAssetUnit.factors");
				logger.debug("getS1 end creation "+coverageCode);
			}
			else if( covTemp != null && coverageCode != null && coverageCode.equals(EnumCoverageCode.MOTOR_BONUS_PROTECTED))
			{
				logger.debug("getS1 begin creation "+coverageCode);
				
				WsAssetUnit assetUnitTemp = getAssetUnitInit();
				WsUnitInstance unitInstanceToAdd = getUnitInstanceInit();
				logger.debug("getS1 add factors generic to "+coverageCode);
				getFactorsForUnitInstanceGeneric(unitInstanceToAdd, covTemp, coverageCode);
				
				assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_BP1.value());						
				assetUnitTemp.setSelection((covTemp.getSelected() != null && covTemp.getSelected()) ? tybT : tybF);
				
//				add Unitinstance to AssetUnit for MOTOR_BONUS_PROTECTED
				assetUnitTemp.getInstances().add(unitInstanceToAdd);
//				add AssetUnit to AssetSection for MOTOR_BONUS_PROTECTED
				assetSectionResultS1.getUnits().add(assetUnitTemp);
				
				logger.debug("getS1 AssetSection "+assetSectionResultS1.getCode());
				logger.debug("getS1 add wsAssetUnit.code"+assetUnitTemp.getCode()+" wsAssetUnit.selected ? "+covTemp.getSelected());
				logger.debug("getS1 with "+unitInstanceToAdd.getFactors().size()+" wsAssetUnit.factors");
				logger.debug("getS1 end creation "+coverageCode);
			}
		}
		
		if(assetSectionResultS1.getUnits().isEmpty())
		{
			assetSectionResultS1 = null;
			logger.debug("getS1 S1 is empty");
		}
		
		return assetSectionResultS1;
	}
	
}
