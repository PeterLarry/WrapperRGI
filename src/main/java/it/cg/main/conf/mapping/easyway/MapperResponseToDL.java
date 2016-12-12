package it.cg.main.conf.mapping.easyway;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.ICoverage;
import com.pass.global.CalculatePremiumResponse;
import com.pass.global.WsUnitInstance;

import it.cg.main.dto.InboundResponseHttpJSON;

@Mapper(uses=ExternalCustomMapperResponse.class)
public interface MapperResponseToDL
{
	/**
	 * Mapping product->premium->annual to quote->premium
	 * ExternalCustomMapperResponse.getPremiumQuoteFromProdAnnual
	 * 
	 * @param CalculatePremiumResponse
	 * @return InboundResponseHttpJSON.quote.premium
	 */
	@Mappings({
//		@Mapping(source="return.output.product", target = "quote"),
		@Mapping(source="return.output.product.premium.annual", target = "quote.premium"),
	})
	InboundResponseHttpJSON getResponseJsonFromProd(CalculatePremiumResponse cp);
	
	/**
	 * Mapping UnitInstance->premium->annual to Coverage->amount
	 * (Mapstruct instantiate an interface when i give only the fields)
	 * @param WsUnitInstance
	 * @param Coverage
	 */
	@Mappings({
		@Mapping(source="premium.annual", target="amount")
	})
	public void getResponseJsonFromUnitInstance(WsUnitInstance unitInstance, @MappingTarget ICoverage coverage);

}
