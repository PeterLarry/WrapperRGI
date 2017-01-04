package test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.mapfre.engines.rating.business.objects.wrapper.Coinsurance;
import com.mapfre.engines.rating.business.objects.wrapper.Context;
import com.mapfre.engines.rating.business.objects.wrapper.Coverage;
import com.mapfre.engines.rating.business.objects.wrapper.RatingInfo;
import com.mapfre.engines.rating.business.objects.wrapper.TechnicalData;
import com.mapfre.engines.rating.business.objects.wrapper.Vehicle;
import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.ICoinsurance;
import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.IContext;
import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.ICoverage;
import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.IRatingInfo;
import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.ITechnicalData;
import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.IVehicle;
import com.mapfre.engines.rating.common.enums.EnumAlimentation;
import com.mapfre.engines.rating.common.enums.EnumProductType;
import com.mapfre.engines.rating.common.enums.EnumPromoCode;
import com.mapfre.engines.rating.common.enums.EnumRestrictionType;
import com.mapfre.engines.rating.common.enums.EnumRiskType;

import it.cg.main.dto.InboundRequestHttpJSON;
import it.cg.main.dto.main.Quote;

public class TestGson
{
	/** file excel AUTO DLI COLLISIONE
	 * EnumRiskType.MOPED
	 * @return
	 */
	public InboundRequestHttpJSON getQuoteAutoDLICollisione()
	{
		InboundRequestHttpJSON responseInbound = new InboundRequestHttpJSON();
		
		Quote testQuote = new Quote();
		testQuote.setEffectiveDate(new Date());
		testQuote.setAffinity("0");
		testQuote.setOtherVehiclesInsuredWithUs(true);
		testQuote.setNumberOfClaimsInLastYear(0);
		testQuote.setCampaign("A");
		testQuote.setRenewalYears(0);
		testQuote.setOtherVehiclesInsuredWithUs(true);
		testQuote.setGoodDriverClass("02");
		testQuote.setInnerClass("2");
		testQuote.setPreviousRenewalYears(0);
		testQuote.setFiddleFactorCalculationRequired(null);
		testQuote.setClean5(true);

		IRatingInfo testRating = new RatingInfo();
		testRating.setPromocode(EnumPromoCode.PROMO_1);
		testRating.setUwClass("1");
		
		IVehicle testVehicle = new Vehicle();
		testVehicle.setKmPerYear(1);
		testVehicle.setHabitualUse(null);
		testVehicle.setPreviousVehicleAgeInMonth(5);
		testVehicle.setVehicleAge(0);
		testVehicle.setLeasingType(EnumRestrictionType.LOAN);
		testVehicle.setInsuredValue(0);
		ITechnicalData testTechnical = new TechnicalData();
		testVehicle.setTechnicalData(testTechnical);
		testTechnical.setAlimentation(EnumAlimentation.PETROL);
		
		List<ICoverage> listCov = new ArrayList<ICoverage>();
			ICoverage testCov = new Coverage();
			testCov.setFiddleFactor(0.0);
			testCov.setDiscount(null);
			testCov.setPreviousNetAmount(5.6808);
			testCov.setQuickAndDirty(0.0);
			ICoinsurance testCoin = new Coinsurance();
			testCoin.setCode("1");
			testCov.setCoinsurance(testCoin);
			listCov.add(testCov);
		
		testQuote.setRatingInfo(testRating);
		testQuote.setVehicle(testVehicle);
		testQuote.setCoverages(listCov);
		
		responseInbound.setInboundQuoteDTO(testQuote);
		responseInbound.setServiceType("easy");
		
		
		IContext contextAddon = new Context();
		contextAddon.setRiskType(EnumRiskType.CAR);
		contextAddon.setProductType(EnumProductType.DLI);
		//		TODO ADDON
//		"context": {
//		      "platform": "QOL",
//		      "riskType": "CAR",
//		      "quoteType": "NEW_BUSINESS",
//		      "amendmentType": null,
//		      "dateType": "N",
//		      "productType": "DLI",
//		      "section": "PPA",
//		      "flowType": "NEW_QUOTE",
//		      "systemDate": "2016-12-13T04:18:44.429Z",
//		      "userSessionId": "223E721691659AD51EF6E0526FF35CA9",
//		      "directlineSelfService": false
//		    }
		testQuote.setContext(contextAddon);
		
		
		return responseInbound;
	}
	

}
