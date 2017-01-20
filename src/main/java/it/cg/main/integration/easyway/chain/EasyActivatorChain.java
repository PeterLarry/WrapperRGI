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

import it.cg.main.dto.QuoteInternalComponent;
import it.cg.main.dto.RoutingDTO;
import it.cg.main.integration.easyway.parsing.ParsingOut;
import it.cg.main.integration.interfaces.ActivatorHandler;
import it.cg.main.process.mapping.easyway.MapperRequestToPASS;

@Component
public class EasyActivatorChain extends ActivatorHandler
{
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private MapperRequestToPASS easyMapperMapstruct;
	
	@Autowired
	private QuoteInternalComponent quoteInternal;
	
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
		
		logger.debug("TIMEtest - BEFORE parsing from DL to PASS");
		ParsingOut pout = new ParsingOut(easyMapperMapstruct);
//		parse object to PASS
		WsCalculatePremiumInput calcPremium = pout.getQuoteToPass(request);
//		set request WS to PASS		
		CalculatePremium cp = new CalculatePremium();
		cp.setArg0(new MsgCalculatePremiumRequest());
		cp.getArg0().setInput(calcPremium);
		this.quoteInternal.setQuoteInternal(request.getInboundRequestHttpJSON().getInboundQuoteDTO());
		logger.debug("TIMEtest - AFTER parsing from DL to PASS");

//		create message response
		Message<CalculatePremium> messageResponse = createMessage(cp);
		
 		logger.info("Outof method gotoEasyCall than call WS to PASS , output="+cp);
		return messageResponse;
	}


}
