package it.cg.main.integration.easyway.parsing;

import com.pass.global.*;
import org.apache.log4j.Logger;

import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.IFigure;

import it.cg.main.dto.RoutingDTO;
import it.cg.main.dto.main.Quote;
import it.cg.main.process.mapping.easyway.MapperAssetSectionToPASS;
import it.cg.main.process.mapping.easyway.MapperRequestToPASS;
import it.cg.main.process.mapping.utilities.MapperUtilityToPASS;

import java.util.List;

/**
 * Handling request From DL , parsing to PASS
 * @author RiccardoEstia
 *
 */
public class ParsingOut
{
	private Logger logger = Logger.getLogger(getClass());

	private MapperRequestToPASS easyMapperMapstruct;
	private MapperAssetSectionToPASS mapperAssetSection;
	
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
	
	/**
	 * Parse the quote request for the PASS calculate premium request
	 * @param request
	 * @return WsCalculatePremiumInput from Quote
	 */
	public WsCalculatePremiumInput getQuoteToPass(RoutingDTO request)
	{
		logger.info("into getQuoteToPass Request form DL to parse : " + request);

		logger.debug("getQuoteToPass Begin Parsing to PASS");
		
		Quote quoteRequest = request.getInboundRequestHttpJSON().getInboundQuoteDTO();
		TypeBooleano tybF = new TypeBooleano();
		tybF.setBoolean(false);
		TypeBooleano tybT = new TypeBooleano();
		tybT.setBoolean(true);
		mapperAssetSection = new MapperAssetSectionToPASS();
		
//		mapping instance
		WsCalculatePremiumInput responseCalculatePremium = new WsCalculatePremiumInput();
		MapperUtilityToPASS mapperUtility = new MapperUtilityToPASS();
		WsAsset asset = mapperUtility.getInitWsAsset(quoteRequest);
		logger.debug("getQuoteToPass wsAsset created");
		IFigure figureOwner = mapperUtility.getFigureOwner(quoteRequest); 
		logger.debug("getQuoteToPass figureOwner fount");
		
		try
		{
//			populate product
			getMapper().quoteDtoToProduct(request.getInboundRequestHttpJSON(), responseCalculatePremium);
			logger.debug("getQuoteToPass wsProduct setted");
//			populate assetInstance
			getMapper().quoteDtoToAsset(request.getInboundRequestHttpJSON(), asset);
			logger.debug("getQuoteToPass wsAssetInstance setted");
//			populate assetUnit and UnitInstance
			mapperAssetSection.getAssetSections(request.getInboundRequestHttpJSON(), asset.getInstances().get(0),
													responseCalculatePremium.getProduct().getCode(), figureOwner);
			writeSummaryAssetSection(asset.getInstances().get(0));
			logger.debug("getQuoteToPass wsAssetUnit and wsUnitInstance setted");
			responseCalculatePremium.getProduct().getAssets().add(asset);
			
			logger.debug("getQuoteToPass End Parsing to PASS");
		}
		catch(NullPointerException ex)
		{
			logger.error("Error getQuoteToPass during create asset section "+ex.getMessage()+" - "+ ex.getCause());
			ex.printStackTrace();
		}
		catch(ArrayIndexOutOfBoundsException ex)
		{
			logger.error("Error getQuoteToPass during create asset section "+ex.getMessage()+" - "+ ex.getCause());
			ex.printStackTrace();
		}
		
		logger.info("out getQuoteToPass PASS object parsed : " + responseCalculatePremium);
		return responseCalculatePremium;
	}

	/**
	 * Write into the log a summary of AssetSection and AssetUnit
	 * @param wsAssetInstance
	 */
	private void writeSummaryAssetSection(WsAssetInstance wsAssetInstance)
	{
		logger.info("writeSummaryAssetSection begin");

		if(wsAssetInstance.getSections() != null && !wsAssetInstance.getSections().isEmpty())
		{
			List<WsAssetSection> assetSectionsList = wsAssetInstance.getSections();
			logger.debug("writeSummaryAssetSection AssetSection added : "+assetSectionsList.size());
			String sectionAddedCodesAndSelected = "" ;
			for(WsAssetSection assetSectionTemp : assetSectionsList)
			{
				String assetUnitSummary = "";
				for(WsAssetUnit assetUnitTemp : assetSectionTemp.getUnits())
				{
					assetUnitSummary += assetSectionTemp.getCode()+"."+assetUnitTemp.getCode() +" = "+assetUnitTemp.getSelection().isBoolean()+" ";
				}
				sectionAddedCodesAndSelected += assetUnitSummary +"||" ;
			}
			logger.debug(sectionAddedCodesAndSelected);
		}


		logger.info("writeSummaryAssetSection end");
	}

	/**
	 * Create  msgFor more output factors 
	 * @return MsgCalculatePremiumRequest
	 */
	public MsgCalculatePremiumRequest getQuoteToMsgCalculate()
	{
		MsgCalculatePremiumRequest msgResponse = new MsgCalculatePremiumRequest();
		logger.info("into getQuoteToMsgCalculate with no request ");
		
		msgResponse.setServiceInfo(new MsgRequestHeader());
		TypeProperty returnFactorsProperty = new TypeProperty();
		
		returnFactorsProperty.setChiave("RETURN_FACTORS");
		returnFactorsProperty.setValore("true");
		
		msgResponse.getServiceInfo().getProperties().add(returnFactorsProperty);
		logger.debug("getQuoteToMsgCalculate setted new property : "+returnFactorsProperty.getChiave() +" -> "+returnFactorsProperty.getValore());
//		____________________________
		returnFactorsProperty = new TypeProperty();
		
		returnFactorsProperty.setChiave("MANAGE_TAXTYPE_TARIFFARTICLE");
		returnFactorsProperty.setValore("true");
		
		msgResponse.getServiceInfo().getProperties().add(returnFactorsProperty);
		logger.debug("getQuoteToMsgCalculate setted new property : "+returnFactorsProperty.getChiave() +" -> "+returnFactorsProperty.getValore());
		
		logger.info("out getQuoteToMsgCalculate with response : "+msgResponse);
		return msgResponse;
	}


}
