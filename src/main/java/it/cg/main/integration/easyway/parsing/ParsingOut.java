package it.cg.main.integration.easyway.parsing;

import org.apache.log4j.Logger;

import com.pass.global.GetTechnicalData;
import com.pass.global.WsFactor;
import com.pass.global.WsProduct;

import it.cg.main.conf.mapping.easyway.EasyMapperMapstruct;
import it.cg.main.dto.RoutingDTO;
import it.cg.main.dto.inbound.InboundQuoteDTO;

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
	
	public WsProduct getQuoteToWsProduct(RoutingDTO request)
	{
		logger.info("richiesta" + request);
		
		WsProduct response = getMapper().quoteDtoToFactor(request.getInboundRequestHttpJSON().getInboundQuoteDTO());
		
		logger.info("risposta" + response);
		
		return response;
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
