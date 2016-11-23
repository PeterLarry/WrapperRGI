package test;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.pass.global.CalculatePremium;
import com.pass.global.GetTechnicalData;
import com.pass.global.MsgGetTechnicalDataRequest;
import com.pass.global.TypeBooleano;
import com.pass.global.TypeData;
import com.pass.global.WsFactor;
import com.pass.global.WsGetTechnicalDataInput;
import com.pass.global.WsProduct;

public class CustomTestHard
{
	GregorianCalendar c = new GregorianCalendar();
	TypeBooleano tyBoolGen = new TypeBooleano();
	
	
	private void getWsProduct(WsProduct wsProduct) throws DatatypeConfigurationException
	{
		TypeData dataOpenTypeData  = new TypeData(); 
		c.setTime(new Date()); // dopo ottobre
		XMLGregorianCalendar dataOpen = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		dataOpenTypeData.setData(dataOpen);
//		-----------------
		
		wsProduct.setCode("000002");
		wsProduct.setOpenDate(dataOpenTypeData);
//		wsProduct.setPaymentFrequencyCode("000001");
//		wsProduct.setCurrencyCode("000001");
		
		WsFactor factorTest;
//		factorTest = new WsFactor();factorTest.setCode("_1CETA");factorTest.setValue("18");wsProduct.getFactors().add(factorTest );
//		factorTest = new WsFactor();factorTest.setCode("_1CNCA");factorTest.setValue("11");wsProduct.getFactors().add(factorTest );
//		factorTest = new WsFactor();factorTest.setCode("_2BM");factorTest.setValue("1");wsProduct.getFactors().add(factorTest );
//		factorTest = new WsFactor();factorTest.setCode("_2AAVE");factorTest.setValue("1");wsProduct.getFactors().add(factorTest );
	}
	
	public GetTechnicalData getGetTechnicalDataTest() throws DatatypeConfigurationException
	{
		GetTechnicalData technicalData = new GetTechnicalData();
		technicalData.setArg0(new MsgGetTechnicalDataRequest());
		technicalData.getArg0().setInput(new WsGetTechnicalDataInput());
		technicalData.getArg0().getInput().setProduct(new WsProduct());
		getWsProduct(technicalData.getArg0().getInput().getProduct());
				
		return technicalData;
	}

}
