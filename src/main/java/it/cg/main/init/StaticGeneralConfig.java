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
	 * When the message is created in a specific handler phase : 
	 * GetTechnicaldata or GetFactors of technical data <br>
	 * this parameter into header specify the start point of the message
	 * <b>phaseHardway</b>
	 */
	HEADER_MESSAGE_HARDWAY_PHASE_KEY("phaseHardway"),
	HEADER_MESSAGE_HARDWAY_GETTECHINICALDATA_VAL("getTechinicalData"),
	HEADER_MESSAGE_HARDWAY_GETASSETS_VAL("getAssets"),
	HEADER_MESSAGE_HARDWAY_GETRISK_VAL("getRisk")
	
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
