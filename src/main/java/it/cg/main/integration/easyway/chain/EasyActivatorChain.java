package it.cg.main.integration.easyway.chain;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;
import org.springframework.integration.annotation.Gateway;
import org.springframework.stereotype.Component;

import com.pass.global.CalculatePremium;
import com.pass.global.MsgCalculatePremiumRequest;
import com.pass.global.TypeData;
import com.pass.global.WSPassProHelloWorldOperation;
import com.pass.global.WsCalculatePremiumInput;
import com.pass.global.WsFactor;
import com.pass.global.WsProduct;

import it.cg.main.dto.RoutingDTO;
import it.cg.main.integration.easyway.parsing.ParsingOut;

@Component
public class EasyActivatorChain
{
	private Logger logger = Logger.getLogger(getClass());
	
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
		
//		parser
		ParsingOut pout = new ParsingOut();
		WSPassProHelloWorldOperation response = pout.getParsing(request);
		
//		Calculate premium
		CustomTest ctest = new CustomTest();
		CalculatePremium cp = new CalculatePremium();
		ctest.getCalculatePremiumTest(cp);
//		-----------

		logger.info("Into method gotoEasyCall , output="+cp);
		return cp;
	}


}
