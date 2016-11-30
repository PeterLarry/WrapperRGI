package it.cg.main.conf.mapping.easyway;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

//import javax.inject.Qualifier;
import org.springframework.stereotype.Service;

import com.pass.global.TypeBooleano;
import com.pass.global.TypeData;
import com.pass.global.WsAssetInstance;
import com.pass.global.WsAssetUnit;
import com.pass.global.WsClause;
import com.pass.global.WsFactor;
import com.pass.global.WsPremiumGroup;
import com.pass.global.WsProduct;
import com.pass.global.WsVehicle;

import it.cg.main.dto.InboundRequestHttpJSON;
import it.cg.main.dto.inbound.InboundPremiumDTO;
import it.cg.main.dto.inbound.InboundQuoteDTO;
import it.cg.main.dto.inbound.InboundVehicleDTO;
import it.cg.main.integration.mapper.enumerations.AssetInstanceFactorsENUM;
import it.cg.main.integration.mapper.enumerations.CodeProductENUM;
import it.cg.main.integration.mapper.enumerations.UnitInstanceFactorsENUM;
import it.cg.main.integration.mapper.enumerations.WsProductFactorsENUM;

@Service
public class ExternalCustomMapper
{
	
	public WsProduct quoteToListWsProduct(InboundQuoteDTO quote)
	{
		WsProduct prod = new WsProduct();
		
		prod.setOpenDate(dataToTypeData(quote.getRateFromDate()));
		
		if(quote.getInstallments()==1){
			
			prod.setPaymentFrequencyCode("000001");
			
		}
		prod.setOperationCode(quote.getContext().getFlowType());
		
		ArrayList<WsFactor> factProp = new ArrayList<WsFactor>();
		
		WsFactor wsFactor = new WsFactor();
			
		if(quote.getAffinity() != null)
		{
			wsFactor.setCode(WsProductFactorsENUM.FACTOR_1AFF.value());
			wsFactor.setValue(quote.getAffinity());
			factProp.add(wsFactor);
		}
	
		if(quote.getEffectiveDate() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(WsProductFactorsENUM.FACTOR__1PEFF.value());
			wsFactor.setValue("");	//DA MODIFICARE
			factProp.add(wsFactor);
		}

		if(quote.getRenewalYears() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(WsProductFactorsENUM.FACTOR_1ANRIN.value());
			wsFactor.setValue(quote.getRenewalYears().toString());
			factProp.add(wsFactor);
		}
		
		
//		----------------------
//		case AUTO DLI
		if(quote.getContext().getRiskType() != null)
		{
			if(quote.getContext().getRiskType().equalsIgnoreCase("000001")&&
			   quote.getContext().getProductType().equalsIgnoreCase("DLI"))
			{	
				prod.setCode(CodeProductENUM.CODE_AUTODLI.value());
			}
			else if(quote.getContext().getProductType().equalsIgnoreCase("DLI") &&
				  ( quote.getContext().getRiskType().equalsIgnoreCase("000003")||
					quote.getContext().getRiskType().equalsIgnoreCase("000004"))  )
			{
				prod.setCode(CodeProductENUM.CODE_MOTOCICLODLI.value());
			}
			else if(quote.getContext().getRiskType().equalsIgnoreCase("000006")&&
					quote.getContext().getProductType().equalsIgnoreCase("DLI"))//cambiare la stringa
			{
				prod.setCode(CodeProductENUM.CODE_AUTODLSS.value());
			}
			else if ((quote.getContext().getProductType().equalsIgnoreCase("ADI"))&&
					   quote.getContext().getRiskType().equalsIgnoreCase("000001"))
			{
					
				prod.setCode(CodeProductENUM.CODE_AUTOADI.value());
				
			}
			else if ( ( !quote.getContext().getRiskType().equalsIgnoreCase("000001") )&&
					  ( !quote.getContext().getRiskType().equalsIgnoreCase("000003") )&&
					  ( !quote.getContext().getRiskType().equalsIgnoreCase("000004") )&&
					    quote.getContext().getProductType().equalsIgnoreCase("DLI")  )
			{
					
				prod.setCode(CodeProductENUM.CODE_ALTRIVEICOLIDLI.value());
			}
		}
		prod.getFactors().addAll(factProp);
		
		return prod;
	}
	
	
	public TypeBooleano boolToTypeBool(Boolean bool) {
		
		TypeBooleano typeB = new TypeBooleano();
		
		typeB.setBoolean(bool);
		
		return typeB;
	}
	
	public TypeData dataToTypeData(Date data) {
		
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
	
	
	
	public List<WsAssetInstance> quoteToWsAsset(InboundQuoteDTO quote)
	{
		ArrayList<WsAssetInstance> assetInst = new ArrayList<WsAssetInstance>();
		assetInst.add(new WsAssetInstance());
		ArrayList<WsFactor> factAsset= new ArrayList<WsFactor>();
		WsFactor wsFactor = new WsFactor();
		
		if(quote.getNumberOfClaimsInLastYear() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_SXTOT.value());
			wsFactor.setValue(quote.getNumberOfClaimsInLastYear().toString());
			factAsset.add(wsFactor);
		}
		if(quote.getVehicle().getVehicleAge() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2ETAV.value());
			wsFactor.setValue(quote.getVehicle().getVehicleAge().toString());
			factAsset.add(wsFactor);
		}
		
		if(quote.getVehicle().getHabitualUse() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2USOVE.value());
			wsFactor.setValue(quote.getVehicle().getHabitualUse());
			factAsset.add(wsFactor);
		}
		
		if(quote.getVehicle().getCarAgeAtPurchase() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2EVACQ.value());
			wsFactor.setValue(quote.getVehicle().getCarAgeAtPurchase().toString());
			factAsset.add(wsFactor);
		}
		//CONTROLLARE A CHE FATTORE METTERLO
		if(quote.getFigures().getAge() != null)
		{
					wsFactor = new WsFactor();
					wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2RD1ET.value());
					wsFactor.setValue(quote.getFigures().getAge().toString());
					factAsset.add(wsFactor);
		}
				//CONTROLLARE A CHE FATTORE METTERLO
		if(quote.getFigures().getResidenceAddress().getCap() != null)
		{
					wsFactor = new WsFactor();
					wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2RD2CA.value());
					wsFactor.setValue(quote.getFigures().getResidenceAddress().getCap());
					factAsset.add(wsFactor);
		}
				
		if(quote.getFigures().getYearsWithLicence() != null)
		{
					wsFactor = new WsFactor();
					wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2MDAP.value());
					wsFactor.setValue(quote.getFigures().getYearsWithLicence().toString());
					factAsset.add(wsFactor);
		}
				
		if(quote.getNumberOfVehiclesOwned() != null)
		{
					wsFactor = new WsFactor();
					wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2ANUCF.value());
					wsFactor.setValue(quote.getNumberOfVehiclesOwned().toString());
					factAsset.add(wsFactor);
		}
		if(quote.getRatingInfo().getUwClass() != null)
		{
					wsFactor = new WsFactor();
					wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2CLUWR.value());
					wsFactor.setValue(quote.getRatingInfo().getUwClass());
					factAsset.add(wsFactor);
		}
				
		if(quote.getGoodDriverClass() != null)
		{
					wsFactor = new WsFactor();
					wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR__2BM.value());
					wsFactor.setValue(quote.getGoodDriverClass());
					factAsset.add(wsFactor);
		}
				
		if(quote.getInnerClass() != null)
		{
					wsFactor = new WsFactor();
					wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR__2CU.value());
					wsFactor.setValue(quote.getInnerClass());
					factAsset.add(wsFactor);
		}
				
		if(quote.getDriverNumber() != null)
		{
					wsFactor = new WsFactor();
					wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2NDRIV.value());
					wsFactor.setValue(quote.getDriverNumber().toString());
					factAsset.add(wsFactor);
		}
		if(quote.getNumberOfClaimsInLastYear() != null)
		{
					wsFactor = new WsFactor();
					wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_SXTOT.value());
					wsFactor.setValue(quote.getNumberOfClaimsInLastYear().toString());
					factAsset.add(wsFactor);
		}
				
		if(quote.getVehicle().getVehicleAge() != null)
		{
					wsFactor = new WsFactor();
					wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2ETAV.value());
					wsFactor.setValue(quote.getVehicle().getVehicleAge().toString());
					factAsset.add(wsFactor);
		}
				
		if(quote.getVehicle().getHabitualUse() != null)
		{
					wsFactor = new WsFactor();
					wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2USOVE.value());
					wsFactor.setValue(quote.getVehicle().getHabitualUse());
					factAsset.add(wsFactor);
		}
				
		if(quote.getVehicle().getHabitualUse() != null)
		{
					wsFactor = new WsFactor();
					wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2USOVE.value());
					wsFactor.setValue(quote.getVehicle().getHabitualUse());
					factAsset.add(wsFactor);
		}
				
		if(quote.getVehicle().getCarAgeAtPurchase() != null)
		{
					wsFactor = new WsFactor();
					wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2EVACQ.value());
					wsFactor.setValue(quote.getVehicle().getCarAgeAtPurchase().toString());
					factAsset.add(wsFactor);
		}
		//CONTROLLARE A CHE FATTORE METTERLO
		if(quote.getFigures().getAge() != null)
		{
					wsFactor = new WsFactor();
					wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2RD1ET.value());
					wsFactor.setValue(quote.getFigures().getAge().toString());
					factAsset.add(wsFactor);
		}
				//CONTROLLARE A CHE FATTORE METTERLO
		if(quote.getFigures().getResidenceAddress().getCap() != null)
		{
					wsFactor = new WsFactor();
					wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2RD2CA.value());
					wsFactor.setValue(quote.getFigures().getResidenceAddress().getCap());
					factAsset.add(wsFactor);
		}
				
		if(quote.getFigures().getYearsWithLicence() != null)
		{
					wsFactor = new WsFactor();
					wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2MDAP.value());
					wsFactor.setValue(quote.getFigures().getYearsWithLicence().toString());
					factAsset.add(wsFactor);
		}
				
		if(quote.getNumberOfVehiclesOwned() != null)
		{
					wsFactor = new WsFactor();
					wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2ANUCF.value());
					wsFactor.setValue(quote.getNumberOfVehiclesOwned().toString());
					factAsset.add(wsFactor);
		}
				
		if(quote.getRatingInfo().getUwClass() != null)
		{
					wsFactor = new WsFactor();
					wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2CLUWR.value());
					wsFactor.setValue(quote.getRatingInfo().getUwClass());
					factAsset.add(wsFactor);
		}
				
		if(quote.getGoodDriverClass() != null)
		{
					wsFactor = new WsFactor();
					wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR__2BM.value());
					wsFactor.setValue(quote.getGoodDriverClass());
					factAsset.add(wsFactor);
		}
				
		if(quote.getInnerClass() != null)
		{
					wsFactor = new WsFactor();
					wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR__2CU.value());
					wsFactor.setValue(quote.getInnerClass());
					factAsset.add(wsFactor);
		}
				
		if(quote.getDriverNumber() != null)
		{
					wsFactor = new WsFactor();
					wsFactor.setCode(AssetInstanceFactorsENUM.FACTOR_2NDRIV.value());
					wsFactor.setValue(quote.getDriverNumber().toString());
					factAsset.add(wsFactor);
		}
		assetInst.get(0).getFactors().addAll(factAsset);
				
		return assetInst;
	}
	
	public List<WsVehicle> quoteToWsVehicle(InboundRequestHttpJSON inb)
	{
		ArrayList<WsVehicle> ve = new ArrayList<WsVehicle>();
		WsVehicle wsVe = new WsVehicle();

		wsVe.setClassCode(inb.getInboundQuoteDTO().getContext().getRiskType());
		wsVe.setSectorCode(inb.getSectorCodeVehicle());
		wsVe.setUseCode(inb.getInboundQuoteDTO().getVehicle().getTechnicalData().getPraUse());
		
		return ve;
	
	}
	
	public List<WsAssetUnit> quoteToAssetUnit(InboundRequestHttpJSON inb)
	{
		ArrayList<WsAssetUnit> listAssetUnit = new ArrayList<WsAssetUnit>();
		WsAssetUnit assetUnit = new WsAssetUnit();
		TypeBooleano typeB =  new TypeBooleano();
		
		typeB.setBoolean(inb.isSelectionAssetUnit());

		assetUnit.setCode(inb.getCodeAssetUnit());
		
		assetUnit.setSelection(typeB);
		
		listAssetUnit.add(assetUnit);
		
		return listAssetUnit;
	
	}
	
	public List<WsClause> quoteToClause(InboundRequestHttpJSON inb)
	{
		ArrayList<WsClause> listClause = new ArrayList<WsClause>();
		WsClause cla = new WsClause();
		TypeBooleano typeB =  new TypeBooleano();
		
		typeB.setBoolean(inb.isSelectedClause());
		
		cla.setCode(inb.getCodeClause());
		cla.setSelected(typeB);
		
		listClause.add(cla);
		
		return listClause;
	
	}
	
	public List<WsFactor> quoteToUnitFactor(InboundQuoteDTO quote)
	{
		ArrayList<WsFactor> factorUnit = new ArrayList<WsFactor>();
		WsFactor wsFactor = new WsFactor();
		
		if(quote.getCoverages().getLimit().getCode() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(UnitInstanceFactorsENUM.FACTOR_3CRLMT.value());
			wsFactor.setValue(quote.getCoverages().getLimit().getCode());
			factorUnit.add(wsFactor);
		}
		
		if(quote.getCoverages().getDeductible().getCode() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(UnitInstanceFactorsENUM.FACTOR_3CRDED.value());
			wsFactor.setValue(quote.getCoverages().getDeductible().getCode());
			factorUnit.add(wsFactor);
		}
		
		if(quote.getClean1() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(UnitInstanceFactorsENUM.FACTOR_3CLIN1.value());
			wsFactor.setValue(quote.getClean1().toString());
			factorUnit.add(wsFactor);
		}
		
		if(quote.getCoverages().getLimit().getCode() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(UnitInstanceFactorsENUM.FACTOR_3CRLMT.value());
			wsFactor.setValue(quote.getCoverages().getLimit().getCode());
			factorUnit.add(wsFactor);
		}
		
		if(quote.getCoverages().getDeductible().getCode() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(UnitInstanceFactorsENUM.FACTOR_3CRDED.value());
			wsFactor.setValue(quote.getCoverages().getDeductible().getCode());
			factorUnit.add(wsFactor);
		}
		
		if(quote.getClean1() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(UnitInstanceFactorsENUM.FACTOR_3CLIN1.value());
			wsFactor.setValue(quote.getClean1().toString());
			factorUnit.add(wsFactor);
		}
		
		
		return factorUnit;
	
	}
	
	
	public WsPremiumGroup changeInstance (InboundPremiumDTO pre)
	{		
		return new WsPremiumGroup();
	}
	
	public WsVehicle changeInstance (InboundVehicleDTO vehicle)
	{		
		return new WsVehicle();
	}

}
