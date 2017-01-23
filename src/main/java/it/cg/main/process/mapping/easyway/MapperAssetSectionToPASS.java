package it.cg.main.process.mapping.easyway;

import java.util.List;

import org.apache.log4j.Logger;

import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.ICoverage;
import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.IFigure;
import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.IOtherVehicle;
import com.mapfre.engines.rating.common.enums.EnumCoverageCode;
import com.mapfre.engines.rating.common.enums.EnumRiskType;
import com.pass.global.TypeBooleano;
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

public class MapperAssetSectionToPASS
{
	private Logger logger = Logger.getLogger(getClass());

	private TypeBooleano tybT = new TypeBooleano();
	private TypeBooleano tybF = new TypeBooleano();
	private boolean isEnableTariffFormulaLogActive;
	private IFigure figureOwner;
	private Integer numberOfYoungDriver;
	
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

		
		EnumRiskType riskType = inbJsonRequest.getInboundQuoteDTO().getContext().getRiskType();
		logger.debug("init riskType :"+riskType);
		List<ICoverage> listCov = inbJsonRequest.getInboundQuoteDTO().getCoverages();
		logger.debug("init listCov with coverages :"+listCov);
		
		this.tybT.setBoolean(true);
		this.tybF.setBoolean(false);
		this.isEnableTariffFormulaLogActive = inbJsonRequest.getInboundQuoteDTO().getEnableDebugging() == null ? 
														false : inbJsonRequest.getInboundQuoteDTO().getEnableDebugging();
		logger.debug("init isEnableTariffFormulaLogActive :"+isEnableTariffFormulaLogActive);
		this.figureOwner = figureOwnerRequest;
		logger.debug("init figureOwner :"+figureOwner);
		this.numberOfYoungDriver = inbJsonRequest.getInboundQuoteDTO().getNumberOfYoungDriver();
		
		WsAssetSection assetSectionSx = getS1( listCov, riskType, inbJsonRequest.getInboundQuoteDTO().getOtherVehicle() );
		if(assetSectionSx != null)
			assetInReqest.getSections().add(assetSectionSx);
		
		assetSectionSx = new WsAssetSection();
		assetSectionSx = getS2(listCov, riskType);
		if(assetSectionSx != null)
			assetInReqest.getSections().add(assetSectionSx);
		
		assetSectionSx = new WsAssetSection();
		assetSectionSx = getS3(listCov, riskType);
		if(assetSectionSx != null)
			assetInReqest.getSections().add(assetSectionSx);
		
		assetSectionSx = new WsAssetSection();
		assetSectionSx = getS4(listCov, riskType);
		if(assetSectionSx != null)
			assetInReqest.getSections().add(assetSectionSx);
		
		assetSectionSx = new WsAssetSection();
		assetSectionSx = getS5(listCov, riskType);
		if(assetSectionSx != null)
			assetInReqest.getSections().add(assetSectionSx);
		
		assetSectionSx = new WsAssetSection();
		assetSectionSx = getS6(listCov, riskType);
		if(assetSectionSx != null)
			assetInReqest.getSections().add(assetSectionSx);
		
	}
	
	/**
	 * Init new UnitInstace with  isEnableTariffFormulaLogActive for test mode
	 * and exceptionCode from figure type Owner (province)
	 * Add Clauses for unitinstance
	 * @param 
	 * @return new WsUnitInstance
	 */
	private WsUnitInstance getUnitInstanceInit()
	{
		logger.info("into getUnitInstanceInit, init unitinstace");
		
		WsUnitInstance unitInstance = new WsUnitInstance();
//		EnableTariffFormulalog
		logger.debug("New instance of UnitInstance with isEnableTariffFormulaLogActive : "+isEnableTariffFormulaLogActive);
		if(isEnableTariffFormulaLogActive)
			unitInstance.setEnableTariffFormulaLog(this.tybT);
		else
			unitInstance.setEnableTariffFormulaLog(this.tybF);
//		ExceptionCode
		if(figureOwner != null && figureOwner.getResidenceAddress() != null)
		{
			logger.debug("set into unitInstance ExceptionCode : "+figureOwner.getResidenceAddress().getProvince());
			unitInstance.setExceptionCode(figureOwner.getResidenceAddress().getProvince());
		}
		
//		Clauses
		WsClause clauseUnitInstance = new WsClause();
		clauseUnitInstance.setCode("RCA001");
		if(this.numberOfYoungDriver != null && this.numberOfYoungDriver > 0)
			clauseUnitInstance.setSelected(tybT);
		else
			clauseUnitInstance.setSelected(tybF);
		logger.debug("Add clauses "+clauseUnitInstance+" into unitinstance"+unitInstance);
		unitInstance.getClauses().add(clauseUnitInstance);
		
		logger.info("out getUnitInstanceInit with response : "+unitInstance);
		return unitInstance;
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
		if(coverageCode != null)
		{
			logger.debug("(getFactorsForUnitInstanceGeneric) Setting of coverage code into unitinstance name : " + coverageCode.getCode());
			unitInstanceToEdit.setName(coverageCode.getCode());
		}
		WsFactor factorToAdd = new WsFactor();
//		fattori per tutte le unit instances
		if(coverage.getFiddleFactor() != null)
		{
			factorToAdd.setCode(ENUMInternalUnitInstanceFactors.FACTOR_1FIDRC.value());
			factorToAdd.setValue(coverage.getFiddleFactor().toString());
			unitInstanceToEdit.getFactors().add(factorToAdd);
		}
		if(coverage.getDiscount() != null)
		{
			factorToAdd = new WsFactor();
			factorToAdd.setCode(ENUMInternalUnitInstanceFactors.FACTOR_3ADJ.value());
			factorToAdd.setValue(coverage.getDiscount().toString());
			unitInstanceToEdit.getFactors().add(factorToAdd);
		}
		if(coverage.getQuickAndDirty() != null)
		{
			factorToAdd = new WsFactor();
			factorToAdd.setCode(ENUMInternalUnitInstanceFactors.FACTOR_3QD.value());
			factorToAdd.setValue(coverage.getQuickAndDirty().toString());
			unitInstanceToEdit.getFactors().add(factorToAdd);
		}
		if(coverage.getPreviousNetAmount() != null)
		{
			factorToAdd = new WsFactor();
			factorToAdd.setCode(ENUMInternalUnitInstanceFactors.FACTOR_FRRCA.value());
			factorToAdd.setValue(coverage.getPreviousNetAmount().toString());
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
			}
			else if(coverage.getCode().equals(EnumCoverageCode.MOTOR_ROAD_ASSISTANCE_DELUXE) )
			{
				factorToAdd = new WsFactor();
				factorToAdd.setCode(ENUMInternalUnitInstanceFactors.FACTOR_3TIPAS.value());
				factorToAdd.setValue("2");
				unitInstanceToEdit.getFactors().add(factorToAdd);
			}
			else if(coverage.getCode().equals(EnumCoverageCode.MOTOR_LEGAL_PROTECTION_BASE) )
			{
				factorToAdd = new WsFactor();
				factorToAdd.setCode(ENUMInternalUnitInstanceFactors.FACTOR_3TIPTL.value());
				factorToAdd.setValue("1");
				unitInstanceToEdit.getFactors().add(factorToAdd);
			}
			else if(coverage.getCode().equals(EnumCoverageCode.MOTOR_LEGAL_PROTECTION_DELUXE) )
			{
				factorToAdd = new WsFactor();
				factorToAdd.setCode(ENUMInternalUnitInstanceFactors.FACTOR_3TIPTL.value());
				factorToAdd.setValue("2");
				unitInstanceToEdit.getFactors().add(factorToAdd);
			}
		}
	
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
			}
			if(coverage.getLimit() != null && coverage.getLimit().getCode() != null)
			{
				factorToAdd  = new WsFactor();
				factorToAdd.setCode(ENUMInternalUnitInstanceFactors.FACTOR_3CRLMT.value());
				factorToAdd.setValue(coverage.getLimit().getCode());
				
				unitInstanceToEdit.getFactors().add(factorToAdd);
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
			WsAssetUnit assetUnitTemp = new WsAssetUnit();
			WsUnitInstance unitInstanceToAdd = getUnitInstanceInit();
			WsFactor unitInstanceFactorToAdd  = new WsFactor();
			getFactorsForUnitInstanceGeneric(unitInstanceToAdd, covTemp, covTemp.getCode());

			if(covTemp.getCode().equals(EnumCoverageCode.MOTOR_VANDALISM))
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
			else if(covTemp.getCode().equals(EnumCoverageCode.MOTOR_COLLISION))
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
			else if(covTemp.getCode().equals(EnumCoverageCode.MOTOR_CRYSTALS))
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
			else if(covTemp.getCode().equals(EnumCoverageCode.MOTOR_NATURAL_EVENTS))
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
			else if(covTemp.getCode().equals(EnumCoverageCode.MOTOR_KASKO))
			{
//			assetunit MOTOR_KASKO
				assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_KAS1.value());
				assetUnitTemp.setSelection((covTemp.getSelected() != null && covTemp.getSelected()) ? tybT : tybF);
				
				getFactorsForUnitInstanceNotS1S5(unitInstanceToAdd, covTemp, ENUMInternalCodeAssetUnit.CODE_EN1.value(), riskType);

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
							unitInstanceToAdd.getFactors().add(unitInstanceFactorToAdd);
						}
					}
				}
//				add Unitinstance tu AssetUnit
				assetUnitTemp.getInstances().add(unitInstanceToAdd);
//				add AssetUnit to AssetSection
				assetSectionResultS6.getUnits().add(assetUnitTemp);
			}
		}
		
		if(assetSectionResultS6.getUnits().isEmpty())
		{
			assetSectionResultS6 = null;
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
			WsAssetUnit assetUnitTemp = new WsAssetUnit();
			WsUnitInstance unitInstanceToAdd = getUnitInstanceInit();
			getFactorsForUnitInstanceGeneric(unitInstanceToAdd, covTemp, covTemp.getCode());
			
			if( (covTemp.getCode().equals(EnumCoverageCode.MOTOR_LEGAL_PROTECTION_BASE) ||
					covTemp.getCode().equals(EnumCoverageCode.MOTOR_LEGAL_PROTECTION_DELUXE)) &&
						(riskType.equals(EnumRiskType.CAR) || riskType.equals(EnumRiskType.MOTORBIKE)) )
			{
				assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_TG1.value());
				assetUnitTemp.setSelection((covTemp.getSelected() != null && covTemp.getSelected()) ? tybT : tybF);
//				add Unitinstance tu AssetUnit
				assetUnitTemp.getInstances().add(unitInstanceToAdd);
//				add AssetUnit to AssetSection
				assetSectionResultS5.getUnits().add(assetUnitTemp);
			}
			else if( (covTemp.getCode().equals(EnumCoverageCode.MOTOR_LEGAL_PROTECTION_BASE) ||
						covTemp.getCode().equals(EnumCoverageCode.MOTOR_LEGAL_PROTECTION_DELUXE)) &&
							riskType.equals(EnumRiskType.MOPED) )
			{
				assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_TG2.value());
				assetUnitTemp.setSelection((covTemp.getSelected() != null && covTemp.getSelected()) ? tybT : tybF);
//				add Unitinstance tu AssetUnit
				assetUnitTemp.getInstances().add(unitInstanceToAdd);
//				add AssetUnit to AssetSection
				assetSectionResultS5.getUnits().add(assetUnitTemp);
			}
		}
		
		if(assetSectionResultS5.getUnits().isEmpty())
		{
			assetSectionResultS5 = null;
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
			WsAssetUnit assetUnitTemp = new WsAssetUnit();
			WsUnitInstance unitInstanceToAdd = getUnitInstanceInit();
			getFactorsForUnitInstanceGeneric(unitInstanceToAdd, covTemp, covTemp.getCode());
			
			if( covTemp.getCode().equals(EnumCoverageCode.MOTOR_ROAD_ASSISTANCE_BASE) ||
					covTemp.getCode().equals(EnumCoverageCode.MOTOR_ROAD_ASSISTANCE_DELUXE) )
			{
				assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_AS1.value());
				assetUnitTemp.setSelection((covTemp.getSelected() != null && covTemp.getSelected()) ? tybT : tybF);
//				add Unitinstance tu AssetUnit
				assetUnitTemp.getInstances().add(unitInstanceToAdd);
//				add AssetUnit to AssetSection
				assetSectionResultS4.getUnits().add(assetUnitTemp);
			}
		}
		
		if(assetSectionResultS4.getUnits().isEmpty())
		{
			assetSectionResultS4 = null;
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
			WsAssetUnit assetUnitTemp = new WsAssetUnit();
			WsUnitInstance unitInstanceToAdd = getUnitInstanceInit();
			WsFactor unitInstanceFactorToAdd  = new WsFactor();
			getFactorsForUnitInstanceGeneric(unitInstanceToAdd, covTemp, covTemp.getCode());
			unitInstanceFactorToAdd.setCode(ENUMInternalUnitInstanceFactors.FACTOR_3SAINP.value());
			
			if( covTemp.getCode().equals(EnumCoverageCode.MOTOR_PERMANENT_INVALIDITY_DRIVER) )
			{
				assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_IIP1.value());
				assetUnitTemp.setSelection((covTemp.getSelected() != null && covTemp.getSelected()) ? tybT : tybF);
				
				if(covTemp.getLimit() != null && covTemp.getLimit().getCode() != null)
				{
					if(riskType.equals(EnumRiskType.MOTORBIKE))
					{
						unitInstanceFactorToAdd.setValue(covTemp.getLimit().getCode());
						unitInstanceToAdd.getFactors().add(unitInstanceFactorToAdd);
					}
					else if(riskType.equals(EnumRiskType.CAR))
					{
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
					}
				}
//				add Unitinstance tu AssetUnit
				assetUnitTemp.getInstances().add(unitInstanceToAdd);
//				add AssetUnit to AssetSection
				assetSectionResultS3.getUnits().add(assetUnitTemp);
			}
			else if( covTemp.getCode().equals(EnumCoverageCode.MOTOR_DEATH_DRIVER) )
			{
				assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_IM1.value());
				assetUnitTemp.setSelection((covTemp.getSelected() != null && covTemp.getSelected()) ? tybT : tybF);
				if(covTemp.getLimit() != null && covTemp.getLimit().getCode() != null)
				{
					if(riskType.equals(EnumRiskType.MOTORBIKE))
					{
						unitInstanceFactorToAdd.setValue(covTemp.getLimit().getCode());
						unitInstanceToAdd.getFactors().add(unitInstanceFactorToAdd);
					}
					else if(riskType.equals(EnumRiskType.CAR))
					{
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
					}
				}
//				add Unitinstance tu AssetUnit
				assetUnitTemp.getInstances().add(unitInstanceToAdd);
//				add AssetUnit to AssetSection
				assetSectionResultS3.getUnits().add(assetUnitTemp);
			}
			
		}
		
		if(assetSectionResultS3.getUnits().isEmpty())
		{
			assetSectionResultS3 = null;
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
			WsAssetUnit assetUnitTemp = new WsAssetUnit();
			WsUnitInstance unitInstanceToAdd = getUnitInstanceInit();
			WsFactor factorsUnitInstanceToAdd  = new WsFactor();
			getFactorsForUnitInstanceGeneric(unitInstanceToAdd, covTemp, covTemp.getCode());
			
			if( covTemp.getCode().equals(EnumCoverageCode.MOTOR_THEFT) )
			{
				assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_FUR1.value());
				assetUnitTemp.setSelection((covTemp.getSelected() != null && covTemp.getSelected()) ? tybT : tybF);
				
				if(covTemp.getCoinsurance() != null && covTemp.getCoinsurance().getCode() != null)
				{
						factorsUnitInstanceToAdd.setCode(ENUMInternalUnitInstanceFactors.FACTOR_3FRSCF.value());
						if(riskType.equals(EnumRiskType.MOTORBIKE))
						{
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
						}
						else if(riskType.equals(EnumRiskType.CAR))
						{
							factorsUnitInstanceToAdd.setValue(covTemp.getCoinsurance().getCode());
							
							unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd);
						}
				}
				
//				add Unitinstance tu AssetUnit
				assetUnitTemp.getInstances().add(unitInstanceToAdd);
//				add AssetUnit to AssetSection
				assetSectionResultS2.getUnits().add(assetUnitTemp);
			}
			else if( covTemp.getCode().equals(EnumCoverageCode.MOTOR_FIRE) )
			{
				assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_INC1.value());
				assetUnitTemp.setSelection((covTemp.getSelected() != null && covTemp.getSelected()) ? tybT : tybF);
				
				if(covTemp.getCoinsurance() != null && covTemp.getCoinsurance().getCode() != null)
				{
					factorsUnitInstanceToAdd.setCode(ENUMInternalUnitInstanceFactors.FACTOR_3FRSCF.value());
					if(riskType.equals(EnumRiskType.MOTORBIKE))
					{
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
					}
					else if(riskType.equals(EnumRiskType.CAR))
					{
						factorsUnitInstanceToAdd.setValue(covTemp.getCoinsurance().getCode());
						
						unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd);
					}
				}
//				add Unitinstance tu AssetUnit
				assetUnitTemp.getInstances().add(unitInstanceToAdd);
//				add AssetUnit to AssetSection
				assetSectionResultS2.getUnits().add(assetUnitTemp);
			}
		}
		
		if(assetSectionResultS2.getUnits().isEmpty())
		{
			assetSectionResultS2 = null;
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
		WsAssetSection assetSectionResultS1 = new WsAssetSection();
		assetSectionResultS1.setCode(ENUMInternalCodeSection.CODE_S1.value());
		
		for (ICoverage covTemp : listCov)
		{
			WsAssetUnit assetUnitTemp = new WsAssetUnit();
			WsUnitInstance unitInstanceToAdd = getUnitInstanceInit();
			WsFactor factorsUnitInstanceToAdd3RCFRA  = new WsFactor();
			WsFactor factorsUnitInstanceToAdd3MASS  = new WsFactor();
			getFactorsForUnitInstanceGeneric(unitInstanceToAdd, covTemp, covTemp.getCode());
			
			if( covTemp != null && covTemp.getCode() != null && covTemp.getCode().equals(EnumCoverageCode.MOTOR_RCA))
			{
//				CODE Factor for Unitinstance 
				factorsUnitInstanceToAdd3RCFRA.setCode(ENUMInternalUnitInstanceFactors.FACTOR_3RCFRA.value());
				factorsUnitInstanceToAdd3MASS.setCode(ENUMInternalUnitInstanceFactors.FACTOR_3MASS.value());
				
				//TODO Controllare se per RCA11 sia da associare il riskType RIAB (rimorchiio autobus)
				if( riskType.equals(EnumRiskType.URBAN_BUS) || riskType.equals(EnumRiskType.OUT_OF_TOWN_TURISTIC_BUS) )
				{
					assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_RCA11.value());						
				}
				else if(riskType.equals(EnumRiskType.CAR) || riskType.equals(EnumRiskType.MOTORBIKE))
				{
					assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_RCA1.value());
//					unitinstance factor Value
					if(covTemp.getDeductible() != null && covTemp.getDeductible().getCode() != null)
					{
						factorsUnitInstanceToAdd3RCFRA.setValue(covTemp.getDeductible().getCode());
						unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3RCFRA);
					}
					if(covTemp.getLimit() != null && covTemp.getLimit().getCode() != null)
					{
						if(riskType.equals(EnumRiskType.CAR))
						{
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
						}
						else if(riskType.equals(EnumRiskType.MOTORBIKE) )
						{
							factorsUnitInstanceToAdd3MASS.setValue(covTemp.getLimit().getCode());
						}
						unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3MASS);
					}
				}
				//TODO Aggiungere i set delle unit instance 3MASS e 3RCFRA per tutti gli "other vehicle"
				else if(riskType.equals(EnumRiskType.TRUCK_UPTO_60000KG))
				{
					assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_RCA1.value());
					
					if(otherVehicle.getSelectedDeductible() != null )
					{
							factorsUnitInstanceToAdd3RCFRA.setValue(otherVehicle.getSelectedDeductible().getWrapperCode().toString());
							unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3RCFRA);
					}
					if(otherVehicle.getSelectedLimit()!= null )
					{
						factorsUnitInstanceToAdd3RCFRA.setValue(otherVehicle.getSelectedLimit().getWrapperCode().toString());
						unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3MASS);
					}
				}
				else if(riskType.equals(EnumRiskType.TAXI))
				{
					assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_RCA12.value());
					
					if(otherVehicle.getSelectedDeductible() != null )
					{
							factorsUnitInstanceToAdd3RCFRA.setValue(otherVehicle.getSelectedDeductible().getWrapperCode().toString());
							unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3RCFRA);
					}
					if(otherVehicle.getSelectedLimit()!= null )
					{
						factorsUnitInstanceToAdd3RCFRA.setValue(otherVehicle.getSelectedLimit().getWrapperCode().toString());
						unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3MASS);
					}
				}
				else if(riskType.equals(EnumRiskType.SPECIAL_VEHICLE))
				{
					assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_RCA14.value());
					
					if(otherVehicle.getSelectedDeductible() != null )
					{
							factorsUnitInstanceToAdd3RCFRA.setValue(otherVehicle.getSelectedDeductible().getWrapperCode().toString());
							unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3RCFRA);
					}
					if(otherVehicle.getSelectedLimit()!= null )
					{
						factorsUnitInstanceToAdd3RCFRA.setValue(otherVehicle.getSelectedLimit().getWrapperCode().toString());
						unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3MASS);
					}
				}
				else if(riskType.equals(EnumRiskType.AGRICULTURAL_MACHINERY))
				{
					assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_RCA15.value());
					
					if(otherVehicle.getSelectedDeductible() != null )
					{
							factorsUnitInstanceToAdd3RCFRA.setValue(otherVehicle.getSelectedDeductible().getWrapperCode().toString());
							unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3RCFRA);
					}
					if(otherVehicle.getSelectedLimit()!= null )
					{
						factorsUnitInstanceToAdd3RCFRA.setValue(otherVehicle.getSelectedLimit().getWrapperCode().toString());
						unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3MASS);
					}
				}
				else if(riskType.equals(EnumRiskType.AGRICULTURAL_MACHINERY_TRAILER))
				{
					assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_RCA16.value());
					
					if(otherVehicle.getSelectedDeductible() != null )
					{
							factorsUnitInstanceToAdd3RCFRA.setValue(otherVehicle.getSelectedDeductible().getWrapperCode().toString());
							unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3RCFRA);
					}
					if(otherVehicle.getSelectedLimit()!= null )
					{
						factorsUnitInstanceToAdd3RCFRA.setValue(otherVehicle.getSelectedLimit().getWrapperCode().toString());
						unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3MASS);
					}
				}
				else if(riskType.equals(EnumRiskType.SPECIAL_VEHICLE_TRAILER))
				{
					assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_RCA17.value());
					
					if(otherVehicle.getSelectedDeductible() != null )
					{
							factorsUnitInstanceToAdd3RCFRA.setValue(otherVehicle.getSelectedDeductible().getWrapperCode().toString());
							unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3RCFRA);
					}
					if(otherVehicle.getSelectedLimit()!= null )
					{
						factorsUnitInstanceToAdd3RCFRA.setValue(otherVehicle.getSelectedLimit().getWrapperCode().toString());
						unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3MASS);
					}
				}
				else if(riskType.equals(EnumRiskType.TRUCK_MORE_THAN_60000KG))
				{
					assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_RCA2.value());
					
					if(otherVehicle.getSelectedDeductible() != null )
					{
							factorsUnitInstanceToAdd3RCFRA.setValue(otherVehicle.getSelectedDeductible().getWrapperCode().toString());
							unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3RCFRA);
					}
					if(otherVehicle.getSelectedLimit()!= null )
					{
						factorsUnitInstanceToAdd3RCFRA.setValue(otherVehicle.getSelectedLimit().getWrapperCode().toString());
						unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3MASS);
					}
				}
				else if(riskType.equals(EnumRiskType.TRUCK_TRAILER))
				{
					if(otherVehicle.getWeight() <= 60)
						assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_RCA3.value());
					
					if(otherVehicle.getSelectedDeductible() != null )
					{
							factorsUnitInstanceToAdd3RCFRA.setValue(otherVehicle.getSelectedDeductible().getWrapperCode().toString());
							unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3RCFRA);
					}
					if(otherVehicle.getSelectedLimit()!= null )
					{
						factorsUnitInstanceToAdd3RCFRA.setValue(otherVehicle.getSelectedLimit().getWrapperCode().toString());
						unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3MASS);
					}
					if(otherVehicle.getWeight() > 60)
						assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_RCA4.value());
					
					if(otherVehicle.getSelectedDeductible() != null )
					{
							factorsUnitInstanceToAdd3RCFRA.setValue(otherVehicle.getSelectedDeductible().getWrapperCode().toString());
							unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3RCFRA);
					}
					if(otherVehicle.getSelectedLimit()!= null )
					{
						factorsUnitInstanceToAdd3RCFRA.setValue(otherVehicle.getSelectedLimit().getWrapperCode().toString());
						unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3MASS);
					}
				}
				else if(riskType.equals(EnumRiskType.CAMPER))
				{
					assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_RCA5.value());
					
					if(otherVehicle.getSelectedDeductible() != null )
					{
							factorsUnitInstanceToAdd3RCFRA.setValue(otherVehicle.getSelectedDeductible().getWrapperCode().toString());
							unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3RCFRA);
					}
					if(otherVehicle.getSelectedLimit()!= null )
					{
						factorsUnitInstanceToAdd3RCFRA.setValue(otherVehicle.getSelectedLimit().getWrapperCode().toString());
						unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3MASS);
					}
				}
				else if(riskType.equals(EnumRiskType.MOTORCYCLE_FREIGHT_TRANSPORT))
				{
					assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_RCA7.value());
					
					if(otherVehicle.getSelectedDeductible() != null )
					{
							factorsUnitInstanceToAdd3RCFRA.setValue(otherVehicle.getSelectedDeductible().getWrapperCode().toString());
							unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3RCFRA);
					}
					if(otherVehicle.getSelectedLimit()!= null )
					{
						factorsUnitInstanceToAdd3RCFRA.setValue(otherVehicle.getSelectedLimit().getWrapperCode().toString());
						unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3MASS);
					}
				}
				else if(riskType.equals(EnumRiskType.URBAN_BUS))
				{
					assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_RCA8.value());
					
					if(otherVehicle.getSelectedDeductible() != null )
					{
							factorsUnitInstanceToAdd3RCFRA.setValue(otherVehicle.getSelectedDeductible().getWrapperCode().toString());
							unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3RCFRA);
					}
					if(otherVehicle.getSelectedLimit()!= null )
					{
						factorsUnitInstanceToAdd3RCFRA.setValue(otherVehicle.getSelectedLimit().getWrapperCode().toString());
						unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3MASS);
					}
				}
				else if(riskType.equals(EnumRiskType.OUT_OF_TOWN_TURISTIC_BUS))
				{
					assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_RCA9.value());
					
					if(otherVehicle.getSelectedDeductible() != null )
					{
							factorsUnitInstanceToAdd3RCFRA.setValue(otherVehicle.getSelectedDeductible().getWrapperCode().toString());
							unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3RCFRA);
					}
					if(otherVehicle.getSelectedLimit()!= null )
					{
						factorsUnitInstanceToAdd3RCFRA.setValue(otherVehicle.getSelectedLimit().getWrapperCode().toString());
						unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3MASS);
					}
				}
				else if(riskType.equals(EnumRiskType.MOPED))
				{
					assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_RCAR1.value());
					
					if(covTemp.getDeductible() != null && covTemp.getDeductible().getCode() != null)
					{
						factorsUnitInstanceToAdd3RCFRA.setValue(covTemp.getDeductible().getCode());
						unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3RCFRA);
					}
					if(covTemp.getLimit() != null && covTemp.getLimit().getCode() != null)
					{
						factorsUnitInstanceToAdd3MASS.setValue(covTemp.getLimit().getCode());
						unitInstanceToAdd.getFactors().add(factorsUnitInstanceToAdd3MASS);
					}
					
				}
				assetUnitTemp.setSelection((covTemp.getSelected() != null && covTemp.getSelected())  ? tybT : tybF);
				
//				add Unitinstance to AssetUnit for MOTOR_RCA
				assetUnitTemp.getInstances().add(unitInstanceToAdd);
//				add AssetUnit to AssetSection for MOTOR_RCA
				assetSectionResultS1.getUnits().add(assetUnitTemp);
			}
			else if( covTemp != null && covTemp.getCode() != null && covTemp.getCode().equals(EnumCoverageCode.MOTOR_BONUS_PROTECTED))
			{
				assetUnitTemp.setCode(ENUMInternalCodeAssetUnit.CODE_BP1.value());						
				assetUnitTemp.setSelection((covTemp.getSelected() != null && covTemp.getSelected()) ? tybT : tybF);
				
//				add Unitinstance to AssetUnit for MOTOR_BONUS_PROTECTED
				assetUnitTemp.getInstances().add(unitInstanceToAdd);
//				add AssetUnit to AssetSection for MOTOR_BONUS_PROTECTED
				assetSectionResultS1.getUnits().add(assetUnitTemp);
			}
			
		}
//		TODO manca Other vehicle
		
		if(assetSectionResultS1.getUnits().isEmpty())
		{
			assetSectionResultS1 = null;
		}
		
		return assetSectionResultS1;
	}
	
}
