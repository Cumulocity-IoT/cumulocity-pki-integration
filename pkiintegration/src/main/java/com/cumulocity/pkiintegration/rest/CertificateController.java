package com.cumulocity.pkiintegration.rest;

import java.net.URISyntaxException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import com.cumulocity.pkiintegration.service.CertificateManagementService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/certificates")
public class CertificateController {
	
	private final CertificateManagementService certificateManagementService;
	
	@GetMapping("/registrations")
	public ResponseEntity<?> getCertificateRegsitrations() throws RestClientException, URISyntaxException {
		return ResponseEntity.status(HttpStatus.OK).body(certificateManagementService.getRegistrations());
	}
}
