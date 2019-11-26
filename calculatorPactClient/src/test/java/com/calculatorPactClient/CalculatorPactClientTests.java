package com.calculatorPactClient;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;

import com.calculatorPactClient.model.DataRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;



public class CalculatorPactClientTests {
	
	@Rule
    public PactProviderRuleMk2 provider = new PactProviderRuleMk2("CalculatorService", "localhost", 8001, this);

	@Pact(consumer = "CalculatorServiceClient")
    public RequestResponsePact createPact(PactDslWithProvider builder) throws JsonProcessingException {
        Map<String, String> headers = new HashMap();
        headers.put("Content-Type", "application/json");


        DslPart addResults = new PactDslJsonBody()
                .integerType("500")
                .asBody();
        
    	DataRequest dataRequest = new DataRequest();
		dataRequest.setA(100l);
		dataRequest.setB(400l);
		
        return builder
                .given("Add Numbers")
                .uponReceiving("Add Numbers")
                .path("/calculate/add")
                .method("POST").body(mapToJson(dataRequest))
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(addResults).toPact();

    }

    @Test
    @PactVerification()
    public void doTest() {
    	DataRequest dataRequest = new DataRequest();
		dataRequest.setA(100l);
		dataRequest.setB(400l);
		
        System.setProperty("c:/","/Batra/pacts");  // Change output dir for generated pact-files
        Long addResult = new CalculatorPactClient(provider.getPort()).addition(dataRequest);
        System.out.println("According to test addResult="+addResult);
        assertTrue(addResult == 500);
    }
    
	String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}
}
