package it.cg.main.conf.mapping.easyway;

import java.util.List;

import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.ICoverage;
import com.mapfre.engines.rating.common.enums.EnumCoverageCode;
import com.mapfre.engines.rating.common.enums.EnumRiskType;
import com.pass.global.TypeBooleano;
import com.pass.global.WsAssetInstance;
import com.pass.global.WsAssetSection;
import com.pass.global.WsAssetUnit;
import com.pass.global.WsFactor;
import com.pass.global.WsUnitInstance;

import it.cg.main.dto.InboundRequestHttpJSON;
import it.cg.main.integration.mapper.enumerations.ENUMInternalCodeSection;
import it.cg.main.integration.mapper.enumerations.ENUMInternalCodeUnit;
import it.cg.main.integration.mapper.enumerations.ENUMInternalUnitInstanceFactors;

public class ExternalCustomMapperToPASSEasy
{
	private TypeBooleano tybT = new TypeBooleano();
	

	/**
	 * Return a list of asset section -> asset unit -> unit instance.
	 * @param inb
	 * @return
	 */
	public void getAssetSections(InboundRequestHttpJSON inb, WsAssetInstance instance, String codeAssetUnit) throws NullPointerException
	{
		EnumRiskType riskType = inb.getInboundQuoteDTO().getContext().getRiskType();
		List<ICoverage> listCov = inb.getInboundQuoteDTO().getCoverages();
		tybT.setBoolean(true);
		
		WsAssetSection assetSectionSx = getS1(listCov, riskType);
		if(assetSectionSx != null)
			instance.getSections().add(assetSectionSx);
		
		assetSectionSx = new WsAssetSection();
		assetSectionSx = getS2(listCov, riskType);
		if(assetSectionSx != null)
			instance.getSections().add(assetSectionSx);
		
		assetSectionSx = new WsAssetSection();
		assetSectionSx = getS3(listCov, riskType);
		if(assetSectionSx != null)
			instance.getSections().add(assetSectionSx);
		
		assetSectionSx = new WsAssetSection();
		assetSectionSx = getS4(listCov, riskType);
		if(assetSectionSx != null)
			instance.getSections().add(assetSectionSx);
		
		assetSectionSx = new WsAssetSection();
		assetSectionSx = getS5(listCov, riskType);
		if(assetSectionSx != null)
			instance.getSections().add(assetSectionSx);
		
		assetSectionSx = new WsAssetSection();
		assetSectionSx = getS6(listCov, riskType);
		if(assetSectionSx != null)
			instance.getSections().add(assetSectionSx);
		
	}
	
	/**
	 * 
	 * @param companyChangeDetails
	 * @return
	 */
	private WsUnitInstance getUnitInstanceGeneric()
	{
		WsUnitInstance unitInstance = new WsUnitInstance();
		
		return unitInstance;
	}

	/**
	 * Set the unitinstance factors only without riskType if is equal
	 * @param unitInstanceToEdit
	 * @param coverage
	 * @param codeAssetUnit
	 */
	private void getFactorsForUnitInstanceNotS1S5(WsUnitInstance unitInstanceToEdit, ICoverage coverage, String codeAssetUnit, EnumRiskType riskType)
	{
//		fattori solo per le giuste asset unit
		
		WsFactor factorToAdd = new WsFactor();
		
		if(codeAssetUnit.equals(ENUMInternalCodeUnit.CODE_AV1.value()) && EnumRiskType.CAR.equals(riskType))
		{
			if(coverage.getCoinsurance() != null )
			{
				if(coverage.getCoinsurance().getCode() != null)
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
		}
		if(codeAssetUnit.equals(ENUMInternalCodeUnit.CODE_COLL1.value()) && EnumRiskType.CAR.equals(riskType))
		{
			if(coverage.getCoinsurance() != null )
			{
				if(coverage.getCoinsurance().getCode() != null)
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
		}
		if(codeAssetUnit.equals(ENUMInternalCodeUnit.CODE_CRI1.value()) && EnumRiskType.CAR.equals(riskType))
		{
			if(coverage.getDeductible() != null )
			{
				if(coverage.getDeductible().getCode() != null)
				{
					factorToAdd  = new WsFactor();
					factorToAdd.setCode(ENUMInternalUnitInstanceFactors.FACTOR_3CRDED.value());
					factorToAdd.setValue(coverage.getDeductible().getCode());
					
					unitInstanceToEdit.getFactors().add(factorToAdd);
				}
			}
			if(coverage.getLimit() != null)
			{
				if(coverage.getLimit().getCode() != null)
				{
					factorToAdd  = new WsFactor();
					factorToAdd.setCode(ENUMInternalUnitInstanceFactors.FACTOR_3CRLMT.value());
					factorToAdd.setValue(coverage.getLimit().getCode());
					
					unitInstanceToEdit.getFactors().add(factorToAdd);
				}
				
			}
		}
		if(codeAssetUnit.equals(ENUMInternalCodeUnit.CODE_EN1.value()) && EnumRiskType.CAR.equals(riskType))
		{
			if(coverage.getCoinsurance() != null)
			{
				if(coverage.getCoinsurance().getCode() != null)
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
	}
	/**
	 * Set the unitinstance factors for all the asset unit
	 * Edit the initinstance 
	 */
	private void getFactorsForUnitInstanceGeneric(WsUnitInstance unitInstanceToEdit, ICoverage coverage)
	{
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
	 * S6 -> with assetUnit and Unitinstance
	 * @param Coverages[]
	 * @param RiskType
	 * @return AssetSection S6 , if no S6 present return null
	 */
	private WsAssetSection getS6(List<ICoverage> listCov, EnumRiskType riskType)
	{
		WsAssetSection resultS6 = new WsAssetSection();
		resultS6.setCode(ENUMInternalCodeSection.CODE_S5.value());
		
		for (ICoverage covTemp : listCov)
		{
			WsAssetUnit assetUnitTemp = new WsAssetUnit();
			WsUnitInstance unitInstanceToAdd = getUnitInstanceGeneric();
			WsFactor unitInstanceFactorToAdd  = new WsFactor();
			getFactorsForUnitInstanceGeneric(unitInstanceToAdd, covTemp);

			if(covTemp.getCode().equals(EnumCoverageCode.MOTOR_VANDALISM))
			{
//			assetunit MOTOR_VANDALISM
				assetUnitTemp.setCode(ENUMInternalCodeUnit.CODE_AV1.value());
				assetUnitTemp.setSelection(tybT);
//				add unitinstance with factors
				getFactorsForUnitInstanceGeneric(unitInstanceToAdd,covTemp);
				getFactorsForUnitInstanceNotS1S5(unitInstanceToAdd, covTemp, ENUMInternalCodeUnit.CODE_AV1.value(), riskType);
				
				resultS6.getUnits().add(assetUnitTemp);
			}
			else if(covTemp.getCode().equals(EnumCoverageCode.MOTOR_COLLISION))
			{
//			assetunit MOTOR_COLLISION
				assetUnitTemp.setCode(ENUMInternalCodeUnit.CODE_COLL1.value());
				assetUnitTemp.setSelection(tybT);
				
				resultS6.getUnits().add(assetUnitTemp);
			}
			else if(covTemp.getCode().equals(EnumCoverageCode.MOTOR_CRYSTALS))
			{
//			assetunit MOTOR_CRYSTALS
				assetUnitTemp.setCode(ENUMInternalCodeUnit.CODE_CRI1.value());
				assetUnitTemp.setSelection(tybT);
				
				resultS6.getUnits().add(assetUnitTemp);
			}
			else if(covTemp.getCode().equals(EnumCoverageCode.MOTOR_NATURAL_EVENTS))
			{
//			assetunit MOTOR_NATURAL_EVENTS
				assetUnitTemp.setCode(ENUMInternalCodeUnit.CODE_EN1.value());
				assetUnitTemp.setSelection(tybT);
				
				resultS6.getUnits().add(assetUnitTemp);
			}
			
			else if(covTemp.getCode().equals(EnumCoverageCode.MOTOR_KASKO))
			{
//			assetunit MOTOR_KASKO
				assetUnitTemp.setCode(ENUMInternalCodeUnit.CODE_KAS1.value());
				assetUnitTemp.setSelection(tybT);

				if(covTemp.getCoinsurance() != null)
				{
					if(covTemp.getCoinsurance().getCode() != null)
					{
						unitInstanceFactorToAdd = new WsFactor();
						
						unitInstanceFactorToAdd.setCode(ENUMInternalUnitInstanceFactors.FACTOR_3FRAKS.value());
						if(riskType.equals(EnumRiskType.CAR))
						{
							String valueCoinsCode = "";
							switch (covTemp.getLimit().getCode())
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
				
				resultS6.getUnits().add(assetUnitTemp);
			}
		}
		
		if(resultS6.getUnits().isEmpty())
		{
			resultS6 = null;
		}
		
		return resultS6;
	}
	
	/**
	 * S5 -> with assetUnit and Unitinstance
	 * @param Coverages[]
	 * @param RiskType
	 * @return AssetSection S5 , if no S5 present return null
	 */
	private WsAssetSection getS5(List<ICoverage> listCov, EnumRiskType riskType)
	{
		WsAssetSection resultS5 = new WsAssetSection();
		resultS5.setCode(ENUMInternalCodeSection.CODE_S5.value());
		
		for (ICoverage covTemp : listCov)
		{
			WsAssetUnit assetUnitTemp = new WsAssetUnit();
			WsUnitInstance unitInstanceToAdd = getUnitInstanceGeneric();
			
			if( (covTemp.getCode().equals(EnumCoverageCode.MOTOR_LEGAL_PROTECTION_BASE) ||
					covTemp.getCode().equals(EnumCoverageCode.MOTOR_LEGAL_PROTECTION_DELUXE)) &&
					(riskType.equals(EnumRiskType.CAR) || riskType.equals(EnumRiskType.MOTORBIKE)) )
			{
				assetUnitTemp.setCode(ENUMInternalCodeUnit.CODE_TG1.value());
				assetUnitTemp.setSelection(tybT);
//				add unitinstace generic plus specific for riskType
				getFactorsForUnitInstanceGeneric(unitInstanceToAdd,covTemp);
				if(riskType.equals(EnumRiskType.MOTORBIKE))
				{
					
				}
				
			}
			else if( (covTemp.getCode().equals(EnumCoverageCode.MOTOR_LEGAL_PROTECTION_BASE) ||
					covTemp.getCode().equals(EnumCoverageCode.MOTOR_LEGAL_PROTECTION_DELUXE)) &&
					riskType.equals(EnumRiskType.MOPED) )
			{
				assetUnitTemp.setCode(ENUMInternalCodeUnit.CODE_TG2.value());
				assetUnitTemp.setSelection(tybT);
			}
		}
		
		if(resultS5.getUnits().isEmpty())
		{
			resultS5 = null;
		}
		
		return resultS5;
	}
	
	/**
	 * S4 -> with assetUnit and Unitinstance
	 * @param Coverages[]
	 * @param RiskType
	 * @return AssetSection S4 , if no S4 present return null
	 */
	private WsAssetSection getS4(List<ICoverage> listCov, EnumRiskType riskType)
	{
		WsAssetSection resultS4 = new WsAssetSection();
		resultS4.setCode(ENUMInternalCodeSection.CODE_S3.value());
		
		for (ICoverage covTemp : listCov)
		{
			WsAssetUnit assetUnitTemp = new WsAssetUnit();
			WsUnitInstance unitInstanceToAdd = getUnitInstanceGeneric();
			
			if( covTemp.getCode().equals(EnumCoverageCode.MOTOR_ROAD_ASSISTANCE_BASE) ||
					covTemp.getCode().equals(EnumCoverageCode.MOTOR_ROAD_ASSISTANCE_DELUXE) )
			{
				assetUnitTemp.setCode(ENUMInternalCodeUnit.CODE_AS1.value());
				assetUnitTemp.setSelection(tybT);
				
				resultS4.getUnits().add(assetUnitTemp);
			}
			
		}
		
		if(resultS4.getUnits().isEmpty())
		{
			resultS4 = null;
		}
		
		return resultS4;
	}
	
	
	/**
	 * S3 -> with assetUnit and Unitinstance
	 * @param Coverages[]
	 * @param RiskType
	 * @return AssetSection S3 , if no S3 present return null
	 */
	private WsAssetSection getS3(List<ICoverage> listCov, EnumRiskType riskType)
	{
		WsAssetSection resultS3 = new WsAssetSection();
		resultS3.setCode(ENUMInternalCodeSection.CODE_S3.value());
		
		for (ICoverage covTemp : listCov)
		{
			WsAssetUnit assetUnitTemp = new WsAssetUnit();
			WsUnitInstance unitInstanceToAdd = getUnitInstanceGeneric();
			WsFactor unitInstanceFactorToAdd  = new WsFactor();
			getFactorsForUnitInstanceGeneric(unitInstanceToAdd, covTemp);
			
			if( covTemp.getCode().equals(EnumCoverageCode.MOTOR_PERMANENT_INVALIDITY_DRIVER) )
			{
				assetUnitTemp.setCode(ENUMInternalCodeUnit.CODE_IIP1.value());
				assetUnitTemp.setSelection(tybT);
				
				if(covTemp.getLimit() != null)
				{
					if(covTemp.getLimit().getCode() != null)
					{
						unitInstanceFactorToAdd = new WsFactor();
						
						unitInstanceFactorToAdd.setCode(ENUMInternalUnitInstanceFactors.FACTOR_3SAINP.value());
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
				}
						
				assetUnitTemp.getInstances().add(unitInstanceToAdd);
				resultS3.getUnits().add(assetUnitTemp);
			}
			else if( covTemp.getCode().equals(EnumCoverageCode.MOTOR_DEATH_DRIVER) )
			{
				assetUnitTemp.setCode(ENUMInternalCodeUnit.CODE_IM1.value());
				assetUnitTemp.setSelection(tybT);
//				TODO da aggiungere una unitinstance personalizzata per caso MORTE CONDUCENTE
				resultS3.getUnits().add(assetUnitTemp);
			}
			
		}
		
		if(resultS3.getUnits().isEmpty())
		{
			resultS3 = null;
		}
		
		return resultS3;
	}
	
	/**
	 * S2 -> with assetUnit and Unitinstance
	 * @param Coverages[]
	 * @param RiskType
	 * @return AssetSection S2 , if no S2 present return null
	 */
	private WsAssetSection getS2(List<ICoverage> listCov, EnumRiskType riskType)
	{
		WsAssetSection resultS2 = new WsAssetSection();
		resultS2.setCode(ENUMInternalCodeSection.CODE_S2.value());
		
		for (ICoverage covTemp : listCov)
		{
			WsAssetUnit assetUnitTemp = new WsAssetUnit();
			WsUnitInstance unitInstanceToAdd = getUnitInstanceGeneric();
			WsFactor unitInstanceFactorToAdd  = new WsFactor();
			getFactorsForUnitInstanceGeneric(unitInstanceToAdd, covTemp);
			
			if( covTemp.getCode().equals(EnumCoverageCode.MOTOR_THEFT) )
			{
				assetUnitTemp.setCode(ENUMInternalCodeUnit.CODE_FUR1.value());
				assetUnitTemp.setSelection(tybT);
				
				if(covTemp.getCoinsurance() != null)
				{
					if(covTemp.getCoinsurance().getCode() != null)
					{
						unitInstanceFactorToAdd = new WsFactor();
						
						unitInstanceFactorToAdd.setCode(ENUMInternalUnitInstanceFactors.FACTOR_3FRSCF.value());
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
//									da avere risposta
//								case "3":
//									break;
								default:
									break;
							}
							unitInstanceFactorToAdd.setValue(valueCoinsCode);
							
							unitInstanceToAdd.getFactors().add(unitInstanceFactorToAdd);
						}
						else if(riskType.equals(EnumRiskType.CAR))
						{
							unitInstanceFactorToAdd = new WsFactor();
							
							unitInstanceFactorToAdd.setCode(ENUMInternalUnitInstanceFactors.FACTOR_3FRSCF.value());
							unitInstanceFactorToAdd.setValue(covTemp.getCoinsurance().getCode());
							
							unitInstanceToAdd.getFactors().add(unitInstanceFactorToAdd);
						}
						
					}
				}
				assetUnitTemp.getInstances().add(unitInstanceToAdd);
				
				resultS2.getUnits().add(assetUnitTemp);
			}
			else if( covTemp.getCode().equals(EnumCoverageCode.MOTOR_FIRE) )
			{
				assetUnitTemp.setCode(ENUMInternalCodeUnit.CODE_INC1.value());
				assetUnitTemp.setSelection(tybT);
//				TODO da aggiungere una unitinstance personalizzata per caso INCENDIO
				assetUnitTemp.getInstances().add(unitInstanceToAdd);
				
				resultS2.getUnits().add(assetUnitTemp);
			}
			
		}
		
		if(resultS2.getUnits().isEmpty())
		{
			resultS2 = null;
		}
		
		return resultS2;
	}
	
	/**
	 * S1 -> with assetUnit and Unitinstance
	 * @param Coverages[]
	 * @param RiskType
	 * @return AssetSection S1 , if no S1 present return null
	 */
	private WsAssetSection getS1(List<ICoverage> listCov, EnumRiskType riskType)
	{
		WsAssetSection resultS1 = new WsAssetSection();
		resultS1.setCode(ENUMInternalCodeSection.CODE_S1.value());
		
		for (ICoverage covTemp : listCov)
		{
			WsAssetUnit assetUnitTemp = new WsAssetUnit();
			WsUnitInstance unitInstanceToAdd = getUnitInstanceGeneric();
			WsFactor unitInstanceFactorToAdd  = new WsFactor();
			getFactorsForUnitInstanceGeneric(unitInstanceToAdd, covTemp);
			
			if( covTemp.getCode().equals(EnumCoverageCode.MOTOR_RCA))
			{
//				CODE Factor for Unitinstance 
				if(covTemp.getDeductible() != null)
				{
					if(covTemp.getDeductible().getCode() != null)
					{
						unitInstanceFactorToAdd.setCode(ENUMInternalUnitInstanceFactors.FACTOR_3RCFRA.value());
					}
				}
				
//				AssetUnit
				if( riskType.equals(EnumRiskType.URBAN_BUS) || riskType.equals(EnumRiskType.OUT_OF_TOWN_TURISTIC_BUS) )
				{
					assetUnitTemp.setCode(ENUMInternalCodeUnit.CODE_RCA11.value());						
				}
				else if(riskType.equals(EnumRiskType.CAR) || riskType.equals(EnumRiskType.MOTORBIKE) || 
						riskType.equals(EnumRiskType.TRUCK_UPTO_60000KG))
				{
					assetUnitTemp.setCode(ENUMInternalCodeUnit.CODE_RCA1.value());
					
//					unitinstance factor
					if(riskType.equals(EnumRiskType.CAR) || riskType.equals(EnumRiskType.MOTORBIKE) )
					{
						if(covTemp.getDeductible() != null)
						{
							if(covTemp.getDeductible().getCode() != null)
							{
								unitInstanceFactorToAdd.setValue(covTemp.getDeductible().getCode());
								unitInstanceToAdd.getFactors().add(unitInstanceFactorToAdd);
							}
						}
					}
				}
				else if(riskType.equals(EnumRiskType.TAXI))
				{
					assetUnitTemp.setCode(ENUMInternalCodeUnit.CODE_RCA12.value());
				}
				else if(riskType.equals(EnumRiskType.SPECIAL_VEHICLE))
				{
					assetUnitTemp.setCode(ENUMInternalCodeUnit.CODE_RCA14.value());
				}
				else if(riskType.equals(EnumRiskType.AGRICULTURAL_MACHINERY))
				{
					assetUnitTemp.setCode(ENUMInternalCodeUnit.CODE_RCA15.value());
				}
				else if(riskType.equals(EnumRiskType.AGRICULTURAL_MACHINERY_TRAILER))
				{
					assetUnitTemp.setCode(ENUMInternalCodeUnit.CODE_RCA16.value());
				}
				else if(riskType.equals(EnumRiskType.SPECIAL_VEHICLE_TRAILER))
				{
					assetUnitTemp.setCode(ENUMInternalCodeUnit.CODE_RCA17.value());
				}
				else if(riskType.equals(EnumRiskType.TRUCK_MORE_THAN_60000KG))
				{
					assetUnitTemp.setCode(ENUMInternalCodeUnit.CODE_RCA2.value());
				}
				else if(riskType.equals(EnumRiskType.TRUCK_TRAILER))
				{
					assetUnitTemp.setCode(ENUMInternalCodeUnit.CODE_RCA3.value());
				}
//				else if(riskType.equals(EnumRiskType.16))
//				{
//					assetUnitTemp.setCode(ENUMInternalCodeUnit.CODE_RCA4.value());
//				}
				else if(riskType.equals(EnumRiskType.CAMPER))
				{
					assetUnitTemp.setCode(ENUMInternalCodeUnit.CODE_RCA5.value());
				}
//				else if(riskType.equals(EnumRiskType.????))
//				{
//					assetUnitTemp.setCode(ENUMInternalCodeUnit.CODE_RCA6.value());
//				}
//				TODO da verificare perchè in rosso su excel
				else if(riskType.equals(EnumRiskType.MOTORCYCLE_FREIGHT_TRANSPORT))
				{
					assetUnitTemp.setCode(ENUMInternalCodeUnit.CODE_RCA7.value());
				}
				else if(riskType.equals(EnumRiskType.URBAN_BUS))
				{
					assetUnitTemp.setCode(ENUMInternalCodeUnit.CODE_RCA8.value());
				}
				else if(riskType.equals(EnumRiskType.OUT_OF_TOWN_TURISTIC_BUS))
				{
					assetUnitTemp.setCode(ENUMInternalCodeUnit.CODE_RCA9.value());
				}
//				non chiaro cosa mettere da excel
//				else if(riskType.equals(EnumRiskType.))
//				{
//					assetUnitTemp.setCode(ENUMInternalCodeUnit.CODE_RCAR1.value());
//				}
				assetUnitTemp.setSelection(tybT);
				
				resultS1.getUnits().add(assetUnitTemp);
			}
			else if(covTemp.getCode().equals(EnumCoverageCode.MOTOR_BONUS_PROTECTED))
			{
				assetUnitTemp.setCode(ENUMInternalCodeUnit.CODE_BP1.value());						
				assetUnitTemp.setSelection(tybT);
				
				resultS1.getUnits().add(assetUnitTemp);
			}
			
		}
		
		if(resultS1.getUnits().isEmpty())
		{
			resultS1 = null;
		}
		
		return resultS1;
	}
	
}
