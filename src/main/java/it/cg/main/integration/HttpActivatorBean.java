package it.cg.main.integration;

import org.apache.log4j.Logger;
import org.springframework.integration.annotation.Gateway;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import it.cg.main.integration.interfaces.ActivatorHandler;

@Component
public class HttpActivatorBean implements ActivatorHandler
{
	private final Logger logger = Logger.getLogger(getClass());
	
	@Gateway
	public Message<?> getHandling(Message<?> request)
	{
		logger.debug("Request "+ request);
		
		return null;
	}

}
