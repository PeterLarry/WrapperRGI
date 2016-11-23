package it.cg.main.integration.easyway.parsing;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.pass.global.WSPassProHelloWorldOperationResponse;

import it.cg.main.conf.mapping.EasyMapperMapstruct;
import it.cg.main.dto.InboundResponseHttpJSON;

@Service
public class ParsingIn
{
	private Logger logger = Logger.getLogger(getClass());
	
	private EasyMapperMapstruct easyMapperMapstruct;
	
	/**
	 * 
	 * @param routingDTO
	 * @return InboundResponseHttpJSON
	 */
	public InboundResponseHttpJSON parse(WSPassProHelloWorldOperationResponse routingDTO)
	{
		logger.info("parse input DTO "+routingDTO);
		
		InboundResponseHttpJSON response = new InboundResponseHttpJSON();
//		Mapstruct test
		
		response = getMapper().helloWorldToDetailService(routingDTO);
		
		logger.info("parse output DTO "+response);
		return response;
	}
	
	
	/**
	 * Costruttore che necessita del mapper factory :<br>
	 * <i>@Autowired <br> org.mapstruct.@Mapper </i>
	 * @param mapper
	 */
	public ParsingIn(EasyMapperMapstruct easyMapperMapstruct)
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
	

}
