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
	
	public Message<?> createErrorResponse(AggregateMessageDeliveryException message)
	{
		logger.info("Binding Error for exception : "+message);
		
//		ServiceCallResponse callResp = new ServiceCallResponse();
//		DetailService detailService = new DetailService();
//		detailService.setReturn("ERRORE WEB channel");
//		callResp.setDetailService(detailService );
//		Message<ServiceCallResponse> messageResponse = MessageBuilder.withPayload(callResp).build();
		InboundResponseHttpJSON response = new InboundResponseHttpJSON();
		ErrorIntegrationDTO errorResponseDTO = new ErrorIntegrationDTO();
		
		errorResponseDTO.setErrorMessage("Message ERRRO");
		errorResponseDTO.setErrorType("Type ERROR");
//		response.setErrorResponseDTO(errorResponseDTO);
		
		Message<InboundResponseHttpJSON> messageResponse = MessageBuilder.withPayload(response).build();
		
		logger.info("Return Error for exception : "+messageResponse);
		return messageResponse;
		
	}
	
}
