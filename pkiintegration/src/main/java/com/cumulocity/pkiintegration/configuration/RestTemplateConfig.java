package com.cumulocity.pkiintegration.configuration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class RestTemplateConfig {

	@Bean
	public RestTemplate restTemplate() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException, UnrecoverableKeyException, KeyManagementException {
	    log.info("Initializing RestTemplate to connect with Certificate-Authority API");
	    String certificatePassword = "FU63C84I";
		KeyStore clientStore = KeyStore.getInstance("PKCS12");
		InputStream is = getClass().getClassLoader().getResourceAsStream("so38.2.p12");
	    clientStore.load(is, certificatePassword.toCharArray());
	    
	    SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
	    sslContextBuilder.setProtocol("TLS");
	    sslContextBuilder.loadKeyMaterial(clientStore, certificatePassword.toCharArray());
	    sslContextBuilder.loadTrustMaterial(new TrustSelfSignedStrategy());

	    SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContextBuilder.build());
	    CloseableHttpClient httpClient = HttpClients.custom()
	            .setSSLSocketFactory(sslConnectionSocketFactory)
	            .build();
	    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
	    return new RestTemplate(requestFactory);
	}
}
