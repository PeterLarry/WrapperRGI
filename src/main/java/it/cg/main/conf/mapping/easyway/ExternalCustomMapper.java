package it.cg.main.conf.mapping.easyway;

import java.util.ArrayList;
import java.util.List;

//import javax.inject.Qualifier;
import org.springframework.stereotype.Service;

import com.pass.global.TypeBooleano;
import com.pass.global.WsAsset;
import com.pass.global.WsAssetInstance;
import com.pass.global.WsAssetSection;
import com.pass.global.WsAssetUnit;
import com.pass.global.WsCalculatePremiumInput;
import com.pass.global.WsFactor;
import com.pass.global.WsPremiumGroup;
import com.pass.global.WsProduct;
import com.pass.global.WsVehicle;

import it.cg.main.dto.inbound.InboundPremiumDTO;
import it.cg.main.dto.inbound.InboundQuoteDTO;
import it.cg.main.dto.inbound.InboundVehicleDTO;
import it.cg.main.integration.mapper.enumerations.AssetInstanceFactorsENUM;
import it.cg.main.integration.mapper.enumerations.UnitInstanceFactorsENUM;
import it.cg.main.integration.mapper.enumerations.WsProductFactorsENUM;

@Service
public class ExternalCustomMapper
{
	
	public WsProduct quoteToListWsFactor(InboundQuoteDTO quote)
	{

		WsProduct prod = new WsProduct();
		
		ArrayList<WsFactor> factProp = new ArrayList<WsFactor>();
		ArrayList<WsFactor> factInst = new ArrayList<WsFactor>();
		ArrayList<WsFactor> factUnit = new ArrayList<WsFactor>();
		
		

		
		WsFactor wsFactor = new WsFactor();
		
//		factProp = (ArrayList<WsFactor>) quote.getFactors();
		
		
		if(quote.getAffinity() != null)
		{
			wsFactor.setCode(WsProductFactorsENUM.FACTOR_1AFF.value());
			wsFactor.setValue(quote.getAffinity());
			factProp.add(wsFactor);
		}
		
		if(quote.getNumberOfClaimsInLastYear() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_SXTOT.value());
			wsFactor.setValue(quote.getNumberOfClaimsInLastYear().toString());
			factProp.add(wsFactor);
		}
		
		if(quote.getEffectiveDate() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(WsProductFactorsENUM.FACTOR__1PEFF.value());
			wsFactor.setValue("");	//DA MODIFICARE
			factProp.add(wsFactor);
		}
		if(quote.getVehicle().getVehicleAge() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2ETAV.value());
			wsFactor.setValue(quote.getVehicle().getVehicleAge().toString());
			factProp.add(wsFactor);
		}
		
		if(quote.getVehicle().getHabitualUse() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2USOVE.value());
			wsFactor.setValue(quote.getVehicle().getHabitualUse());
			factProp.add(wsFactor);
		}
		
		if(quote.getVehicle().getHabitualUse() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2USOVE.value());
			wsFactor.setValue(quote.getVehicle().getHabitualUse());
			factProp.add(wsFactor);
		}
		
		if(quote.getVehicle().getCarAgeAtPurchase() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2EVACQ.value());
			wsFactor.setValue(quote.getVehicle().getCarAgeAtPurchase().toString());
			factProp.add(wsFactor);
		}
		
		if(quote.getCoverages().getLimit().getCode() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(UnitInstanceFactorsENUM.FACTOR_3CRLMT.value());
			wsFactor.setValue(quote.getCoverages().getLimit().getCode());
			factProp.add(wsFactor);
		}
		
		if(quote.getCoverages().getDeductible().getCode() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(UnitInstanceFactorsENUM.FACTOR_3CRDED.value());
			wsFactor.setValue(quote.getCoverages().getDeductible().getCode());
			factProp.add(wsFactor);
		}
		//CONTROLLARE A CHE FATTORE METTERLO
		if(quote.getFigures().getAge() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2RD1ET.value());
			wsFactor.setValue(quote.getFigures().getAge().toString());
			factProp.add(wsFactor);
		}
		//CONTROLLARE A CHE FATTORE METTERLO
		if(quote.getFigures().getResidenceAddress().getCap() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2RD2CA.value());
			wsFactor.setValue(quote.getFigures().getResidenceAddress().getCap());
			factProp.add(wsFactor);
		}
		
		if(quote.getFigures().getYearsWithLicence() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2MDAP.value());
			wsFactor.setValue(quote.getFigures().getYearsWithLicence().toString());
			factProp.add(wsFactor);
		}
		
		if(quote.getNumberOfVehiclesOwned() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2ANUCF.value());
			wsFactor.setValue(quote.getNumberOfVehiclesOwned().toString());
			factProp.add(wsFactor);
		}
		
		if(quote.getRenewalYears() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(WsProductFactorsENUM.FACTOR_1ANRIN.value());
			wsFactor.setValue(quote.getRenewalYears().toString());
			factProp.add(wsFactor);
		}
		
		if(quote.getRatingInfo().getUwClass() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2CLUWR.value());
			wsFactor.setValue(quote.getRatingInfo().getUwClass());
			factProp.add(wsFactor);
		}
		
		if(quote.getGoodDriverClass() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR__2BM.value());
			wsFactor.setValue(quote.getGoodDriverClass());
			factProp.add(wsFactor);
		}
		
		if(quote.getInnerClass() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR__2CU.value());
			wsFactor.setValue(quote.getInnerClass());
			factProp.add(wsFactor);
		}
		
		if(quote.getDriverNumber() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2NDRIV.value());
			wsFactor.setValue(quote.getDriverNumber().toString());
			factProp.add(wsFactor);
		}
		
		if(quote.getClean1() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(UnitInstanceFactorsENUM.FACTOR_3CLIN1.value());
			wsFactor.setValue(quote.getClean1().toString());
			factProp.add(wsFactor);
		}
		
//		return factProp;
		
		
//	}
	
//	public List<WsFactor> quoteToListWsFactorAssetInstance(InboundQuoteDTO quote)
//	{
//	ArrayList<WsFactor> factProp = new ArrayList<WsFactor>();
	
//	WsFactor wsFactor = new WsFactor();
	
	
	if(quote.getNumberOfClaimsInLastYear() != null)
	{
//		wsFactor = new WsFactor();
		wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_SXTOT.value());
		wsFactor.setValue(quote.getNumberOfClaimsInLastYear().toString());
		factInst.add(wsFactor);
	}
	
	if(quote.getVehicle().getVehicleAge() != null)
	{
		wsFactor = new WsFactor();
		wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2ETAV.value());
		wsFactor.setValue(quote.getVehicle().getVehicleAge().toString());
		factInst.add(wsFactor);
	}
	
	if(quote.getVehicle().getHabitualUse() != null)
	{
		wsFactor = new WsFactor();
		wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2USOVE.value());
		wsFactor.setValue(quote.getVehicle().getHabitualUse());
		factInst.add(wsFactor);
	}
	
	if(quote.getVehicle().getHabitualUse() != null)
	{
		wsFactor = new WsFactor();
		wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2USOVE.value());
		wsFactor.setValue(quote.getVehicle().getHabitualUse());
		factInst.add(wsFactor);
	}
	
	if(quote.getVehicle().getCarAgeAtPurchase() != null)
	{
		wsFactor = new WsFactor();
		wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2EVACQ.value());
		wsFactor.setValue(quote.getVehicle().getCarAgeAtPurchase().toString());
		factInst.add(wsFactor);
	}
	
	
	//CONTROLLARE A CHE FATTORE METTERLO
	if(quote.getFigures().getAge() != null)
	{
		wsFactor = new WsFactor();
		wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2RD1ET.value());
		wsFactor.setValue(quote.getFigures().getAge().toString());
		factInst.add(wsFactor);
	}
	//CONTROLLARE A CHE FATTORE METTERLO
	if(quote.getFigures().getResidenceAddress().getCap() != null)
	{
		wsFactor = new WsFactor();
		wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2RD2CA.value());
		wsFactor.setValue(quote.getFigures().getResidenceAddress().getCap());
		factInst.add(wsFactor);
	}
	
	if(quote.getFigures().getYearsWithLicence() != null)
	{
		wsFactor = new WsFactor();
		wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2MDAP.value());
		wsFactor.setValue(quote.getFigures().getYearsWithLicence().toString());
		factInst.add(wsFactor);
	}
	
	if(quote.getNumberOfVehiclesOwned() != null)
	{
		wsFactor = new WsFactor();
		wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2ANUCF.value());
		wsFactor.setValue(quote.getNumberOfVehiclesOwned().toString());
		factInst.add(wsFactor);
	}
	
	if(quote.getRatingInfo().getUwClass() != null)
	{
		wsFactor = new WsFactor();
		wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2CLUWR.value());
		wsFactor.setValue(quote.getRatingInfo().getUwClass());
		factInst.add(wsFactor);
	}
	
	if(quote.getGoodDriverClass() != null)
	{
		wsFactor = new WsFactor();
		wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR__2BM.value());
		wsFactor.setValue(quote.getGoodDriverClass());
		factInst.add(wsFactor);
	}
	
	if(quote.getInnerClass() != null)
	{
		wsFactor = new WsFactor();
		wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR__2CU.value());
		wsFactor.setValue(quote.getInnerClass());
		factInst.add(wsFactor);
	}
	
	if(quote.getDriverNumber() != null)
	{
		wsFactor = new WsFactor();
		wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2NDRIV.value());
		wsFactor.setValue(quote.getDriverNumber().toString());
		factInst.add(wsFactor);
	}
	
//	return factProp;
//	}
	
	
//	public List<WsFactor> quoteToListWsFactorUnitInstance(InboundQuoteDTO quote)
//	{
		
//		ArrayList<WsFactor> factProp = new ArrayList<WsFactor>();
		
//		WsFactor wsFactor = new WsFactor();
		
		if(quote.getCoverages().getLimit().getCode() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(UnitInstanceFactorsENUM.FACTOR_3CRLMT.value());
			wsFactor.setValue(quote.getCoverages().getLimit().getCode());
			factProp.add(wsFactor);
		}
		
		if(quote.getCoverages().getDeductible().getCode() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(UnitInstanceFactorsENUM.FACTOR_3CRDED.value());
			wsFactor.setValue(quote.getCoverages().getDeductible().getCode());
			factProp.add(wsFactor);
		}
		
		if(quote.getClean1() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(UnitInstanceFactorsENUM.FACTOR_3CLIN1.value());
			wsFactor.setValue(quote.getClean1().toString());
			factProp.add(wsFactor);
		}
		
		prod.getFactors().addAll(factProp);
		prod.getAssets().add(new WsAsset());
		prod.getAssets().get(0).getInstances().add(new WsAssetInstance());
		prod.getAssets().get(0).getInstances().get(0).getFactors().addAll(factInst);
		
		prod.getAssets().get(0).getInstances().get(0).getSections().add(new WsAssetSection());
		prod.getAssets().get(0).getInstances().get(0).getSections().get(0).getUnits().add(new WsAssetUnit());
		prod.getAssets().get(0).getInstances().get(0).getSections().get(0).getUnits().get(0).getFactors().addAll(factUnit);
		
		return prod;
	
	}
	
	
	public TypeBooleano boolToTypeBool(Boolean bool) {
		
		TypeBooleano typeB = new TypeBooleano();
		
		typeB.setBoolean(bool);
		
		return typeB;
	}
	

	public List<WsAsset> riskToWsAssetCode(InboundQuoteDTO quote)
	{
		ArrayList<WsAsset> assetProp = new ArrayList<WsAsset>();
		
		WsAsset wsAsset = new WsAsset();
				
		wsAsset.setCode(quote.getContext().getRisktype());
		
		assetProp.add(wsAsset);
		
		return assetProp;
			
	}
	

	
	public WsPremiumGroup changeInstance (InboundPremiumDTO pre){
		
		return new WsPremiumGroup();
	}
	
	public WsVehicle changeInstance (InboundVehicleDTO vehicle){
		
		return new WsVehicle();
	}

}
