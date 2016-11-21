package it.cg.main.dto;

import java.util.Date;

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
	
	public boolean quoteMode = false;
	public boolean adaptToMinimumPremium = true;
	public boolean applyDiscount = true;
	public String codeProduct = "000002";
	public Date openDateProduct = new Date();
	public String paymentFrequencyCodeProduct = "000001";
	public String currencyCodeProduct = "000001";
	public String codeAsset = "000002";
	public String classCodeVehicle = "Ciclomotore";
	public String sectorCodeVehicle = "Settore5";
	public String useCodeVehicle = "Privato";
	public String codeAssetSection = "S1";
	public String codeAssetUnit = "RCAR1";
	public boolean selectionAssetUnit = true;
	public String codeClause = "RCA001";
	public boolean selectedClause = false;
	
//	private WsCalculatePremiumInput wsCalculatePremiumInput;
	
	
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
	
}
