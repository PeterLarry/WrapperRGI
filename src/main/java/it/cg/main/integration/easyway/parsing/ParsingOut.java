package it.cg.main.integration.easyway.parsing;

import org.apache.log4j.Logger;

import com.pass.global.GetTechnicalData;
import com.pass.global.TypeBooleano;
import com.pass.global.WsAsset;
import com.pass.global.WsCalculatePremiumInput;

import it.cg.main.conf.mapping.easyway.MapperAssetSectionToPASS;
import it.cg.main.conf.mapping.easyway.MapperAssetToPASS;
import it.cg.main.conf.mapping.easyway.MapperRequestToPASS;
import it.cg.main.dto.RoutingDTO;
import it.cg.main.dto.main.Quote;

public class ParsingOut
{
	private Logger logger = Logger.getLogger(getClass());

	private MapperRequestToPASS easyMapperMapstruct;
	private MapperAssetSectionToPASS mapperEasy;
	private boolean isEnableTariffFormulaLogActive;
	
	
	/**
	 * Costruttore che necessita del mapper factory :<br>
	 * <i>@Autowired <br> org.mapstruct.@Mapper </i>
	 * @param mapper
	 */
	public ParsingOut(MapperRequestToPASS easyMapperMapstruct, boolean isEnableTariffFormulaLogActive)
	{
		this.easyMapperMapstruct = easyMapperMapstruct;
		this.isEnableTariffFormulaLogActive = isEnableTariffFormulaLogActive;
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

		Quote quoteRequest = request.getInboundRequestHttpJSON().getInboundQuoteDTO();
		TypeBooleano tybF = new TypeBooleano();
		tybF.setBoolean(false);
		TypeBooleano tybT = new TypeBooleano();
		tybT.setBoolean(true);
		mapperEasy = new MapperAssetSectionToPASS();
		
//		mapping instance
		WsCalculatePremiumInput responseCalculatePremium = new WsCalculatePremiumInput();
		MapperAssetToPASS mapAsset = new MapperAssetToPASS();
		WsAsset asset = mapAsset.getInitWsAsset(quoteRequest);
		
		try
		{
//			populate product
			getMapper().quoteDtoToProduct(request.getInboundRequestHttpJSON(), responseCalculatePremium);
//			populate assetInstance
			getMapper().quoteDtoToAsset(request.getInboundRequestHttpJSON(), asset);
//			populate assetUnit and UnitInstance
			mapperEasy.getAssetSections(request.getInboundRequestHttpJSON(), asset.getInstances().get(0),
									responseCalculatePremium.getProduct().getCode(), isEnableTariffFormulaLogActive);
			responseCalculatePremium.getProduct().getAssets().add(asset);
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
