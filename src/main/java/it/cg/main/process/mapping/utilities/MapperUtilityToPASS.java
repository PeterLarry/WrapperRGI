package it.cg.main.process.mapping.utilities;

import java.util.List;

import org.apache.log4j.Logger;

import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.IFigure;
import com.mapfre.engines.rating.common.enums.EnumRole;
import com.pass.global.WsAsset;

import it.cg.main.dto.main.Quote;

public class MapperUtilityToPASS
{
	private Logger logger = Logger.getLogger(getClass());

	/**
	 * Set inizialized asset code with risk type (wrapper code)
	 * @param requestQuote
	 * @return
	 */
	public WsAsset getInitWsAsset(Quote requestQuote)
	{
		logger.info("into getInitWsAsset with request : "+requestQuote);
		WsAsset assetResponse = new  WsAsset();

		assetResponse.setCode(requestQuote.getContext().getRiskType().getWrapperCode());
		logger.debug("getInitWsAsset init wsAsset with code:"+assetResponse.getCode());
		
		logger.info("out getInitWsAsset with response : "+assetResponse);
		return assetResponse;
	}
	
	/**
	 * Get the figure type <b>EnumRole.OWNER</b> 
	 * @param requestQuote
	 * @return null if no OWNER found
	 */
	public IFigure getFigureOwner(Quote requestQuote)
	{
		logger.info("into getFigureOwner with request : "+requestQuote);
		
		IFigure responseFigure = null;
		
		List<IFigure> listFigure = requestQuote.getFigures();
		
		for (IFigure iFigureTemp : listFigure)
		{
			if(EnumRole.OWNER.equals(iFigureTemp.getRole()))
			{
				responseFigure = iFigureTemp;
				logger.debug("found Figure OWNER : "+responseFigure);
				break;
			}
		}
		
		logger.info("out getFigureOwner with response : "+responseFigure);
		return responseFigure;
	}

}
