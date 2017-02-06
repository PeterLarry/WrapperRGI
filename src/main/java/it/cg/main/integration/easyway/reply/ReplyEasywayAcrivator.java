package it.cg.main.integration.easyway.reply;

import org.apache.log4j.Logger;
import org.springframework.integration.annotation.Gateway;
import org.springframework.messaging.Message;

import com.pass.global.CalculatePremiumResponse;

import it.cg.main.dto.InboundResponseHttpJSON;
import it.cg.main.integration.easyway.parsing.ParsingIn;
import it.cg.main.integration.interfaces.ActivatorHandler;

public class ReplyEasywayAcrivator extends ActivatorHandler
{

	private Logger logger = Logger.getLogger(getClass());
	
	/**
	 * Method to access to the mapping object from PASS to DL reply
	 * @param calculateResponse
	 * @return InboundResponseHttpJSON
	 */
	@Gateway(requestChannel="easyChainActivatorResultChannel")
	public Message<InboundResponseHttpJSON> gotoEasyWay(CalculatePremiumResponse calculateResponse)
	{
		logger.info("gotoEasyWay input DTO "+calculateResponse+" Received response from Pass");
		
		logger.debug("time test response - response arrived, before parsing from PASS to DL");
		ParsingIn pIn = new ParsingIn();
		InboundResponseHttpJSON responseJson  = pIn.parseCalculatePremiumResponse(calculateResponse);
		
		Message<InboundResponseHttpJSON> message = createMessage(responseJson);
		logger.debug("time test response - after parsing response from PASS to DL");
		
		logger.info("Into easyChainActivatorResultChannel easy call finished, ready to response proxy - Sent response to Proxy");
		logger.info("gotoEasyWay response DTO "+message);
		return message;
	}


}
