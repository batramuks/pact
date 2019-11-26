package com.calculatorPactServer.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.calculatorPactServer.model.DataRequest;
@RestController
public class CalculatorController implements CalulatorInterf {

	public ResponseEntity<?> getAddition(@RequestBody DataRequest dataRequest) {
		Long result = dataRequest.getA() + dataRequest.getB();

		return new ResponseEntity<Long>(result, HttpStatus.OK);
	}

}
