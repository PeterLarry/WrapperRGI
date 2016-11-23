package it.cg.main.conf.mapping.hardway;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import com.pass.global.GetTechnicalDataResponse;
import com.pass.global.WsProduct;

@Mapper
public interface GetTechnicalDataMapstruct
{
	@Mappings({})
	void parseGetFactors(WsProduct technicalDataResponse, @MappingTarget WsProduct wsProduct);

}
