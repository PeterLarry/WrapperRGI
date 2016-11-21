package it.cg.main.dto.inbound;

public class InboundPremiumDTO {
	
	private Double net; 
	private Double tax; 
	private Double ssn; 
	private Double gross;
	
	
	public Double getNet() {
		return net;
	}
	public void setNet(Double net) {
		this.net = net;
	}
	public Double getTax() {
		return tax;
	}
	public void setTax(Double tax) {
		this.tax = tax;
	}
	public Double getSsn() {
		return ssn;
	}
	public void setSsn(Double ssn) {
		this.ssn = ssn;
	}
	public Double getGross() {
		return gross;
	}
	public void setGross(Double gross) {
		this.gross = gross;
	}
	
	

}
