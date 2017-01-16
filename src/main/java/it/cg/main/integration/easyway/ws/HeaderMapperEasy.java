package it.cg.main.integration.easyway.ws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.MessageHeaders;
import org.springframework.ws.soap.SoapMessage;

import it.cg.main.integration.interfaces.SoapHeadersHandler;

public class HeaderMapperEasy extends SoapHeadersHandler
{
	@Value("${headerOutboundPass}")
	private String headerOutboundPass;
	
	@Override
	public void setRequestHeaderNames(String... requestHeaderNames)
	{
		super.setRequestHeaderNames(requestHeaderNames);
	}
	
	@Override
	public void fromHeadersToRequest(MessageHeaders headers, SoapMessage target)
	{
//		target.setSoapAction(headerOutboundPass);
	}

	
	
}
