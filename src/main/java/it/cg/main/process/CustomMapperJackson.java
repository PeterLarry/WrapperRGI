package it.cg.main.process;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Mapper for Jackson
 * @author RiccardoEstia
 *
 */
public class CustomMapperJackson extends Jackson2ObjectMapperFactoryBean {

	
	 public CustomMapperJackson() {
        super();
        setFeaturesToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
	 
	/**
	 * Give the ISO 8601 pattern for the parsing date for jackson mapper,
	 * usefull for Json to Object mapping
	 * @return
	 */
    public static Jackson2JsonObjectMapper getMapper()
    {
        ObjectMapper mapper = new ObjectMapper();
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        mapper.setDateFormat(dateFormat);
        mapper.writer(dateFormat);
        
        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        
        return new Jackson2JsonObjectMapper(mapper);
    }
 
    
    
}