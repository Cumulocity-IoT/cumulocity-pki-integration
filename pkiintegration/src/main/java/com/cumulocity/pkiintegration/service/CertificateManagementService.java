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

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import com.google.common.io.CharSource;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CertificateManagementService {

	private final HttpRestRequestService request;
	private final CSRService csrService;

	public Object getRegistrations() throws RestClientException, URISyntaxException {
		String endpoint = "/registrations/t4793186911343297060/est";
		Object response = request.sendRequest(HttpMethod.GET, endpoint, Object.class);
		return response;
	}

	public Object simpleEnrollRequest() throws Exception {
		String csrBody = csrService.GenerateCSR();
		String endpoint = "/simpleenroll";
		String response = request.sendRequest(HttpMethod.POST, endpoint, csrBody, String.class);
		
		return extractCertificate(response);
	}

	private String extractCertificate(String pkcsSevenData) throws Exception {
		pkcsSevenData = "-----BEGIN PKCS7-----\n" + pkcsSevenData + "\n-----END PKCS7-----";

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
		sb.append(Base64.getMimeEncoder().encodeToString(cert.getEncoded()));
		sb.append("\n-----END CERTIFICATE-----");

		// TODO: Print other attributes, do some research to get them all...

		return sb.toString();
	}
}
