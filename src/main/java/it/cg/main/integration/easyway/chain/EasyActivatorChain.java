package it.cg.main.integration.easyway.chain;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Gateway;
import org.springframework.stereotype.Component;

import com.pass.global.CalculatePremium;
import com.pass.global.MsgCalculatePremiumRequest;
import com.pass.global.TypeBooleano;
import com.pass.global.TypeData;
import com.pass.global.WsCalculatePremiumInput;

import it.cg.main.conf.mapping.easyway.EasyMapperMapstruct;
import it.cg.main.dto.RoutingDTO;
import it.cg.main.integration.easyway.parsing.ParsingOut;

@Component
public class EasyActivatorChain
{
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private EasyMapperMapstruct easyMapperMapstruct;
	
	/**
	 * Parsing Input PASS
	 * @param routingDTO
	 * @param headerMap
	 * @return
	 * @throws DatatypeConfigurationException 
	 */
	@Gateway
	public CalculatePremium gotoEasyCallChain(RoutingDTO request) throws DatatypeConfigurationException
	{
		logger.info("Into gotoEasyCall call , input="+request);
		
		 TypeBooleano tB = new TypeBooleano();
		 tB.setBoolean(true);
		 
		 TypeBooleano tBF = new TypeBooleano();
		 tBF.setBoolean(false);
		 
		 TypeData dataOpenTypeData  = new TypeData(); 
		 GregorianCalendar c = new GregorianCalendar();
		 c.setTime(new Date()); // dopo ottobre
		 XMLGregorianCalendar dataOpen = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		 dataOpenTypeData.setData(dataOpen);
		 
//		parser
		ParsingOut pout = new ParsingOut(easyMapperMapstruct);
		WsCalculatePremiumInput calcPremium = pout.getQuoteToPass(request);
		
		CalculatePremium cp = new CalculatePremium();
		cp.setArg0(new MsgCalculatePremiumRequest());
		cp.getArg0().setInput(calcPremium);

 		logger.info("Into method gotoEasyCall , output="+cp);
		return cp;
	}


}
