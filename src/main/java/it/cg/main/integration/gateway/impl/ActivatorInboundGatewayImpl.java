package it.cg.main.integration.gateway.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.annotation.Gateway;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import com.blog.samples.webservices.DetailService;
import com.blog.samples.webservices.servicedetail.ServiceCallResponse;

import it.cg.main.dto.InboundRequestHttpJSON;
import it.cg.main.dto.RoutingDTO;
import it.cg.main.integration.interfaces.ActivatorHandler;


@Service
public class ActivatorInboundGatewayImpl implements ActivatorHandler
{
	private final Logger logger = Logger.getLogger(getClass());
	
	@Value("${easyFieldRouting:vuoto}")
	private String externalTypeForEasyWay;
	@Value("${hardFieldRouting:vuoto}")
	private String externalTypeForHardWay;
	
	/**
	 * Setto che tipo di routing è da fare e l'oggetto di request intero per trasportarlo tra
	 * gli elementi di integration
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
		routingDto.setTypeOf(request.getTesto());
		routingDto.setRequestHttpService(request);
		
		logger.info("for handlingJsonObjectToRouter response : "+routingDto);
		return routingDto;
	}
	
	/**
	 * TODO da implementare con la response finale
	 * 
	 * @param response
	 * @return ServiceCallResponse
	 */
	@Gateway
	public ServiceCallResponse getReplyMetod(ServiceCallResponse response)
	{
		logger.info("getReplyMetod with parameters : " + response);
		
		ServiceCallResponse serviceCallResponse = new ServiceCallResponse();
		DetailService detail = new DetailService();
		
		detail.setServiceType("fake response");
		
		serviceCallResponse.setDetailService(detail );
		
		logger.info("getReplyMetod reply : " + detail);
		return serviceCallResponse;
	}


}
