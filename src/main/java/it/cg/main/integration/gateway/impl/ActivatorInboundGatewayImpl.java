package it.cg.main.integration.gateway.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.annotation.Gateway;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import it.cg.main.dto.InboundRequestHttpJSON;
import it.cg.main.dto.RoutingDTO;
import it.cg.main.integration.interfaces.ActivatorHandler;


@Service
public class ActivatorInboundGatewayImpl extends ActivatorHandler
{
	private final Logger logger = Logger.getLogger(getClass());
	
	/**
	 * external constant for the EASY way field check
	 */
	@Value("${easyFieldRouting:vuoto}")
	private String externalTypeForEasyWay;
	/**
	 * external constant for the HARD way field check
	 */
	@Value("${hardFieldRouting:vuoto}")
	private String externalTypeForHardWay;
	
	/**
	 * Set the routing's type and the external request object into RoutingDTO.<br>
	 * This object will be transported through the spring integration flow.
	 * @param request
	 * @return
	 * @throws MessagingException
	 */
	@Gateway
	public RoutingDTO handlingJsonObjectToRouter(InboundRequestHttpJSON request)
	{
		logger.info("for handlingJsonObjectToRouter with : "+request);
		
		RoutingDTO routingDto = new RoutingDTO();
		routingDto.setExternalTypeForEasyWay(externalTypeForEasyWay);
		routingDto.setExternalTypeForHardWay(externalTypeForHardWay);
		routingDto.setTypeOf(request.getServiceType());
		routingDto.setInboundRequestHttpJSON(request);
		
		logger.info("for handlingJsonObjectToRouter response : "+routingDto);
		return routingDto;
	}
	
}
