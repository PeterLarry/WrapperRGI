package it.cg.main.integration.easyway.parsing;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.pass.global.CalculatePremiumResponse;
import com.pass.global.WsUnitInstance;

import it.cg.main.conf.mapping.easyway.MapperResponseToDL;
import it.cg.main.dto.InboundResponseHttpJSON;

/**
 * Handling recived resposnse from PASS to parse Tto DL
 * @author RiccardoEstia
 *
 */
@Service
public class ParsingIn
{
	private Logger logger = Logger.getLogger(getClass());
	
	private MapperResponseToDL mapperToDL;
	
	public InboundResponseHttpJSON parseCalculatePremiumResponse(CalculatePremiumResponse responseCalculate)
	{
		logger.info("parseCalculatePremiumResponse enter with parameters :"+responseCalculate);
		InboundResponseHttpJSON response = getMapper().getResponseJsonFromProd(responseCalculate);
		WsUnitInstance unitInstance = new WsUnitInstance();
		try
		{
			unitInstance = responseCalculate.getReturn().getOutput().getProduct().getAssets().get(0).getInstances().get(0).getSections().get(0).getUnits().get(0).getInstances().get(0);
		}
		catch(NullPointerException ex)
		{
			logger.error("Get Output from PASS error, no unitinstance populated "+ex.getMessage());
		}
		catch(ArrayIndexOutOfBoundsException ex)
		{
			logger.error("Get Output from PASS error, no unitinstance populated "+ex.getMessage());
		}
		
//		getMapper().getResponseJsonFromUnitInstance(unitInstance , response);
		
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
