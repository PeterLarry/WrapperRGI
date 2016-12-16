package it.cg.main.integration.mapper.enumerations;

/**
 * WsProduct product -> List<WsAsset> assets; -> List<WsAssetInstance> instances -> AssetSection-> code
 * @author RiccardoEstia
 *
 */
public enum ENUMInternalCodeSection
{
	
		CODE_S1("S1"),
		CODE_S2("S2"),
		CODE_S3("S3"),
		CODE_S4("S4"),
		CODE_S5("S5"),
		CODE_S6("S6")
	
	;

	
//	Impl
	private String value;

	ENUMInternalCodeSection(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

}
