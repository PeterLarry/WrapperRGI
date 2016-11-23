package it.cg.main.integration.hardway.reply;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Gateway;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.pass.global.GetFactorsResponse;
import com.pass.global.GetTechnicalDataResponse;
import com.pass.global.WSPassProHelloWorldOperationResponse;

import it.cg.main.conf.mapping.EasyMapperMapstruct;
import it.cg.main.dto.InboundResponseHttpJSON;
import it.cg.main.integration.easyway.parsing.ParsingIn;
import it.cg.main.integration.interfaces.ActivatorHandler;

@Service
public class ReplyHardwayAcrivator extends ActivatorHandler
{

	private Logger logger = Logger.getLogger(getClass());
	
	/**
	 * Method to access to the mapping object from PASS to DL reply
	 * @param routingDTO
	 * @return InboundResponseHttpJSON
	 */
	@Gateway
	public Message<InboundResponseHttpJSON> gotoHardwayReply(GetFactorsResponse factorsResponse)
	{
		logger.info("gotoHardWay input DTO "+factorsResponse);
		
//		ParsingIn pIn = new ParsingIn(easyMapperMapstruct);
		InboundResponseHttpJSON responseJson  = new InboundResponseHttpJSON();
		responseJson.setGetFactorsResponse(factorsResponse);
//		responseJson = pIn.parse(routingDTO);
		
		Message<InboundResponseHttpJSON> message = MessageBuilder.withPayload(responseJson).build();
		
		logger.info("gotoHardWay response DTO "+message);
		return message;
	}


}
