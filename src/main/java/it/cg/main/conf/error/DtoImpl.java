package it.cg.main.conf.error;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.integration.dispatcher.AggregateMessageDeliveryException;

public abstract class DtoImpl implements Serializable
{
	private static final long serialVersionUID = 2708950285561187948L;
	
	private Logger logger = Logger.getLogger(getClass());
	
//	-------------------------
	
	private List<ErrorIntegrationDTO> errorResponseDTO;

	public List<ErrorIntegrationDTO> getErrorResponseDTO()
	{
		if(this.errorResponseDTO == null)
		{
			this.errorResponseDTO = new ArrayList<ErrorIntegrationDTO>();
		}
		return errorResponseDTO;
	}

	public void setErrorResponseDTO(List<ErrorIntegrationDTO> errorResponseDTO) {
		this.errorResponseDTO = errorResponseDTO;
	}

	/**
	 * 
	 * @param errorMessage
	 */
	public void bindAggregateError(AggregateMessageDeliveryException errorMessage)
	{
		ErrorIntegrationDTO bindingErrorMesssage = new ErrorIntegrationDTO();
		
		
		
		getErrorResponseDTO().add(bindingErrorMesssage );
	}

}
