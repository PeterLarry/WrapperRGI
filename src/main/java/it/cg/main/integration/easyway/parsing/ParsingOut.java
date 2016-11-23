package it.cg.main.integration.easyway.parsing;

import org.apache.log4j.Logger;

import com.pass.global.GetTechnicalData;
import com.pass.global.WSPassProHelloWorldOperation;

import it.cg.main.dto.RoutingDTO;

public class ParsingOut
{
	private Logger logger = Logger.getLogger(getClass());
	
	public WSPassProHelloWorldOperation getParsing(RoutingDTO request)
	{
		logger.info("into getParsing with request : "+request);
		
		WSPassProHelloWorldOperation getService = new WSPassProHelloWorldOperation();
		
		getService.setArg0(0);
		
		logger.info("into getParsing with response : "+getService);
		return getService;
	}
	
	/**
	 * Hard way GET TECHNICAL DATA
	 * @param request
	 * @return
	 */
	public GetTechnicalData getGetTechnicalDataParse(RoutingDTO request)
	{
		GetTechnicalData technicalData = new GetTechnicalData();
		
		
		
		return technicalData;
		
	}

}
