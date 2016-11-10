package it.cg.main.dto.inbound;

import java.io.Serializable;

public class InboundWsUnitInstanceDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9101829727272214353L;
	private InboundWsFactorUnitInstanceDTO factors;
	private InboundWsClauseUnitInstanceDTO clauses;
	
	/**
	 * Se impostato a true nel tracciato di output valorizza un campo stringa con xml di quanto successo durante 
	 * il calcolo. Funzionalità aggiuntiva rispetto alla gestione errori. Serve per fare diagnosi in fase 
	 * di sviluppo. In produzione potrebbe non essere attivato
	 */
	private Boolean enableTariffFormulaLog;
	
	/**
	 * E' possibile passarlo. Altrimenti  lo attribuisce Pass
	 */
	private String name;
}
