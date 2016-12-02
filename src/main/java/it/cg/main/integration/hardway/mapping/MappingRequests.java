package it.cg.main.integration.hardway.mapping;

import org.apache.log4j.Logger;

public class MappingRequests
{
	private Logger logger = Logger.getLogger(getClass());
	
//	private GetTechnicalDataMapstruct getTechnicalDataMapstruct;
//	
//	
//	public GetFactors parseGetTecnicalDataFactors(GetTechnicalDataResponse techinicalData )
//	{
//		logger.info("into parseGetTecnicalDataFactors with request : "+techinicalData);
//		GetFactors getFactors = new GetFactors();
//		getFactors.setArg0(new MsgGetFactorsRequest());
//		getFactors.getArg0().setInput(new WsGetFactorsInput());
//		getFactors.getArg0().getInput().setProduct(new WsProduct());
//		
//		getMapper().parseGetFactors(techinicalData.getReturn().getOutput().getProduct(), getFactors.getArg0().getInput().getProduct());
//		
//		logger.info("return parseGetTecnicalDataFactors with response : "+getFactors);
//		return getFactors;
//	}
//	
//	/**
//	 * getFactorsResponse -> request getCaluses 
//	 * @param factorsData
//	 * @return
//	 */
//	public GetClauses parseGetFactorsGetClauses(GetFactorsResponse factorsData )
//	{
//		logger.info("into parseGetTecnicalDataFactors with request : "+factorsData);
//		GetClauses getClauses = new GetClauses();
//		getClauses.setArg0(new MsgGetClausesRequest());
//		getClauses.getArg0().setInput(new WsGetClausesInput());
//		getClauses.getArg0().getInput().setProduct(new WsProduct());
//		
//		getMapper().parseGetFactors(factorsData.getReturn().getOutput().getProduct(), getClauses.getArg0().getInput().getProduct());
//		
//		logger.info("return parseGetFactorsGetClauses with response : "+getClauses);
//		return getClauses;
//	}
//	
//	/**
//	 * Costruttore che necessita del mapper factory :<br>
//	 * <i>@Autowired <br> org.mapstruct.@Mapper </i>
//	 * @param mapper
//	 */
//	public MappingRequests(GetTechnicalDataMapstruct easyMapperMapstruct)
//	{
//		this.getTechnicalDataMapstruct = easyMapperMapstruct;
//	}
//
//	
//	/**
//	 * Ritorna il Mapper di mapperstruct
//	 * <b>org.mapstruct.@Mapper</b>
//	 * @return Mapper
//	 * @throws NullPointerException nel caso sia null
//	 */
//	private GetTechnicalDataMapstruct getMapper() throws NullPointerException
//	{
//		if(getTechnicalDataMapstruct == null)
//		{
//			throw new NullPointerException("mapper null from super implementation");
//		}
//		
//		return getTechnicalDataMapstruct;
//	}
	


}
