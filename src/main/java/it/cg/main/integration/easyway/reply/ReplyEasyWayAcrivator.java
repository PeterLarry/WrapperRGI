package it.cg.main.integration.easyway.reply;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Gateway;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import com.pass.global.WSPassProHelloWorldOperationResponse;

import it.cg.main.conf.mapping.EasyMapperMapstruct;
import it.cg.main.dto.InboundResponseHttpJSON;
import it.cg.main.integration.easyway.parsing.ParsingIn;
import it.cg.main.integration.interfaces.ActivatorHandler;

public class ReplyEasyWayAcrivator implements ActivatorHandler {

	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private EasyMapperMapstruct easyMapperMapstruct;
	
	/**
	 * Method to access to the mapping object from PASS to DL reply
	 * @param routingDTO
	 * @return InboundResponseHttpJSON
	 */
	@Gateway(requestChannel="easyChainActivatorResultChannel")
	public Message<InboundResponseHttpJSON> gotoEasyWay(WSPassProHelloWorldOperationResponse routingDTO)
	{
		logger.info("gotoEasyWay input DTO "+routingDTO);
		
		ParsingIn pIn = new ParsingIn(easyMapperMapstruct);
		InboundResponseHttpJSON responseJson  = new InboundResponseHttpJSON();
		
		responseJson = pIn.parse(routingDTO);
		
		Message<InboundResponseHttpJSON> message = MessageBuilder.withPayload(responseJson).build();
		
		logger.info("gotoEasyWay response DTO "+message);
		return message;
	}


}
