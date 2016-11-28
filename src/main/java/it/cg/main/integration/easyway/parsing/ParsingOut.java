package it.cg.main.integration.easyway.parsing;

import org.apache.log4j.Logger;

import com.pass.global.GetTechnicalData;
import com.pass.global.WsCalculatePremiumInput;
import com.pass.global.WsProduct;

import it.cg.main.conf.mapping.easyway.EasyMapperMapstruct;
import it.cg.main.dto.RoutingDTO;

public class ParsingOut
{
	private Logger logger = Logger.getLogger(getClass());

	private EasyMapperMapstruct easyMapperMapstruct;
	
	
	/**
	 * Costruttore che necessita del mapper factory :<br>
	 * <i>@Autowired <br> org.mapstruct.@Mapper </i>
	 * @param mapper
	 */
	public ParsingOut(EasyMapperMapstruct easyMapperMapstruct)
	{
		this.easyMapperMapstruct = easyMapperMapstruct;
	}
	
	/**
	 * Ritorna il Mapper di mapperstruct
	 * <b>org.mapstruct.@Mapper</b>
	 * @return Mapper
	 * @throws NullPointerException nel caso sia null
	 */
	private EasyMapperMapstruct getMapper() throws NullPointerException
	{
		if(easyMapperMapstruct == null)
		{
			throw new NullPointerException("mapper null from super implementation");
		}
		
		return easyMapperMapstruct;
	}
	
	public WsCalculatePremiumInput getQuoteToWsProduct(RoutingDTO request)
	{
		logger.info("richiesta" + request);
		
		WsCalculatePremiumInput responsePro = getMapper().quoteDtoToFactor(request.getInboundRequestHttpJSON());
		
		logger.info("risposta" + responsePro);
		
		return responsePro;
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
