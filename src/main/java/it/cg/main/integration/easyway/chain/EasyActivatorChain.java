package it.cg.main.integration.easyway.chain;

import javax.xml.datatype.DatatypeConfigurationException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Gateway;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.pass.global.CalculatePremium;
import com.pass.global.WsCalculatePremiumInput;

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
		logger.info("Into gotoEasyCall Start easy call with input="+request);
//		Received request from Proxy – targa x, data e ora test (info ricevute dal Proxy)
		String plateNumber = request.getInboundRequestHttpJSON().getInboundQuoteDTO().getPlateNumber();
		String internalIdPlatDate = request.getInboundRequestHttpJSON().getInboundQuoteDTO().getProxyQuoteInternalId();
		logger.debug("Received request from Proxy - "+plateNumber+" ,"+internalIdPlatDate);

		logger.debug("time test - before parsing from DL to PASS");
		ParsingOut pout = new ParsingOut(easyMapperMapstruct);
//		parse object to PASS
		WsCalculatePremiumInput calcPremium = pout.getQuoteToPass(request);
//		set request WS to PASS		
		CalculatePremium cp = new CalculatePremium();
		cp.setArg0(pout.getQuoteToMsgCalculate());
		cp.getArg0().setInput(calcPremium);
		logger.debug("time test - after parsing from DL to PASS");

//		create message response
		Message<CalculatePremium> messageResponse = createMessage(cp);
		
 		logger.info("Outof method gotoEasyCall than call WS to PASS , output="+cp);
		return messageResponse;
	}


}
