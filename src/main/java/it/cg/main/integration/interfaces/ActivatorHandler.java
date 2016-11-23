package it.cg.main.integration.interfaces;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

public abstract class ActivatorHandler
{
	
	/**
	 * Create the message with the payload for <b>integration messages</b><br>
	 * If the <b>object</b> is <b>null</b>, the return will be <b>null</b>
	 * @param <T>
	 * @param class1 
	 * @return  Message<?>
	 */
	@SuppressWarnings("unchecked")
	protected <T> Message<T> createMessage(Object objIntoMessage)
	{
		Message<T> response = null ;
		if(objIntoMessage != null)
		{
			response = (Message<T>) MessageBuilder.withPayload(objIntoMessage).build();
		}
		
		return response;
	}
	
	/**
	 * Create a spring message with the parameters into header message.<br>
	 * If <b>headerKey</b> parameter is already present, <b>headerValue</b> overwrite the current value<br>
	 * If the <b>headerValue</b> is null, the parameter <b>headerKey</b> will be removed.
	 * @param objIntoMessage
	 * @param headerKey
	 * @param headerValue
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <T> Message<T> createMessageWithHeader(Object objIntoMessage, String headerKey, String headerValue)
	{
		Message<T> response = null ;
		if(objIntoMessage != null)
		{
			response = (Message<T>) MessageBuilder.withPayload(objIntoMessage)
						.setHeader(headerKey,headerValue)
						.build();
		}
		
		return response;
	}

}
