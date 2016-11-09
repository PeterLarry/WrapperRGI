package it.cg.main.conf.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.pass.global.WSPassProHelloWorldOperationResponse;

import it.cg.main.dto.InboundResponseHttpJSON;

@Mapper
public interface EasyMapperMapstruct
{
	
	@Mappings({
		@Mapping(source="return", target="typeOfService")
	})
	InboundResponseHttpJSON helloWorldToDetailService(WSPassProHelloWorldOperationResponse response);
}
