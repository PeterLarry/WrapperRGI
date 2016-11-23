package test;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

public class JacksonExtends extends MappingJackson2HttpMessageConverter
{
	public JacksonExtends() {
		System.out.println("Mah");
	}

}
