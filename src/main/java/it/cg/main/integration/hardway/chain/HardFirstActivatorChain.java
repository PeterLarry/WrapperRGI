package it.cg.main.integration.hardway.chain;

import javax.xml.datatype.DatatypeConfigurationException;

import org.apache.log4j.Logger;
import org.springframework.integration.annotation.Gateway;
import org.springframework.stereotype.Component;

import com.pass.global.GetTechnicalData;

import it.cg.main.dto.RoutingDTO;
import it.cg.main.integration.interfaces.ActivatorHandler;
import test.CustomTestHard;

/**
 * Mapping and calling routing getTechnicalData, getAssetts, getRisk, CalcolaPremio
 * @author RiccardoEstia
 *
 */
@Component
public class HardFirstActivatorChain extends ActivatorHandler
{
	private Logger logger = Logger.getLogger(getClass());
	
	/**
	 * Parsing Input PASS
	 * @param routingDTO
	 * @return GetTechnicalData WS
	 * @throws DatatypeConfigurationException 
	 */
	@Gateway
	public GetTechnicalData gotoGetTechnicalData(RoutingDTO request) throws DatatypeConfigurationException
	{
		logger.info("Into goto Hard Call call GetTechnicalData , input="+request);
		
//		parser
//		ParsingOut pout = new ParsingOut();
//		GetTechnicalData req = pout.getGetTechnicalDataParse(request);
		CustomTestHard ch = new CustomTestHard();
		GetTechnicalData req = ch.getGetTechnicalDataTest();

		logger.info("Into method goto Hard Call , output="+req);
		return req;
	}


}
