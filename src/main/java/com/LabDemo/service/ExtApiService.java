package com.LabDemo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ExtApiService {

	private static final Logger logger = LoggerFactory.getLogger(ExtApiService.class);

	@Autowired
	RestTemplate restTemplate;
	
	public ResponseEntity<String> doSendExtApiGetResponseData(UriComponentsBuilder builder) {
		// 呼叫外部API
		logger.info("========Start doSendExtApiGetResponseData =====");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

		ResponseEntity<String> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET,
				new HttpEntity(headers), String.class);
		logger.info("===response:{}", response.getBody());
		logger.info("========End doSendExtApiGetResponseData =====");
		return response;
	}
}
