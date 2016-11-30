package it.cg.main.integration.easyway.chain;

import javax.xml.datatype.DatatypeConfigurationException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Gateway;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.pass.global.CalculatePremium;
import com.pass.global.MsgCalculatePremiumRequest;
import com.pass.global.WsCalculatePremiumInput;

import it.cg.main.conf.mapping.easyway.EasyMapperMapstruct;
import it.cg.main.dto.RoutingDTO;
import it.cg.main.integration.easyway.parsing.ParsingOut;
import it.cg.main.integration.interfaces.ActivatorHandler;

@Component
public class EasyActivatorChain extends ActivatorHandler
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
	public Message<CalculatePremium> gotoEasyCallChain(RoutingDTO request) throws DatatypeConfigurationException
	{
		logger.info("Into gotoEasyCall call , input="+request);
		
		ParsingOut pout = new ParsingOut(easyMapperMapstruct);
//		parse object
		WsCalculatePremiumInput calcPremium = pout.getQuoteToPass(request);
//		set request WS		
		CalculatePremium cp = new CalculatePremium();
		cp.setArg0(new MsgCalculatePremiumRequest());
		cp.getArg0().setInput(calcPremium);

//		create message response
		Message<CalculatePremium> messageResponse = createMessage(cp);
		
 		logger.info("Outof method gotoEasyCall , output="+cp);
		return messageResponse;
	}


}
