package test.luigi;

import com.google.gson.Gson;

import it.cg.main.dto.InboundRequestHttpJSON;

public class ExecuteGsonLuigi {

	public static void main(String[] args)
	{
//		SOS sos = new SOS();
//		Quote sosNOFFriga = sos.getRiga1(false);
//		Quote sosNOFFriga = sos.getRiga2(false);
//		Quote sosNOFFriga = sos.getRiga3(false);
		
//		Quote sosFFriga = sos.getRiga1(true);
//		Quote sosFFriga = sos.getRiga2(true);
//		Quote sosFFriga = sos.getRiga3(true);
//		Motocarri motocarriGen = new Motocarri();
//		Quote motocarriFFriga = motocarriGen.getRiga1(false);
		RCA rcaTest = new RCA();
		
//		vvvvvvvvvvvvvvvv
		
		InboundRequestHttpJSON jsonComplete = new InboundRequestHttpJSON();
//		jsonComplete.setInboundQuoteDTO(sosFFriga);
		jsonComplete.setInboundQuoteDTO(rcaTest.getRiga3NoFF());
		jsonComplete.setServiceType("easy");
		
		Gson gson = new Gson();
		System.out.println(gson.toJson(jsonComplete));

	}

}
