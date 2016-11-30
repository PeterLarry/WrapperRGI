package it.cg.main.dto;

import com.pass.global.WsCalculatePremiumInput;

import it.cg.main.conf.DtoImpl;
import it.cg.main.dto.inbound.InboundQuoteDTO;

/**
 * DTO exposed for the request from Direct Line
 * @author RiccardoEstia
 *
 */
public class InboundRequestHttpJSON extends DtoImpl
{
	private static final long serialVersionUID = -4058572266373539270L;

	public InboundQuoteDTO inboundQuoteDTO;
	/**
	 * Service type variable request from external service
	 */
	private String serviceType;
	/**
	 * Start Object for pass
	 */
	
	private boolean quoteMode = false;
	private boolean adaptToMinimumPremium = true;
	private boolean applyDiscount = true;
	private String currencyCodeProduct = "";
	private String sectorCodeVehicle = "000005";
	private String codeAssetSection = "S1";
	private String codeAssetUnit = "RCAR1";
	private boolean selectionAssetUnit = true;
	private String codeClause = "RCA001";
	private boolean selectedClause = false;
	
	private WsCalculatePremiumInput wsCalculatePremiumInput;
	
	
	/**
	 * @return the serviceType
	 */
	public String getServiceType() {
		return serviceType;
	}
	/**
	 * @param serviceType the serviceType to set
	 */
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public InboundQuoteDTO getInboundQuoteDTO() {
		return inboundQuoteDTO;
	}
	public void setInboundQuoteDTO(InboundQuoteDTO inboundQuoteDTO) {
		this.inboundQuoteDTO = inboundQuoteDTO;
	}
	public boolean isQuoteMode() {
		return quoteMode;
	}
	public void setQuoteMode(boolean quoteMode) {
		this.quoteMode = quoteMode;
	}
	public boolean isAdaptToMinimumPremium() {
		return adaptToMinimumPremium;
	}
	public void setAdaptToMinimumPremium(boolean adaptToMinimumPremium) {
		this.adaptToMinimumPremium = adaptToMinimumPremium;
	}
	public boolean isApplyDiscount() {
		return applyDiscount;
	}
	public void setApplyDiscount(boolean applyDiscount) {
		this.applyDiscount = applyDiscount;
	}
//	public String getCodeProduct() {
//		return codeProduct;
//	}
//	public void setCodeProduct(String codeProduct) {
//		this.codeProduct = codeProduct;
//	}
	
	public String getCurrencyCodeProduct() {
		return currencyCodeProduct;
	}
	public void setCurrencyCodeProduct(String currencyCodeProduct) {
		this.currencyCodeProduct = currencyCodeProduct;
	}
	public String getSectorCodeVehicle() {
		return sectorCodeVehicle;
	}
	public void setSectorCodeVehicle(String sectorCodeVehicle) {
		this.sectorCodeVehicle = sectorCodeVehicle;
	}
	
	public String getCodeAssetSection() {
		return codeAssetSection;
	}
	public void setCodeAssetSection(String codeAssetSection) {
		this.codeAssetSection = codeAssetSection;
	}
	public String getCodeAssetUnit() {
		return codeAssetUnit;
	}
	public void setCodeAssetUnit(String codeAssetUnit) {
		this.codeAssetUnit = codeAssetUnit;
	}
	public boolean isSelectionAssetUnit() {
		return selectionAssetUnit;
	}
	public void setSelectionAssetUnit(boolean selectionAssetUnit) {
		this.selectionAssetUnit = selectionAssetUnit;
	}
	public String getCodeClause() {
		return codeClause;
	}
	public void setCodeClause(String codeClause) {
		this.codeClause = codeClause;
	}
	public boolean isSelectedClause() {
		return selectedClause;
	}
	public void setSelectedClause(boolean selectedClause) {
		this.selectedClause = selectedClause;
	}
	public WsCalculatePremiumInput getWsCalculatePremiumInput() {
		return wsCalculatePremiumInput;
	}
	public void setWsCalculatePremiumInput(WsCalculatePremiumInput wsCalculatePremiumInput) {
		this.wsCalculatePremiumInput = wsCalculatePremiumInput;
	}
	/**
	 * @return the factors
	 */
	/**
	 * @return the factors
	 */
//	public List<WsFactor> getFactors() {
//		return factors;
//	}
//	/**
//	 * @param factors the factors to set
//	 */
//	public void setFactors(List<WsFactor> factors) {
//		this.factors = factors;
//	}
//	
	
}
