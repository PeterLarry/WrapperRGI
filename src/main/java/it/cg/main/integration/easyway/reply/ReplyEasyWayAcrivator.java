package it.cg.main.integration.easyway.reply;

import java.util.Map;

import org.apache.log4j.Logger;
import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Gateway;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.support.MessageBuilder;

import com.blog.samples.webservices.DetailService;
import com.blog.samples.webservices.servicedetail.ServiceCallResponse;
import com.pass.global.WSPassProHelloWorldOperationResponse;

import it.cg.main.conf.mapping.EasyMapperMapstruct;
import it.cg.main.integration.easyway.parsing.ParsingIn;
import it.cg.main.integration.interfaces.ActivatorHandler;

public class ReplyEasyWayAcrivator implements ActivatorHandler {

	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	DozerBeanMapperFactoryBean dozerFactory;
	@Autowired
	private EasyMapperMapstruct easyMapperMapstruct;
	
	@Gateway(requestChannel="easyChainActivatorResultChannel")
	public Message<ServiceCallResponse> gotoEasyWay(WSPassProHelloWorldOperationResponse routingDTO, @Headers Map<String, Object> headerMap)
	{
		logger.info("gotoEasyWay input DTO "+routingDTO);
		
		ServiceCallResponse callResp = new ServiceCallResponse();
		DetailService detailServ = new DetailService();
		
		ParsingIn pIn = new ParsingIn(dozerFactory, easyMapperMapstruct);
		
		detailServ = pIn.parse(routingDTO);
		
		
		callResp.setDetailService(detailServ );
		Message<ServiceCallResponse> message = MessageBuilder.withPayload(callResp).build();
		
		logger.info("gotoEasyWay response DTO "+message);
		return message;
	}


}
