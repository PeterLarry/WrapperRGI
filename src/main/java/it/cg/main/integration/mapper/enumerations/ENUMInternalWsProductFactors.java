package it.cg.main.integration.mapper.enumerations;

/**
 * WsProduct product -> List<WsFactor> factors
 * @author RiccardoEstia
 *
 */
public enum ENUMInternalWsProductFactors
{
	FACTOR__1CETA("_1CETA"),
	FACTOR__1CNAS("_1CNAS"),
	FACTOR__1CNCA("_1CNCA"),
//	FACTOR__1CTPF("_1CTPF"),FATTORE ELIMINATO
//	FACTOR__1PEFF("_1PEFF"),FATTORE ELIMINATO
	FACTOR_1AFF("1AFF"),
	FACTOR_1ANRIN("1ANRIN"),
	FACTOR_1CSC("1CSC"),
//	FACTOR_1FIDCO("1FIDCO"),FATTORE ELIMINATO
//	FACTOR_1FIDCR("1FIDCR"),FATTORE ELIMINATO
//	FACTOR_1FIDEV("1FIDEV"),FATTORE ELIMINATO
//	FACTOR_1FIDFU("1FIDFU"),FATTORE ELIMINATO
//	FACTOR_1FIDIF("1FIDIF"),FATTORE ELIMINATO
//	FACTOR_1FIDIN("1FIDIN"),FATTORE ELIMINATO
//	FACTOR_1FIDKA("1FIDKA"),FATTORE ELIMINATO
//	FACTOR_1FIDMO("1FIDMO"),FATTORE ELIMINATO
//	FACTOR_1FIDRC("1FIDRC"),FATTORE ELIMINATO(RIPETUTO)
//	FACTOR_1FIDVT("1FIDVT"),FATTORE ELIMINATO
	FACTOR_1FRRIN("1FRRIN"),
	FACTOR_1PHAP("1PHAP"),
//	FACTOR_1PHPR("1PHPR"),FATTORE ELIMINATO
	FACTOR_1PHSC("1PHSC"),
	FACTOR_1FLRFF("1FLRFF"),
	FACTOR_3FRC1("3FRC1"),
	FACTOR_3FRC5("3FRC5")
//	FACTOR_2CM26("2CM26")FATTORE ELIMINATO
	;

	
//	Impl
	private String value;

	ENUMInternalWsProductFactors(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
    
    public static ENUMInternalWsProductFactors getEnumFromCode(String enumCode) {
        for (ENUMInternalWsProductFactors element : ENUMInternalWsProductFactors.values()) {
            if (element.value.equals(enumCode)) {
                return element;
            }
        }
        throw new IllegalArgumentException("Unknow Code: '" + enumCode + "'");
    }

}
