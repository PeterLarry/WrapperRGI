package it.cg.main.integration.easyway.parsing;


import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.ICoverage;
import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.IFigure;
import com.pass.global.CalculatePremiumResponse;

import it.cg.main.dto.InboundResponseHttpJSON;
import it.cg.main.dto.main.Quote;
import it.cg.main.process.mapping.easyway.response.MapperResponsePremiumToDL;

/**
 * Handling response recived from PASS to parse to DL
 * @author RiccardoEstia
 *
 */
@Service
public class ParsingIn
{
	private Logger logger = Logger.getLogger(getClass());
	
	/**
	 * The param is parsed using custom classes, check the errors from pass
	 * Set the "success" field if no errors into the param, else bind the errors to the errorsdto included
	 * @param CalculatePremiumResponse
	 * @return InboundResponseHttpJSON parsed
	 */
	public InboundResponseHttpJSON parseCalculatePremiumResponse(CalculatePremiumResponse responseCalculateFromPASS, Quote quoteInternal)
	{
		logger.info("parseCalculatePremiumResponse enter with parameters :"+responseCalculateFromPASS);
		InboundResponseHttpJSON responseCalculateToDL = new InboundResponseHttpJSON();
		boolean isErrorFromPass = false;
//		check for errors
		try
		{
			isErrorFromPass = responseCalculateToDL.bindPassError(responseCalculateFromPASS.getReturn().getServiceInfo());
		}
		catch(NullPointerException ex)
		{
			logger.error("Get Output from PASS error, no unitinstance populated "+ex.getMessage());
			throw new NullPointerException("Get Output from PASS error, no unitinstance populated "+ex.getMessage());
		}
		catch(ArrayIndexOutOfBoundsException ex)
		{
			logger.error("Get Output from PASS error, no unitinstance populated "+ex.getMessage());
			throw new ArrayIndexOutOfBoundsException("Get Output from PASS error, no unitinstance populated "+ex.getMessage());
		}
		
		logger.debug("Some PASS error occurs ? "+isErrorFromPass);
		responseCalculateToDL.setSuccess(!isErrorFromPass);
		
		if(responseCalculateToDL.getSuccess())
		{
//			init mapper
			MapperResponsePremiumToDL mappingResponse = new MapperResponsePremiumToDL(responseCalculateFromPASS);
//			create and set generic quote premium
			responseCalculateToDL.setQuote(mappingResponse.getInitQuoteResponse());
//			create and set coverages premium
			List<ICoverage> coveragesMapped = mappingResponse.getCoveragesFromPass();
			logger.debug("parseCalculatePremiumResponse, create : "+coveragesMapped.size() +" coverages ");
			responseCalculateToDL.getQuote().setCoverages(coveragesMapped);
//			set input figures into response
//			logger.debug("parseCalculatePremiumResponse, found : "+quoteInternal.getFigures().size()+" figures");
//			responseCalculatePremium.getQuote().setFigures(quoteInternal.getFigures());
//			figures
			List<IFigure> figuresListMapped = mappingResponse.getFiguresMapped();
			logger.debug("parseCalculatePremiumResponse, found : "+figuresListMapped.size() +" figures ");
			responseCalculateToDL.getQuote().setFigures(figuresListMapped);
		}
		
		logger.info("parseCalculatePremiumResponse out with response :"+responseCalculateToDL);
		return responseCalculateToDL;
	}
	
}
