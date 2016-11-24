package it.cg.main.conf.mapping.easyway;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Qualifier;
import org.springframework.stereotype.Service;

import com.pass.global.WsFactor;
import com.pass.global.WsPremiumGroup;
import com.pass.global.WsVehicle;

import it.cg.main.dto.inbound.InboundContextDTO;
import it.cg.main.dto.inbound.InboundPremiumDTO;
import it.cg.main.dto.inbound.InboundQuoteDTO;
import it.cg.main.dto.inbound.InboundVehicleDTO;
import it.cg.main.integration.mapper.enumerations.WsProductFactorsENUM;

@Service
public class CustomMapperInbound
{
//	@Qualifier
//	@Target(ElementType.TYPE)
//	@Retention(RetentionPolicy.CLASS)
//	public @interface TitleTranslator {
//	}
//	
//	@Qualifier
//	@Target(ElementType.METHOD)
//	@Retention(RetentionPolicy.CLASS)
//	public @interface TitleTranslator {
//	}
//	
//	@Qualifier
//	@Target(ElementType.METHOD)
//	@Retention(RetentionPolicy.CLASS)
//	public @interface TitleTranslator {
//	}
	
	
	
	/**
	 * Populate ONLY list factors for wsproduct
	 * @param quote
	 * @return
	 */
	public List<WsFactor> figureToListWsFactor(InboundQuoteDTO quote)
	{
		ArrayList<WsFactor> factProp = new ArrayList<WsFactor>();
		
		WsFactor wsFactor = new WsFactor();

		if(quote.getAffinity() != null)
		{
			wsFactor.setCode(WsProductFactorsENUM.FACTOR_1AFF.value());
			wsFactor.setValue(quote.getAffinity());
			factProp.add(wsFactor);
		}
//		-------
		if(quote.getNumberOfYoungDriver() != null)
		{
			wsFactor = new WsFactor();
			wsFactor.setCode(WsProductFactorsENUM.FACTOR_2CM26.value());
			wsFactor.setValue(quote.getNumberOfYoungDriver().toString());
			factProp.add(wsFactor);
		}
//		-------
		return factProp;
		
		
	}
//	
//	public List<WsFactor> figurToListWsFactor(InboundQuoteDTO quote){
////		
////		
//		ArrayList<WsFactor> factProp = new ArrayList<WsFactor>();
////		
//		WsFactor wsFactor = new WsFactor();
////
//		wsFactor.setCode(WsProductFactorsENUM.FACTOR_1AFF.value());
//		wsFactor.setValue(quote.getAffinity());
//		factProp.add(wsFactor);
////		
//		wsFactor = new WsFactor();
//		wsFactor.setCode(WsProductFactorsENUM.FACTOR_2CM26.value());
//		wsFactor.setValue(quote.getNumberOfYoungDriver()+"");
//		factProp.add(wsFactor);
//		
//		return factProp;
		
		
//	}
	
	public WsPremiumGroup changeInstance (InboundPremiumDTO pre){
		
		return new WsPremiumGroup();
	}
	
	public WsVehicle changeInstance (InboundVehicleDTO vehicle){
		
		return new WsVehicle();
	}

}
