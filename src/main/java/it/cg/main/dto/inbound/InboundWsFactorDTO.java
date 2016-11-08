package it.cg.main.dto.inbound;

import java.io.Serializable;

public class InboundWsFactorDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6431689577948111482L;
	
	private InboundWsFactorProductDTO factProd;
	private InboundWsFactorAssetInstanceDTO factAssetInst;
	private InboundWsFactorAssetUnitDTO factAssetUnit;
	private InboundWsFactorUnitInstanceDTO factUnitInst;
	
	
	public InboundWsFactorProductDTO getFactProd() {
		return factProd;
	}
	public void setFactProd(InboundWsFactorProductDTO factProd) {
		this.factProd = factProd;
	}
	public InboundWsFactorAssetInstanceDTO getFactAssetInst() {
		return factAssetInst;
	}
	public void setFactAssetInst(InboundWsFactorAssetInstanceDTO factAssetInst) {
		this.factAssetInst = factAssetInst;
	}
	public InboundWsFactorAssetUnitDTO getFactAssetUnit() {
		return factAssetUnit;
	}
	public void setFactAssetUnit(InboundWsFactorAssetUnitDTO factAssetUnit) {
		this.factAssetUnit = factAssetUnit;
	}
	public InboundWsFactorUnitInstanceDTO getFactUnitInst() {
		return factUnitInst;
	}
	public void setFactUnitInst(InboundWsFactorUnitInstanceDTO factUnitInst) {
		this.factUnitInst = factUnitInst;
	}
	
	
	
}
