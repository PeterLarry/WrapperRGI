package it.cg.main.init;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.FileSystemResource;

@WebListener
@Configuration
//@ComponentScan(basePackages={"it.cg.*"})
public class ApplicationContextConfig implements ServletContextListener
{ 
	private static final Logger logger = Logger.getLogger("it.cg.main.init.ApplicationContextConfig");
	
	/**
	 * Load configuration external and internal
	 * @return
	 */
	@Bean
	@PostConstruct
	public static PropertyPlaceholderConfigurerProxy properties()
	{
		logger.info("WrapperPASS-init Loading properties begin");
		PropertyPlaceholderConfigurerProxy propConf = new PropertyPlaceholderConfigurerProxy();

        AbstractResource resources[] ;

		resources = new AbstractResource[] {
			new FileSystemResource(SystemEnvironmentBean.getFullPathNameConfigFile())
		};
		propConf.setLocations(resources);
		logger.info("WrapperPASS-init "+SystemEnvironmentBean.getFullPathNameConfigFile()+" loaded");
		propConf.setIgnoreUnresolvablePlaceholders(true);
		logger.info("WrapperPASS-init set ignoreUnresolvablePlaceholders true");

		logger.info("WrapperPASS-init Loading properties finish");

		return propConf;
	}


	/**
	 * Inizializzo il main.properties
	 */
	@Override
	public void contextInitialized(ServletContextEvent event)
	{
		logger.info("contextInitialized begin initialize context:"+event);
		
		Properties properties = new Properties();
		try
		{
			InputStream input = new FileInputStream(SystemEnvironmentBean.getFullPathNameConfigFile());
			properties.load(input);

			logger.info("contextInitialized "+SystemEnvironmentBean.getFullPathNameConfigFile()+" loaded");
		}
		catch (IOException ex)
		{
			logger.error("GRAVE Impossibile caricare le configurazioni principali del main.properties "+ex.getMessage());
			ex.printStackTrace();
		}

		logger.info("contextInitialized finish initialize context");
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0)
	{
		logger.info("contextDestroyed Destroyed");
	}

}
