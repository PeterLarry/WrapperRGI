package it.cg.main.integration.easyway.parsing;


import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.ICoverage;
import com.pass.global.CalculatePremiumResponse;

import it.cg.main.dto.InboundResponseHttpJSON;
import it.cg.main.process.mapping.easyway.response.MapperResponsePremiumToDL;

/**
 * Handling recived response from PASS to parse to DL
 * @author RiccardoEstia
 *
 */
@Service
public class ParsingIn
{
	private Logger logger = Logger.getLogger(getClass());
	
	/**
	 * The param is parsed using mapstruct custom classes, check the errors from pass
	 * Set the "success" field if no errors into the param, else bind the errors to the errorsdto included
	 * @param CalculatePremiumResponse
	 * @return InboundResponseHttpJSON parsed
	 */
	public InboundResponseHttpJSON parseCalculatePremiumResponse(CalculatePremiumResponse responseCalculate)
	{
		logger.info("parseCalculatePremiumResponse enter with parameters :"+responseCalculate);
		InboundResponseHttpJSON responseCalculatePremium = new InboundResponseHttpJSON();
		boolean isErrorFromPass = false;
//		check for errors
		try
		{
			isErrorFromPass = responseCalculatePremium.bindPassError(responseCalculate.getReturn().getServiceInfo());
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
		
		responseCalculatePremium.setSuccess(!isErrorFromPass);
		
		if(responseCalculatePremium.getSuccess())
		{
			MapperResponsePremiumToDL mappingResponse = new MapperResponsePremiumToDL();
//			create and set generic quote premium
			responseCalculatePremium.setQuote(mappingResponse.getInitQuoteResponse(responseCalculate));
//			create and set coverages premium
			List<ICoverage> coveragesMapped = mappingResponse.getCoveragesFromPass(responseCalculate);
			responseCalculatePremium.getQuote().setCoverages(coveragesMapped);
		}
		
		logger.info("parseCalculatePremiumResponse out with response :"+responseCalculatePremium);
		return responseCalculatePremium;
	}
	
}
