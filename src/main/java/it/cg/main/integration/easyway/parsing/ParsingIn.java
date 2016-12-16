package it.cg.main.integration.easyway.parsing;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.mapfre.engines.rating.business.objects.wrapper.Coverage;
import com.pass.global.CalculatePremiumResponse;
import com.pass.global.WsUnitInstance;

import it.cg.main.conf.mapping.easyway.MapperResponseToDL;
import it.cg.main.dto.InboundResponseHttpJSON;

/**
 * Handling recived response from PASS to parse to DL
 * @author RiccardoEstia
 *
 */
@Service
public class ParsingIn
{
	private Logger logger = Logger.getLogger(getClass());
	
	private MapperResponseToDL mapperToDL;
	
	/**
	 * The param is parsed using mapstruct custom classes, check the errors from pass
	 * Set the "success" field if no errors into the param, else bind the errors to the errorsdto included
	 * @param CalculatePremiumResponse
	 * @return InboundResponseHttpJSON parsed
	 */
	public InboundResponseHttpJSON parseCalculatePremiumResponse(CalculatePremiumResponse responseCalculate)
	{
		logger.info("parseCalculatePremiumResponse enter with parameters :"+responseCalculate);
		InboundResponseHttpJSON response = new InboundResponseHttpJSON();
		boolean isErrorFromPass = false;
//		ceck for errors
		try
		{
			isErrorFromPass = response.bindPassError(responseCalculate.getReturn().getServiceInfo());
		}
		catch(NullPointerException ex)
		{
			logger.error("Get Output from PASS error, no unitinstance populated "+ex.getMessage());
			throw new NullPointerException("Get Output from PASS error, no unitinstance populated "+ex.getMessage());
		}
		
		response.setSuccess(!isErrorFromPass);
		
		if(response.getSuccess())
		{
//			mapping
			response = getMapper().getResponseJsonFromProd(responseCalculate);
			
			WsUnitInstance unitInstance = new WsUnitInstance();
			Coverage coverageOut = new Coverage();
			try
			{
				unitInstance = responseCalculate.getReturn().getOutput().getProduct().
						getAssets().get(0).getInstances().get(0).
						getSections().get(0).getUnits().get(0).getInstances().get(0);
//				mapping
				getMapper().getResponseJsonFromUnitInstance(unitInstance, coverageOut);
				
				response.getQuote().getCoverages().add(coverageOut);
			}
			catch(NullPointerException ex)
			{
				logger.error("Get Output from PASS error, no unitinstance populated "+ex.getMessage());
			}
			catch(ArrayIndexOutOfBoundsException ex)
			{
				logger.error("Get Output from PASS error, no unitinstance populated "+ex.getMessage());
			}
		}
		
		logger.info("parseCalculatePremiumResponse out with response :"+response);
		return response;
	}
	
	
	/**
	 * Costruttore che necessita del mapper factory :<br>
	 * <i>@Autowired <br> org.mapstruct.@Mapper </i>
	 * @param mapper
	 */
	public ParsingIn(MapperResponseToDL mapperToDL)
	{
		this.mapperToDL = mapperToDL;
	}

	
	/**
	 * Ritorna il Mapper di mapperstruct
	 * <b>org.mapstruct.@Mapper</b>
	 * @return Mapper
	 * @throws NullPointerException nel caso sia null
	 */
	private MapperResponseToDL getMapper() throws NullPointerException
	{
		if(mapperToDL == null)
		{
			throw new NullPointerException("mapper null from super implementation");
		}
		
		return mapperToDL;
	}
	

}
