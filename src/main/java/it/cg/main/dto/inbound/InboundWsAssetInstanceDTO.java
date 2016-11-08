package it.cg.main.dto.inbound;

import java.io.Serializable;

public class InboundWsAssetInstanceDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1130136391745904176L;
	
	private InboundWsFactorDTO factors;
	private InboundWsClauseDTO clauses;
	private InboundWsAssetSectionDTO sections;
	
}
