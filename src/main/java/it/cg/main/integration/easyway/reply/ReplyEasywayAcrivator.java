package it.cg.main.integration.easyway.reply;

import org.apache.log4j.Logger;
import org.springframework.integration.annotation.Gateway;
import org.springframework.messaging.Message;

import com.pass.global.CalculatePremiumResponse;

import it.cg.main.dto.InboundResponseHttpJSON;
import it.cg.main.integration.easyway.parsing.ParsingIn;
import it.cg.main.integration.interfaces.ActivatorHandler;

public class ReplyEasywayAcrivator extends ActivatorHandler
{

	private Logger logger = Logger.getLogger(getClass());
	
	/**
	 * Method to access to the mapping object from PASS to DL reply
	 * @param calculateResponse
	 * @return InboundResponseHttpJSON
	 */
	@Gateway(requestChannel="easyChainActivatorResultChannel")
	public Message<InboundResponseHttpJSON> gotoEasyWay(CalculatePremiumResponse calculateResponse)
	{
		logger.info("gotoEasyWay input DTO "+calculateResponse+" Received response from Pass");
		
		logger.debug("time test response - response arrived, before parsing from PASS to DL");
		ParsingIn pIn = new ParsingIn();
		InboundResponseHttpJSON responseJson  = pIn.parseCalculatePremiumResponse(calculateResponse);
		
		Message<InboundResponseHttpJSON> message = createMessage(responseJson);
		logger.debug("time test response - after parsing response from PASS to DL");
		
//		 Sent response to Proxy – tariffDate (ratefromdate), premio netto a livello di prodotto
		try
		{
		logger.debug("Sent response to Proxy - product tariffDate="+
						calculateResponse.getReturn().getOutput().getProduct().getOpenDate().getData()
				+ " premium NET="+calculateResponse.getReturn().getOutput().getProduct().getPremium().getAnnual().getNet());
		}
		catch(NullPointerException ex)
		{
			logger.error("something null during logging into gotoEasyWay");
			logger.error(ex.getMessage()+" - "+ex.getCause());
		}
		catch(ArrayIndexOutOfBoundsException ex)
		{
			logger.error("outofbounds during logging into gotoEasyWay");
			logger.error(ex.getMessage()+" - "+ex.getCause());
		}
		logger.info("gotoEasyWay response DTO "+message);
		return message;
	}


}
