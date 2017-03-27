package it.cg.main.process;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.log4j.Logger;
import org.springframework.integration.dispatcher.AggregateMessageDeliveryException;

import com.pass.global.MsgResponseHeader;

import it.cg.main.process.error.ErrorIntegrationDTO;

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
	 * Binding error
	 * 
	 * @param errorMessage AggregateMessageDeliveryException
	 */
	public void bindAggregateError(AggregateMessageDeliveryException errorMessage)
	{
		logger.error("Enter into method for Binding with parameter: "+errorMessage);
		ErrorIntegrationDTO bindingErrorMesssage = new ErrorIntegrationDTO();
		try
		{
			bindingErrorMesssage.setErrorCauseJava(errorMessage.getCause().toString());
			bindingErrorMesssage.setErrorMessageJava(errorMessage.getMessage());
			bindingErrorMesssage.setErrorSpecCauseJava(errorMessage.getStackTrace().toString());
			bindingErrorMesssage.setErrorRootCauseJava( errorMessage.getRootCause().toString());
			
			logger.error("Error binding JAVA error : "+errorMessage.getMessage());
		}
		catch(Exception ex)
		{
			logger.error("Error during binding messages : "+ex.getMessage());
			ex.printStackTrace();
		}
		finally
		{
			logger.error("The follow is the real error into integration flush : "+errorMessage.getMessage());
			errorMessage.printStackTrace();
		}
		
		getErrorResponseDTO().add(bindingErrorMesssage);
		logger.error("Out form method of binding aggregate : "+bindingErrorMesssage);
	}
	
	/**
	 * Every pass response have a message error object,<br>
	 *  this method set the right fields to return to DL
	 * @param msgError MsgResponseHeader
	 * @return boolean, if true = there are errors, else no errors found
	 */
	public boolean bindPassError(MsgResponseHeader msgError)
	{
		boolean isErrorsFound = false;
		
		if(msgError != null)
		{
			if(msgError.isErroreOccorso())
			{
				isErrorsFound = true;
				ErrorIntegrationDTO bindingErrorMesssage = new ErrorIntegrationDTO();
				
				bindingErrorMesssage.setCustomCodiceErroreOccorso(msgError.getCodiceErroreOccorso());
				bindingErrorMesssage.setCustomDescrizioneErroreOccorso(msgError.getDescrizioneErroreOccorso());
				bindingErrorMesssage.setCustomDettagliSullErroreOccorsos(msgError.getDettagliSullErroreOccorsos());
				bindingErrorMesssage.setCustomEccezioneOccorsaSerializzata(msgError.getEccezioneOccorsaSerializzata());
				bindingErrorMesssage.setCustomExecutionId(msgError.getExecutionId());
				bindingErrorMesssage.setCustomLogs(msgError.getLogs());
				bindingErrorMesssage.setCustomTipoErroreOccorso(msgError.getTipoErroreOccorso());
				
				getErrorResponseDTO().add(bindingErrorMesssage);
				logger.info("bindPassError error from PASS : " + msgError.getDescrizioneErroreOccorso());
			}
		}
		
		return isErrorsFound;
	}
	
	@Override
	public String toString()
	{
		return ReflectionToStringBuilder.toString(this);
	}

}
