package it.cg.main.integration.mapper.enumerations;

public enum ENUMInternalUnitInstanceFactors {
	
	FACTOR_1FIDRC("1FIDRC"),
	FACTOR_2LOYAL("2LOYAL"),
	FACTOR_2PROMC("2PROMC"),
	FACTOR_2PRRCA("2PRRCA"),
	FACTOR_3ADJ("3ADJ"),
	FACTOR_3CLIN1("3CLIN1"),
	FACTOR_3CRDED("3CRDED"),
	FACTOR_3CRLMT("3CRLMT"),
	FACTOR_3FIDRC("3FIDRC"),
	FACTOR_3FRACO("3FRACO"),
	FACTOR_3FRAEN("3FRAEN"),
	FACTOR_3FRAKS("3FRAKS"),
	FACTOR_3FRATV("3FRATV"),
	FACTOR_3FRSCF("3FRSCF"),
	FACTOR_3MASS("3MASS"),
	FACTOR_3QD("3QD"),
	FACTOR_3RCFRA("3RCFRA"),
	FACTOR_3SAINP("3SAINP"),
	FACTOR_3TIPAS("3TIPAS"),
	FACTOR_3TIPTL("3TIPTL"),
	FACTOR_FRRCA("FRRCA"),
	FACTOR_QDRCA("QDRCA"),
	;

	
//	Impl
	private String value;

	ENUMInternalUnitInstanceFactors(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

}