package it.cg.main.integration.easyway.parsing;

import org.apache.log4j.Logger;

import com.pass.global.GetTechnicalData;
import com.pass.global.WsAsset;
import com.pass.global.WsAssetSection;
import com.pass.global.WsAssetUnit;
import com.pass.global.WsCalculatePremiumInput;
import com.pass.global.WsUnitInstance;

import it.cg.main.conf.mapping.easyway.EasyMapperMapstruct;
import it.cg.main.dto.RoutingDTO;

public class ParsingOut
{
	private Logger logger = Logger.getLogger(getClass());

	private EasyMapperMapstruct easyMapperMapstruct;
	
	
	/**
	 * Costruttore che necessita del mapper factory :<br>
	 * <i>@Autowired <br> org.mapstruct.@Mapper </i>
	 * @param mapper
	 */
	public ParsingOut(EasyMapperMapstruct easyMapperMapstruct)
	{
		this.easyMapperMapstruct = easyMapperMapstruct;
	}
	
	/**
	 * Ritorna il Mapper di mapperstruct
	 * <b>org.mapstruct.@Mapper</b>
	 * @return Mapper
	 * @throws NullPointerException nel caso sia null
	 */
	private EasyMapperMapstruct getMapper() throws NullPointerException
	{
		if(easyMapperMapstruct == null)
		{
			throw new NullPointerException("mapper null from super implementation");
		}
		
		return easyMapperMapstruct;
	}
	
	public WsCalculatePremiumInput getQuoteToPass(RoutingDTO request)
	{
		logger.info("richiesta" + request);
		
		WsCalculatePremiumInput responsePremium = new WsCalculatePremiumInput();
		getMapper().quoteDtoToProduct(request.getInboundRequestHttpJSON(), responsePremium);
		
		WsAsset ass = getMapper().quoteDtoToAsset(request.getInboundRequestHttpJSON());
		WsAssetSection sec = getMapper().quoteDtoToAssetSection(request.getInboundRequestHttpJSON());
		WsAssetUnit unit = new WsAssetUnit();
		getMapper().inboundToUnit(request.getInboundRequestHttpJSON(), unit);
		sec.getUnits().add(unit);
		WsUnitInstance uInst = getMapper().quoteDtoToUnitInst(request.getInboundRequestHttpJSON());
		
		responsePremium.getProduct().getAssets().add(ass);
		responsePremium.getProduct().getAssets().get(0).getInstances().get(0).getSections().add(sec);
		responsePremium.getProduct().getAssets().get(0).getInstances().get(0).getSections().get(0).getUnits().get(0).getInstances().add(uInst);
		
		logger.info("risposta" + responsePremium);
		
		return responsePremium;
	}
	
	
	
	/**
	 * Hard way GET TECHNICAL DATA
	 * @param request
	 * @return
	 */
	public GetTechnicalData getGetTechnicalDataParse(RoutingDTO request)
	{
		GetTechnicalData technicalData = new GetTechnicalData();
		
		return technicalData;
		
	}

}
