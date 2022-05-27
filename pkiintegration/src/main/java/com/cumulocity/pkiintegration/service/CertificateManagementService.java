package com.cumulocity.pkiintegration.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cumulocity.pkiintegration.model.Registration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CertificateManagementService {

	private final RestTemplate restTemplate;

	public Registration[] getRegistrations() {
		HttpEntity<?> httpEntity = new HttpEntity<>(null, new HttpHeaders());
		String url = "https://pgw.38.qa.go.nexusgroup.com/api/registrations/t4793186911343297060/est";
		ResponseEntity<Registration[]> response = restTemplate.getForEntity(url, Registration[].class);
		Registration[] registrations = response.getBody();
		
		return registrations;
	}

}
