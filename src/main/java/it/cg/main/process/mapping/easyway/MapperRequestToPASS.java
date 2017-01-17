package it.cg.main.process.mapping.easyway;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import com.pass.global.WsAsset;
import com.pass.global.WsAssetInstance;
import com.pass.global.WsAssetSection;
import com.pass.global.WsAssetUnit;
import com.pass.global.WsCalculatePremiumInput;
import com.pass.global.WsUnitInstance;

import it.cg.main.dto.InboundRequestHttpJSON;
import it.cg.main.dto.main.Quote;



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
	
	@Mappings({
//		@Mapping(source="inbound", target = "clauses"),
		@Mapping(source="inboundQuoteDTO", target = "factors")
	})
	void quoteDtoToUnitInst(InboundRequestHttpJSON inbound, @MappingTarget WsUnitInstance uInst);
	
	}

