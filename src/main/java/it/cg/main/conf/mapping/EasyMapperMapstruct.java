package it.cg.main.conf.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.blog.samples.webservices.DetailService;
import com.pass.global.WSPassProHelloWorldOperationResponse;

@Mapper
public interface EasyMapperMapstruct
{
	
	@Mappings({
		@Mapping(source="return", target="serviceType"),
		@Mapping(source="return", target="return")
	})
	DetailService helloWorldToDetailService(WSPassProHelloWorldOperationResponse response);
}
