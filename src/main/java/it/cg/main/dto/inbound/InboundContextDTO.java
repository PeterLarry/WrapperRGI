package it.cg.main.dto.inbound;

public class InboundContextDTO {
	
	private String platform;
	private String riskType;
	private String quoteType;
	private String amendmentType;
	private String dateType;
	private String productType;
	private String section;
	private String flowType;
	
	
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	
	public String getQuoteType() {
		return quoteType;
	}
	public void setQuoteType(String quoteType) {
		this.quoteType = quoteType;
	}
	public String getAmendmentType() {
		return amendmentType;
	}
	public void setAmendmentType(String amendmentType) {
		this.amendmentType = amendmentType;
	}
	public String getDateType() {
		return dateType;
	}
	public void setDateType(String dateType) {
		this.dateType = dateType;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getFlowType() {
		return flowType;
	}
	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}
	/**
	 * @return the riskType
	 */
	public String getRiskType() {
		return riskType;
	}
	/**
	 * @param riskType the riskType to set
	 */
	public void setRiskType(String riskType) {
		this.riskType = riskType;
	}
	
	

}
