package it.cg.main.integration.easyway.reply;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Gateway;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import com.pass.global.CalculatePremiumResponse;

import it.cg.main.conf.mapping.easyway.MapperRequestToPASS;
import it.cg.main.conf.mapping.easyway.MapperResponseToDL;
import it.cg.main.dto.InboundResponseHttpJSON;
import it.cg.main.integration.easyway.parsing.ParsingIn;
import it.cg.main.integration.interfaces.ActivatorHandler;

public class ReplyEasywayAcrivator extends ActivatorHandler {

	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private MapperResponseToDL mapperToDL;
	
	/**
	 * Method to access to the mapping object from PASS to DL reply
	 * @param routingDTO
	 * @return InboundResponseHttpJSON
	 */
	@Gateway(requestChannel="easyChainActivatorResultChannel")
	public Message<InboundResponseHttpJSON> gotoEasyWay(CalculatePremiumResponse routingDTO)
	{
		logger.info("gotoEasyWay input DTO "+routingDTO);
		
		ParsingIn pIn = new ParsingIn(mapperToDL);
		InboundResponseHttpJSON responseJson  = pIn.parseCalculatePremiumResponse(routingDTO);
		responseJson.setSuccess(true);
		
		Message<InboundResponseHttpJSON> message = createMessage(responseJson);
		
		logger.info("gotoEasyWay response DTO "+message);
		return message;
	}


}
