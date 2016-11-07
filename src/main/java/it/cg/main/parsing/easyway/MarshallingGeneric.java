package it.cg.main.parsing.easyway;

import java.awt.TrayIcon.MessageType;
import java.io.IOException;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.transform.Result;
import javax.xml.transform.Source;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

public class MarshallingGeneric implements Marshaller, Unmarshaller, InitializingBean
{

	
	@Autowired
	private Jaxb2Marshaller marshaller;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object unmarshal(Source source) throws IOException, XmlMappingException
	{
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void marshal(Object graph, Result result) throws IOException, XmlMappingException {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
