package it.cg.main.init;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

@WebListener
@Configuration
//@ComponentScan(basePackages={"it.cg.*"})
public class ApplicationContextConfig implements ServletContextListener
{ 
	private static Logger logger = Logger.getLogger("it.cg.main.init.ApplicationContextConfig");
	
	/**
	 * Load configuration external and internal
	 * @return
	 */
	@Bean
	@PostConstruct
	public static PropertySourcesPlaceholderConfigurer properties()
	{
		logger.info("Loading properties");
		PropertySourcesPlaceholderConfigurer propConf = new PropertySourcesPlaceholderConfigurer();
		String pathLog4j = getPropertyValue(StaticGeneralConfig.LOG4J_PARAM_MAIN_PROPERTIES.value());
		String pathEndpoint = getPropertyValue(StaticGeneralConfig.WEBSERVICE_PARAM_MAIN_PROPERTIES.value());
		String pathRouting = getPropertyValue(StaticGeneralConfig.ROUTING_PARAM_MAIN_PROPERTIES.value());
		
        AbstractResource resources[] = new AbstractResource[] {
        		new ClassPathResource(StaticGeneralConfig.MAIN_PROPERTIES_FILE_NAME.value()),
        		new FileSystemResource(pathLog4j),
        		new FileSystemResource(pathEndpoint),
        		new FileSystemResource(pathRouting)
        };
		
		propConf.setLocations(resources);
		propConf.setIgnoreUnresolvablePlaceholders(true);
		
		return propConf;
	}
	
	/**
	 * main.properties contiene i path delle configurazioni esterne
	 * @param resourceRequest
	 * @return
	 */
	private static String getPropertyValue(String resourceRequest)
	{
		String paramValue = "";
		try
		{
			InputStream input =  ApplicationContextConfig.class.
							getClassLoader().getResourceAsStream(StaticGeneralConfig.MAIN_PROPERTIES_FILE_NAME.value());
			Properties properties = new Properties();
			
			properties.load(input);
			
			paramValue = properties.getProperty(resourceRequest);
		}
		catch (IOException ex) {
			logger.error("GRAVE Impossibile caricare le configurazioni principali"+ex.getMessage());
			ex.printStackTrace();
		}
		catch (NullPointerException ex) {
			logger.error("GRAVE Impossibile caricare le configurazioni principali, null pointer exception "+ex.getMessage());
			ex.printStackTrace();
		}
		
		return paramValue;
	}



	/**
	 * Inizializzo il log4j
	 */
	@Override
	public void contextInitialized(ServletContextEvent event)
	{
		ServletContext context = event.getServletContext();
		Properties properties = new Properties();
		try
		{
			properties.load(context.getResourceAsStream(StaticGeneralConfig.MAIN_PROPERTIES_CLASSPATH.value()+StaticGeneralConfig.MAIN_PROPERTIES_FILE_NAME.value()));
		}
		catch (IOException ex)
		{
			logger.error("GRAVE Impossibile caricare le configurazioni principali del main.properties"+ex.getMessage());
			ex.printStackTrace();
		}
		String log4jConfigFile = properties.getProperty(StaticGeneralConfig.LOG4J_PARAM_MAIN_PROPERTIES.value());
				
		PropertyConfigurator.configureAndWatch(log4jConfigFile);
		
		logger.info("contextLoaded");
		
	}



	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

	
	
	
}
