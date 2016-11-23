package it.cg.main.integration.mapper.enumerations;

/**
 * WsProduct product -> List<WsFactor> factors
 * @author RiccardoEstia
 *
 */
public enum WsProductFactorsENUM
{
	FACTOR__1CETA("_1CETA"),
	FACTOR__1CNAS("_1CNAS"),
	FACTOR__1CNCA("_1CNCA"),
	FACTOR__1CTPF("_1CTPF"),
	FACTOR__1PEFF("_1PEFF"),
	FACTOR_1AFF("1AFF"),
	FACTOR_1ANRIN("1ANRIN"),
	FACTOR_1CSC("1CSC"),
	FACTOR_1FIDCO("1FIDCO"),
	FACTOR_1FIDCR("1FIDCR"),
	FACTOR_1FIDEV("1FIDEV"),
	FACTOR_1FIDFU("1FIDFU"),
	FACTOR_1FIDIF("1FIDIF"),
	FACTOR_1FIDIN("1FIDIN"),
	FACTOR_1FIDKA("1FIDKA"),
	FACTOR_1FIDMO("1FIDMO"),
	FACTOR_1FIDRC("1FIDRC"),
	FACTOR_1FIDVT("1FIDVT"),
	FACTOR_1FRRIN("1FRRIN"),
	FACTOR_1PHAP("1PHAP"),
	FACTOR_1PHPR("1PHPR"),
	FACTOR_1PHSC("1PHSC"),
	FACTOR_2CM26("2CM26")
	;

	
//	Impl
	private String value;

	WsProductFactorsENUM(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

}
