package it.cg.main.integration.easyway.chain;

import javax.xml.datatype.DatatypeConfigurationException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Gateway;
import org.springframework.stereotype.Component;

import com.pass.global.CalculatePremium;

import it.cg.main.conf.mapping.easyway.EasyMapperMapstruct;
import it.cg.main.dto.RoutingDTO;
import it.cg.main.integration.easyway.parsing.ParsingOut;
import test.CustomTest;

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
		
//		parser
		ParsingOut pout = new ParsingOut(easyMapperMapstruct);
		pout.getQuoteToWsProduct(request);
		
//		Calculate premium
		CustomTest ctest = new CustomTest();
		CalculatePremium cp = new CalculatePremium();
		ctest.getCalculatePremiumTest(cp);
//		-----------

		logger.info("Into method gotoEasyCall , output="+cp);
		return cp;
	}


}
