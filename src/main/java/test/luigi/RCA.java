package test.luigi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mapfre.engines.rating.business.objects.wrapper.Address;
import com.mapfre.engines.rating.business.objects.wrapper.Context;
import com.mapfre.engines.rating.business.objects.wrapper.Coverage;
import com.mapfre.engines.rating.business.objects.wrapper.Deductible;
import com.mapfre.engines.rating.business.objects.wrapper.Figure;
import com.mapfre.engines.rating.business.objects.wrapper.Limit;
import com.mapfre.engines.rating.business.objects.wrapper.RatingInfo;
import com.mapfre.engines.rating.business.objects.wrapper.TechnicalData;
import com.mapfre.engines.rating.business.objects.wrapper.Vehicle;
import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.IAddress;
import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.IContext;
import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.ICoverage;
import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.IDeductible;
import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.IFigure;
import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.ILimit;
import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.IRatingInfo;
import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.ITechnicalData;
import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.IVehicle;
import com.mapfre.engines.rating.common.enums.EnumAlimentation;
import com.mapfre.engines.rating.common.enums.EnumBmalType;
import com.mapfre.engines.rating.common.enums.EnumBodyType;
import com.mapfre.engines.rating.common.enums.EnumCampaign;
import com.mapfre.engines.rating.common.enums.EnumCoverageCode;
import com.mapfre.engines.rating.common.enums.EnumGoodDriveClass;
import com.mapfre.engines.rating.common.enums.EnumHabitualUse;
import com.mapfre.engines.rating.common.enums.EnumMaritalStatus;
import com.mapfre.engines.rating.common.enums.EnumOccupation;
import com.mapfre.engines.rating.common.enums.EnumProductType;
import com.mapfre.engines.rating.common.enums.EnumPromoCode;
import com.mapfre.engines.rating.common.enums.EnumRiskType;
import com.mapfre.engines.rating.common.enums.EnumRole;
import com.mapfre.engines.rating.common.enums.EnumSection;
import com.mapfre.engines.rating.common.enums.EnumUsualDriverAndOwnerRelationship;
import com.mapfre.engines.rating.common.enums.EnumVehicleType;

import it.cg.main.dto.main.Quote;

public class RCA
{
	
	public Quote getRiga3NoFF()
	{
		Quote testQuote = new Quote();
		
		
//		//fattore 1FRRIN
//		testQuote.setPreviousRenewalYears(5);
//		//fattore 3FRC1
//		testQuote.setPreviousFlagClaimsInLastYear(false);
		//fattore 2ROWMD
		testQuote.setUsualDriverOwnerRelationship(EnumUsualDriverAndOwnerRelationship.OTHER);
		//fattore _2BM
		testQuote.setGoodDriverClass(EnumGoodDriveClass.CLASS_4);
//		//fattore 1FLRFF
//		testQuote.setFiddleFactorCalculationRequired(false);
		// fattore 1ANRIN 
		testQuote.setRenewalYears(1);
//		//fattore 3FRC5
//		testQuote.setPreviousCleanIn5(false);
		// fattore 1CSC
		testQuote.setCampaign(EnumCampaign.CAMP_13);
		// fattore 1AFF
 		testQuote.setAffinity("258");
		// fattore 2NDRIV
		testQuote.setDriverNumber(3);
		// FATTORE 2ANUCF
		testQuote.setNumberOfVehiclesOwned(1);
 		//FATTORE 2ALAUT
		testQuote.setOtherVehiclesInsuredWithUs(false);
		// FATTORE 2RINNB
		testQuote.setFnb(true);
		//fattore 3CLIN5
		testQuote.setClean5(true);
		// fattore 3CLIN1
		testQuote.setClean1(false);
		//fattore SXTOT
		testQuote.setNumberOfClaimsInLastYear(0);
		//fattore _2CU
		testQuote.setInnerClass("4");
		// FATTORE 2BERS
		testQuote.setBmalType(null);

		IRatingInfo testRating = new RatingInfo();
		//fattore 2CLUWR
 		testRating.setUwClass("3");
		//fattore 2PROMC
		testRating.setPromocode(EnumPromoCode.PROMO_3);
		// fattore 2SIN6, mappaggio 0=1, 1=2...
 		testRating.setAggregatedClaims("1");
 		// fattore 2LOYAL
 		testRating.setCompanyChangesDetails("0T01");
 		// fattore 2SIN3, mappaggio 0=1, 1=2...
 		testRating.setLast3YearsAggregatedClaims("1");
 		
 		
		IVehicle testVehicle = new Vehicle();
		//fattore 2USOVE
		testVehicle.setHabitualUse(EnumHabitualUse.BOTH);
		//fattore 2ETAV
		testVehicle.setVehicleAge(6);	
		//FATTORE 2EVACQ
		testVehicle.setCarAgeAtPurchase(7);
		//FATTORE _2VVAL
		testVehicle.setInsuredValue(2000);
		//fattore 2KMAN
		testVehicle.setKmPerYear(3L);
		
		
		ITechnicalData testTechnical = new TechnicalData();
		//fattore _2ALIM
		testTechnical.setAlimentation(EnumAlimentation.ELECTRIC_MOTOR);
		//fattore _2CMAR
		testTechnical.setMakerId("1024");
		//fattore  2RROAD
		testTechnical.setType(EnumVehicleType.CAR);
		// FATTORE 2TICAR
		testTechnical.setBodyType(EnumBodyType.BERLINA_2_VOLUMI);
		//FATTORE _2KW
		testTechnical.setKw(72);
		//FATTORE 2MKMDL
		testTechnical.setTheftAndFireRiskClass(4);
		//fattore _2CMOD
		testTechnical.setModelId("2868");
////		testTechnical.setPraUse(EnumPublicRegisterUse.PRIVATE_CAR);
		
		testVehicle.setTechnicalData(testTechnical);

//		FIGURES
		List<IFigure> listFig = new ArrayList<IFigure>();
		IFigure  figureTest1 = new Figure();
		figureTest1.setRole(EnumRole.USUAL_DRIVER);
		// fattore 2MDSC
		figureTest1.setMaritalStatus(EnumMaritalStatus.NOT_DECLARED);
		// fattore 2MDPR
		figureTest1.setOccupation(EnumOccupation.RETIRED);
		// fattore _2CETA
		figureTest1.setAge(19);
		//fattore 2MDAP
		figureTest1.setYearsWithLicense(1);
		figureTest1.setLicensed(true);
		IAddress testAdress1 = new Address();
		// fattore _2CRCA
		testAdress1.setCap("63845");
		figureTest1.setResidenceAddress(testAdress1);
//		
//		
//		
		IFigure  figureTest2 = new Figure();
		figureTest2.setRole(EnumRole.POLICY_HOLDER);
		//fattore _1CETA
		figureTest2.setAge(19);
		// fattore 1PHAP
		figureTest2.setYearsWithLicense(1);
		figureTest2.setLicensed(true);
		IAddress testAdress2 = new Address();
		// fattore _1CNCA		
		testAdress2.setCap("63845");
		figureTest2.setResidenceAddress(testAdress2);
//		
//		
		IFigure  figureTest3 = new Figure();	
		figureTest3.setRole(EnumRole.OWNER);
		// fattore _2PETA
		figureTest3.setAge(19);
		// fattore 2OWAP
		figureTest3.setYearsWithLicense(1);
		figureTest3.setLicensed(true);
		IAddress testAdress3 = new Address();
		// fattore _2PRCA
		testAdress3.setCap("63845");
////		testAdress3.setProvince("FM");
		figureTest3.setResidenceAddress(testAdress3);
//	
		listFig.add(figureTest1);
		listFig.add(figureTest2);
		listFig.add(figureTest3);
		
//		vvvvvvvvvvvvvv ADDON vvvvvvvvvvvvvv
		testQuote.setEffectiveDate(new Date());
		IContext contextAddon = new Context();
		contextAddon.setRiskType(EnumRiskType.CAR);
		contextAddon.setProductType(EnumProductType.DLI);
		contextAddon.setDirectlineSelfService(true);
//		2TIPGU
		contextAddon.setSection(EnumSection.PPA);
//		operation quote
//		contextAddon.setFlowType(EnumFlowType.NEW_QUOTE);
		
		List<ICoverage> listCov = new ArrayList<ICoverage>();
		  ICoverage testCov = new Coverage();
//			TODO ADDON
			testQuote.setInstallments(1);
			testQuote.setRateFromDate(new Date());
			testQuote.setEnableDebugging(true);

			// ADDON
			testCov.setCode(EnumCoverageCode.MOTOR_RCA);
			testCov.setSelected(true);
//		  	testCov.setPreviousNetAmount(284.3904D);
//			testCov.setQuickAndDirty(0.95D);
			//fattore 1FIDRC
			testCov.setFiddleFactor(1.0);
			//fattore 3ADJ
			testCov.setDiscount(1.0);
			
			// aggiunto per RCA
			ILimit testLimit = new Limit();
			//fattore 3MASS
			testLimit.setCode("3");
			
			IDeductible testDeduct = new Deductible();
			//fattore 3RCFRA
			testDeduct.setCode("1");

			testCov.setLimit(testLimit);
			testCov.setDeductible(testDeduct);
			listCov.add(testCov);
	
			
		testQuote.setRatingInfo(testRating);
		testQuote.setVehicle(testVehicle);
		testQuote.setCoverages(listCov);
		testQuote.setFigures(listFig);
		testQuote.setCoverages(listCov);
		testQuote.setContext(contextAddon);

		
		return testQuote;
	}
	public Quote getRiga3SiFF()
	{
		Quote testQuote = new Quote();
		
		
		//fattore 1FRRIN
		testQuote.setPreviousRenewalYears(5);
		//fattore 3FRC1
		testQuote.setPreviousFlagClaimsInLastYear(false);
		//fattore 2ROWMD
		testQuote.setUsualDriverOwnerRelationship(EnumUsualDriverAndOwnerRelationship.OTHER);
		//fattore _2BM
		testQuote.setGoodDriverClass(EnumGoodDriveClass.CLASS_4);
		//fattore 1FLRFF
		testQuote.setFiddleFactorCalculationRequired(false);
		// fattore 1ANRIN 
		testQuote.setRenewalYears(1);
		//fattore 3FRC5
		testQuote.setPreviousCleanIn5(false);
		// fattore 1CSC
		testQuote.setCampaign(EnumCampaign.CAMP_13);
		// fattore 1AFF
		testQuote.setAffinity("258");
		// fattore 2NDRIV
		testQuote.setDriverNumber(3);
		// FATTORE 2ANUCF
		testQuote.setNumberOfVehiclesOwned(1);
		//FATTORE 2ALAUT
		testQuote.setOtherVehiclesInsuredWithUs(false);
		// FATTORE 2RINNB
		testQuote.setFnb(true);
		//fattore 3CLIN5
		testQuote.setClean5(true);
		// fattore 3CLIN1
		testQuote.setClean1(false);
		//fattore SXTOT
		testQuote.setNumberOfClaimsInLastYear(0);
		//fattore _2CU
		testQuote.setInnerClass("4");
		// FATTORE 2BERS
		testQuote.setBmalType(EnumBmalType.NO_BERSANI);
		
		IRatingInfo testRating = new RatingInfo();
		//fattore 2CLUWR
		testRating.setUwClass("3");
		//fattore 2PROMC
		testRating.setPromocode(EnumPromoCode.PROMO_3);
		// fattore 2SIN6
		testRating.setAggregatedClaims("0");
		// fattore 2LOYAL
		testRating.setCompanyChangesDetails("0T01");
		// fattore 2SIN3
		testRating.setLast3YearsAggregatedClaims("01");
		
		
		IVehicle testVehicle = new Vehicle();
		//fattore 2USOVE
		testVehicle.setHabitualUse(EnumHabitualUse.BOTH);
		//fattore 2ETAV
		testVehicle.setVehicleAge(6);	
		//FATTORE 2EVACQ
		testVehicle.setCarAgeAtPurchase(7);
		//FATTORE _2VVAL
		testVehicle.setInsuredValue(2000);
		//fattore 2KMAN
		testVehicle.setKmPerYear(3L);
		
		
		ITechnicalData testTechnical = new TechnicalData();
		//fattore _2ALIM
		testTechnical.setAlimentation(EnumAlimentation.ELECTRIC_MOTOR);
		//fattore _2CMAR
		testTechnical.setMakerId("1024");
		//fattore  2RROAD
		testTechnical.setType(EnumVehicleType.CAR);
		// FATTORE 2TICAR
		testTechnical.setBodyType(EnumBodyType.BERLINA_2_VOLUMI);
		//FATTORE _2KW
		testTechnical.setKw(72);
		//FATTORE 2MKMDL
		testTechnical.setTheftAndFireRiskClass(4);
		//fattore _2CMOD
		testTechnical.setModelId("2868");
//		testTechnical.setPraUse(EnumPublicRegisterUse.PRIVATE_CAR);
		
		testVehicle.setTechnicalData(testTechnical);
		
//		FIGURES
		List<IFigure> listFig = new ArrayList<IFigure>();
		IFigure  test1 = new Figure();
		test1.setRole(EnumRole.USUAL_DRIVER);
		// fattore 2MDSC
		test1.setMaritalStatus(EnumMaritalStatus.NOT_DECLARED);
		// fattore 2MDPR
		test1.setOccupation(EnumOccupation.RETIRED);
		// fattore _2CETA
		test1.setAge(19);
		//fattore 2MDAP
		test1.setYearsWithLicense(1);
		test1.setLicensed(true);
		IAddress testAdress1 = new Address();
		// fattore _2CRCA
		testAdress1.setCap("63845");
		test1.setResidenceAddress(testAdress1);
//		
//		
//		
		IFigure  test2 = new Figure();
		test2.setRole(EnumRole.POLICY_HOLDER);
		//fattore _1CETA
		test2.setAge(19);
		// fattore 1PHAP
		test2.setYearsWithLicense(1);
		test2.setLicensed(true);
		IAddress testAdress2 = new Address();
		// fattore _1CNCA		
		testAdress2.setCap("63845");
		test2.setResidenceAddress(testAdress2);
//		
//		
		IFigure  test3 = new Figure();	
		test3.setRole(EnumRole.OWNER);
		// fattore _2PETA
		test3.setAge(19);
		// fattore 2OWAP
		test3.setYearsWithLicense(1);
		test3.setLicensed(true);
		IAddress testAdress3 = new Address();
		// fattore _2PRCA
		testAdress3.setCap("63845");
//		testAdress3.setProvince("FM");
		test3.setResidenceAddress(testAdress3);
//	
		listFig.add(test1);
		listFig.add(test2);
		listFig.add(test3);
		
//		vvvvvvvvvvvvvv ADDON vvvvvvvvvvvvvv
		testQuote.setEffectiveDate(new Date());
		IContext contextAddon = new Context();
		contextAddon.setRiskType(EnumRiskType.CAR);
		contextAddon.setProductType(EnumProductType.DLI);
		contextAddon.setDirectlineSelfService(true);
//		2TIPGU
		contextAddon.setSection(EnumSection.PPA);
//		operation quote
//		contextAddon.setFlowType(EnumFlowType.NEW_QUOTE);
		
		List<ICoverage> listCov = new ArrayList<ICoverage>();
		ICoverage testCov = new Coverage();
//			TODO ADDON
		testQuote.setInstallments(1);
		testQuote.setRateFromDate(new Date());
		testQuote.setEnableDebugging(true);
		
		//commentato
		testCov.setPreviousNetAmount(284.3904D);
		testCov.setCode(EnumCoverageCode.MOTOR_RCA);
		testCov.setSelected(true);
		testCov.setQuickAndDirty(0.95D);
		//fattore 1FIDRC
		testCov.setFiddleFactor(1.0);
		//fattore 3ADJ
		testCov.setDiscount(1.0);
		
		// aggiunto per RCA
		ILimit testLimit = new Limit();
		//fattore 3MASS
		testLimit.setCode("3");
		
		IDeductible testDeduct = new Deductible();
		//fattore 3RCFRA
		testDeduct.setCode("1");
		
		testCov.setLimit(testLimit);
		testCov.setDeductible(testDeduct);
		listCov.add(testCov);
		
		
		testQuote.setRatingInfo(testRating);
		testQuote.setVehicle(testVehicle);
		testQuote.setCoverages(listCov);
		testQuote.setFigures(listFig);
		testQuote.setCoverages(listCov);
		testQuote.setContext(contextAddon);
		
		
		return testQuote;
	}
	
}
