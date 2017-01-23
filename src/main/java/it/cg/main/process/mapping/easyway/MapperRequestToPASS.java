package it.cg.main.process.mapping.easyway;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import com.pass.global.WsAsset;
import com.pass.global.WsCalculatePremiumInput;

import it.cg.main.dto.InboundRequestHttpJSON;



@Mapper(uses=MapperProductAssetToPASS.class)
public interface MapperRequestToPASS
{
	
	@Mappings({
		@Mapping(source="inboundQuoteDTO", target = "product"),//In questa riga mappo il Code
	})
	void quoteDtoToProduct(InboundRequestHttpJSON inbound, @MappingTarget WsCalculatePremiumInput wsCalculate);
	
	@Mappings({
		@Mapping(source="inboundQuoteDTO", target = "instances"),
//		@Mapping(source="inboundQuoteDTO.context.riskType", target = "code"),//In questa riga mappo il code dell'asset relativo al WsProduct
		@Mapping(source="inbound", target = "vehicles")
	})
	void quoteDtoToAsset(InboundRequestHttpJSON inbound, @MappingTarget WsAsset asset);
	
//	@Mappings({
////		@Mapping(source="inbound", target = "clauses"),
//		@Mapping(source="inboundQuoteDTO", target = "factors")
//	})
//	void quoteDtoToUnitInst(InboundRequestHttpJSON inbound, @MappingTarget WsUnitInstance uInst);
	
	}

