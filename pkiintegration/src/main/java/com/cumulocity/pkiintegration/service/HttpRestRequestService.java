package com.cumulocity.pkiintegration.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class HttpRestRequestService {

	// TODO: Move to tenant options
	private static final String NEXUS_BASE_URL = "https://pgw.38.qa.go.nexusgroup.com/api";
	private final RestTemplate restTemplate;

	private HttpEntity<Object> getHttpEntity(final Object requestBody) {
		final HttpHeaders headers = new HttpHeaders();

		if (requestBody != null) {
			return new HttpEntity<>(requestBody, headers);
		} else {
			return new HttpEntity<>(headers);
		}
	}

	public <T> T sendRequest(final HttpMethod requestMethod, final String path, final Class<T> responseType) {
		log.trace("Sending {} request with query {} on {}", requestMethod, path);
		final HttpEntity<Object> entity = getHttpEntity(null);

		ResponseEntity<T> response;
		try {
			response = restTemplate.exchange(NEXUS_BASE_URL + path, requestMethod, entity, responseType);
		} catch (final HttpStatusCodeException e) {
			log.error(e.getMessage());
			throw e;
		}
		return evaluateResponse(response);
	}

	public <T> T sendRequest(final HttpMethod requestMethod, final String path, final Object requestBody,
			final Class<T> responseType) {
		log.trace("Sending {} request {} on {}", requestMethod, requestBody, path);
		final HttpEntity<Object> entity = getHttpEntity(requestBody);
		ResponseEntity<T> response;
		try {
			response = restTemplate.exchange(NEXUS_BASE_URL + path, requestMethod, entity, responseType);
			return evaluateResponse(response);
		} catch (final HttpStatusCodeException e) {
			log.error(e.getMessage());
			throw e;
		}
	}

	private <T> T evaluateResponse(final ResponseEntity<T> response) {
		final HttpStatus responseStatus = response.getStatusCode();
		if (responseStatus.is2xxSuccessful()) {
			return response.getBody();
		} else {
			if (responseStatus.is4xxClientError()) {
				throw new HttpClientErrorException(responseStatus);
			} else {
				throw new HttpServerErrorException(responseStatus);
			}
		}
	}
}
