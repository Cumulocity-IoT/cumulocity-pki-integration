package com.cumulocity.pkiintegration.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CertificateManagementService {

	private static final String BASE_URL = null;
	private final RestTemplate resttemplate;
	
	public void testRequest() {
		HttpEntity<?> httpEntity = new HttpEntity<>(null, new HttpHeaders());
}
	
	
}
