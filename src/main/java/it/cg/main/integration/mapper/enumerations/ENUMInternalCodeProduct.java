package it.cg.main.integration.mapper.enumerations;

public enum ENUMInternalCodeProduct {
	
	CODE_AUTODLI("000001"),
	CODE_MOTOCICLODLI("000002"),
	CODE_ALTRIVEICOLIDLI("000003"),
	CODE_AUTOADI("000004"),
	CODE_AUTODLSS("000005"),
	;
	
	
	
	private String value;

	ENUMInternalCodeProduct(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
    
    public static ENUMInternalCodeProduct getEnumFromCode(String enumCode) {
        for (ENUMInternalCodeProduct element : ENUMInternalCodeProduct.values()) {
            if (element.value.equals(enumCode)) {
                return element;
            }
        }
        throw new IllegalArgumentException("Unknow Code: '" + enumCode + "'");
    }

}
