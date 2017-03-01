package it.cg.main.init;

public enum StaticGeneralConfig
{
	/**
	 * main.properties
	 */
	MAIN_PROPERTIES_FILE_NAME("main.properties"),
	
	/**
	 * /WEB-INF/classes/
	 */
	MAIN_PROPERTIES_CLASSPATH("/WEB-INF/classes/"),
	
	/**
	 * log4j-conf
	 */
//	LOG4J_PARAM_MAIN_PROPERTIES("log4j-conf"),
	
	/**
	 * webservice-conf
	 */
//	WEBSERVICE_PARAM_MAIN_PROPERTIES("webservice-conf"),
	/**
	 * routing-conf
	 */
//	ROUTING_PARAM_MAIN_PROPERTIES("routing-conf"),

	/**
	 * name of header pamaram for
	 * field ProxyQuoteInternalId
	 */
	HEADER_PARAM_INTERNAL_ID_PROXY("internalIdProxyParam")

	;
	
	
	
	
	
//	Impl
	private String value;

	StaticGeneralConfig(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

}
