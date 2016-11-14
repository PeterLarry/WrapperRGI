package it.cg.main.integration.easyway.chain;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.pass.global.CalculatePremium;
import com.pass.global.MsgCalculatePremiumRequest;
import com.pass.global.TypeBooleano;
import com.pass.global.TypeData;
import com.pass.global.WsAsset;
import com.pass.global.WsAssetInstance;
import com.pass.global.WsAssetSection;
import com.pass.global.WsAssetUnit;
import com.pass.global.WsCalculatePremiumInput;
import com.pass.global.WsClause;
import com.pass.global.WsFactor;
import com.pass.global.WsProduct;
import com.pass.global.WsUnitInstance;
import com.pass.global.WsVehicle;

public class CustomTest
{
	GregorianCalendar c = new GregorianCalendar();
	TypeBooleano tyBoolGen = new TypeBooleano();
	
	
	private void getWsCalculatePremiumInput(CalculatePremium cp)
	{
		TypeBooleano tyBool = new TypeBooleano();
		tyBool.setBoolean(true);
		cp.getArg0().getInput().setQuoteMode(tyBool);
		tyBool = new TypeBooleano();
		tyBool.setBoolean(true);
		cp.getArg0().getInput().setAdaptToMinimumPremium(tyBool);
		tyBool = new TypeBooleano();
		tyBool.setBoolean(true);
		cp.getArg0().getInput().setApplyDiscount(tyBool);
		tyBool = new TypeBooleano();
		tyBool.setBoolean(true);
	}
	
	private void getWsProduct(CalculatePremium cp) throws DatatypeConfigurationException
	{
		cp.getArg0().getInput().getProduct().setCode("000002");
		TypeData dataOpenTypeData  = new TypeData(); 
		c.setTime(new Date()); // dopo ottobre
		XMLGregorianCalendar dataOpen = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		dataOpenTypeData.setData(dataOpen);
		cp.getArg0().getInput().getProduct().setOpenDate(dataOpenTypeData);
		cp.getArg0().getInput().getProduct().setPaymentFrequencyCode("000001");
		cp.getArg0().getInput().getProduct().setCurrencyCode("000001");
	}
	
	/**
	 * wsCalculatePremiumInput -> wsProduct -> wsAsset
	 * @param cp
	 */
	private void getWsAsset(CalculatePremium cp)
	{
		WsAsset assetTest = new WsAsset();
		assetTest.setCode("000004");
//		WsAsset -> WsAssetInstance
		WsAssetInstance instanceTest = new WsAssetInstance();
//		WsAsset -> WsAssetInstance -> wsFactor
		getFactorsAssetInstances(instanceTest);
//		WsAsset -> WsAssetInstance -> WsClausole
//		instanceTest.getClauses().add(getClausoleTest());
//		WsAsset -> WsAssetInstance -> WsAssetSection
		WsAssetSection sectionTest = new WsAssetSection();
		sectionTest.setCode("S1");
//		WsAsset -> WsAssetInstance -> WsAssetSection -> WsAssetUnit
		WsAssetUnit wsAssUnitTest = new WsAssetUnit();
		wsAssUnitTest.setCode("RCAR1");
		tyBoolGen = new TypeBooleano(); tyBoolGen.setBoolean(true); wsAssUnitTest.setSelection(tyBoolGen);
//		WsAsset -> WsAssetInstance -> WsAssetSection -> WsAssetUnit -> WsUnitInstance
		WsUnitInstance assetInstance = new WsUnitInstance();
		getFactorsWsUnitInstance(assetInstance);
		wsAssUnitTest.getInstances().add(assetInstance);
		wsAssUnitTest.getClauses().add(getClausoleTest());
		
		sectionTest.getUnits().add(wsAssUnitTest);
		instanceTest.getSections().add(sectionTest);
		
//		WsAsset -> wsAssetInstance -> WsVehicle
		WsVehicle vehicleTest = new WsVehicle();
		instanceTest.setVehicle(vehicleTest);
		
		assetTest.getInstances().add(instanceTest);
		cp.getArg0().getInput().getProduct().getAssets().add(assetTest);
	}


	private WsClause getClausoleTest()
	{
		WsClause clausoleTest = new WsClause();
		clausoleTest.setCode("RCA001");
		tyBoolGen.setBoolean(true); clausoleTest.setSelected(tyBoolGen);
		return clausoleTest;
	}
	
	
//	vvvvvvvvvvvvvv FACTORS vvvvvvvvvvvvvv
	private void getFactorsWsUnitInstance(WsUnitInstance unitInstance)
	{
		WsFactor factorTest  ;
		factorTest = new WsFactor();factorTest.setCode("3MASS");factorTest.setValue("1");unitInstance.getFactors() .add(factorTest);
		factorTest = new WsFactor();factorTest.setCode("3RCFRA");factorTest.setValue("1");unitInstance.getFactors() .add(factorTest);
		factorTest = new WsFactor();factorTest.setCode("3YDAGE");factorTest.setValue("1");unitInstance.getFactors() .add(factorTest);
		factorTest = new WsFactor();factorTest.setCode("SXTOT");factorTest.setValue("6");unitInstance.getFactors() .add(factorTest);
		factorTest = new WsFactor();factorTest.setCode("FRRCA");factorTest.setValue("6");unitInstance.getFactors() .add(factorTest);
		factorTest = new WsFactor();factorTest.setCode("QDRCA");factorTest.setValue("6");unitInstance.getFactors() .add(factorTest);
	}
	
	private void getFactorsAssetInstances(WsAssetInstance assetInstances)
	{
		WsFactor factorTest  ;
		factorTest = new WsFactor();factorTest.setCode("2USOVE");factorTest.setValue("2");assetInstances.getFactors().add(factorTest);
		factorTest = new WsFactor();factorTest.setCode("2CLUWR");factorTest.setValue("4");assetInstances.getFactors().add(factorTest);
		factorTest = new WsFactor();factorTest.setCode("1PHAP");factorTest.setValue("41");assetInstances.getFactors().add(factorTest);
		factorTest = new WsFactor();factorTest.setCode("1ANRIN");factorTest.setValue("1");assetInstances.getFactors().add(factorTest);
		factorTest = new WsFactor();factorTest.setCode("1AFF");factorTest.setValue("1");assetInstances.getFactors().add(factorTest);
		factorTest = new WsFactor();factorTest.setCode("_2PETA");factorTest.setValue("8");assetInstances.getFactors().add(factorTest);
		factorTest = new WsFactor();factorTest.setCode("_2PRCA");factorTest.setValue("8");assetInstances.getFactors().add(factorTest);
		factorTest = new WsFactor();factorTest.setCode("_2CETA");factorTest.setValue("2");assetInstances.getFactors().add(factorTest);
		factorTest = new WsFactor();factorTest.setCode("_2CRCA");factorTest.setValue("2");assetInstances.getFactors().add(factorTest);
		factorTest = new WsFactor();factorTest.setCode("2MDPR");factorTest.setValue("1");assetInstances.getFactors().add(factorTest);
		factorTest = new WsFactor();factorTest.setCode("2MDAP");factorTest.setValue("2");assetInstances.getFactors().add(factorTest);
		factorTest = new WsFactor();factorTest.setCode("2RD1ET");factorTest.setValue("1");assetInstances.getFactors().add(factorTest);
		factorTest = new WsFactor();factorTest.setCode("2RD1CA");factorTest.setValue("1");assetInstances.getFactors().add(factorTest);
		factorTest = new WsFactor();factorTest.setCode("2NDRIV");factorTest.setValue("4");assetInstances.getFactors().add(factorTest);
		factorTest = new WsFactor();factorTest.setCode("2ETAV");factorTest.setValue("2");assetInstances.getFactors().add(factorTest);
		factorTest = new WsFactor();factorTest.setCode("2EVACQ");factorTest.setValue("2");assetInstances.getFactors().add(factorTest);
		factorTest = new WsFactor();factorTest.setCode("2ANUCF");factorTest.setValue("4");assetInstances.getFactors().add(factorTest);
		factorTest = new WsFactor();factorTest.setCode("2ALAUT");factorTest.setValue("2");assetInstances.getFactors().add(factorTest);
		factorTest = new WsFactor();factorTest.setCode("_2DICH");factorTest.setValue("4");assetInstances.getFactors().add(factorTest);
		factorTest = new WsFactor();factorTest.setCode("3CLIN1");factorTest.setValue("3");assetInstances.getFactors().add(factorTest);
		factorTest = new WsFactor();factorTest.setCode("_2FTAP");factorTest.setValue("3");assetInstances.getFactors().add(factorTest);
		factorTest = new WsFactor();factorTest.setCode("_2CUPR");factorTest.setValue("1");assetInstances.getFactors().add(factorTest);
		factorTest = new WsFactor();factorTest.setCode("_2CUAS");factorTest.setValue("18");assetInstances.getFactors().add(factorTest);
		factorTest = new WsFactor();factorTest.setCode("_2CU");factorTest.setValue("1");assetInstances.getFactors().add(factorTest);
		factorTest = new WsFactor();factorTest.setCode("2OWAP");factorTest.setValue("4");assetInstances.getFactors().add(factorTest);
		factorTest = new WsFactor();factorTest.setCode("2WCAP");factorTest.setValue("3");assetInstances.getFactors().add(factorTest);
		factorTest = new WsFactor();factorTest.setCode("2BERSP");factorTest.setValue("2");assetInstances.getFactors().add(factorTest);
		factorTest = new WsFactor();factorTest.setCode("1FIDRC");factorTest.setValue("41");assetInstances.getFactors().add(factorTest);
		factorTest = new WsFactor();factorTest.setCode("2ASSIN");factorTest.setValue("4");assetInstances.getFactors().add(factorTest);
		factorTest = new WsFactor();factorTest.setCode("2SIN6P");factorTest.setValue("101");assetInstances.getFactors().add(factorTest);
		factorTest = new WsFactor();factorTest.setCode("1OPER");factorTest.setValue("41");assetInstances.getFactors().add(factorTest);
		factorTest = new WsFactor();factorTest.setCode("1FRRIN");factorTest.setValue("41");assetInstances.getFactors().add(factorTest);
	}
	
	private void getFactorsWsProduct(WsProduct wsProduct)
	{
		WsFactor factorTest;
		factorTest = new WsFactor();factorTest.setCode("_1CETA");factorTest.setValue("18");wsProduct.getFactors().add(factorTest );
		factorTest = new WsFactor();factorTest.setCode("_1CNCA");factorTest.setValue("11");wsProduct.getFactors().add(factorTest );
		factorTest = new WsFactor();factorTest.setCode("_2BM");factorTest.setValue("3");wsProduct.getFactors().add(factorTest );
		factorTest = new WsFactor();factorTest.setCode("_2AAVE");factorTest.setValue("Da altro Veicolo del proprietario");wsProduct.getFactors().add(factorTest );
	}

	/***
	 * main
	 * @param cp
	 * @throws DatatypeConfigurationException
	 */
	public void getCalculatePremiumTest(CalculatePremium cp) throws DatatypeConfigurationException
	{
		cp.setArg0(new MsgCalculatePremiumRequest());
		cp.getArg0().setInput(new WsCalculatePremiumInput());
		getWsCalculatePremiumInput(cp);
		
		cp.getArg0().getInput().setProduct(new WsProduct());
		getWsProduct(cp);
		getFactorsWsProduct(cp.getArg0().getInput().getProduct()); 
		getWsAsset(cp);
		
		cp.getArg0().getInput().getProduct().getClauses().add(getClausoleTest());
	}
	
	
}
