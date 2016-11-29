package it.cg.main.integration.mapper.enumerations;

public enum CodeProductENUM {
	
	CODE_AUTODLI("000001"),
	CODE_MOTOCICLODLI("000002"),
	CODE_ALTRIVEICOLIDLI("000003"),
	CODE_AUTOADI("000004"),
	CODE_AUTODLSS("000005"),
	;
	
	
	
	private String value;

	CodeProductENUM(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
    

}
