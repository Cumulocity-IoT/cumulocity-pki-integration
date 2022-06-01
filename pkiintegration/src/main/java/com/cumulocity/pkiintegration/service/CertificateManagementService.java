package com.cumulocity.pkiintegration.service;

import java.net.URISyntaxException;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CertificateManagementService {

	private final HttpRestRequestService request;

	public Object getRegistrations() throws RestClientException, URISyntaxException {
		String endpoint = "/registrations/t4793186911343297060/est";
		Object response = request.sendRequest(HttpMethod.GET, endpoint, Object.class);
		return response;
	}
}
