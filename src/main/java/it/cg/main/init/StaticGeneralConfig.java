package it.cg.main.init;

public enum StaticGeneralConfig
{
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
