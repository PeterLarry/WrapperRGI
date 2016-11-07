package it.cg.main.integration.easyway;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.integration.annotation.Gateway;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import com.pass.global.WSPassProHelloWorldOperation;

import it.cg.main.dto.RoutingDTO;
import it.cg.main.integration.easyway.parsing.ParsingOut;
import it.cg.main.integration.interfaces.ActivatorHandler;

@Component
public class EasyRouterActivatorBean implements ActivatorHandler
{
	private Logger logger = Logger.getLogger(getClass());

	
	/**
	 * TODO Parsing
	 * @param routingDTO
	 * @param headerMap
	 * @return
	 */
	@Gateway
	public WSPassProHelloWorldOperation gotoEasyCallChain(RoutingDTO request, @Headers Map<String, Object> headerMap)
	{
		logger.info("Into "+getClass()+" gotoEasyCall call , input="+request);
		
//		parserizzatore
		ParsingOut pout = new ParsingOut();
		WSPassProHelloWorldOperation response = pout.getParsing(request);

		logger.info("Into "+getClass()+"method gotoEasyCall , output="+response);
		return response;
	}

}
