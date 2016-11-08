package it.cg.main.conf.error;

import org.apache.log4j.Logger;
import org.springframework.integration.dispatcher.AggregateMessageDeliveryException;
import org.springframework.integration.router.ErrorMessageExceptionTypeRouter;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import it.cg.main.dto.InboundResponseHttpJSON;

@Service
public class ErrorMessageHandler  extends ErrorMessageExceptionTypeRouter
{
	private Logger logger = Logger.getLogger(getClass());
	
	/**
	 * Dato un errore nel flusso integration, questi viene bindato
	 * e ritornato nell'oggetto di response.
	 * @param message
	 * @return
	 */
	public Message<?> createErrorResponse(AggregateMessageDeliveryException message)
	{
		logger.info("Binding Error for exception : "+message);
		
		InboundResponseHttpJSON response = new InboundResponseHttpJSON();
//		attuate the binding
		response.bindAggregateError(message);
		
		Message<InboundResponseHttpJSON> messageResponse = MessageBuilder.withPayload(response).build();
		
		logger.info("Return Error for exception : "+messageResponse);
		return messageResponse;
		
	}
	
}
