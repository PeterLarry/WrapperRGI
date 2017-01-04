package test;

import com.google.gson.Gson;

import it.cg.main.dto.InboundRequestHttpJSON;

public class RunTestMain {

	public static void main(String[] args)
	{
		TestGson testGson = new TestGson();
		InboundRequestHttpJSON responseInbound = testGson.getQuoteAutoDLICollisione();

		Gson gson = new Gson();
		
		System.out.println(gson.toJson(responseInbound));
		
	}

}
