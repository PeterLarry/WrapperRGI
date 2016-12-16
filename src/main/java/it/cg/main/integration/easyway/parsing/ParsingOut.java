package it.cg.main.integration.easyway.parsing;

import org.apache.log4j.Logger;

import com.pass.global.GetTechnicalData;
import com.pass.global.TypeBooleano;
import com.pass.global.WsAsset;
import com.pass.global.WsCalculatePremiumInput;
import com.pass.global.WsUnitInstance;

import it.cg.main.conf.mapping.easyway.ExternalCustomMapperToPASSEasy;
import it.cg.main.conf.mapping.easyway.MapperRequestToPASS;
import it.cg.main.dto.RoutingDTO;

public class ParsingOut
{
	private Logger logger = Logger.getLogger(getClass());

	private MapperRequestToPASS easyMapperMapstruct;
	private ExternalCustomMapperToPASSEasy mapperEasy;
	
	
	/**
	 * Costruttore che necessita del mapper factory :<br>
	 * <i>@Autowired <br> org.mapstruct.@Mapper </i>
	 * @param mapper
	 */
	public ParsingOut(MapperRequestToPASS easyMapperMapstruct)
	{
		this.easyMapperMapstruct = easyMapperMapstruct;
	}
	
	/**
	 * Ritorna il Mapper di mapperstruct
	 * <b>org.mapstruct.@Mapper</b>
	 * @return Mapper
	 * @throws NullPointerException nel caso sia null
	 */
	private MapperRequestToPASS getMapper() throws NullPointerException
	{
		if(easyMapperMapstruct == null)
		{
			throw new NullPointerException("mapper null from super implementation");
		}
		
		return easyMapperMapstruct;
	}
	
	public WsCalculatePremiumInput getQuoteToPass(RoutingDTO request)
	{
		logger.info("Request form DL to parse : " + request);

		TypeBooleano tybF = new TypeBooleano();
		tybF.setBoolean(false);
		TypeBooleano tybT = new TypeBooleano();
		tybT.setBoolean(true);
		mapperEasy = new ExternalCustomMapperToPASSEasy();
		
//		mapping instance
		WsCalculatePremiumInput responseCalculatePremium = new WsCalculatePremiumInput();
		WsAsset asset = new WsAsset();
		WsUnitInstance uInst = new WsUnitInstance();
		try
		{
//			populate product
			getMapper().quoteDtoToProduct(request.getInboundRequestHttpJSON(), responseCalculatePremium);
//			populate assetInstance
			getMapper().quoteDtoToAsset(request.getInboundRequestHttpJSON(), asset);
//			populate assetSection and assetUnit and UnitInstance
			mapperEasy.getAssetSections(request.getInboundRequestHttpJSON(), asset.getInstances().get(0), responseCalculatePremium.getProduct().getCode());
		}
		catch(NullPointerException ex)
		{
			logger.error("Error during create asset section "+ex.getMessage()+"\n"+ ex.getCause());
			ex.printStackTrace();
		}
		catch(ArrayIndexOutOfBoundsException ex)
		{
			logger.error("Error during create asset section "+ex.getMessage()+"\n"+ ex.getCause());
			ex.printStackTrace();
		}
		

//		TODO da rimappare su custom xx11
//		uInst.getClauses().get(0).setCode("RCA001");
//		uInst.getClauses().get(0).setSelected(tybF);
//		
//		responseCalculatePremium.getProduct().getAssets().add(asset);
//		responseCalculatePremium.getProduct().getAssets().get(0).getInstances().add(asset.getInstances().get(0));
//		responseCalculatePremium.getProduct().getAssets().get(0).getInstances().get(0).getSections().get(0).getUnits().get(0).getInstances().add(uInst);
//		
		logger.info("PASS object parsed : " + responseCalculatePremium);
		return responseCalculatePremium;
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
