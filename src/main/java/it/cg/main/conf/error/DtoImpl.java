package it.cg.main.conf.error;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.log4j.Logger;
import org.springframework.integration.dispatcher.AggregateMessageDeliveryException;

public abstract class DtoImpl implements Serializable
{
	private static final long serialVersionUID = 2708950285561187948L;
	
	private Logger logger = Logger.getLogger(getClass());
	
	
	private List<ErrorIntegrationDTO> errorResponseDTO;

	public List<ErrorIntegrationDTO> getErrorResponseDTO()
	{
		if(this.errorResponseDTO == null)
		{
			this.errorResponseDTO = new ArrayList<ErrorIntegrationDTO>();
		}
		return errorResponseDTO;
	}

	public void setErrorResponseDTO(List<ErrorIntegrationDTO> errorResponseDTO)
	{
		this.errorResponseDTO = errorResponseDTO;
	}

	/**
	 * Aggiungo la mappatura di un errore nell'oggetto custom di errore del dto.
	 * 
	 * @param errorMessage
	 */
	public void bindAggregateError(AggregateMessageDeliveryException errorMessage)
	{
		logger.error("Enter into method for Binding with parameter: "+errorMessage);
		ErrorIntegrationDTO bindingErrorMesssage = new ErrorIntegrationDTO();
		try
		{
			bindingErrorMesssage.setErrorMessageJava(errorMessage.getCause().toString());
			bindingErrorMesssage.setErrorMessageJava(errorMessage.getMessage());
			bindingErrorMesssage.setErrorSpecCauseJava(errorMessage.getMostSpecificCause().toString());
			bindingErrorMesssage.setErrorRootCauseJava( errorMessage.getRootCause().toString());
			logger.error("Error binding : "+errorMessage.getMessage());
		}
		catch(Exception ex)
		{
			logger.error("Error during binding messages : "+ex.getMessage());
			ex.printStackTrace();
		}
		finally
		{
			logger.error("The follow is the real error into integration flush : ");
			errorMessage.printStackTrace();
		}
		
		getErrorResponseDTO().add(bindingErrorMesssage);
		logger.error("Out form method of binding aggregate : "+bindingErrorMesssage);
	}
	
	@Override
	public String toString()
	{
		return ReflectionToStringBuilder.toString(this);
	}

}
