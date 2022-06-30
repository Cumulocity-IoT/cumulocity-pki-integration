package com.cumulocity.pkiintegration.service;

import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import com.cumulocity.microservice.subscription.service.MicroserviceSubscriptionsService;
import com.cumulocity.model.idtype.GId;
import com.cumulocity.sdk.client.identity.ExternalIDCollection;
import com.cumulocity.sdk.client.identity.IdentityApi;
import com.google.common.io.CharSource;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CertificateManagementService {

	private static final String SIMPLEENROLL = "/simpleenroll";
	private static final String CACERTS = "/cacerts";
	private static final String PKCS_PLACEHOLDER = "PKCS7DATA";
	private static final String PKCS7_HEADER = "-----BEGIN PKCS7-----\n" + PKCS_PLACEHOLDER + "\n-----END PKCS7-----";
	private final HttpRestRequestService request;
	private final CSRService csrService;
	private final IdentityApi identity;
	private final MicroserviceSubscriptionsService subscriptionService;

	public Object simpleEnrollRequest(String deviceId) throws Exception {
		String tenantId = subscriptionService.getTenant();
		String deviceExternalId = getDeviceExternalId(deviceId);
		Optional<String> csrBody = csrService.GenerateCSR(deviceExternalId, tenantId);

		if (!csrBody.isEmpty()) {
			// String csrBody = csrService.GenerateCSR(deviceId);
			String response = request.sendRequest(HttpMethod.POST, SIMPLEENROLL, csrBody.get(), String.class);
			return extractCertificate(response);
		} else {
			// TODO: create meaningful custom exception
			throw new Exception("Unable to generate PKCS10 CSR Body, check logs for error details");
		}
	}
	
	public String getCacerts() {
		String response = request.sendRequest(HttpMethod.GET, CACERTS, String.class);
		return response;
	}

	private String getDeviceExternalId(String deviceId) {
		ExternalIDCollection externalIdRepresentaton = identity.getExternalIdsOfGlobalId(new GId(deviceId));
		String deviceExternalId = externalIdRepresentaton.get(0).getExternalIds().get(0).getExternalId();
		return deviceExternalId;
	}

	private String extractCertificate(String pkcsSevenData) throws Exception {
		pkcsSevenData = PKCS7_HEADER.replace(PKCS_PLACEHOLDER, pkcsSevenData);
		String certificateString = "";
		InputStream is = CharSource.wrap(pkcsSevenData).asByteSource(StandardCharsets.UTF_8).openStream();
		CertificateFactory cf = CertificateFactory.getInstance("X.509");

		Collection<? extends Certificate> c = cf.generateCertificates(is);
		Iterator<? extends Certificate> i = c.iterator();
		while (i.hasNext()) {
			X509Certificate cert = (X509Certificate) i.next();
			certificateString = printCertInfo(pkcsSevenData, cert);
		}
		return certificateString;
	}

	private String printCertInfo(String pkcsSevenData, X509Certificate cert) throws CertificateEncodingException {
		StringBuilder sb = new StringBuilder();

		sb.append("PKCS7 DATA:\r\n");
		sb.append(pkcsSevenData);
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append("CERTIFICATE INFO:\r\n");
		sb.append("\tVersion: " + cert.getVersion() + "\r\n");
		sb.append("\tSerial Number: " + cert.getSerialNumber() + "\r\n");
		sb.append("\tSignature Algorithm: " + cert.getSigAlgName() + "\r\n");
		sb.append("\tIssuer: " + cert.getIssuerDN().getName() + "\r\n");
		sb.append("\tSubject: " + cert.getSubjectDN() + "\r\n");
		sb.append("\tValidity\r\n");
		sb.append("\t\tNot Before: " + cert.getNotBefore() + "\r\n");
		sb.append("\t\tNot After: " + cert.getNotAfter() + "\r\n");
		sb.append("\r\n");
		sb.append("-----BEGIN CERTIFICATE-----\n");
		sb.append(Base64.getEncoder().encodeToString(cert.getEncoded()));
		sb.append("\n-----END CERTIFICATE-----");

		// TODO: Print other attributes, do some research to get them all...

		return sb.toString();
	}
	
	// TODO: Remove this method if not required
	public Object getRegistrations() throws RestClientException, URISyntaxException {
		String endpoint = "/registrations/t4793186911343297060/est";
		Object response = request.sendRequest(HttpMethod.GET, endpoint, Object.class);
		return response;
	}
}
