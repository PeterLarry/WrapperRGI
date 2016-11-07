package it.cg.main.integration.easyway.ws;

import org.springframework.messaging.MessageHeaders;
import org.springframework.ws.soap.SoapMessage;

import it.cg.main.integration.interfaces.SoapHeadersHandler;

public class HeaderMapperEasy extends SoapHeadersHandler
{
	
	@Override
	public void setRequestHeaderNames(String... requestHeaderNames)
	{
		super.setRequestHeaderNames(requestHeaderNames);
	}
	
	@Override
	public void fromHeadersToRequest(MessageHeaders headers, SoapMessage target)
	{
		//		TODO da mettere la request corretta
		target.setSoapAction("http://WSPassPro.sei.rgi.it/");
	}

	
	
}
