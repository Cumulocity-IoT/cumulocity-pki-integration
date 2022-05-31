package com.cumulocity.pkiintegration.service;

import java.net.URISyntaxException;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CertificateManagementService {

	private final RestTemplate restTemplate;

	public Object getRegistrations() throws RestClientException, URISyntaxException {
		String url = "https://pgw.38.qa.go.nexusgroup.com/api/registrations/t4793186911343297060/est";
		ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);

		return response.getBody();
	}
}
