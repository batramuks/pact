package com.calculatorPactClient;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.calculatorPactClient.model.DataRequest;

public class CalculatorPactClient {
	int port = 9001;
	private RestTemplate restTemplate = new RestTemplate();

	CalculatorPactClient() {
		// Will use default port.
		System.out.println("Default port " + port);
	}

	CalculatorPactClient(int port) {
		this.port = port;
		System.out.println("Custom port " + port);
	}

	public static void main(String[] args) {
		DataRequest dataRequest = new DataRequest();
		dataRequest.setA(100l);
		dataRequest.setB(400l);
		Long addResult = new CalculatorPactClient().addition(dataRequest);
		System.out.println("addResult=" + addResult);
	}

	public Long addition(DataRequest dataRequest) {
		try {
			String url = String.format("Http://localhost:%d/calculate/add", port);

			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.add("Content-Type", "application/json");
			ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, dataRequest, String.class);
			System.out.println(responseEntity.getBody());
			
			
			return 500l;

		} catch (Exception e) {
			System.out.println("Unable to get add result " + e);
			return null;
		}
	}


}
