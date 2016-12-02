package it.cg.main.dto.main;

import java.util.ArrayList;
import java.util.List;

import com.mapfre.engines.rating.business.objects.wrapper.RatingQuote;
import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.ICoverage;
import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.IFigure;

public class Quote extends RatingQuote
{

	@Override
	public List<ICoverage> getCoverages()
	{
		if(super.getCoverages() == null)
		{
			super.setCoverages(new ArrayList<ICoverage>(0));
		}
		return super.getCoverages();
	}
	
	@Override
	public List<IFigure> getFigures()
	{
		if(super.getFigures() == null)
		{
			super.setFigures(new ArrayList<IFigure>(0));
		}
		return super.getFigures();
	}
	
//	Da qua in poi iniziano i nuovi attributi presi dalla versione 1.1 non presenti nel file di debora.
	
//	private Boolean policyHolderEqualMainDriver;
//	private Boolean policyHolderEqualVehicleOwner;
//	private Boolean ownerEqualMainDriver;
//	private Integer driverNumber;
//	private Boolean clean1;
//	private Boolean clean5;
//	private Boolean enableDebugging;
//	private String debuggingLog;
		
	
	
	

}
