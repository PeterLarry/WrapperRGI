package it.cg.main.conf.mapping.easyway;

import com.pass.global.WsAsset;

import it.cg.main.dto.main.Quote;

public class MapperAssetToPASS
{
	
	/**
	 * Set inizialized asset code with risk type (wrapper code)
	 * @param quoteRequest
	 * @return
	 */
	public WsAsset getInitWsAsset(Quote quoteRequest)
	{
		WsAsset assetResponse = new  WsAsset();

		assetResponse.setCode(quoteRequest.getContext().getRiskType().getWrapperCode());
		
		return assetResponse;
	}

}
