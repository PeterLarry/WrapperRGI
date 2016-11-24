package it.cg.main.conf.mapping.easyway;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.pass.global.WsAssetInstance;
import com.pass.global.WsProduct;

import it.cg.main.dto.inbound.InboundQuoteDTO;



@Mapper(uses=CustomMapperInbound.class)
public interface EasyMapperMapstruct
{
	
	@Mappings({
		@Mapping(source = "quote", target = "factors")
		})
	WsProduct quoteDtoToFactor(InboundQuoteDTO quote);
	
	@Mappings({
		@Mapping(source = "assetIn", target = "factors")
		})
	WsAssetInstance quoteDtoToAssetInstance(InboundQuoteDTO assetIn);
	
	//-------------PROVARE SU QUESTA STRADA--------------
	
//	@ValueMappings({
//		@ValueMapping(source = "affinity", target = "AssetInstanceFactors.ENUM.FACTOR_2ALLR1" )
//		})
//	WsFactor quoteDtoToFactor(InboundQuoteDTO quote);
//	
//	@ValueMappings({
//		@ValueMapping(source = "quote.vehicle.purchaseYear", target = "WsProductFactorsENUM.FACTOR__1CETA")
//		})
//	WsFactor quotDtoToFactor(InboundQuoteDTO quote);
	
	
	
	
//	@InheritConfiguration
//	void figureDtoToFactorProduct(InboundFigureDTO figure, @MappingTarget product);
}
