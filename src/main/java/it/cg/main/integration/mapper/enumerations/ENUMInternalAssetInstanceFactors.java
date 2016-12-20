package it.cg.main.integration.mapper.enumerations;

/**
 * WsProduct product -> List<WsAsset> assets; -> List<WsAssetInstance> instances -> List<WsFactor> factors
 * @author RiccardoEstia
 *
 */
public enum ENUMInternalAssetInstanceFactors
{
	FACTOR_2LOYAL("2LOYAL"),
	FACTOR__2AAVE("_2AAVE"),
	FACTOR__2ALIM("_2ALIM"),
	FACTOR__2BM("_2BM"),
	FACTOR__2CC("_2CC"),
	FACTOR__2CETA("_2CETA"),
	FACTOR__2CF("_2CF"),
	FACTOR__2CMAR("_2CMAR"),
	FACTOR__2CMOD("_2CMOD"),
//	FACTOR__2CNAS("_2CNAS"),FATTORE ELIMINATO
	FACTOR__2CRCA("_2CRCA"),
//	FACTOR__2CTPF("_2CTPF"),FATTORE ELIMINATO
	FACTOR__2CU("_2CU"),
	FACTOR__2DIMM("_2DIMM"),
	FACTOR__2FTAA("_2FTAA"),
	FACTOR__2FTAP("_2FTAP"),
	FACTOR__2KW("_2KW"),
	FACTOR__2PETA("_2PETA"),
//	FACTOR__2PNAS("_2PNAS"),FATTORE ELIMINATO
	FACTOR__2PRCA("_2PRCA"),
//	FACTOR__2PTPF("_2PTPF"),FATTORE ELIMINATO
	FACTOR__2VVAL("_2VVAL"),
	FACTOR_2ALAUT("2ALAUT"),
	FACTOR_2ALLR1("2ALLR1"),
//	FACTOR_2ANACQ("2ANACQ"),FATTORE ELIMINATO
	FACTOR_2ANTIF("2ANTIF"),
	FACTOR_2ANUCF("2ANUCF"),
	FACTOR_2APIMM("2APIMM"),
	FACTOR_2ASSIC("2ASSIC"),
	FACTOR_2ASSIN("2ASSIN"),
	FACTOR_2BERS("2BERS"),
//	FACTOR_2BERSP("2BERSP"),FATTORE ELIMINATO
	FACTOR_2BOMBK("2BOMBK"),
	FACTOR_2CLUWR("2CLUWR"),
	FACTOR_2ETAV("2ETAV"),
	FACTOR_2EVACQ("2EVACQ"),
	FACTOR_2KMAN("2KMAN"),
	FACTOR_2MDAP("2MDAP"),
//	FACTOR_2MDPH("2MDPH"),FATTORE ELIMINATO
	FACTOR_2MDPR("2MDPR"),
	FACTOR_2MDSC("2MDSC"),
	FACTOR_2MKMDL("2MKMDL"),
	FACTOR_2MODR1("2MODR1"),
	FACTOR_2NAIRB("2NAIRB"),
	FACTOR_2NDRIV("2NDRIV"),
	FACTOR_2NUMAB("2NUMAB"),
	FACTOR_2OWAP("2OWAP"),
//	FACTOR_2OWMD("2OWMD"),FATTORE ELIMINATO
//	FACTOR_2OWPH("2OWPH"),FATTORE ELIMINATO
//	FACTOR_2OWPR("2OWPR"),FATTORE ELIMINATO
	FACTOR_2OWSC("2OWSC"),
	FACTOR_2PARKN("2PARKN"),
	FACTOR_2PRIMC("2PRIMC"),
	FACTOR_2PROMC("2PROMC"),
	FACTOR_2RD1AP("2RD1AP"),
	FACTOR_2RD1CA("2RD1CA"),
	FACTOR_2RD1ET("2RD1ET"),
	FACTOR_2RD1SC("2RD1SC"),
	FACTOR_2RD2AP("2RD2AP"),
	FACTOR_2RD2CA("2RD2CA"),
	FACTOR_2RD2ET("2RD2ET"),
	FACTOR_2RD2SC("2RD2SC"),
	FACTOR_2REGRE("2REGRE"),
	FACTOR_2RINNB("2RINNB"),
	FACTOR_2ROWMD("2ROWMD"),
	FACTOR_2RROAD("2RROAD"),
	FACTOR_2SEATS("2SEATS"),
	FACTOR_2SIN3("2SIN3"),
	FACTOR_2SIN6("2SIN6"),
	FACTOR_2TICAR("2TICAR"),
	FACTOR_2TIPGU("2TIPGU"),
	FACTOR_2TVINC("2TVINC"),
	FACTOR_2USOVE("2USOVE"),
//	FACTOR_3ASSI5("3ASSI5"),FATTORE ELIMINATO
	FACTOR_3CLIN1("3CLIN1"),
	FACTOR_3CLIN5("3CLIN5"),
	FACTOR_3CONTO("3CONTO"),
	FACTOR_3MASSA("3MASSA"),
	FACTOR_SXTOT("SXTOT"),
	FACTOR__2MIMM("_2MIMM")//FATTORE AGGIUNTO
	
	;

	
//	Impl
	private String value;

	ENUMInternalAssetInstanceFactors(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

}
