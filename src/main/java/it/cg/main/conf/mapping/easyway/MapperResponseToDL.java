package it.cg.main.conf.mapping.easyway;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import com.pass.global.CalculatePremiumResponse;
import com.pass.global.WsUnitInstance;

import it.cg.main.dto.InboundResponseHttpJSON;

@Mapper(uses=ExternalCustomMapperResponse.class)
public interface MapperResponseToDL
{
	@Mappings({
		@Mapping(source="return.output.product", target = "inboundQuoteDTO"),
		@Mapping(source="return.output.product.premium.annual.net", target = "inboundQuoteDTO.premium.net"),
		@Mapping(source="return.output.product.premium.annual.gross", target = "inboundQuoteDTO.premium.gross"),
		@Mapping(source="return.output.product.premium.annual.taxes", target = "inboundQuoteDTO.premium.tax")
	})
	public InboundResponseHttpJSON getResponseJsonFromProd(CalculatePremiumResponse cp);
	
	@Mappings({
		@Mapping(source="premium.annual.net", target="inboundQuoteDTO.coverages.amount.net"),
		@Mapping(source="premium.annual.gross", target="inboundQuoteDTO.coverages.amount.gross"),
		@Mapping(source="premium.annual.taxes", target="inboundQuoteDTO.coverages.amount.tax")
	})
	public void getResponseJsonFromUnitInstance(WsUnitInstance unitInstance, @MappingTarget InboundResponseHttpJSON response );

}
