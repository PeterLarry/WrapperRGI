package it.cg.main.integration.interfaces;

import org.apache.log4j.Logger;
import org.springframework.integration.ws.DefaultSoapHeaderMapper;

public  abstract class SoapHeadersHandler  extends DefaultSoapHeaderMapper
{

	private Logger logger = Logger.getLogger(getClass());
}
