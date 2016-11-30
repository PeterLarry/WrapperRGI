package it.cg.main.conf.mapping.easyway;

import org.springframework.stereotype.Service;

import com.pass.global.TypeReal;
import com.pass.global.WsProduct;

import it.cg.main.dto.inbound.InboundQuoteDTO;

@Service
public class ExternalCustomMapperResponse
{
	
	public InboundQuoteDTO getCustomOutput(WsProduct prod)
	{
		InboundQuoteDTO response = new InboundQuoteDTO();
		
		
		
		return response;
	}
	
	
	public TypeReal boolToTypeBool(Double doub) {
		
		TypeReal typeR = new TypeReal();
		
		typeR.setReal(doub);
		
		return typeR;
	}
}
