package it.cg.main.process.mapping.utilities;

import java.util.HashMap;
import java.util.Map;

public class MapperHashmapUtilitiesToDL
{
	private Map<String, String> mapWorstProvince = new HashMap<String, String>();
	
	public String getWorstProvince(String idProvincePASS)
	{
		String result = "";

		result = mapWorstProvince.get(idProvincePASS);
		
		return result;
	}
	
	public MapperHashmapUtilitiesToDL()
	{
		if(!mapWorstProvince.isEmpty())
		{
			return;
		}
		mapWorstProvince.put("1","TO");
		mapWorstProvince.put("2","VC");
		mapWorstProvince.put("3","NO");
		mapWorstProvince.put("4","CN");
		mapWorstProvince.put("5","AT");
		mapWorstProvince.put("6","AL");
		mapWorstProvince.put("7","AO");
		mapWorstProvince.put("8","IM");
		mapWorstProvince.put("9","SV");
		mapWorstProvince.put("10","GE");
		mapWorstProvince.put("11","SP");
		mapWorstProvince.put("12","VA");
		mapWorstProvince.put("13","CO");
		mapWorstProvince.put("14","SO");
		mapWorstProvince.put("15","MI");
		mapWorstProvince.put("16","BG");
		mapWorstProvince.put("17","BS");
		mapWorstProvince.put("18","PV");
		mapWorstProvince.put("19","CR");
		mapWorstProvince.put("20","MN");
		mapWorstProvince.put("21","BZ");
		mapWorstProvince.put("22","TN");
		mapWorstProvince.put("23","VR");
		mapWorstProvince.put("24","VI");
		mapWorstProvince.put("25","BL");
		mapWorstProvince.put("26","TV");
		mapWorstProvince.put("27","VE");
		mapWorstProvince.put("28","PD");
		mapWorstProvince.put("29","RO");
		mapWorstProvince.put("30","UD");
		mapWorstProvince.put("31","GO");
		mapWorstProvince.put("32","TS");
		mapWorstProvince.put("33","PC");
		mapWorstProvince.put("34","PR");
		mapWorstProvince.put("35","RE");
		mapWorstProvince.put("36","MO");
		mapWorstProvince.put("37","BO");
		mapWorstProvince.put("38","FE");
		mapWorstProvince.put("39","RA");
		mapWorstProvince.put("40","FC");
		mapWorstProvince.put("41","PU");
		mapWorstProvince.put("42","AN");
		mapWorstProvince.put("43","MC");
		mapWorstProvince.put("44","AP");
		mapWorstProvince.put("45","MS");
		mapWorstProvince.put("46","LU");
		mapWorstProvince.put("47","PT");
		mapWorstProvince.put("48","FI");
		mapWorstProvince.put("49","LI");
		mapWorstProvince.put("50","PI");
		mapWorstProvince.put("51","AR");
		mapWorstProvince.put("52","SI");
		mapWorstProvince.put("53","GR");
		mapWorstProvince.put("54","PG");
		mapWorstProvince.put("55","TR");
		mapWorstProvince.put("56","VT");
		mapWorstProvince.put("57","RI");
		mapWorstProvince.put("58","RM");
		mapWorstProvince.put("59","LT");
		mapWorstProvince.put("60","FR");
		mapWorstProvince.put("61","CE");
		mapWorstProvince.put("62","BN");
		mapWorstProvince.put("63","NA");
		mapWorstProvince.put("64","AV");
		mapWorstProvince.put("65","SA");
		mapWorstProvince.put("66","AQ");
		mapWorstProvince.put("67","TE");
		mapWorstProvince.put("68","PE");
		mapWorstProvince.put("69","CH");
		mapWorstProvince.put("70","CB");
		mapWorstProvince.put("71","FG");
		mapWorstProvince.put("72","BA");
		mapWorstProvince.put("73","TA");
		mapWorstProvince.put("74","BR");
		mapWorstProvince.put("75","LE");
		mapWorstProvince.put("76","PZ");
		mapWorstProvince.put("77","MT");
		mapWorstProvince.put("78","CS");
		mapWorstProvince.put("79","CZ");
		mapWorstProvince.put("80","RC");
		mapWorstProvince.put("81","TP");
		mapWorstProvince.put("82","PA");
		mapWorstProvince.put("83","ME");
		mapWorstProvince.put("84","AG");
		mapWorstProvince.put("85","CL");
		mapWorstProvince.put("86","EN");
		mapWorstProvince.put("87","CT");
		mapWorstProvince.put("88","RG");
		mapWorstProvince.put("89","SR");
		mapWorstProvince.put("90","SS");
		mapWorstProvince.put("91","NU");
		mapWorstProvince.put("92","CA");
		mapWorstProvince.put("93","PN");
		mapWorstProvince.put("94","IS");
		mapWorstProvince.put("95","OR");
		mapWorstProvince.put("96","BI");
		mapWorstProvince.put("97","LC");
		mapWorstProvince.put("98","LO");
		mapWorstProvince.put("99","RN");
		mapWorstProvince.put("100","PO");
		mapWorstProvince.put("101","KR");
		mapWorstProvince.put("102","VV");
		mapWorstProvince.put("103","VB");
		mapWorstProvince.put("104","OT");
		mapWorstProvince.put("105","OG");
		mapWorstProvince.put("106","VS");
		mapWorstProvince.put("107","CI");
		mapWorstProvince.put("108","BT");
		mapWorstProvince.put("109","FM");
		mapWorstProvince.put("110","MB");
		mapWorstProvince.put("910","ZY");
		mapWorstProvince.put("993","ZZ");
	}

}
