package it.cg.main.integration.easyway.chain;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Gateway;
import org.springframework.stereotype.Component;

import com.pass.global.CalculatePremium;
import com.pass.global.MsgCalculatePremiumRequest;
import com.pass.global.TypeBooleano;
import com.pass.global.TypeData;
import com.pass.global.WsCalculatePremiumInput;
import com.pass.global.WsClause;
import com.pass.global.WsProduct;
import com.pass.global.WsUnitInstance;

import it.cg.main.conf.mapping.easyway.EasyMapperMapstruct;
import it.cg.main.dto.RoutingDTO;
import it.cg.main.integration.easyway.parsing.ParsingOut;

@Component
public class EasyActivatorChain
{
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private EasyMapperMapstruct easyMapperMapstruct;
	
	/**
	 * Parsing Input PASS
	 * @param routingDTO
	 * @param headerMap
	 * @return
	 * @throws DatatypeConfigurationException 
	 */
	@Gateway
	public CalculatePremium gotoEasyCallChain(RoutingDTO request) throws DatatypeConfigurationException
	{
		logger.info("Into gotoEasyCall call , input="+request);
		
		 TypeBooleano tB = new TypeBooleano();
		 tB.setBoolean(true);
		 
		 TypeBooleano tBF = new TypeBooleano();
		 tBF.setBoolean(false);
		 
		 TypeData dataOpenTypeData  = new TypeData(); 
		 GregorianCalendar c = new GregorianCalendar();
		 c.setTime(new Date()); // dopo ottobre
		 XMLGregorianCalendar dataOpen = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		 dataOpenTypeData.setData(dataOpen);
		 
//		parser
		ParsingOut pout = new ParsingOut(easyMapperMapstruct);
//		WsProduct wsprod= pout.getQuoteToWsProduct(request);
		WsCalculatePremiumInput calcPremium = pout.getQuoteToWsProduct(request);
		
		CalculatePremium cp = new CalculatePremium();
		cp.setArg0(new MsgCalculatePremiumRequest());
		cp.getArg0().setInput(calcPremium);
//		cp.getArg0().getInput().setProduct(wsprod);
		cp.getArg0().getInput().setAdaptToMinimumPremium(tB);
		cp.getArg0().getInput().setApplyDiscount(tB);
		cp.getArg0().getInput().getProduct().setOpenDate(dataOpenTypeData);
		cp.getArg0().getInput().getProduct().setPaymentFrequencyCode("000001");
		cp.getArg0().getInput().getProduct().setCurrencyCode("000001");
		cp.getArg0().getInput().getProduct().getAssets().get(0).getInstances().get(0).getSections().get(0).setCode("S1");
		cp.getArg0().getInput().getProduct().getAssets().get(0).getInstances().get(0).getSections().get(0).getUnits().get(0).setCode("RCAR1");
		cp.getArg0().getInput().getProduct().getAssets().get(0).getInstances().get(0).getSections().get(0).getUnits().get(0).setSelection(tB);
		cp.getArg0().getInput().getProduct().getAssets().get(0).getInstances().get(0).getSections().get(0).getUnits().get(0).getInstances().add(new WsUnitInstance());
		cp.getArg0().getInput().getProduct().getAssets().get(0).getInstances().get(0).getSections().get(0).getUnits().get(0).getInstances().get(0).getClauses().add(new WsClause());
		
		
		
//		cp.getArg0().getInput().getProduct().getAssets().get(0).getInstances().get(0).getSections().get(0).getUnits().get(0).getInstances().get(0).getClauses().add(new WsClause());
		cp.getArg0().getInput().getProduct().getAssets().get(0).getInstances().get(0).getSections().get(0).getUnits().get(0).getInstances().get(0).getClauses().get(0).setCode("RCA001");
		cp.getArg0().getInput().getProduct().getAssets().get(0).getInstances().get(0).getSections().get(0).getUnits().get(0).getInstances().get(0).getClauses().get(0).setSelected(tBF);
		

		logger.info("Into method gotoEasyCall , output="+cp);
		return cp;
	}


}
