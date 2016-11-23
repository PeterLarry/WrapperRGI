package it.cg.main.integration.hardway.chain.factcla;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Gateway;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import com.pass.global.GetClauses;
import com.pass.global.GetFactors;
import com.pass.global.GetFactorsResponse;
import com.pass.global.GetTechnicalDataResponse;

import it.cg.main.conf.mapping.hardway.GetTechnicalDataMapstruct;
import it.cg.main.init.StaticGeneralConfig;
import it.cg.main.integration.hardway.mapping.MappingRequests;
import it.cg.main.integration.interfaces.ActivatorHandler;

@Component
public class FactClauReqRespHandler extends ActivatorHandler
{

	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private GetTechnicalDataMapstruct getTechnicalDataMapstruct;
	
	
	/**
	 * Calling Factors after GetTechnicalData
	 * @param GetTechnicalDataResponse
	 * @return getFactors
	 */
	@Gateway
	public Message<GetFactors> gotoGetFactors(GetTechnicalDataResponse technicalData, @Headers Map<String, Object>headers)
	{
		logger.info("gotoFactorsRequest input DTO "+technicalData);
		
		MappingRequests mappingRequests = new MappingRequests(getTechnicalDataMapstruct);
		GetFactors requestFactors  = mappingRequests.parseGetTecnicalDataFactors(technicalData);
		
		Message<GetFactors> message = createMessageWithHeader( requestFactors,
								StaticGeneralConfig.HEADER_MESSAGE_HARDWAY_PHASE_KEY.value(),
								StaticGeneralConfig.HEADER_MESSAGE_HARDWAY_GETTECHINICALDATA_VAL.value() );
		
		logger.info("gotoFactorsRequest response message "+message);
		return message;
	}
	
	/**
	 * Calling getClauses after factors
	 * @param factorsResponse
	 * @return
	 */
	@Gateway
	public Message<GetClauses> gotoGetClauses(GetFactorsResponse factorsResponse, @Headers Map<String, Object>headers)
	{
		GetClauses getClauses = new GetClauses();
//		TODO Mapping for getClauses
		Message<GetClauses> message = createMessage(getClauses);
		
		logger.info("gotoGetClauses response message "+message);
		return message;
	}

	/**
	 * GETCLAUSES routing
	 * Routing for GetAssets / GetRisk / CalcolaPremio
	 * @param GetClauses
	 * @return ? :=> GetAssets / GetRisk / CalcolaPremio
	 */
	@Gateway
	public Message<?> routingAfterClauses(GetClauses factorsResponse, @Headers Map<String, Object>headers)
	{
		GetClauses getClauses = new GetClauses();
//		TODO Mapping for getClauses
		Message<GetClauses> message = createMessage(getClauses);
		
		logger.info("gotoGetClauses response message "+message);
		return message;
	}
	
	
	

}
