package it.cg.main.conf.mapping.easyway;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import com.pass.global.WsAsset;
import com.pass.global.WsAssetInstance;
import com.pass.global.WsAssetSection;
import com.pass.global.WsAssetUnit;
import com.pass.global.WsCalculatePremiumInput;
import com.pass.global.WsUnitInstance;

import it.cg.main.dto.InboundRequestHttpJSON;
import it.cg.main.dto.inbound.InboundQuoteDTO;



@Mapper(uses=ExternalCustomMapper.class)
public interface EasyMapperMapstruct
{
	
	@Mappings({
		@Mapping(source="quoteMode", target = "quoteMode"),
		@Mapping(source="adaptToMinimumPremium", target = "adaptToMinimumPremium"),
		@Mapping(source="applyDiscount", target = "applyDiscount"),
		@Mapping(source="inboundQuoteDTO", target = "product"),//In questa riga mappo il Code
//		@Mapping(source="inboundQuoteDTO.rateFromDate", target = "product.openDate"), 
//		@Mapping(source = "inboundQuoteDTO.installments", target = "product.paymentFrequencyCode"),
//		@Mapping(source = "inboundQuoteDTO.context.flowType", target = "product.operationCode")
		})
	void quoteDtoToProduct(InboundRequestHttpJSON inbound, @MappingTarget WsCalculatePremiumInput wsCalculate);
	
	@Mappings({
		@Mapping(source="inboundQuoteDTO", target = "instances"),
		@Mapping(source="inboundQuoteDTO.context.riskType", target = "code"),//In questa riga mappo il code dell'asset relativo al WsProduct
		@Mapping(source="inbound", target = "vehicles"),
	})
	WsAsset quoteDtoToAsset(InboundRequestHttpJSON inbound);
	
	
	@Mappings({
		@Mapping(source="codeAssetSection", target = "code")
	})
	WsAssetSection quoteDtoToAssetSection(InboundRequestHttpJSON inbound);
	
	@Mappings({
		@Mapping(source="codeAssetUnit", target = "code"),
		@Mapping(source="selectionAssetUnit", target = "selection")
	})
	void inboundToUnit(InboundRequestHttpJSON inbound, @MappingTarget WsAssetUnit unit);
	
	
	@Mappings({
		@Mapping(source="inbound", target = "clauses"),
		@Mapping(source="inboundQuoteDTO", target = "factors")
	})
	WsUnitInstance quoteDtoToUnitInst(InboundRequestHttpJSON inbound);
	
	
//	@Mappings({
////		@Mapping(target = "factors", qualifiedBy = { MapperFactor.class, FactorAssetInstance.class })
//		@Mapping(source = "inboundQuoteDTO", target = "factors")
//		})
//	WsAssetInstance quoteDtoToAssetInstance(InboundRequestHttpJSON assetIn);
//	
//	
//	@Mappings({
////		@Mapping(target = "factors", qualifiedBy = { MapperFactor.class, FactorUnitInstance.class })
//		@Mapping(source = "inboundQuoteDTO", target = "factors")
//		})
//	WsUnitInstance quoteDtoToUnitInstance(InboundRequestHttpJSON assetIn);
	

//	@MapperFactor
//	public class CustomMapperInbound
//	{
//		@Qualifier
//		@Target(ElementType.TYPE)
//		@Retention(RetentionPolicy.CLASS)
//		public @interface MapperFactor{}
////
//		@Qualifier
//		@Target(ElementType.METHOD)
//		@Retention(RetentionPolicy.CLASS)
//		public @interface FactorProduct{}
//		
//		@Qualifier
//		@Target(ElementType.METHOD)
//		@Retention(RetentionPolicy.CLASS)
//		public @interface FactorAssetInstance{}
//		
//		@Qualifier
//		@Target(ElementType.METHOD)
//		@Retention(RetentionPolicy.CLASS)
//		public @interface FactorUnitInstance{}
		
		/**
		 * Populate ONLY list factors for wsproduct
		 * @param quote
		 * @return
		 */
		
//		@FactorProduct
//		public List<WsFactor> quoteToListWsFactor(InboundQuoteDTO quote)
//		{
////			mappin ma = new mappin();
//			
//			ArrayList<WsFactor> factProp = new ArrayList<WsFactor>();
//			
//			WsFactor wsFactor = new WsFactor();
//			
////			factProp = (ArrayList<WsFactor>) quote.getFactors();
//			
//			
//			if(quote.getAffinity() != null)
//			{
//				wsFactor.setCode(WsProductFactorsENUM.FACTOR_1AFF.value());
//				wsFactor.setValue(quote.getAffinity());
//				factProp.add(wsFactor);
//			}
//			
//			if(quote.getNumberOfClaimsInLastYear() != null)
//			{
//				wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_SXTOT.value());
//				wsFactor.setValue(quote.getNumberOfClaimsInLastYear().toString());
//				factProp.add(wsFactor);
//			}
//			
//			if(quote.getEffectiveDate() != null)
//			{
//				wsFactor.setCode(WsProductFactorsENUM.FACTOR__1PEFF.value());
//				wsFactor.setValue("");	//DA MODIFICARE
//				factProp.add(wsFactor);
//			}
//			if(quote.getVehicle().getVehicleAge() != null)
//			{
//				wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2ETAV.value());
//				wsFactor.setValue(quote.getVehicle().getVehicleAge().toString());
//				factProp.add(wsFactor);
//			}
//			if(quote.getVehicle().getHabitualUse() != null)
//			{
//				wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2USOVE.value());
//				wsFactor.setValue(quote.getVehicle().getHabitualUse());
//				factProp.add(wsFactor);
//			}
//			if(quote.getVehicle().getHabitualUse() != null)
//			{
//				wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2USOVE.value());
//				wsFactor.setValue(quote.getVehicle().getHabitualUse());
//				factProp.add(wsFactor);
//			}
//			if(quote.getVehicle().getCarAgeAtPurchase() != null)
//			{
//				wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2EVACQ.value());
//				wsFactor.setValue(quote.getVehicle().getCarAgeAtPurchase().toString());
//				factProp.add(wsFactor);
//			}
//			if(quote.getCoverages().getLimit().getCode() != null)
//			{
//				wsFactor.setCode(UnitInstanceFactorsENUM.FACTOR_3CRLMT.value());
//				wsFactor.setValue(quote.getCoverages().getLimit().getCode());
//				factProp.add(wsFactor);
//			}
//			if(quote.getCoverages().getDeductible().getCode() != null)
//			{
//				wsFactor.setCode(UnitInstanceFactorsENUM.FACTOR_3CRDED.value());
//				wsFactor.setValue(quote.getCoverages().getDeductible().getCode());
//				factProp.add(wsFactor);
//			}
//			//CONTROLLARE A CHE FATTORE METTERLO
//			if(quote.getFigures().getAge() != null)
//			{
//				wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2RD1ET.value());
//				wsFactor.setValue(quote.getFigures().getAge().toString());
//				factProp.add(wsFactor);
//			}
//			//CONTROLLARE A CHE FATTORE METTERLO
//			if(quote.getFigures().getResidenceAddress().getCap() != null)
//			{
//				wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2RD2CA.value());
//				wsFactor.setValue(quote.getFigures().getResidenceAddress().getCap());
//				factProp.add(wsFactor);
//			}
//			if(quote.getFigures().getYearsWithLicence() != null)
//			{
//				wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2MDAP.value());
//				wsFactor.setValue(quote.getFigures().getYearsWithLicence().toString());
//				factProp.add(wsFactor);
//			}
//			if(quote.getNumberOfVehiclesOwned() != null)
//			{
//				wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2ANUCF.value());
//				wsFactor.setValue(quote.getNumberOfVehiclesOwned().toString());
//				factProp.add(wsFactor);
//			}
//			if(quote.getRenewalYears() != null)
//			{
//				wsFactor.setCode(WsProductFactorsENUM.FACTOR_1ANRIN.value());
//				wsFactor.setValue(quote.getRenewalYears().toString());
//				factProp.add(wsFactor);
//			}
//			if(quote.getRatingInfo().getUwClass() != null)
//			{
//				wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2CLUWR.value());
//				wsFactor.setValue(quote.getRatingInfo().getUwClass());
//				factProp.add(wsFactor);
//			}
//			if(quote.getGoodDriverClass() != null)
//			{
//				wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR__2BM.value());
//				wsFactor.setValue(quote.getGoodDriverClass());
//				factProp.add(wsFactor);
//			}
//			if(quote.getInnerClass() != null)
//			{
//				wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR__2CU.value());
//				wsFactor.setValue(quote.getInnerClass());
//				factProp.add(wsFactor);
//			}
//			if(quote.getDriverNumber() != null)
//			{
//				wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2NDRIV.value());
//				wsFactor.setValue(quote.getDriverNumber().toString());
//				factProp.add(wsFactor);
//			}
//			if(quote.getClean1() != null)
//			{
//				wsFactor.setCode(UnitInstanceFactorsENUM.FACTOR_3CLIN1.value());
//				wsFactor.setValue(quote.getClean1().toString());
//				factProp.add(wsFactor);
//			}
//			
//			return factProp;
//			
//			
//		}
		
//		
//		@FactorAssetInstance
//		public List<WsFactor> quoteToListWsFactorAssetInstance(InboundQuoteDTO quote)
//		{
//		ArrayList<WsFactor> factProp = new ArrayList<WsFactor>();
//		
//		WsFactor wsFactor = new WsFactor();
//		
//		
//		if(quote.getNumberOfClaimsInLastYear() != null)
//		{
//			wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_SXTOT.value());
//			wsFactor.setValue(quote.getNumberOfClaimsInLastYear().toString());
//			factProp.add(wsFactor);
//		}
//		
//		if(quote.getVehicle().getVehicleAge() != null)
//		{
//			wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2ETAV.value());
//			wsFactor.setValue(quote.getVehicle().getVehicleAge().toString());
//			factProp.add(wsFactor);
//		}
//		if(quote.getVehicle().getHabitualUse() != null)
//		{
//			wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2USOVE.value());
//			wsFactor.setValue(quote.getVehicle().getHabitualUse());
//			factProp.add(wsFactor);
//		}
//		if(quote.getVehicle().getHabitualUse() != null)
//		{
//			wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2USOVE.value());
//			wsFactor.setValue(quote.getVehicle().getHabitualUse());
//			factProp.add(wsFactor);
//		}
//		if(quote.getVehicle().getCarAgeAtPurchase() != null)
//		{
//			wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2EVACQ.value());
//			wsFactor.setValue(quote.getVehicle().getCarAgeAtPurchase().toString());
//			factProp.add(wsFactor);
//		}
//		
//		
//		//CONTROLLARE A CHE FATTORE METTERLO
//		if(quote.getFigures().getAge() != null)
//		{
//			wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2RD1ET.value());
//			wsFactor.setValue(quote.getFigures().getAge().toString());
//			factProp.add(wsFactor);
//		}
//		//CONTROLLARE A CHE FATTORE METTERLO
//		if(quote.getFigures().getResidenceAddress().getCap() != null)
//		{
//			wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2RD2CA.value());
//			wsFactor.setValue(quote.getFigures().getResidenceAddress().getCap());
//			factProp.add(wsFactor);
//		}
//		if(quote.getFigures().getYearsWithLicence() != null)
//		{
//			wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2MDAP.value());
//			wsFactor.setValue(quote.getFigures().getYearsWithLicence().toString());
//			factProp.add(wsFactor);
//		}
//		if(quote.getNumberOfVehiclesOwned() != null)
//		{
//			wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2ANUCF.value());
//			wsFactor.setValue(quote.getNumberOfVehiclesOwned().toString());
//			factProp.add(wsFactor);
//		}
//		
//		if(quote.getRatingInfo().getUwClass() != null)
//		{
//			wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2CLUWR.value());
//			wsFactor.setValue(quote.getRatingInfo().getUwClass());
//			factProp.add(wsFactor);
//		}
//		if(quote.getGoodDriverClass() != null)
//		{
//			wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR__2BM.value());
//			wsFactor.setValue(quote.getGoodDriverClass());
//			factProp.add(wsFactor);
//		}
//		if(quote.getInnerClass() != null)
//		{
//			wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR__2CU.value());
//			wsFactor.setValue(quote.getInnerClass());
//			factProp.add(wsFactor);
//		}
//		if(quote.getDriverNumber() != null)
//		{
//			wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2NDRIV.value());
//			wsFactor.setValue(quote.getDriverNumber().toString());
//			factProp.add(wsFactor);
//		}
//		
//		return factProp;
//		}
//		
//		@FactorUnitInstance
//		public List<WsFactor> quoteToListWsFactorUnitInstance(InboundQuoteDTO quote)
//		{
//			
//			ArrayList<WsFactor> factProp = new ArrayList<WsFactor>();
//			
//			WsFactor wsFactor = new WsFactor();
//			
//			if(quote.getCoverages().getLimit().getCode() != null)
//			{
//				wsFactor.setCode(UnitInstanceFactorsENUM.FACTOR_3CRLMT.value());
//				wsFactor.setValue(quote.getCoverages().getLimit().getCode());
//				factProp.add(wsFactor);
//			}
//			if(quote.getCoverages().getDeductible().getCode() != null)
//			{
//				wsFactor.setCode(UnitInstanceFactorsENUM.FACTOR_3CRDED.value());
//				wsFactor.setValue(quote.getCoverages().getDeductible().getCode());
//				factProp.add(wsFactor);
//			}
//			
//			if(quote.getClean1() != null)
//			{
//				wsFactor.setCode(UnitInstanceFactorsENUM.FACTOR_3CLIN1.value());
//				wsFactor.setValue(quote.getClean1().toString());
//				factProp.add(wsFactor);
//			}
//			
//			return factProp;
//		
//		}
		
		
		
	
	//-------------PROVARE SU QUESTA STRADA--------------
	
//	@ValueMappings({
//		@ValueMapping(source = "affinity", target = "AssetInstanceFactors.ENUM.FACTOR_2ALLR1" )
//		})
//	WsFactor quoteDtoToFactor(InboundQuoteDTO quote);
//	
//	@ValueMappings({
//		@ValueMapping(source = "quote.vehicle.purchaseYear", target = "WsProductFactorsENUM.FACTOR__1CETA")
//		})
//	WsFactor quotDtoToFactor(InboundQuoteDTO quote);

}
//}

