package test;

import java.util.Date;

import javax.xml.datatype.DatatypeConfigurationException;

import com.google.gson.Gson;

import it.cg.main.dto.InboundRequestHttpJSON;
import it.cg.main.dto.inbound.InboundAddressDTO;
import it.cg.main.dto.inbound.InboundContextDTO;
import it.cg.main.dto.inbound.InboundCoverageDTO;
import it.cg.main.dto.inbound.InboundDeductibleDTO;
import it.cg.main.dto.inbound.InboundFigureDTO;
import it.cg.main.dto.inbound.InboundLimitDTO;
import it.cg.main.dto.inbound.InboundQuoteDTO;
import it.cg.main.dto.inbound.InboundRatingInfoDTO;
import it.cg.main.dto.inbound.InboundVehicleDTO;

public class TestInitMapper {

	public static void main(String[] args) throws DatatypeConfigurationException {
		// TODO Auto-generated method stub
		
//		CustomTest ctest = new CustomTest();
//		CalculatePremium cp = new CalculatePremium();
//		ctest.getCalculatePremiumTest(cp);
		
		InboundRequestHttpJSON inboundRequestHttpJSON = new InboundRequestHttpJSON();
		inboundRequestHttpJSON.setServiceType("easy");

		
		//InboundQuoteDTO
		inboundRequestHttpJSON.setInboundQuoteDTO(new InboundQuoteDTO());
		inboundRequestHttpJSON.getInboundQuoteDTO().setEffectiveDate(new Date());
		inboundRequestHttpJSON.getInboundQuoteDTO().setInstallments(000001);
		inboundRequestHttpJSON.getInboundQuoteDTO().setRenewalYears(2);//Relativo a 1ANRIN
		inboundRequestHttpJSON.getInboundQuoteDTO().setAffinity("0064");//Relativo a 1AFF
		inboundRequestHttpJSON.getInboundQuoteDTO().setNumberOfVehiclesOwned(3);
		inboundRequestHttpJSON.getInboundQuoteDTO().setOtherVehiclesInsuredWithUs(true);
		inboundRequestHttpJSON.getInboundQuoteDTO().setGoodDriverClass("99");
		inboundRequestHttpJSON.getInboundQuoteDTO().setNumberOfClaimsInLastYear(0);
		inboundRequestHttpJSON.getInboundQuoteDTO().setInnerClass("9");
//		inboundRequestHttpJSON.getInboundQuoteDTO().setBmalType("X"); non più utilizzato, da eliminare tracciato input. Da utilizzare 2bers
		inboundRequestHttpJSON.getInboundQuoteDTO().setDriverNumber(1);
		inboundRequestHttpJSON.getInboundQuoteDTO().setClean1(true);
		

		//InboundQuoteDTO.InboundContextDTO
		inboundRequestHttpJSON.getInboundQuoteDTO().setContext(new InboundContextDTO());
		inboundRequestHttpJSON.getInboundQuoteDTO().getContext().setProductType(("MOTOCICLO DLI"));
		inboundRequestHttpJSON.getInboundQuoteDTO().getContext().setRisktype("Ciclomotore");
		
		//InboundQuoteDTO.InboundVehicleDTO
		inboundRequestHttpJSON.getInboundQuoteDTO().setVehicle(new InboundVehicleDTO());
		inboundRequestHttpJSON.getInboundQuoteDTO().getVehicle().setHabitualUse("Tempo libero");
		inboundRequestHttpJSON.getInboundQuoteDTO().getVehicle().setVehicleAge(18);
		inboundRequestHttpJSON.getInboundQuoteDTO().getVehicle().setCarAgeAtPurchase(3);
		
		//InboundQuoteDTO.InboundRatingInfoDTO
		inboundRequestHttpJSON.getInboundQuoteDTO().setRatingInfo(new InboundRatingInfoDTO());
		inboundRequestHttpJSON.getInboundQuoteDTO().getRatingInfo().setUwClass("3");
		
		/**
		 * Figure
		 */
		//InboundQuoteDTO.InboundFigureDTO
		inboundRequestHttpJSON.getInboundQuoteDTO().setFigures(new InboundFigureDTO());
		inboundRequestHttpJSON.getInboundQuoteDTO().getFigures().setAge(18);//Relativo a _1CETA
		inboundRequestHttpJSON.getInboundQuoteDTO().getFigures().setYearsWithLicence(1);//Relativo a 1PHAP
		inboundRequestHttpJSON.getInboundQuoteDTO().getFigures().setOccupation("Casalinga");
		
		//InboundQuoteDTO.InboundFigureDTO.InboundAddressDTO
		inboundRequestHttpJSON.getInboundQuoteDTO().getFigures().setResidenceAddress(new InboundAddressDTO());
		inboundRequestHttpJSON.getInboundQuoteDTO().getFigures().getResidenceAddress().setCap("23");
		
		/**
		 * Coverage
		 */
		//InboundQuoteDTO.InboundCoverageDTO
		inboundRequestHttpJSON.getInboundQuoteDTO().setCoverages(new InboundCoverageDTO());
		
		//InboundQuoteDTO.InboundCoverageDTO.InboundLimitDTO
		inboundRequestHttpJSON.getInboundQuoteDTO().getCoverages().setLimit(new InboundLimitDTO());
		inboundRequestHttpJSON.getInboundQuoteDTO().getCoverages().getLimit().setCode("362");
		
		//InboundQuoteDTO.InboundCoverageDTO.InboundDeductibleDTO
		inboundRequestHttpJSON.getInboundQuoteDTO().getCoverages().setDeductible(new InboundDeductibleDTO());
		inboundRequestHttpJSON.getInboundQuoteDTO().getCoverages().getDeductible().setCode("258");
		

		Gson gson = new Gson();
		
		System.out.println(gson.toJson(inboundRequestHttpJSON));
	}

}
