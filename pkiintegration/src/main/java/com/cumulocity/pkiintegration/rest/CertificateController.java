package com.cumulocity.pkiintegration.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cumulocity.pkiintegration.service.CertificateManagementService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/certificates")
public class CertificateController {
	
	private final CertificateManagementService certificateManagementService;
	
	@PostMapping("/generateCertificate/{deviceId}")
	public ResponseEntity<?> generateCertificate(@PathVariable final String deviceId) throws Exception {
		return ResponseEntity.status(HttpStatus.OK).body(certificateManagementService.simpleEnrollRequest(deviceId));
	}
}
